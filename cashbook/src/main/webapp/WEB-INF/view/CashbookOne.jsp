	<%@page import="dao.CashbookDao"%>
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
		int cashbookNo = (Integer)request.getAttribute("cashbookNo");
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
			<tr>
				<td>createDate</td>
				<td><%=cashbook.getCreateDate()%></td>
			</tr>
			<tr>
				<td>updateDate</td>
				<td><%=cashbook.getUpdateDate()%></td>
			</tr>
			<tr>
				<td colspan="2">
					<span class="float-right">
						<a class="btn btn-primary" href="<%=request.getContextPath()%>/UpdateCashbookController?cashbookNo=<%=cashbookNo%>&cashDate=<%=cashbook.getCashDate()%>&kind=<%=cashbook.getKind()%>&cash=<%=cashbook.getCash()%>&memo=<%=cashbook.getMemo()%>">수정</a>
						<a class="btn btn-danger" href="<%=request.getContextPath()%>/DeleteCashbookController?cashbookNo=<%=cashbookNo%>">삭제</a>
					</span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>