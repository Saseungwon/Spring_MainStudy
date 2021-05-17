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
 
 
 
 <table>
		
			<tr>
				<td>${student.stdNo }</td>
				<td>${student.stdName }</td>
				<td>${student.stdSex }</td>
				<td>${student.stdBirth }</td>
			</tr>

	
	</table>
 
<a href="<%=request.getContextPath()%>/student/studentEdit?studentNumber=${student.stdNo }"> 수정</a>
 <a href="<%=request.getContextPath()%>/student/studentDelete?studentNumber=${student.stdNo }"> 삭제</a>
 
 
</body>
</html>