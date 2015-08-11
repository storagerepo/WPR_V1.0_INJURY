

var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowDoctorsCtrl', function($scope,$http,$location,$state,requestHandler) {
	 
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };	
    requestHandler.getRequest("Admin/getAllDoctorss.json","").then(function(response){
    	 $scope.doctors = response.data.doctorsForms;
         $scope.sort('name');
        });
	
   
    $scope.deleteDoctor=function(id)
	  {
if(confirm("Are you sure to delete Doctor ?")){
		  requestHandler.deletePostRequest("Admin/deleteDoctors.json?id=",id).then(function(results){
			  $state.reload('dashboard.doctor');
		     });
	}
	}
   
});

adminApp.controller('AddDoctorsCtrl', function($scope,$http,$location,$state,requestHandler) {

	$scope.myFormButton=true;
	$scope.title=$state.current.title;
	$scope.saveDoctor=function()
	{
		requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			  $location.path('dashboard/doctor');
			});
	};

});

adminApp.controller('EditDoctorController', function($scope,$http,$location,$stateParams,$state,requestHandler) {
	
	$scope.myFormButton=false;
	$scope.title=$state.current.title;
	
	requestHandler.getRequest("Admin/getDoctors.json?id="+$stateParams.id,"").then(function(response){
		 $scope.doctor = response.data.doctorsForm;
	});
	

	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			  $location.path('dashboard/doctor');
			});
			
	  
	};
});


