<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="searchVO" required="true"  type="com.study.common.vo.PagingVO"%>
<%@ attribute name="formPage" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- START : 목록건수 및 새글쓰기 버튼  -->
		<div class="row" style="margin-bottom: 10px;">
			<div class="col-sm-3 form-inline">
				전체 ${searchVO.totalRowCount }건 조회
				<select id="id_rowSizePerPage" name="rowSizePerPage" class="form-control input-sm">
					<c:forEach var="i" begin="10" end="50" step="10">
						<option value="${i}" ${i eq searchVO.rowSizePerPage ? "selected='selected'" : "" } >${i }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2 col-sm-offset-7 text-right">
				<a href="${formPage }" class="btn btn-primary btn-sm"> <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> &nbsp;새글쓰기
				</a>
			</div>
		</div>
		<!-- END : 목록건수 및 새글쓰기 버튼  -->