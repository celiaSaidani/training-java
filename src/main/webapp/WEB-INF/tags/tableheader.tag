<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="size" required="true" type="java.lang.Integer" description="Size of a page"%>
<%@ attribute name="order" required="true" type="java.lang.String" description="Column to order"%>
<%@ attribute name="by" required="true" type="java.lang.String" description="Ascending or descending"%>
<%@ attribute name="sort" required="false" type="java.lang.Boolean" description="Sort mode"%>
<%@ attribute name="search" required="false" type="java.lang.String" description="Search case"%>

<c:set var="orderComputer" value="" />
<c:set var="orderIntroduced" value="" />
<c:set var="orderDiscontinued" value="" />
<c:set var="orderCompany" value="" />
<c:set var="byComputer" value="up" />
<c:set var="byIntroduced" value="up" />
<c:set var="byDiscontinued" value="up" />
<c:set var="byCompany" value="up" />
<c:set var="byQuery" value="up" />

<c:if test="${sort eq true}">
	<c:choose>
		<c:when test="${by eq 'down'}">
			<c:set var="byQuery" value="up" />
		</c:when>
		<c:when test="${by eq 'up'}">
			<c:set var="byQuery" value="down" />
		</c:when>
	</c:choose>
</c:if>

<c:if test="${not empty search}">
	<c:set var="search" value="search=${search}&" />
</c:if>

<c:choose>
		<c:when test="${order == 'computer.name'}">
			<c:set var="orderComputer" value="-${byQuery}" />
			<c:set var="byComputer" value="${byQuery}" />
		</c:when>
		<c:when test="${order == 'introduced'}">
			<c:set var="orderIntroduced" value="-${byQuery}" />
			<c:set var="byIntroduced" value="${byQuery}" />
		</c:when>
		<c:when test="${order == 'discontinued'}">
			<c:set var="orderDiscontinued" value="-${byQuery}" />
			<c:set var="byDiscontinued" value="${byQuery}" />
		</c:when>
		<c:when test="${order == 'company.name'}">
			<c:set var="orderCompany" value="-${byQuery}" />
			<c:set var="byCompany" value="${byQuery}" />
		</c:when>
</c:choose>

<th>Computer name <a
	href="<c:url value="/DashboardServlet?${search}order=computer.name&size=${size}&by=${byComputer}&sort=true" />"><i
		class="fa fa-sort${orderComputer} pull-right"></i></a>
</th>
<th>Introduced date <a
	href="<c:url value="/DashboardServlet?${search}order=introduced&size=${size}&by=${byIntroduced}&sort=true" />"><span
		class="fa fa-sort${orderIntroduced} pull-right"></span></a></th>
<!-- Table header for Discontinued Date -->
<th>Discontinued date <a
	href="<c:url value="/DashboardServlet?${search}order=discontinued&size=${size}&by=${byDiscontinued}&sort=true" />"><span
		class="fa fa-sort${orderDiscontinued} pull-right"></span></a></th>
<!-- Table header for Company -->
<th>Company <a
	href="<c:url value="/DashboardServlet?${search}order=company.name&size=${size}&by=${byCompany}&sort=true" />"><span
class="fa fa-sort${orderCompany} pull-right"></span></a></th>