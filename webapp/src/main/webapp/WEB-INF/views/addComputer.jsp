<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css" />
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />

</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand"  href="${pageContext.request.contextPath}/DashboardServlet"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form id="Formulaire" role="form" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="nameComp">Computer name</label>
                                <input  class="form-control" id="nameComp" name="nameComp" placeholder="Computer name" >
                            </div>
                            <div class="form-group">
                                <label  for="dateIn" class="control-label">Introduced date</label>
                                <div class="date">
                                    <div class="input-group input-append date" id="intoducedPicker">
                                        <input type="text" class="form-control" name="dateIn" id="dateIn"/>
                                        <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dateOut">Discontinued date</label>
                                <div class="date">
                                    <div class="input-group input-append date" id="discontinuedPicker">
                                        <input type="text" class="form-control" name="dateOut" id="dateOut"/>
                                        <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="company">Company</label>
                                <select class="form-control" id="company" name="company" >
                                    <option></option>
                                   <c:forEach var="company" items="${requestScope.company}">
                                    <option value=${company.idCompany}>${company.nameCompany}</option>
                                 	</c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary" >
                            or
                            <a class="btn btn-default" onclick=" return confirm('etes vous sur de vouloir quitter')">Cancel</a>
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
  			 $('#Formulaire').bootstrapValidator('revalidateField', 'introduced');
				});
        
    $('#discontinuedPicker')
        .datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (e) {
  			 $('#Formulaire').bootstrapValidator('revalidateField', 'discontinued');
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
                      },
                        regex: /^\\d+$/
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