package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		// 디버깅
		System.out.println("InsertCashbookController.doGet -> year : " + year);
		System.out.println("InsertCashbookController.doGet -> month : " + month);
		System.out.println("InsertCashbookController.doGet -> day : " + day);
		
		String cashDate = year+"-"+month+"-"+day; // 선택한 날짜
		
		request.setAttribute("cashDate", cashDate); // insertCashbook.jsp에 값을 넘겨주기 위해 저장
		
		request.getRequestDispatcher("/WEB-INF/view/insertCashbook.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글이 깨지지 않게 설정
		
		// 정보 받아오기
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
		
		String cashDate = "";
		if(request.getParameter("cashDate") != null) {
			cashDate = request.getParameter("cashDate");
		}
		
		// 디버깅
		System.out.println("InsertCashbookController.doPost -> kind : " + kind);
		System.out.println("InsertCashbookController.doPost -> cash : " + cash);
		System.out.println("InsertCashbookController.doPost -> memo : " + memo);
		System.out.println("InsertCashbookController.doPost -> cashDate : " + cashDate);
		
		// ------------------------------- 해시태그 구현 시작 -------------------------------
		List<String> hashtag = new ArrayList<>();
		String memo2 = memo.replace("#", " #");
		String[] arr = memo2.split(" ");
		
		for(String s : arr) {
			if(s.startsWith("#")) {
				String temp = s.replace("#", "");
				if(temp.length()>0) {
					hashtag.add(temp);
				}
			}
		}
		// 디버깅
		System.out.println("InsertCashbookController.doPost -> hashtag.size() : " + hashtag.size());
		for(String s : hashtag) {
			System.out.println("InsertCashbookController.doPost -> s : " + s);
		}
		// ------------------------------- 해시태그 구현 끝 -------------------------------
		
		Cashbook cashbook = new Cashbook(); // 메서드 매개변수로 사용할 객체 생성
		// 정보 객체에 저장
		cashbook.setKind(kind);
		cashbook.setCashDate(cashDate);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		CashbookDao cashbookDao = new CashbookDao(); // 메서드 사용을 위한 객체 생성
		cashbookDao.insertCashbook(cashbook, hashtag); // 메서드 사용
		
		// 입력 후 되돌아가기
		response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
	}
}
