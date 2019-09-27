package com.daumsoft.test3.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.daumsoft.test3.model.dto.ViewDTO;

@Repository
public class ViewDAOImpl implements ViewDAO {
	@Inject
	SqlSession sqlSession;

	@Override
	public void insert(ViewDTO dto) throws Exception {
		sqlSession.insert("view.insert", dto);
	}

	@Override
	public void delete() throws Exception {
		sqlSession.delete("view.delete");
	}

	@Override
	public List<ViewDTO> select() throws Exception {
		return sqlSession.selectList("view.select");
	}
}
