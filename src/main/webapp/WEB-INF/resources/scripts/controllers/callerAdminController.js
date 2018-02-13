var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('ShowCallerAdminController',function($http,$state,$scope,$location,requestHandler,Flash){
	
	$scope.noOfRows="25";
	$scope.roleId=$state.current.roleId;
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
   
   
    if($scope.roleId==2){
    	$scope.addLink="dashboard.add-calleradmin";
    	$scope.editLink="edit-calleradmin";
    	$scope.headingText="Call Center Admin";
    	$scope.addButtonText="Add Call Center Admin";
    	$scope.hintText="Caller Admin";
    }else if($scope.roleId==6){
    	$scope.addLink="dashboard.add-automanager";
    	$scope.editLink="edit-automanager";
    	$scope.headingText="Dealer Manager";
    	$scope.addButtonText="Add Dealer Manager";
    	$scope.hintText="Dealer Manager";
    }else if($scope.roleId==8){
    	$scope.addLink="dashboard.add-bodyshopowner";
    	$scope.editLink="edit-bodyshopowner";
    	$scope.headingText="Body Shop Owners";
    	$scope.addButtonText="Add Body Shop Owners";
    	$scope.hintText="Body Shop Owners";
    }
	
    requestHandler.getRequest("Admin/getAllCallerAdmins.json?roleId="+$scope.roleId,"").then(function(response){
		$scope.callerAdmins=response.data.callerAdminForms;
		$scope.sort('username');
	});
    
    $scope.getCallerAdminList=function(){
    	requestHandler.getRequest("Admin/getAllCallerAdmins.json?roleId="+$scope.roleId,"").then(function(response){
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
	
	$scope.viewRating=function(userId){
		  requestHandler.getRequest("/getRatingReviewsByPassingUserId.json?userId="+userId,"").then(function(response){
		  			if(response.data.ratingReviewsForm.ratingReviewId!=null){
		  				$scope.isAvailable=true;
		  				$scope.ratingQ1Style={'width':response.data.ratingReviewsForm.ratingQ1*20+'%'};
		  				$scope.ratingQ2Style={'width':response.data.ratingReviewsForm.ratingQ2*20+'%'};
		  				$scope.ratingQ3Style={'width':response.data.ratingReviewsForm.ratingQ3*20+'%'};
		  				$scope.ratingQ4Style={'width':response.data.ratingReviewsForm.ratingQ4*20+'%'};
		  				$scope.ratingQ5Style={'width':response.data.ratingReviewsForm.ratingQ5*20+'%'};
		  				$scope.ratingQ1=response.data.ratingReviewsForm.ratingQ1;
		  				$scope.ratingQ2=response.data.ratingReviewsForm.ratingQ2;
		  				$scope.ratingQ3=response.data.ratingReviewsForm.ratingQ3;
		  				$scope.ratingQ4=response.data.ratingReviewsForm.ratingQ4;
		  				$scope.ratingQ5=response.data.ratingReviewsForm.ratingQ5;
		  				$scope.reviewQ1=response.data.ratingReviewsForm.reviewQ1;
		  				$scope.reviewQ2=response.data.ratingReviewsForm.reviewQ2;
		  				$scope.reviewQ3=response.data.ratingReviewsForm.reviewQ3;
		  				$scope.reviewQ4=response.data.ratingReviewsForm.reviewQ4;
		  			}else{
		  				$scope.isAvailable=false;
		  			}

		  			$("#viewRatingModal").modal('show');
		  		});
	};
});

adminApp.controller('SaveCallerAdminController', function($http,$state,$scope,$location,requestHandler,Flash) {
	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.isAdd=true;

	$scope.callerAdmin={};
	$scope.callerAdmin.isDrivingDistance=0;
	$scope.callerAdmin.county=[];
	$scope.callerAdmin.countyForms=[];
	$scope.requiredValue= false;
	
	$scope.roleId=$state.current.roleId;
    if($scope.roleId==2){
    	$scope.backLink="dashboard.calleradmin";
    	$scope.detailsText="Enter Call Center Admin Details";
    }else if($scope.roleId==6){
    	$scope.backLink="dashboard.automanager";
    	$scope.detailsText="Enter Dealer Manager Details";
    }else if($scope.roleId==8){
    	$scope.backLink="dashboard.bodyshopowner";
    	$scope.detailsText="Enter Body Shop Owner Details";
    }
	
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
	
	 $scope.isChecked=function(){
			if($scope.callerAdmin.countyForms.length>0){
				$.each($scope.callerAdmin.countyForms, function(index,value) {
					if(document.getElementById('checkAll').checked==true){
						$scope.callerAdmin.county.push(value.id);
						$("#"+value.id).checked==true;
						//$("#"+value.id).prop('checked',$(this).prop("checked"));
						$scope.requiredValue= true;
					}
					else if(document.getElementById('checkAll').checked==false){
						$scope.callerAdmin.county=[];
						//$("#"+value.id).prop('checked', $(this).prop("checked"));
						$("#"+value.id).checked==false;
						$scope.requiredValue= false;
					}
				});
				
			}
		};
		
	
	// Get County List
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.callerAdmin.countyForms=response.data.countyForms;
	});
	 
	 
	
	
	$scope.saveCallerAdmin=function(){
		$scope.callerAdmin.roleId=$scope.roleId;
			 requestHandler.postRequest("Admin/saveUpdateCallerAdmin.json",$scope.callerAdmin).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  if($scope.roleId==2){
					  $location.path('dashboard/calleradmin');
				  }else if($scope.roleId==6){
					  $location.path('dashboard/automanager');
				  }else if($scope.roleId==8){
					  $location.path('dashboard/bodyshopowner');
				  }
		});
			
	};
});

