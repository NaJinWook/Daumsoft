package com.daumsoft.test6.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
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
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search(HttpServletRequest request, Model model,
			@RequestParam(value = "sort", required = false, defaultValue = "") String sort,
			@RequestParam(value = "oName", required = false, defaultValue = "") String oName,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
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
				String keywordQuery = "title:" + keyword + " OR content:" + keyword;
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
				
				responseSolr = SolrJDriver.solr.query(".", query);
				results = responseSolr.getResults();
				totalRecordsCount = (int)results.getNumFound(); // 전체 게시글 수
				
				pager = new PagerDTO(ROWS, PAGEPERGROUP, currentPage, totalRecordsCount); // 페이징 처리 객체 생성
				
				Map<String, Map<String, List<String>>> map = responseSolr.getHighlighting();
				
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
		return "board/print";
	}
}
