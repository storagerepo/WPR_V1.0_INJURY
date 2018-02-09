<html>
 
<head>
	<title>Crash Reports | crashreportsonline.com</title>
	 <meta name="description" content="Crash reports online contains accident reports of Ohio which is easy to access, download and get Lightning fast reports upon request.">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<link rel="icon" href="resources/images/favicon.ico" type="image/gif" sizes="16x16">
	<link rel="stylesheet" href="resources/components/bootstrap/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="styles/sb-admin-2.css">
	<link rel="stylesheet" href="styles/timeline.css">
	<link rel="stylesheet" href="resources/components/metisMenu/dist/metisMenu.min.css">
	<link rel="stylesheet" href="resources/components/angular-loading-bar/build/loading-bar.min.css">
	<link rel="stylesheet" href="resources/components/font-awesome/css/font-awesome.min.css" type="text/css">
	<script src="resources/components/jquery/dist/jquery.min.js"></script>
	<style media="screen" type="text/css">
	html,
	body {
		margin:0;
		padding:0;
		height:100%;
		background: url(resources/images/login-back-1.jpg) no-repeat fixed;
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
	    overflow-y:hidden;
	}
	#container {
		min-height:100%;
		position:relative;
	}
	#header h2{
		padding:20px 40px;
		background:rgba(0, 77, 153,0.9);
		color:rgba(255, 255, 255,1);
		width:60%;
		font-family: "Palatino Linotype", "Book Antiqua", Palatino, serif;
	}
	#body {
		padding:10px;
		/* padding-bottom:60px; */	/* Height of the footer */
	}
	#footer {
		position:absolute;
		bottom:0;
		width:100%;
		height:50px;			/* Height of the footer */
		background:rgba(255, 255, 255,0.9);
	}
	#footer p {
		text-align:center;
		color:rgba(0, 51, 102,1);
		padding-top:7px;
		font-size:12px
	}
	.panel-override{
		border-radius:0!important;
		border:0!important;
		background:rgba(255, 255, 255,0.7)!important;
	}
	
	.panel-override .panel-heading{
		border-radius:0!important;
		border:0!important;
		background:rgba(0, 77, 153,0.9)!important;
		color:rgba(255, 255, 255,1)!important;
		padding:20px!important;
	}
	
	.panel-override .form-control{
		border-radius:0!important;
		border:1px solid rgba(0, 0, 0,0.2)!important;
		padding:10px!important;
		height:40px!important;
		line-height:19px!important;
		box-shadow: none;
	}
	
	.btn-login{
		width:100%!important;
		border-radius:0!important;
		padding:10px!important;
		border:1px solid rgba(0, 77, 153,0.9)!important;
		background:rgba(0, 77, 153,0.9)!important;
		color:#fff!important;
	}
	.btn-login:hover{
		border:1px solid rgba(0, 51, 102,1)!important;
		background:rgba(0, 51, 102,1)!important;
	}
	
	.tag-color-white{
		color:rgba(0, 51, 102,1)!important;
	}
	
	.no-padding{
		padding:0 !important;
	}
	
	</style>
</head>

