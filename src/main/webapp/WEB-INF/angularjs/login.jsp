<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="resources/components/bootstrap/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="styles/sb-admin-2.css">
    <link rel="stylesheet" href="styles/timeline.css">
    <link rel="stylesheet" href="resources/components/metisMenu/dist/metisMenu.min.css">
    <link rel="stylesheet" href="resources/components/angular-loading-bar/build/loading-bar.min.css">
    <link rel="stylesheet" href="resources/components/font-awesome/css/font-awesome.min.css" type="text/css">
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                 <c:if test="${not empty param['error']}">
                            <div style="color:#FF0000;padding:10px;"><i class="fa fa-exclamation-triangle"></i>&nbsp;${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
                             </c:if>
                    <form role="form" action="j_spring_security_check" method="post">
                    <div id="sessionout" style="color:#FF0000">Your Session has been expired. Please login again!<br/><br/></div>
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password" value="">
                            </div>
                                             
                            <!-- Change this to a button or input when using this as a form -->
                            <input type="submit" value="Login" class="btn btn-success pull-right"> 
                         </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

if(location.search=='?sessionout'){
	document.getElementById('sessionout').style.display = 'inline';
}else{
	document.getElementById('sessionout').style.display = 'none';
}
	/* element.style.display = 'none';  */
</script>