<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<style>
#title {align =center;
	font-size: 30px;
	width: 80%;
	height: 50px;
	outline: none;
	border-top: none;
	border-left: none;
	border-right: none;
	border-bottom: none;
}

#content {text-align =center;
	font-size: 20px;
	width: 80%;
	height: 500px;
	outline: none;
	border-top: none;
	border-left: none;
	border-right: none;
	border-bottom: none;
}

button {
	border-radius: 4px;
	background-color: #FFF;
	color: BLACK;
	font-size: 12px;
	min-height: 30px;
	min-width: 60px;
}
</style>
</head>

<body>
	<div style="width: 80%;">
		<form action="write" method="POST">
			<input type="hidden" name="userId" value="${param.userId}"/>
			<div>
				<input id="title" name="title" type="text" maxLength="50" required />
				<select style="float: right;" name="postGrade">
					<c:if test="${userGrade <= 4}"><option value="4">전체 공개</option></c:if>
					<c:if test="${userGrade <= 3}"><option value="3">3등급 이상 공개</option></c:if>
					<c:if test="${userGrade <= 2}"><option value="2">2등급 이상 공개</option></c:if>
					<c:if test="${userGrade <= 1}"><option value="1">1등급 이상 공개</option></c:if>
				</select>
			</div>
			<div>
				<hr style="height: 2px; background-color: black;">
				<textArea id=content name="content" required></textArea>
			</div>
			<div align="right">
				<button type="submit">저장</button>
				<br> <br>
			</div>
		</form>
		<div align="right">
			<a href="board?pageNum=1"><button>목록</button></a>
			<a href="board?pageNum=1"><button>돌아가기</button></a>
		</div>
	</div>
</body>
</html>
