var adminApp=angular.module('sbAdminApp', ['requestModule','flash','ngAnimate']);

adminApp.controller('searchPatientsController', ['$scope','requestHandler','$state','Flash', function($scope,requestHandler,$state,Flash) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isSelectedAddedFromDate=true;
	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.countyId="0";
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
		$scope.patient.itemsPerPage="25";
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.isArchived="0";
		$scope.patient.patientStatus="7";
		$scope.totalRecords=0;
		
		//Initial Search
		$scope.searchItems($scope.patient);
	};

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
		$scope.isIndividual=false;
		$scope.myForm.callerId="";
		$("#assignCallerModal").modal('show');
	};
	
	$scope.assignIndividualCallerPopup=function(id,name,callerId){
		$scope.isIndividual=true;
		$scope.assignPatientName=name;
		$scope.assignPatientId=id;
		if(callerId!=null)$scope.myForm.callerId=callerId.toString();
		else $scope.myForm.callerId="";
		$("#assignCallerModal").modal('show');
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
	
	$scope.assignCallerRequest=function(assignCallerObject){
		requestHandler.postRequest("/CAdmin/assignCaller.json",assignCallerObject).then(function(response){
			Flash.create('success', "You have Successfully Assigned Caller!");
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
		$scope.assignCallerRequest(assignCallerObj);
	};
	

	$scope.assignIndividualCaller=function(id){
		var assignCallerObj ={};
		assignCallerObj.callerId=$scope.myForm.callerId;
		assignCallerObj.patientId=[];
		assignCallerObj.patientId.push(id);
		$scope.assignCallerRequest(assignCallerObj);
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
			Flash.create('success', "You have Successfully Released from Archive!");
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
			$.each($scope.patientSearchData, function(index,value) {
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
				    case 6:
				    	value.patientStatusName="Need Re-Assign";
				        break;
				    default:
				        break;
				};
			});
			$scope.patientSearchDataOrginal=angular.copy($scope.patientSearchData);
			$scope.isCheckedIndividual();
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
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.patientSearchData,$scope.patientSearchDataOrginal);
	};
	
	$scope.init();
	
}]); 

 