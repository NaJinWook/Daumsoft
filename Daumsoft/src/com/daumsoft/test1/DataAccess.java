package com.daumsoft.test1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class DataAccess {
	private TsvParserSettings tsvSet = null; // TSV 읽기 파일 셋팅 객체
	private TsvParser tsv = null; // TSV 읽기 파서 객체
	private BufferedReader br = null; // 파일 읽기 위함
	private FileReader fileReader = null; // 파일 읽기 위함
	
	public void settingReadParser() {
		tsvSet = new TsvParserSettings();
		tsv = new TsvParser(tsvSet);
	}
	
	// TSV -> DB에 넣기
	public void insert() {
		DBConnection dbConnect = new DBConnection();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		settingReadParser(); // 데이터 읽기전에 파서 셋팅
		try {
			fileReader = new FileReader("C:\\doc.tsv"); // 지정된 경로의 TSV 읽기
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fileReader);
		List<String[]> allRowsData = tsv.parseAll(br);
		// List : 전체 라인 수
		// String[] : 각각의 컬럼 0~2 존재
		try {
			conn = dbConnect.conn;
			if(conn != null) {
				System.out.println("DOC TABLE에 데이터 INSERT 중...");
				pstmt = conn.prepareStatement("insert into doc values (?,?,?)");
				for(int i=1; i<allRowsData.size(); i++) {
					pstmt.setInt(1, Integer.parseInt(allRowsData.get(i)[0]));
					pstmt.setString(2, allRowsData.get(i)[1]);
					pstmt.setString(3, allRowsData.get(i)[2]);
					pstmt.addBatch();
					if((i % 100000) == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
						conn.commit();
					}
				}
				pstmt.executeBatch();
				conn.commit();
				conn.close();
				pstmt.close();
			}
		} catch(SQLException e) {
			System.out.println("SQLException 예외 발생");
			e.printStackTrace();
		} finally {
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}
	
	// DB에 접속하여 데이터 조회
	public List<String[]> select(String sort) {
		DBConnection dbConnect = new DBConnection();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String[]> allRowsData = null;
		try {
			allRowsData = new ArrayList<String[]>();
			conn = dbConnect.conn;
			stmt = conn.createStatement();
			
			// asc로 입력받으면 오름차순 실행, desc로 입력받으면 내림차순 실행
			if(sort.equals("asc") || sort.equals("ASC")) {
				rs = stmt.executeQuery("SELECT * FROM doc");
			} else if(sort.equals("desc") || sort.equals("DESC")) {
				rs = stmt.executeQuery("SELECT * FROM doc order by DOC_SEQ DESC");
			}
			
			if(conn != null) {
				while(rs.next()) {
					String[] str = new String[3];
					str[0] = Integer.toString(rs.getInt("DOC_SEQ"));
					str[1] = rs.getString("TITLE");
					str[2] = rs.getString("REG_DT");
					System.out.println(str[0]+"\t"+str[1]+"\t"+str[2]);
					allRowsData.add(str);
				}
			}
		} catch(SQLException e) {
			System.out.println("SQLException 예외 발생");
			e.printStackTrace();
		} finally { 
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (stmt != null) { try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		return allRowsData;
	}
	
	// Column Name 가져오기
	public String[] columnName() {
		DBConnection dbConnect = new DBConnection();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String[] columnName = new String[3];
		try {
			conn = dbConnect.conn;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select column_name from information_schema.columns where table_schema='test' and TABLE_NAME='doc'");
			if(conn != null) {
				int i=0;
				while(rs.next()) {
					columnName[i] = rs.getString("column_name");
					System.out.println(columnName[i]);
					i++;
				}
			}
		} catch(SQLException e) {
			System.out.println("SQLException 예외 발생");
			e.printStackTrace();
		} finally { 
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (stmt != null) { try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		return columnName;
	}
	
	// Tagged text File -> 파싱하여 doc2 table에 넣기
	public void tagParsing() {
		DBConnection dbConnect = new DBConnection();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		List<String[]> allRowsData = new ArrayList<String[]>();
		String[] strArr = null;
		try {
			fileReader = new FileReader("C:\\Users\\Daumsoft\\Downloads\\Tconvert_doc.txt");
			br = new BufferedReader(fileReader);
			String str = "";
			strArr = new String[3];
			int i = 0, j = 0;
			while((str = br.readLine()) != null) {
				if(i%2 == 0 && i%8 != 0) {
					strArr[j] = str;
					j++;
					if(j == 3) {
						allRowsData.add(strArr);
						strArr = new String[3];
						j = 0;
					}
				}
				i++;
			}
			conn = dbConnect.conn;
			if(conn != null) {
				System.out.println("DOC2 TABLE에 데이터 INSERT 중...");
				pstmt = conn.prepareStatement("insert into doc2 values (?,?,?)");
				for(String[] data : allRowsData) {
					//System.out.println(data[0] +"\t"+ data[1]+"\t"+ data[2]);
					pstmt.setInt(1, Integer.parseInt(data[0]));
					pstmt.setString(2, data[1]);
					pstmt.setString(3, data[2]);
					pstmt.addBatch();
					if((i % 100000) == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
						conn.commit();
					}
				}
				pstmt.executeBatch();
				conn.commit();
				conn.close();
				pstmt.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } } 
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}
}
