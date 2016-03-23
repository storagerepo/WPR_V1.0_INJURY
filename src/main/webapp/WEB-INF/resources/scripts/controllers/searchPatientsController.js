var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('searchPatientsController', ['$scope','requestHandler', function($scope,requestHandler,$state) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";

	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.county="";
		$scope.patient.localReportNumber="";
		$scope.patient.crashId="";
		$scope.patient.crashFromDate="";
		$scope.patient.numberOfDays="1";
		$scope.patient.crashToDate="";
		$scope.patient.addedFromDate="";
		$scope.patient.addedToDate="";
		$scope.patient.pageNumber= 1;
		$scope.patient.recordsPerPage=10;
		$scope.totalRecords=0;
	};
	
	$scope.init();
	
	
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countylist=response.data.countyForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom==''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	 
	$scope.searchPatients = function(){
		
		if($scope.patient.addedFromDate!="" && $scope.patient.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="" && $scope.patient.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.patient).then(function(response){
			 $scope.totalRecords=response.data.crashReportForm.totalRecords;
				$scope.crashSearchData=response.data.crashReportForm.crashReportForms;
				console.log($scope.crashSearchData);
		});
		}
	};
	
	
	
	$scope.searchPatientsFromPage = function(pageNum){
		
		 $scope.crashreport.pageNumber=pageNum;
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.crashreport).then(function(response){
			 $scope.totalRecords=response.data.crashReportForm.totalRecords;
				$scope.crashSearchData=response.data.crashReportForm.crashReportForms;
				console.log($scope.crashSearchData);
		});
	};
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
	     $scope.patientSearchForm.$setPristine();
	     $scope.crashSearchData="";
	     $scope.totalRecords="";
	     
	};
	
}]); 


adminApp.controller('LAdminsearchPatientsController', ['$scope','requestHandler', function($scope,requestHandler,$state) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";

	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.county="";
		$scope.patient.localReportNumber="";
		$scope.patient.crashId="";
		$scope.patient.crashFromDate="";
		$scope.patient.numberOfDays="1";
		$scope.patient.crashToDate="";
		$scope.patient.addedFromDate="";
		$scope.patient.addedToDate="";
		$scope.patient.pageNumber= 1;
		$scope.patient.recordsPerPage=10;
		$scope.totalRecords=0;
	};
	
	$scope.init();
	
	
	/* requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countylist=response.data.countyForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom==''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	 
	$scope.searchPatients = function(){
		
		if($scope.patient.addedFromDate!="" && $scope.patient.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="" && $scope.patient.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.patient).then(function(response){
			 $scope.totalRecords=response.data.crashReportForm.totalRecords;
				$scope.crashSearchData=response.data.crashReportForm.crashReportForms;
				console.log($scope.crashSearchData);
		});
		}
	};
	
	
	
	$scope.searchPatientsFromPage = function(pageNum){
		
		 $scope.crashreport.pageNumber=pageNum;
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.crashreport).then(function(response){
			 $scope.totalRecords=response.data.crashReportForm.totalRecords;
				$scope.crashSearchData=response.data.crashReportForm.crashReportForms;
				console.log($scope.crashSearchData);
		});
	};*/
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
	     $scope.patientSearchForm.$setPristine();
	     $scope.crashSearchData="";
	     $scope.totalRecords="";
	     
	};
	
}]); 