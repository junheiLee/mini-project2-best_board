<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>applyList</title>
		<style>
			button	 {
				border-radius:4px;
				background-color: #FFF;
    			color: BLACK;
				font-size: 12px;
			}
			#inputId {
				border-bottom:none;
				border-top: none;
				border-left: none;
				outline:none;
				width: 80px;
			}
			#inputName {
				border-bottom:none;
				border-top: none;
				border-left: none;
				outline:none;
				width: 60px;
			}
			#inputEmail {
				border-bottom:none;
				border-top: none;
				border-right: none;
				outline:none;
				width: 200px;
			}
			.container {
				margin-left:20px;
				margin-right:50px;
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
			<h2 align="center">회원 가입 요청</h2>
			<div class="container" align="center">
				<c:forEach var = "user" items = "${userList}">
					<c:if test="${!user.isApproved()}">
						<input id ="inputId" type="text" value="${user.getUserId()}" readOnly />
						<input id ="inputName" type="text" value="${user.getUserName()}" readOnly />
						<input id ="inputEmail" type="text" value="${user.getEmail()}" readOnly />
						---------------
						<a href="manage?userId=${user.getUserId()}&approval=true"><button>수락</button></a>
						<a href="manage?userId=${user.getUserId()}&approval=false"><button>거절</button></a>
						<br>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</body>
</html>