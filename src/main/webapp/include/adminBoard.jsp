<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
button {
	border-radius: 4px;
	background-color: #FFF;
	color: BLACK;
	font-size: 12px;
	min-height: 30px;
	min-width: 60px;
}
</style>
<h2>
	환영합니다. <br>
	${userId}님!
</h2>
<br>

<button onclick="location.href='logout.jsp'">로그아웃</button>
<button onclick="location.href='write'">글쓰기</button>
<button onclick="location.href='admin'">admin</button>
