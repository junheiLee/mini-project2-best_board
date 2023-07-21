<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>userList</title>
		<style>
			form {
				margin-bottom:5px;
				align="center"
			}
			.inputButton {
				border-radius:4px;
				background-color: #FFF;
    			color: BLACK;
				font-size: 12px;
			}
			#inputId {
				border-bottom:none;
				border-top: none;
				outline:none;
				width: 80px;
			}
			#inputName {
				border-bottom:none;
				border-top: none;
				border-left: none;
				border-right: none;
				outline:none;
				width: 60px;
			}
			.container {
				margin-left:20px;
				height: 500px;
				overflow: auto;
			}
			.container::-webkit-scrollbar {
				width: 10px;
			}
			.container::-webkit-scrollbar-thumb {
				background-color: #2f3542;
				border-radius: 10px;
			}
			.container::-webkit-scrollbar-track {
				background-color: grey;
				border-radius: 10px;
				box-shadow: inset 0px 0px 5px white;
			}
		</style>
	</head>
	<body>
		<div style="width:100%; margin:0 auto;">
			<h2 align="center">회원 목록</h2>
			<div class="container" align="center">
				<c:forEach var = "user" items = "${userList}">
				<c:choose>
					<c:when test="${user.isApproved()}">
					<form action="manage" method="post">
						<input id ="inputId" type="text" value="${user.getUserId()}" name="userId" readOnly />
						<input id = "inputName" type="text" value="${user.getUserName()}" readOnly />
						
						<select name="userGrade">	
							<option value="1" <c:if test="${user.getUserGrade()==1}">selected</c:if>> 1등급</option>
							<option value="2" <c:if test="${user.getUserGrade()==2}">selected</c:if>> 2등급</option>
							<option value="3" <c:if test="${user.getUserGrade()==3}">selected</c:if>> 3등급</option>
							<option value="4" <c:if test="${user.getUserGrade()==4}">selected</c:if>> 4등급</option>
						</select>
						---------------
						<input class="inputButton" type="submit" value="저장">
						<a href="manage?userId=${user.getUserId()}&approval=false"><input class="inputButton" type="button" value="퇴출"/></a>
						<br>
					</form>
					
					</c:when>
				</c:choose>
				
				</c:forEach>
			</div>
		</div>
	</body>
</html>