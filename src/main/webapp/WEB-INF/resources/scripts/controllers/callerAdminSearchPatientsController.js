var adminApp=angular.module('sbAdminApp', ['requestModule','angularjs-dropdown-multiselect','searchModule','flash','ngAnimate','ngFileSaver','googleModule']);

adminApp.controller('searchPatientsController', ['$q','$rootScope','$scope','$http','$window','requestHandler','searchService','$state','$location','Flash','FileSaver','googleService','$timeout', function($q,$rootScope,$scope,$http,$window,requestHandler,searchService,$state,$location,Flash,FileSaver,googleService,$timeout) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isSelectedAddedFromDate=true;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.setScrollDown=false;
	$scope.ageFilterCurrentSelection=[];
	// User Preference Status
	$rootScope.userPrefenceTabStatus=1;
	$scope.loadingCounties=true;

	$scope.archivedToDateRequired=false;
	$scope.archivedFromDateRequired=false;
	// Main Search Param
	$scope.mainSearchParam={};
	
	
	$scope.init=function(){
		
		//Initialize DropDown
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"},{id: 5, label: "Undetermined"}];	
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		$scope.defaultDamageScale=[{id: 1, label: "None",legendClass:"badge-success",haveLegend:true},{id: 2, label: "Minor",legendClass:"badge-yellow",haveLegend:true},{id: 3, label: "Functional",legendClass:"badge-primary",haveLegend:true},{id: 4, label: "Disabling",legendClass:"badge-danger",haveLegend:true},{id: 9, label: "Unknown",legendClass:"badge-default",haveLegend:true},{id: 5, label: "N/A",haveLegend:false}];
		
		$scope.patient={};
		$scope.patient.countyId=[];
		$scope.patient.tier=[];
		$scope.patient.damageScale=[];
		angular.copy(searchService.getCounty(),$scope.patient.countyId);
		angular.copy(searchService.getTier(),$scope.patient.tier);
		angular.copy(searchService.getDamageScale(),$scope.patient.damageScale);
		$scope.patient.crashFromDate=searchService.getCrashFromDate();
		$scope.patient.crashToDate=searchService.getCrashToDate();
		$scope.patient.localReportNumber=searchService.getLocalReportNumber();
		$scope.patient.patientName=searchService.getPatientName();
		$scope.patient.age=searchService.getAge();
		$scope.patient.callerId=searchService.getCallerId();
		$scope.patient.phoneNumber=searchService.getPhoneNumber();
		$scope.patient.lawyerId=searchService.getLawyerId();
		$scope.patient.numberOfDays=searchService.getNumberOfDays();
		$scope.patient.itemsPerPage=searchService.getItemsPerPage();
		$scope.patient.addedOnFromDate=searchService.getAddedOnFromDate();
		$scope.patient.addedOnToDate=searchService.getAddedOnToDate();
		$scope.patient.isArchived=searchService.getIsArchived();
		$scope.patient.patientStatus=searchService.getPatientStatus();
		$scope.patient.archivedFromDate=searchService.getArchivedFromDate();
		$scope.patient.archivedToDate=searchService.getArchivedToDate();
		$scope.patient.isRunnerReport=searchService.getIsRunnerReport();
		$scope.patient.directReportStatus=searchService.getDirectReportStatus();
		$scope.totalRecords=0;
		$scope.countyListType=searchService.getCountyListType();
		//Patient Search 
		$scope.patient.pageNumber= searchService.getPageNumber(); //This will call search function thru patient.pageNumber object $watch function 
		$scope.oldPageNumber= $scope.patient.pageNumber;
		if($scope.oldPageNumber==$scope.patient.pageNumber){
			// To Avoid Main Search Parameter Override
			angular.copy($scope.patient,$scope.mainSearchParam);
			if($scope.patient.countyId!=''){
				searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
					$scope.countylist=response;
					$scope.loadingCounties=false;
				});
				$scope.searchItems($scope.patient);
			}else{
				searchService.checkCoutyListType().then(function(response){
					searchService.setCountyListType(response);
					$scope.countyListType=response;
					searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
						$scope.countylist=response;
						$scope.loadingCounties=false;
					});
					searchService.getInitPreferenceCoutyList($scope.countyListType).then(function(response){
						angular.copy(response,$scope.patient.countyId);
						$scope.mainSearchParam.countyId=angular.copy($scope.patient.countyId);
						$scope.searchItems($scope.patient);
					});
				});
			}

		}
		
		
		//Initial Search
		$scope.disableCustom=true;
		$scope.isSelectedAddedFromDate=true;

		if(searchService.getCrashToDate()!=""){
			$scope.disableCustom=false;
		}
		if(searchService.getAddedOnFromDate()!=""){
			$scope.isSelectedAddedFromDate=false;
		}


		// Get Clinics List For Call Logs
		$scope.getClinics();
		
	};

	// Get Callers
	requestHandler.getRequest("CAdmin/getCallersByCallerAdmin.json","").then(function(response){
		$scope.callerList=response.data.callerForms;
	});
	 
	$scope.checkCustomDate=function(custom){
		//Reset to date if less than from date
		var fromDate=new Date($scope.patient.crashFromDate);
		var toDate=new Date($scope.patient.crashToDate);
		if(toDate<fromDate)
			$scope.patient.crashToDate="";
		//End Reset to date if less than from date
		if(custom=='0'&& $scope.patient.crashFromDate!=''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
			$scope.patient.crashToDate="";
		}
	};
	
	$scope.checkAddedOnToDate=function(){
		if($scope.patient.addedOnFromDate!="")
			$scope.isSelectedAddedFromDate=false;
		else{
			$scope.patient.addedOnToDate="";
			$scope.isSelectedAddedFromDate=true;
		}
		//Reset to date if less than from date
		var fromDate=new Date($scope.patient.addedOnFromDate);
		var toDate=new Date($scope.patient.addedOnToDate);
		if(toDate<fromDate)
			$scope.patient.addedOnToDate="";
		//End Reset to date if less than from date
	};
	
	
	$scope.isChecked=function(){
		if($scope.patientSearchData.length>0){
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					var i=0;
					for(i;i<value1.patientSearchLists.length;i++){
						
						value1.patientSearchLists[i].selected=$scope.isCheckedAllPatients;
					}
				});
				
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
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.selected==true){
						patientIdArray.push(value2.patientId);
					}
					});
			});
			
		});
		assignCallerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Caller/releaseCaller.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Caller!");
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.assignCallerRequest=function(assignCallerObject){
		requestHandler.postRequest("/CAdmin/assignCaller.json",assignCallerObject).then(function(response){
			Flash.create('success', "You have Successfully Assigned Caller!");
			$scope.searchItems($scope.patient);
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
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.selected==true){
						patientIdArray.push(value2.patientId);
					}
				});
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
	
	$scope.moveArchive=function(reportType){
		if(reportType==3){
			var moveArchiveObj ={};
			var crashIdArray=[];
			$.each($scope.directRunnerReportSearchData, function(index,value) {
				$.each(value.crashReportForms, function(index1,value1) {
					if(value1.selected==true){
						crashIdArray.push(value1.crashId);
					}
				});
			});
			moveArchiveObj.crashId=crashIdArray;
			moveArchiveObj.status=1;
			requestHandler.postRequest("/Caller/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}else{
			var assignCallerObj ={};
			var patientIdArray=[];
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							patientIdArray.push(value2.patientId);
						}
						});
				});
				
			});
			assignCallerObj.patientId=patientIdArray;
			
			requestHandler.postRequest("/Caller/moveToArchive.json",assignCallerObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Move Single File To Archive
	$scope.moveSingleFileToArchive=function(patientId){
		var assignCallerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to move to archive?")){
			assignCallerObj.patientId=patientIdArray;
			requestHandler.postRequest("/Caller/moveToArchive.json",assignCallerObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
		
	};
	
	
	// Direct Report Single Archive
	$scope.moveSingleDirectReportToArchive=function(crashId){
		var moveArchiveObj ={};
		var crashIdArray=[crashId];
		if(confirm("Are you sure want to move to archive?")){
			moveArchiveObj.crashId=crashIdArray;
			moveArchiveObj.status=1;
			requestHandler.postRequest("/Caller/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
		
	};
	
	$scope.releaseArchive=function(reportType){
		if(reportType==3){
			var moveArchiveObj ={};
			var crashIdArray=[];
			$.each($scope.directRunnerReportSearchData, function(index,value) {
				$.each(value.crashReportForms, function(index1,value1) {
					if(value1.selected==true){
						crashIdArray.push(value1.crashId);
					}
				});
			});
			moveArchiveObj.crashId=crashIdArray;
			moveArchiveObj.status=0;
			requestHandler.postRequest("/Caller/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Released from Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}else{
			var assignCallerObj ={};
			var patientIdArray=[];
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							patientIdArray.push(value2.patientId);
						}
						});
				});
				
			});
			assignCallerObj.patientId=patientIdArray;
			
			requestHandler.postRequest("/Caller/releaseFromArchive.json",assignCallerObj).then(function(response){
				Flash.create('success', "You have Successfully Released from Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Release Single File From Archive
	$scope.releaseSingleFileFromArchive=function(patientId){
		var assignCallerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to release from archive?")){
			assignCallerObj.patientId=patientIdArray;
			requestHandler.postRequest("/Caller/releaseFromArchive.json",assignCallerObj).then(function(response){
				Flash.create('success', "You have Successfully Released from Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Direct Report Release Single From Archive
	$scope.releaseSingleDirectReportFromArchive=function(crashId){
		var moveArchiveObj ={};
		var crashIdArray=[crashId];
		if(confirm("Are you sure want to release from archive?")){
			moveArchiveObj.crashId=crashIdArray;
			moveArchiveObj.status=0;
			requestHandler.postRequest("/Caller/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Released from Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	 $scope.selectGroup=function(id,archiveDate){
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					if(archiveDate!=''){
						if(value.archivedDate==archiveDate){
							if(value1.localReportNumber==id.resultData.localReportNumber){
									var i=0;
									for(i;i<value1.numberOfPatients;i++){
										if(value1.patientSearchLists[i]!=undefined)
											 value1.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
									}
							}
						}
					}else{
						if(value1.localReportNumber==id.resultData.localReportNumber){
							var i=0;
							for(i;i<value1.numberOfPatients;i++){
								if(value1.patientSearchLists[i]!=undefined)
									 value1.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
							}
					}
					}
					
				});
			
			});
			
	};
	
	// Direct Report Select Archive Group
	$scope.selectDirectReportArchiveGroup=function(id,archivedDate){
		$.each($scope.directRunnerReportSearchData, function(index,value) {
			$.each(value.crashReportForms, function(index1,value1) {
				if(archivedDate!=''){
					if(value1.archivedDate==archivedDate){
						value1.selected=id.isCheckedAllGroupArchiveDirectReport;
					}
				}
			});
		});
	};
	
	// Direct Report Select All Report
	$scope.checkedAllDirectReport=function(){
		$.each($scope.directRunnerReportSearchData, function(index,value) {
			$.each(value.crashReportForms, function(index1,value1) {
				value1.selected=$scope.isCheckedAllDirectReport;
			});
		});
	};
	
	// Select Archive Group
	 $scope.selectArchiveGroup=function(id,archiveDate){
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					if(archiveDate!=''){
						if(value.archivedDate==archiveDate){
							var i=0;
							for(i;i<value1.numberOfPatients;i++){
								if(value1.patientSearchLists[i]!=undefined){
									/*var changedLocalReportNumber=value1.patientSearchLists[i].localReportNumber.replace( /(\.|\(|\)|\ )/g, "\\$1" );
									var changeDate=archiveDate.replace(/(\/)/g,"\\$1");*/
									 value1.patientSearchLists[i].selected=id.isCheckedAllGroupArchivePatients;/*
									 $("#"+changedLocalReportNumber+changeDate).prop("checked",id.isCheckedAllGroupArchivePatients);*/
								}
								 
							}
						 }
					}
				});
			});
			
	};
	
	// Direct Report Reset Check All
	$scope.resetCheckAllDirect=function(){
		if($scope.isCheckedAllDirectReport){
			$scope.isCheckedAllDirectReport=false;
		}
	};
	
	$scope.searchItems=function(searchObj){
		
		$scope.isLoading=true;
		
		//To avoid overwriting actual $scope.patient object.
		$scope.searchParam={};
		angular.copy(searchObj,$scope.searchParam);
		
		//Manipulate Tier Array
		$.each($scope.searchParam.tier, function(index,value) {
			$scope.searchParam.tier[index]=value.id;
		});
		
		//Manipulate County Array
		$.each($scope.searchParam.countyId, function(index,value) {
			$scope.searchParam.countyId[index]=value.id;
		});
		
		//Manipulate Age Array
		$.each($scope.searchParam.age, function(index,value) {
			$scope.searchParam.age[index]=value.id;
		});
		
		//Manipulate Damage Scale Array
		$.each($scope.searchParam.damageScale, function(index,value) {
			$scope.searchParam.damageScale[index]=value.id;
		});
		
		//Reset Check Box - Scanned
		$scope.isCheckedAllDirectReport=false;
		
		var defer=$q.defer();
		
		requestHandler.postRequest("/Patient/searchPatients.json",$scope.searchParam).then(function(response){
			$scope.isLoading=false;
			if($scope.searchParam.isRunnerReport!=3){
				$scope.totalRecords=0;
				$scope.totalRecords=response.data.patientGroupedSearchResult.totalNoOfRecord;
				$scope.patientSearchData=response.data.patientGroupedSearchResult.patientSearchResults;
				
				$.each($scope.patientSearchData, function(index,value) {
					$.each(value.searchResult, function(index1,value1) {
						$.each(value1.patientSearchLists,function(index2,value2){
							switch(value2.patientStatus) {
							    case null:
							        value2.patientStatusName="New";
							        break;
							    case 1:
							    	value2.patientStatusName="Active";
							        break;
							    case 2:
							    	value2.patientStatusName="Not Interested/Injured";
							        break;
							    case 3:
							    	value2.patientStatusName="Voice Mail";
							        break;
							    case 4:
							    	value2.patientStatusName="Appointment Scheduled";
							        break;
							    case 5:
							    	value2.patientStatusName="Do Not Call";
							        break;
							    case 6:
							    	value2.patientStatusName="To be Re-Assigned";
							        break;
							    case 8:
							    	value2.patientStatusName="Call Back";
							        break;
							    case 9:
							    	value2.patientStatusName="Unable To Reach";
							        break;
							    default:
							        break;
							};
							switch(value2.injuries) {
						    case "1":
						    	value2.injuriesName="No Injury/None Reported";
						        break;
						    case "2":
						    	value2.injuriesName="Possible";
						        break;
						    case "3":
						    	value2.injuriesName="Non-Incapacitating";
						        break;
						    case "4":
						    	value2.injuriesName="Incapacitating";
						        break;
						    case "5":
						    	value2.injuriesName="Fatal";
						        break;
						    case "6":
						    	value2.injuriesName="Not Available";
						        break;
						    default:
						        break;
							};
							switch(value2.crashSeverity) {
						    case "1":
						    	value2.crashSeverityName="Fatal";
						        break;
						    case "2":
						    	value2.crashSeverityName="Injury";
						        break;
						    case "3":
						    	value2.crashSeverityName="PDO";
						        break;
						    case "4":
						    	value2.crashSeverityName="Not Available";
						        break;
						    default:
						        break;
							};
							
						});
					});
					
					console.log("service .....");
					defer.resolve(response);
				});

				$scope.patientSearchDataOrginal=angular.copy($scope.patientSearchData);
				$scope.isCheckedIndividual();
			}else{
				$scope.patientSearchData={};
				$scope.totalRecords=response.data.directReportGroupResult.totalNoOfRecords;
				$scope.directRunnerReportSearchData=response.data.directReportGroupResult.directReportGroupListByArchives;
				$.each($scope.directRunnerReportSearchData,function(key,value){
					$.each(value.crashReportForms,function(key1,value1){
						switch(value1.directReportStatus){
						case null:
							value1.directReportStatusName="New";
							break;
						case 1:
							value1.directReportStatusName="Contacted";
							break;
						case 2:
							value1.directReportStatusName="Follow-Up";
							break;
						case 3:
							value1.directReportStatusName="Not Interested";
							break;
						default:
							break;
						}
					});
					
				});
				$scope.isCleanCheckboxDirectReport();
				$scope.resetCheckAllDirect();
			}
			
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
		/*	$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";*/
			$scope.oldPageNumber=$scope.patient.pageNumber;
			$scope.patient.pageNumber=1;
			if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
				$scope.patient.archivedFromDate=searchService.getArchivedFromDate();
				$scope.patient.archivedToDate=searchService.getArchivedToDate();
				$scope.searchItems($scope.patient);
			}

			// To Avoid Main Search Parameter Override
			angular.copy($scope.patient,$scope.mainSearchParam);
		}
		
		// Set To Service
		searchService.setCounty(angular.copy($scope.patient.countyId));
		searchService.setNumberOfDays($scope.patient.numberOfDays);
		searchService.setCrashFromDate($scope.patient.crashFromDate);
		searchService.setCrashToDate($scope.patient.crashToDate);
		searchService.setCallerId($scope.patient.callerId);
		searchService.setLawyerId($scope.patient.lawyerId);
		searchService.setPhoneNumber($scope.patient.phoneNumber);
		searchService.setPatientName($scope.patient.patientName);
		searchService.setAge(angular.copy($scope.patient.age));
		searchService.setLocalReportNumber($scope.patient.localReportNumber);
		searchService.setTier(angular.copy($scope.patient.tier));
		searchService.setAddedOnFromDate($scope.patient.addedOnFromDate);
		searchService.setAddedOnToDate($scope.patient.addedOnToDate);
		searchService.setPatientStatus($scope.patient.patientStatus);
		searchService.setIsArchived($scope.patient.isArchived);
		searchService.setPageNumber($scope.patient.pageNumber);
		searchService.setItemsPerPage($scope.patient.itemsPerPage);
		searchService.setCountyListType($scope.countyListType);
		searchService.setDamageScale(angular.copy($scope.patient.damageScale));
		searchService.setDirectReportStatus($scope.patient.directReportStatus);
	};
	
	
		$scope.secoundarySearchPatient=function(){
			searchService.setPhoneNumber($scope.patient.phoneNumber);
			searchService.setPatientName($scope.patient.patientName);
			searchService.setIsArchived($scope.patient.isArchived);
			searchService.setAge($scope.patient.age);
			searchService.setPatientStatus($scope.patient.patientStatus);
			searchService.setDirectReportStatus($scope.patient.directReportStatus);
			if($scope.patient.isArchived==0){
				$scope.archivedToDateRequired=false;
				$scope.archivedFromDateRequired=false;
				$scope.patient.archivedFromDate="";
				$scope.patient.archivedToDate="";
				searchService.setArchivedFromDate($scope.patient.archivedFromDate);
				searchService.setArchivedToDate($scope.patient.archivedToDate);
			}
			$scope.oldPageNumber=$scope.patient.pageNumber;
			$scope.patient.pageNumber= 1;//This will call search function thru patient.pageNumber object $watch function 
			searchService.setPageNumber($scope.patient.pageNumber);
			searchService.setItemsPerPage($scope.patient.itemsPerPage);
			
			// Use mainsearchparam to Search 
			$scope.mainSearchParam.pageNumber=$scope.patient.pageNumber;
			$scope.mainSearchParam.phoneNumber=$scope.patient.phoneNumber;
			$scope.mainSearchParam.patientName=$scope.patient.patientName;
			$scope.mainSearchParam.isArchived=$scope.patient.isArchived;
			$scope.mainSearchParam.archivedFromDate=searchService.getArchivedFromDate();
			$scope.mainSearchParam.archivedToDate=searchService.getArchivedToDate();
			$scope.mainSearchParam.age=$scope.patient.age;
			$scope.mainSearchParam.patientStatus=$scope.patient.patientStatus;
			$scope.mainSearchParam.directReportStatus=$scope.patient.directReportStatus;
			$scope.mainSearchParam.itemsPerPage=$scope.patient.itemsPerPage;
			if($scope.oldPageNumber==$scope.patient.pageNumber){
				// Copy Mainsearchparam to Patient
				angular.copy($scope.mainSearchParam,$scope.patient);
				// County List
				$scope.countyListType=searchService.getCountyListType();
				searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
					$scope.countylist=response;
					$scope.loadingCounties=false;
				});
				return $scope.searchItems($scope.mainSearchParam);
			}
			
			return null;	
		};
	
		// Watch Report Type
		$scope.$watch("patient.isRunnerReport",function(){
			$scope.mainSearchParam.pageNumber=1;
			$scope.mainSearchParam.isRunnerReport=$scope.patient.isRunnerReport;
			searchService.setIsRunnerReport($scope.patient.isRunnerReport);
			searchService.setPageNumber($scope.patient.pageNumber);
			searchService.setItemsPerPage($scope.patient.itemsPerPage);
			// Copy Mainsearchparam to Patient
			angular.copy($scope.mainSearchParam,$scope.patient);
			if($scope.mainSearchParam.countyId!=''){
				// County List
				$scope.countyListType=searchService.getCountyListType();
				searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
					$scope.countylist=response;
					$scope.loadingCounties=false;
				});
				var promise=$scope.searchItems($scope.mainSearchParam);
				promise.then(function(reponse){
					// After Search
				});
			}
		});
		
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
		$scope.mainSearchParam.pageNumber=$scope.patient.pageNumber;

		 searchService.setPageNumber($scope.patient.pageNumber);
		 searchService.setItemsPerPage($scope.patient.itemsPerPage); 
		// Copy Mainsearchparam to Patient
		angular.copy($scope.mainSearchParam,$scope.patient);
		if($scope.mainSearchParam.countyId!=''){
			var promise=$scope.searchItems($scope.mainSearchParam);
			 if($scope.setScrollDown){
				  promise.then(function(reponse){
					  console.log("scroll down complex");
				 $scope.setScrollDown=false;
				 setTimeout(function(){
	       			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
	       		 },100);
				 });
			 }
		}
		 
		
	});
		
	// Check Archived To Date
	$scope.checkArchivedToDate=function(){
		$scope.archivedFromDateRequired=false;
		//Reset to date if less than from date
		var fromDate=new Date($scope.patient.archivedFromDate);
		var toDate=new Date($scope.patient.archivedToDate);
		if(toDate<fromDate)
			$scope.patient.archivedToDate="";
		//End Reset to date if less than from date
	};
	
	// Reset Archive Filter Date
	$scope.resetArchiveFilterDate=function(){
		$scope.archivedToDateRequired=false;
		$scope.archivedFromDateRequired=false;
		$scope.patient.archivedFromDate="";
		$scope.patient.archivedToDate="";
		searchService.setArchivedFromDate($scope.patient.archivedFromDate);
		searchService.setArchivedToDate($scope.patient.archivedToDate);
		$scope.secoundarySearchPatient();
	};
	
	// Search With Archive Filter
	$scope.searchWithArchiveDateFilter=function(){
		if($scope.patient.archivedFromDate==""){
			$scope.archivedFromDateRequired=true;
		}
		else if($scope.patient.archivedToDate==""){
			$scope.archivedToDateRequired=true;
		}else{
			$scope.archivedFromDateRequired=false;
			$scope.archivedToDateRequired=false;
			searchService.setArchivedFromDate($scope.patient.archivedFromDate);
			searchService.setArchivedToDate($scope.patient.archivedToDate);
			$scope.secoundarySearchPatient();
		}
	};
	
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.patientSearchData, function(index,value) {
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.patientId ==patientId){
						$scope.patientDetails=value2;
					}
				});
			});
		});
		
	/*	requestHandler.getRequest("/Patient/getPatient.json?patientId="+patientId,"").then(function(response){
			$scope.patientDetails1=response.data.patientForm;
			});
*/
	};
	
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.patientSearchData,$scope.patientSearchDataOrginal);
	};
	
	// Direct Report Clean Check Box
	$scope.isCleanCheckboxDirectReport=function(){
		$scope.isDisableButtons=true;
		$.each($scope.directRunnerReportSearchData, function(index,value) {
			$.each(value.crashReportForms, function(index1,value1) {
				if(value1.selected==true){
					$scope.isDisableButtons=false;
				}
			});
		});
	};

	// Reset user prefernce Error Msg
	$scope.userPrefenceExportButton=false;
	$scope.userPrefenceError=false;
	$scope.resetUserPreferenceError=function(){
		$scope.userPrefenceExportButton=false;
		$scope.userPrefenceError=false;
	};
	//Export Excel
	$scope.exportToExcel=function(){
		if($scope.totalRecords>searchService.getMaxRecordsDownload()){
			$("#exportAlertModal").modal('show');
		}else{
			$scope.formatType=1;
			$scope.resetUserPreferenceError();
			$("#exportOptionModal").modal('show');
			$scope.exportExcelByType=function(){
				$scope.exportButtonText="Exporting...";
				$scope.exportButton=true;
				$scope.searchParam.formatType=$scope.formatType;
				requestHandler.postExportRequest('Patient/exportExcel.xlsx',$scope.searchParam).success(function(responseData){
					 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
					 FileSaver.saveAs(blob,"Export_"+moment().format('YYYY-MM-DD')+".xlsx");
					 $scope.exportButtonText="Export to Excel";
					 $scope.exportButton=false;
				});
			};
		}
	};
	
	// Check User Preference Status
	$scope.checkPreferenceStatus=function(){
		requestHandler.getRequest("Patient/checkCustomExportPreferencess.json","").then( function(response) {
		    $scope.userPrefenceStatus= response.data.status;
		    if($scope.userPrefenceStatus==0){
		    	$scope.userPrefenceError=true;
		    	$scope.userPrefenceExportButton=true;
		    
		    }
	 });
	};
	
	// Goto Settings Function
	$scope.goToSettings=function(){
		 $('#exportOptionModal')
         .modal('hide')
         .on('hidden.bs.modal', function (e) {
          $(this).off('hidden.bs.modal'); // Remove the 'on' event binding
             
         });
	  $('body').removeClass('modal-open');
	  $('.modal-backdrop').hide();
 	  $rootScope.userPrefenceTabStatus=2;
 	  $location.path("dashboard/UserPreferrence/2");
	};
	

	// Call Logs
	 // Clinic List For Call Logs
   $scope.getClinics=function(){
   	requestHandler.getRequest("Caller/getClinicId.json","").then( function(response) {
			
		     $scope.clinic= response.data.clinicsForms;
		 
		     });
   };
   // Doctor List For Call Logs
   $scope.getDoctors=function(){
   	if($scope.calllogs.appointmentsForm.clinicId==null){
   		$scope.calllogs.appointmentsForm.clinicId="";
   	}
   	if($scope.calllogs.appointmentsForm.clinicId!=""){
   		requestHandler.getRequest("getNameByClinicId.json?clinicId="+$scope.calllogs.appointmentsForm.clinicId,"").then( function(response) {
				    $scope.doctor= response.data.doctorsForm;
			 });
   	}
   	
   };
	
