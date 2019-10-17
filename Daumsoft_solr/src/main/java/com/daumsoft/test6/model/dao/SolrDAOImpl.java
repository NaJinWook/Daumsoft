package com.daumsoft.test6.model.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class SolrDAOImpl implements SolrDAO {
	@Inject
	SqlSession sqlSession;
}
