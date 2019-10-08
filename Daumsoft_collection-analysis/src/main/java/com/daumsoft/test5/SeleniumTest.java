package com.daumsoft.test5;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {
	public static void main(String[] args) {
		SeleniumTest selTest = new SeleniumTest();
//		selTest.naverCrawl();
		selTest.daumCrawl();
	}

	// WebDriver
	private WebDriver driver;
	private WebElement webElement;

	// Properties
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\Users\\Daumsoft\\Downloads\\chromedriver\\chromedriver.exe";

	// 크롤링 할 URL
	private String URL;

	public void naverCrawl() {
		// System Property SetUp
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// Driver SetUp
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(options);
		URL = "https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=101";
		try {
			Document doc = Jsoup.connect(URL).get();
			String href = URL.substring(22, URL.length());
			String category = doc.select("a[href="+ href +"] > .tx").text();
			System.out.println("카테고리 : " + category);
			
			// get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
			driver.get(URL + "#&date=%2000:00:00&page=1");
			//System.out.println(driver.getPageSource());
			webElement = driver.findElement(By.cssSelector(".section_body"));
			List<WebElement> urlList = webElement.findElements(By.cssSelector("li dt[class='photo'] > a"));
			for(int i=0; i<urlList.size(); i++) {
				String detailURL = urlList.get(i).getAttribute("href");
				Document detailDoc = Jsoup.connect(detailURL).get();
				
				int lastIdx = detailURL.lastIndexOf("aid=");
				String aid = detailURL.substring(lastIdx+4, detailURL.length());
				String press = detailDoc.select(".press_logo img").attr("title");
				String title = detailDoc.select("#articleTitle").text();
				String content = detailDoc.select("#articleBodyContents").text();;
				String regDate = detailDoc.select(".t11").text();
				if(regDate.length() >= 20) {
					regDate = regDate.substring(20, regDate.length());
				}
				SimpleDateFormat original_format = new SimpleDateFormat("yyyy.mm.dd. a h:mm");
				SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date original_date = original_format.parse(regDate);
				regDate = new_format.format(original_date);
				
				System.out.println("aid : " + aid);
				System.out.println("언론사 : " + press);
				System.out.println("제목 : " + title);
				System.out.println("내용 : " + content);
				System.out.println("작성일 : " + regDate);
				System.out.println("URL : " + detailURL);
				System.out.println("===========================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
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
				int idxOf = info.indexOf(" 댓글");
				String regDate = info.substring(idxOf-18, idxOf);
				SimpleDateFormat original_format = new SimpleDateFormat("yyyy.mm.dd. HH:mm");
				SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date original_date = original_format.parse(regDate);
				regDate = new_format.format(original_date);
				System.out.println("aid : " + aid);
				System.out.println("언론사 : " + press);
				System.out.println("제목 : " + title);
				System.out.println("내용 : " + content);
				System.out.println("URL : " + detailURL);
				System.out.println("등록일 : " + regDate);
				System.out.println("===========================================================");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
