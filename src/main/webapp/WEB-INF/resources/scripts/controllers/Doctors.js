

var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowDoctorsCtrl', function($scope,$http,$location,$state,requestHandler,successMessageService) {
	$scope.errorMessage=successMessageService.getMessage();
	$scope.isError=successMessageService.getIsError();
	$scope.isSuccess=successMessageService.getIsSuccess();
    successMessageService.reset();
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };	
    requestHandler.getRequest("Staff/getAllDoctorss.json","").then(function(response){
    	 $scope.doctors = response.data.doctorsForms;
         $scope.sort('doctorName');
        });
	
   
    $scope.deleteDoctor=function(id)
	  {
if(confirm("Are you sure to delete Doctor ?")){
		  requestHandler.deletePostRequest("Admin/deleteDoctors.json?id=",id).then(function(results){
			  successMessageService.setMessage("You have Successfully Deleted!");
	            successMessageService.setIsError(0);
	            successMessageService.setIsSuccess(1);
	        
			  $state.reload('dashboard.doctor');
			  
		     });
	}
	}
   
});

adminApp.controller('AddDoctorsCtrl', function($scope,$http,$location,$state,requestHandler,successMessageService) {

	$scope.myFormButton=true;

	
	
	$scope.sun=["1"];
	$scope.mon=["2"];
	$scope.tue=["3"];
	$scope.wed=["4"];
	$scope.thu=["5"];
	$scope.fri=["6"];
	$scope.sat=["7"];
	
	$scope.title=$state.current.title;
	$scope.saveDoctor=function()
	{
		requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			successMessageService.setMessage("You have Successfully Added Doctor");
            successMessageService.setIsError(0);
            successMessageService.setIsSuccess(1);
			$location.path('dashboard/doctor');
		});
	};

});

adminApp.controller('EditDoctorController', function($scope,$http,$location,$stateParams,$state,requestHandler,successMessageService) {
	
	$scope.myFormButton=false;

	$scope.sun=1;
	$scope.mon=2;
	$scope.tue=3;
	$scope.wed=4;
	$scope.thu=5;
	$scope.fri=6;
	$scope.sat=7;

	
	$scope.title=$state.current.title;
	
	requestHandler.getRequest("Admin/getDoctors.json?id="+$stateParams.id,"").then(function(response){
		 $scope.doctor = response.data.doctorsForm;
	});
	

	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			  successMessageService.setMessage("You have Successfully updated!");
	            successMessageService.setIsError(0);
	            successMessageService.setIsSuccess(1);
			  $location.path('dashboard/doctor');
			});
			
	  
	};
});


//Service for exchange success message
angular.module('sbAdminApp').service('successMessageService', function() {


	 this.setMessage = function(message) {
	        this.message = message;
	    };

	    this.getMessage = function() {
	        return this.message;
	    };

	    this.setIsError = function(error) {
	        this.error = error;
	    };

	    this.getIsError = function() {
	        return this.error;
	    };
	    
	    this.setIsSuccess = function(success) {
	        this.success = success;
	    };

	    this.getIsSuccess = function() {
	        return this.success;
	    };

});


