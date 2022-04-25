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

@WebServlet("/SelectMemberOneController")
public class SelectMemberOneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		
		// 로그인이 안돼있으면 접근 금지
		if(sessionMemberId == null) {
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		MemberDao memberDao = new MemberDao(); // 메서드 사용을 위한 객체 생성
		Member member = memberDao.selectMemberOne(sessionMemberId);
		
		request.setAttribute("member", member);
		
		request.getRequestDispatcher("/WEB-INF/view/MemberOne.jsp").forward(request, response);
		
	}

}
