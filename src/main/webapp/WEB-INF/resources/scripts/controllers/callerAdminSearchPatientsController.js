var adminApp=angular.module('sbAdminApp', ['requestModule','flash','ngAnimate']);

adminApp.controller('searchPatientsController', ['$scope','requestHandler','$state','Flash', function($scope,requestHandler,$state,Flash) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=$scope.patientSearchDataOrginal=[];
	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.countyId="";
		$scope.patient.tier="0";
		$scope.patient.patientStatus="0";
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.callerId="0";
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId=0;
		$scope.patient.numberOfDays="1";
		$scope.patient.pageNumber= 1;
		$scope.patient.itemsPerPage=10;
		$scope.totalRecords=0;
	};
	
	$scope.init();

	requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
		$scope.countylist=response.data.countyList;
	});
	
	requestHandler.getRequest("CAdmin/getCallersByCallerAdmin.json","").then(function(response){
		$scope.callerList=response.data.callerForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom=='0'&&$scope.patient.crashFromDate!=''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	
	$scope.isChecked=function(){
		if($scope.patientSearchData.length>0){
			$.each($scope.patientSearchData, function(index,value) {
				value.selected=$scope.isCheckedAllPatients;
			});
			$("input:checkbox").prop('checked', $(this).prop("checked"));
		}
	};
	
	$scope.isCheckedIndividual=function(){
		if($scope.isCheckedAllPatients){
			$scope.isCheckedAllPatients=false;
		}
	};
	
	$scope.assignCallerPopup=function(){
		$("#assignCallerModal").modal('show');
		$scope.myForm.callerId="1";
	};
	
	$scope.releaseCaller=function(){
		var assignCallerObj ={};
		var patientIdArray=[];
		$.each($scope.patientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignCallerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Caller/releaseCaller.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Caller!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.assignCaller=function(){
		var assignCallerObj ={};
		assignCallerObj.callerId=$scope.myForm.callerId;
		var patientIdArray=[];
		$.each($scope.patientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignCallerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/CAdmin/assignCaller.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully Assinged Caller!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.moveArchive=function(){
		var assignCallerObj ={};
		var patientIdArray=[];
		$.each($scope.patientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignCallerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Caller/moveToArchive.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully Moved to Archive!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.releaseArchive=function(){
		var assignCallerObj ={};
		var patientIdArray=[];
		$.each($scope.patientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignCallerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Caller/releaseFromArchive.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully released from Archive!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.searchItems=function(searchObj){
		requestHandler.postRequest("/Patient/searchPatients.json",searchObj).then(function(response){
			$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
			$scope.patientSearchData=response.data.patientSearchResult.patientSearchLists;
			$scope.patientSearchDataOrginal=angular.copy($scope.patientSearchData);
			$scope.isCheckedIndividual();
		});
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
			$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";
			$scope.patient.localReportNumber="";
			$scope.searchItems($scope.patient);
		}
	};
	
	$scope.secoundarySearchPatient=function(){
		if($scope.patientSearchData.length>0){
			$scope.patient.pageNumber= 1;
			$scope.setPage=1;
			$scope.searchItems($scope.patient);
		}
	};
	
	$scope.searchPatientsFromPage = function(pageNum){
		 $scope.patient.pageNumber=pageNum;
		 $scope.searchItems($scope.patient);
	};
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
		 $scope.patient.callerId="0";
	     $scope.patientSearchForm.$setPristine();
	     $scope.patientSearchData="";
	     $scope.totalRecords=0;
	};
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.patientSearchData,$scope.patientSearchDataOrginal);
	};
	
}]); 

 