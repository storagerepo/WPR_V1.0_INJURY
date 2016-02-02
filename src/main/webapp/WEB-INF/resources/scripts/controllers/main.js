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
	requestHandler.getRequest("Staff/getNoOfStaffs.json","").then( function(response) {
	     $scope.staff= response.data.staffForms;
	     
	     $scope.numberStaff=$scope.staff;
	     
	     });
	// No of Clinics
	requestHandler.getRequest("Staff/getNoOfClinics.json","").then( function(response) {
	      $scope.numberClinics=response.data.NoOfClinics;
	     
	     });
		if($rootScope.isAdmin){
			// No of Clinics
			requestHandler.getRequest("Staff/getNoOfClinics.json","").then( function(response) {
			      $scope.numberClinics=response.data.NoOfClinics;
			     
			     });
			   	requestHandler.getRequest("Staff/getNoOfDoctors.json","").then( function(response) {
				     $scope.doctors= response.data.doctorsForms;
				     
				     $scope.numberDoctors=$scope.doctors;
				     });
					
					 requestHandler.getRequest("Staff/getNoOfPatientss.json","").then( function(response) {
					     $scope.patients= response.data.patientsForms;
					   

					     $scope.numberPatients=$scope.patients;
					     });
				  
				   requestHandler.getRequest("Staff/getNoOfAppointments.json","").then( function(response) {
						     $scope.appointments= response.data.appointmentsForms;

						     
						     $scope.numberappointments=$scope.appointments;
						     });
			}
		   else if(!$rootScope.isAdmin){
			   requestHandler.getRequest("Staff/getNoOfDoctors.json","").then( function(response) {
				     $scope.doctors= response.data.doctorsForms;
				     
				     $scope.numberDoctors=$scope.doctors;
				     });
			   
			   requestHandler.getRequest("Staff/getNoOfPatientss.json","").then( function(response) {
				     $scope.patients= response.data.patientsForms;
				   

				     $scope.numberPatients=$scope.patients;
				     });
			  
			   requestHandler.getRequest("Staff/getNoOfAppointments.json","").then( function(response) {
					     $scope.appointments= response.data.appointmentsForms;

					     
					     $scope.numberappointments=$scope.appointments;
					     });
		   }
		   
	
  });

