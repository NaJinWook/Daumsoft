package com.daumsoft.test5.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.daumsoft.test5.model.dto.CaDTO;

@Repository
public class CaDAOImpl implements CaDAO {
	@Inject
	SqlSession sqlSession;

	@Override
	public List<String> selectOid() throws Exception {
		return sqlSession.selectList("crawl.selectOid");
	}

	@Override
	public void insertOid(CaDTO dto) throws Exception {
		sqlSession.insert("crawl.insertOid", dto);
	}
	
	@Override
	public void insertData(HashMap<String, Object> dtoMap) throws Exception {
		sqlSession.insert("crawl.insertData", dtoMap);
	}

	@Override
	public int countData(CaDTO dto) throws Exception {
		return sqlSession.selectOne("crawl.countData", dto);
	}

	@Override
	public List<String> selectData(String siteId, String categoryId, String startDate, String endDate) throws Exception {
		return sqlSession.selectList("crawl.selectData");
	}

	@Override
	public List<Map<String, String>> selectCount(String siteId, String categoryId, String startDate, String endDate, String oId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("siteId", siteId);
		map.put("categoryId", categoryId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("oId", oId);
		return sqlSession.selectList("crawl.selectCount", map);
	}

}
