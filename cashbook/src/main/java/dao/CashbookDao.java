package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import vo.Cashbook;

public class CashbookDao {
	
	public List<Map<String, Object>> selectCashbookListByMonth(int year, int month) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			
			// 쿼리 작성
			String sql = "SELECT cashbook_no cashbookNo "
					+ "		, DAY(cash_date) day"
					+ "		, kind"
					+ "		, cash"
					+ "		, CONCAT(LEFT(memo, 5), '...') memo"
					+ "   FROM cashbook"
					+ "   WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
					+ "   ORDER BY kind ASC, DAY(cash_date) ASC";
			
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				// Map에 저장
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("day", rs.getInt("day"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map); // list에 저장
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 반납
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public void insertCashbook(Cashbook cashbook, List<String> hashtag) {
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			conn.setAutoCommit(false);; // 자동 커밋 해제
			
			String sql = "INSERT INTO cashbook"
					+ "  (cash_date, kind, cash, memo, update_date, create_date)"
					+ "  VALUES"
					+ "  (?, ?, ?, ?, NOW(), NOW())";
			// insert + select 방금 생성된 행의 키값 ex) select 방금 입력한 cashbook_no from cashbook;
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // 쿼리 작성
			stmt.setString(1, cashbook.getCashDate());
			stmt.setString(2, cashbook.getKind());
			stmt.setInt(3, cashbook.getCash());
			stmt.setString(4, cashbook.getMemo());
			stmt.executeUpdate(); // insert 실행
			rs = stmt.getGeneratedKeys(); // select 실행 -> insert한 키값 select cashbook_no from cashbook;
			int cashbookNo = 0;
			
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
			}
			// hashtag 테이블에 저장하는 코드
			PreparedStatement stmt2 = null;
			for(String h : hashtag) {
				String sql2 = "INSERT INTO hashtag(cashbook_no, tag, create_date) "
						+ " VALUES(?, ?, NOW())";
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, cashbookNo);
				stmt2.setString(2, h);
				stmt2.executeUpdate();
			}
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Cashbook selectCashbookOne(int cashbookNo) {
		Cashbook cashbook = null; // 객체 생성 준비
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			
			// 쿼리 작성
			String sql = "SELECT cash_date cashDate, kind, cash, memo, create_date createDate, update_date updateDate FROM cashbook WHERE cashbook_no=?";
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			stmt.setInt(1, cashbookNo);
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				cashbook = new Cashbook(); // 객체 생성
				cashbook.setCashDate(rs.getString("cashDate"));
				cashbook.setKind(rs.getString("kind"));
				cashbook.setCash(rs.getInt("cash"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setCreateDate(rs.getString("createDate"));
				cashbook.setUpdateDate(rs.getString("updateDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cashbook;
	}
	
	public int deleteCashbook(int cashbookNo) {
		int row = 0;
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			conn.setAutoCommit(false); // 자동커밋 X
			
			// hashtag테이블이 cashbook을 외래키로 가지고 있으므로 삭제할때는 hashtag의 정보를 삭제 후 cashbook의 정보를 삭제해야 한다
			// hashtag테이블 삭제 구현
			String sql = "DELETE FROM hashtag"
					+ " WHERE cashbook_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			stmt.executeUpdate();
			
			// cashbook테이블 삭제 구현
			String sql2 = "DELETE FROM cashbook"
					+ " WHERE cashbook_no=?";
			stmt2 = conn.prepareStatement(sql2); // 쿼리 작성
			stmt2.setInt(1, cashbookNo);
			row = stmt2.executeUpdate(); // 삭제 실행
			
			conn.commit(); // 커밋 실행
		} catch (Exception e) {
			try {
				conn.rollback(); // 예외가 발생하면 rollback
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return row;
	}
}
