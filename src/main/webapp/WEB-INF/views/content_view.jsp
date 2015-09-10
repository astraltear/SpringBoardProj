<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<form action="modify" method="post">
<input type="hidden" name="bId" value="${content_view.bId}">
	no: ${content_view.bId}<br>
	hit: ${content_view.bHit}<br>

	<div>name:<input type="text" name="bName" value="${content_view.bName}"></div>
	<div>title:<input type="text" name="bTitle" value="${content_view.bTitle}"></div>
	<div>content:<input type="text" name="bContent" value="${content_view.bContent}"></div>
	
	<br>
	<input type="submit" value="수정">
	
	<a href="list">목록보기</a>
	<a href="delete">삭제</a> 
	<a href="reply">답변</a>
</form>
</body>
</html>