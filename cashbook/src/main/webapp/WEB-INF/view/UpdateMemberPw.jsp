<%@page import="vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UpdateMemberPw</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%
		Member member = (Member)request.getAttribute("member");
	%>
	<div class="container bg-dark">
		<h1 class="text-white text-center">정보수정</h1>
		<form method="post" action="<%=request.getContextPath()%>/UpdateMemberPwController">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="memberId" value="<%=member.getMemberId()%>" readonly="readonly"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" value="<%=member.getName()%>"></td>
				</tr>
				<tr>
					<%
						if(member.getGender() == "여") {
					%>
							<td>성별</td>
							<td>
								<input type="radio" name="gender" value="남">남 &nbsp; 
								<input type="radio" name="gender" value="여" checked="checked">여
							</td>
					<%
						} else {
					%>
							<td>성별</td>
							<td>
								<input type="radio" name="gender" value="남" checked="checked">남 &nbsp; 
								<input type="radio" name="gender" value="여">여
							</td>
					<%		
						}
					%>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><input type="text" name="phoneNumber" value="<%=member.getPhoneNumber()%>">&nbsp;<span style="opacity: 0.5">('-' 붙여서 전화번호 입력)</span></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="text" name="email" value="<%=member.getEmail()%>"></td>
				</tr>
				<tr>
					<td>현재 비밀번호</td>
					<td><input type="password" name="currentPw"></td>
				</tr>
				<tr>
					<td>변경할 비밀번호</td>
					<td><input type="password" name="memberPw"></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="password" name="checkMemberPw"></td>
				</tr>
				<tr>
					<td colspan="2"><button class="float-right btn btn-primary" type="submit">수정하기</button></td>
				</tr>
			</table>
		</form>		
	</div>
</body>
</html>