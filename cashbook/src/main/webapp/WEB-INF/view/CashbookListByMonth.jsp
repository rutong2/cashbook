<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CashbookListByMonth</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<%
			// Map에 저장된 값들 받아오기
			int startBlank = (Integer)request.getAttribute("startBlank");
			int endDay = (Integer)request.getAttribute("endDay");
			int endBlank = (Integer)request.getAttribute("endBlank");
			int totalTd = (Integer)request.getAttribute("totalTd");
			List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
			int year = (Integer)request.getAttribute("year");
			int month = (Integer)request.getAttribute("month");
			
			// 디버깅
			System.out.println("CashbookListByMonth.jsp -> startBlank : " + startBlank);
			System.out.println("CashbookListByMonth.jsp -> endDay : " + endDay);
			System.out.println("CashbookListByMonth.jsp -> endBlank : " + endBlank);
			System.out.println("CashbookListByMonth.jsp -> totalTd : " + totalTd);
			System.out.println("CashbookListByMonth.jsp -> list.size() : " + list.size());
			System.out.println("CashbookListByMonth.jsp -> year : " + year);
			System.out.println("CashbookListByMonth.jsp -> month : " + month);
		%>
		<h2 class="text-white text-center"><%=year%>년 <%=month%>월</h2>
		<div>
			<a href="<%=request.getContextPath()%>/CashbookListByMonthController?year=<%=year%>&month=<%=month-1%>">이전달</a>
			<a href="<%=request.getContextPath()%>/CashbookListByMonthController?year=<%=year%>&month=<%=month+1%>">다음달</a>
		</div>
		<!-- 
			1) 이번날 1일의 요일 firstDay -> startBlank -> 일 0칸, 월 1칸, 화 2칸 ... 토 6칸
			2) 마지막 날짜 endDay
			3) endBlank -> totalBlank(7로 나누어 떨어지지 않을때 나누어 떨어지게 더하는 Blank)
			4) td의갯수 1~totalBlank
					+
			5) 가계부 list
			6) 오늘 날짜
		 -->
		 <table class="table table-bordered text-center table-striped table-dark">
		 	<thead>
		 		<tr>
		 			<th class="text-danger">일</th>
		 			<th>월</th>
		 			<th>화</th>
		 			<th>수</th>
		 			<th>목</th>
		 			<th>금</th>
		 			<th class="text-primary">토</th>
		 		</tr>
		 	</thead>
			 <tbody>
			 	<tr>
			 		<%
			 			for(int i=1; i<=totalTd; i+=1) {
			 				if((i-startBlank)>0 && (i-startBlank)<=endDay) {
			 					String c = "";
			 					if(i%7==0) {
			 						c = "text-primary";
			 					} else if(i%7==1) {
			 						c = "text-danger";
			 					}
			 		%>
			 						<td>
			 							<span class="<%=c%>"><%=i-startBlank%></span>
			 							<a href="<%=request.getContextPath()%>/InsertCashbookController?year=<%=year%>&month=<%=month%>&day=<%=i-startBlank%>" class="btn btn-light">입력</a>
			 							<div>
			 								<%
			 									// 해당 날짜의 cashbook 목록 출력
			 									for(Map map : list) {
			 										if((Integer)map.get("day") == (i-startBlank)) {
			 								%>
			 											<div>
			 												[<%=map.get("kind")%>] 
			 												<%=map.get("cash")%>원
			 												<%=map.get("memo")%>...
		 												</div>
			 								<%
			 										}
			 									}
			 								%>
			 							</div>
			 						</td> 
			 		<%
			 				} else {
			 		%>
		 						<td>&nbsp;</td>
			 		<%
			 				}
			 				if(i < totalTd && i%7==0) {
			 		%>
			 					</tr>
			 					<tr>
			 		<%			
			 				}
			 			}
			 		%>
			 	</tr>
			 </tbody>
		 </table>
	</div>
</body>
</html>