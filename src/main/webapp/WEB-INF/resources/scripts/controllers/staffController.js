
var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowStaffController', function($rootScope,$scope,$http,$location,$state,requestHandler,successMessageService) {
	 
	$scope.errorMessage=successMessageService.getMessage();
	$scope.isError=successMessageService.getIsError();
	$scope.isSuccess=successMessageService.getIsSuccess();
    successMessageService.reset();
 
	
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
    requestHandler.getRequest("Admin/getAllStaffs.json","").then(function(results){
    	 $scope.staffs= results.data.staffForms;
         $scope.sort('username');
     });
    
    $scope.deleteStaff=function(id)
	  {
		
		  if(confirm("Are you sure to delete Staff ?")){
		  requestHandler.deletePostRequest("Admin/deleteStaff.json?id=",id).then(function(results){
			  $scope.value=results.data.requestSuccess;
			  
			  if($scope.value==true)
				  {
			  successMessageService.setMessage("You have Successfully Deleted!");
	          successMessageService.setIsError(0);
	          successMessageService.setIsSuccess(1);
			  $state.reload('dashboard.staff');
				  }
			  else
				  {
				  $("#deleteStaffModal").modal("show");
				    $scope.deleteStaffFromPatients=function()
				    {
				    	requestHandler.postRequest("Staff/deletePatientsByStaffId.json?id="+id).then(function(response){
				    		
				    		 requestHandler.deletePostRequest("Admin/deleteStaff.json?id=",id).then(function(results){
				    			 
				    			 successMessageService.setMessage("You have Successfully Deleted!");
						          successMessageService.setIsError(0);
						          successMessageService.setIsSuccess(1);   
								  $state.reload('dashboard.staff');
				    		 });
				    		 
							});
				    }
				  
				  }
		     });
		  }
	};
    
    $scope.alertFunction=function()
    {
    	
    };
   
 
});


adminApp.controller('SaveStaffController', function($scope,$http,$location,$state,requestHandler,successMessageService) {
	
	
	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.save=function()
	{
		 requestHandler.postRequest("Admin/saveUpdateStaff.json",$scope.staff).then(function(response){
			  successMessageService.setMessage("You have Successfully Added!");
	          successMessageService.setIsError(0);
	          successMessageService.setIsSuccess(1);
			  $location.path('dashboard/staff');
			});
	};

});


adminApp.controller('EditStaffController', function($scope,$http,$location,$stateParams,$state,requestHandler,successMessageService) {
	
	$scope.options=false;
	$scope.title=$state.current.title;
	
	requestHandler.getRequest("Admin/getStaff.json?id="+$stateParams.id,"").then(function(response){
		$scope.staff=response.data.staffForm;
	});
	

	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateStaff.json",$scope.staff).then(function(response){
			  successMessageService.setMessage("You have Successfully Updated!");
	          successMessageService.setIsError(0);
	          successMessageService.setIsSuccess(1);
			  $location.path('dashboard/staff');
			});
			
	  
	};
});


adminApp.directive("userexists", function ($q, $timeout,requestHandler) {
    var CheckUserExists = function (isNew) {
        if(isNew==true)
            return true;
        else
            return false;
    };
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attributes, ngModel) {
            ngModel.$asyncValidators.userexists = function (modelValue) {
                var defer = $q.defer();
                $timeout(function () {
                    var isNew;
                    var sendRequest=requestHandler.getRequest("Admin/getUsername.json?username="+modelValue,0).then(function(results){
                        isNew=results.data.staffForms;
                    });

                    sendRequest.then(function(){

                        if (CheckUserExists(!isNew)){
                            defer.resolve();
                        }
                        else{
                            defer.reject();
                        } 
                    });
                    isNew = false;
                }, 10);

                return defer.promise;
            }
        }
    };

});
