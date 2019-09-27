package com.daumsoft.test3.service;

import java.util.Map;

import com.daumsoft.test3.model.dto.ViewDTO;

public interface ViewService {
	public Map<String, Object> select() throws Exception;
	public void insert(ViewDTO dto) throws Exception; // DB에 데이터 입력
	public void delete() throws Exception; // 데이터 지우고 새로 조회
}
