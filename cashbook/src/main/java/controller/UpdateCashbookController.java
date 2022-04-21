package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;


@WebServlet("/UpdateCashbookController")
public class UpdateCashbookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 정보 받아오기
		int cashbookNo = 0;
		if(request.getParameter("cashbookNo") != null) {
			cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		}
		
		String cashDate = null;
		if(request.getParameter("cashDate") != null) {
			cashDate = request.getParameter("cashDate");
		}
		
		String kind = null;
		if(request.getParameter("kind") != null) {
			kind = request.getParameter("kind");
		}
		
		int cash = 0;
		if(request.getParameter("cash") != null) {
			cash = Integer.parseInt(request.getParameter("cash"));
		}
		
		String memo = null;
		if(request.getParameter("memo") != null) {
			memo = request.getParameter("memo");
		}
		
		// 디버깅
		System.out.println("UpdateCashbookController.doGet() cashbookNo : " + cashbookNo);
		System.out.println("UpdateCashbookController.doGet() cashDate : " + cashDate);
		System.out.println("UpdateCashbookController.doGet() kind : " + kind);
		System.out.println("UpdateCashbookController.doGet() cash : " + cash);
		System.out.println("UpdateCashbookController.doGet() memo : " + memo);
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCashbookNo(cashbookNo);
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		request.setAttribute("cashbook", cashbook);
		
		request.getRequestDispatcher("/WEB-INF/view/UpdateCashbook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글이 깨지지 않게 설정
		// 정보 받기
		int cashbookNo = 0;
		if(request.getParameter("cashbookNo") != null) {
			cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		}
		
		String cashDate = "";
		if(request.getParameter("cashDate") != null) {
			cashDate = request.getParameter("cashDate");
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
		
		// 디버깅
		System.out.println("UpdateCashbookController.doPost() cashbookNo : " + cashbookNo);
		System.out.println("UpdateCashbookController.doPost() cashDate : " + cashDate);
		System.out.println("UpdateCashbookController.doPost() kind : " + kind);
		System.out.println("UpdateCashbookController.doPost() cash : " + cash);
		System.out.println("UpdateCashbookController.doPost() memo : " + memo);
		
		// 태그 구하는 알고리즘
		List<String> hashtag = new ArrayList<String>();
		String memo2 = memo.replace("#"," #");
		String[] arr = memo2.split(" ");
		for(String s : arr) {
			if(s.startsWith("#")) {
				String temp = s.replace("#","");
				if(!temp.equals("")) {
				hashtag.add(temp);
				}
			}
		}
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCashbookNo(cashbookNo);
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		// 수정 구현
		CashbookDao cashbookDao = new CashbookDao();
		int row = cashbookDao.updateCashbook(cashbook, hashtag);
		
		if(row == 1) {
			System.out.println("수정 성공");
			response.sendRedirect(request.getContextPath()+"/CashbookOneController?cashbookNo="+cashbookNo);
		} else {
			System.out.println("수정 실패");
			response.sendRedirect(request.getContextPath()+"/CashbookOneController?cashbookNo="+cashbookNo);
		}
	}

}
