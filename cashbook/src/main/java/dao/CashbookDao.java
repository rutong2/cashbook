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
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public void insertCashbook(Cashbook cashbook) {
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			String sql = "INSERT INTO cashbook"
					+ "  (cashbook_no, cash_date, kind, cash, memo, update_date, create_date)"
					+ "  VALUES"
					+ "  (?, ?, ?, ?, ?, NOW(), NOW())";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			stmt.setInt(1, cashbook.getCashbookNo());
			stmt.setString(2, cashbook.getCashDate());
			stmt.setString(3, cashbook.getKind());
			stmt.setInt(4, cashbook.getCash());
			stmt.setString(5, cashbook.getMemo());
			
			int row = stmt.executeUpdate(); // 입력한 정보 수
			
			if(row == 1) {
				System.out.println("입력 완료");
			} else {
				System.out.println("입력 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
