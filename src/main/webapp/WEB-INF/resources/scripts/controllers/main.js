'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
var adminApp=angular.module('sbAdminApp',['requestModule']);
adminApp.controller('MainCtrl', function($scope,$position,$http,requestHandler,$rootScope) {
	
	setTimeout(function(){
		
	
	
		if($rootScope.isAdmin==1){
		
			requestHandler.getRequest("Admin/getNumberOfCallerAdmin.json","").then( function(response) {
			      $scope.numberCallerAdmin=response.data.numberOfCallerAdmin;
			   });
			
			requestHandler.getRequest("Admin/getNoOfLawyerAdmin.json","").then( function(response) {
			      $scope.numberLawyerAdmin=response.data.noOfLawyerAdmin;
			   });
			
			requestHandler.getRequest("Admin/getNumberOfCrashReport.json","").then( function(response) {
			      $scope.numberCrashReport=response.data.numberOfCrashReports;
			   });
			
			requestHandler.getRequest("Patient/getNumberOfPatients.json","").then( function(response) {
			      $scope.numberPatient=response.data.numberOfPatients;
			   });
			}
		   else if($rootScope.isAdmin==2){
			   requestHandler.getRequest("CAdmin/getNumberOfCallers.json","").then( function(response) {
				   $scope.numberOfCallers= response.data.numberOfCallers;
			   });
			   
			   requestHandler.getRequest("Caller/getNumberOfClinics.json","").then( function(response) {
				   $scope.numberOfClinics= response.data.NumberOfClinics;
			   });
			   
			   requestHandler.getRequest("Caller/getNumberOfAppointments.json","").then( function(response) {
				   $scope.numberOfAppointments= response.data.numberOfAppointments;
			   });
			   
			  requestHandler.getRequest("Patient/getNumberOfPatients.json","").then( function(response) {
				   $scope.numberOfPatients= response.data.numberOfPatients;
			   });
		
			   
		   }else if($rootScope.isAdmin==3){
			   requestHandler.getRequest("Patient/getNumberOfPatients.json","").then( function(response) {
				   $scope.numberPatients=response.data.numberOfPatients;
				     });
			   
			   requestHandler.getRequest("LAdmin/getNumberOfLawyers.json","").then( function(response) {
				   $scope.numberLawyer=response.data.noOfLawyers;
				     });
		   }
		   else if($rootScope.isAdmin==4){
			   
			   requestHandler.getRequest("Caller/getNumberOfClinics.json","").then( function(response) {
				   $scope.numberOfClinics= response.data.NumberOfClinics;
			   });
			   
			   
			   requestHandler.getRequest("Caller/getNumberOfAssignedPatients.json","").then( function(response) {
				   $scope.numberPatients=response.data.numberOfAssignedPatients;
				     });
		   }
		
		   else if($rootScope.isAdmin==5){
			   requestHandler.getRequest("Lawyer/getNumberOfAssignedPatients.json","").then( function(response) {
				   $scope.numberPatients=response.data.numberOfAssignedPatiets;
				     });
		   }
		   
	}, 500);
  });

