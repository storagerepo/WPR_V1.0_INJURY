var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('ShowLawyerAdminController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.noOfRows="25";
	
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
	
    $scope.enableOrDisableLawyerAdmin=function(lawyerAdminId){
    	
    	requestHandler.postRequest("Admin/enableOrDisableLawyerAdmin.json?lawyerAdminId="+lawyerAdminId,"").then(function(response){
    		 $scope.response=response.data.requestSuccess;
			 if($scope.response==true)
			 {
			 Flash.create('success', "You have Successfully Updated!");
			 $scope.getLawyerAdminList();
    	}
    	});
    };
    
	$scope.resetPassword=function(lawyerAdminId){
		$("#resetLawyerAdminPassword").modal("show");
  	  $scope.resetLawyerAdminPassword=function()
		   {
  		requestHandler.postRequest("Admin/resetLawyerAdminPassword.json?lawyerAdminId="+lawyerAdminId,"").then(function(response) {
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
	$scope.isAdd=true;
	
	$scope.lawyerAdmin={};
	$scope.lawyerAdmin.county=[];
	$scope.lawyerAdmin.countyform=[];
	$scope.requiredValue=false;
	$scope.selectedCounties=function(countyId){
		var idx=$scope.lawyerAdmin.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.lawyerAdmin.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.lawyerAdmin.county.push(countyId);
		}
		console.log($scope.lawyerAdmin.county);
		if($scope.lawyerAdmin.county.length==0){
			$scope.requiredValue=false;
		}
		else if($scope.lawyerAdmin.county.length>0){
			$scope.requiredValue=true;
		}
	};
	
	
	 $scope.isChecked=function(){
			if($scope.lawyerAdmin.countyform.length>0){
				$.each($scope.lawyerAdmin.countyform, function(index,value) {
					if(document.getElementById('checkAll').checked==true){
						$scope.lawyerAdmin.county.push(value.id);
						$("#"+value.id).checked==true;
						//$("#"+value.id).prop('checked',$(this).prop("checked"));
						$scope.requiredValue= true;
					}
					else if(document.getElementById('checkAll').checked==false){
						$scope.lawyerAdmin.county=[];
						//$("#"+value.id).prop('checked', $(this).prop("checked"));
						$("#"+value.id).checked==false;
						$scope.requiredValue= false;
					}
				});
				
			}
		};
	
	// Get County List
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.lawyerAdmin.countyform=response.data.countyForms;
	});
	
	$scope.saveLawyerAdmin=function(){
	
			 requestHandler.postRequest("/Admin/saveUpdateLawyerAdmin.json",$scope.lawyerAdmin).then(function(response){
				 console.log("$scope.lawyerAdmin");
				  Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/LawyerAdmin');
				});
			
	};
});

adminApp.controller('EditLawyerAdminController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
	$scope.options=false;
	$scope.title=$state.current.title;
	$scope.requiredValue=true;
	$scope.isAdd=false;
	
	$scope.lawyerAdmin={};
	$scope.lawyerAdmin.county=[];
	$scope.lawyerAdmin.countyform=[];
	
	var lawyerAdminOriginal="";
	requestHandler.getRequest("Admin/getLawyerAdmin.json?lawyerAdminId="+$stateParams.lawyerAdminId,"").then(function(response){
		lawyerAdminOriginal=angular.copy(response.data.lawyerAdminForm);
		$scope.lawyerAdmin=response.data.lawyerAdminForm;
		
		if($scope.lawyerAdmin.countyform.length==$scope.lawyerAdmin.county.length){
			document.getElementById('checkAll').checked=true;
		}
		else{
			document.getElementById('checkAll').checked=false;
		}
	});
	
	
	$scope.updateLawyerAdmin=function(){
		console.log("lawyer",$scope.lawyerAdmin);
		requestHandler.postRequest("Admin/saveUpdateLawyerAdmin.json",$scope.lawyerAdmin).then(function(response){
			  Flash.create('success', "You have Successfully Added!");
			  $location.path('dashboard/LawyerAdmin');
			});
	};
	
	
	// County Selection
	$scope.selectedCounties=function(countyId){
		var idx=$scope.lawyerAdmin.county.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.lawyerAdmin.county.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.lawyerAdmin.county.push(countyId);
		}
		if($scope.lawyerAdmin.county.length==0){
			$scope.requiredValue=false;
		}
		else if($scope.lawyerAdmin.county.length>0){
			$scope.requiredValue=true;
		}
	};
	
	 $scope.isChecked=function(){
			if($scope.lawyerAdmin.countyform.length>0){
				$.each($scope.lawyerAdmin.countyform, function(index,value) {
					if(document.getElementById('checkAll').checked==true){
						$scope.lawyerAdmin.county.push(value.id);
						$("#"+value.id).checked==true;
						//$("#"+value.id).prop('checked',$(this).prop("checked"));
						$scope.requiredValue= true;
					}
					else if(document.getElementById('checkAll').checked==false){
						$scope.lawyerAdmin.county=[];
						//$("#"+value.id).prop('checked', $(this).prop("checked"));
						$("#"+value.id).checked==false;
						$scope.requiredValue= false;
					}
				});
				
			}
		};
		
		requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.lawyerAdmin.countyform=response.data.countyForms;
	});
	
	$scope.isClean = function() {
        return angular.equals (lawyerAdminOriginal, $scope.lawyerAdmin);
    };
});