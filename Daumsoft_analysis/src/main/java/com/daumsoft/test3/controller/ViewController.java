package com.daumsoft.test3.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daumsoft.test3.model.dto.ViewDTO;
import com.daumsoft.test3.service.ViewService;

@Controller
@RequestMapping("/*")
public class ViewController {
	@Inject
	ViewService viewService;
	
	@RequestMapping("main")
	public String main() throws Exception {
		viewService.delete();
		return "visualize/main";
	}
	
	@RequestMapping(value="view", method = RequestMethod.GET)
	public String view(HttpServletRequest request, Model model) throws Exception {
	    String sns_type = ""; // 소셜미디어 유형
	    int positive_count = 0; // 긍정 빈도수
	    int negative_count = 0; // 부정 빈도수
	    int neutral_count = 0; // 중립 빈도수
	    String register_date = ""; // 등록일시
	    
	    ViewDTO dto = null;
	    
	    String source = request.getParameter("source");
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    String keyword = request.getParameter("keyword");
		String period = request.getParameter("period");
		String url = "http://qt.some.co.kr/TrendMap/JSON/ServiceHandler?lang=ko&source="+source+"&startDate="+startDate+"&endDate="+endDate+"&keyword="+keyword+"&topN=100&cutOffFrequencyMin=0&cutOffFrequencyMax=0&period="+period+"&invertRowCol=on&start_weekday=SUNDAY&categorySetName=SM&command=GetAssociationTransitionBySentiment";
		InputStream is = null;
		String jsonStr = null;
		int positive_sum = 0;
		int negative_sum = 0;
		int neutral_sum = 0;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			viewService.delete(); // 초기화
			is = new URL(url).openStream(); // URL객체로의 스트림 열기
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // 받는 스트림을 버퍼에 저장
			String str;
		    StringBuffer buffer = new StringBuffer(); // 문자열 연산이나 추가시 객체 공간을 유연하게 동작하기 위함
		    while ((str = br.readLine()) != null) {
		        buffer.append(str);
		    }
		    String b_str = buffer.toString();
		    //System.out.println("buffer에 저장된 데이터 : " + b_str);
		    JSONParser jsonParser = new JSONParser();
		    JSONObject jsonObject = (JSONObject) jsonParser.parse(b_str); // jSONObject 형태로 꺼내와야 하기 때문에 형 변형
		    //System.out.println("1번째 jsonObejct = " + jsonObject);
		    JSONArray jsonArray = (JSONArray) jsonObject.get("rows"); // key값이 rows인 데이터들을 JSONArray에 담는다
		    //System.out.println("2번째 jsonArray = " + jsonArray);
		    System.out.println("검색 기준 : " + source + " / 검색어 : " + keyword);
		    
		    if(source.equals("blog")) {
	    		sns_type="B";
	    	} else if(source.equals("twitter")) {
	    		sns_type="T";
	    	} else if(source.equals("news")) {
	    		sns_type="N";
	    	} else if(source.equals("facebook")) {
	    		sns_type="F";
	    	} else if(source.equals("insta")) {
	    		sns_type="I";
	    	}
		    
		    for(int i=0; i<jsonArray.size(); i++) { // JSONArray 사이즈 만큼 반복
		    	dto = new ViewDTO();
		    	JSONObject dataObject = (JSONObject) jsonArray.get(i);
		    	//System.out.println("3번째 jsonObject = "+ "("+i+")+"+ dataObject); // 각각의 데이터를 꺼내오기 위함
		    	//System.out.println((i+1)+"번째 날짜 : "+dataObject.get("date"));
		    	//System.out.println((i+1)+"번째 데이터 : "+dataObject.get(keyword));
		    	//System.out.println("----------------------------");
		    	JSONObject data = (JSONObject) dataObject.get(keyword);
		    	positive_count = Integer.parseInt(data.get("positive").toString());
		    	negative_count = Integer.parseInt(data.get("negative").toString());
		    	neutral_count = Integer.parseInt(data.get("neutral").toString());
		    	register_date = dataObject.get("date").toString();
		    	//System.out.println((i+1)+"월 데이터)"+"긍정 : "+positive_count+", 부정 : "+negative_count+", 중립 : "+neutral_count);
		    	dto.setSns_type(sns_type);
		    	dto.setPositive_count(positive_count);
		    	dto.setNegative_count(negative_count);
		    	dto.setNeutral_count(neutral_count);
		    	dto.setRegister_date(register_date);
		    	//list.add(dto);
		    	viewService.insert(dto);
		    	//model.addAttribute("list", list);
		    }
		    returnMap = viewService.select();
		    Date s_date = new SimpleDateFormat("yyyyMMdd").parse(startDate);
		    Date e_date = new SimpleDateFormat("yyyyMMdd").parse(endDate);
		    String fm_startDate = new SimpleDateFormat("yyyy년 MM월 dd일").format(s_date);
		    String fm_endDate = new SimpleDateFormat("yyyy년 MM월 dd일").format(e_date);
		    model.addAttribute("source", source);
		    model.addAttribute("startDate", fm_startDate);
		    model.addAttribute("endDate", fm_endDate);
		    model.addAttribute("keyword", keyword);
		    model.addAttribute("jsonArray",returnMap.get("jsonArray"));
		    model.addAttribute("positive_sum", returnMap.get("positive_sum"));
		    model.addAttribute("negative_sum", returnMap.get("negative_sum"));
		    model.addAttribute("neutral_sum", returnMap.get("neutral_sum"));
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "visualize/print";
	}
}
