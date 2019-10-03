package com.daumsoft.test4.service;

public interface CrawlerService {
	public int count() throws Exception;
	public String getTime() throws Exception;
	public void getData(String categoryURL) throws Exception;
	public void addData(String categoryURL) throws Exception;
}
