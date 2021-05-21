<%@ tag language="java" pageEncoding="UTF-8" dynamic-attributes="ib"%> <!-- Map형태로 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 태그디렉티브 attribute로 지정해주지않은 값들은 전부
	dynamic-attributes로 들어갑니다 
 -->
 <%@attribute name="name" required="true" %>
 
 <select name=${name }>
 	 <c:forEach items="${ib }" var="option">
 	 	<option value="${option.key }">${option.value } </option>
 	 </c:forEach>
 </select>
 
 
 



