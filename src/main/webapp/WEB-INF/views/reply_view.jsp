<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<form action="reply" method="post">
<input type="hidden" name="bId" value="${reply_view.bId}">
<input type="hidden" name="bGroup" value="${reply_view.bGroup}">
<input type="hidden" name="bStep" value="${reply_view.bStep}">
<input type="hidden" name="bIndent" value="${reply_view.bIndent}">



	no: ${reply_view.bId}<br>
	hit: ${reply_view.bHit}<br>

	<div>name:<input type="text" name="bName" value="${reply_view.bName}"></div>
	<div>title:<input type="text" name="bTitle" value="${reply_view.bTitle}"></div>
	<div>content:<input type="text" name="bContent" value="${reply_view.bContent}"></div>
	
	<br>
	<input type="submit" value="답변">
	
	<a href="list">목록보기</a><br>
</form>
</body>
</html>