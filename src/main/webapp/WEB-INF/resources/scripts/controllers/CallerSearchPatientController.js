var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('CallerSearchPatientsController', ['$scope','requestHandler','Flash', function($scope,requestHandler,Flash,$state) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.callerPatientSearchData=$scope.callerPatientSearchDataOrginal=[];
	$scope.isSelectedAddedFromDate=true;

	 $scope.getMyCountyList=function(){
		 requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
	    	$scope.mycounties=response.data.countyList;
		 });
	 };
	    
		 $scope.getMyCountyList();
		 
		 $scope.isChecked=function(){
				if($scope.callerPatientSearchData.length>0){
					$.each($scope.callerPatientSearchData, function(index,value) {
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
	 
	 
	 $scope.checkCustomDate=function(custom){
			
		 if(custom=='0' && $scope.patient.crashFromDate!=''){
				$scope.disableCustom=false;
			}
			else{
				$scope.disableCustom=true;
			}
		};
	
	$scope.searchItems=function(searchObj){
		
		if(searchObj.countyId==""){
			searchObj.countyId=0;
		}
		
			requestHandler.postRequest("Patient/searchPatients.json",searchObj).then(function(response){
				$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
				$scope.callerPatientSearchData=response.data.patientSearchResult.patientSearchLists;
				$.each($scope.callerPatientSearchData, function(index,value) {
					switch(value.patientStatus) {
					    case null:
					        value.patientStatusName="New";
					        break;
					    case 1:
					    	value.patientStatusName="Active";
					        break;
					    case 2:
					    	value.patientStatusName="Not Interested/Injured";
					        break;
					    case 3:
					    	value.patientStatusName="Voice Mail";
					        break;
					    case 4:
					    	value.patientStatusName="Appointment Scheduled";
					        break;
					    case 5:
					    	value.patientStatusName="Do Not Call";
					        break;
					    default:
					        null;
					};
				});
				$scope.callerPatientSearchDataOrginal=angular.copy($scope.callerPatientSearchData);
				$scope.isCheckedIndividual();
			});
	};
	 
	$scope.searchPatients = function(){
	if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="0" && $scope.patient.crashToDate==""){
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
	
	$scope.releaseMultiPatient=function(){
		var releaseCallerObj ={};
		var patientIdArray=[];
		$.each($scope.callerPatientSearchData, function(index,value) {
			if(value.selected==true){
				patientIdArray.push(value.patientId);
			}
		});
		releaseCallerObj.patientId=patientIdArray;
		$scope.releasePatientRequest(releaseCallerObj);
	};
	
	$scope.releaseSinglePatient=function(id){
		var releaseCallerObj ={};
		releaseCallerObj.patientId=[];
		releaseCallerObj.patientId.push(id);
		$scope.releasePatientRequest(releaseCallerObj);
	};
	
	$scope.releasePatientRequest=function(assignCallerObj){
		requestHandler.postRequest("/Caller/releaseCaller.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Patient!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.releasePatientSingleConfirmPopup=function(id){
		$scope.isSingle=true;
		$scope.releasePatientId=id;
		$("#confirmModal").modal('show');
	};
	
	$scope.releasePatientMultiConfirmPopup=function(){
		$scope.isSingle=false;
		$("#confirmModal").modal('show');
	};
	
	$scope.moveArchive=function(){
		var assignCallerObj ={};
		var patientIdArray=[];
		$.each($scope.callerPatientSearchData, function(index,value) {
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
		$.each($scope.callerPatientSearchData, function(index,value) {
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
	
	$scope.init=function(){

		$scope.patient={};
		$scope.patient.countyId="0";
		$scope.patient.tier="0";
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
		$scope.callerPatientSearchData="";
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.isArchived="0";
		$scope.patient.patientStatus="7";
		
		//Initial Search
		$scope.searchItems($scope.patient);
		
	};
	
	$scope.init();
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
		 $scope.patient.lawyerId="0";
	     $scope.patientSearchForm.$setPristine();
	     $scope.callerPatientSearchData="";
	     $scope.totalRecords="";
	     $scope.init();
	     
	};
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.callerPatientSearchData,$scope.callerPatientSearchDataOrginal);
	};
	
}]); 

 