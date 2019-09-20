package com.ds.bbs.model.board.dao;

import java.util.List;

import com.ds.bbs.model.board.dto.BoardDTO;

public interface BoardDAO {
	public List<BoardDTO> list(int startNum, int postNum, String search_option, String keyword) throws Exception; // 게시글 목록
	public void write(BoardDTO dto) throws Exception; // 게시글 등록
	public BoardDTO read(int bno) throws Exception; // 게시글 읽기
	public void update(BoardDTO dto) throws Exception; // 게시글 수정
	public void delete(int bno) throws Exception; // 게시글 삭제
	public int count(String search_option, String keyword) throws Exception;
}
