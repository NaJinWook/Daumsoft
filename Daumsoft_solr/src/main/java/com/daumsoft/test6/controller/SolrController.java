package com.daumsoft.test6.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daumsoft.test6.SolrJDriver;

@Controller
@RequestMapping("/*")
public class SolrController {
	@RequestMapping("main")
	public String main() {
		return "board/main";
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search(HttpServletRequest request, Model model,
			@RequestParam(value = "sort", required = false, defaultValue = "") String sort) {
		String keyword = request.getParameter("keyword");
		try {
			if(!"".equals(keyword)) {
				SolrQuery query = new SolrQuery();
				query.setQuery(keyword);
				if(sort.equals("desc")) {
					query.setSort("regDate", ORDER.desc);
				} else if(sort.equals("asc")) {
					query.setSort("regDate", ORDER.asc);
				}
				QueryResponse responseSolr;
				responseSolr = SolrJDriver.solr.query(".", query);
				SolrDocumentList results = responseSolr.getResults();

				model.addAttribute("keyword", keyword);
				model.addAttribute("dataList", results.toArray());
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return "board/print";
	}
}
