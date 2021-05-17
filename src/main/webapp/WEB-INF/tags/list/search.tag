<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="searchVO" required="true"   type="com.study.common.vo.PagingVO"%>
<%@attribute name="actionPage"  required="true"%>
<%@attribute name="searchType" required="true" type="java.util.Map"%>

<%@tag dynamic-attributes="dynamic" %>







	<!-- START : 검색 폼  -->
		<div class="panel panel-default collapse in" id="id_search_area">
			<div class="panel-body">
				<form name="search" action="${actionPage }" method="post" class="form-horizontal">
					<input type="hidden" name="curPage" value="${searchVO.curPage }"> <input type="hidden" name="rowSizePerPage" value="${searchVO.rowSizePerPage }">
					<div class="form-group">
						<label for="id_searchType" class="col-sm-2 control-label">검색</label>
						<div class="col-sm-2">
							<select id="id_searchType" name="searchType" class="form-control input-sm">
								<c:forEach  items="${searchType }" var="option">
									<option value="${option.key }" ${searchVO.searchType eq option.key ? "selected='selected'" : "" }>${option.value }</option>							
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<input type="text" name="searchWord" class="form-control input-sm" value="${searchVO.searchWord }" placeholder="검색어">
						</div>
						<div>
							<c:forEach items="${dynamic }" var="option">
								<c:forEach items="${option.value }" var="i"> 
							<label for="id_${option.key }" class="col-sm-1 col-sm-offset control-label">${i.key }</label>
							<div class="col-sm-2">
								<select id="id_${option.key }" name="${option.key }" class="form-control input-sm">
									<option value="">-- 전체 --</option>
									<c:forEach items="${i.value}" var="code">
										<option value="${code.commCd}" ${searchVO[option.key] eq code.commCd ? "selected='selected'" : "" }>${code.commNm}</option>
									</c:forEach>
								</select>
							</div>
							</c:forEach>
							</c:forEach>
							
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-9 text-right">
							<button type="button" id="id_btn_reset" class="btn btn-sm btn-default">
								<i class="fa fa-sync"></i> &nbsp;&nbsp;초기화
							</button>
						</div>
						<div class="col-sm-1 text-right">
							<button type="submit" class="btn btn-sm btn-primary ">
								<i class="fa fa-search"></i> &nbsp;&nbsp;검 색
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- END : 검색 폼  -->