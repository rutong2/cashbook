package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/InsertCashbookController")
public class InsertCashbookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/insertCashbook.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글이 깨지지 않게 설정
		
		// 정보 받아오기
		int year = 0;
		if(request.getParameter("year") != null) {
			year = Integer.parseInt(request.getParameter("year"));
		}
		
		int month = 0;
		if(request.getParameter("month") != null) {
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		int day = 0;
		if(request.getParameter("day") != null) {
			day = Integer.parseInt(request.getParameter("day"));
		}
		
		String kind = "";
		if(request.getParameter("kind") != null) {
			kind = request.getParameter("kind");
		}
		
		int cash = 0;
		if(request.getParameter("cash") != null) {
			cash = Integer.parseInt(request.getParameter("cash"));
		}
		
		String memo = "";
		if(request.getParameter("memo") != null) {
			memo = request.getParameter("memo");
		}
		
		String choiceDate = year+"-"+month+"-"+day; // 선택한 날짜
		
		// 디버깅
		System.out.println("InsertCashbookController.java -> year : " + year);
		System.out.println("InsertCashbookController.java -> month : " + month);
		System.out.println("InsertCashbookController.java -> day : " + day);
		System.out.println("InsertCashbookController.java -> kind : " + kind);
		System.out.println("InsertCashbookController.java -> cash : " + cash);
		System.out.println("InsertCashbookController.java -> memo : " + memo);
		System.out.println("InsertCashbookController.java -> choiceDate : " + choiceDate);
		
		
		
		Cashbook cashbook = new Cashbook(); // 메서드 매개변수로 사용할 객체 생성
		// 정보 객체에 저장
		cashbook.setKind(kind);
		cashbook.setCashDate(choiceDate);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		CashbookDao cashbookDao = new CashbookDao(); // 메서드 사용을 위한 객체 생성
		cashbookDao.insertCashbook(cashbook); // 메서드 사용
		
		// 입력 후 되돌아가기
		response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController?year="+year+"&month="+month);
	}
}
