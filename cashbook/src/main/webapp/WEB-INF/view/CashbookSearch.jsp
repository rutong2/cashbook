<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CashbookSearch</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
	%>
	<div class="container bg-dark">
		<h1 class="text-white text-center">검색</h1>
		<table class="table table-bordered text-center table-striped table-dark">
		 	<thead>
		 		<tr>
		 			<th>kind</th>
		 			<th>tag</th>
		 			<th>memo</th>
		 			<th>cashDate</th>
		 		</tr>
		 	</thead>
		 	<tbody>
		 		<%
		 			for(Map map : list) {
		 		%>
		 				<tr>
		 					<td><%=map.get("kind")%></td>
		 					<td><%=map.get("tag")%></td>
		 					<td><%=map.get("memo")%></td>
		 					<td><%=map.get("cashDate")%></td>
		 				</tr>
		 		<%
		 			}
		 		%>
		 	</tbody>
		 </table>	
	</div>
</body>
</html>