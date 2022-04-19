package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;

@WebServlet("/CashbookListByMonthController")
public class CashbookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 월별 가계부 리스트 요청분석
		Calendar now = Calendar.getInstance(); // ex) 2022.04.19
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1; // 0부터 시작 -> 1월은 0, 2월은 1
		
		if(request.getParameter("year") != null) {
			year = Integer.parseInt(request.getParameter("year"));
		}
		
		if(request.getParameter("month") != null) {
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		// 다음달과 이전달로 갔을때 년도나 월이 바뀌는 알고리즘 구현 
		if(month == 0) {
			month = 12;
			year = year - 1;
		}
		
		if(month == 13) {
			month = 1;
			year = year + 1;
		}
		
		// 디버깅
		System.out.println("year : " + year);
		System.out.println("month : " + month);
		
		// ----------------------------- 캘린더 구현하는 알고리즘 시작 -----------------------------
		
		// 시작시 필요한 공백 <td>
		// firstDay로 오늘 날짜를 구하고 날짜만 1일로 변경
		Calendar firstDay = Calendar.getInstance(); // ex) 2022.04.19
		firstDay.set(Calendar.YEAR, year);
		firstDay.set(Calendar.MONTH, month-1);
		firstDay.set(Calendar.DATE, 1); // ex) 2022.04.01
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		
		// dayOfWeek 일 1, 월 2, ..., 토 7
		// startBlank 일 0, 월 1, ..., 토 6
		// 1)
		int startBlank = dayOfWeek - 1; // 일요일 0, 월요일 1, ..., 토요일 6 -> 1일의 요일을 이용하여 구한다
		
		// 마지막 날짜는 자바달력 API를 이용하여 구함
		// 2)
		int endDay = firstDay.getActualMaximum(Calendar.DATE); // firstDay 달의 제일 큰 숫자 -> 마지막 날짜
		
		// startBlank + endDay 합 결과에 endBlank를 더해서 7의 배수가 되도록
		// 3)
		int endBlank = 0;
		if((startBlank + endDay)%7 != 0) {
			// 7에서 (startBlank + endDay)%7를 빼주면 뒷쪽 Blank의 갯수가 나옴
			endBlank = 7 - ((startBlank + endDay)%7);
		}
		
		// 달력의 총 칸 수
		// 4)
		int totalTd = startBlank + endDay + endBlank;
		
		// ----------------------------- 캘린더 구현하는 알고리즘 끝 -----------------------------
		
		// 모델값(월별 가계부 리스트)을 반환하는 비지니스 로직(모델) 호출
		CashbookDao cashbookDao = new CashbookDao(); // 메서드 사용을 위한 객체 생성
		List<Map<String, Object>> list = cashbookDao.selectCashbookListByMonth(year, month); // 메서드 사용 후 저장
		
		/*
		 달력 출력에 필요한 모델값(1), 2), 3), 4)) + 데이터베이스에서 반환된 모델값(list, year출력년도, month출력월) View쪽으로 넘기기 위해서 Map에 저장
		 */
		// 달력 출력에 필요한 모델값(1), 2), 3), 4))
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalTd", totalTd);
		// 데이터베이스에서 반환된 모델값(list, year출력년도, month출력월)
		request.setAttribute("list", list);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		
		// 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashbookListByMonth.jsp").forward(request, response);
	}

}
