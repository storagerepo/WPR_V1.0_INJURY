'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
var adminApp=angular.module('sbAdminApp',['requestModule']);
adminApp.controller('MainCtrl', function($scope,$position,$http,requestHandler) {
	
	requestHandler.postRequest("Staff/getCurrentRole.json","").then(function(response) {
		  
		   $scope.admin=false;
		   $scope.staff=false;
		   if(response.data.role=="ROLE_ADMIN"){
			   $scope.admin=true;
			   $scope.staff=true;
			   requestHandler.getRequest("Admin/getNoOfStaffs.json","").then( function(response) {
				     $scope.staff= response.data.staffForms;
				     
				     $scope.numberStaff=$scope.staff;
				     
				     });
				
				
				
				
				  requestHandler.getRequest("Admin/getNoOfDoctors.json","").then( function(response) {
				     $scope.doctors= response.data.doctorsForms;
				     
				     $scope.numberDoctors=$scope.doctors;
				     });
			  
				 
		   }
		   else if(response.data.role=="ROLE_STAFF"){
			   $scope.staff=true;
			   requestHandler.getRequest("Staff/getNoOfPatientss.json","").then( function(response) {
				     $scope.patients= response.data.patientsForms;
				   

				     $scope.numberPatients=$scope.patients;
				     });
			  
			   requestHandler.getRequest("Staff/getNoOfAppointments.json","").then( function(response) {
					     $scope.appointments= response.data.appointmentsForms;

					     
					     $scope.numberappointments=$scope.appointments;
					     });
		   }
		   else{
			   $location.path('/logout');
		   }
		   
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

adminApp.controller('roleController', function($scope,$http,$location,requestHandler) {
	 
	  requestHandler.postRequest("Staff/getCurrentRole.json","").then(function(response) {
	  
	   $scope.admin=false;
	   $scope.staff=false;
	   if(response.data.role=="ROLE_ADMIN"){
		   $scope.admin=true;
		   $scope.staff=true;
	   }
	   else if(response.data.role=="ROLE_STAFF"){
		   $scope.staff=true;
	   }
	   else{
		   $location.path('/logout');
	   }
	   
	});	

});