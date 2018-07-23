'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
var adminApp=angular.module('sbAdminApp',['requestModule','flash']);
adminApp.controller('MainCtrl', function($scope,$state,$position,$http,requestHandler,$rootScope,Flash) {
	
	// For Password Change Modal
/*	requestHandler.getRequest("checkPasswordChangedStatus.json","").then( function(response) {
	      var status=response.data.status;
	      if(status!=undefined && status!=1){
	    	$("#changePasswordModal").modal('show',{backdrop: 'static', keyboard: true});
	      }
	   });*/
	
	if($rootScope.passwordChangedStatus!=1){
		$("#changePasswordModal").modal('show',{backdrop: 'static', keyboard: true});
	}
	
	if($rootScope.isAdmin==1){
		
		requestHandler.getRequest("dashboardCount.json","").then( function(response) {
		      $scope.numberPatient=response.data.dashboardCount[3];
		      $scope.numberCrashReport=response.data.dashboardCount[2];
		      $scope.numberCallerAdmin=response.data.dashboardCount[0];
		      $scope.numberDealerManager=response.data.dashboardCount[4];
		      $scope.numberLawyerAdmin=response.data.dashboardCount[1];
		   });
		/*requestHandler.getRequest("Patient/getNumberOfPatients.json","").then( function(response) {
		      $scope.numberPatient=response.data.numberOfPatients;
		   });
		
		requestHandler.getRequest("Admin/getNumberOfCrashReport.json","").then( function(response) {
		      $scope.numberCrashReport=response.data.numberOfCrashReports;
		   });
		
		requestHandler.getRequest("Admin/getNumberOfCallerAdmin.json","").then( function(response) {
		      $scope.numberCallerAdmin=response.data.numberOfCallerAdmin;
		      $scope.numberDealerManager=response.data.numberOfDealerManager;
		   });
		
		requestHandler.getRequest("Admin/getNoOfLawyerAdmin.json","").then( function(response) {
		      $scope.numberLawyerAdmin=response.data.noOfLawyerAdmin;
		   });*/
		
		}
	   else if($rootScope.isAdmin==2){
		   requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			      $scope.numberOfCallers=response.data.dashboardCount[0];
			      $scope.numberOfClinics=response.data.dashboardCount[1];
			      $scope.numberOfAppointments=response.data.dashboardCount[2];
			      $scope.numberOfPatients=response.data.dashboardCount[3];
			 });
		  /* requestHandler.getRequest("Patient/getNumberOfPatients.json","").then( function(response) {
			   $scope.numberOfPatients= response.data.numberOfPatients;
		   });
		   
		   requestHandler.getRequest("Caller/getNumberOfAppointments.json","").then( function(response) {
			   $scope.numberOfAppointments= response.data.numberOfAppointments;
		   });
		   
		   requestHandler.getRequest("CAdmin/getNumberOfCallers.json","").then( function(response) {
			   $scope.numberOfCallers= response.data.numberOfCallers;
		   });
		   
		   requestHandler.getRequest("Caller/getNumberOfClinics.json","").then( function(response) {
			   $scope.numberOfClinics= response.data.NumberOfClinics;
		   });
		   */
		}else if($rootScope.isAdmin==3){
			requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			      $scope.numberLawyer=response.data.dashboardCount[0];
			      $scope.numberPatients=response.data.dashboardCount[1];
			 });
		  /* requestHandler.getRequest("Patient/getNumberOfPatients.json","").then( function(response) {
			   $scope.numberPatients=response.data.numberOfPatients;
			     });
		   
		   requestHandler.getRequest("LAdmin/getNumberOfLawyers.json","").then( function(response) {
			   $scope.numberLawyer=response.data.noOfLawyers;
			     });*/
	   }
	   else if($rootScope.isAdmin==4){
		   requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			   	$scope.numberPatients=response.data.dashboardCount[0];
			    $scope.numberOfClinics=response.data.dashboardCount[1];
		   });
		   /*requestHandler.getRequest("Caller/getNumberOfAssignedPatients.json","").then( function(response) {
			   $scope.numberPatients=response.data.numberOfAssignedPatients;
		   });
		   
		   requestHandler.getRequest("Caller/getNumberOfClinics.json","").then( function(response) {
			   $scope.numberOfClinics= response.data.NumberOfClinics;
		   });*/
	 }
	
	   else if($rootScope.isAdmin==5){
		   requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			   	$scope.numberPatients=response.data.dashboardCount[0];
		   });
		  /* requestHandler.getRequest("Lawyer/getNumberOfAssignedPatients.json","").then( function(response) {
			   $scope.numberPatients=response.data.numberOfAssignedPatiets;
		   });*/
	   } 
	   else if($rootScope.isAdmin==6){
		   requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			   $scope.numberOfDealers=response.data.dashboardCount[0];
			   $scope.numberOfClinics=response.data.dashboardCount[1];
			   $scope.numberOfAppointments=response.data.dashboardCount[2];
			   $scope.numberOfVehicles=response.data.dashboardCount[3];
		   });
		   /*requestHandler.getRequest("Patient/getNumberOfVehicles.json","").then( function(response) {
			   $scope.numberOfVehicles= response.data.numberOfVehicles;
		   });
		   requestHandler.getRequest("CAdmin/getNumberOfCallers.json","").then( function(response) {
			   $scope.numberOfDealers=response.data.numberOfCallers;
		   });*/
	 } else if($rootScope.isAdmin==7){
		 requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			   $scope.numberOfClinics=response.data.dashboardCount[0];
			   $scope.numberOfVehicles=response.data.dashboardCount[1];
		   });
		/*   requestHandler.getRequest("Caller/getNumberOfAssignedVehicles.json","").then( function(response) {
			   $scope.numberOfVehicles= response.data.numberOfAssignedVehicles;
		   });*/
	 } else if($rootScope.isAdmin==8){
		   requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			   $scope.numberOfDealers=response.data.dashboardCount[0];
			   $scope.numberOfClinics=response.data.dashboardCount[1];
			   $scope.numberOfAppointments=response.data.dashboardCount[2];
			   $scope.numberOfVehicles=response.data.dashboardCount[3];
		   });
	 } else if($rootScope.isAdmin==9){
		 requestHandler.getRequest("dashboardCount.json","").then( function(response) {
			   $scope.numberOfClinics=response.data.dashboardCount[0];
			   $scope.numberOfVehicles=response.data.dashboardCount[1];
		   });
		/*   requestHandler.getRequest("Caller/getNumberOfAssignedVehicles.json","").then( function(response) {
			   $scope.numberOfVehicles= response.data.numberOfAssignedVehicles;
		   });*/
	 }
	
	$scope.isShowCloseError=false;
	$scope.showAlert=function(){
		$scope.isShowCloseError=true;
		$scope.isModalCloseError="Please change the password to continue.";
	};
	
	$scope.changePassword=function()
	{
		
		requestHandler.postRequest("changePassword.json?newPassword="+$scope.user.confirmpassword,"").then(function(response){
			 $scope.value=response.data.requestSuccess;
			  
			  if($scope.value==true)
				  {
				  	$("#changePasswordModal").modal('hide');
				  	Flash.create('success', "You have Successfully Changed!");
				  	$scope.checkPasswordChangedStatus();
				  }
		
		});
	};
	
	$scope.checkPasswordChangedStatus=function(){
		requestHandler.getRequest("checkPasswordChangedStatus.json","").then( function(response) {
			$rootScope.passwordChangedStatus=response.data.status;
		     
		   });
	};
	
  });

