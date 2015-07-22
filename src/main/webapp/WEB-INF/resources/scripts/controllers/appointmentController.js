'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */



angular.module('sbAdminApp').controller('ShowAppointmentsCtrl', function($scope,$http) {
	 
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };

$http.get("http://localhost:8080/Injury/todaysAppointment.json").success( function(response) {
     $scope.appointments = response.appointmentsForms;
    $scope.appointments.status = true;
    });

$scope.viewPatients=function(id)
{
	$http.get('http://localhost:8080/Injury/getAllAppointmentsByPatient.json?patientId='+id).success( function(response) {
	    $scope.patients=response.patientsForms;
	      $("#myModal").modal("show");
     });
}

});
