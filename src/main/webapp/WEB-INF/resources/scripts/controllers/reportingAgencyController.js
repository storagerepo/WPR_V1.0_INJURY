var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('ShowReportingAgencyController',function($http,$state,$scope,$location,requestHandler,Flash){
	
	$scope.setScrollDown=false;
	$scope.searchParamForm={
		"countyId":0,
		"ncicCode":"",
		"reportingAgencyName":"",
		"pageNumber":1,
		"recordsPerPage":"25"
	};
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
  //getting county List
	$scope.getCountyList=function()
	{
		 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
				$scope.countyform=response.data.countyForms;
				
				console.log($scope.countyform);
				
		});
	}
	$scope.getCountyList();
	
	$scope.getReportingAgency=function()
	{
		requestHandler.postRequest("Patient/getReportingAgencyList2.json",$scope.searchParamForm).then(function(response)
				{
			$scope.reportingAgencyList=response.data.reportingAgencyList.reportingAgencyForms;
			$scope.totalRecords=response.data.reportingAgencyList.totalRecords;
			
				})
		
	}
	
	$scope.getReportingAgency();
	
		//Filter County
		$scope.CountyFilterFunction=function(selectedCounty)
		{
			if(selectedCounty==null)
				{
				$scope.selectedCounty=0;
				}
		}
		    //Getting New Records From Back-end 
		 
		$scope.itemsPerPage=function()
		{
			$scope.setScrollDown=true;
			var promise=$scope.getReportingAgency();
			if(promise!=null)
			promise.then(function(reponse){
				setTimeout(function(){
	       			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
	       		 },100);
			});
		}
		
	//Watch Page Number And fetching new Records From Back-end
		
		$scope.$watch("searchParamForm.pageNumber",function(){
			var promise=$scope.getReportingAgency();
			if($scope.setScrollDown){
				promise.then(function(reponse){
				 $scope.setScrollDown=false;
				 setTimeout(function(){
	     			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
	     		 },100);
				});
			 }
			
		});
		
		//Search Based On KeyWord
		$scope.searchparam=function()
        {
			$scope.getReportingAgency();
		}
		
		//Reset Search Param
		
		$scope.reset=function()
		{
			$scope.searchParamForm={
					"countyId":0,
					"ncicCode":"",
					"reportingAgencyName":"",
					"pageNumber":1,
					"recordsPerPage":"25"
				};
			$scope.getReportingAgency();
		}
		
});

adminApp.controller('AddReportingAgencyController',function($http,$state,$scope,$location,requestHandler,Flash){
	$scope.title="Add Reporting Agency";
	 $scope.options=true;
	 $scope.disableNcic=false;
	 $scope.showNcicError=false;
	 $scope.reportingAgencyForm={};
	 $scope.reportingAgencyForm.reportingAgencyId=0;
	 
	  //getting county List
	$scope.getCountyList=function()
	{
		 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
				$scope.countyform=response.data.countyForms;
				
		});
	}
	$scope.getCountyList();
	
	//Save Reporting Agency
	var reportingAgencyOriginal="";
	$scope.saveReportingAgency=function()
	{
       requestHandler.postRequest("Patient/checkNcicCode.json?nciccode="+$scope.reportingAgencyForm.code+"&id="+$scope.reportingAgencyForm.reportingAgencyId+"&countyId="+$scope.reportingAgencyForm.countyId,0).then(function(response){
           var result=response.data.result;
           if(result==1)
        	   {
        	   $scope.showNcicError=true;
        	   }
           else
        	   {
        	   requestHandler.postRequest("Patient/saveUpdateReportingAgency.json",$scope.reportingAgencyForm).then(function(response)
       				{
       			 $location.path('dashboard/reportingAgency');
       			 Flash.create('success', "New Reporting Agency Added Successfully");
       				});
        	   }
       });
		   
	}
	
});

adminApp.controller('EditReportingAgencyController',function($http,$state,$scope,$stateParams,$location,requestHandler,Flash){
	$scope.title="Edit Reporting Agency";
$scope.disableNcic=true;
$scope.options=false;

//getting County List
$scope.getCountyList=function()
{
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countyform=response.data.countyForms;
			
	});
}
$scope.getCountyList();

       //Get Reporting Agency
var reportingAgencyOriginal="";
requestHandler.getRequest("Patient/getReportingAgency.json?reportingAgencyId="+$stateParams.reportingAgencyId,"").then(function(response)
		{
	$scope.reportingAgencyForm=response.data.reportingAgencyForm;
	reportingAgencyOriginal=angular.copy($scope.reportingAgencyForm);
		});
		

           //update Reporting Agency
        var reportingAgencyOriginal="";
		$scope.updateReportingAgency=function()
		{
		       requestHandler.postRequest("Patient/checkNcicCode.json?nciccode="+$scope.reportingAgencyForm.code+"&id="+$scope.reportingAgencyForm.reportingAgencyId+"&countyId="+$scope.reportingAgencyForm.countyId,0).then(function(response){
		           var result=response.data.result;
		           if(result==1)
		        	   {
		        	   $scope.showNcicError=true;
		        	   }
		           else
		        	   {
	requestHandler.postRequest("Patient/saveUpdateReportingAgency.json",$scope.reportingAgencyForm).then(function(response)
			{
		 $location.path('dashboard/reportingAgency');
		 Flash.create('success', "You Have Successfully Updated");

			});
		}
		       });
		}
		//Check for changes in Ng-Model
		$scope.isClean = function() {
	
    return angular.equals (reportingAgencyOriginal,$scope.reportingAgencyForm);
}
});


