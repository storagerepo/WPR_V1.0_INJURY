<!DOCTYPE HTML>
<html>
<head>
<title>Crash Reports Online</title>
 <link rel="stylesheet" type='text/css' href="resources/components/bootstrap/dist/css/bootstrap.min.css" />
    <link rel="icon" type="image/png" href="resources/images/favicon.ico">
<script src="resources/components/jquery/dist/jquery.min.js"></script>
<link href="resources/styles/marketing-style.css" rel='stylesheet' type='text/css' />
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>

<!-- Angular JS -->
<script src="resources/components/angular/angular.min.js"></script>
<!-- Flash -->
<script src="resources/components/angular-flash/angular-flash.min.js"></script>
<link rel="stylesheet" href="resources/components/angular-flash/angular-flash.css">
<!-- Request Handler -->
<script src="resources/scripts/services/requestHandler.js"></script>
<!-- Common App -->
 <script src="resources/scripts/common/commonApp.js"></script>
</head>
<body ng-app="commonApp">
<!-- header_top -->
<div class="top_bg">
<div class="container">
<div class="header_top">
	<div class="top_left">
		<h2>Crash Reports Online</h2>
	</div>
	<div class="top_right">
		<ul>
			<li><a>800 Cross Pointe Road, Suite A, Gahanna, OH 43230, U.S.A.</a></li>
		</ul>
	</div>
	<div class="clearfix"> </div>
</div>
</div>
</div>

