<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>valid USer</title>
</head>
<body>
	<form:form commandName="user" action="/springprjboard/validuser/create" method="GET">
		<form:input path="userId"/><form:errors path="userId"/><br> 
		<form:input path="password"/><form:errors path="password"/><br> 
		<form:input path="name"/><form:errors path="name"/><br>
		<form:input path="email"/><form:errors path="email"/><br>
		<input type="submit" value="submit"><br>
	</form:form>

</body>
</html>