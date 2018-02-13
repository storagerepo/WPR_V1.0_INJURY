var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ShowCallersController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="25";
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
    $scope.sortKey = 'username';
    $scope.roleId=$state.current.roleId;
    if($scope.roleId==4){
    	$scope.addLink="dashboard.addCaller";
    	$scope.editLink="EditCaller";
    	$scope.headingText="Callers";
    	$scope.addButtonText="Add Caller";
    	$scope.hintText="Caller";
    }else if($scope.roleId==7){
    	$scope.addLink="dashboard.addAutoDealer";
    	$scope.editLink="editAutoDealer";
    	$scope.headingText="Dealers";
    	$scope.addButtonText="Add Dealer";
    	$scope.hintText="Dealer";
    }else if($scope.roleId==9){
    	$scope.addLink="dashboard.add-shop";
    	$scope.editLink="edit-shop";
    	$scope.headingText="Shops";
    	$scope.addButtonText="Add Shop";
    	$scope.hintText="Shop";
    }
    
    $scope.getCallersList=function(){
    	requestHandler.getRequest("CAdmin/getCallersByCallerAdmin.json","").then(function(response){
    		$scope.callers=response.data.callerForms;
    	});
    };
    
    $scope.getCallersList();
    
    $scope.enableOrDisbaleCaller=function(callerId){
 
    	requestHandler.postRequest("CAdmin/enableOrDisableCaller.json?callerId="+callerId,"").then(function(response){
    		 $scope.response=response.data.requestSuccess;
			 if($scope.response==true){
				 Flash.create('success', "You have Successfully Updated!");
				 $scope.getCallersList();
				 $(function(){
						$("html,body").scrollTop(0);
				});
			 }
    	});
    };
	
	
	$scope.resetPassword=function(callerId){
		$("#resetCallerPassword").modal("show");
  	  	$scope.resetCallerPassword=function(){
  	  		requestHandler.postRequest("CAdmin/resetCallerPassword.json?callerId="+callerId,"").then(function(response) {
				$scope.response=response.data.requestSuccess;
				if($scope.response==true){
					$("#resetCallerPassword").modal("hide");
					$('.modal-backdrop').hide();
					Flash.create('success', "You have Successfully Reset the Password!");
					$scope.getCallersList();
					$(function(){
						$("html,body").scrollTop(0);
					});
				}
  	  		});
  	  	};
	};
	
	$scope.deleteCallerConfirmModal=function(callerId){
		$("#deleteCallerConfirmOne").modal("show");
  	  	$scope.deleteCaller=function(){
  	  		requestHandler.postRequest("CAdmin/deleteCaller.json?callerId="+callerId,"").then(function(response) {
				if(response.data.isDeleteable==true){
					$("#deleteCallerConfirmOne").modal("hide");
					$('.modal-backdrop').hide();
					 if($scope.roleId==4){
						 Flash.create('success', "You have Successfully Deleted the Caller!");
					 }else if($scope.roleId==7){
						 Flash.create('success', "You have Successfully Deleted the Dealer!");
					 }else if($scope.roleId==9){
						 Flash.create('success', "You have Successfully Deleted the Shop!");
					 }
					
					$scope.getCallersList();
					$(function(){
						$("html,body").scrollTop(0);
					});
				}else{
					$("#deleteCallerConfirmOne").modal("hide");
					$('.modal-backdrop').hide();
					$("#deleteCallerConfirmTwo").modal("show");
					$scope.deleteCallerWithMap=function(){
						requestHandler.postRequest("CAdmin/deleteCallerWithMap.json?callerId="+callerId,"").then(function(response) {
							$("#deleteCallerConfirmTwo").modal("hide");
							$('.modal-backdrop').hide();
							 if($scope.roleId==4){
								 Flash.create('success', "You have Successfully Deleted the Caller!");
							 }else if($scope.roleId==7){
								 Flash.create('success', "You have Successfully Deleted the Dealer!");
							 }else if($scope.roleId==9){
								 Flash.create('success', "You have Successfully Deleted the Shop!");
							 }
							$scope.getCallersList();
							$(function(){
								$("html,body").scrollTop(0);
							});
						});
					};
				}
  	  		});
  	  	};
	};
	
});

