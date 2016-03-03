var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ShowLawyerController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="10";
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
   requestHandler.getRequest("Lawyer/getLawyersByLawyerAdmin.json","").then(function(response){
		$scope.lawyers=response.data.lawyersForms;
		$scope.sort('username');
	});
    
    $scope.getLawyerList=function(){
    	requestHandler.getRequest("Lawyer/getLawyersByLawyerAdmin.json","").then(function(response){
    		$scope.lawyers=response.data.lawyersForms;
    	});
    };
	
    // Enable Lawyer
    $scope.enableLawyer=function(lawyerId){
    	$scope.confirmation="Enable";
    	$scope.isEnable=true;
    	$("#enableOrDisableStaff").modal('show');
    	$scope.enable=function(){
    		requestHandler.getRequest("Lawyer/enableDisableLawyers.json?lawyerId="+lawyerId,"").then(function(response){
    	    	 if(response.data.requestSuccess){
    	    		 $("#enableOrDisableStaff").modal("hide");
   		          	$('.modal-backdrop').hide();
    	  		   Flash.create('success', "You have Successfully Enabled!");
    	    	 }
    	    	 $scope.getLawyerList();
    	  		});
    	};
    	
    };
    
    // Disable Lawyer
    $scope.disableLawyer=function(lawyerId){
    	$scope.confirmation="Disable";
    	$scope.isEnable=false;
    	$("#enableOrDisableStaff").modal('show');
    	$scope.disable=function(){
	    	requestHandler.getRequest("Lawyer/enableDisableLawyers.json?lawyerId="+lawyerId,"").then(function(response){
	    	 if(response.data.requestSuccess){
	    		$("#enableOrDisableStaff").modal("hide");
		        $('.modal-backdrop').hide();
	  		   Flash.create('success', "You have Successfully Disabled!");
	    	 }
	    	 $scope.getLawyerList();
	  		});
    	};
    };
    
    //Reset Password
    $scope.resetPassword=function(id){
		$("#resetLawyerPassword").modal("show");
  	  $scope.resetLawyerPassword=function()
		   {
  		requestHandler.getRequest("Lawyer/resetLawyerPassword.json?lawyerId="+id,"").then(function(response) {
			 $scope.response=response.data.requestSuccess;
			 if($scope.response==true)
			 {
			 $("#resetLawyerPassword").modal("hide");
			 $('.modal-backdrop').hide();
			 Flash.create('success', "You have Successfully Reset the Password!");
			 $scope.getLawyerList();
			
			 }
			});
		   };
	};
	
	// Delete Lawyer
	$scope.deleteLawyer=function(lawyerId)
    {
    	$("#deleteLawyerModal").modal("show");
    	$scope.deleteOneLawyer=function(){
        	 requestHandler.deletePostRequest("Lawyer/deleteLawyers.json?lawyerId=",lawyerId).then(function(results){
       			 $("#deleteLawyerModal").modal("hide");
   			    	$('.modal-backdrop').hide();
   		          Flash.create('success', "You have Successfully Deleted!");
   		          $scope.getLawyerList();
       		 });
       		 
    	};
    	
    };
	
});

adminApp.controller('SaveLawyerController', function($http,$state,$scope,$location,requestHandler,Flash) {
	$scope.options=true;
	$scope.title=$state.current.title;
	
	$scope.lawyer={};
	$scope.lawyer.countyId=[];
	$scope.lawyer.countyForms=[];
	$scope.selectedCounties=function(countyId){
		var idx=$scope.lawyer.countyId.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.lawyer.countyId.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.lawyer.countyId.push(countyId);
		}
		console.log($scope.lawyer.countyId);
	};
	
	// Get County List
	 requestHandler.getRequest("Lawyer/getAllCountys.json","").then(function(response){
			$scope.lawyer.countyForms=response.data.countyForms;
	});
	
	
	$scope.saveLawyer=function(){
		$("#username_exists").text("");
		requestHandler.getRequest("Lawyer/checkUsernameExist.json?username="+$scope.lawyer.username,"").then(function(response){
			var isNew=response.data.isUserNameExist;
			if(isNew==0){
				$("#username_exists").text("");
			 requestHandler.postRequest("Lawyer/saveUpdateLawyers.json",$scope.lawyer).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/Lawyer');
				});
			}
			else{
				$("#username_exists").text("UserName already exists");
			}
		});
	};
});

adminApp.controller('EditLawyerController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	
	var lawyerOriginal="";
	requestHandler.getRequest("Lawyer/getLawyers.json?id="+$stateParams.id,"").then(function(response){
		lawyerOriginal=angular.copy(response.data.lawyersForm);
		$scope.lawyer=response.data.lawyersForm;
		console.log("County Selected",$scope.lawyer.countyId);
	});
	$scope.updateLawyer=function(){
		requestHandler.postRequest("Lawyer/saveUpdateLawyers.json",$scope.lawyer).then(function(response){
			  Flash.create('success', "You have Successfully Added!");
			  $location.path('dashboard/Lawyer');
			});
	};
	
	// County Selection
	
	$scope.selectedCounties=function(countyId){
		var idx=$scope.lawyer.countyId.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.lawyer.countyId.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.lawyer.countyId.push(countyId);
		}
	};
	
	$scope.isClean = function() {
        return angular.equals (lawyerOriginal, $scope.lawyer);
    };
});