<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<form action="logCheck" method="post">
		ID <input type="text" minLength="6" maxLength="20" name="userId" placeholder="아이디를 입력하세요" required /> <br>
		PW <input type="password" minLength="4" maxLength="20" name="userPw" placeholder="비밀번호를 입력하세요" required /> <br>
		<br>
		<input type="submit" value="로그인" />
		<input type="button" value="회원가입" onclick="location.href='join'" />
	</form>
