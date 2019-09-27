package com.daumsoft.test3.model.dao;

import java.util.List;

import com.daumsoft.test3.model.dto.ViewDTO;

public interface ViewDAO {
	public List<ViewDTO> select() throws Exception; // 전체 데이터 가져오기
	public void insert(ViewDTO dto) throws Exception; // DB에 데이터 입력
	public void delete() throws Exception; // 데이터 지우고 새로 조회
}