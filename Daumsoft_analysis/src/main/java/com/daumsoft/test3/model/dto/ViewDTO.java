package com.daumsoft.test3.model.dto;

public class ViewDTO {
	private String sns_type; // 소셜미디어 유형
	private int positive_count; // 긍정 빈도수
	private int negative_count; // 부정 빈도수
	private int neutral_count; // 중립 빈도수
	private String register_date; // 등록일시
	
	public ViewDTO(){
	}
	
	public String getSns_type() {
		return sns_type;
	}
	public void setSns_type(String sns_type) {
		this.sns_type = sns_type;
	}
	public int getPositive_count() {
		return positive_count;
	}
	public void setPositive_count(int positive_count) {
		this.positive_count = positive_count;
	}
	public int getNegative_count() {
		return negative_count;
	}
	public void setNegative_count(int negative_count) {
		this.negative_count = negative_count;
	}
	public int getNeutral_count() {
		return neutral_count;
	}
	public void setNeutral_count(int neutral_count) {
		this.neutral_count = neutral_count;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	@Override
	public String toString() {
		return "ViewDTO [sns_type=" + sns_type + ", positive_count=" + positive_count + ", negative_count="
				+ negative_count + ", neutral_count=" + neutral_count + ", register_date=" + register_date + "]";
	}
}
