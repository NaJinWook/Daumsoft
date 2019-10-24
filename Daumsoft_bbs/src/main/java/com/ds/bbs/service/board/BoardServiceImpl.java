package com.ds.bbs.service.board;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ds.bbs.model.board.dao.BoardDAO;
import com.ds.bbs.model.board.dto.BoardDTO;
import com.ds.bbs.model.board.dto.FileDTO;

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
	public int count(String search_option, String keyword) throws Exception {
		return boardDao.count(search_option, keyword);
	}

	@Override
	public void insert_fileUpload(FileDTO f_dto) throws Exception {
		boardDao.insert_fileUpload(f_dto);
	}
	
	@Override
	public void update_fileUpload(FileDTO f_dto) throws Exception {
		boardDao.update_fileUpload(f_dto);
	}

	@Override
	public List<FileDTO> f_read(int bno) throws Exception {
		return boardDao.f_read(bno);
	}

	// 첨부파일 한건의 정보를 가져온다.
	@Override
	public FileDTO selectFile(int fileNo) throws Exception {
		return boardDao.selectFile(fileNo);
	}

	@Override
	public void delFile(Map<String, Object> map) throws Exception {
		boardDao.delFile(map);
	}

	@Override
	public void delete2(int bno) throws Exception {
		boardDao.delete2(bno);
	}

}
