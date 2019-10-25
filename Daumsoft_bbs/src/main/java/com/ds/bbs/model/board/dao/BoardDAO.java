package com.ds.bbs.model.board.dao;

import java.util.List;
import java.util.Map;

import com.ds.bbs.model.board.dto.BoardDTO;
import com.ds.bbs.model.board.dto.FileDTO;

public interface BoardDAO {
	public List<BoardDTO> list(int startNum, int postNum, String search_option, String keyword, String sort) throws Exception; // 게시글 목록
	public void write(BoardDTO dto) throws Exception; // 게시글 등록
	public BoardDTO read(int bno) throws Exception; // 게시글 읽기
	public void update(BoardDTO dto) throws Exception; // 게시글 수정
	public void delete(int bno) throws Exception; // 게시글 삭제
	public int count(String search_option, String keyword) throws Exception; // 
	public void insert_fileUpload(FileDTO f_dto) throws Exception;
	public void update_fileUpload(FileDTO f_dto) throws Exception;
	public List<FileDTO> f_read(int bno) throws Exception;
	public FileDTO selectFile(int fileNo) throws Exception;
	public void delFile(Map<String, Object> map) throws Exception;
	public void delete2(int bno) throws Exception;
}
