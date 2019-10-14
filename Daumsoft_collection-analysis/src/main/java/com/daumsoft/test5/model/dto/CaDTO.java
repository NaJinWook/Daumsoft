package com.daumsoft.test5.model.dto;

import org.apache.ibatis.type.Alias;

@Alias("dto")
public class CaDTO {
	private String aId;
	private int siteId;
	private int categoryId;
	private String oId;
	private String oName;
	private String title;
	private String content;
	private String analysisData;
	private String url;
	private String regDate;
	
	public CaDTO() {
	}
	public CaDTO(String oId, String oName) {
		this.oId = oId;
		this.oName = oName;
	}
	public CaDTO(String aId, int siteId, int categoryId, String oId, String title, String content, String analysisData,
			String url, String regDate) {
		this.aId = aId;
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.oId = oId;
		this.title = title;
		this.content = content;
		this.analysisData = analysisData;
		this.url = url;
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "CaDTO [aId=" + aId + ", siteId=" + siteId + ", categoryId=" + categoryId + ", oId=" + oId + ", title="
				+ title + ", content=" + content + ", analysisData=" + analysisData + ", url=" + url + ", regDate="
				+ regDate + "]";
	}

	
	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}
	
	public String getoName() {
		return oName;
	}
	public void setoName(String oName) {
		this.oName = oName;
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

	public String getAnalysisData() {
		return analysisData;
	}

	public void setAnalysisData(String analysisData) {
		this.analysisData = analysisData;
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
	
	
}
