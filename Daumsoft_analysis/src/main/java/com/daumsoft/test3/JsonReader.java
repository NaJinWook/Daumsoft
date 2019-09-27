package com.daumsoft.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public void main(String[] args) throws IOException, JSONException {
		JSONObject json = readJsonFromUrl("http://qt.some.co.kr/TrendMap/JSON/ServiceHandler?lang=ko&source=twitter&startDate=20190101&endDate=20190924&keyword=%EC%95%84%EC%9D%B4%ED%8F%B0&topN=100&cutOffFrequencyMin=0&cutOffFrequencyMax=0&period=2&invertRowCol=on&start_weekday=SUNDAY&categorySetName=SM&command=GetAssociationTransitionBySentiment");
		System.out.println(json.toString());
		//System.out.println(json.get("rows"));
	}
}