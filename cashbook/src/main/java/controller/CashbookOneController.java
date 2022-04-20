package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/CashbookOneController")
public class CashbookOneController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 정보 받기
		int cashbookNo = 0;
		if(request.getParameter("cashbookNo") != null) {
			cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		}
		
		CashbookDao cashbookDao = new CashbookDao(); // 메서드 사용을 위한 객체생성
		Cashbook cashbook = cashbookDao.selectCashbookOne(cashbookNo); // 메서드 실행 후 저장
		
		request.setAttribute("cashbook", cashbook); // CashbookOne.jsp에 값 넘겨주기 위해 저장
		request.setAttribute("cashbookNo", cashbookNo); // 삭제 수정을 위한 정보의 번호 저장
		
		request.getRequestDispatcher("/WEB-INF/view/CashbookOne.jsp").forward(request, response);
	}

}
