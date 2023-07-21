<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List, java.util.ArrayList, com.bestteam.dto.ListDto, com.bestteam.service.PostService"%>
<%
	List<ListDto> postList = (List<ListDto>) request.getAttribute("postList");
	int totalCount = (int) request.getAttribute("totalCount");
	int pageNum = 1;
	String userId = (String) session.getAttribute("userId");
	Integer userGrade = (Integer) session.getAttribute("userGrade");
	String searchCondition = (String) request.getAttribute("searchCondition");
	String searchContent = (String) request.getAttribute("searchContent");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 화면</title>
<style>
.search-button {
	position: absolute;
	top: 0;
	right: 0;
}

.welcome {
	position: absolute;
	top: 30px;
	right: 10px;
}
</style>
</head>
<body>
	<!-- 게시판  -->
	<div align="center">
		<table border="1" width="1000">
			<caption style="height: 100px;">
				<h1>Board List</h1>
			</caption>
			<colgroup>
				<col style="width: 5%;" />
				<col style="width: auto;" />
				<col style="width: 15%;" />
				<col style="width: 10%;" />
				<col style="width: 10%;" />
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>

			<%
			if (postList.size() == 0) {
			%>
			<tr>
				<td align="center" colspan="5">등록된 게시글이 없습니다.</td>
			</tr>
			<%
			} else {
			for (ListDto dto : postList) {
			%>
			<tr>
				<td align="center"><%=dto.getPostId()%></td>
				<td align="center"><a href="detail?postId=<%=dto.getPostId()%>"><%=dto.getTitle()%></a></td>
				<td align="center"><%=dto.getWriter()%></td>
				<td align="center"><%=dto.getRegDate()%></td>
				<td align="center"><%=dto.getHit()%></td>
			</tr>
			<%
			}
			}
			%>
			<tr>
				<td colspan="5" align="center">
					<%
					for (int i = 1; i <= totalCount; i++) {
					%> <a href="board?pageNum=<%= i %>&searchCondition=${searchCondition}&searchContent=${searchContent}"><%=i%></a>
					<%
					}
					%>
				</td>
			</tr>
		</table>
	</div>

	<!-- id pw -->
	<div class="welcome">
		<%
		if (userId == null) {
		%>
		<%@ include file="include/nonUserBoard.jsp"%>
		<%
		} else if (userGrade == 0) {
		%>
		<%@ include file="include/adminBoard.jsp"%>
		<%
		} else {
		%>
		<%@ include file="include/userBoard.jsp"%>
		<%
		}
		%>
	</div>

	<!-- 검색 -->
	<div class="search-button">
		<form action="board" method="get">
			<input type="hidden" name="pageNum" value="<%=pageNum%>" />
			<select name="searchCondition">
				<option value="ALL">전체</option>
				<option value="TITLE">제목</option>
				<option value="WRITER">작성자</option>
				<option value="CONTENT">내용</option>
			</select> <input type="text" name="searchContent" /> <input type="submit" value="검색" />
		</form>
	</div>
</body>
</html>