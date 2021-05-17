<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>

	<a href="<%=request.getContextPath()%>/student/studentForm">등록</a>

	<table>
		<c:forEach items="${studentList }" var="student">
			<tr>
				<td>${student.stdNo } </td>
				<td>
				<a href="<%=request.getContextPath()%>/student/studentView?studentNumber=${student.stdNo }">${student.stdName }</a>
				</td>
				<td>${student.stdSex }</td>
				<td>${student.stdBirth }</td>
			</tr>

		</c:forEach>
	</table>




</body>
</html>