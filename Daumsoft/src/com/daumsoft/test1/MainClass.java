package com.daumsoft.test1;

public class MainClass {
	public static void main(String[] args) {
		long start = System.currentTimeMillis(); // 프로그램 시작 시간
		DataAccess dao = new DataAccess();
		ParseClass ps = new ParseClass();
		String sort = "asc"; // 정렬 방식 지정
		String type = "json"; // 확장자명 지정
		//dao.insert(); // TSV파일을 읽어들여 DB에 데이터 입력
		//dao.select(sort); // DB정보를 읽어들여 콘솔창 출력(오름차순, 내림차순)
		//dao.columnName(); // 컬럼명 가져오기
		//ps.fileTSV(dao.columnName(), dao.select(sort), sort); // DB정보를 가져와서 TSV파일 생성(오름차순, 내림차순)
		//ps.formatData(dao.columnName(), dao.select(sort), type); // DB정보를 가져와서 Tagged type, Json Type formatting(오름차순, 내림차순)
		//dao.tagParsing(); // Tagged Text File을 읽어들여 DB doc2 테이블에 넣기
		long end = System.currentTimeMillis(); // 프로그램 종료 시간
		System.out.println("========================================================");
		System.out.println("실행 시간 : " + ( end - start) / 1000.0 + "초");
	}
}
