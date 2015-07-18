var adminApp=angular.module('sbAdminApp', []);

adminApp.controller('ShowPatientController', function($scope,$http,$location,$state) {
	
	$scope.title="Add Patient";
	$http.get("http://localhost:8080/Injury/Staff/getAllPatientss.json").success( function(response) {
		
	     $scope.patients= response.patientsForms;
	     });
	
	$scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

	
	$scope.getPatientList=function(){
		
		$http.get("http://localhost:8080/Injury/Staff/getAllPatientss.json").success( function(response) {
		     $scope.patients= response.patientForms;
		     });
	};
	
	$scope.addModel=function()
	{
		$scope.title="Add Patient";
		$("#myModal").modal("show");
	};
	
	$scope.editModal=function()
	{
		$scope.title="Edit Patient";
		$("#myModal").modal("show");
	};
	

});


adminApp.controller('EditPatientController', function($scope,$http,$location,$stateParams){
	
	$scope.patientid=$stateParams.id;
	
		$http.get("http://localhost:8080/Injury/Staff/getPatients.json?id="+$stateParams.id).success( function(response) {
		     $scope.patient= response.patientForms;
		     alert($scope.patient);
		     });	
		
	
});