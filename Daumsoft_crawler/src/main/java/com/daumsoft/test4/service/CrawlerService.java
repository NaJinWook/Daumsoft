package com.daumsoft.test4.service;

public interface CrawlerService {
	public int count(int type, int category) throws Exception;
	public String getTime() throws Exception;
	public void getInsightData(String categoryURL) throws Exception;
	public void addInsightData(String categoryURL) throws Exception;
	public void getHuffingtonpostData(String categoryURL) throws Exception;
	public void addHuffingtonpostData(String categoryURL) throws Exception;
}
