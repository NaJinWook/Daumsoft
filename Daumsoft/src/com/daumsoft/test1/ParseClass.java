package com.daumsoft.test1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ParseClass {
	// DB -> TSV 파일 생성
	public void fileTSV(String[] columnName, List<String[]> allRowsData, String sort) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("C:\\Users\\Daumsoft\\Downloads\\"+sort+"_doc.tsv", false));
			bw.write(columnName[0]+"\t"+columnName[1]+"\t"+columnName[2]);
			bw.newLine();
			for(String[] str : allRowsData) {
				//System.out.println(str[0]+"\t"+str[1]+"\t"+str[2]);
				bw.write(str[0]+"\t"+str[1]+"\t"+str[2]);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) { try { bw.close(); } catch (IOException e) { e.printStackTrace(); } } 
		}
	}
	
	// DB -> Tagged type, Json type format하여 file 형태로 출력
	public void formatData(String[] columnName, List<String[]> allRowsData, String type) {
		BufferedWriter bw = null;
		try {
			if(type.equals("tag") || type.equals("TAG")) {
				type = "txt"; // 파일 저장 형식 txt로 하기 위해 값을 변경
				bw = new BufferedWriter(new FileWriter("C:\\Users\\Daumsoft\\Downloads\\Tconvert_doc."+type, false));
				for(String[] str : allRowsData) {
					bw.write("^[START]"+"\n"+"["+columnName[0]+"]"+"\n"+str[0]+"\n"+"["+columnName[1]+"]"+"\n"+str[1]+"\n"+"["+columnName[2]+"]"+"\n"+str[2]+"\n"+"^[END]");
					bw.newLine();
				}
				bw.flush();
				bw.close();
			} else if(type.equals("json") || type.equals("JSON")) {
				JSONObject jsonObject = new JSONObject(); // 최종 완성될 JSONObject 선언(전체)
				JSONArray jsonArray = new JSONArray(); // JSON 정보를 담을 Array 선언
				for(String[] str : allRowsData) {
					JSONObject data = new JSONObject(); // 하나의 정보가 들어갈 JSONObject 선언
					// 데이터 입력
					data.put(columnName[0], str[0]);
					data.put(columnName[1], str[1]);
					data.put(columnName[2], str[2]);
					jsonArray.add(data); // 데이터 Array에 입력
				}
				jsonObject.put("data", jsonArray); // JSONObject에 data란 name으로 JSON 정보로 구성된 Array의 value 입력 
				bw = new BufferedWriter(new FileWriter("C:\\Users\\Daumsoft\\Downloads\\Jconvert_doc."+type, false));
				bw.write(jsonObject.toJSONString()); // JSONObject String 형 변환
				bw.flush(); // 버퍼에 저장된 내용을 전송하고 비움
				bw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) { try { bw.close(); } catch (IOException e) { e.printStackTrace(); } } 
		}
	}
}
