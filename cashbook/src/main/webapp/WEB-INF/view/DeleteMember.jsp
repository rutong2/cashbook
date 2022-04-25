<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DeleteMember</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%
		String sessionMemberId = (String)request.getAttribute("sessionMemberId");
	
		// 디버깅
		System.out.println("DeleteMember.jsp sessionMemberId : " + sessionMemberId);
	%>
	<div class="container bg-dark">
		<h1 class="text-white text-center">회원탈퇴</h1>
		<form method="post" action="<%=request.getContextPath()%>/DeleteMemberController">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="memberId" value="<%=sessionMemberId%>" readonly="readonly"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="memberPw"></td>
				</tr>
				<tr>
					<td colspan="2"><button class="float-right btn btn-danger" type="submit">회원탈퇴</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>