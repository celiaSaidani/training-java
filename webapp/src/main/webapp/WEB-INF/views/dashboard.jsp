<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="Dashboard"> Application -
				Computer Database </a>
            <a class="navbar-brand btn btn-success" id="logout"
               href="/logout" onclick="$.fn.toggleEditMode();" style="float: right">log out
            </a>
            </div>
    </header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${count} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm"
						action="${pageContext.request.contextPath}/Dashboard"
						method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" value="${search}" />
						<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${pageContext.request.contextPath}/addcomputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm"
			action="${pageContext.request.contextPath}/Dashboard"
			method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th style="display: none" class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<mylib:tableheader size="${requestScope.size}" order="${requestScope.order}" by="${requestScope.by}" 
						sort="${requestScope.sort}" search="${requestScope.search}"/>
						
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computerdb" items="${requestScope.computerdb}">
						<tr>
							<td style="display: none" class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computerdb.idComp}"></td>
							<td><a
								href="editcomputer?idComp=${computerdb.idComp}"
								onclick="">${computerdb.nameComp}</a></td>
							<td>${computerdb.dateIn}</td>
							<td>${computerdb.dateOut}</td>
							<td>${computerdb.nameCompany}</td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<mylib:pagination page="${requestScope.page}"
				nbrPage="${requestScope.nbrPage}" size="${requestScope.size}"
				search="${requestScope.search}" order="${requestScope.order}"
				by="${requestScope.by}" sort="${requestScope.sort}" />
		</div>
	</footer>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>
</body>
</html>