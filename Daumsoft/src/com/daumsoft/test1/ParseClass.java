package com.daumsoft.test1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ParseClass {
	// Tagged Format File(txt) 파일 생성 후 doc2 Table에 데이터 입력하기 위해 파일명을 동일하게 지정
	public String tagFileName() {
		String tagfile = "TConvert_doc";
		return tagfile;
	}
	// DB -> TSV 파일 생성
	// 매개변수로 컬럼명, 데이터, 정렬값을 받아온다
	public void fileTSV(String[] columnName, List<String[]> allRowsData, String sort) {
		long start = System.currentTimeMillis(); // 프로그램 시작 시간
		BufferedWriter bw = null;
		if(sort == "asc" || sort == "ASC") {
			System.out.println("<><><><><><><><><><오름차순으로 파일을 저장합니다!><><><><><><><><><>");
		} else if(sort == "desc" || sort == "DESC") {
			System.out.println("<><><><><><><><><><내림차순으로 파일을 저장합니다!><><><><><><><><><>");
		}
		try {
			bw = new BufferedWriter(new FileWriter("C:\\Users\\Daumsoft\\Downloads\\"+sort+"_doc.tsv", false));
			bw.write(columnName[0]+"\t"+columnName[1]+"\t"+columnName[2]); // 컬럼명을 상단에 넣기 위함
			bw.newLine();
			for(String[] str : allRowsData) { // 리스트에 담긴 데이터를 String 배열로 꺼내오기 위함
				//System.out.println(str[0]+"\t"+str[1]+"\t"+str[2]);
				bw.write(str[0]+"\t"+str[1]+"\t"+str[2]); // 반복문을 통해 하나씩 데이터를 입력
				bw.newLine(); // 개행
			}
			bw.flush(); // 버퍼의 내용을 사용자가 원할 때 출력하여 비움
			bw.close(); // auto-flush
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) { try { bw.close(); } catch (IOException e) { e.printStackTrace(); } } 
		}
		long end = System.currentTimeMillis(); // 프로그램 종료 시간
		System.out.println("===========================================================");
		System.out.println("2-1) DB to File(tsv)");
		System.out.println("실행 시간 : " + ( end - start) / 1000.0 + "초");
	}
	
	// DB -> Tagged type, Json type format하여 file 형태로 출력
	public void formatData(String[] columnName, List<String[]> allRowsData, String tagFileName) {
		long start = System.currentTimeMillis(); // 프로그램 시작 시간
		BufferedWriter bw = null;
		try {
				bw = new BufferedWriter(new FileWriter("C:\\Users\\Daumsoft\\Downloads\\"+tagFileName+".txt", false));
				for(String[] str : allRowsData) { // 리스트에 담긴 데이터를 String 배열로 꺼내오기 위함
					bw.write("^[START]"+"\n"+"["+columnName[0]+"]"+"\n"+str[0]+"\n"
											+"["+columnName[1]+"]"+"\n"+str[1]+"\n"
											+"["+columnName[2]+"]"+"\n"+str[2]+"\n"
							+"^[END]");
					bw.newLine(); // 개행
				}
				bw.flush(); // 버퍼의 내용을 사용자가 원할 때 출력하여 비움
				bw.close(); // auto-flush
				
				JSONObject jsonObject = new JSONObject(); // 최종 완성될 JSONObject 선언(전체)
				JSONArray jsonArray = new JSONArray(); // JSON 정보를 담을 Array 선언
				for(String[] str : allRowsData) { // 리스트에 담긴 데이터를 String 배열로 꺼내오기 위함
					JSONObject data = new JSONObject(); // 하나의 정보가 들어갈 JSONObject 선언
					// 데이터 입력
					data.put(columnName[0], str[0]);
					data.put(columnName[1], str[1]);
					data.put(columnName[2], str[2]);
					jsonArray.add(data); // 데이터 Array에 입력
				}
				jsonObject.put("data", jsonArray); // JSONObject에 data란 name으로 JSON 정보로 구성된 Array의 value 입력 
				bw = new BufferedWriter(new FileWriter("C:\\Users\\Daumsoft\\Downloads\\JConvert_doc.json", false));
				bw.write(jsonObject.toJSONString()); // JSONObject String 형 변환
				bw.flush(); // 버퍼에 저장된 내용을 전송하고 비움
				bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) { try { bw.close(); } catch (IOException e) { e.printStackTrace(); } } 
		}
		long end = System.currentTimeMillis(); // 프로그램 종료 시간
		System.out.println("===========================================================");
		System.out.println("2-2, 2-3) DB to File(Tagged type, json type)");
		System.out.println("실행 시간 : " + ( end - start) / 1000.0 + "초");
		System.out.println("===========================================================");
	}
}
