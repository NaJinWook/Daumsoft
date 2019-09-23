package com.ds.bbs.service.board;

import java.util.List;
import java.util.Map;

import com.ds.bbs.model.board.dto.BoardDTO;
import com.ds.bbs.model.board.dto.FileDTO;

public interface BoardService {
	public List<BoardDTO> list(int startNum, int postNum, String search_option, String keyword) throws Exception; // 게시글 목록
	public void write(BoardDTO dto) throws Exception; // 게시글 등록
	public BoardDTO read(int bno) throws Exception; // 게시글 읽기
	public void update(BoardDTO dto) throws Exception; // 게시글 수정
	public void delete(int bno) throws Exception; // 게시글 삭제
	public void delete2(int bno) throws Exception; // 게시글 삭제시 첨부파일도 같이 삭제
	public void fileUpload(FileDTO f_dto) throws Exception; // 게시글 등록
	public int count(String search_option, String keyword) throws Exception;
	public List<FileDTO> f_read(int bno) throws Exception;
	public FileDTO selectFile(int fileNo) throws Exception;
	public void delFile(Map<String, Object> map) throws Exception;
}
