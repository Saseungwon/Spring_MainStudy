<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="list"  tagdir="/WEB-INF/tags/list"%> --%>
<div class="page-header">
	<h3>
		자유게시판 - <small>글 목록</small>
	</h3>
</div>

<div class="row">
<div class="col-sm-2 col-sm-offset-7 text-right">
	<a href="regist.wow" class="btn btn-primary btn-sm">
		<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> &nbsp;새글쓰기
	</a>
</div>
</div>

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
		<c:forEach items="${results}" var="vo">
			<tr class="text-center">
				<td>${vo.boNo }</td>
				<td>${vo.boCategoryNm }</td>
				<td class="text-left"><a href="view.wow?boNo=${vo.boNo }"> ${vo.boTitle } </a></td>
				<td>${vo.boWriter }</td>
				<td>${vo.boRegDate }</td>
				<td>${vo.boHit }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>