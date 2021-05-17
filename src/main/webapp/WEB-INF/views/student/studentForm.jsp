<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>

<form action="studentRegist" method="post">
				<input type="text" name="stdName" value="">
				<input type="text" name="stdSex" value="">
				<input type="date" name="stdBirth" value="">
	<input type="submit" value="등록">
</form>






</body>
</html>