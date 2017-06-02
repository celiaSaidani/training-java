<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                    </div>
                    <h1>Edit Computer</h1>

                    <form id="Formulaire" action="editcomputer" method="POST">
                        <input type="hidden" value="${computerdb.idComp}" id="idComp" name="idComp"/> <!-- TODO: Change this value with the computer id -->
                        <th>${computerdb.idComp}</th>
                        <fieldset>
                            <div class="form-group">
                                <label for="nameComp">Computer name</label>
                                <input type="text" class="form-control" id="nameComp" name="nameComp" value="${computerdb.nameComp}">
                            </div>
                            <div class="form-group">
                                <label for="dateIn">Introduced date</label>
                                <div class="date">
                                    <div class="input-group input-append date" id="intoducedPicker">
                                        <input type="text" class="form-control" name="dateIn" id="dateIn" value="${computerdb.dateIn}"/>
                                        <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dateOut">Discontinued date</label>
                                <div class="date">
                                    <div class="input-group input-append date" id="discontinuedPicker">
                                        <input type="text" class="form-control" name="dateOut" id="dateOut" value="${computerdb.dateOut}"/>
                                        <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </div>
                            </div>
                             <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="company" >
                                   <c:forEach var="company" items="${requestScope.company}">
                                    <option value=${company.idCompany} 
                                    	<c:if test="${company.idCompany == computerdb.idCompany}">selected</c:if>
                                    	>${company.nameCompany}</option>
                                 	</c:forEach>
                                </select>
                            </div> 
                           
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
<script src="js/jquery.min.js"></script>
<script src="/js/jbootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.5.3/js/bootstrapValidator.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
    $('#intoducedPicker')
        .datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (e) {
  			 $('#Formulaire').bootstrapValidator('revalidateField', 'dateIn');
				});
        
    $('#discontinuedPicker')
        .datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (e) {
  			 $('#Formulaire').bootstrapValidator('revalidateField', 'dateOut');
				});
        
    $("#Formulaire").bootstrapValidator({
	    		feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
                    nameComp: {
                	validators: {
                  	notEmpty: {
                    	message: 'The name is required'
                      }
                     }
	                },
                    dateIn: {
									validators: {
										date: {
                    	format: 'YYYY-MM-DD',
                      message: 'The value is not a valid date'
                      }
	                	}
	            	},
                    dateOut: {
									validators: {
										date: {
											format: 'YYYY-MM-DD',
											message: 'The value is not a valid date :3'
                    }
                	}
	            	}
	            }
	    	});
});
</script>
</html>