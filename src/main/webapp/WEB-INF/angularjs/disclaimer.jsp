<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width">
<!-- Favicons-->
<link rel="icon" href="resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
<meta name="description" content="Crash reports online contains accident reports of Ohio which is easy to access, download and get Lightning fast reports upon request.">
<title>Disclaimer</title>
<!-- bower:css -->
<link rel="stylesheet" href="resources/components/bootstrap/dist/css/bootstrap.min.css" />
<script src="resources/components/jquery/dist/jquery.min.js"></script>
<script src="resources/components/angular/angular.min.js"></script>
<script src="resources/components/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body ng-app="disclaimerApp" ng-controller=disclaimerController>
<style>
#divElement{
    display: table;
    margin: 0 auto;
}
</style>
<div class="col-md-12" style="padding-top: 50px;">
<div class="col-md-3"></div>
<div class="col-md-6" style="border: 1px solid black;padding-top: 50px;"><div style="text-align: center;"><img src="resources/images/logo_crashreportsonline.png" width="180px;" height="130px"/><br/><h2>DISCLAIMER</h2><br/></div>
<div>
<div style="padding: 5px 5px 5px 5px;">
<p>
<b>CrashReportsOnline</b> and <b>Deemsys Inc.</b> are not responsible for, and expressly disclaims all liability for, damages of any kind arising out of use, reference to, or reliance on any information contained within the site. 
<p>
While the information contained within the site is continuously updated, the content of the site is collected from PDFs provided by the State and therefore neither <b>CrashReportsOnline</b> nor <b>Deemsys Inc.</b> can guarantee that the information provided in the PDFs is correct, complete and up-to-date.</p>
<p>
Product information is based solely on material received from suppliers.</p>
<br/>
</div>
</div>
<br/>
<br/>
</div>
<div class="col-md-3"></div>
</div>
<div class="col-md-12">
<div class="col-md-3"></div>
<div class="col-md-6">
<br/>
<div style="text-align: center;"><a href="" class="btn btn-primary" ng-click="agreeDisclaimer()">I Agree & Continue</a><br/><br/></div>
</div>
<div class="col-md-3"></div>
</div>
</body>
<script>
var disclaimerApp=angular.module('disclaimerApp',[]);
disclaimerApp.controller('disclaimerController',function($scope,$http,$window){
	$scope.agreeDisclaimer=function(){
		 requestURL="/updateDisclaimerStatus.json";
	        $http.post(requestURL,'').then(function (response) {
	                if(response.data.requestSuccess){
	                	$window.location.reload();
	                }
	         });
	};
});
</script>
</html>
