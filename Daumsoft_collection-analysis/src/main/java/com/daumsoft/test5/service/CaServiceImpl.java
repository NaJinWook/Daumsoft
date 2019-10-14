package com.daumsoft.test5.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.daumsoft.test5.model.dao.CaDAO;
import com.daumsoft.test5.model.dto.CaDTO;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

@Service
public class CaServiceImpl implements CaService {
	@Inject
	CaDAO caDao;

	// 크롤링 할 URL
	private String URL;
	
	@SuppressWarnings("null")
	@Override
	public void naverCrawl() throws Exception {
		String sid1 = "102"; // 100 = 정치속보, 101 = 경제속보, 102 = 사회속보
		String date = "20191010"; // 조회 날짜
		boolean loop = true; // 페이지 이동
		String endPage = null; // 하단 선택 된 페이지 번호
		int siteId = 1;
		CaDTO dto = null;
		List<CaDTO> dtoList = null;
		HashMap<String, Object> dtoMap = null;
		try {
			for(int i=1; loop; i++) {
				dtoList = new ArrayList<CaDTO>();
				URL = "https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1="+sid1+"&date="+date+"&page=" + i;
				System.out.println("===========================================================");
				Document doc = Jsoup.connect(URL).get();
				endPage = doc.select(".paging strong").text(); // 하단 현재 페이지
				if(i != Integer.parseInt(endPage)) { // 파라미터 페이지 수와 하단의 페이지가 다르면 반복문 탈출
					break;
				}
				String category = doc.select("div[class='list_header newsflash_header2'] > h3").text();
				System.out.println("카테고리 : " + category);
				Elements header = doc.select("div[class='list_body newsflash_body'] ul > li dt > a");
				Set<String> urlSet = new LinkedHashSet<String>();
				
				for(Element el : header) {
					urlSet.add(el.attr("href").toString());
				}
				List<String> urlList = new ArrayList<String>(urlSet);
				for(int j=0; j<urlList.size(); j++) {
					String detailURL = urlList.get(j);
					Document detailDoc = Jsoup.connect(detailURL).get();
					int aIdx = detailURL.lastIndexOf("aid=");
					String aId = detailURL.substring(aIdx+4, detailURL.length());
					int sIdx = detailURL.indexOf("sid1=");
					String sId = detailURL.substring(sIdx+5, sIdx+8);
					int oIdx = detailURL.lastIndexOf("oid=");
					String oId = detailURL.substring(oIdx+4, oIdx+7);
					String oName = detailDoc.select(".press_logo img, #pressLogo img, .press_logo img").attr("alt");
					if(!caDao.selectOid().contains(oId)) {
						dto = new CaDTO(oId, oName);
						caDao.insertOid(dto);
					}
					String title = detailDoc.select("#articleTitle, h4.title, .end_tit").text();
					String content = detailDoc.select("#articleBodyContents, #newsEndContents, #articeBody").text();
					String regDate = detailDoc.select(".t11, .info > span, .article_info em").text();
					System.out.println(regDate);
					if(regDate.contains("입력")) {
						regDate = regDate.substring(regDate.indexOf("입력")+3, regDate.indexOf("입력")+23);
					}
					SimpleDateFormat original_format = new SimpleDateFormat("yyyy.MM.dd. a h:mm");
					SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date original_date = original_format.parse(regDate);
					regDate = new_format.format(original_date);
					
					// 아래부터 코모란으로 키워드 추출
					Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
				    KomoranResult analyzeResultList = komoran.analyze(content);
				    System.out.println("전체 리스트 : " + analyzeResultList.getPlainText());
			        List<String> strList = analyzeResultList.getNouns();
			        System.out.println("=========================분석 결과============================");
			        System.out.println("명사 리스트 : " + strList);
			        Map<String, Integer> countMap = new HashMap<String, Integer>();
			        for(String str : strList) {
			           if(countMap.containsKey(str)) {
			        	   countMap.put(str, countMap.get(str)+1);
			           } else {
			        	   countMap.put(str, 1);
			           }
			        }
			        
			        // value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
					List<Map.Entry<String, Integer>> list = new LinkedList<>(countMap.entrySet());
					Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
						@Override
						public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
							int comparision = (o1.getValue() - o2.getValue()) * -1;
							return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
						}
					});

					// 순서유지를 위해 LinkedHashMap을 사용
					Map<String, Integer> sortedMap = new LinkedHashMap<>();
					for (Iterator<Map.Entry<String, Integer>> iter = list.iterator(); iter.hasNext();) {
						Map.Entry<String, Integer> entry = iter.next();
						sortedMap.put(entry.getKey(), entry.getValue());
						//System.out.println("KEY : " + entry.getKey() + "\t" + "VALUE : " + entry.getValue());
					}
					Gson gson = new Gson();
					String analysisData = gson.toJson(sortedMap); // map을 담으면 Json형태로 만들어준다.
					
					System.out.println("=========================분석 종료============================");
					System.out.println("aid : " + aId);
					System.out.println("siteId = " + siteId);
					System.out.println("sId = " + sId); // sId는 URL에서 subString 한것
					System.out.println("언론사 : " + oName);
					System.out.println("oId : " + oId);
					System.out.println("제목 : " + title);
					System.out.println("내용 : " + content);
					System.out.println("analysisData : " + analysisData); //여기까지 json만들기 끝
					System.out.println("작성일 : " + regDate);
					System.out.println("URL : " + detailURL);
					System.out.println("===========================================================");
					dto = new CaDTO(aId, siteId, Integer.parseInt(sid1), oId, title, content, analysisData, detailURL, regDate);
					if(caDao.countData(dto) == 0) { // 중복 URL일 경우 리스트에 추가하지 않기 위해
						dtoList.add(dto);
					}
				}
				dtoMap = new HashMap<String, Object>();
				dtoMap.put("dtoList", dtoList);
				caDao.insertData(dtoMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	@Override
	public void dataView(String siteId, String categoryId, String startDate, String endDate) throws Exception {
		try {
			Map<String, Integer> sumMap = new HashMap<String, Integer>();
			Map<String, Integer> listMap = null;
			Gson gson = new Gson();
			
			List<String> dataList = new ArrayList<String>();
			dataList = caDao.selectData(siteId, categoryId, startDate, endDate); // 기간 빈도수 데이터 가져옴
			for(String data : dataList) {
				listMap = gson.fromJson(data, new TypeToken<Map<String, Integer>>(){}.getType());
				for(String key : listMap.keySet()) {
					if(sumMap.containsKey(key)) {
						sumMap.put(key, listMap.get(key) + sumMap.get(key));
					} else {
						sumMap.put(key, listMap.get(key));
					}
				}
			}
			
			Iterator<Entry<String, Integer>> iter = sumMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Integer> entry = iter.next();
				String key = entry.getKey();
				
				if(entry.getValue() == 1) { // if(SumMap.get(key)==1) 대체 가능
					System.out.println("key : " + key + ", value : " + sumMap.get(key));
					iter.remove();
				}
			}
			
			// value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
			List<Map.Entry<String, Integer>> list = new LinkedList<>(sumMap.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				@Override
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					int comparision = (o1.getValue() - o2.getValue()) * -1;
					return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
				}
			});

			// 순서유지를 위해 LinkedHashMap을 사용
			Map<String, Integer> sortedMap = new LinkedHashMap<>();
			for (Iterator<Map.Entry<String, Integer>> iter2 = list.iterator(); iter2.hasNext();) {
				Map.Entry<String, Integer> entry = iter2.next();
				sortedMap.put(entry.getKey(), entry.getValue());
				//System.out.println("KEY : " + entry.getKey() + "\t" + "VALUE : " + entry.getValue());
			}
			String analysisData = gson.toJson(sortedMap); // map을 담으면 Json형태로 만들어준다.
			System.out.println(analysisData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, String>> countView(String siteId, String categoryId, String startDate, String endDate, String oId) throws Exception {
		System.out.println("siteId : " + siteId);
		System.out.println("categoryId : " + categoryId);
		System.out.println("startDate : " + startDate);
		System.out.println("endDate : " + endDate);
		System.out.println("oId : " + oId);
		return caDao.selectCount(siteId, categoryId, startDate, endDate, oId);
	}

}
