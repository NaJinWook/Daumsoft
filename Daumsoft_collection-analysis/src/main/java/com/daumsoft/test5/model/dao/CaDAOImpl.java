package com.daumsoft.test5.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class CaDAOImpl implements CaDAO {
	SqlSession sqlSession;
}
