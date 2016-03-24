var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('searchCrashReportController', ['$scope','requestHandler', function($scope,requestHandler,$state) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";

	
	$scope.init=function(){
		$scope.crashreport={};
		$scope.crashreport.county="";
		$scope.crashreport.localReportNumber="";
		$scope.crashreport.crashId="";
		$scope.crashreport.crashFromDate="";
		$scope.crashreport.numberOfDays="1";
		$scope.crashreport.crashToDate="";
		$scope.crashreport.addedFromDate="";
		$scope.crashreport.addedToDate="";
		$scope.crashreport.pageNumber= 1;
		$scope.crashreport.recordsPerPage="10";
		$scope.totalRecords=0;
	};
	
	$scope.init();
	
	
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countylist=response.data.countyForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom=='0' && $scope.crashreport.crashFromDate!=''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	 
	$scope.searchCrashReport = function(){
		
		if($scope.crashreport.addedFromDate!="" && $scope.crashreport.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.crashreport.crashFromDate!="" && $scope.crashreport.numberOfDays=="" && $scope.crashreport.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.crashreport).then(function(response){
			 $scope.totalRecords=response.data.crashReportForm.totalRecords;
				$scope.crashSearchData=response.data.crashReportForm.crashReportForms;
				console.log($scope.crashSearchData);
		});
		}
	};
	
	
	
	$scope.searchCrashReportFromPage = function(pageNum){
		
		 $scope.crashreport.pageNumber=pageNum;
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.crashreport).then(function(response){
			 $scope.totalRecords=response.data.crashReportForm.totalRecords;
				$scope.crashSearchData=response.data.crashReportForm.crashReportForms;
				console.log($scope.crashSearchData);
		});
	};
	
	$scope.resetSearchData = function(){
		 $scope.crashSearchForm.$setPristine();
	     $scope.crashSearchData="";
	     $scope.init();
	     
	};
	
}]); 