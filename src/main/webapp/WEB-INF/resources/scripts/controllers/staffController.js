
var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowStaffController', function($rootScope,$scope,$state,$http,$stateParams,$location,$state,requestHandler,successMessageService) {
	 
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
    
    $scope.disableStaff=function(id)
    {
    	  $("#disableStaff").modal("show");
    	  $scope.disable=function()
		   {
    	  requestHandler.getRequest("Admin/disableStaff.json?id="+id,"").then(function(results){
 $scope.response=results.data.requestSuccess;
			  
			  if($scope.response==true)
				  {
				  successMessageService.setMessage("You have Successfully Enabled!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1);  
		          $("#disableStaff").modal("hide");
		          $('.modal-backdrop').hide();
		          	$state.reload('dashboard.staff');;

				  }
			  if($scope.response==false)
			  {
				  successMessageService.setMessage("You have Successfully Disabled!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1); 
		          $("#disableStaff").modal("hide");
		          $('.modal-backdrop').hide();
		          $state.reload('dashboard.staff');

			  }
    	     });
		   };
		   
    };
    
    $scope.enableStaff=function(id)
    {
    	  $("#enableStaff").modal("show");
    	  $scope.enable=function()
		   {
    	  requestHandler.getRequest("Admin/disableStaff.json?id="+id,"").then(function(results){
 $scope.result=results.data.requestSuccess;
			  
			  if($scope.result==true)
				  {
				  successMessageService.setMessage("You have Successfully Enabled!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1);
		          $("#enableStaff").modal("hide");
		          $('.modal-backdrop').hide();
		          $state.reload('dashboard.staff');

				  }
			  if($scope.result==false)
			  {
				  successMessageService.setMessage("You have Successfully Disabled!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1); 
		          $("#enableStaff").modal("hide");
		          $('.modal-backdrop').hide();
		          $state.reload('dashboard.staff');

			  }
    	     });
		   };
		   
    };
    
    $scope.deleteStaff=function(id)
	  {
		
		  if(confirm("Are you sure to delete Caller ?")){
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
				    			 $("#deleteStaffModal").modal("hide");
							    	$('.modal-backdrop').hide();
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
	
	$("#username_exists").text("");
	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.save=function()
	{
		$("#username_exists").text("");
		requestHandler.getRequest("Admin/getUsername.json?username="+$scope.staff.username,"").then(function(response){
			var isNew=response.data.staffForms;
			if(isNew==0){
				$("#username_exists").text("");
			 requestHandler.postRequest("Admin/saveUpdateStaff.json",$scope.staff).then(function(response){
				  successMessageService.setMessage("You have Successfully Added!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1);
				  $location.path('dashboard/staff');
				});
			}
			else{
				$("#username_exists").text("UserName already exists");
			}
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