adminApp.controller('SaveCallerController', function($http,$state,$scope,$location,requestHandler,Flash) {
	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.isAdd=true;
	$scope.caller={};
	$scope.caller.county=[];
	$scope.caller.countyForms=[];
	$scope.requiredValue= false;
	
	$scope.roleId=$state.current.roleId;
    if($scope.roleId==4){
    	$scope.backLink="dashboard.callers";
    	$scope.detailsText="Enter Caller Details";
    }else if($scope.roleId==7){
    	$scope.backLink="dashboard.autoDealer";
    	$scope.detailsText="Enter Dealer Details";
    }else if($scope.roleId==9){
    	$scope.backLink="dashboard.shops";
    	$scope.detailsText="Enter Shop Details";
    }
	
	$scope.selectedCounties=function(countyId){
		
		var idx=$scope.caller.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.caller.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.caller.county.push(countyId);
		}
		if($scope.caller.county.length==0){
			$scope.requiredValue= false;
		}
		else if($scope.caller.county.length>0){
		$scope.requiredValue= true;
		}
		
	};
	
	// Get County List
	 requestHandler.getRequest("Caller/getAllCountys.json","").then(function(response){
			$scope.caller.countyForms=response.data.countyForms;
	});
	
	
	$scope.saveCaller=function(){
		/*$("#username_exists").text("");
		requestHandler.getRequest("Admin/checkUsernameExist.json?username="+$scope.lawyerAdmin.username,"").then(function(response){
			var isNew=response.data.isUserNameExist;
			if(isNew==0){
				$("#username_exists").text("");*/
			$scope.caller.roleId=$scope.roleId;
			 requestHandler.postRequest("CAdmin/saveUpdateCaller.json",$scope.caller).then(function(response){
				  Flash.create('success', "You have Successfully Added!");
				  if($scope.roleId==4){
					   $location.path('dashboard/Callers');
				   }else if($scope.roleId==7){
					   $location.path('dashboard/autoDealer');
				   }else if($scope.roleId==9){
					   $location.path('dashboard/shops');
				   }
				  
				});
			 /*}
			else{
				$("#username_exists").text("UserName already exists");
			}
		});*/
	};
});

adminApp.controller('EditCallerController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	$scope.requiredValue=true;
	$scope.isAdd=false;
	
	$scope.roleId=$state.current.roleId;
    if($scope.roleId==4){
    	$scope.backLink="dashboard.callers";
    	$scope.detailsText="Enter Caller Details";
    }else if($scope.roleId==7){
    	$scope.backLink="dashboard.autoDealer";
    	$scope.detailsText="Enter Dealer Details";
    }else if($scope.roleId==9){
    	$scope.backLink="dashboard.shops";
    	$scope.detailsText="Enter Shop Details";
    }
	
	var callerOriginal="";
	requestHandler.getRequest("CAdmin/getCaller.json?callerId="+$stateParams.callerId,"").then(function(response){
		callerOriginal=angular.copy(response.data.callerForm);
		$scope.caller=response.data.callerForm;
	});
	
	$scope.updateCaller=function(){
		requestHandler.postRequest("CAdmin/saveUpdateCaller.json",$scope.caller).then(function(response){
			Flash.create('success', "You have Successfully Updated!");
			if($scope.roleId==4){
				$location.path('dashboard/Callers');
			}else if($scope.roleId==7){
				$location.path('dashboard/autoDealer');
			}else if($scope.roleId==9){
				$location.path('dashboard/shops');
			}
		});
	};
	
// County Selection
	$scope.selectedCounties=function(countyId){
		var idx=$scope.caller.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.caller.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.caller.county.push(countyId);
		}
		if($scope.caller.county.length==0){
			$scope.requiredValue= false;
		}
		else if($scope.caller.county.length>0){
		$scope.requiredValue= true;
		}
	};
	
	$scope.isClean = function() {
        return angular.equals (callerOriginal, $scope.caller);
    };
});