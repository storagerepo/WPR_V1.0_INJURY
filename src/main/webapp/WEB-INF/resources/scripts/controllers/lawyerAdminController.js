var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ShowLawyerAdminController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="10";
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
    requestHandler.getRequest("Admin/getAllLawyerAdmins.json","").then(function(response){
		$scope.lawyerAdmins=response.data.lawyerAdminForms;
		$scope.sort('username');
	});
    
    $scope.getLawyerAdminList=function(){
    	requestHandler.getRequest("Admin/getAllLawyerAdmins.json","").then(function(response){
    		$scope.lawyerAdmins=response.data.lawyerAdminForms;
    	});
    };
	
	
	$scope.resetPassword=function(id){
		$("#resetLawyerAdminPassword").modal("show");
  	  $scope.resetLawyerAdminPassword=function()
		   {
  		requestHandler.getRequest("Admin/resetLawyerAdminPassword.json?id="+id,"").then(function(response) {
			 $scope.response=response.data.requestSuccess;
			 if($scope.response==true)
			 {
			 $("#resetLawyerAdminPassword").modal("hide");
			 $('.modal-backdrop').hide();
			 Flash.create('success', "You have Successfully Reset the Password!");
			 $scope.getLawyerAdminList();
			
			 }
			});
		   };
		
	};
	
});

adminApp.controller('SaveLawyerAdminController', function($http,$state,$scope,$location,requestHandler,Flash) {
	$scope.options=true;
	$scope.title=$state.current.title;
	
	$scope.saveLawyerAdmin=function(){
		$("#username_exists").text("");
		requestHandler.getRequest("Admin/checkUsernameExist.json?username="+$scope.lawyerAdmin.username,"").then(function(response){
			var isNew=response.data.isUserNameExist;
			if(isNew==0){
				$("#username_exists").text("");
			 requestHandler.postRequest("Admin/saveUpdateLawyerAdmin.json",$scope.lawyerAdmin).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/LawyerAdmin');
				});
			}
			else{
				$("#username_exists").text("UserName already exists");
			}
		});
	};
});

adminApp.controller('EditLawyerAdminController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	
	var lawyerAdminOriginal="";
	requestHandler.getRequest("Admin/getLawyerAdmin.json?id="+$stateParams.id,"").then(function(response){
		lawyerAdminOriginal=angular.copy(response.data.lawyerAdminForm);
		$scope.lawyerAdmin=response.data.lawyerAdminForm;
	});
	$scope.updateLawyerAdmin=function(){
		console.log("lawyer",$scope.lawyerAdmin);
		requestHandler.postRequest("Admin/saveUpdateLawyerAdmin.json",$scope.lawyerAdmin).then(function(response){
			  Flash.create('success', "You have Successfully Added!");
			  $location.path('dashboard/LawyerAdmin');
			});
	};
	$scope.isClean = function() {
        return angular.equals (lawyerAdminOriginal, $scope.lawyerAdmin);
    };
});