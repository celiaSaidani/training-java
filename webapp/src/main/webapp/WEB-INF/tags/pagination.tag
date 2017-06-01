<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" description="Current page"%>
<%@ attribute name="nbrPage" required="false" type="java.lang.String" description="number of page"%>
<%@ attribute name="size" required="true" type="java.lang.Integer" description="Size a page"%>
<%@ attribute name="search" required="false" type="java.lang.String" description="Search case"%>
<%@ attribute name="order" required="false" type="java.lang.String" description="Column to order"%>
<%@ attribute name="by" required="false" type="java.lang.String" description="Ascending or descending"%>
<%@ attribute name="sort" required="false" type="java.lang.Boolean" description="Sort mode"%>


<c:if test="${not empty search}">
	<c:set var="search" value="search=${search}&" />
</c:if>

<c:if test="${sort eq true}">
	<c:set var="sortQuery" value="&sort=${sort}" />
	<c:if test="${not empty order and not empty by}">
		<c:set var="orderby" value="&order=${order}&by=${by}" />
	</c:if>
</c:if>

<ul class="pagination">
	<li><a href="dashboard?${search}page=1&size=${size}${orderby}${sortQuery}" aria-label="Previous"> <span
			aria-hidden="true">&laquo;</span>
	</a></li>
	<c:choose>
		<c:when test="${nbrPage > 5}">
			<c:choose>
				<c:when test="${page < 4}">
					<c:forEach begin="1" end="4" varStatus="nestedLoop">
						<li
							<c:if test="${nestedLoop.index == page}"> class="active"</c:if>><a
							href="dashboard?${search}page=${nestedLoop.index}&size=${size}${orderby}${sortQuery}">${nestedLoop.index}</a></li>
					</c:forEach>
					<li><a href="dashboard?${search}page=${page+1}&size=${size}${orderby}${sortQuery}">...</a></li>
				</c:when>
				<c:when test="${page > nbrPage-4}">
					<li><a href="dashboard?${search}page=${page-1}&size=${size}${orderby}${sortQuery}">...</a></li>
					<c:forEach begin="${nbrPage-4}" end="${nbrPage}"
						varStatus="nestedLoop">
						<li
							<c:if test="${nestedLoop.index == page}"> class="active"</c:if>><a
							href="dashboard?${search}page=${nestedLoop.index}&size=${size}${orderby}${sortQuery}">${nestedLoop.index}</a></li>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<li><a href="dashboard?${search}page=${page-1}&size=${size}${orderby}${sortQuery}">...</a></li>
					<c:forEach begin="${page-1}" end="${page+1}" varStatus="nestedLoop">
						<li
							<c:if test="${nestedLoop.index == page}"> class="active"</c:if>><a
							href="dashboard?${search}page=${nestedLoop.index}&size=${size}${orderby}${sortQuery}">${nestedLoop.index}</a></li>
					</c:forEach>
					<li><a href="dashboard?${search}page=${page+1}&size=${size}${orderby}${sortQuery}">...</a></li>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${nbrPage}" varStatus="loop">
				<li <c:if test="${loop.index == page}"> class="active"</c:if>><a
					href="dashboard?${search}page=${loop.index}&size=${size}${orderby}${sortQuery}">${loop.index}</a></li>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<li><a href="dashboard?${search}page=${nbrPage}&size=${size}${orderby}${sortQuery}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
	</a></li>
</ul>
<div class="btn-group btn-group-sm pull-right" role="group">
	<a type="button" class="btn btn-default<c:if test='${size == 10}'> active</c:if>" href="dashboard?${search}page=1&size=10${orderby}${sortQuery}">10</a>
	<a type="button" class="btn btn-default<c:if test='${size == 50}'> active</c:if>"href="dashboard?${search}page=1&size=50${orderby}${sortQuery}">50</a>
	<a type="button" class="btn btn-default<c:if test='${size == 100}'> active</c:if>"href="dashboard?${search}page=1&size=100${orderby}${sortQuery}">100</a>
</div>