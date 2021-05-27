<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="page-header">
	<h3>
		자유게시판 - <small>글 등록</small>
	</h3>
</div>

<form:form action="regist.wow" method="post" enctype="multipart/form-data" modelAttribute="board">
	<table class="table table-striped table-bordered">
		<colgroup>
			<col width="20%" />
			<col />
		</colgroup>
		<tr>
			<th>제목</th>
			<td><form:input path="boTitle" cssClass="form-control input-sm" /> <form:errors path="boTitle" /></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><form:input path="boWriter" cssClass="form-control input-sm" /> <form:errors path="boWriter" /></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><form:password path="boPass" cssClass="form-control input-sm" /> <form:errors path="boPass" /></td>
		</tr>
		<tr>
			<th>IP</th>
			<td><%=request.getRemoteAddr()%></td>
		</tr>
		<tr>
			<th>첨부파일
				<button type="button" id="id_btn_new_file">추가</button>
			</th>
			<td class="file_area">
				<div class="form-inline">
					<input type="file" name="boFiles" class="form-control">
					<button type="button" class="btn_delete btn btn-sm">삭제</button>
				</div>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><form:textarea path="boContent" cssClass="form-control" /> <form:errors path="boContent" /></td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="pull-left">
					<a href="list.wow" class="btn btn-default btn-sm"> <span class="glyphicon glyphicon-list" aria-hidden="true"></span> &nbsp;&nbsp;목록
					</a>
				</div>
				<div class="pull-right">
					<button type="submit" class="btn btn-sm btn-primary">
						<span class="glyphicon glyphicon-save" aria-hidden="true"></span> &nbsp;&nbsp;저장
					</button>
				</div>
			</td>
		</tr>
	</table>
</form:form>
<script>
	$('#id_btn_new_file').click(function(){
		$('.file_area').append('<div class="form-inline">'
			+ '<input type="file" name="boFiles" class="form-control">'
			+ ' <button type="button" class="btn_delete btn btn-sm">삭제</button>'
			+ '</div>');
	}); // #id_btn_new_file.click
	
	// 상위객체를 통해 이벤트 위임
	$('.file_area').on('click','.btn_delete', function(){
		$(this).closest('div').remove();
	});
</script>
