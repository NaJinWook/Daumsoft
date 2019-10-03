package com.daumsoft.test4.model.dto;

public class CrawlerDTO {
	private String idx; // 게시글 고유 번호
	private int type; // 사이트 구분 (1=인사이트)
	private int category; // 카테고리 (1=생활일반)
	private String title; // 제목
	private String content; // 내용
	private String writer; // 작성자
	private String email; // 이메일
	private String url; // 상세 URL 주소
	private String regDate; // 등록일
	public CrawlerDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public CrawlerDTO(String idx, int type, int category, String title, String content, String writer,
			String email, String url, String regDate) {
		this.idx = idx;
		this.type = type;
		this.category = category;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.email = email;
		this.url = url;
		this.regDate = regDate;
	}

	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "CrawlerDTO [idx=" + idx + ", type=" + type + ", category=" + category + ", title=" + title
				+ ", content=" + content + ", writer=" + writer + ", email=" + email + ", url=" + url + ", regDate="
				+ regDate + "]";
	}
}
