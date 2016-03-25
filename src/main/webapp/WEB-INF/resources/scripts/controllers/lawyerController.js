var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ShowLawyerController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="10";
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
   requestHandler.getRequest("LAdmin/getLawyersByLawyerAdmin.json","").then(function(response){
		$scope.lawyers=response.data.lawyersForms;
		$scope.sort('username');
	});
    
    $scope.getLawyerList=function(){
    	requestHandler.getRequest("LAdmin/getLawyersByLawyerAdmin.json","").then(function(response){
    		$scope.lawyers=response.data.lawyersForms;
    	});
    };
	
    // Enable Lawyer
    $scope.enableLawyer=function(lawyerId){
    	$scope.confirmation="Enable";
    	$scope.isEnable=true;
    	$("#enableOrDisableStaff").modal('show');
    	$scope.enable=function(){
    		requestHandler.getRequest("La/enableDisableLawyers.json?lawyerId="+lawyerId,"").then(function(response){
    	    	 if(response.data.requestSuccess){
    	    		 $("#enableOrDisableStaff").modal("hide");
   		          	$('.modal-backdrop').hide();
    	  		   Flash.create('success', "You have Successfully Enabled!");
    	    	 }
    	    	 $scope.getLawyerList();
    	  		});
    	};
    	
    };
    
    $scope.enableOrDisableLawyer=function(lawyerId){
    	 
    	requestHandler.postRequest("LAdmin/enableOrDisableLawyers.json?lawyerId="+lawyerId,"").then(function(response){
    		 $scope.response=response.data.requestSuccess;
			 if($scope.response==true)
			 {
			 Flash.create('success', "You have Successfully Updated!");
			 $scope.getLawyerList();
    	}
    	});
    };
	
    
    //Reset Password
    $scope.resetPassword=function(lawyerId){
		$("#resetLawyerPassword").modal("show");
  	  $scope.resetLawyerPassword=function()
		   {
  		requestHandler.postRequest("LAdmin/resetLawyerPassword.json?lawyerId="+lawyerId,"").then(function(response) {
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
	
	$scope.viewLawyerModal=function(lawyerId){
		$("#viewLawyerModal").modal("show");
		requestHandler.getRequest("LAdmin/getLawyers.json?lawyerId="+lawyerId,"").then(function(response){
			$scope.lawyer=response.data.lawyersForm;
			
			 requestHandler.getRequest("Lawyer/getAllCountys.json","").then(function(response){
					$scope.lawyer.countyForms=response.data.countyForms;
			});

			 $scope.countyNames=[];
			   $.each($scope.lawyer.countyForms,function(index,value){
         
             $.each($scope.lawyer.county,function(index,value1){
            	
            	if(value.id === value1){
            		$scope.countyNames.push(value.name);
            	}
             });
         });
		});

		
	};
	
});

adminApp.controller('SaveLawyerController', function($http,$state,$scope,$location,requestHandler,Flash) {
	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.isAdd=true;
	
	$scope.lawyer={};
	$scope.lawyer.county=[];
	$scope.lawyer.countyForms=[];
	$scope.requiredValue= false;
	$scope.selectedCounties=function(countyId){
		var idx=$scope.lawyer.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.lawyer.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.lawyer.county.push(countyId);
		}
		console.log($scope.lawyer.county);
		if($scope.lawyer.county.length==0){
			$scope.requiredValue= false;
		}
		else if($scope.lawyer.county.length>0){
		$scope.requiredValue= true;
		}
	};
	
	// Get County List
	 requestHandler.getRequest("Lawyer/getAllCountys.json","").then(function(response){
			$scope.lawyer.countyForms=response.data.countyForms;
	});
	
	
	$scope.saveLawyer=function(){
	/*	$("#username_exists").text("");
		requestHandler.getRequest("Lawyer/checkUsernameExist.json?username="+$scope.lawyer.username,"").then(function(response){
			var isNew=response.data.isUserNameExist;
			if(isNew==0){
				$("#username_exists").text("");*/
			 requestHandler.postRequest("LAdmin/saveUpdateLawyers.json",$scope.lawyer).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/Lawyer');
				});
		/*	}
			else{
				$("#username_exists").text("UserName already exists");
			}
		});*/
	};
});

adminApp.controller('EditLawyerController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	$scope.requiredValue=true;
	$scope.isAdd=false;
	
	var lawyerOriginal="";
	requestHandler.getRequest("LAdmin/getLawyers.json?lawyerId="+$stateParams.lawyerId,"").then(function(response){
		lawyerOriginal=angular.copy(response.data.lawyersForm);
		$scope.lawyer=response.data.lawyersForm;
		console.log("County Selected",$scope.lawyer.county);
	});
	$scope.updateLawyer=function(){
		requestHandler.postRequest("LAdmin/saveUpdateLawyers.json",$scope.lawyer).then(function(response){
			  Flash.create('success', "You have Successfully Added!");
			  $location.path('dashboard/Lawyer');
			});
	};
	
	// County Selection
	
	$scope.selectedCounties=function(countyId){
		var idx=$scope.lawyer.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.lawyer.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.lawyer.county.push(countyId);
		}
		if($scope.lawyer.county.length==0){
			$scope.requiredValue= false;
		}
		else if($scope.lawyer.county.length>0){
		$scope.requiredValue= true;
		}
	};
	
	$scope.isClean = function() {
        return angular.equals (lawyerOriginal, $scope.lawyer);
    };
});