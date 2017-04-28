<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" description="Current page"%>
<%@ attribute name="count" required="true" type="java.lang.Integer" description="Total number of rows"%>
<%@ attribute name="size" required="true" type="java.lang.Integer" description="Size a page"%>
<%@ attribute name="search" required="true" type="java.lang.String" description="name search"%>

<fmt:parseNumber var="nbrPage" integerOnly="true" type="number" value="${count/size}" />
<c:if test="${count % size != 0}">
	<c:set var="nbrPage" value="${nbrPage+1}" />
</c:if>
<c:if test="${not empty search}">
   		 <c:set var="search" value="search=${search}&" />
	</c:if>
	
<ul class="pagination">
	<li><a href="DashboardServlet?page=1&size=${size}" aria-label="Previous"> <span
			aria-hidden="true">&laquo;</span>
	</a></li>
	<c:choose>
		<c:when test="${nbrPage > 5}">
			<c:choose>
				<c:when test="${page < 4}">
					<c:forEach begin="1" end="4" varStatus="nestedLoop">
						<li
							<c:if test="${nestedLoop.index == page}"> class="active"</c:if>><a
							href="DashboardServlet?${search}page=${nestedLoop.index}&size=${size}">${nestedLoop.index}</a></li>
					</c:forEach>
					<li><a href="DashboardServlet?${search}page=${page+1}&size=${size}">...</a></li>
				</c:when>
				<c:when test="${page > nbrPage-4}">
					<li><a href="DashboardServlet?${search}page=${page-1}&size=${size}">...</a></li>
					<c:forEach begin="${nbrPage-4}" end="${nbrPage}"
						varStatus="nestedLoop">
						<li
							<c:if test="${nestedLoop.index == page}"> class="active"</c:if>><a
							href="DashboardServlet?${search}page=${nestedLoop.index}&size=${size}">${nestedLoop.index}</a></li>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<li><a href="DashboardServlet?${search}page=${page-1}&size=${size}">...</a></li>
					<c:forEach begin="${page-1}" end="${page+1}" varStatus="nestedLoop">
						<li
							<c:if test="${nestedLoop.index == page}"> class="active"</c:if>><a
							href="DashboardServlet?${search}page=${nestedLoop.index}&size=${size}">${nestedLoop.index}</a></li>
					</c:forEach>
					<li><a href="DashboardServlet?${search}page=${page+1}&size=${size}">...</a></li>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${nbrPage}" varStatus="loop">
				<li <c:if test="${loop.index == page}"> class="active"</c:if>><a
					href="DashboardServlet?${search}page=${loop.index}&size=${size}">${loop.index}</a></li>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<li><a href="DashboardServlet?${search}page=${nbrPage}&size=${size}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
	</a></li>
</ul>
<div class="btn-group btn-group-sm pull-right" role="group">
	<a type="button" class="btn btn-default<c:if test='${size == 10}'> active</c:if>" href="DashboardServlet?page=1&size=10">10</a>
	<a type="button" class="btn btn-default<c:if test='${size == 50}'> active</c:if>"href="DashboardServlet?page=1&size=50">50</a>
	<a type="button" class="btn btn-default<c:if test='${size == 100}'> active</c:if>"href="DashboardServlet?page=1&size=100">100</a>
</div>