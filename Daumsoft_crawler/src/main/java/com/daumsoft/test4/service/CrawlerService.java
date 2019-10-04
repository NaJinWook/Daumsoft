package com.daumsoft.test4.service;

public interface CrawlerService {
	public int count(int category) throws Exception;
	public String getTime() throws Exception;
	public void getInsightData(String categoryURL) throws Exception;
	public void addInsightData(String categoryURL) throws Exception;
}
