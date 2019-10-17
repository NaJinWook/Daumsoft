package com.daumsoft.test6.controller;

import java.io.IOException;
import java.util.HashMap;
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
	
	@RequestMapping("main")
	public String main() {
		return "board/main";
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search(HttpServletRequest request, Model model,
			@RequestParam(value = "sort", required = false, defaultValue = "") String sort,
			@RequestParam(value = "oName", required = false, defaultValue = "") String oName,
			@RequestParam(value = "start", required = false, defaultValue = "0") int start,
			@RequestParam(value = "rows", required = false, defaultValue = "10") int rows,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		String keyword = request.getParameter("keyword"); // 키워드 파라미터로 받음
		SolrQuery query;
		QueryResponse responseSolr;
		SolrDocumentList results;
		int totalRecordsCount;
		PagerDTO pager = null; // 페이징 처리를 위함
		
		try {
			if(!"".equals(keyword)) {
				query = new SolrQuery(); // 쿼리 객체 생성
				query.setQuery(keyword);
				if(sort.equals("desc")) {
					query.setSort("regDate", ORDER.desc);
				} else if(sort.equals("asc")) {
					query.setSort("regDate", ORDER.asc);
				}
				if(!oName.equals("")) {
					query.addFilterQuery("oName : " + oName);
				}
				query.setStart(start);
				query.setRows(rows);
				
				responseSolr = SolrJDriver.solr.query(".", query);
				results = responseSolr.getResults();
				totalRecordsCount = (int)results.getNumFound(); // 전체 게시글 수
				
				pager = new PagerDTO(rows, PAGEPERGROUP, currentPage, totalRecordsCount); // 페이징 처리 객체 생성
				start = (int)pager.getStartRecord();
				System.out.println("start값입니다 : " + start);
				
				if(start < 0) {
					start = 0;
				}
				query.setStart(start);
				responseSolr = SolrJDriver.solr.query(".", query);
				results = responseSolr.getResults();
				
				model.addAttribute("pager", pager);
				model.addAttribute("totalRecordsCount", totalRecordsCount);
				model.addAttribute("keyword", keyword);
				model.addAttribute("sort", sort);
				model.addAttribute("oName", oName);
				model.addAttribute("rows", rows);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("dataList", results.toArray());
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return "board/print";
	}
}
