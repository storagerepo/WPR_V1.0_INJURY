var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('ShowPoliceDepartmentsController',function($http,$state,$scope,$location,requestHandler,Flash){
	
	$scope.noOfRows="25";
	$scope.selectedReportPullingType="0";
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
  //getting county List
	$scope.getCountyList=function()
	{
		 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
				$scope.countyform=response.data.countyForms;
				
		});
	}
	$scope.getCountyList();
	
	//getting Police Department List
	$scope.getPoliceDepartments=function()
	{
		requestHandler.getRequest("Admin/getAllPoliceAgencys.json","").then(function(response){
			$scope.policeDepartmentsForm=response.data.policeAgencyForms;
			
			$scope.sort('mapId');
	});
	}
		$scope.getPoliceDepartments();

		//Filter County
		$scope.CountyFilterFunction=function(selectedCounty)
		{
			if(selectedCounty==null)
				{
				$scope.selectedCounty=0;
				}
			console.log($scope.selectedCounty);
		}
		
		$scope.searchparam=function()
		{
			requestHandler.getRequest("Admin/searchPoliceDepartments.json?countyParam="+$scope.selectedCounty+"&reportPullingTypeParam="+$scope.selectedReportPullingType).then(function(response){
				$scope.policeDepartmentsForm=response.data.policeAgencyForms;
				console.log($scope.policeDepartmentsForm);
		
		
});
		};
		
		$scope.deletePoliceDepartment=function(mapId)
		{
			$scope.modalHeader="Confirmation";
			$scope.modalContent="Do you want to delete this Department?";
			$scope.modalButtonText="No";
            $scope.switchingmodalbutton=true;
			
			$scope.confirmDeletePoliceDepartment=function()
			{	
				$('#deletePoliceDepartmentModal').modal('hide');
					requestHandler.getRequest("Admin/deletePoliceAgency.json?mapId="+mapId).then(function(response)
						{
					$scope.status=response.data.status;
					if($scope.status==0)
					{
					$scope.modalHeader="Warning";
					$scope.modalContent="There is a available records uploaded by this Department!";
					$scope.modalButtonText="Ok";
					$scope.switchingmodalbutton=false;
					setTimeout(function () {
						$('#deletePoliceDepartmentModal').modal('show');
				    }, 2000);
						
			
					}
				else
					{
					 Flash.create('success', "Action completed successfully");
					 $scope.getPoliceDepartments=function()
						{
							requestHandler.getRequest("Admin/getAllPoliceAgencys.json","").then(function(response){
								$scope.policeDepartmentsForm=response.data.policeAgencyForms;
								
						});
						}
							$scope.getPoliceDepartments();
					}
				
						});
			}
			
		}
		
});


adminApp.controller('SavePoliceDepartmentController', function($http,$state,$scope,$location,requestHandler,Flash)
		{
	$scope.title=$state.current.title;
    $scope.options=true;
    
  //getting county List
	$scope.getCountyList=function()
	{
		 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
				$scope.countyform=response.data.countyForms;
				
		});
	}
	$scope.getCountyList();
	
	//saving Police Department
	$scope.savePoliceDepartment=function()
		{
		if($scope.policeAgencyForm.schedulerType==4)
			{
			$scope.policeAgencyForm.agencyId=0;
			}
		$scope.policeAgencyForm.mapId=null;
		console.log($scope.policeAgencyForm);
		requestHandler.postRequest("Admin/saveUpdatePoliceAgency.json",$scope.policeAgencyForm).then(function(response){
			 $location.path('dashboard/policeDepartments');
		});
		}
});

adminApp.controller('EditPoliceDepartmentController', function($http,$state,$location,$scope,$stateParams,requestHandler,Flash){
$scope.options=false;
$scope.title=$state.current.title;

//getting county List
$scope.getCountyList=function()
{
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countyform=response.data.countyForms;
			
	});
}
$scope.getCountyList();

//Get Police Department
var policeDepartmentOriginal="";
requestHandler.getRequest("Admin/getPoliceAgency.json?mapId="+$stateParams.mapId,"").then(function(response){
	
	
	$scope.policeAgencyForm=response.data.policeAgencyForm;
	if($scope.policeAgencyForm.agencyId==0)
		{
		$scope.policeAgencyForm.agencyId="";
		}
	$scope.policeAgencyForm.schedulerType=$scope.policeAgencyForm.schedulerType.toString();
	policeDepartmentOriginal=angular.copy($scope.policeAgencyForm);
});

//update Police Department

$scope.updatePoliceDepartment=function()
{
	if($scope.policeAgencyForm.schedulerType==4)
	{
	$scope.policeAgencyForm.agencyId=0;
	}
	requestHandler.postRequest("Admin/saveUpdatePoliceAgency.json",$scope.policeAgencyForm).then(function(response){
		 $location.path('dashboard/policeDepartments');
	});

}

$scope.isClean = function() {
	
    return angular.equals (policeDepartmentOriginal,$scope.policeAgencyForm);
    
};
});