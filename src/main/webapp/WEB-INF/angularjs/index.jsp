<!doctype html>
<html class="no-js">
  <head>
  
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
    <meta charset="utf-8">
    <title>Crash Reports | crashreportsonline.com</title>
    <link rel="icon" href="resources/images/favicon.ico" type="image/gif" sizes="16x16">
    <meta name="description" content="Crash reports online contains accident reports of Ohio which is easy to access, download and get Lightning fast reports upon request.">
    <meta name="viewport" content="width=device-width">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <!-- build:css(.) styles/vendor.css -->
    <!-- bower:css -->
    <link rel="stylesheet" href="resources/components/bootstrap/dist/css/bootstrap.min.css" />
   <!--  <link  rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tour/0.10.3/css/bootstrap-tour.min.css"/> -->
    <!-- endbower -->
    <!-- endbuild -->
    
    
    <!-- build:css(.tmp) styles/main.css -->
    <link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/sb-admin-2.css">
    <link rel="stylesheet" href="styles/timeline.css">
    <link rel="stylesheet" href="resources/components/metisMenu/dist/metisMenu.min.css">
    <link rel="stylesheet" href="resources/components/angular-loading-bar/build/loading-bar.min.css">
    <link rel="stylesheet" href="resources/components/font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- endbuild  error-->
    
    <!-- build:js(.) scripts/vendor.js -->
    <!-- bower:js -->
    <script src="resources/components/jquery/dist/jquery.min.js"></script>
    <script src="resources/components/angular/angular.min.js"></script>
	  <script src="resources/components/bootstrap/dist/js/bootstrap.min.js"></script>
	  

<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tour/0.10.3/js/bootstrap-tour.min.js"></script> -->

	     <script src="resources/components/angular-ui-router/release/angular-ui-router.min.js"></script>
    <script src="resources/components/json3/lib/json3.min.js"></script>
    <script src="resources/components/oclazyload/dist/ocLazyLoad.min.js"></script>
   <script src="resources/components/angular-animate/angular-animate-1.4.3.js"></script>
    <script src="resources/components/angular-loading-bar/build/loading-bar.min.js"></script>
    <script src="resources/components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
    <script src="resources/components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="resources/components/Chart.js/Chart.min.js"></script>
    <script src="resources/components/angular-utils-pagination/dirPagination.js"></script> 
     <script src="resources/components/angular-file-upload/angular-file-upload.min.js"></script>
	<!-- Flash -->
	<script src="resources/components/angular-flash/angular-flash.min.js"></script>
	<link rel="stylesheet" href="resources/components/angular-flash/angular-flash.css">
     <!-- <script  src="resources/components/prettify/prettify.js"></script> -->
	<!-- endbower  error-->
   
    
    <!-- build:js({.tmp,app}) scripts/scripts.js -->
        <script src="scripts/app.js"></script>
        <script src="js/sb-admin-2.js"></script>
    <!-- endbuild -->
	
	<!-- Google Map-->
	<script src="resources/components/angular-map-rawgit/lodash.min.js"></script>
	<script src="resources/components/angular-map-rawgit/angular-google-maps.js"></script>
	 
	<!-- File Saver -->
	<script src="resources/components/angular-file-saver/angular-file-saver.min.js"></script>
	<script src="resources/components/angular-file-saver/angular-file-saver.bundle.min.js"></script>

	<!-- For Multi Select Dropdowns -->
	<link rel="stylesheet" href="resources/components/bootstrap-multi-select/bootstrap-select.min.css"></link>
	<script src="resources/components/bootstrap-multi-select/bootstrap-select.min.js"></script>
	
	 <!-- Drag Drop -->
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<script src="resources/components/angular-sortable/angular-sortable.js"></script>

	<!-- Load For Menu -->
	<script src="resources/scripts/services/searchService.js"></script>
	<script src="resources/scripts/services/requestHandler.js"></script>
	<script src='resources/scripts/directives/sidebar/sidebar.js'></script>
    <script src='resources/scripts/directives/sidebar/sidebar-search/sidebar-search.js'></script>
 	<script src="resources/scripts/directives/header/header.js"></script>
    <script src="resources/scripts/directives/header/header-notification/header-notification.js"></script>
    <script src="resources/scripts/directives/dashboard/stats/stats.js"></script>
    <script src="resources/scripts/directives/errorDisplay/errordisplay.js"></script>
    <script src="resources/js/analytics.js"></script>
    
    <!-- Controllers -->

   <!--  <script>
       (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
       (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
       m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
       })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
       ga('create', 'UA-XXXXX-X');
       ga('send', 'pageview');
    </script> -->
    <!-- Custom CSS -->

    <!-- Custom Fonts -->

    <!-- Morris Charts CSS -->
    <!-- <link href="styles/morrisjs/morris.css" rel="stylesheet"> -->


    </head>
    <body ng-app="sbAdminApp">
	
    <div class="margin-top-30">

        <div ui-view></div>
		<div ng-controller="authenticationController"></div>
    </div>

    </body>
<style type="text/css">
.btn-file {
    position: relative;
    overflow: hidden;
}
.btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 100px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: white;
    cursor: inherit;
    display: block;
}
</style>



</html>