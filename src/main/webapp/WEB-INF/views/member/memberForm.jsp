<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원가입</h3>
		<form:form action="memberRegist.wow" method="post" modelAttribute="member">
			<table class="table table-striped table-bordered">
				<tbody>
					<tr>
						<th>아이디</th>
						<td>
							<form:input path="memId" cssClass="form-control input-sm"/>
							<form:errors path="memId"/>
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<form:password path="memPass" cssClass="form-control input-sm"/>
							<form:errors path="memPass"/>
						</td>
					</tr>
					<tr>
						<th>회원명</th>
						<td>
							<form:input path="memName" cssClass="form-control input-sm"/>
							<form:errors path="memName"/>
						</td>
					</tr>
					<tr>
						<th>우편번호</th>
						<td>
							<form:input path="memZip" cssClass="form-control input-sm"/>
							<form:errors path="memZip"/>
						</td>
					</tr>
					<tr>
						<th>주소</th>
						<td>
							<form:input path="memAdd1" cssClass="form-control input-sm"/>
							<form:errors path="memAdd1"/>
						</td>
					</tr>
					<tr>
						<th>생일</th>
						<td>
							<form:input path="memBir" cssClass="form-control input-sm"/>
							<form:errors path="memBir"/>
						</td>
					</tr>
					<tr>
						<th>메일</th>
						<td>
							<form:input path="memMail" cssClass="form-control input-sm"/>
							<form:errors path="memMail"/>
						</td>
					</tr>
					<tr>
						<th>핸드폰</th>
						<td>
							<form:input path="memHp" cssClass="form-control input-sm"/>
							<form:errors path="memHp"/>
						</td>
					</tr>
					<tr>
						<th>직업</th>
						<td>
							<form:select path="memJob" cssClass="form-control input-sm">
								<form:option value="">-- 직업 선택 --</form:option>
								<form:options items="${jobList}" itemLabel="commNm" itemValue="commCd"/>
							</form:select>
							<form:errors path="memJob"/>
						</td>
					</tr>
					<tr>
						<th>취미</th>
						<td>
							<form:select path="memLike" cssClass="form-control input-sm">
								<form:option value="">-- 취미 선택 --</form:option>
								<form:options items="${hobbyList}" itemLabel="commNm" itemValue="commCd"/>
							</form:select>
							<form:errors path="memLike"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button type="submit" class="btn btn-primary">
								<span class="glyphicon glyphicon-home" aria-hidden="true"></span> &nbsp;회원가입
							</button> <a href="memberList.wow" class="btn btn-info btn-sm"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> &nbsp;목록
						</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>

</body>
</html>