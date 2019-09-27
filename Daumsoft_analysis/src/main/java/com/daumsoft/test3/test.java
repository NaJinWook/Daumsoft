package com.daumsoft.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class test {
	public static void main(String[] args) {
		//String url = "http://qt.some.co.kr/TrendMap/JSON/ServiceHandler?lang=ko&source=twitter&startDate=20190101&endDate=20190924&keyword=%EC%95%84%EC%9D%B4%ED%8F%B0&topN=100&cutOffFrequencyMin=0&cutOffFrequencyMax=0&period=2&invertRowCol=on&start_weekday=SUNDAY&categorySetName=SM&command=GetAssociationTransitionBySentiment";
		String url = "http://qt.some.co.kr/TrendMap/JSON/ServiceHandler?lang=ko&source=twitter&startDate=20190101&endDate=20190924&keyword=%EC%95%84%EC%9D%B4%ED%8F%B0&topN=100&cutOffFrequencyMin=0&cutOffFrequencyMax=0&period=2&invertRowCol=on&start_weekday=SUNDAY&categorySetName=SM&command=GetAssociationTransitionBySentiment";
		InputStream is = null;
		try {
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
		    for(int i=0; i<jsonArray.size(); i++) { // JSONArray 사이즈 만큼 반복
		    	JSONObject dataObject = (JSONObject) jsonArray.get(i);
		    	//System.out.println("3번째 jsonObject = "+ "("+i+")+"+ dataObject); // 각각의 데이터를 꺼내오기 위함
		    	System.out.println((i+1)+"번째 날짜 : "+dataObject.get("date"));
		    	System.out.println((i+1)+"번째 데이터 : "+dataObject.get("아이폰"));
		    	System.out.println("----------------------------");
		    	JSONObject data = (JSONObject) dataObject.get("아이폰");
		    	System.out.println("과연"+data.get("negative"));
		    }
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			try {is.close();} catch (IOException e) {e.printStackTrace();}
		}
	}
}
