<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<tiles:insertAttribute name="header" />
	<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<tiles:insertAttribute name="top" />
	<div class="container">
		<tiles:insertAttribute name="body" />
	</div>
	<!-- .container -->
</body>
</html>