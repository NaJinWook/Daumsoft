package com.daumsoft.test5;

import java.io.IOException;
import java.text.ParseException;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

public class SeleniumTest {
	public static void main(String[] args) {
		SeleniumTest selTest = new SeleniumTest();
		selTest.naverCrawl();
//		selTest.test();
//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//        String strToAnalyze = "대한민국은 민주공화국이다.";
//
//        KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);
//
//        System.out.println(analyzeResultList.getPlainText());
//
//        List<Token> tokenList = analyzeResultList.getTokenList();
//        for (Token token : tokenList) {
//            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//            if(token.getPos().equals("NNP")) {
//            	System.out.println(token.getMorph());
//            }
//        }
//		selTest.daumCrawl();
	}
	
	// 크롤링 할 URL
	private String URL;
	
	private void naverCrawl() {
		String sid1 = "100"; // 100 = 정치속보, 101 = 경제속보, 103 = 사회속보
		String date = "20191010"; // 조회 날짜
		boolean loop = true;
		String endPage = null;
		int siteId = 1;
		try {
			for(int i=84; loop; i++) {
				URL = "https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1="+sid1+"&date="+date+"&page=" + i;
				System.out.println("=========================="+i+"페이지"+"==========================");
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
					String categoryId = detailURL.substring(sIdx+5, sIdx+8);
					int oIdx = detailURL.lastIndexOf("oid=");
					String oId = detailURL.substring(oIdx+4, oIdx+7);
					String press = detailDoc.select(".press_logo img").attr("title");
					String title = detailDoc.select("#articleTitle").text();
					String content = detailDoc.select("#articleBodyContents").text();
					
/********************************코모란 시작*****************************************/
				    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
				    String strToAnalyze = detailDoc.select("#articleBodyContents").text();
				    KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);
//				    System.out.println("전체 리스트 : " + analyzeResultList.getPlainText());
			        List<String> strList = analyzeResultList.getNouns();
//			        System.out.println("===================분석 결과======================");
//			        System.out.println("명사 리스트 : " + strList);
			        Map<String, Integer> map = new HashMap<String, Integer>();
			        for(String str : strList) {
			           if(map.containsKey(str)) {
			        	   map.put(str, map.get(str)+1);
			           } else {
			        	   map.put(str, 1);
			           }
			        }
//			        for(Map.Entry<String, Integer> entry : map.entrySet()) {
//			        	System.out.println("KEY : " + entry.getKey() +"\t\t"+ "VALUE : " + entry.getValue());
//			        }
			        
					// value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
					List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

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
//						System.out.println("KEY : " + entry.getKey() + "\t" + "VALUE : " + entry.getValue());
					}
					Gson gson = new Gson();
					String jsonData = gson.toJson(sortedMap); // map을 담으면 Json형태로 만들어준다.
//			        System.out.println("===================분석 종료======================");
/********************************코모란끝*****************************************/
					String regDate = detailDoc.select(".t11").text();
					SimpleDateFormat original_format = new SimpleDateFormat("yyyy.MM.dd. a h:mm");
					SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date original_date = original_format.parse(regDate);
					regDate = new_format.format(original_date);
					
					System.out.println("aId : " + aId);
					System.out.println("siteId = " + siteId);
					System.out.println("categoryId = " + categoryId);
					System.out.println("oId : " + oId);
					System.out.println("언론사 : " + press);
					System.out.println("제목 : " + title);
					System.out.println("내용 : " + content);
					System.out.println("analysisData : " + jsonData); //여기까지 json만들기 끝
					System.out.println("작성일 : " + regDate);
					System.out.println("URL : " + detailURL);
					System.out.println("===========================================================");
				}
			}
			System.out.println("종료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void daumCrawl() {
		URL = "https://news.daum.net/breakingnews/politics?page=1";
		try {
			Document doc = Jsoup.connect(URL).get();
			String category = doc.select("a[href='https://media.daum.net/politics/']").text();
			System.out.println("카테고리 : " + category);
			Elements urlList = doc.select(".box_etc > ul > li");
			for(Element elementURL : urlList) {
				String detailURL = elementURL.select("a").attr("href");
				String[] aidArray = detailURL.split("/");
				String aid = aidArray[4];
				Document detailDoc = Jsoup.connect(detailURL).get();
				String press = detailDoc.select(".info_cp img").attr("alt");
				String title = detailDoc.select(".tit_view").text();
				String content = detailDoc.select(".article_view").text();
				String info = detailDoc.select(".info_view").text();
				int idxOf = info.indexOf("입력 ");
				String regDate = info.substring(idxOf+3, idxOf+20);
				SimpleDateFormat original_format = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
				SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date original_date = original_format.parse(regDate);
				regDate = new_format.format(original_date);
				System.out.println("aid : " + aid);
				System.out.println("언론사 : " + press);
				System.out.println("제목 : " + title);
				System.out.println("내용 : " + content);
				System.out.println("URL : " + detailURL);
				System.out.println("등록일 : " + regDate);
				System.out.println("info : " + info);
				System.out.println("===========================================================");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
//	@SuppressWarnings("serial")
//	public void test() {
//		List<String> list = new ArrayList<String>();
//		String str = "{\"사과\":6,\"키위\":5,\"포도\":3,\"바나나\":1,\"메추리알\":1}";
//		String str2 = "{\"바나나\":1,\"사과\":1}";
//		String str3 = "{\"사과\":3,\"포도\":2,\"메론\":1,\"바나나\":1,\"거봉\":1}";
//		list.add(str);
//		list.add(str2);
//		list.add(str3);
//		Map<String, Integer> SumMap = new HashMap<>();
//		Map<String, Integer> listMap = null;
//		Gson gson = new Gson();
//		for(String hey : list) {
////			System.out.println(hey);
////			System.out.println("===================");
//			listMap = gson.fromJson(hey, new TypeToken<Map<String, Integer>>(){}.getType());
//			for(String hoy : listMap.keySet()) {
//				if(SumMap.containsKey(hoy)) {
//					SumMap.put(hoy, listMap.get(hoy) + SumMap.get(hoy));
//				} else {
//					SumMap.put(hoy, listMap.get(hoy));
//				}
//				
//			}
//		}
//		
//		for(String key : SumMap.keySet()) {
//			System.out.println("key : " + key);
//			if(SumMap.get(key)==1) {
//				System.out.println(SumMap.get(key));
//				SumMap.remove(key);
//			}
//		}
//		
//		Iterator<Entry<String, Integer>> iter = SumMap.entrySet().iterator();
//		while (iter.hasNext()) {
//			Entry<String, Integer> entry = iter.next();
//			String key = entry.getKey();
//			
//			if(entry.getValue()==1) {
////			if(SumMap.get(key)==1) {
//				System.out.println("key : " + key + ", value : " + SumMap.get(key));
//				iter.remove();
//			}
//		}

		
//		Iterator it = SumMap.keySet().iterator();
//		while(it.hasNext()) {
//			String key = it.next().toString();
//			System.out.println("key : " + key);
//			if(SumMap.get(key)==1) {
//				System.out.println(SumMap.get(key));
//				SumMap.remove(key);
//			}
//		}
//		System.out.println(SumMap);
	}
	
//	// WebDriver
//	private WebDriver driver;
//	private WebElement webElement;
//
//	// Properties
//	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
//	public static final String WEB_DRIVER_PATH = "C:\\Users\\Daumsoft\\Downloads\\chromedriver\\chromedriver.exe";
//
//	// 크롤링 할 URL
//	private String URL;
//
//	public void naverCrawl() {
//		// System Property SetUp
//		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
//		// Driver SetUp
//        ChromeOptions options = new ChromeOptions();
//        options.setCapability("ignoreProtectedModeSettings", true);
//        driver = new ChromeDriver(options);
//		URL = "https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=101";
//		try {
//			Document doc = Jsoup.connect(URL).get();
//			String href = URL.substring(22, URL.length());
//			String category = doc.select("a[href="+ href +"] > .tx").text();
//			System.out.println("카테고리 : " + category);
//			
//			for(int i=1; i<100; i++) {
//				// get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
//				driver.get(URL + "#&date=%2000:00:00&page="+i);
//				//System.out.println(driver.getPageSource());
//				webElement = driver.findElement(By.cssSelector(".section_body"));
//				List<WebElement> urlList = webElement.findElements(By.cssSelector("li dt > a"));
//				Set<String> urlSet = new LinkedHashSet<String>();
//				for(WebElement el : urlList) {
//					urlSet.add(el.getAttribute("href").toString());
//				}
//				List<String> list = new ArrayList<String>(urlSet);
//				for(int j=0; j<list.size(); j++) {
//					String detailURL = list.get(j);
//					Document detailDoc = Jsoup.connect(detailURL).get();
//					
//					int lastIdx = detailURL.lastIndexOf("aid=");
//					String aid = detailURL.substring(lastIdx+4, detailURL.length());
//					String press = detailDoc.select(".press_logo img").attr("title");
//					String title = detailDoc.select("#articleTitle").text();
//					String content = detailDoc.select("#articleBodyContents").text();;
//					String regDate = detailDoc.select(".t11").text();
//					SimpleDateFormat original_format = new SimpleDateFormat("yyyy.MM.dd. a h:mm");
//					SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//					Date original_date = original_format.parse(regDate);
//					regDate = new_format.format(original_date);
//					
//					System.out.println("aid : " + aid);
//					System.out.println("언론사 : " + press);
//					System.out.println("제목 : " + title);
//					System.out.println("내용 : " + content);
//					System.out.println("작성일 : " + regDate);
//					System.out.println("URL : " + detailURL);
//					System.out.println("===========================================================");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			driver.close();
//		}
//	}
//}
