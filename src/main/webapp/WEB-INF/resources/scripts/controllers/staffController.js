
var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('ShowStaffController', function($rootScope,$scope,$state,$http,$stateParams,$location,$state,requestHandler,successMessageService,Flash) {
	
	$scope.noOfRows="10";
	
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
	    requestHandler.getRequest("Admin/getAllCallers.json","").then(function(results){
	    	 $scope.staffs= results.data.staffForms;
	         $scope.sort('username');
	     });
	    
	$scope.getStaffList=function() {
    requestHandler.getRequest("Admin/getAllCallers.json","").then(function(results){
    	 $scope.staffs= results.data.staffForms;
     });
	};
	
    $scope.disableStaff=function(id)
    {
    	  $("#disableStaff").modal("show");
    	  $scope.disable=function()
		   {
    	  requestHandler.getRequest("Admin/disableCaller.json?id="+id,"").then(function(results){
    		 
 $scope.response=results.data.enableOrDisable;
			  
			  if($scope.response==0)
				  {
		          $("#disableStaff").modal("hide");
		          $('.modal-backdrop').hide();
		          Flash.create('success', "You have Successfully Disabled!");
		          $scope.getStaffList();

				  }
			
    	     });
		   };
		   
    };
    
    $scope.enableStaff=function(id)
    {
    	  $("#enableStaff").modal("show");
    	  $scope.enable=function()
		   {
    	  requestHandler.getRequest("Admin/disableCaller.json?id="+id,"").then(function(results){
 $scope.result=results.data.enableOrDisable;
			  
			  if($scope.result==1)
				  {
				  $("#enableStaff").modal("hide");
		          $('.modal-backdrop').hide();
		          Flash.create('success', "You have Successfully Enabled!");
		          $scope.getStaffList();

				  }
			  
    	     });
		   };
		   
    };
    
    
    
    $scope.resetPassword=function(id)
    {
    	  $("#resetPassword").modal("show");
    	  $scope.reset=function()
		   {
    	  requestHandler.getRequest("Admin/resetPassword.json?id="+id,"").then(function(results){
			 $scope.response=results.data.requestSuccess;
			 if($scope.response==true)
			 {
			 
			 $("#resetPassword").modal("hide");
			 $('.modal-backdrop').hide();
			 Flash.create('success', "You have Successfully Reset the Password!");
			 $scope.getStaffList();
			
			 }
			});
		   };
		   
    };
    
    $scope.releasePatient=function(id)
    {
    	  $("#releasePatient").modal("show");
    	  $scope.release=function()
		   {
    	  requestHandler.postRequest("Caller/releasePatientsFromCaller.json?id="+id).then(function(results){
			 $scope.response=results.data.requestSuccess;
			 if($scope.response==true)
			 {
			 
			 $("#resetPassword").modal("hide");
			 $('.modal-backdrop').hide();
			 Flash.create('success', "You have Successfully Released the Patient!");
			 $scope.getStaffList();
			
			 }
			});
		   };
		   
    };
    
    
    $scope.deleteStaff=function(id)
	  {
    	$("#deleteStaffModal1").modal("show");
		  $scope.deleteStaffNormal=function(){
			requestHandler.deletePostRequest("Admin/deleteCaller.json?id=",id).then(function(results){
			  $scope.value=results.data.requestSuccess;
			  
			  if($scope.value==true)
				  {
				  $("#deleteStaffModal1").modal("hide");
				  $('.modal-backdrop').hide();
				  Flash.create('success', "You have Successfully Deleted!");
		          $scope.getStaffList();
			  $state.reload('dashboard.staff');
				  }
			  else
				  {
				  $("#deleteStaffModal1").modal("hide");
				  $('.modal-backdrop').hide();
				  $("#deleteStaffModal2").modal("show");
				    $scope.deleteStaffFromPatients=function()
				    {
				    	
				    	requestHandler.postRequest("Caller/releasePatientsFromCaller.json?id="+id).then(function(response){
				    		
				    		 requestHandler.deletePostRequest("Admin/deleteCaller.json?id=",id).then(function(results){
				    			 $("#deleteStaffModal2").modal("hide");
							    	$('.modal-backdrop').hide();
						          Flash.create('success', "You have Successfully Deleted!");
						          $scope.getStaffList();
				    		 });
				    		 
							});
				    };
				  
				  }
		     });
		  }
	};
    
    $scope.alertFunction=function()
    {
    	
    };
   
 
});





adminApp.controller('SaveStaffController', function($scope,$http,$location,$state,requestHandler,successMessageService,Flash) {
	
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
			 requestHandler.postRequest("Admin/saveUpdateCaller.json",$scope.staff).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/staff');
				});
			}
			else{
				$("#username_exists").text("UserName already exists");
			}
		});
		
		
	};

});


adminApp.controller('EditStaffController', function($scope,$http,$location,$stateParams,$state,requestHandler,successMessageService,Flash) {
	
	$scope.options=false;
	$scope.title=$state.current.title;
	var staffOriginal="";
	requestHandler.getRequest("Admin/getCaller.json?id="+$stateParams.id,"").then(function(response){
		staffOriginal=angular.copy(response.data.staffForm);
		$scope.staff=response.data.staffForm;
	});
	

	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateCaller.json",$scope.staff).then(function(response){
			  Flash.create('success', "You have Successfully Updated!");
			  $location.path('dashboard/staff');
			});
		};
		
	$scope.isClean = function() {
	        return angular.equals (staffOriginal, $scope.staff);
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
