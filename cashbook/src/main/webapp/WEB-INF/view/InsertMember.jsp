<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>InsertMember</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<h1 class="text-white text-center">회원가입</h1>
		<form method="post" action="<%=request.getContextPath()%>/InsertMemberController">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="memberId"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="memberPw"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td>성별</td>
					<td><input type="radio" name="gender" value="남">남&nbsp;<input type="radio" name="gender" value="여">여</td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><input type="text" name="phoneNumber">&nbsp;<span style="opacity: 0.5">('-' 붙여서 전화번호 입력)</span></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<td colspan="2"><button class="float-right btn btn-primary" type="submit">회원가입</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>