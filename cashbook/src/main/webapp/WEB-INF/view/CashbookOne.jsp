<%@page import="vo.Cashbook"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CashbookOne</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%
		Cashbook cashbook = (Cashbook)request.getAttribute("cashbook");
	%>
	<div class="container bg-dark">
		<h1 class="text-white text-center">입력페이지</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<tr>
				<td>cashDate</td>
				<td><%=cashbook.getCashDate()%></td>
			</tr>
			<tr>
				<td>kind</td>
				<td><%=cashbook.getKind()%></td>
			</tr>
			<tr>
				<td>cash</td>
				<td><%=cashbook.getCash()%></td>
			</tr>
			<tr>
				<td>memo</td>
				<td><%=cashbook.getMemo()%></td>
			</tr>
		</table>
	</div>
</body>
</html>