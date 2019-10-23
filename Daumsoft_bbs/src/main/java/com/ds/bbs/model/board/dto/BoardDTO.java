package com.ds.bbs.model.board.dto;

import java.sql.Date;

public class BoardDTO {
	private int bno; // 게시글 번호
	private String title; // 게시글 제목
	private String contents; // 게시글 내용
	private String writer; // 작성자
	private Date regDate; // 작성일
	private Date editDate; // 수정 시간
	private String delYn; // 삭제 여부
	private int fileCount; // 첨부파일 수
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	@Override
	public String toString() {
		return "BoardDTO [bno=" + bno + ", title=" + title + ", contents=" + contents + ", writer=" + writer
				+ ", regDate=" + regDate + ", editDate=" + editDate + ", delYn=" + delYn + ", fileCount=" + fileCount
				+ "]";
	}
}
