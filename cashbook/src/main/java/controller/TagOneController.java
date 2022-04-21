package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;


@WebServlet("/TagOneController")
public class TagOneController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 정보받기
		String tag = "";
		if(request.getParameter("tag") != null) {
			tag = request.getParameter("tag");
		}
		
		// 디버깅
		System.out.println("TagOneController.doGet() tag : " + tag);
		
		HashtagDao hashtagDao = new HashtagDao(); // 메서드 사용을 위한 객체 생성
		List<Map<String, Object>> list = hashtagDao.selectTagOne(tag); // 메서드 사용후 객체 저장
		request.setAttribute("list", list); // TagOne.jsp에 넘겨주기 위해 저장
		
		request.getRequestDispatcher("/WEB-INF/view/TagOne.jsp").forward(request, response);
	}

}
