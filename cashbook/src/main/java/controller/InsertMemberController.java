package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인이 되어있으면 메인페이지로 보내주기
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
  
		// 디버깅
		System.out.println("InsertMemberController.doGet() sessionMemberId : " + sessionMemberId);
  
		if(sessionMemberId != null) {
			// 이미 로그인이 되어있는 상태
			response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
			return;
		}
		
		// 로그인 되어있으면
		request.getRequestDispatcher("/WEB-INF/view/InsertMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글이 깨지지않게 설정
		// 정보 받아오기
		String memberId = null;
		if(request.getParameter("memberId") != null) {
			memberId = request.getParameter("memberId");
		}
		
		String memberPw = null;
		if(request.getParameter("memberPw") != null) {
			memberPw = request.getParameter("memberPw");
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
		
		// 메서드 매개변수를 위해서 정보 저장하기
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setGender(gender);
		member.setName(name);
		member.setPhoneNumber(phoneNumber);
		member.setEmail(email);
		
		
		MemberDao memberDao = new MemberDao(); // 입력 메서드 사용을 위한 객체 생성
		int row = memberDao.insertMember(member);
		
		if(row == 1) {
			System.out.println("회원가입 성공");
			response.sendRedirect(request.getContextPath()+"/LoginController");
		} else {
			System.out.println("회원가입 실패");
			response.sendRedirect(request.getContextPath()+"/InsertMemberController");
		}
		
		// 디버깅
		System.out.println("InsertMemberController.doPost() memberId : " + memberId);
		System.out.println("InsertMemberController.doPost() memberPw : " + memberPw);
		System.out.println("InsertMemberController.doPost() gender : " + gender);
		System.out.println("InsertMemberController.doPost() name : " + name);
		System.out.println("InsertMemberController.doPost() memberPw : " + memberPw);
		System.out.println("InsertMemberController.doPost() phoneNumber : " + phoneNumber);
		System.out.println("InsertMemberController.doPost() email : " + email);
		
	}

}
