var adminApp=angular.module('sbAdminApp', ['requestModule','searchModule','flash','ngAnimate','ngFileSaver']);

adminApp.controller('searchPatientsController', ['$rootScope','$scope','$http','requestHandler','searchService','$state','Flash','FileSaver', function($rootScope,$scope,$http,requestHandler,searchService,$state,Flash,FileSaver) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isSelectedAddedFromDate=true;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.countyId=searchService.getCounty();
		$scope.patient.tier=searchService.getTier();
		$scope.patient.crashFromDate=searchService.getCrashFromDate();
		$scope.patient.crashToDate=searchService.getCrashToDate();
		$scope.patient.localReportNumber=searchService.getLocalReportNumber();
		$scope.patient.patientName=searchService.getPatientName();
		$scope.patient.callerId=searchService.getCallerId();
		$scope.patient.phoneNumber=searchService.getPhoneNumber();
		$scope.patient.lawyerId=searchService.getLawyerId();
		$scope.patient.numberOfDays=searchService.getNumberOfDays();
		$scope.patient.pageNumber= searchService.getPageNumber();
		$scope.patient.itemsPerPage=searchService.getItemsPerPage();
		$scope.patient.addedOnFromDate=searchService.getAddedOnFromDate();
		$scope.patient.addedOnToDate=searchService.getAddedOnToDate();
		$scope.patient.isArchived=searchService.getIsArchived();
		$scope.patient.patientStatus=searchService.getPatientStatus();
		$scope.totalRecords=0;
		
		
		//Initial Search
		$scope.searchItems($scope.patient);
		$scope.disableCustom=true;
		$scope.isSelectedAddedFromDate=true;
		if(searchService.getCrashFromDate()!=""){
			$scope.disableCustom=false;
		}
		if(searchService.getAddedOnFromDate()!=""){
			$scope.isSelectedAddedFromDate=false;
		}
	};

	requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
		$scope.countylist=response.data.countyList;
	});
	
	requestHandler.getRequest("CAdmin/getCallersByCallerAdmin.json","").then(function(response){
		$scope.callerList=response.data.callerForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom=='0'&& $scope.patient.crashFromDate!=''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	
	$scope.isChecked=function(){
		if($scope.patientSearchData.length>0){
			$.each($scope.patientSearchData, function(index,value) {
				var i=0;
				for(i;i<value.patientSearchLists.length;i++){
					
					value.patientSearchLists[i].selected=$scope.isCheckedAllPatients;
				}
			});
			$("input:checkbox").prop('checked', $(this).prop("checked"));
		}
	};	
	$scope.isCheckedIndividual=function(){
		if($scope.isCheckedAllPatients){
			$scope.isCheckedAllPatients=false;
		}
		else if($scope.isCheckedAllGroupPatients){
			$scope.isCheckedAllGroupPatients=false;
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
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
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
			$.each(value.patientSearchLists,function(index1,value1){
				if(value1.selected==true){
					patientIdArray.push(value1.patientId);
				}
			});
			
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
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
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
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
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
	
	
	 $scope.selectGroup=function(id){
				$.each($scope.patientSearchData, function(index,value) {
				if(value.localReportNumber==id.resultData.localReportNumber){
					
					var i=0;
					for(i;i<value.numberOfPatients;i++){
						value.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
					}
				}
				});
				
		};
	
	$scope.searchItems=function(searchObj){
		requestHandler.postRequest("/Patient/searchPatients.json",searchObj).then(function(response){
			$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
			$scope.patientSearchData=response.data.patientSearchResult.searchResult;
			
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.patientSearchLists,function(index1,value1){
				switch(value1.patientStatus) {
				    case null:
				        value1.patientStatusName="New";
				        break;
				    case 1:
				    	value1.patientStatusName="Active";
				        break;
				    case 2:
				    	value1.patientStatusName="Not Interested/Injured";
				        break;
				    case 3:
				    	value1.patientStatusName="Voice Mail";
				        break;
				    case 4:
				    	value1.patientStatusName="Appointment Scheduled";
				        break;
				    case 5:
				    	value1.patientStatusName="Do Not Call";
				        break;
				    case 6:
				    	value1.patientStatusName="Need Re-Assign";
				        break;
				    case 8:
				    	value1.patientStatusName="Call Back";
				        break;
				    case 9:
				    	value1.patientStatusName="Unable To Reach";
				        break;
				    default:
				        break;
				};
				});
			});
			$scope.patientSearchDataOrginal=angular.copy($scope.patientSearchData);
			$scope.isCheckedIndividual();
			
			 
		});
	};
	
	
		
	$scope.searchPatients = function(){
		if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="0" && $scope.patient.crashToDate==""){
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
			$scope.searchItems($scope.patient);
		}
		$scope.patient.pageNumber=1;
		// Set To Service
		searchService.setCounty($scope.patient.countyId);
		searchService.setNumberOfDays($scope.patient.numberOfDays);
		searchService.setCrashFromDate($scope.patient.crashFromDate);
		searchService.setCrashToDate($scope.patient.crashToDate);
		searchService.setCallerId($scope.patient.callerId);
		searchService.setLawyerId($scope.patient.lawyerId);
		searchService.setPhoneNumber($scope.patient.phoneNumber);
		searchService.setPatientName($scope.patient.patientName);
		searchService.setLocalReportNumber($scope.patient.localReportNumber);
		searchService.setTier($scope.patient.tier);
		searchService.setAddedOnFromDate($scope.patient.addedOnFromDate);
		searchService.setAddedOnToDate($scope.patient.addedOnToDate);
		searchService.setPatientStatus($scope.patient.patientStatus);
		searchService.setIsArchived($scope.patient.isArchived);
		searchService.setPageNumber($scope.patient.pageNumber);
		searchService.setItemsPerPage($scope.patient.itemsPerPage);
	};
	
	
		$scope.secoundarySearchPatient=function(){
			$scope.patient.pageNumber= 1;
			$scope.searchItems($scope.patient);
			searchService.setPhoneNumber($scope.patient.phoneNumber);
			searchService.setPatientName($scope.patient.patientName);
			searchService.setIsArchived($scope.patient.isArchived);
			searchService.setTier($scope.patient.tier);
			searchService.setPatientStatus($scope.patient.patientStatus);
			searchService.setPageNumber($scope.patient.pageNumber);
			searchService.setItemsPerPage($scope.patient.itemsPerPage);
				    
		};
	
		
		
		$scope.itemsPerFilter=function(){
			$scope.secoundarySearchPatient();
			setTimeout(function(){
   			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
   		 },500);	
		};
		
	$scope.searchPatientsFromPage = function(pageNum){
		
		 $scope.patient.pageNumber=pageNum;
		 $scope.searchItems($scope.patient);
		 searchService.setPageNumber($scope.patient.pageNumber);
		 searchService.setItemsPerPage($scope.patient.itemsPerPage); 
	};
	

	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.patientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
				if(value1.patientId ==patientId){
					$scope.patientDetails=value1;
				}
			});
			
		});
		
	/*	requestHandler.getRequest("/Patient/getPatient.json?patientId="+patientId,"").then(function(response){
			$scope.patientDetails1=response.data.patientForm;
			});
*/
	};
	
	
	
	$scope.resetSearchData = function(){
	    // $scope.patientSearchForm.$setPristine();
	     $scope.patientSearchData="";
	     	searchService.setCounty("0");
			searchService.setNumberOfDays("1");
			searchService.setCrashFromDate("");
			searchService.setCrashToDate("");
			searchService.setCallerId("0");
			searchService.setLawyerId("0");
			searchService.setPhoneNumber("");
			searchService.setPatientName("");
			searchService.setLocalReportNumber("");
			searchService.setTier("0");
			searchService.setAddedOnFromDate("");
			searchService.setAddedOnToDate("");
			searchService.setPatientStatus("7");
			searchService.setIsArchived("0");
			searchService.setPageNumber(1);
			searchService.setItemsPerPage("25");
	     $scope.init();
	};
	
	// Reset Search Data Based on State
	if($rootScope.previousState!="dashboard.Calllogs/:id"){
		
		$scope.resetSearchData();
	}
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.patientSearchData,$scope.patientSearchDataOrginal);
	};
	
	//Export Excel
	$scope.exportToExcel=function(){
		$scope.exportButtonText="Exporting...";
		$scope.exportButton=true;
		requestHandler.postExportRequest('Patient/exportExcel.xlsx',$scope.patient).success(function(responseData){
			 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
			 FileSaver.saveAs(blob,"Export_"+moment().format('YYYY-MM-DD')+".xlsx");
			 $scope.exportButtonText="Export to Excel";
			 $scope.exportButton=false;
		});
	};
	
	$scope.init();
	
}]); 

 