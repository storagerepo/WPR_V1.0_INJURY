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
                    <form role="form" action="j_spring_security_check" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password" value="">
                            </div>
                             <c:if test="${failed=='true'}"><div style="color:#FF0000"> Invalid Username or Password</div> </c:if>
                            
                            <!-- Change this to a button or input when using this as a form -->
                            <input type="submit" value="Login" class="btn btn-success pull-right"> 
                         </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>