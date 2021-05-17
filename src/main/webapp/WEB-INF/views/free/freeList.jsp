<%@page import="com.study.code.vo.CodeVO"%>
<%@page import="com.study.code.service.CommonCodeServiceImpl"%>
<%@page import="com.study.code.service.ICommonCodeService"%>
<%@page import="com.study.free.service.FreeBoardServiceImpl"%>
<%@page import="com.study.free.service.IFreeBoardService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.study.free.vo.FreeBoardVO"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="list"  tagdir="/WEB-INF/tags/list"%>

<%
request.setCharacterEncoding("utf-8");	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/inc/header.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/inc/top.jsp"%>






<div class="container">
	<div class="page-header">
		<h3>자유게시판 - <small>글 목록</small></h3>
	</div>
${searchVO }



	<!-- 검색 폼  -->
	<list:search searchVO="${searchVO }" 
	actionPage="freeList.wow" 
	searchType='${{"T":"제목",
	"W":"작성자","C":"내용"}}' 
	searchCategory='${{"분류":cateList}}' >
	</list:search>

	<list:listCount searchVO="${searchVO }" formPage="freeForm.wow"></list:listCount>



	<table class="table table-striped table-bordered table-hover">
	<colgroup>
		<col width="10%" />
		<col width="15%" />
		<col />
		<col width="10%" />
		<col width="15%" />
		<col width="10%" />
	</colgroup>
	<thead>
		<tr>
			<th>글번호</th>
			<th>분류</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
	</thead>	
	<tbody>
	<c:forEach items="${freeList }" var="free">
			<tr class="text-center">
				<td>${free.boNo }</td>
				<td>${free.boCategoryNm }</td>
				<td class="text-left">
					<a href="freeView.wow?boNo=${free.boNo }">
						${free.boTitle }
					</a>
				</td>
				<td>${free.boWriter }</td>
				<td>${free.boRegDate }</td>
				<td>${free.boHit }</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	
	<list:paging searchVO="${searchVO }" linkPage="freeList.wow"></list:paging>
	
	
</div><!-- container --> 
</body>

<script src="<%=request.getContextPath() %>/resource/js/list.js"></script>
    




</html>






