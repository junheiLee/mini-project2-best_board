<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.List, java.util.ArrayList, com.bestteam.dto.UserListDto" %>
<%
	List<UserListDto> userList = (List<UserListDto>) request.getAttribute("userList");
%>
<c:set var="userList" scope="request" value="<%=userList %>" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>manage</title>
		<style>
			#applyList {
				float:left;
				width:50%;
				height: auto;
			}
			#userList {
				float:right;
				height: auto;
				width:50%;
			}
		</style>
	</head>
	<body>
	<div>
		<h1 align="center"> 회원 관리 </h1>
		<div id="applyList" style="margin:0 auto;border-right:1px;">
			<jsp:include page="applyList.jsp"/>
		</div>
		<div id="userList" style="margin:0 auto;">
			<jsp:include page="userList.jsp"/>
		</div>
		<a href="board?pageNum=1"><button style="position: absolute; bottom: 10px; right: 10px;">돌아가기</button></a>
	</div>
	</body>
</html>