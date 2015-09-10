<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>no</td>
			<td>name</td>
			<td>title</td>
			<td>date</td>
			<td>hit</td>
		</tr>
		<c:forEach items="${list}" var="dto">
			<tr>
				<td><c:out value="${dto.bId}"></c:out></td>
				<td><c:out value="${dto.bName}"></c:out></td>
				<td>
					<c:forEach begin="1" end="${dto.bIndent }">-</c:forEach>
					<a href="content_view?bId=${dto.bId}"><c:out value="${dto.bTitle}"></c:out></a>
				</td>
				<td><c:out value="${dto.bDate}"></c:out></td>
				<td><c:out value="${dto.bHit}"></c:out></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5"><a href="write_view">write</a> </td>
		</tr>
	</table>

</body>
</html>