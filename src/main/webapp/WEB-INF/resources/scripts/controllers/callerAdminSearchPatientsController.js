var adminApp=angular.module('sbAdminApp', ['requestModule','searchModule','flash','ngAnimate','ngFileSaver']);

adminApp.controller('searchPatientsController', ['$q','$rootScope','$scope','$http','requestHandler','searchService','$state','Flash','FileSaver', function($q,$rootScope,$scope,$http,requestHandler,searchService,$state,Flash,FileSaver) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isSelectedAddedFromDate=true;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.setScrollDown=false;
	
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
		$scope.patient.itemsPerPage=searchService.getItemsPerPage();
		$scope.patient.addedOnFromDate=searchService.getAddedOnFromDate();
		$scope.patient.addedOnToDate=searchService.getAddedOnToDate();
		$scope.patient.isArchived=searchService.getIsArchived();
		$scope.patient.patientStatus=searchService.getPatientStatus();
		$scope.totalRecords=0;
		
		//Patient Search 
		$scope.patient.pageNumber= searchService.getPageNumber(); //This will call search function thru patient.pageNumber object $watch function 
		$scope.oldPageNumber= $scope.patient.pageNumber;
		
		if($scope.oldPageNumber==$scope.patient.pageNumber){
			$scope.searchItems($scope.patient);
		}
		
		
		//Initial Search
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
		var defer=$q.defer();
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
				console.log("service .....");
				defer.resolve(response);
			});
			$scope.patientSearchDataOrginal=angular.copy($scope.patientSearchData);
			$scope.isCheckedIndividual();
			console.log("service call end");
		});
		return defer.promise;
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
			$scope.oldPageNumber=$scope.patient.pageNumber;
			$scope.patient.pageNumber=1;
			if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
				$scope.searchItems($scope.patient);
			}
		}
		
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
			searchService.setPhoneNumber($scope.patient.phoneNumber);
			searchService.setPatientName($scope.patient.patientName);
			searchService.setIsArchived($scope.patient.isArchived);
			searchService.setTier($scope.patient.tier);
			searchService.setPatientStatus($scope.patient.patientStatus);
			$scope.oldPageNumber=$scope.patient.pageNumber;
			$scope.patient.pageNumber= 1;//This will call search function thru patient.pageNumber object $watch function 
			searchService.setPageNumber($scope.patient.pageNumber);
			searchService.setItemsPerPage($scope.patient.itemsPerPage);
			if($scope.oldPageNumber==$scope.patient.pageNumber){
				return $scope.searchItems($scope.patient);
			}
			return null;
					
		};
	
		$scope.itemsPerFilter=function(){
			$scope.setScrollDown=true;
			var promise=$scope.secoundarySearchPatient();
			if(promise!=null)
			promise.then(function(reponse){
				console.log("scroll down simple");
				console.log(reponse);
				setTimeout(function(){
	       			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
	       		 },100);
			});
				
		};
	
	$scope.$watch("patient.pageNumber",function(){
		 var promise=$scope.searchItems($scope.patient);
		 searchService.setPageNumber($scope.patient.pageNumber);
		 searchService.setItemsPerPage($scope.patient.itemsPerPage); 
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

 