adminApp.controller('EditCallerAdminController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	$scope.requiredValue=true;
	$scope.isAdd=false;
	
	$scope.callerAdmin={};
	$scope.callerAdmin.county=[];
	$scope.callerAdmin.countyForms=[];
	
	$scope.roleId=$state.current.roleId;
    if($scope.roleId==2){
    	$scope.backLink="dashboard.calleradmin";
    	$scope.detailsText="Enter Call Center Admin Details";
    }else if($scope.roleId==6){
    	$scope.backLink="dashboard.automanager";
    	$scope.detailsText="Enter Dealer Manager Details";
    }else if($scope.roleId==8){
    	$scope.backLink="dashboard.bodyshopowner";
    	$scope.detailsText="Enter Body Shop Owner Details";
    }
    
	var callerAdminOriginal="";
	requestHandler.getRequest("Admin/getCallerAdmin.json?callerAdminId="+$stateParams.callerAdminId,"").then(function(response){
		callerAdminOriginal=angular.copy(response.data.callerAdminForm);
		$scope.callerAdmin=response.data.callerAdminForm;
		
		if($scope.callerAdmin.countyForms.length==$scope.callerAdmin.county.length){
			document.getElementById('checkAll').checked=true;
		}
		else{
			document.getElementById('checkAll').checked=false;
		}
	});
	$scope.updateCallerAdmin=function(){
		console.log("caller",$scope.callerAdmin);
		requestHandler.postRequest("Admin/saveUpdateCallerAdmin.json",$scope.callerAdmin).then(function(response){
			  Flash.create('success', "You have Successfully Updated!");
			  if($scope.roleId==2){
				  $location.path('dashboard/calleradmin');
			  }else if($scope.roleId==6){
				  $location.path('dashboard/automanager');
			  }else if($scope.roleId==8){
				  $location.path('dashboard/bodyshopowner');
			  }
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
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.callerAdmin.countyForms=response.data.countyForms;
	});
	 
	 
	$scope.isChecked=function(){
		if($scope.callerAdmin.countyForms.length>0){
			$.each($scope.callerAdmin.countyForms, function(index,value) {
				if(document.getElementById('checkAll').checked==true){
					$scope.callerAdmin.county.push(value.id);
					$("#"+value.id).checked==true;
					//$("#"+value.id).prop('checked',$(this).prop("checked"));
					$scope.requiredValue= true;
				}
				else if(document.getElementById('checkAll').checked==false){
					$scope.callerAdmin.county=[];
					//$("#"+value.id).prop('checked', $(this).prop("checked"));
					$("#"+value.id).checked==false;
					$scope.requiredValue= false;
				}
			});
			
		}
	};
	
	
	$scope.isClean = function() {
        return angular.equals (callerAdminOriginal, $scope.callerAdmin);
    };
});

adminApp.controller('PatientResponseController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="25";
	
	$scope.addPatientResponseModel=function(){
		$scope.modelName="Add";
		$("#patientResponseModel").modal("show");
		
  	  	$scope.addResponse=function(){
  	  		alert("call Add Response");
  	  		/*requestHandler.postRequest("Admin/resetCallerAdminPassword.json?callerAdminId="+callerAdminId,"").then(function(response) {
				 $scope.response=response.data.requestSuccess;
				 if($scope.response==true)
				 {
				 $("#resetCallerAdminPassword").modal("hide");
				 $('.modal-backdrop').hide();
				 Flash.create('success', "You have Successfully Reset the Password!");
				 $scope.getCallerAdminList();
				
				 }
			});*/
		};
		
	};
	
	$scope.EditPatientResponseModel=function(id){
		$scope.modelName="Edit";
		$("#patientResponseModel").modal("show");
		
  	  	$scope.addResponse=function(){
  	  		alert("call Add Response");
  	  		/*requestHandler.postRequest("Admin/resetCallerAdminPassword.json?callerAdminId="+callerAdminId,"").then(function(response) {
				 $scope.response=response.data.requestSuccess;
				 if($scope.response==true)
				 {
				 $("#resetCallerAdminPassword").modal("hide");
				 $('.modal-backdrop').hide();
				 Flash.create('success', "You have Successfully Reset the Password!");
				 $scope.getCallerAdminList();
				
				 }
			});*/
		};
		
	};
	
});