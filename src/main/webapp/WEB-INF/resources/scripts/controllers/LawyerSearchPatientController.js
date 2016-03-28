var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('LawyerSearchPatientsController', ['$scope','requestHandler','$state','Flash', function($scope,requestHandler,$state,Flash) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.lawyerPatientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isCheckedAllPatients=false;
	
	
	
	
	    
	 $scope.getMyCountyList=function(){
	    	requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
	    		$scope.mycounties=response.data.countyList;
	    	});
	    };
	    
		 $scope.getMyCountyList();
	 
	 
	 $scope.checkCustomDate=function(custom){
			
		 if(custom=='0' && $scope.patient.crashFromDate!=''){
				$scope.disableCustom=false;
			}
			else{
				$scope.disableCustom=true;
			}
		};
		
		$scope.isChecked=function(){
			console.log($scope.lAdminPatientSearchData);
			if($scope.lawyerPatientSearchData.length>0){
				$.each($scope.lawyerPatientSearchData, function(index,value) {
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
	
	$scope.searchItems=function(searchObj){
			requestHandler.postRequest("Patient/searchPatients.json",searchObj).then(function(response){
				$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
				$scope.lawyerPatientSearchData=response.data.patientSearchResult.patientSearchLists;
				$scope.patientSearchDataOrginal=angular.copy($scope.lawyerPatientSearchData);
				$scope.isCheckedIndividual();
			});
	};
	 
	$scope.searchPatients = function(){
	
	if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="" && $scope.patient.crashToDate==""){
			$scope.crashToRequired=true;
		}
	else if($scope.patient.addedOnFromDate!="" && $scope.patient.addedOnToDate==""){
		$scope.addedToRequired=true;
	}
	else{
			$scope.crashToRequired=false;
			$scope.addedToRequired=false;
			$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";
			$scope.patient.localReportNumber="";
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
	
	
	$scope.releaseLawyer=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/releaseLawyer.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Lawyer!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.moveArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/moveToArchive.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully Moved to Archive!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.releaseArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/releaseFromArchive.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully released from Archive!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.init=function(){

		$scope.patient={};
		$scope.patient.countyId="0";
		$scope.patient.tier="0";
		$scope.patient.patientStatus=0;
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.callerId=0;
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId="0";
		$scope.patient.numberOfDays="1";
		$scope.patient.pageNumber= 1;
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.lawyerPatientSearchData="";
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.patientStatus="7";
		$scope.patient.isArchived="0";
		
		$scope.searchItems($scope.patient);
	};
	
	$scope.init();
	
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
		 $scope.patient.lawyerId="0";
	     $scope.patientSearchForm.$setPristine();
	     $scope.lawyerPatientSearchData="";
	     $scope.totalRecords="";
	     $scope.init();
	     
	};
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.lawyerPatientSearchData,$scope.patientSearchDataOrginal);
	};
	
}]); 

 