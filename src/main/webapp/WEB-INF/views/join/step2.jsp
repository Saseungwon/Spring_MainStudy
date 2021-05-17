<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/WEB-INF/inc/header.jsp"%>
	<title>회원가입 2단계</title>
</head>
<body>
	<%@include file="/WEB-INF/inc/top.jsp"%>
	
	<div class="container">
		<form:form action="step3.wow" modelAttribute="member">
			<div class="row col-md-8 col-md-offset-2">
				<div class="page-header">
					<h3>회원가입 2단계</h3>
				</div>

				<table class="table" >
					<colgroup>
						<col width="20%" />
						<col />
					</colgroup>
					<tr>
						<th>ID</th>
						<td>
							<form:input path="memId" id="text_id" cssClass="form-control input-sm"/>
							<form:errors path="memId"/>
						</td>
						<td>
							<form:button id="btn_idck" cssClass="btn btn-default">중복 체크</form:button>
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td colspan="2">
							<form:password path="memPass" cssClass="form-control input-sm"/>
							<form:errors path="memPass"/>
						</td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td colspan="2">
							<form:password path="memPassConfirm" cssClass="form-control input-sm"/>
							<form:errors path="memPassConfirm"/>
						</td>
					</tr>
					<tr class="form-group-sm">
						<th>회원명</th>
						<td colspan="2">
							<form:input path="memName" cssClass="form-control input-sm"/>
							<form:errors path="memName"/>
						</td>
					</tr>
					<tr class="form-group-sm">
						<th>이메일</th>
						<td colspan="2">
							<form:input path="memMail" id="txt_email" cssClass="form-control input-sm"/>
							<form:errors path="memMail"/>
							<form:button id="btn_email">인증 코드 전송</form:button>
							<input type="text" value="">
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="pull-left" >
								<a href="cancel" class="btn btn-sm btn-default" >
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									&nbsp;&nbsp;취 소
								</a>
							</div>
							<div class="pull-right">
								<button type="submit" id="btn_sub" class="btn btn-sm btn-primary" >
									<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> 
									&nbsp;&nbsp;다 음 
								</button>
							</div>
						</td>
					</tr>	
				</table>
			</div>
		</form:form>
	</div> <!-- END : 메인 콘텐츠  컨테이너  -->
<script type="text/javascript">
	var isExist = true;
	
	$("#text_id").on("keydown", function() {
		isExist = true;
	})

	$("#btn_idck").on("click", function() {
		event.preventDefault();
		memId = {memId:$("#text_id").val()};
		$.ajax({
			type:"POST",
			url:'<c:url value="/join/idck" />',
			dataType:'text',
			data:memId,
			success:function(data) {
				if (data == "isExist") {
					alert("이미 존재하는 아이디입니다.");
					isExist = true;
					return;
				}
				
				alert("사용할 수 있는 아이디입니다.");
				isExist = false;
			}
		})
	})
	
	$("#btn_sub").on("click", function() {
		if (isExist) {
			event.preventDefault();
			alert("아이디 중복체크 필요");
		}
	})
	
	$("#btn_email").on("click", function() {
		event.preventDefault();
		alert("인증메일을 발송했습니다. 메일을 확인해주세요.");
		var param = {email:$("#txt_email").val()};
		$.ajax({
			url: "email.wow",
			type: "post",
			dataType: "text",
			data: param,
			success: function(data) {
				alert(data);
			}
		})
	})
</script>
</body>
</html>



