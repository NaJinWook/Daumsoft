package com.daumsoft.test1;

public class MainClass {
	public static void main(String[] args) {
		DataAccess dao = new DataAccess();
		ParseClass ps = new ParseClass();
		String sort = "desc"; // 정렬 방식 지정
		dao.insert(); // TSV파일을 읽어들여 DB에 데이터 입력
		ps.fileTSV(dao.columnName(), dao.select(sort), sort); // DB정보를 가져와서 TSV파일 생성(오름차순, 내림차순)
		ps.formatData(dao.columnName(), dao.select(sort), ps.tagFileName()); // DB정보를 가져와서 Tagged type, Json Type formatting(오름차순, 내림차순)
		dao.tagParsing(ps.tagFileName()); // Tagged Text File을 읽어들여 DB doc2 테이블에 넣기
	}
}
