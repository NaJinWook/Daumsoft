package com.daumsoft.test6.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daumsoft.test6.SolrJDriver;
import com.daumsoft.test6.model.dto.PagerDTO;

@Controller
@RequestMapping("/*")
public class SolrController {
	final static int PAGEPERGROUP = 10; // 그룹 당 페이지 수
	final static int ROWS = 10; // 보여질 게시글 수
	
	@RequestMapping("main")
	public String main() {
		return "board/main";
	}
	
	@RequestMapping(value="search", method= RequestMethod.GET)
	public String search(HttpServletRequest request, Model model,
			@RequestParam(value = "sort", required = false, defaultValue = "") String sort,
			@RequestParam(value = "oName", required = false, defaultValue = "") String oName,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "regDate", required = false, defaultValue = "all") String regDate) {
		String keyword = request.getParameter("keyword"); // 키워드 파라미터로 받음
		SolrQuery query;
		QueryResponse responseSolr;
		SolrDocumentList results;
		int totalRecordsCount;
		int start = currentPage == 1 ? 0 : (currentPage-1) * 10;
		PagerDTO pager = null; // 페이징 처리를 위함
		
		try {
			if(!"".equals(keyword)) {
				query = new SolrQuery(); // 쿼리 객체 생성
				String keywordQuery = "title:*" + keyword + "*^2" + "+content:" + keyword;
				query.set("q", keywordQuery);
				
				// hl=true&hl.fl=title&hl.simple.pre=<strong>&hl.simple.post=</strong>
				query.setHighlight(true);
				query.setParam("hl.fl", "content");
				query.setParam("hl.simple.pre", "<span class=hl>");
				query.setParam("hl.simple.post", "</span>");
				
				if(sort.equals("desc")) {
					query.setSort("regDate", ORDER.desc);
				} else if(sort.equals("asc")) {
					query.setSort("regDate", ORDER.asc);
				}
				if(!oName.equals("")) {
					query.addFilterQuery("oName : " + oName);
				}
				query.setStart(start);
				query.setRows(ROWS);
				
				query.setFacet(true); // 패싯을 사용
				query.addFacetField("title"); // 필드 지정
				query.setParam("facet.prefix" , keyword); // ex) 조국~~
				query.setFacetLimit(10); // 상위 10개 limit
				
				if(regDate.equals("all")) {
					query.addFilterQuery("regDate : *");
				} else if(regDate.equals("1D")) {
					query.addFilterQuery("regDate : [NOW-1DAYS TO NOW]");
				} else if(regDate.equals("7D")) {
					query.addFilterQuery("regDate : [NOW-7DAYS TO NOW]");
				} else if(regDate.equals("1M")) {
					query.addFilterQuery("regDate : [NOW-1MONTHS TO NOW]");
				}
				
				responseSolr = SolrJDriver.solr.query(".", query);
				results = responseSolr.getResults();
				totalRecordsCount = (int)results.getNumFound(); // 전체 게시글 수
				pager = new PagerDTO(ROWS, PAGEPERGROUP, currentPage, totalRecordsCount); // 페이징 처리 객체 생성
				Map<String, Map<String, List<String>>> map = responseSolr.getHighlighting();
				List<Count> facetList = responseSolr.getFacetFields().get(0).getValues(); // 리스트에 패싯 키워드 담음
				List<String> conversionList = conversion(facetList); // 리스트를 패싯 키워드에 (count) 값 삭제
				
				
				// 날짜 포맷 Wed Oct 09 13:04:00 KST 2019
				SimpleDateFormat originDataFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", new Locale("en","US"));
				SimpleDateFormat newDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				List<String> transDateList = new ArrayList<String>();
				Object[] transDate = results.toArray();
				for(Object obj : transDate) {
					String str = obj.toString();
					//2019-10-07T03:32:00Z
					str = str.substring(str.indexOf("regDate=")+8, str.indexOf("regDate=")+36);
//					System.out.println("str : " + str);
					Date date = originDataFormat.parse(str);
//					System.out.println("변경 전 시간 : " + date);
					long dateLong = date.getTime() - 32400000; // -9시간 뺌
					String viewDate = newDataFormat.format(new Date(dateLong));
//					System.out.println("변경 후 시간 : " + date);
					transDateList.add(viewDate);
				}
				
				model.addAttribute("transDateList", transDateList);
				model.addAttribute("regDate", regDate);
				model.addAttribute("facetList", conversionList);
				model.addAttribute("map", map);
				model.addAttribute("pager", pager);
				model.addAttribute("totalRecordsCount", totalRecordsCount);
				model.addAttribute("keyword", keyword);
				model.addAttribute("sort", sort);
				model.addAttribute("oName", oName);
				model.addAttribute("rows", ROWS);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("dataList", results.toArray());
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return "board/print";
	}
	
	public List<String> conversion(List<Count> facetList) {
		List<String> conversionList = new ArrayList<String>();
		for(Count count : facetList) {
			String str = count.toString();
			char[] cArr = str.toCharArray();
			for(int i=0; i<str.length(); i++) {
				if(cArr[i] == '(' || cArr[i] == ')' || ((int)cArr[i] > 47  && (int)cArr[i] < 58)) {
					cArr[i] = ' ';
				}
			}
			str = String.valueOf(cArr).trim();
			conversionList.add(str);
		}
		return conversionList;
	}
}
