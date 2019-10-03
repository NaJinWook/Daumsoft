package com.daumsoft.test4.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.daumsoft.test4.model.dto.CrawlerDTO;

@Repository
public class CrawlerDAOImpl implements CrawlerDAO {
	@Inject
	SqlSession sqlSession;

	@Override
	public void getData(List<CrawlerDTO> list) throws Exception {
		for(int i=list.size()-1; i>=0; i--) {
			sqlSession.insert("crawler.insert", list.get(i));
		}
	}

	@Override
	public void update_list(List<CrawlerDTO> update_list) throws Exception {
		for(CrawlerDTO dto : update_list) {
			sqlSession.update("crawler.update", dto);
		}
	}
	
	@Override
	public void add_list(List<CrawlerDTO> add_list) throws Exception {
		if(add_list != null) {
			for(int i=add_list.size()-1; i>=0; i--) {
				sqlSession.insert("crawler.insert", add_list.get(i));
			}
		}
	}

	@Override
	public String top_idx() throws Exception {
		return sqlSession.selectOne("crawler.top_idx");
	}

	@Override
	public int count(int category) throws Exception {
		return sqlSession.selectOne("crawler.count", category);
	}

}
