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




<form action="studentModify" method="post">
<table>
			<tr>
				<td>${student.stdNo } <input type="hidden" name="stdNo" value="${student.stdNo }"></td>
				<td><input type="text" name="stdName" value="${student.stdName }"></td>
				<td><input type="text" name="stdSex" value="${student.stdSex }"></td>
				<td><input type="date" name="stdBirth" value="${student.stdBirth }"></td>
			</tr>	
	</table>
	<input type="submit" value="수정">
</form>





</body>
</html>