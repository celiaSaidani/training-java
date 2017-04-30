<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="order" required="true" type="java.lang.String" description="Column to order"%>
<%@ attribute name="by" required="true" type="java.lang.String" description="Ascending or descending"%>

<c:set var="orderComputer" value="" />
<c:set var="orderIntroduced" value="" />
<c:set var="orderDiscontinued" value="" />
<c:set var="orderCompany" value="" />
<c:set var="byComputer" value="up" />
<c:set var="byIntroduced" value="up" />
<c:set var="byDiscontinued" value="up" />
<c:set var="byCompany" value="up" />

<c:choose>
		<c:when test="${order == 'computer'}">
			<c:set var="orderComputer" value="-${by}" />
			<c:set var="byComputer" value="${by}" />
		</c:when>
		<c:when test="${order == 'introduced'}">
			<c:set var="orderIntroduced" value="-${by}" />
			<c:set var="byIntroduced" value="${by}" />
		</c:when>
		<c:when test="${order == 'discontinued'}">
			<c:set var="orderDiscontinued" value="-${by}" />
			<c:set var="byDiscontinued" value="${by}" />
		</c:when>
		<c:when test="${order == 'company'}">
			<c:set var="orderCompany" value="-${by}" />
			<c:set var="byCompany" value="${by}" />
		</c:when>
</c:choose>


<th>Computer name <a
	href="<c:url value="/DashboardServlet?order=computer.name&by=${byComputer}" />"><i
		class="fa fa-sort${orderComputer} pull-right"></i></a>
</th>
<th>Introduced date <a
	href="<c:url value="/DashboardServlet?order=introduced&by=${byIntroduced}" />"><span
		class="fa fa-sort${orderIntroduced} pull-right"></span></a></th>
<!-- Table header for Discontinued Date -->
<th>Discontinued date <a
	href="<c:url value="/DashboardServlet?order=discontinued&by=${byDiscontinued}" />"><span
		class="fa fa-sort${orderDiscontinued} pull-right"></span></a></th>
<!-- Table header for Company -->
<th>Company <a
	href="<c:url value="/DashboardServlet?order=company.name&by=${byCompany}" />"><span
class="fa fa-sort${orderCompany} pull-right"></span></a></th>