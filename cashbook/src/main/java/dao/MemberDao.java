package dao;

import java.sql.*;
import java.util.*;

import vo.Member;


public class MemberDao {
	// 회원가입
	// 회원수정
	// 회원탈퇴
	// 회원정보
	
	// 로그인
	 public String selectMemberByIdPw(Member member) {
	      String memberId = null;
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
	      try {
	         Class.forName("org.mariadb.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, member.getMemberId());
	         stmt.setString(2, member.getMemberPw());
	         rs = stmt.executeQuery();
	         if(rs.next()) {
	            memberId = rs.getString("memberId");
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
	      return memberId;
	   }
	 
	 public int insertMember(Member member) {
		 int row = 0;
		 // 자원 준비
		 Connection conn = null;
		 PreparedStatement stmt = null;
		 ResultSet rs = null;	
		 String sql = "INSERT INTO member VALUES("
		 		+ "	?"
		 		+ "	, PASSWORD(?)"
		 		+ " , ?"
		 		+ " , ?"
		 		+ " , ?"
		 		+ " , ?"
		 		+ "	, NOW())";
		 
		 try {
			 Class.forName("org.mariadb.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, member.getMemberId());
	         stmt.setString(2, member.getMemberPw());
	         stmt.setString(3, member.getGender());
	         stmt.setString(4, member.getName());
	         stmt.setString(5, member.getPhoneNumber());
	         stmt.setString(6, member.getEmail());
	         row = stmt.executeUpdate(); // 입력 실행
		} catch (Exception e) {
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
	 
	 public Member selectMemberOne(String memberId) {
		 Member member = null;
		 // 자원 준비
		 Connection conn = null;
		 PreparedStatement stmt = null;
		 ResultSet rs = null;	
		 
		 String sql = "SELECT member_id memberId, name, gender, phone_number phoneNumber, email FROM member WHERE member_id = ?";
		 
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				member = new Member();
				member.setMemberId(rs.getString("memberId"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setPhoneNumber(rs.getString("phoneNumber"));
				member.setEmail(rs.getString("email"));
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
		 
		 return member;
	 }
	 
	 public int updateMemberPw(Map<String, Object> map) {
		 int row = 0;
		 // 자원 준비
		 Connection conn = null;
		 PreparedStatement stmt = null;
		 
		 String sql = "UPDATE member SET member_pw = PASSWORD(?), gender = ?, name = ?, phone_number = ?, email = ?"
		 		+ " WHERE member_id = ? AND member_pw = PASSWORD(?)";
		 
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, (String)map.get("memberPw"));
				stmt.setString(2, (String)map.get("gender"));
				stmt.setString(3, (String)map.get("name"));
				stmt.setString(4, (String)map.get("phoneNumber"));
				stmt.setString(5, (String)map.get("email"));
				stmt.setString(6, (String)map.get("memberId"));
				stmt.setString(7, (String)map.get("currentPw"));
				
				row = stmt.executeUpdate();
			} catch (Exception e) {
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
	 
	 public int deleteMember(Member member) {
		 int row = 0;
		 // 자원 준비
		 Connection conn = null;
		 PreparedStatement stmt = null;
		 PreparedStatement stmt2 = null;
		 PreparedStatement stmt3 = null;
		 PreparedStatement stmt4 = null;
		 ResultSet rs = null;
		 List<Integer> list = new ArrayList<Integer>();
		 
		 String sql = "SELECT cashbook_no cashbookNo FROM cashbook"
		 		+ " WHERE member_id=?";
		 
		 String sql2 = "DELETE FROM hashtag"
		 		+ " WHERE cashbook_no=?";
		 
		 String sql3 = "DELETE FROM cashbook"
		 		+ " WHERE member_id=?";
		 
		 String sql4 = "DELETE FROM member"
		 		+ " WHERE member_id=? AND member_pw=PASSWORD(?)";
		 
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			conn.setAutoCommit(false); // 오토커밋 X
			
			// cashbookNo 가져오기
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getInt("cashbookNo"));
			}
			
			// 해시태그 삭제
			for(int i=0; i<list.size(); i++) {
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, list.get(i));
				stmt2.executeUpdate();
				System.out.println("cashbookNo : " + list.get(i));
			}
			
			// 캘린더 정보 삭제
			stmt3 = conn.prepareStatement(sql3);
			stmt3.setString(1, member.getMemberId());
			stmt3.executeUpdate();
			
			// 회원 정보 삭제
			stmt4 = conn.prepareStatement(sql4);
			stmt4.setString(1, member.getMemberId());
			stmt4.setString(2, member.getMemberPw());
			row = stmt4.executeUpdate();
			
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
		 
		 return row;
	 }
}
