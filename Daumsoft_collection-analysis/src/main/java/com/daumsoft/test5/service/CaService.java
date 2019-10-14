package com.daumsoft.test5.service;

import java.util.List;
import java.util.Map;

public interface CaService {
	public void naverCrawl() throws Exception;
	public void dataView(String siteId, String categoryId, String startDate, String endDate) throws Exception;
	public List<Map<String, String>> countView(String siteId, String categoryId, String startDate, String endDate, String oId) throws Exception;
}
