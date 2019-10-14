package com.daumsoft.test5.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daumsoft.test5.model.dto.CaDTO;

public interface CaDAO {
	public List<String> selectOid() throws Exception;
	public void insertOid(CaDTO dto) throws Exception;
	public void insertData(HashMap<String, Object> dtoMap) throws Exception;
	public int countData(CaDTO dto) throws Exception; // 데이터가 현재 있는지 없는지 확인
	public List<String> selectData(String siteId, String categoryId, String startDate, String endDate) throws Exception; // 조회 데이터 더해서 json형태로 반환
	public List<Map<String, String>> selectCount(String siteId, String categoryId, String startDate, String endDate, String oId) throws Exception; // 파라미터를 보내 현재 데이터 카운트수 반환
}
