package com.daumsoft.test4.model.dao;

import java.util.List;

import com.daumsoft.test4.model.dto.CrawlerDTO;

public interface CrawlerDAO {
	public void getData(List<CrawlerDTO> list) throws Exception;
	public void update_list(List<CrawlerDTO> update_list) throws Exception;
	public void add_list(List<CrawlerDTO> add_list) throws Exception;
	public String top_idx() throws Exception;
	public int count(int category) throws Exception;
}
