<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<head>
<title>Post Detail</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
}

.content-wrapper {
	display: flex;
	justify-content: space-between;
}

.main-content {
	width: 80%;
}

.blank-space {
	width: 20%;
}
</style>

<script>
	const postId = urlParams.get("postId");
	function confirmDelete(){
		
		var result = confirm("게시글을 삭제를 하고싶나요?");
		if(result) {
		     document.location.href =`delete?postId=${postId}`;
		}
	}
</script>

</head>
<body>
	<div class="content-wrapper">
		<div class="main-content">
			<div align="right" style="padding: 10px; padding-right: 25px;">
				<a href="modify?postId=<%=postId%>"><button>수정</button></a> 
				<button onclick="confirmDelete()">삭제</button>
			</div>
			<table border="1">
				<tr>
					<td><input type="text" name="title" value="<%=title%>" readOnly style="font-weight: bold; font-size: 32px;"></td>
				</tr>
				<tr>
					<td><%=writer%></td>
				</tr>
				<tr>
					<td>마지막 수정일시: <%=modDate%></td>
				</tr>
				<tr>
					<td>게시글 등급: <%=postGrade%></td>
				</tr>
				<tr>
					<td>Views: <%=hit%></td>
				</tr>
			</table>
			<textarea rows="22" cols="160" readOnly style="resize: none;"><%=content%></textarea>
			<div>
				<a href="board?pageNum=1"><button style="position: absolute; bottom: 10px; right: 80px;">목록</button></a>
			</div>
			<br>
		</div>
		<!-- main-content  -->
	</div>
	<!--  content-wrapper -->
</body>
</html>