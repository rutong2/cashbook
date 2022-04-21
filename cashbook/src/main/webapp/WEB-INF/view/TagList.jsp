<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TagList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
	%>
	<div class="container bg-dark">
		<h1 class="text-white text-center">Tag Rank</h1>
		<form method="post" action="<%=request.getContextPath()%>/TagController">
			<div class="float-right">
				<select class="text-center" name="kind">
					<option value="수입">수입</option>
					<option value="지출">지출</option>
				</select>
				<input type="date" name="beginDate"> <span class="text-white">~</span> <input type="date" name="endDate">
				<button type="submit">검색</button>
			</div>
		</form>
		<table class="table table-bordered text-center table-striped table-dark">
		 	<thead>
		 		<tr>
		 			<th>rank</th>
		 			<th>tag</th>
		 			<th>count</th>
		 		</tr>
		 	</thead>
		 	<tbody>
		 		<%
		 			for(Map map : list) {
		 		%>
		 				<tr>
		 					<td><%=map.get("rank")%></td>
		 					<td><a href="<%=request.getContextPath()%>/TagOneController?tag=<%=map.get("tag")%>"><%=map.get("tag")%></a></td>
		 					<td><%=map.get("cnt")%></td>
		 				</tr>
		 		<%
		 			}
		 		%>
		 	</tbody>
		 </table>	
	</div>
</body>
</html>