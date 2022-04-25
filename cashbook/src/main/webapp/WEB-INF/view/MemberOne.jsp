<%@page import="vo.Member"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MemberOne</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%	
		// 정보 가져오기
		Member member = (Member)request.getAttribute("member"); 
	%>
	<div class="container bg-dark">
		<h1 class="text-white text-center">회원정보</h1>
		<div class="float-right">
			<a class="text-white" href="<%=request.getContextPath()%>/UpdateMemberPwController?name=<%=member.getName()%>&gender=<%=member.getGender()%>&phoneNumber=<%=member.getPhoneNumber()%>&email=<%=member.getEmail()%>">정보수정</a> 
			<span class="text-white">/</span>
			<a class="text-white" href="<%=request.getContextPath()%>/DeleteMemberController">회원탈퇴</a>
		</div>
		<table class="table table-bordered text-center table-striped table-dark">
			<tr>
				<td>아이디</td>
				<td><%=member.getMemberId()%></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><%=member.getName()%></td>
			</tr>
			<tr>
				<td>성별</td>
				<td><%=member.getGender()%></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><%=member.getPhoneNumber()%></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><%=member.getEmail()%></td>
			</tr>
		</table>
	</div>
</body>
</html>