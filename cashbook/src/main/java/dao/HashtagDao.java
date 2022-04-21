package dao;

import java.util.*;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import vo.Cashbook;
import vo.Hashtag;

import java.sql.*;

public class HashtagDao {
	public List<Map<String, Object>> selectTagRankList() {
		List<Map<String, Object>> list = new ArrayList<>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			String sql = "SELECT t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) rank"
					+ " FROM"
					+ " (SELECT tag, COUNT(*) cnt"
					+ " FROM hashtag"
					+ " GROUP BY tag) t";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("tag", rs.getString("tag"));
				map.put("cnt", rs.getInt("cnt"));
				map.put("rank", rs.getString("rank"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<Map<String, Object>> selectCashbookListByTag(Hashtag hashtag) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			String sql = "SELECT h.tag tag, c.kind kind, c.cash cash, c.memo memo, c.cash_date cashDate"
					+ " FROM cashbook c LEFT JOIN hashtag h"
					+ " ON c.cashbook_no = h.cashbook_no"
					+ " WHERE h.tag = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, hashtag.getTag());
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag", rs.getString("tag"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				map.put("cashDate", rs.getString("cashDate"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Map<String, Object>> selectTagRankBySearch(String kind, String beginDate, String endDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			
			String sql = "SELECT t.kind kind, t.tag tag, t.cash_date cashDate, t.memo memo"
					+ " FROM"
					+ " (SELECT h.tag, c.kind, c.cash_date, c.memo"
					+ " FROM hashtag h INNER JOIN cashbook c"
					+ " ON h.cashbook_no = c.cashbook_no) t"
					+ " WHERE kind=? AND cash_date BETWEEN ? AND ?"
					+ " GROUP BY kind, tag";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, kind);
			stmt.setString(2, beginDate);
			stmt.setString(3, endDate);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("kind", rs.getString("kind"));
				map.put("tag", rs.getString("tag"));
				map.put("memo", rs.getString("memo"));
				map.put("cashDate", rs.getString("cashDate"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public List<Map<String, Object>> selectTagOne(String tag) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이브 로딩
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234"); // DB 접속
			String sql = "SELECT t.tag tag, t.kind kind, t.cash cash, t.memo memo, t.cash_date cashDate"
					+ " FROM"
					+ " (SELECT h.tag, c.kind, c.cash, c.memo, c.cash_date"
					+ " FROM hashtag h INNER JOIN cashbook c"
					+ " ON h.cashbook_no = c.cashbook_no) t"
					+ " WHERE t.tag = ?"
					+ " GROUP BY kind, tag"
					+ " ORDER BY cash_date DESC";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, tag);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag", rs.getString("tag"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				map.put("cashDate", rs.getString("cashDate"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
