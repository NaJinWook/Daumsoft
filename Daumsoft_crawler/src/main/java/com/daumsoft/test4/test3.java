package com.daumsoft.test4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test3 {
	
	private static DBConnection dbConnect = new DBConnection(); // DB 연결 객체 생성
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	
	private static String URL = "https://www.insight.co.kr/section/life-style?";
	
	public static String page(int num) {
		return "page=" + num;
	}
	
	public static void crawling() {
		String info = null; // 전체 목록 페이지에서 작성자 · 날짜 변수
		int info_length = 0; // 전체 목록 페이지에서 작성자 · 날짜 변수 길이 구해서 substring 하기 위해
		String regDate = null; // 작성일
		String detailURL = null; // 글에 대한 세부 내용을 뽑아오기 위한 URL
		String[] detailURL_split = null; // 글에 대한 세부 내용을 뽑아오기 위한 URL에서 고유 번호를 가져오기 위해
		String idx = null; // 고유 번호
		String today = null; // 현재 날짜
		String before = null; // 일주일 전
		
		try {
			conn = dbConnect.conn; // DB 접속
			if(conn != null) {
				pstmt = conn.prepareStatement("insert into crawler (idx, type, category, title, content, writer, email, url, regDate) values (?,?,?,?,?,?,?,?,?)");
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select regDate from crawler where idx = 1");
				for(int i=1; i<100; i++) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					SimpleDateFormat fm = new SimpleDateFormat ( "yyyy-MM-dd");
					today = fm.format(cal.getTime()); // 현재 날짜
					cal.add(Calendar.DATE, -7);
					before = fm.format(cal.getTime()); // 일주일 전
					
					Document list_doc = Jsoup.connect(URL + page(i)).get(); // URL로 부터 Document를 가져옴
					
					Elements subject_elements = list_doc.select(".nav .nav-ul > li > a[title='생활일반']");
					String subject = subject_elements.text();
					System.out.println("주제어 : " + subject);
					
					Elements list_elements = list_doc.select(".section-list li");
					for(Element list_data : list_elements) {
						// 전체 목록 페이지에서 작성자 · 날짜 출력
						//System.out.println(list_data.select(".section-list-article-byline").text());
						info = list_data.select(".section-list-article-byline").text();
						info_length = info.length();
						regDate = info.substring(info_length - 19, info_length);
						detailURL = list_data.select("a").attr("href");
						detailURL_split = detailURL.split("/");
						idx = detailURL_split[4];
						
						// 세부 URL
						//System.out.println(detailURL);
						Document detail_doc = Jsoup.connect(detailURL).get();
						Elements content_elements = detail_doc.select(".content");
						
						if (regDate.indexOf(before) != -1) {
							System.out.println("프로그램 종료");
							return;
						}
						
						for(Element detail_data : content_elements) {
							System.out.println("고유번호 : " + idx);
							System.out.println("카테고리 : " + subject);
							System.out.println("제목 : " + detail_data.select(".news-header h1").text());
							System.out.println("내용 : " + detail_data.select(".news-article-memo > p").text());
							System.out.println("작성자 : " + detail_data.select(".news-byline-writer").text());
							System.out.println("메일 : " + detail_data.select(".news-byline-mail").text());
							System.out.println("URL : " + detailURL);
							System.out.println("작성일 : " + regDate);
							System.out.println("======================================================");
//							pstmt.setString(1, idx);
//							pstmt.setInt(2, 1);
//							pstmt.setString(3, subject);
//							pstmt.setString(4, detail_data.select(".news-header h1").text());
//							pstmt.setString(5, detail_data.select(".news-article-memo > p").text());
//							pstmt.setString(6, detail_data.select(".news-byline-writer").text());
//							pstmt.setString(7, detail_data.select(".news-byline-mail").text());
//							pstmt.setString(8, detailURL);							
//							pstmt.setString(9, regDate);
//							pstmt.executeUpdate();
//							conn.commit();
						}
					}
				}
			}
			pstmt.close();
			conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			System.out.println("데이터베이스 관련 예외 발생!");
			e.printStackTrace();
		} finally {
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}
	
	public static void main(String[] args) {
		crawling();
	}
}
