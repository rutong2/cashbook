package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/UpdateMemberPwController")
public class UpdateMemberPwController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
  
		// 디버깅
		System.out.println("UpdateMemberPwController.doGet() sessionMemberId : " + sessionMemberId);
  
		if(sessionMemberId == null) {
			// 로그인이 안돼있으면
			response.sendRedirect(request.getContextPath()+"/LoginController");
			return;
		}
		
		// 정보 받아오기
		String name = null;
		if(request.getParameter("name") != null) {
			name = request.getParameter("name");
		}
		
		String gender = null;
		if(request.getParameter("gender") != null) {
			gender = request.getParameter("gender");
		}
		
		String phoneNumber = null;
		if(request.getParameter("phoneNumber") != null) {
			phoneNumber = request.getParameter("phoneNumber");
		}
		
		String email = null;
		if(request.getParameter("email") != null) {
			email = request.getParameter("email");
		}
		
		// 디버깅
		System.out.println("UpdateMemberPwController.doGet() name : " + name);
		System.out.println("UpdateMemberPwController.doGet() gender : " + gender);
		System.out.println("UpdateMemberPwController.doGet() phoneNumber : " + phoneNumber);
		System.out.println("UpdateMemberPwController.doGet() email : " + email);
		
		Member member = new Member();
		member.setMemberId(sessionMemberId);
		member.setName(name);
		member.setGender(gender);
		member.setPhoneNumber(phoneNumber);
		member.setEmail(email);
		
		request.setAttribute("member", member);
		
		request.getRequestDispatcher("/WEB-INF/view/UpdateMemberPw.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글이 깨지지 않게 설정
		
		// 정보 받아오기
		String memberId = null;
		if(request.getParameter("memberId") != null) {
			memberId = request.getParameter("memberId");
		}
		
		String name = null;
		if(request.getParameter("name") != null) {
			name = request.getParameter("name");
		}
		
		String gender = null;
		if(request.getParameter("gender") != null) {
			gender = request.getParameter("gender");
		}
		
		String phoneNumber = null;
		if(request.getParameter("phoneNumber") != null) {
			phoneNumber = request.getParameter("phoneNumber");
		}
		
		String email = null;
		if(request.getParameter("email") != null) {
			email = request.getParameter("email");
		}
		
		String currentPw = null;
		if(request.getParameter("currentPw") != null) {
			currentPw = request.getParameter("currentPw");
		}
		
		String memberPw = null;
		if(request.getParameter("memberPw") != null) {
			memberPw = request.getParameter("memberPw");
		}
		
		String checkMemberPw = null;
		if(request.getParameter("checkMemberPw") != null) {
			checkMemberPw = request.getParameter("checkMemberPw");
		}
		
		// 디버깅
		System.out.println("UpdateMemberPwController.doPost() memberId : " + memberId);
		System.out.println("UpdateMemberPwController.doPost() name : " + name);
		System.out.println("UpdateMemberPwController.doPost() gender : " + gender);
		System.out.println("UpdateMemberPwController.doPost() phoneNumber : " + phoneNumber);
		System.out.println("UpdateMemberPwController.doPost() email : " + email);
		System.out.println("UpdateMemberPwController.doPost() currentPw : " + currentPw);
		System.out.println("UpdateMemberPwController.doPost() memberPw : " + memberPw);
		System.out.println("UpdateMemberPwController.doPost() checkMemberPw : " + checkMemberPw);
		
		// 바꿀 비밀번호가 서로 일치하지 않으면 수정페이지로 보내기
		if(!memberPw.equals(checkMemberPw)) {
			System.out.println("변경할 비밀번호와 체크한 비밀번호가 일치하지 않습니다.");
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController");
			return;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("name", name);
		map.put("gender", gender);
		map.put("phoneNumber", phoneNumber);
		map.put("email", email);
		map.put("currentPw", currentPw);
		map.put("memberPw", memberPw);
		

		MemberDao memberDao = new MemberDao();
		int row = memberDao.updateMemberPw(map);
		
		if(row == 1) {
			System.out.println("수정 완료");
			response.sendRedirect(request.getContextPath()+"/LogoutController");
			return;
		} else {
			System.out.println("수정 실패 (현재 비밀번호가 틀렸습니다)");
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController");
			return;
		}
	}

}