<div id="container">
 <div class="col-md-12 no-padding" id="callcenter">
	<div id="header" style="width:100%!important;">
		<h2>Lightning fast reports upon request!</h2>
		
	</div>
	<div id="body">
		<div class="col-md-2"><img src="resources/images/logo_crashreportsonline.png" width="180px;" height="150px" style="border-radius: 70px;"/></div>
        <div class="col-md-4" style="margin-top:50px;">
	        <div>
		            <div class="panel panel-default panel-override">
		                <div class="panel-heading">
		                    <h3 class="panel-title">Please Sign In</h3>
		                </div>
		                <div class="panel-body">
		                 <c:if test="${not empty param['error']}">
		                            <div style="color:#FF0000;"><i class="fa fa-exclamation-triangle"></i>&nbsp;${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}<br/><br/></div>
		                             </c:if>
		                    <form role="form" action="j_spring_security_check" method="post">
		                    <div class="sessionout" style="color:#FF0000">Your Session has been expired. Please login again!<br/><br/></div>
		                        <fieldset>
		                            <div class="form-group">
		                                <input class="form-control input-md" placeholder="Username" name="username" id="username" type="text" autofocus>
		                                <span style="color:#FF0000" id="username_error"></span>
		                            </div>
		                            <div class="form-group">
		                                <input class="form-control input-md" placeholder="Password" name="password" id="password" type="password" value="">
		                                <span style="color:#FF0000" id="password_error"></span>
		                            </div>
		                                             
		                            <!-- Change this to a button or input when using this as a form -->
		                            <input type="submit" onclick="return checkValidation()" value="Login" class="btn btn-primary btn-login pull-right"> 
		                         </fieldset>
		                    </form>
		                </div>
		            </div>
	            </div>
        </div>
		<div class="col-md-7"></div>
	</div>
	</div>
	<div class="col-md-12 auto">
		<div id="body">
		<div class="col-md-12"><div class="col-md-10"></div><div class="col-md-2 pull-right"><img src="resources/images/logo_crashreportsonline1.png" width="180px;" height="180px" style="border-radius: 90px;"/></div></div>
		<div class="col-md-12" style="margin-top:-40px;">
		<div class="col-md-8"></div>
        <div class="col-md-4" style="margin-top:50px;">
	        <div>
		            <div class="panel panel-default panel-override">
		                <div class="panel-heading">
		                    <h3 class="panel-title">Please Sign In</h3>
		                </div>
		                <div class="panel-body">
		                 <c:if test="${not empty param['error']}">
		                            <div style="color:#FF0000;"><i class="fa fa-exclamation-triangle"></i>&nbsp;${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}<br/><br/></div>
		                             </c:if>
		                    <form role="form" action="j_spring_security_check" method="post">
		                    <div class="sessionout" style="color:#FF0000">Your Session has been expired. Please login again!<br/><br/></div>
		                        <fieldset>
		                            <div class="form-group">
		                                <input class="form-control input-md" placeholder="Username" name="username" id="usernameauto" type="text" autofocus>
		                                <span style="color:#FF0000" id="usernameauto_error"></span>
		                            </div>
		                            <div class="form-group">
		                                <input class="form-control input-md" placeholder="Password" name="password" id="passwordauto" type="password" value="">
		                                <span style="color:#FF0000" id="passwordauto_error"></span>
		                            </div>
		                                             
		                            <!-- Change this to a button or input when using this as a form -->
		                            <input type="submit" onclick="return checkValidationAuto()" value="Login" class="btn btn-primary btn-login pull-right"> 
		                         </fieldset>
		                    </form>
		                </div>
		            </div>
	            </div>
        </div>
		</div>
	</div>
	</div> 
	<div class="col-md-12 auto no-padding">
	<div id="header" style="width:100%!important;">
		<h2 id="header-h2">Lightning fast reports upon request!</h2>
		
	</div>
	</div>
	<div id="footer">
		<!-- Footer start -->
		<p>Copyright &copy; 2018 - 2020 &middot; All Rights Reserved &middot; <a class="tag-color-white" target="#blank" href="http://www.deemsysinc.com/" >Deemsys Inc</a></p>
		<div style="margin-top: -10px !important;font-size: 16px !important;" class="pull-right">Register at <a href="https://www.crashreportsonline.com" target="_blank">https://www.crashreportsonline.com</a></div>
		<!-- Footer end -->
	</div>
</div>

<script>

// Get Hostname and Url to change the content of page
var hostname=window.location.hostname;
var url=hostname.split(".");

if(url[0]=='auto'){
	$(".auto").css({'display':'block'});
	$("#callcenter").css({'display':'none'});
	$("#header-h2").css({'border':'2px solid white','border-left':'none'});
	$("body").css({ 'background-image': 'url("resources/images/auto -dealers-login-back.jpg")' });
}else{
	$(".auto").css({'display':'none'});
	$("#callcenter").css({'display':'block'});
}

if(location.search=='?sessionout'){
	$('.sessionout').css({'display':'block'});
}else{
	$('.sessionout').css({'display':'none'});
}


function checkValidation(){
	var username=document.getElementById("username").value;
	var password=document.getElementById("password").value;

	document.getElementById("username_error").innerText="";
	document.getElementById("password_error").innerText="";
	
	var error=false;
	if(username==""){
		error=true;
		document.getElementById("username_error").innerText="Please Enter Username";
	}
	if(password==""){
		error=true;
		document.getElementById("password_error").innerText="Please Enter Password";
	}
	if(error){
		return false;
	}else{
		return true;
	}
}

function checkValidationAuto(){
	var username=document.getElementById("usernameauto").value;
	var password=document.getElementById("passwordauto").value;

	document.getElementById("usernameauto_error").innerText="";
	document.getElementById("passwordauto_error").innerText="";
	
	var error=false;
	if(username==""){
		error=true;
		document.getElementById("usernameauto_error").innerText="Please Enter Username";
	}
	if(password==""){
		error=true;
		document.getElementById("passwordauto_error").innerText="Please Enter Password";
	}
	if(error){
		return false;
	}else{
		return true;
	}
}

</script>
</body>
</html>