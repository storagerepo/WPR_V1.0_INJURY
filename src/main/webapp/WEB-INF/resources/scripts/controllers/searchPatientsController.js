var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('searchPatientsController', ['$scope','requestHandler', function($scope,requestHandler,$state) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=[];
	$scope.isSelectedFromDate=true;
	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.countyId="0";
		$scope.patient.tier=0;
		$scope.patient.patientStatus=0;
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.callerId=0;
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId=0;
		$scope.patient.numberOfDays="1";
		$scope.patient.pageNumber= 1;
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.searchItems($scope.patient);
	};

	requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
		$scope.countylist=response.data.countyForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom=='0'&&$scope.patient.crashFromDate!=''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	
	$scope.searchItems=function(searchObj){
		requestHandler.postRequest("/Patient/searchPatients.json",searchObj).then(function(response){
			$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
			$scope.patientSearchData=response.data.patientSearchResult.patientSearchLists;
		});
	};
	 
	$scope.searchPatients = function(){
		if($scope.patient.addedFromDate!="" && $scope.patient.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="0" && $scope.patient.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else if($scope.patient.addedOnFromDate!="" && $scope.patient.addedOnToDate==""){
			$scope.AddedOnToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
			$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";
			$scope.patient.localReportNumber="";
			if($scope.patient.countyId=="")
			$scope.patient.countyId=0;
			$scope.searchItems($scope.patient);
		}
	};
	
	$scope.secoundarySearchPatient=function(){
		$scope.patient.pageNumber= 1;
		$scope.setPage=1;
		$scope.searchItems($scope.patient);
	};
	
	$scope.searchPatientsFromPage = function(pageNum){
		 $scope.patient.pageNumber=pageNum;
		 $scope.searchItems($scope.patient);
	};
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		requestHandler.getRequest("/Patient/getPatient.json?patientId="+patientId,"").then(function(response){
			$scope.patient=response.data.patientForm;
		
			});

	};
	
	
	$scope.resetSearchData = function(){
	     $scope.patientSearchForm.$setPristine();
	     $scope.patientSearchData="";
	     $scope.init();
	};
	
	$scope.init();
	
}]); 

 