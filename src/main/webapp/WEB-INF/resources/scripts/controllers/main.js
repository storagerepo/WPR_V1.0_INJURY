'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp', ['requestModule'])
  .controller('MainCtrl', function($scope,$position,$http,requestHandler) {
	
	
	
	  requestHandler.getRequest("Admin/getNoOfStaffs.json","").then( function(response) {
	     $scope.staff= response.data.staffForms;
	     
	     $scope.numberStaff=$scope.staff;
	     
	     });
	
	
	
	
	  requestHandler.getRequest("Admin/getNoOfDoctors.json","").then( function(response) {
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
	     
	  
	  
	 
	  
	  
	  
	  
	 
	  

			  $scope.saveStaff=function($scope){
				  console.log("ok");
				  alert("sdsds");
			};  
			 $scope.newStaff=function($scope){
				  console.log("ok");
				  alert("sdsds");
			};  
  });
