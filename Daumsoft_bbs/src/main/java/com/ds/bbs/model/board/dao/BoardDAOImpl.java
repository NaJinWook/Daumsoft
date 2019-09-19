package com.ds.bbs.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ds.bbs.model.board.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	SqlSession sqlSession;
	
	// 게시글 목록
	@Override
	public List<BoardDTO> list(int startNum, int postNum, String search_option, String keyword) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("postNum", postNum);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		return sqlSession.selectList("board.listPage", map);
	}
	
	// 게시글 등록
	@Override
	public void write(BoardDTO dto) throws Exception {
		sqlSession.insert("board.write", dto);
	}
	
	// 게시글 읽기
	@Override
	public BoardDTO read(int bno) throws Exception {
		return sqlSession.selectOne("board.read", bno);
	}
	
	// 게시글 수정
	@Override
	public void update(BoardDTO dto) throws Exception {
		sqlSession.update("board.update", dto);
	}

	// 게시글 삭제
	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete("board.delete", bno);
	}

	@Override
	public int count() throws Exception {
		return sqlSession.selectOne("board.count");
	}
	
}
