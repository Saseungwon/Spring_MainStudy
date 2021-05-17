<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="searchVO" required="true" type="com.study.common.vo.PagingVO"  %>
<%@ attribute name="linkPage" required="false"  %><!-- 기본은 String -->
<%@ attribute name="totalPageCount" required="false" type="java.lang.Integer"  %>

${searchVO}

<%
	
	searchVO.setTotalPageCount(totalPageCount);

%>
<br>
${searchVO}
${linkPage}