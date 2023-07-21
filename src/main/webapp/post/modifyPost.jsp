<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title> 수정 </title>
		<style>
			#title {
				align=center;
				font-size: 30px;
				width: 80%;
				height: 50px;
				outline: none;
				border-top: none;
				border-left: none;
				border-right:none;
				border-bottom: none; 
			}
			#content {
				text-align=center;
				font-size: 20px;
				width:80%;
				height: 500px;
				outline: none;
				border-top: none;
				border-left: none;
				border-right:none;
				border-bottom: none; 
			}
			
			button {
				border-radius:4px;
				background-color: #FFF;
    			color: BLACK;
				font-size: 12px;
				min-height:30px; 
    			min-width: 60px;
			}
		</style>
	</head>
	
	<body>
		<form action="modify" method="POST">
			<input type="hidden" name="postId" value="${requestScope.postId}" >
			<div style="width:80%; text-align=center;">
				<div>
					<input id="title" name="title" type="text" value="${requestScope.title}" required />
					
					<c:set var="postGrade" value="${requestScope.postGrade}" />
					<select style="float: right;" name="postGrade">	
						<c:if test="${userGrade <= 4}"><option value="4" <c:if test="${postGrade==4}">selected</c:if>> 전체 공개 </option></c:if>
						<c:if test="${userGrade <= 3}"><option value="3" <c:if test="${postGrade==3}">selected</c:if>> 3등급 이상 공개 </option></c:if>
						<c:if test="${userGrade <= 2}"><option value="2" <c:if test="${postGrade==2}">selected</c:if>> 2등급 이상 공개 </option></c:if>
						<c:if test="${userGrade <= 1}"><option value="1" <c:if test="${postGrade==1}">selected</c:if>> 1등급 이상 공개 </option></c:if>
					</select>

				</div>
				<div>
					<hr style="height: 2px; background-color:black;">
					<textArea id=content name="content" required>${requestScope.content}</textArea>
				</div>
				<div align="right">
					<button type="submit">저장</button> <br><br>
					<a href="board?pageNum=1"><button>목록</button></a>
					<a href="board?pageNum=1"><button>돌아가기</button></a>
				</div>
			</div>
		</form>
	</body>
</html>