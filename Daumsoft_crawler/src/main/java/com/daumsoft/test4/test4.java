package com.daumsoft.test4;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test4 {

	private static String URL = "https://www.huffingtonpost.kr/news/tv/";

	public static void crawling() {
		try {
			Document list_doc = Jsoup.connect(URL + "2").get(); // URL로 부터 Document를 가져옴
			Elements subject_elements = list_doc.select(".master-container .section-name");
			String subject = subject_elements.text();
			Elements list_elements = list_doc.select("div[class='col col--body-center bnp__card-list yr-col-body-center'] .card__headline");
			for(Element list_data : list_elements) {
				String detailURL = "https://www.huffingtonpost.kr" + list_data.select("a").attr("href");
				
				// 고유 idx를 가져오기 위함
	            String[]URLArray = detailURL.split("/");
	            String tempIdx = URLArray[4];
	            String[] URLArray2 = tempIdx.split("\\?");
	            String idx = URLArray2[0];
	            
				Document detail_doc = Jsoup.connect(detailURL).get();
				Elements detail_data = detail_doc.select(".page__content");
				
				String regDate = detail_data.select(".timestamp").text();
				if (!regDate.equals("")) {
					regDate = regDate.substring(0, 21);
					SimpleDateFormat original_format = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
					SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date original_date = original_format.parse(regDate);
					regDate = new_format.format(original_date);
					System.out.println("고유번호 : " + idx);
					System.out.println("카테고리 : " + subject);
					System.out.println("제목 : " + detail_data.select(".headline__title").text());
					System.out.println("내용 : " + detail_data.select("div[class='content-list-component text']").text());
					System.out.println("URL : " + detailURL);
					System.out.println("작성일 : " + regDate);
					System.out.println("============================================");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		crawling();
	}
}
