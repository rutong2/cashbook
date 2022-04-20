package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;


@WebServlet("/DeleteCashbookController")
public class DeleteCashbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 삭제할 정보 번호 받아오기
		int cashbookNo = 0;
		if(request.getParameter("cashbookNo") != null) {
			cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		}
		
		// 디버깅
		System.out.println("DeleteCashbookController.doGet() cashbookNo : " + cashbookNo);
		
		CashbookDao cashbookDao = new CashbookDao(); // 메서드 사용을 위한 객체 생성
		int row = cashbookDao.deleteCashbook(cashbookNo); // 메서드 사용 후 반환값 저장
		
		if(row == 1) {
			System.out.println("삭제 성공");
			response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
		} else {
			System.out.println("삭제 실패");
			response.sendRedirect(request.getContextPath()+"/CashbookOneController?cashbookNo="+cashbookNo);
		}
	}
}
