'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('MainCtrl', function($scope,$position,$http) {
	
	
	
	$http.get('http://localhost:8081/Injury/Admin/getNoOfStaffs.json').success( function(response) {
	     $scope.staff= response.staffForms;
	     
	     $scope.numberStaff=$scope.staff;
	     });
	
	
	
	
	 $http.get('http://localhost:8081/Injury/Admin/getNoOfDoctors.json').success( function(response) {
	     $scope.doctors= response.doctorsForms;
	     
	     $scope.numberDoctors=$scope.doctors;
	     });
  
	 
	 $http.get('http://localhost:8081/Injury/Staff/getNoOfPatients.json').success( function(response) {
	     $scope.patients= response.patientsForms;
	   

	     $scope.numberPatients=$scope.patients;
	     });
  
	 
	
	
	  
	  $http.get('http://localhost:8081/Injury/Staff/getNoOfAppointments.json').success( function(response) {
		     $scope.appointments= response.appointmentsForms;

		     
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
