<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<h1 class="text-white text-center">로그인</h1>
		<div>
			<a class="text-white float-right" href="<%=request.getContextPath()%>/InsertMemberController">회원가입</a>
		</div>
		<form method="post" action="<%=request.getContextPath()%>/LoginController">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>memberId</td>
					<td><input type="text" name="memberId"></td>
				</tr>
				<tr>
					<td>memberPw</td>
					<td><input type="password" name="memberPw"></td>
				</tr>
				<tr>
					<td colspan="2"><button class="float-right btn btn-primary" type="submit">로그인</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>