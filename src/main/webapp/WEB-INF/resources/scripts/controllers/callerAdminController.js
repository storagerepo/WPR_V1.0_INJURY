var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('ShowCallerAdminController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="10";
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
    requestHandler.getRequest("Admin/getAllCallerAdmins.json","").then(function(response){
		$scope.callerAdmins=response.data.callerAdminForms;
		$scope.sort('username');
	});
    
    $scope.getCallerAdminList=function(){
    	requestHandler.getRequest("Admin/getAllCallerAdmins.json","").then(function(response){
    		$scope.callerAdmins=response.data.callerAdminForms;
    	});
    };
    
    $scope.enableOrDisbaleCallerAdmin=function(callerAdminId){
 
    	requestHandler.postRequest("Admin/enableOrDisableCallerAdmin.json?callerAdminId="+callerAdminId,"").then(function(response){
    		 $scope.response=response.data.requestSuccess;
			 if($scope.response==true)
			 {
			 Flash.create('success', "You have Successfully Updated!");
			 $scope.getCallerAdminList();
    	}
    	});
    };
	
	
	$scope.resetPassword=function(callerAdminId){
		$("#resetCallerAdminPassword").modal("show");
  	  $scope.resetCallerAdminPassword=function()
		   {
  		requestHandler.postRequest("Admin/resetCallerAdminPassword.json?callerAdminId="+callerAdminId,"").then(function(response) {
			 $scope.response=response.data.requestSuccess;
			 if($scope.response==true)
			 {
			 $("#resetCallerAdminPassword").modal("hide");
			 $('.modal-backdrop').hide();
			 Flash.create('success', "You have Successfully Reset the Password!");
			 $scope.getCallerAdminList();
			
			 }
			});
		   };
		
	};
	
});

adminApp.controller('SaveCallerAdminController', function($http,$state,$scope,$location,requestHandler,Flash) {
	$scope.options=true;
	$scope.title=$state.current.title;
	
	$scope.callerAdmin={};
	$scope.callerAdmin.county=[];
	$scope.callerAdmin.countyForms=[];
	$scope.requiredValue= false;
	$scope.selectedCounties=function(countyId){
		
		var idx=$scope.callerAdmin.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.callerAdmin.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.callerAdmin.county.push(countyId);
		}
		console.log($scope.callerAdmin.county.length);
		if($scope.callerAdmin.county.length==0){
			$scope.requiredValue= false;
		}
		else if($scope.callerAdmin.county.length>0){
		$scope.requiredValue= true;
		}
		
	};
	
	// Get County List
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.callerAdmin.countyForms=response.data.countyForms;
	});
	
	
	$scope.saveCallerAdmin=function(){
		/*$("#username_exists").text("");
		requestHandler.getRequest("Admin/checkUsernameExist.json?username="+$scope.lawyerAdmin.username,"").then(function(response){
			var isNew=response.data.isUserNameExist;
			if(isNew==0){
				$("#username_exists").text("");*/
			 requestHandler.postRequest("Admin/saveUpdateCallerAdmin.json",$scope.callerAdmin).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/CallerAdmin');
				});
			 /*}
			else{
				$("#username_exists").text("UserName already exists");
			}
		});*/
	};
});

adminApp.controller('EditCallerAdminController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	$scope.requiredValue=true;
	
	
	var callerAdminOriginal="";
	requestHandler.getRequest("Admin/getCallerAdmin.json?callerAdminId="+$stateParams.callerAdminId,"").then(function(response){
		callerAdminOriginal=angular.copy(response.data.callerAdminForm);
		$scope.callerAdmin=response.data.callerAdminForm;
		console.log("selected county",$scope.callerAdmin.county);
	});
	$scope.updateCallerAdmin=function(){
		console.log("caller",$scope.callerAdmin);
		requestHandler.postRequest("Admin/saveUpdateCallerAdmin.json",$scope.callerAdmin).then(function(response){
			  Flash.create('success', "You have Successfully Added!");
			  $location.path('dashboard/CallerAdmin');
			});
	};
	
	
// County Selection
	$scope.selectedCounties=function(countyId){
		var idx=$scope.callerAdmin.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.callerAdmin.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.callerAdmin.county.push(countyId);
		}
		if($scope.callerAdmin.county.length==0){
			$scope.requiredValue= false;
		}
		else if($scope.callerAdmin.county.length>0){
		$scope.requiredValue= true;
		}
	};
	
	$scope.isClean = function() {
        return angular.equals (callerAdminOriginal, $scope.callerAdmin);
    };
});