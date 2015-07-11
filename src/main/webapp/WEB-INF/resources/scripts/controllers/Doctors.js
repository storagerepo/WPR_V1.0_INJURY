'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */



angular.module('sbAdminApp').controller('ShowDoctorsCtrl', function($scope,$http,$location,$state) {
	 
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };	
	
    $http.get("http://localhost:8080/Injury/getAllDoctorss.json").success( function(response) {
     $scope.doctors = response.doctorsForms;
     $scope.sort('name');
   
     });
    
    $scope.deleteDoctor=function(id)
	  {
		
		  if(confirm("Are you sure to delete Doctor ?")){
			  $http({
				  method : "POST",
				  url : "http://localhost:8080/Injury/deleteDoctors.json?id="+id,
				  }).success(function(response){
					  $state.reload("dashboard.doctor");
				  });
			  
			}
		  
		 
	}
   
});
angular.module('sbAdminApp').controller('AddDoctorsCtrl', function($scope,$http) {
	$scope.myFormButton=true;
$scope.saveDoctor=function()
  {
	$http.post('http://localhost:8080/Injury/saveUpdateDoctors.json', $scope.doctor).then(function (results) {
        
        $location.path('/dashboard/doctor');
  });
		
		 
}
});

angular.module('sbAdminApp').controller('EditDoctorController', function($scope,$http,$location,$stateParams) {
	 

	 $scope.myFormButton=false;
	$http.get('http://localhost:8080/Injury/getDoctors.json?id='+ $stateParams.id).success( function(response) {
		 $scope.doctor = response.doctorsForm;
});

	  $scope.update=function(){
	  $http.post('http://localhost:8080/Injury/saveUpdateDoctors.json', $scope.doctor).success(function (status) {
	         
	         
	      });
	};
});

