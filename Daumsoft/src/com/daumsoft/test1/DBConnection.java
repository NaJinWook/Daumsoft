package com.daumsoft.test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	// 프로그램 실행 시 DB정보 메모리에 올림
	private static final String driver = "org.mariadb.jdbc.Driver";
	private static final String url = "jdbc:mariadb://127.0.0.1:3306/test";
	private static final String user = "root";
	private static final String pwd = "1234";
	
	public Connection conn = null;
	
	public DBConnection() { // DB 연결 확인
		try {
			Class.forName(driver); // Driver에 Load
			conn = DriverManager.getConnection(url,user,pwd); // DB 접속 정보를 넘겨줌
			conn.setAutoCommit(false); // 자동 commit 막음
		} catch(ClassNotFoundException e) {
			System.out.println("Driver Load 실패");
			e.printStackTrace();
		} catch(SQLException e) {
			System.out.println("데이터베이스 관련 예외 발생");
			e.printStackTrace();
		}
	}
}