// Add Call Logs Button Enable and Disable
	$scope.enableCallLogsButton=function(localReportNumber,archiveDate){
		var isChecked=false;
		var changedLocalReportNumber=localReportNumber.replace( /(\.|\(|\)|\ )/g, "\\$1" );
		var changeDate=archiveDate.replace(/(\/)/g,"\\$1");
		 $.each($scope.patientSearchData, function(index,value) {
			 $.each(value.searchResult,function(index1,value1){
				 if(archiveDate!=''){
					 if(value.archivedDate==archiveDate){
						 if(value1.localReportNumber==localReportNumber){
								$.each(value1.patientSearchLists,function(index2,value2){
									if(value2.selected==true){
										isChecked=true;
									}
								});
							}
					 }
				 }else{
					 if(value1.localReportNumber==localReportNumber){
							$.each(value1.patientSearchLists,function(index2,value2){
								if(value2.selected==true){
									isChecked=true;
								}
							});
						}
				 }
				 
				
			 });
				
			});
		 if(isChecked){
			 $("#addCallLog"+changedLocalReportNumber+changeDate+"").removeAttr("disabled",false);
		 }else{
			$("#addCallLog"+changedLocalReportNumber+changeDate+"").attr("disabled",true);
		 }
	};
	
	// Call Logs Add Modal
	  $scope.addModel=function(id,archiveDate)
		{
		  var patientIdArray=[];
		  $.each($scope.patientSearchData, function(index,value) {
			  $.each(value.searchResult,function(index1,value1){
				  if(archiveDate!=''){
					  if(value.archivedDate==archiveDate){
						  if(value1.localReportNumber==id.resultData.localReportNumber){
								$.each(value1.patientSearchLists,function(index2,value2){
									if(value2.selected==true){
										patientIdArray.push(value2.patientId);
									}
									});
							}
					  }
				  }else{
					  if(value1.localReportNumber==id.resultData.localReportNumber){
							$.each(value1.patientSearchLists,function(index2,value2){
								if(value2.selected==true){
									patientIdArray.push(value2.patientId);
								}
								});
						}
				  }
			  });
				
			});
		  if(patientIdArray.length==0){
			  alert("Select Atleast one.");
		  }else{
		  
		  	$scope.title="Add Call Log";
			$scope.options=true;
			$scope.callLogForm.$setPristine();
			$scope.calllogs={};
			$scope.calllogs.multiplePatientId=patientIdArray;
			$scope.isAlert=false;
			$scope.calllogs.appointmentsForm={};
			$scope.calllogs.timeStamp=moment().format('MM/DD/YYYY h:mm A');
			
			$("#calllogsModel").modal("show");
		  }
		};
		
		// Single Patient Add Call log From View Call Log
		  $scope.addModelFromViewCallLog=function(id)
			{
			  var patientIdArray=[];
  			  
			    patientIdArray.push(id);
			  	$scope.title="Add Call Log";
				$scope.options=true;
				$scope.callLogForm.$setPristine();
				$scope.calllogs={};
				$scope.calllogs.multiplePatientId=patientIdArray;
				$scope.isAlert=false;
				$scope.calllogs.appointmentsForm={};
				$scope.calllogs.timeStamp=moment().format('MM/DD/YYYY h:mm A');
			  $('#viewCallLogsListModal')
	            .modal('hide')
	            .on('hidden.bs.modal', function (e) {
	               
					$("#calllogsModel").modal("show");
	             $(this).off('hidden.bs.modal'); // Remove the 'on' event binding
	                
	            });
			};
		
		$scope.save=function()
		{
			
			$("#calllogsModel").modal("hide");
			$('.modal-backdrop').hide();
			requestHandler.postRequest("Caller/saveUpdateCallLogs.json",$scope.calllogs)
				.then(function(response)
					{
					Flash.create("success","You have Successfully Added!");
					$scope.searchItems($scope.patient);
					$(function(){
						$("html,body").scrollTop(0);
					});
				});
			
		};
		
		// Appointment Alert
		$scope.appointmentAlert=function(){
			if($scope.isAlert){
				if($scope.calllogs.appointmentsForm.appointmentId!=""){
					if($scope.response!=""){
						$scope.response="";
						if(confirm("Are you sure want to remove appointment?")){
							$scope.isCollapse=false;
							$scope.calllogs.appointmentsForm.doctorId="";
							$scope.calllogs.appointmentsForm.clinicId="";
							$scope.calllogs.appointmentsForm.scheduledDate="";
						}else{
							$scope.calllogs.response="4";
							$scope.response="4";
							$scope.isCollapse=true;
						}
					}
				}else if($scope.calllogs.response==4){
					$scope.response=$scope.calllogs.response;
				}
			}
		};
		// View Call Log List
		 $scope.viewCallLogsList=function(patientId,name,localReportNumber){
			 $scope.callLogPatientId=patientId;
			 $scope.callLogPatientName=name;
			 $scope.callLogLocalReportNumber=localReportNumber;
	    	  requestHandler.getRequest("Caller/getAllCallLogss.json?patientId="+patientId,"").then( function(response) {
	  	    	$scope.callLogs= response.data.callLogsForms;
	  	    	 $.each($scope.callLogs,function(index,value) {
	  		    	 switch(value.response) {
	  		    	    case 2:
	  		    	        value.response="Not Interested/Injured";
	  		    	        break;
	  		    	    case 3:
	  		    	    	value.response="Voice mail";
	  		    	        break;
	  		    	    case 4:
	  		    	    	value.response="Appointment Scheduled";
	  		    	    	break;
	  		    	    case 5:
	  		    	    	value.response="Do not call";
	  		    	    	break;
	  		    	    case 8:
			    	    	value.response="Call Back";
			    	    	break;
	  		    	    case 9:
			    	    	value.response="Unable To Reach";
			    	    	break;
	  		    	    default:
	  		    	    	break;
	  		    	} 
	  		     });
	  	    	 $("#viewCallLogsListModal").modal('show');
	  	  });	
	    };

	    // View Appointments under call log
	    $scope.viewAppointments=function(appointmentId)
		{
		if(appointmentId==null)
			{
			 $scope.appointments={};
			 $scope.appointments.notavailable=true;
			}
		else{
		requestHandler.getRequest("Caller/getAppointments.json?appointmentId="+appointmentId,"").then( function(response) {
			
			    $scope.appointments=response.data.appointmentsForm;
			    $scope.appointments.notavailable=false;
		  });
		}	
			 $("#viewAppointmentsModal").modal("show");
			 
		
		};
	
		$scope.viewNearByClinic=function(patientId){
			$window.open('#/dashboard/viewlocations/'+patientId,'Crash Reports Online','width=1200,height=600,scrollbars=1');
		};
		
		$scope.resetSearchData = function(){
		    // $scope.patientSearchForm.$setPristine();
		     $scope.patientSearchData="";
		     searchService.resetSearchData();
		     $scope.init();
		};
		
	
	$scope.init();
	
	//Watch Age Filter
	$scope.$watch('patient.age' , function() {	
		if($scope.patient.age.length!=0){
			angular.copy($scope.patient.age,$scope.ageFilterCurrentSelection);
		}else{
			angular.copy($scope.ageFilterCurrentSelection,$scope.patient.age);
		}
	    
	}, true );
	
	// Age Drop down Events
	$scope.ageEvents = {onInitDone: function(item) {console.log("initi",item);},
			onItemDeselect: function(item) {console.log("deselected",item);if($scope.patient.age!=''){$scope.secoundarySearchPatient();}},
			onItemSelect: function(item) {console.log("selected",item);$scope.secoundarySearchPatient();}};
	
	//Watch County Filter
	$scope.$watch('patient.countyId' , function() {		
	   if(!$scope.loadingCounties){
		   if($scope.patient.countyId.length==0){
			   $scope.disableSearch=true;
			   $scope.searchCountyMinError=true;
		   }else{
			   $scope.searchCountyMinError=false;
			   if(!$scope.searchTierMinError)
				   $scope.disableSearch=false;			   
		   }
	   }
		
	}, true );
	
	//Watch Tier Filter
	$scope.$watch('patient.tier' , function() {		
		   if($scope.patient.tier.length==0){
			   $scope.disableSearch=true;
			   $scope.searchTierMinError=true;
		   }else{
			   $scope.searchTierMinError=false;
			   if(!$scope.searchCountyMinError)
				   $scope.disableSearch=false;	
		   }
		}, true );
	
	// Watch Damage Scale Filter
	$scope.$watch('patient.damageScale' , function() {		
		   if($scope.patient.damageScale.length==0){
			   $scope.disableSearch=true;
			   $scope.searchDamageScaleMinError=true;
		   }else{
			   $scope.searchDamageScaleMinError=false;
			   if(!$scope.searchCountyMinError)
				   $scope.disableSearch=false;	
		   }
		}, true );
	
	// County Drop down Events
	$scope.countyEvents = {onInitDone: function(item) {console.log("initi ciuny",$scope.countyListType);},
			onItemDeselect: function(item) {console.log("deselected couny",item);},
			onItemSelect: function(item) {console.log("selected couny",item);},
			onPreferenceChange: function(item) {
				$scope.countyListType=item;
				searchService.getPreferenceCoutyList(item).then(function(response){
					$scope.countylist=response;
					$scope.patient.countyId=[];
					if($scope.countylist.length>0){
						$.each(response, function(index,value) {
							$scope.patient.countyId.push({"id":value.countyId});
						});
					}else{
						$location.path("dashboard/UserPreferrence/1");
					}
				
				});
			}};
	
	// While Change to Open to Archive vice versa
	$scope.resetResultData=function(){
		$scope.patientSearchData="";
		$scope.totalRecords="";
		$scope.directRunnerReportSearchData="";
	};
	
	// Direct Report Change Status Modal
	$scope.directReportChangeStatusModal=function(crashId,reportStatus){
		$scope.directReportStatusValue="";
		$scope.directReportChangeStatusForm.$setPristine();
		$scope.directReportCrashId=crashId;
		if(reportStatus!=null){
			$scope.directReportStatusValue=reportStatus.toString();
		}
		$("#directReportChangeStatusModal").modal('show');
	};
	
	// Direct Report Change Status
	$scope.directReportChangeStatus=function(){
		$scope.directReportForm={};
		$scope.directReportForm.crashId=[];
		$scope.directReportForm.crashId.push($scope.directReportCrashId);
		$scope.directReportForm.status=$scope.directReportStatusValue;
		requestHandler.postRequest("/Caller/directReportChangeStatus.json",$scope.directReportForm).then(function(response){
			if(response.data.success){
				$("#directReportChangeStatusModal").modal('hide');
				Flash.create('success', "You have Successfully Changed!");
				$scope.searchItems($scope.patient);
			}
		});
	};
	
	// Print Reports
	$scope.printReports=function(){
		$scope.user="";
		googleService.login().then(function(data){
			console.log(data);
			$scope.xsrf=gapi.auth.getToken().access_token;
			requestHandler.postRequest("/getPrinterList.json?xsrf="+$scope.xsrf+"&accessToken="+$scope.xsrf,"").then(function(response){
				if(response.data.success){
					$scope.user=response.data.request.user;
					$scope.printersList=response.data.printers;
					$.each($scope.printersList,function(key,value){
						if(value.defaultDisplayName==""){
							value.defaultDisplayName=value.name;
						}
						
						if(value.connectionStatus=='UNKNOWN'){
							value.connectionStatus="";
						}else{
							value.displayConnectionStatus="("+value.connectionStatus+")";
						}
					
					});
					$("#selectPrinterModal").modal("show");
				}else{
					alert("Something went wrong!!!");
				}
				
			});
		});
	};

	$scope.reportsListAlreadySelected=[];
	// post files to Print
	$scope.postFilesToPrint=function(){
		$scope.sendingReportsList=[];
		$scope.sendingReportsList.printerId=$scope.printerId;
		$scope.sendingReportsList.xsrf=$scope.xsrf;
		if($scope.patient.isRunnerReport==3){
			$.each($scope.directRunnerReportSearchData, function(index,value) {
				$.each(value.crashReportForms, function(index1,value1) {
					if(value1.selected==true){
						$scope.sendingReportsList.push({"localReportNumber":value1.localReportNumber,"fileName":value1.filePath,"printStatus":0});
					}
				});
			});
		}else{
			var localReportNumber="";
			$.each($scope.patientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							if(localReportNumber!=value2.localReportNumber){
								$scope.sendingReportsList.push({"localReportNumber":value2.localReportNumber,"fileName":value2.crashReportFileName,"printStatus":0});
								localReportNumber=value1.localReportNumber;
							}
							else
								console.log("available");
						}
						});
				});
				
			});
		}
		

		
		/*$scope.sendingReportsList.printerId=$scope.printerId;
		$scope.sendingReportsList.xsrf=$scope.xsrf;
		if($scope.reportsListAlreadySelected.length==0){
			angular.copy(reportsList,$scope.reportsListAlreadySelected);
			angular.copy(reportsList,$scope.sendingReportsList);
			console.log("selected",$scope.reportsListAlreadySelected);
		}else{
			$.each(reportsList,function(selectedKey,selectedValue){
				var found = false;
				for(var i = 0; i < $scope.reportsListAlreadySelected.length; i++) {
				    if ($scope.reportsListAlreadySelected[i].localReportNumber == selectedValue.localReportNumber) {
				        found = true;
				        break;
				    }
				}
				
				if(found==false){
					$scope.sendingReportsList.push({"localReportNumber":selectedValue.localReportNumber,"fileName":selectedValue.fileName,"printStatus":0});
					$scope.reportsListAlreadySelected.push({"localReportNumber":selectedValue.localReportNumber,"fileName":selectedValue.fileName,"printStatus":0});
				}else{
					$scope.sendingReportsList.push({"localReportNumber":selectedValue.localReportNumber,"fileName":selectedValue.fileName,"printStatus":7});
				}
			});	
		}*/
		var randomnumber = Math.floor((Math.random()*100)+1);
		window.$windowScope = $scope;
		var $popup=$window.open('#/dashboard/printreports/','_blank','Crash Reports Online',randomnumber,'width=1200,height=600,scrollbars=1');
		/*if($scope.sendingReportsList.length!=0){
			
			// $popup.reportsList=sendingReportsList;
		}else{
			alert("Selected Reports are already in printing list (or) Printed. Please Select some other reports.");
		}*/
		
	};
	
	// Logout Account And Login with another account
	$scope.switchAccount=function(){ 
		var $popup=$window.open('https://mail.google.com/mail/u/0/?logout&hl=en','Gmail','width=900,height=500,scrollbars=1');
		$("#selectPrinterModal").modal("hide");
		$timeout(function(){$popup.close();$scope.printReports();},3000);
	};
	
	// Move to Help Page
	$scope.moveToHelpPage=function(){
		$('#selectPrinterModal')
        .modal('hide')
        .on('hidden.bs.modal', function (e) {
           
			$("#printReportsHelpModal").modal("show");
          $(this).off('hidden.bs.modal'); // Remove the 'on' event binding
            
        });
	};
}]); 

 