var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate','summernote']);

adminApp.controller('ReportIssueController',function($scope,$http,$location,$state,requestHandler,Flash){
	// summernote
	$scope.options={
		height:250	
	};
	
	$scope.technicalIssuesForm = {
			"platform":"Web",
			"status":1,
			"issueStatus":0,
			"issueAddedFrom":2,
	};
	
	// Scroll To Top
	$(function(){
		$("html,body").scrollTop(0);
	});
	
	requestHandler.getRequest("getCurrentUserDetails.json","").then(function(response){
			$scope.currentUserDetailsForm=response.data.currentUserDetailsForm;
			$scope.technicalIssuesForm.productId=$scope.currentUserDetailsForm.productId;
			$scope.technicalIssuesForm.role=$scope.currentUserDetailsForm.role;
			$scope.technicalIssuesForm.firstName=$scope.currentUserDetailsForm.firstName;
			$scope.technicalIssuesForm.lastName=$scope.currentUserDetailsForm.lastName;
			$scope.technicalIssuesForm.contactNumber=$scope.currentUserDetailsForm.phoneNumber;
			$scope.technicalIssuesForm.emailId=$scope.currentUserDetailsForm.emailId;
	});
	
	$scope.postReportIssue=function(){
		requestHandler.postRequest("/postIssue.json",$scope.technicalIssuesForm).then(function(response){
			$(function(){
				$("html,body").scrollTop(0);
			});
			$state.reload('dashboard.report-issue');
			Flash.create('success', "You have Successfully Posted!");
			});
		
};
});