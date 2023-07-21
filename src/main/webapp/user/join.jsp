<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% boolean doubled = (boolean) request.getAttribute("doubled"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<style type="text/css">
#full {
	width: 300px;
	height: 300px;
	margin: 0 auto;
}
</style>
</head>
<body>
	<script type="text/javascript">
		function fail() {
			if (<%= doubled %>) {
				alert("중복된 아이디입니다.");
			}
		}
		
		fail();
	</script>
	
	<div id="full">
		<div align="center">
			<h1>회원 가입</h1>
		</div>
		<form action="join" method="post">
			NAME <input type="text" minLength="2" maxLength="10" name="userName" value="${param.userName}" placeholder="이름을 입력하세요" required /> <br>
			ID <input type="text" minLength="6" maxLength="20" name="userId" value="${param.userId}" placeholder="아이디를 입력하세요" required /> <br>
			PW <input type="password" minLength="4" maxLength="20" name="userPw" value="${param.userPw}" placeholder="비밀번호를 입력하세요" required /> <br>
			EMAIL <input type="email" name="email" value="${param.email}" placeholder="이메일을 입력하세요" required /> <br>
			<br>
			<input type="button" value="뒤로 가기" onclick="location.href='board?pageNum=1'" />
			<input type="submit" value="가입하기" />
		</form>
	</div>
</body>
</html>
