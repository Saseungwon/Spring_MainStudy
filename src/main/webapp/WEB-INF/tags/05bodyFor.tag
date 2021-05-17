<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="scriptless" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="count" required="true" type="java.lang.Integer" %>

<!--  여기 건들지말고-->  
<c:forEach begin="1" end="${count }" var="i">
	<jsp:doBody />
</c:forEach>