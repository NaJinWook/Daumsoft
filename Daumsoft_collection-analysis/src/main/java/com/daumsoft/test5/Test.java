package com.daumsoft.test5;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {
	public void main(String[] args) {
		String URL = "https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=101";
		String href = URL.substring(22, URL.length());
		try {
			Document body = Jsoup.connect(URL + "#&date=%2000:00:00&page=1")
					.header("origin", "https://news.naver.com") // same-origin-polycy 로 인한 설정
			        .header("referer", "https://news.naver.com") // same-origin-polycy 로 인한 설정
			        .ignoreContentType(true) // json 받아오려면 타입무시를 해야하는듯?
			        .get();
			String category = body.select("a[href="+ href +"] > .tx").text();
			System.out.println("카테고리 : " + category);
			String json = body.select("body").text();
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
