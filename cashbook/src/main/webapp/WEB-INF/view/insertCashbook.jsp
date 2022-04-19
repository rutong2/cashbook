<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int year = Integer.parseInt(request.getParameter("year"));
	int month = Integer.parseInt(request.getParameter("month"));
	int day = Integer.parseInt(request.getParameter("day"));
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>insertCashbook</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<h1 class="text-white text-center">입력페이지</h1>
		<form method="post" action="<%=request.getContextPath()%>/InsertCashbookController">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>
						<input type="hidden" name="year" value="<%=year%>">
						<input type="hidden" name="month" value="<%=month%>">
						<input type="hidden" name="day" value="<%=day%>">
					</td>
					<td colspan="2">
						<input type="radio" name="kind" value="수입">수입 
						&nbsp;&nbsp;&nbsp;
						<input type="radio" name="kind" value="지출">지출
					</td>
				</tr>
				<tr>
					<td>돈</td>
					<td><input type="number" name="cash"></td>
				</tr>
				<tr>
					<td>메모</td>
					<td><textarea rows="10" cols="50" name="memo"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
						<button class="btn btn-primary" type="submit">입력하기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>