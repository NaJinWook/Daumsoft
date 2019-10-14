package com.daumsoft.test5.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daumsoft.test5.service.CaService;

@Controller
public class CaController {
	@Inject
	CaService caService;
	
	@RequestMapping(value="/main")
	public String main() {
		try {
//			caService.naverCrawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "visualize/main";
	}
	
	@RequestMapping(value="/search")
	public String search(HttpServletRequest request, Model model) {
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			
			String siteId = request.getParameter("siteId");
			String categoryId = request.getParameter("categoryId");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String oId = request.getParameter("oId");
			List<Map<String, String>> count = caService.countView(siteId, categoryId, startDate, endDate, oId);
			System.out.println("검색한 결과의 리스트 값 : " + count);
			for(Map<String, String> map : count) {
				
				String tempMap = map.values().toString();

				String tempDate = tempMap.substring(1, tempMap.indexOf(","));
				String tempCount = tempMap.substring(tempMap.indexOf(",") + 2, tempMap.length() - 1);

				jsonObject = new JSONObject();
				jsonObject.put("regDate", tempDate);
				jsonObject.put("collection", tempCount);
				jsonArray.add(jsonObject);
				
			}
			model.addAttribute("jsonArray", jsonArray);
			model.addAttribute("siteId", siteId);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("oId", oId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "visualize/print";
	}
}
