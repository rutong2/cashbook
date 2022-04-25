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

@WebServlet("/DeleteMemberController")
public class DeleteMemberController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션 받아오기
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId"); // 세션 아이디 받아오기
		
		// 로그인이 되어있지 않으면 로그인페이지로 보내기
		if(sessionMemberId == null) {
			response.sendRedirect(request.getContextPath()+"/LoginController");
			return;
		}
		
		// 디버깅
		System.out.println("DeleteMemberController.doGet() sessionMemberId : " + sessionMemberId);
		
		request.setAttribute("sessionMemberId", sessionMemberId); // DeleteMember.jsp에 정보 넘겨주기 위해 저장
		
		request.getRequestDispatcher("/WEB-INF/view/DeleteMember.jsp").forward(request, response); // DeleteMember.jsp 연결
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 정보받기
		String memberId = null;
		if(request.getParameter("memberId") != null) {
			memberId = request.getParameter("memberId");
		}
		String memberPw = null;
		if(request.getParameter("memberPw") != null) {
			memberPw = request.getParameter("memberPw");
		}
		
		// 디버깅
		System.out.println("DeleteMemberController.doPost() memberId : " + memberId);
		System.out.println("DeleteMemberController.doPost() memberPw : " + memberPw);
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		MemberDao memberDao = new MemberDao();
		int row = memberDao.deleteMember(member);
		
		if(row == 1) {
			System.out.println("회원탈퇴 성공");
			response.sendRedirect(request.getContextPath()+"/LogoutController");
			return;
		} else {
			System.out.println("회원탈퇴 실패");
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController");
			return;
		}
	}

}