<!-- content -->
<div class="container">
    <div class="main">
        <div class="row content_top">
            <div class="col-md-12 sidebar">
                <div class="grid_list">
                    <div class="grid_text left">
                        <h3><a>The Game is About to Change!</a></h3>
                        <p class="margin-top-20"><b>Greetings top PI attorneys,</b></p>
                        <p class="margin-top-10">We have just completed the web based crash report retrieval app and are now offering it to a given number of clients in Ohio. The number of clients will be limited, to offer a competitive advantage to our customers.</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
            </div>

            <div class="col-md-4">
                <div class="right-angle-arrow themecolor"></div>
                <div class="corner">
                    <div class="feature">
                        <h4>FEATURES</h4>
                        <ul>
                            <li>Lightning fast crash report retrieval for all 88 Ohio counties (fastest on market!)</li>
                            <li>Very affordable monthly rate.</li>
                            <li>Saves hours of time downloading reports and searching for addresses.</li>
                            <li>Behind the scenes filtering so you can eliminate reports you don&#39;t want.</li>
                            <li>Lists of patients names, addresses, report number etc. extracted from crash report.</li>
                            <li>PDF button shows entire report.</li>
                            <li>No downloading necessary: automatically loads new reports as they come in.</li>
                            <li>4 tiers of accident types, single and multiple cars.</li>
                            <li>Sort by county, date, report number or tier.</li>
                            <li>List groups reports by reports, so all passengers are together.</li>
                            <li>Minors and injured passengers are color coded.</li>
                            <li>Create an excel spreadsheet of report data by click of a button!</li>
                        </ul>
                    </div>
                </div>
                <br/><br/><br/>
                <div>
                    <div class="h_nav">
                        <h4>company address</h4>
                        <ul class="address">
                            <li><b>Deemsys Inc</b></li>
                            <li>800 Cross Pointe Road,</li>
                            <li>Suite A,</li>
                            <li>Gahanna,</li>
                            <li>OH 43230, U.S.A.</li>
                            <li><br/><b>Email ID</b></li>
                            <li>support@deemsysinc.com</li>
                            <!--<li><br/><b>Follow Us</b></li>-->
                        </ul>
                    </div>
                    <!--<div class="span_of_2">
                        <div class="social-icons">
                            <ul>
                                <li><a href="#" target="_blank"></a></li>
                                <li><a href="#" target="_blank"></a></li>
                                <li><a href="#" target="_blank"></a></li>
                                <li><a href="#" target="_blank"></a></li>
                                <li><a href="#" target="_blank"></a></li>
                            </ul>
                        </div>
                    </div>-->

                </div>
                </div>

            <div class="col-md-8" ng-controller="userContactUsController">
                <!-- <img src="resources/images/video.gif" width="100%"> -->
                <video width="100%" height="auto" controls>
  <source src="http://cdn.crashreportsonline.com/videos/InjuryAppIntro.mp4" type="video/mp4"></source></video>
                <br/><br/>
                <div class="content">
                    <div class="content_text">
                        <h4><a>Be the first to jump...</a></h4>
                        <p>Be the first to jump on board with this super quick, state of the art software. We truly are limiting our clients per county so depending on the response; you may not be able to jump on board soon after launch. Free demo online.
                            <br/><br/>
                            For more information or to become part of this group opportunity, please click this link and fill out your name, email and phone number. A rep will call you within 24 hours.
                        </p>
                    </div>
                </div>
                <br/><br/>
                    <div class="contact-form">
                    <h3>Contact Us</h3>
                    <form name="contactForm" novalidate ng-submit="contactForm.$valid && saveContactUs()">
                       <div class="col-md-12">
                        <div class="col-md-6">
                            <span><label>First Name</label></span>
                            <span><input name="firstName" ng-model="contactUsForm.firstName" type="text" required validate-name maxlength="45" placeholder="First Name"></span>
                            <span class="error-container" ng-show="submitted">
                                    <span ng-cloak ng-show="contactForm.firstName.$error.required">Please Enter First Name</span>
                                    <span ng-cloak ng-show="!contactForm.firstName.$error.required&&contactForm.firstName.$error.validateName">Enter Valid First Name</span>
                            </span>
                        </div>
                        <div class="col-md-6">
                            <span><label>Last Name</label></span>
                            <span><input name="lastName" ng-model="contactUsForm.lastName" type="text" class="textbox" required validate-name maxlength="45" placeholder="Last Name"></span>
                             <span class="error-container" ng-show="submitted">
                                    <span ng-cloak ng-show="contactForm.lastName.$error.required">Please Enter Last Name</span>
                                    <span ng-cloak ng-show="!contactForm.lastName.$error.required&&contactForm.lastName.$error.validateName">Enter Valid Last Name</span>
                            </span>
                        </div>
                        </div>
                        <div class="col-md-6">
                            <span><label>E-mail</label></span>
                            <span><input name="email" ng-model="contactUsForm.email" type="email" validate-email required maxlength="60" placeholder="Email"></span>
                             <span class="error-container" ng-show="submitted">
                                    <span ng-cloak ng-show="contactForm.email.$error.required">Please Enter Email Id</span>
                                    <span ng-cloak ng-show="!contactForm.email.$error.required&&contactForm.email.$error.email">Enter Valid Email</span>
                            </span>
                        </div>
                        <div class="col-md-6">
                            <span><label>Mobile</label></span>
                            <span><input name="phoneNumber" type="text" class="textbox" ng-model="contactUsForm.phoneNumber" required validate-mobile maxlength="20" placeholder="Ex:123-345-6789 or 1234567890"></span>
                              <span class="error-container" ng-show="submitted">
	                          			<span ng-cloak ng-show="contactForm.phoneNumber.$error.required">Please Enter Phone Number</span>
	                          			<span ng-cloak ng-show="!contactForm.phoneNumber.$error.required&&contactForm.phoneNumber.$error.validateMobile">Enter Valid Phone Number</span>
	                            </span>
                        </div>
                        <div class="col-md-12">
                            <span><label>Subject</label></span>
                            <span><input class="fullwidth" name="subject" ng-model="contactUsForm.subject" type="text" class="textbox" required maxlength="150" placeholder="Subject"></span>
                        	  <span class="error-container" ng-show="submitted">
	                          			<span ng-cloak ng-show="contactForm.subject.$error.required">Please Enter Subject</span>
	                          	</span>
                        </div>
                        <div class="col-md-12">
                            <span><label>Message</label></span>
                            <span><textarea class="fullwidth" name="message" ng-model="contactUsForm.message" required maxlength="5000" placeholder="Message"> </textarea></span>
                              <span class="error-container" ng-show="submitted">
	                          			<span ng-cloak ng-show="contactForm.message.$error.required">Please Enter Messsage</span>
	                            </span>
                        </div>
                        <div class="col-md-12">
                            <span><input type="submit" class="pull-left" value="{{submitText}}" ng-disabled="isSubmit" ng-click="submitted=true"></span>
                            <span class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <div flash-message="2000" class="col-md-5 pull-left"></div>	
                        </div>
                        <div class="col-md-12">
                            <br/><br/>
                        </div>
                    </form>
                </div>

            </div>

        <!-- end content -->
        </div>
    </div>
</div>
<!-- footer_top -->
<!--<div class="footer_top">
 <div class="container">
		&lt;!&ndash; start span_of_2 &ndash;&gt;
		<div class="span_of_2">
		<div class="span1_of_2">
			<h5>need help? <a href="#">contact us <span> ></span> </a> </h5>
			<p>(or) Call us: +91-70-45022088</p>
		</div>
		<div class="span1_of_2">
			<h5>follow us </h5>
			<div class="social-icons">
				     <ul>
				        <li><a href="#" target="_blank"></a></li>
				        <li><a href="#" target="_blank"></a></li>
				        <li><a href="#" target="_blank"></a></li>
				        <li><a href="#" target="_blank"></a></li>
				        <li><a href="#" target="_blank"></a></li>
					</ul>
			</div>
		</div>
		<div class="clearfix"></div>
		</div>
  </div>
</div>-->
<!-- footer -->
<div class="footer">
 <div class="container">
	<div class="copy">
		<p class="link">&copy; All rights reserved | Deemsys Inc</p>
	</div>
 </div>
</div>
</body>
</html>