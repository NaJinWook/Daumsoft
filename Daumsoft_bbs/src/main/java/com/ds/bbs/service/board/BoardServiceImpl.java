package com.ds.bbs.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ds.bbs.model.board.dao.BoardDAO;
import com.ds.bbs.model.board.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	BoardDAO boardDao;
	
	// 게시글 목록
	@Override
	public List<BoardDTO> list(int startNum, int postNum, String search_option, String keyword) throws Exception {
		return boardDao.list(startNum, postNum, search_option, keyword);
	}
	
	// 게시글 등록
	@Override
	public void write(BoardDTO dto) throws Exception {
		boardDao.write(dto);
	}
	
	// 게시글 읽기
	@Override
	public BoardDTO read(int bno) throws Exception {
		return boardDao.read(bno);
	}
	
	// 게시글 수정
	@Override
	public void update(BoardDTO dto) throws Exception {
		boardDao.update(dto);
	}
	
	// 게시글 삭제
	@Override
	public void delete(int bno) throws Exception {
		boardDao.delete(bno);
	}

	@Override
	public int count() throws Exception {
		return boardDao.count();
	}

}
