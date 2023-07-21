<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int postId = (int) request.getAttribute("postId");
String title = (String) request.getAttribute("title");
String writer = (String) request.getAttribute("writer");
String content = (String) request.getAttribute("content");
int postGrade = (int) request.getAttribute("postGrade");
String modDate = (String) request.getAttribute("modDate");
int hit = (int) request.getAttribute("hit");
%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
<title>Post Detail</title>
<style>
body{
font: Raleway
}

#title {
	font-weight: normal;
	font-size: 30px;
	width: 80%;
	outline: none;
	border-top: none;
	border-left: none;
	border-right: none;
	border-bottom: none;
}

#headers {
	border-collapse: collapse; /* Optional: Merge cell borders */
	border: 0; /* Remove the border */
	font-size: 16px;
	width: 80%;
	height: 50px;
	outline: none;
	border-top: none;
	border-left: none;
	border-right: none;
	border-bottom: none;
}

#content {
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
<script>
	const postId = urlParams.get("postId");
	function confirmDelete() {

		var result = confirm("게시글을 삭제를 하고싶나요?");
		if (result) {
			document.location.href = `delete?postId=${postId}`;
		}
	}
</script>
</head>
<body>
	<div style="width: 80%;">
		<div align="right" style="padding-top: 10px;">
			<a href="modify?postId=<%=postId%>"><button>수정</button></a>
			<button onclick="confirmDelete()">삭제</button>
		</div>
		<div id="title"><%=title%> </div>
		<hr style="height: 2px; background-color: black;">
		<table id="headers">
			<tr>
				<td>작성자: <%=writer%></td>
				<td class="vertical-separator"></td>
				<td>마지막 수정일시: <%=modDate%></td>
				<td class="vertical-separator"></td>
				<td>게시글 등급: <%=postGrade%></td>
				<td class="vertical-separator"></td>
				<td>조회수: <%=hit%></td>
			</tr>
		</table>
		<hr style="height: 2px; background-color: black;">
		<textarea id=content name="content" rows="22" cols="160"
			style="resize: none;" readOnly><%=content%></textarea>
		<div>
			<a href="board?pageNum=1"><button
					style="position: absolute; bottom: 20px; right: 320px;">돌아가기</button></a>
		</div>
		<br>
	</div>
</body>