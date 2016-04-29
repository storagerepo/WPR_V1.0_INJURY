var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('searchCrashReportController', ['$scope','requestHandler','$q', function($scope,requestHandler,$q) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.setScrollDown=false;
	
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
		$scope.crashreport.recordsPerPage="25";
		$scope.totalRecords=0;
		
		$scope.crashreport.pageNumber= 1;
		$scope.oldPageNumber= $scope.crashreport.pageNumber;
		
		if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			$scope.searchItems($scope.crashreport);
		}
		
		$scope.searchCrashReport();
	};
	
	
	
	
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
		else if($scope.crashreport.crashFromDate!="" && $scope.crashreport.numberOfDays=="0" && $scope.crashreport.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
			$scope.oldPageNumber=$scope.crashreport.pageNumber;
			$scope.crashreport.pageNumber=1;
			if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
				$scope.searchItems($scope.crashreport);
			}
		
		}
		
		
	};
	$scope.searchItems=function(searchObj){
		var defer=$q.defer();
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.crashreport).then(function(response){
			$scope.totalRecords=response.data.searchResults.totalNoOfRecords;
			$scope.crashSearchData=response.data.searchResults.crashReportForms;
			defer.resolve(response);
		});
		return defer.promise;
	};
	
	$scope.secoundarySearchCrashReport=function(){
		$scope.oldPageNumber=$scope.crashreport.pageNumber;
		$scope.crashreport.pageNumber=1;
		if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			return $scope.searchItems($scope.crashreport);
		}
		return null;
	};
	
	$scope.itemsPerFilter=function(){
		$scope.setScrollDown=true;
		var promise=$scope.secoundarySearchCrashReport();
		if(promise!=null)
		promise.then(function(reponse){
			console.log("scroll down simple");
			console.log(reponse);
			setTimeout(function(){
       			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
       		 },100);
		});
	};
	
	
	$scope.$watch("crashreport.pageNumber",function(){
		var promise=$scope.searchItems($scope.crashreport); 
		if($scope.setScrollDown){
			promise.then(function(reponse){
			 console.log("scroll down complex");
			 $scope.setScrollDown=false;
			 setTimeout(function(){
     			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
     		 },100);
			});
		 }
		
	});
	
	$scope.resetSearchData = function(){
		 $scope.crashSearchForm.$setPristine();
	     $scope.crashSearchData="";
	     $scope.init();
	     
	};
	
	$scope.init();
	
}]); 