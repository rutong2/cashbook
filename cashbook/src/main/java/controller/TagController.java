package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;

@WebServlet("/TagController")
public class TagController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.selectTagRankList();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/TagList.jsp").forward(request, response);
 	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글이 깨지지않게 설정
		// 정보 받아오기
		String kind = "";
		if(!request.getParameter("kind").equals("")) {
			kind = request.getParameter("kind");
		}
		String beginDate = "1990-01-01";
		if(!request.getParameter("beginDate").equals("")) {
			beginDate = request.getParameter("beginDate");
		}
		
		String endDate = "2099-12-31";
		if(!request.getParameter("endDate").equals("")) {
			endDate = request.getParameter("endDate");
		}
		
		// 디버깅
		System.out.println("TagController.doPost() kind : " + kind);
		System.out.println("TagController.doPost() beginDate : " + beginDate);
		System.out.println("TagController.doPost() endDate : " + endDate);
		
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.selectTagRankBySearch(kind, beginDate, endDate);
		
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/CashbookSearch.jsp").forward(request, response);
	}

}
