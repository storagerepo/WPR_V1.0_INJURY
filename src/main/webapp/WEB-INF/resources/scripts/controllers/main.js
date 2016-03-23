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
	/*requestHandler.getRequest("Caller/getNoOfCallers.json","").then( function(response) {
	     $scope.staff= response.data.staffForms;
	     
	     $scope.numberStaff=$scope.staff;
	     
	     });
	// No of Clinics
	requestHandler.getRequest("StaCallertNoOfClinics.json","").then( function(response) {
	      $scope.numberClinics=response.data.NoOfClinics;
	     
	     });*/
	
	
		if($rootScope.isAdmin==1){
			// No of Clinics
			requestHandler.getRequest("Caller/getNoOfClinics.json","").then( function(response) {
			      $scope.numberClinics=response.data.NoOfClinics;
			     
			     });
			requestHandler.getRequest("Caller/getNoOfCallers.json","").then( function(response) {
			     $scope.staff= response.data.callerForms;
			     
			     $scope.numberStaff=$scope.staff;
			     
			     });
			   	requestHandler.getRequest("Caller/getNoOfDoctors.json","").then( function(response) {
				     $scope.doctors= response.data.doctorsForms;
				     
				     $scope.numberDoctors=$scope.doctors;
				     });
					
					 requestHandler.getRequest("Caller/getNoOfPatients.json","").then( function(response) {
					     $scope.patients= response.data.patientForms;
					   

					     $scope.numberPatients=$scope.patients;
					     });
				  
				   requestHandler.getRequest("Caller/getNoOfAppointments.json","").then( function(response) {
						     $scope.appointments= response.data.appointmentsForms;

						     
						     $scope.numberappointments=$scope.appointments;
						     });
				   requestHandler.getRequest("Admin/getNoOfLawyerAdmin.json","").then( function(response) {
					    	$scope.numberLawyerAdmin=response.data.noOfLawyerAdmin;
					     });
			}
		   else if($rootScope.isAdmin==2){
			   requestHandler.getRequest("CAdmin/getNumberOfCallers.json","").then( function(response) {
				   $scope.numberOfCallers= response.data.numberOfCallers;
			   });
			   requestHandler.getRequest("CAdmin/getNumberOfClinics.json","").then( function(response) {
				   $scope.numberOfClinics= response.data.NoOfClinics;
			   });
			   /*requestHandler.getRequest("CAdmin/getNoOfDoctors.json","").then( function(response) {
				     $scope.doctors= response.data.doctorsForms;
				     
				     $scope.numberDoctors=$scope.doctors;
				     });
			   
			   requestHandler.getRequest("CAdmin/getNoOfPatients.json","").then( function(response) {
				     $scope.patients= response.data.patientForms;
				   

				     $scope.numberPatients=$scope.patients;
				     });
			  
			   requestHandler.getRequest("CAdmin/getNoOfAppointments.json","").then( function(response) {
					     $scope.appointments= response.data.appointmentsForms;

					     
					     $scope.numberappointments=$scope.appointments;
					     });*/
		   }else if($rootScope.isAdmin==3){
			   requestHandler.getRequest("Lawyer/getNoOfLawyers.json","").then( function(response) {
				   $scope.numberLawyer=response.data.noOfLawyers;
				     });
		   }
		   else if($rootScope.isAdmin==4){
			   requestHandler.getRequest("Patient/getNoOfPatientsByLawyer.json","").then( function(response) {
				   $scope.numberPatients=response.data.noOfPatientsByLawyer;
				     });
		   }
		   
	
  });

