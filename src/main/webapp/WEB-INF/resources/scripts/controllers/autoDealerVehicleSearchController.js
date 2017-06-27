var adminApp=angular.module('sbAdminApp', ['requestModule', 'angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver','googleModule']);

adminApp.controller('AutoDealerVehicleSearchController', ['$rootScope','$scope','requestHandler','searchService','$state','$location','Flash','FileSaver','$q','$window','googleService','$timeout', function($rootScope,$scope,requestHandler,searchService,$state,$location,Flash,FileSaver,$q,$window,googleService,$timeout) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.autoDealerVehicleSearchData=$scope.autoDealerVehicleSearchDataOrginal=[];
	$scope.isCheckedAllPatients=false;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.ageFilterCurrentSelection=[];
	$scope.setScrollDown=false;
	$scope.loadingCounties=true;
	
    $scope.archivedToDateRequired=false;
	$scope.archivedFromDateRequired=false;
	// Main Search Param
	$scope.mainSearchParam={};
	
	 $scope.getDealerList=function(){
		 	requestHandler.getRequest("CAdmin/getCallersByCallerAdmin.json","").then(function(response){
				$scope.dealerList=response.data.callerForms;
			});
	    };
	    
	    if($rootScope.isAdmin==6){
	    	 $scope.getDealerList(); 
	    }
	   
	  $scope.checkCustomDate=function(){
		//Reset to date if less than from date
			var fromDate=new Date($scope.vehicle.crashFromDate);
			var toDate=new Date($scope.vehicle.crashToDate);
			if(toDate<fromDate)
				$scope.vehicle.crashToDate="";
			//End Reset to date if less than from date
		 if($scope.vehicle.crashFromDate!=''){
				$scope.disableCustom=false;
			}
			else{
				$scope.disableCustom=true;
				$scope.crashToRequired=false;
				$scope.vehicle.crashToDate="";
			}
		};
		$scope.checkAddedOnToDate=function(){
			if($scope.vehicle.addedOnFromDate!="")
				$scope.isSelectedAddedFromDate=false;
			else{
				$scope.vehicle.addedOnToDate="";
				$scope.addedToRequired=false;
				$scope.isSelectedAddedFromDate=true;
			}
			//Reset to date if less than from date
			var fromDate=new Date($scope.vehicle.addedOnFromDate);
			var toDate=new Date($scope.vehicle.addedOnToDate);
			if(toDate<fromDate)
				$scope.vehicle.addedOnToDate="";
			//End Reset to date if less than from date
		};
		
		$scope.isChecked=function(){
			if($scope.autoDealerVehicleSearchData.length>0){
				$.each($scope.autoDealerVehicleSearchData, function(index,value) {
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
			
			if($scope.isCheckedAllGroupPatients){
				$scope.isCheckedAllGroupPatients=false;
			}
			
			if($scope.isCheckedAllGroupArchivePatients){
				$scope.isCheckedAllGroupArchivePatients=false;
			}
		};
	
	$scope.searchItems=function(searchObj){
		$scope.isLoading=true;
		$scope.isDisableButtons=false;
		//To avoid overwriting actual $scope.vehicle object.
		$scope.searchParam={};
		angular.copy(searchObj,$scope.searchParam);
		
		
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
			requestHandler.postRequest("Patient/searchPatients.json",$scope.searchParam).then(function(response){
				$scope.isLoading=false;
				if($scope.searchParam.isRunnerReport!=3){
					$scope.totalRecords=0;
					$scope.totalRecords=response.data.patientGroupedSearchResult.totalNoOfRecord;
					$scope.autoDealerVehicleSearchData=response.data.patientGroupedSearchResult.patientSearchResults;
					
					$.each($scope.autoDealerVehicleSearchData, function(index,value) {
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
						defer.resolve(response);
					});
					$scope.autoDealerVehicleSearchDataOrginal=angular.copy($scope.autoDealerVehicleSearchData);
					$scope.isCheckedIndividual();
				}else{
					$scope.totalRecords=0;
					$scope.autoDealerVehicleSearchData={};
					$scope.totalRecords=response.data.directReportGroupResult.totalNoOfRecords;
					$scope.directRunnerReportSearchData=response.data.directReportGroupResult.directReportGroupListByArchives;
					$.each($scope.directRunnerReportSearchData,function(key,value){
						$.each(value.crashReportForms,function(key1,value1){
							switch(value1.directReportStatus){
							case null:
								value1.directReportStatusName="New";
								break;
							case 1:
								value1.directReportStatusName="Processing";
								break;
							case 2:
								value1.directReportStatusName="Do Not Call";
								break;
							default:
								break;
							}
						});
						
					});
					$scope.isCleanCheckboxDirectReport();
				}
				
			});
			return defer.promise;
	};
	 
	$scope.searchPatients = function(){
	
	if($scope.vehicle.crashFromDate!="" && $scope.vehicle.crashToDate==""){
		$scope.crashToRequired=true;
	}
	else if($scope.vehicle.addedOnFromDate!="" && $scope.vehicle.addedOnToDate==""){
		$scope.addedToRequired=true;
	}else {
		$scope.crashToRequired=false;
		$scope.addedToRequired=false;
		$scope.oldPageNumber=$scope.vehicle.pageNumber;
		$scope.vehicle.pageNumber=1;
		if($scope.oldPageNumber==$scope.vehicle.pageNumber){//This will call search function thru vehicle.pageNumber object $watch function 
			$scope.vehicle.archivedFromDate=searchService.getArchivedFromDate();
			$scope.vehicle.archivedToDate=searchService.getArchivedToDate(); 
			$scope.searchItems($scope.vehicle);
		}
		// To Avoid Main Search Parameter Override
		angular.copy($scope.vehicle,$scope.mainSearchParam);	
	}
	// Set To Service
	searchService.setCounty(angular.copy($scope.vehicle.countyId));
	searchService.setNumberOfDays($scope.vehicle.numberOfDays);
	searchService.setCrashFromDate($scope.vehicle.crashFromDate);
	searchService.setCrashToDate($scope.vehicle.crashToDate);
	searchService.setCallerId($scope.vehicle.callerId);
	searchService.setPatientName($scope.vehicle.patientName);
	searchService.setAge($scope.vehicle.age);
	searchService.setLocalReportNumber($scope.vehicle.localReportNumber);
	searchService.setAddedOnFromDate($scope.vehicle.addedOnFromDate);
	searchService.setAddedOnToDate($scope.vehicle.addedOnToDate);
	searchService.setPatientStatus($scope.vehicle.patientStatus);
	searchService.setIsArchived($scope.vehicle.isArchived);
	searchService.setPageNumber($scope.vehicle.pageNumber);
	searchService.setItemsPerPage($scope.vehicle.itemsPerPage);
	searchService.setCountyListType($scope.countyListType);
	searchService.setDamageScale(angular.copy($scope.vehicle.damageScale));
	searchService.setDirectReportStatus($scope.vehicle.directReportStatus);
	searchService.setReportingAgency($scope.vehicle.reportingAgency);
	searchService.setVehicleMake($scope.vehicle.vehicleMake);
	searchService.setVehicleYear($scope.vehicle.vehicleYear);
	};
	
	$scope.secoundarySearchPatient=function(){
		searchService.setPatientName($scope.vehicle.patientName);
		searchService.setIsArchived($scope.vehicle.isArchived);
		if($scope.vehicle.isArchived==0){
			$scope.archivedToDateRequired=false;
			$scope.archivedFromDateRequired=false;
			$scope.vehicle.archivedFromDate="";
			$scope.vehicle.archivedToDate="";
			searchService.setArchivedFromDate($scope.vehicle.archivedFromDate);
			searchService.setArchivedToDate($scope.vehicle.archivedToDate);
		}
		searchService.setAge($scope.vehicle.age);
		searchService.setPatientStatus($scope.vehicle.patientStatus);
		searchService.setDirectReportStatus($scope.vehicle.directReportStatus);
		$scope.oldPageNumber=$scope.vehicle.pageNumber;
		$scope.vehicle.pageNumber=1;
		searchService.setPageNumber($scope.vehicle.pageNumber);
		searchService.setItemsPerPage($scope.vehicle.itemsPerPage);
		
		// Main Search Param
		$scope.mainSearchParam.pageNumber=$scope.vehicle.pageNumber;
		$scope.mainSearchParam.patientName=$scope.vehicle.patientName;
		$scope.mainSearchParam.isArchived=$scope.vehicle.isArchived;
		$scope.mainSearchParam.archivedFromDate=searchService.getArchivedFromDate();
		$scope.mainSearchParam.archivedToDate=searchService.getArchivedToDate();
		$scope.mainSearchParam.age=$scope.vehicle.age;
		$scope.mainSearchParam.patientStatus=$scope.vehicle.patientStatus;
		$scope.mainSearchParam.directReportStatus=$scope.vehicle.directReportStatus;
		$scope.mainSearchParam.itemsPerPage=$scope.vehicle.itemsPerPage;
		
		if($scope.oldPageNumber==$scope.vehicle.pageNumber){//This will call search function thru vehicle.pageNumber object $watch function 
			// Copy Mainsearchparam to Patient
			angular.copy($scope.mainSearchParam,$scope.vehicle);
			// County List
			$scope.countyListType=searchService.getCountyListType();
			searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
				$scope.mycounties=response;
				$scope.searchCountyMinError=false;
	    		$scope.loadingCounties=false;
			});
			return $scope.searchItems($scope.mainSearchParam);
		}
		return null;
	};
	
	$scope.loadPreferenceCountyList=function(){
		$scope.countyListType=searchService.getCountyListType();
		searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
			$scope.mycounties=response;
			$scope.searchCountyMinError=false;
    		$scope.loadingCounties=false;
		});
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
	
	// Watch Report Type
	$scope.$watch("vehicle.isRunnerReport",function(){
		$scope.mainSearchParam.pageNumber=1;
		$scope.mainSearchParam.isRunnerReport=$scope.vehicle.isRunnerReport;
		searchService.setIsRunnerReport($scope.vehicle.isRunnerReport);
		searchService.setPageNumber($scope.vehicle.pageNumber);
		searchService.setItemsPerPage($scope.vehicle.itemsPerPage);
		// Copy Mainsearchparam to Patient
		angular.copy($scope.mainSearchParam,$scope.vehicle);
		if($scope.mainSearchParam.countyId!=''){
			// County List
			$scope.countyListType=searchService.getCountyListType();
			searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
				$scope.mycounties=response;
				$scope.loadingCounties=false;
			});
			var promise=$scope.searchItems($scope.mainSearchParam);
			promise.then(function(reponse){
				// After Search
			});
			
		}
	});
	
	$scope.$watch("vehicle.pageNumber",function(){
		$scope.mainSearchParam.pageNumber=$scope.vehicle.pageNumber;
		searchService.setPageNumber($scope.vehicle.pageNumber);
		searchService.setItemsPerPage($scope.vehicle.itemsPerPage); 
		// Copy Mainsearchparam to Patient
		angular.copy($scope.mainSearchParam,$scope.vehicle);
		if($scope.mainSearchParam.countyId!=''){
			var promise=$scope.searchItems($scope.mainSearchParam); 
			 if($scope.setScrollDown){
				 promise.then(function(response){
				 console.log("scroll down complex");
				 $scope.setScrollDown=false;
				 setTimeout(function(){
	       			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
	       		 },100);
				 });
			 }
		}
	});
	
	 $scope.selectGroup=function(id,archiveDate){
			$.each($scope.autoDealerVehicleSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					if(archiveDate!=''){
						if(value.archivedDate==archiveDate){
							if(value1.localReportNumber==id.resultData.localReportNumber){
								var i=0;
								for(i;i<value1.vehicleCount;i++){
									if(value1.patientSearchLists[i]!=undefined)
									  value1.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
								}
							}
						}
					}else{
						if(value.archivedDate==archiveDate){
							if(value1.localReportNumber==id.resultData.localReportNumber){
								var i=0;
								for(i;i<value1.vehicleCount;i++){
									if(value1.patientSearchLists[i]!=undefined)
									  value1.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
								}
							}
						}
					}
					
				});
			
			});
	};
	
	// Select Archive Group
	 $scope.selectArchiveGroup=function(id,archiveDate){
			$.each($scope.autoDealerVehicleSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					if(archiveDate!=''){
						if(value.archivedDate==archiveDate){
							var i=0;
							for(i;i<value1.vehicleCount;i++){
								if(value1.patientSearchLists[i]!=undefined){
									/*var changedLocalReportNumber=value1.patientSearchLists[i].localReportNumber.replace( /(\.|\(|\)|\ )/g, "\\$1" );
									var changeDate=archiveDate.replace(/(\/)/g,"\\$1");*/
									 value1.patientSearchLists[i].selected=id.isCheckedAllGroupArchivePatients;
									/* $("#"+changedLocalReportNumber+changeDate).prop("checked",id.isCheckedAllGroupArchivePatients);*/
								}
							}
						 }
					}
				});
			});
			
	};
	
	$scope.assignDealerPopup=function(){
		$scope.single= false;
		$("#assignDealerModal").modal('show');
	};
	
	$scope.assignSingleDealerPopup=function(patientId,name,dealerId){
		$scope.single=true;
		$scope.patientname=name;
		$scope.patientId=patientId;
		if(dealerId!=null){
			dealerId= dealerId.toString();
			$scope.myForm.dealerId=dealerId;
		}
		else {$scope.myForm.dealerId="";}
		$("#assignDealerModal").modal('show');
	};
	
	$scope.assignSingleDealer=function(patientId){
		var assignDealerObj ={};
		var patientIdArray=[];
		patientIdArray.push(patientId);
		assignDealerObj.callerId=$scope.myForm.dealerId;
		assignDealerObj.patientId=patientIdArray;
		$scope.assign(assignDealerObj);
		
	};
	
	$scope.assign=function(assignDealerObj){
		requestHandler.postRequest("/CAdmin/assignCaller.json",assignDealerObj).then(function(response){
			
			Flash.create('success', "You have Successfully Assigned Dealer!");
			$scope.searchItems($scope.mainSearchParam);
			$scope.loadPreferenceCountyList();
			angular.copy($scope.mainSearchParam,$scope.vehicle);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	
	$scope.assignDealer=function(){
		var assignDealerObj ={};
		assignDealerObj.callerId=$scope.myForm.dealerId;
		var patientIdArray=[];
		$.each($scope.autoDealerVehicleSearchData, function(index,value) {
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.selected==true){
						patientIdArray.push(value2.patientId);
					}
				});
			});
			
		});
		assignDealerObj.patientId=patientIdArray;
		$scope.assign(assignDealerObj);
	
	};
	
	
	$scope.releaseDealer=function(){
		var assignDealerObj ={};
		var patientIdArray=[];
		$.each($scope.autoDealerVehicleSearchData, function(index,value) {
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.selected==true){
						patientIdArray.push(value2.patientId);
					}
				});
			});
			
		});
		assignDealerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Caller/releaseCaller.json",assignDealerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Dealer!");
			$scope.searchItems($scope.mainSearchParam);
			$scope.loadPreferenceCountyList();
			angular.copy($scope.mainSearchParam,$scope.vehicle);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	// Move Multiple File to Archive
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
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}else{
			var moveArchiveObj ={};
			var patientIdArray=[];
			$.each($scope.autoDealerVehicleSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							patientIdArray.push(value2.patientId);
						}
					});
				});
				
			});
			moveArchiveObj.patientId=patientIdArray;
			
			requestHandler.postRequest("/Caller/moveToArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Move Single File To Archive
	$scope.moveSingleFileToArchive=function(patientId){
		var moveArchiveObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to move to archive?")){
			moveArchiveObj.patientId=patientIdArray;
			requestHandler.postRequest("/Caller/moveToArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
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
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
		
	};
	
	// Release Multiple File From Archive
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
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}else{
			var moveArchiveObj ={};
			var patientIdArray=[];
			$.each($scope.autoDealerVehicleSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							patientIdArray.push(value2.patientId);
						}
					});
				});
				
			});
			moveArchiveObj.patientId=patientIdArray;
			
			requestHandler.postRequest("/Caller/releaseFromArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Release Single File From Archive
	$scope.releaseSingleFileFromArchive=function(patientId){
		var moveArchiveObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to release from archive?")){
			moveArchiveObj.patientId=patientIdArray;
			requestHandler.postRequest("/Caller/releaseFromArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
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
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.vehicle);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.autoDealerVehicleSearchData, function(index,value) {
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.patientId ==patientId){
						$scope.patientDetails=value2;
					}
				});
			});
			
			
		});
		/*requestHandler.getRequest("/Patient/getPatient.json?patientId="+patientId,"").then(function(response){
			$scope.patientDetails=response.data.patientForm;
		
			});*/

	};
	
	$scope.init=function(){

		//Initialize DropDown
			
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		$scope.defaultDamageScale=[{id: 1, label: "None",legendClass:"badge-success",haveLegend:true},{id: 2, label: "Minor",legendClass:"badge-yellow",haveLegend:true},{id: 3, label: "Functional",legendClass:"badge-primary",haveLegend:true},{id: 4, label: "Disabling",legendClass:"badge-danger",haveLegend:true},{id: 9, label: "Unknown",legendClass:"badge-default",haveLegend:true},{id: 5, label: "N/A",haveLegend:false}];
		
		$scope.vehicle={};
		$scope.totalRecords=0;
		$scope.vehicle.countyId=[];
		$scope.vehicle.damageScale=[];
		$scope.vehicle.isOwner=1;
		angular.copy(searchService.getCounty(),$scope.vehicle.countyId);
		angular.copy(searchService.getDamageScale(),$scope.vehicle.damageScale);
		$scope.vehicle.crashFromDate=searchService.getCrashFromDate();
		$scope.vehicle.crashToDate=searchService.getCrashToDate();
		$scope.vehicle.localReportNumber=searchService.getLocalReportNumber();
		$scope.vehicle.patientName=searchService.getPatientName();
		$scope.vehicle.age=searchService.getAge();
		$scope.vehicle.callerId=searchService.getCallerId();
		$scope.vehicle.phoneNumber=searchService.getPhoneNumber();
		$scope.vehicle.itemsPerPage=searchService.getItemsPerPage();
		$scope.vehicle.addedOnFromDate=searchService.getAddedOnFromDate();
		$scope.vehicle.addedOnToDate=searchService.getAddedOnToDate();
		$scope.vehicle.isArchived=searchService.getIsArchived();
		$scope.vehicle.archivedFromDate=searchService.getArchivedFromDate();
		$scope.vehicle.archivedToDate=searchService.getArchivedToDate();
		$scope.vehicle.patientStatus=searchService.getPatientStatus();
		$scope.countyListType=searchService.getCountyListType();
		$scope.vehicle.directReportStatus=searchService.getDirectReportStatus();
		$scope.vehicle.reportingAgency=searchService.getReportingAgency();
		$scope.vehicle.vehicleMake=searchService.getVehicleMake();
		$scope.vehicle.vehicleYear=searchService.getVehicleYear();
		$scope.isSelectedAddedFromDate=true;

		// Report Type
		$scope.vehicle.isRunnerReport=searchService.getIsRunnerReport();
		
		$scope.vehicle.pageNumber= searchService.getPageNumber();
		$scope.oldPageNumber= $scope.vehicle.pageNumber;
		if($scope.oldPageNumber==$scope.vehicle.pageNumber){//This will call search function thru vehicle.pageNumber object $watch function 
			// To Avoid Main Search Parameter Override
			angular.copy($scope.vehicle,$scope.mainSearchParam);
			if($scope.vehicle.countyId!=''){
				searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
					$scope.mycounties=response;
					$scope.searchCountyMinError=false;
		    		$scope.loadingCounties=false;
				});
				$scope.searchItems($scope.vehicle);
			}else{
				searchService.checkCoutyListType().then(function(response){
					searchService.setCountyListType(response);
					$scope.countyListType=response;
					searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
						$scope.mycounties=response;
						$scope.searchCountyMinError=false;
			    		$scope.loadingCounties=false;
					});
					searchService.getInitPreferenceCoutyList($scope.countyListType).then(function(response){
						angular.copy(response,$scope.vehicle.countyId);
						$scope.mainSearchParam.countyId=angular.copy($scope.vehicle.countyId);
						$scope.searchItems($scope.vehicle);
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
		
	};
	
	$scope.init();
	
	$scope.resetSearchData = function(){
		 $scope.vehicle={};
		 $scope.vehicle.numberOfDays="0";
		 $scope.vehicle.lawyerId="0";
	     $scope.vehicleSearchForm.$setPristine();
	     $scope.lAdminPatientSearchData="";
	     $scope.totalRecords="";
	     $scope.addedToRequired=false;
	     $scope.crashToRequired=false;

	     $scope.addedToRequired=false;
	     $scope.crashToRequired=false;
	     searchService.resetSearchData();
	     $scope.init();
	     
	};
	
	// Check Archived To Date
	$scope.checkArchivedToDate=function(){
		$scope.archivedFromDateRequired=false;
		//Reset to date if less than from date
		var fromDate=new Date($scope.vehicle.archivedFromDate);
		var toDate=new Date($scope.vehicle.archivedToDate);
		if(toDate<fromDate)
			$scope.vehicle.archivedToDate="";
		//End Reset to date if less than from date
	};
	
	// Reset Archive Filter Date
	$scope.resetArchiveFilterDate=function(){
$scope.archivedToDateRequired=false;
			$scope.archivedFromDateRequired=false;
		$scope.vehicle.archivedFromDate="";
		$scope.vehicle.archivedToDate="";
		searchService.setArchivedFromDate($scope.vehicle.archivedFromDate);
		searchService.setArchivedToDate($scope.vehicle.archivedToDate);
		$scope.secoundarySearchPatient();
	};
	
	// Search With Archive Filter
	$scope.searchWithArchiveDateFilter=function(){
		if($scope.vehicle.archivedFromDate==""){
			$scope.archivedFromDateRequired=true;
		}
		else if($scope.vehicle.archivedToDate==""){
			$scope.archivedToDateRequired=true;
		}else{
			$scope.archivedFromDateRequired=false;
			$scope.archivedToDateRequired=false;
			searchService.setArchivedFromDate($scope.vehicle.archivedFromDate);
			searchService.setArchivedToDate($scope.vehicle.archivedToDate);
			$scope.secoundarySearchPatient();
		}
	};
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.autoDealerVehicleSearchData,$scope.autoDealerVehicleSearchDataOrginal);
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
	
	//Watch Age Filter
	$scope.$watch('patient.age' , function() {	
		if($scope.vehicle.age.length!=0){
			angular.copy($scope.vehicle.age,$scope.ageFilterCurrentSelection);
		}else{
			angular.copy($scope.ageFilterCurvehicleection,$scope.vehicle.age);
		}
	    
	}, true );
	
	// Age Drop down Events
	$scope.ageEvents = {onInitDone: function(item) {console.log("initi",item);},
			onItemDeselect: function(item) {console.log("deselected",item);if($scope.vehicle.age!=''){$scope.secoundarySearchPatient();}},
			onItemSelect: function(item) {console.log("selected",item);$scope.secoundarySearchPatient();}};
	
	//Watch County Filter
	$scope.$watch('vehicle.countyId' , function() {		
	   if(!$scope.loadingCounties){
		   if($scope.vehicle.countyId.length==0){
			   $scope.disableSearch=true;
			   $scope.searchCountyMinError=true;
		   }else{
			   $scope.searchCountyMinError=false;
			   if(!$scope.searchTierMinError)
				   $scope.disableSearch=false;			   
		   }
	   }
	   /*$scope.vehicle.reportingAgency=[];
	   //Some change happened in county selection lets update reporting agency list too
	   searchService.getReportingAgencyList($scope.vehicle.countyId).then(function(response){
		 //Load Reporting Agency List		   
		 $scope.reportingAgencyList=response;  
	   });*/
	   
	   
	}, true );
	
	// County Drop down events
	$scope.countyEvents = {onInitDone: function(item) {},
			onItemDeselect: function(item) {},
			onItemSelect: function(item) {},
			onPreferenceChange: function(item) {
				$scope.countyListType=item;
				searchService.getPreferenceCoutyList(item).then(function(response){
					$scope.mycounties=response;
					$scope.vehicle.countyId=[];
					if($scope.mycounties.length>0){
						$.each(response, function(index,value) {
							$scope.vehicle.countyId.push({"id":value.countyId});
						});
					}else{
						if($rootScope.isAdmin==6){
							$location.path("dashboard/UserPreferrence/1");
						}else{
							alert("Not done with Preference, Please Contact Manager to set up.");
						}
					}
					
				});
			}};
	
	//Reporting Agency Drop Down Events
	$scope.reportingAgencyEvents={onInitDone: function(item) {console.log("initi ciuny",$scope.countyListType);},
			onItemDeselect: function(item) {console.log("deselected couny",item);},
			onItemSelect: function(item) {console.log("selected couny",item);},
			onPreferenceChange: function(item) {}
	};
	
	// Watch Damage Scale Filter
	$scope.$watch('vehicle.damageScale' , function() {		
		   if($scope.vehicle.damageScale.length==0){
			   $scope.disableSearch=true;
			   $scope.searchDamageScaleMinError=true;
		   }else{
			   $scope.searchDamageScaleMinError=false;
			   if(!$scope.searchCountyMinError)
				   $scope.disableSearch=false;	
		   }
		}, true );
	
	// While Change to Open to Archive vice versa
	$scope.resetResultData=function(){
		$scope.autoDealerVehicleSearchData="";
		$scope.totalRecords="";
		$scope.directRunnerReportSearchData="";
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
		if($scope.vehicle.isRunnerReport==3){
			$.each($scope.directRunnerReportSearchData, function(index,value) {
				$.each(value.crashReportForms, function(index1,value1) {
					if(value1.selected==true){
						$scope.sendingReportsList.push({"localReportNumber":value1.localReportNumber,"fileName":value1.filePath,"printStatus":0});
					}
				});
			});
		}else{
			var localReportNumber="";
			$.each($scope.autoDealerVehicleSearchData, function(index,value) {
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
		
		
		var randomnumber = Math.floor((Math.random()*100)+1);
		window.$windowScope = $scope;
		var $popup=$window.open('#/dashboard/printreports/','_blank','Crash Reports Online',randomnumber,'width=1200,height=600,scrollbars=1');
		
		// Send Only Newly Selected Reports
	/*	$scope.sendingReportsList=[];
		$scope.sendingReportsList.printerId=$scope.printerId;
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
		}
		
		if($scope.sendingReportsList.length!=0){
			var randomnumber = Math.floor((Math.random()*100)+1);
			window.$windowScope = $scope;
			var $popup=$window.open('#/dashboard/printreports/','_blank','Crash Reports Online',randomnumber,'width=1200,height=600,scrollbars=1');
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
	
	//------------------------- Direct Report --------------------
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
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
			}
		});
	};
}]); 

// Pop over Directive
adminApp.directive( 'popoverHtmlUnsafePopup', function () {
    return {
        restrict: 'EA',
        replace: true,
        scope: { title: '@', content: '@', placement: '@', animation: '&', isOpen: '&' },
        template: '<div class="popover {{placement}}" ng-class="{ in: isOpen(), fade: animation() }"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title" bind-html-unsafe="title" ng-show="title"></h3><div class="popover-content" bind-html-unsafe="content"></div></div></div>'
    };
})
.directive( 'popoverHtmlUnsafe', [ '$tooltip', function ( $tooltip ) {
    return $tooltip('popoverHtmlUnsafe', 'popover', 'click' );
}]);