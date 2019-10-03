package com.daumsoft.test4;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test2 {
	private static String URL = "https://www.insight.co.kr/section/weird-news?";

	public static String page(int num) {
		return "page=" + num;
	}

	public static void main(String[] args) throws IOException {
		String writer = null;
		String regDate = null;
		String str = null;
		int textLength = 0;
		
		for (int i = 1; i < 10; i++) {
			// URL로 부터 Document를 가져온다.
			Document doc = Jsoup.connect(URL + page(i)).get();

			// 목록 가져오기
			// System.out.println(doc.toString());

			Elements subject_elements = doc.select(".section-gnb");
			String subject = subject_elements.text();

			System.out.println("=================================================");
			System.out.println("주제 : " + subject + " " + i +"페이지 입니다.");
			System.out.println("=================================================");

			Elements list_elements = doc.select(".section-list");

//		for(Element element : elements2.select("li")) {
//			System.out.println(element.text());
//		}

			for (Element element : list_elements.select("li")) {
//				System.out.println("제목 : " + element.select(".section-list-article-title").text());
//				System.out.println("내용 : " + element.select(".section-list-article-summary").text());
//				System.out.println("작성 정보 : " + element.select(".section-list-article-byline").text());
				str = element.select(".section-list-article-byline").text();
				textLength = (element.select(".section-list-article-byline").text()).length();
//				writer = str.substring(0, textLength - 22);
				regDate = str.substring(textLength - 19, textLength);
//				System.out.println("작성자 : " + writer);
				String detailURL = element.select("a").attr("href");
//				System.out.println("세부 URL : " + detailURL);

				// URL을 통해 세부 정보 가져오기
				Document detail_doc = Jsoup.connect(detailURL).get();
				// System.out.println(detail_doc.toString());
				Elements content_elements = detail_doc.select(".content");
				for (Element element2 : content_elements) {
					if ((element2.select(".news-byline-date").text()).indexOf("2019.09.27") != -1) {
						System.out.println("세부 제목 : " + element2.select(".news-header h1").text());
						System.out.println("세부 작성자 : " + element2.select(".news-byline-writer").text());
						//System.out.println("세부 날짜 : " + element2.select(".news-byline-date").text());
						System.out.println("작성일 : " + regDate);
						System.out.println("세부 메일 : " + element2.select(".news-byline-mail").text());
						str = element2.select(".news-byline-date").text();
//						regDate = str.substring(5, 21);
//						System.out.println("세부 날짜 substring : " + regDate);
						System.out.println("세부 내용 : " + element2.select(".news-article-memo > p").text());
					}
				}
				System.out.println("=================================================");
			}
		}
	}
}
