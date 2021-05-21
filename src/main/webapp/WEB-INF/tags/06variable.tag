<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ variable scope="NESTED" name-given="sum" variable-class="java.lang.Integer"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="begin" required="true" %>
<%@attribute name="end" required="true" %>



<c:forEach begin="${begin }" end="${end }" var="i">
	<c:set var="sum" value="${sum+i }"></c:set>
</c:forEach>
<jsp:doBody/>



