<%@page import="com.study.code.vo.CodeVO"%>
<%@page import="com.study.code.service.CommonCodeServiceImpl"%>
<%@page import="com.study.code.service.ICommonCodeService"%>
<%@page import="com.study.member.service.MemberServiceImpl"%>
<%@page import="com.study.member.service.IMemberService"%>
<%@page import="com.study.member.vo.MemberVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="list" tagdir="/WEB-INF/tags/list" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>


 <div class="container">	
	<h3>회원목록</h3>	
		
		
	<!-- 검색 폼  -->		
	<list:search searchVO="${searchVO }" 
	actionPage="memberList.wow" 
	searchType='${{"HP":"핸드폰번호",
	"ID":"아이디","NM":"이름"}}' 
	searchJob='${{"직업":jobList}}' 
	searchLike='${{"취미":hobbyList} }'>
	</list:search>
		
	<!-- list 수  -->
	<list:listCount searchVO="${searchVO }" formPage="memberForm.wow"></list:listCount>
		
	
	<table class="table table-striped table-bordered">
	<caption class="hidden">회원목록 조회</caption>
	<colgroup>
		<col style="width: 15%" />
		<col />
		<col style="width: 20%" />
		<col style="width: 20%" />
		<col style="width: 15%" />
		<col style="width: 15%" />
	</colgroup>
	<thead>
		<tr>
			<th>ID</th>
			<th>회원명</th>
			<th>HP</th>
			<th>생일</th>
			<th>직업</th>
			<th>마일리지</th>
		</tr>
	</thead>
	<tbody>
			<c:forEach items="${memberList }" var="member">
			<tr>
				<td>${member.memId }</td>
				<td><a href="memberView.wow?memId=${member.memId }">
				${member.memName }</a></td>
				<td>${member.memHp }</td>
				<td>${member.memBir }</td>
				<td>${member.memJobNm }</td>
				<td>${member.memMileage }</td>
			</tr>
			</c:forEach>
	</tbody>			
	</table>
	
	
	<!-- 하단 글 번호  -->
	<list:paging searchVO="${searchVO }" linkPage="memberList.wow"></list:paging>
	
</div><!-- container -->
</body>
<script src="<%=request.getContextPath() %>/resource/js/list.js"></script>



</html>