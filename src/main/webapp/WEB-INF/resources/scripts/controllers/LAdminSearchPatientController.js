var adminApp=angular.module('sbAdminApp', ['requestModule', 'angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver','googleModule']);

adminApp.controller('LAdminSearchPatientsController', ['$rootScope','$scope','requestHandler','searchService','$state','$location','Flash','FileSaver','$q','$window','googleService','$timeout', function($rootScope,$scope,requestHandler,searchService,$state,$location,Flash,FileSaver,$q,$window,googleService,$timeout) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.lAdminPatientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isCheckedAllPatients=false;
	// Export Excel
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.isExportPatientSelected=true;
	$scope.isExcludeState=false;
	
	$scope.ageFilterCurrentSelection=[];
	$scope.setScrollDown=false;
	$scope.loadingCounties=true;
	
    $scope.archivedToDateRequired=false;
	$scope.archivedFromDateRequired=false;
	// Main Search Param
	$scope.mainSearchParam={};
	
	$scope.reportingAgencyListType="1";
	
	 $scope.getLawyerList=function(){
	    	requestHandler.getRequest("LAdmin/getLawyersByLawyerAdmin.json","").then(function(response){
	    		$scope.lawyers=response.data.lawyersForms;
	    	});
	    };
	    

	/* $scope.getMyCountyList=function(){
		 $scope.loadingCounties=true;
	    	requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
	    		$scope.mycounties=response.data.countyList;
	    		$scope.searchCountyMinError=false;
	    		$scope.loadingCounties=false;
	    		$.each($scope.mycounties, function(index,value) {
	    			$scope.patient.countyId.push({"id":value.countyId});
	    		});
	    	});
	    };*/
	    
	    $scope.getLawyerList(); 
	 
	 
		 $scope.checkCustomDate=function(){
		//Reset to date if less than from date
			var fromDate=new Date($scope.patient.crashFromDate);
			var toDate=new Date($scope.patient.crashToDate);
			if(toDate<fromDate)
				$scope.patient.crashToDate="";
			//End Reset to date if less than from date
		 if($scope.patient.crashFromDate!=''){
				$scope.disableCustom=false;
			}
			else{
				$scope.disableCustom=true;
				$scope.crashToRequired=false;
				$scope.patient.crashToDate="";
			}
		};
		$scope.checkAddedOnToDate=function(){
			if($scope.patient.addedOnFromDate!="")
				$scope.isSelectedAddedFromDate=false;
			else{
				$scope.patient.addedOnToDate="";
				$scope.addedToRequired=false;
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
			if($scope.lAdminPatientSearchData.length>0){
				$.each($scope.lAdminPatientSearchData, function(index,value) {
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
		
		//Manipulate Reporting Agency Array
		$.each($scope.searchParam.reportingAgency, function(index,value) {
			$scope.searchParam.reportingAgency[index]=value.id;
		});
		
		//Type Of Use Array
		$.each($scope.searchParam.typeOfUse,function(index,value){
			$scope.searchParam.typeOfUse[index]=value.id;
		});
		
		// Manipulate Seating Position
		$.each($scope.searchParam.seatingPosition, function(index,value){
			$scope.searchParam.seatingPosition[index]=value.id;
		});
		
		// Manipulate Injuries
		$.each($scope.searchParam.injuries, function(index,value){
			$scope.searchParam.injuries[index]=value.id;
		});
		
		
		//Reset Check Box - Scanned
		$scope.isCheckedAllDirectReport=false;
		
		var defer=$q.defer();
		requestHandler.postRequest("Patient/searchPatients.json",$scope.searchParam).then(function(response){
				$scope.isLoading=false;
				if($scope.searchParam.isRunnerReport!=3){
					$scope.totalRecords=0;
					$scope.directRunnerReportSearchData={};
					$scope.totalRecords=response.data.patientGroupedSearchResult.totalNoOfRecord;
					$scope.lAdminPatientSearchData=response.data.patientGroupedSearchResult.patientSearchResults;
					
					$.each($scope.lAdminPatientSearchData, function(index,value) {
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
							    	value2.injuriesName="Fatal";
							        break;
							    case "2":
							    	value2.injuriesName="Suspected Serious Injury";
							        break;
							    case "3":
							    	value2.injuriesName="Suspected Minor Injury";
							        break;
							    case "4":
							    	value2.injuriesName="Possible Injury";
							        break;
							    case "5":
							    	value2.injuriesName="No Apparent Injury";
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
							    	value2.crashSeverityName="Serious Injury Suspected";
							        break;
							    case "3":
							    	value2.crashSeverityName="Minor Injury Suspected";
							        break;
							    case "4":
							    	value2.crashSeverityName="Injury Possible";
							        break;
							    case "5":
							    	value2.crashSeverityName="PDO";
							        break;
							    case "6":
							    	value2.crashSeverityName="N/A";
							        break;
							    default:
							    	value2.crashSeverityName="N/A";
							        break;
								};
								switch(value2.typeOfUse) {
							    case 1:
							    	value2.typeOfUseName="1 - Commercial";
							        break;
							    case 2:
							    	value2.typeOfUseName="2 - Government";
							        break;
							    case 3:
							    	value2.typeOfUseName="3 - In Emergency Response";
							        break;
							    case 0:
							    	value2.typeOfUseName="Unknown";
							        break;
							    default:
							    	value2.typeOfUseName="Unknown";
							        break;
								};
							});
						});
						defer.resolve(response);
					});
					$scope.patientSearchDataOrginal=angular.copy($scope.lAdminPatientSearchData);
					$scope.isCheckedIndividual();
				}else{
					$scope.totalRecords=0;
					$scope.lAdminPatientSearchData={};
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
		
	if($scope.patient.crashFromDate!="" && $scope.patient.crashToDate==""){
			$scope.crashToRequired=true;
		}
	else if($scope.patient.addedOnFromDate!="" && $scope.patient.addedOnToDate==""){
		$scope.addedToRequired=true;
	}
	else{
			$scope.crashToRequired=false;
			$scope.addedToRequired=false;
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
	searchService.setLAdminAge($scope.patient.age);
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
	searchService.setReportingAgency($scope.patient.reportingAgency);
	searchService.setReportingAgencyListType($scope.reportingAgencyListType);
	searchService.setTypeOfUse(angular.copy($scope.patient.typeOfUse));
	searchService.setSeatingPosition(angular.copy($scope.patient.seatingPosition));
	searchService.setInjuries(angular.copy($scope.patient.injuries));
	};
	
	$scope.secoundarySearchPatient=function(){
		searchService.setPhoneNumber($scope.patient.phoneNumber);
		searchService.setPatientName($scope.patient.patientName);
		searchService.setIsArchived($scope.patient.isArchived);
		if($scope.patient.isArchived==0){
			$scope.archivedToDateRequired=false;
			$scope.archivedFromDateRequired=false;
			$scope.patient.archivedFromDate="";
			$scope.patient.archivedToDate="";
			searchService.setArchivedFromDate($scope.patient.archivedFromDate);
			searchService.setArchivedToDate($scope.patient.archivedToDate);
		}
		searchService.setLAdminAge($scope.patient.age);
		searchService.setPatientStatus($scope.patient.patientStatus);
		searchService.setDirectReportStatus($scope.patient.directReportStatus);
		$scope.oldPageNumber=$scope.patient.pageNumber;
		$scope.patient.pageNumber=1;
		searchService.setPageNumber($scope.patient.pageNumber);
		searchService.setItemsPerPage($scope.patient.itemsPerPage);
		
		// Main Search Param
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
		
		if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			// Copy Mainsearchparam to Patient
			angular.copy($scope.mainSearchParam,$scope.patient);
			// County List
			$scope.countyListType=searchService.getCountyListType();
			searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
				$scope.mycounties=response;
				$scope.searchCountyMinError=false;
	    		$scope.loadingCounties=false;
			});
			
			// Get Reporting Agency List
			$scope.getReportingAgencyList();
			
			if($scope.mainSearchParam.reportingAgency.length>0){
				return $scope.searchItems($scope.mainSearchParam);
			}
			else{
				 $scope.disableSearch=true;
				 $scope.searchReportingAgencyMinError=true;
			 }
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
	

	//get Reporting Agency List
	$scope.getReportingAgencyList=function(){
		$scope.reportingAgencyListType=searchService.getReportingAgencyListType();
		if($scope.mainSearchParam.countyId.length>0){
			searchService.getReportingAgencyListByPreference($scope.mainSearchParam.countyId,$scope.reportingAgencyListType).then(function(response){
				 //Load Reporting Agency List		   
				 $scope.reportingAgencyList=response;
				 $scope.reportingAgencyLoaded=true;
			});
		}
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
	$scope.$watch("patient.isRunnerReport",function(){
		$scope.resetResultData();
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
				$scope.mycounties=response;
				$scope.loadingCounties=false;
			});
			
			// Get Reporting Agency List
			if($scope.patient.isRunnerReport==0)
				$scope.getReportingAgencyList();
			
			var promise=$scope.searchItems($scope.mainSearchParam);
			promise.then(function(reponse){
				// After Search
			});
			
		}
		
	});
	
	$scope.$watch("patient.pageNumber",function(){
		$scope.mainSearchParam.pageNumber=$scope.patient.pageNumber;
		searchService.setPageNumber($scope.patient.pageNumber);
		searchService.setItemsPerPage($scope.patient.itemsPerPage); 
		// Copy Mainsearchparam to Patient
		angular.copy($scope.mainSearchParam,$scope.patient);
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
			$.each($scope.lAdminPatientSearchData, function(index,value) {
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
						if(value.archivedDate==archiveDate){
							if(value1.localReportNumber==id.resultData.localReportNumber){
								var i=0;
								for(i;i<value1.numberOfPatients;i++){
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
			$.each($scope.lAdminPatientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					if(archiveDate!=''){
						if(value.archivedDate==archiveDate){
							var i=0;
							for(i;i<value1.numberOfPatients;i++){
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
	
	$scope.assignlawyerPopup=function(){
		$scope.single= false;
		$("#assignlawyerModal").modal('show');
	};
	
	$scope.assignSingleLawyerPopup=function(patientId,name,lawyerId){
		$scope.single=true;
		$scope.patientname=name;
		$scope.patientId=patientId;
		if(lawyerId!=null){
			lawyerId= lawyerId.toString();
			$scope.myForm.lawyerId=lawyerId;
		}
		else {$scope.myForm.lawyerId="";}
		$("#assignlawyerModal").modal('show');
	};
	
	$scope.assignSingleLawyer=function(patientId){
		var assignLawyerObj ={};
		var patientIdArray=[];
		patientIdArray.push(patientId);
		assignLawyerObj.lawyerId=$scope.myForm.lawyerId;
		assignLawyerObj.patientId=patientIdArray;
		$scope.assign(assignLawyerObj);
		
	};
	
	$scope.assign=function(assignLawyerObj){
		requestHandler.postRequest("/LAdmin/assignLawyer.json",assignLawyerObj).then(function(response){
			
			Flash.create('success', "You have Successfully Assigned Lawyer!");
			$scope.searchItems($scope.mainSearchParam);
			$scope.loadPreferenceCountyList();
			angular.copy($scope.mainSearchParam,$scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	
	$scope.assignLawyer=function(){
		var assignLawyerObj ={};
		assignLawyerObj.lawyerId=$scope.myForm.lawyerId;
		var patientIdArray=[];
		$.each($scope.lAdminPatientSearchData, function(index,value) {
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.selected==true){
						patientIdArray.push(value2.patientId);
					}
				});
			});
			
		});
		assignLawyerObj.patientId=patientIdArray;
		$scope.assign(assignLawyerObj);
	
	};
	
	
	$scope.releaseLawyer=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lAdminPatientSearchData, function(index,value) {
			$.each(value.searchResult, function(index1,value1) {
				$.each(value1.patientSearchLists,function(index2,value2){
					if(value2.selected==true){
						patientIdArray.push(value2.patientId);
					}
				});
			});
			
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/releaseLawyer.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Lawyer!");
			$scope.searchItems($scope.mainSearchParam);
			$scope.loadPreferenceCountyList();
			angular.copy($scope.mainSearchParam,$scope.patient);
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
			requestHandler.postRequest("/Lawyer/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}else{
			var assignLawyerObj ={};
			var patientIdArray=[];
			$.each($scope.lAdminPatientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							patientIdArray.push(value2.patientId);
						}
					});
				});
				
			});
			assignLawyerObj.patientId=patientIdArray;
			
			requestHandler.postRequest("/Lawyer/moveToArchive.json",assignLawyerObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Move Single File To Archive
	$scope.moveSingleFileToArchive=function(patientId){
		var assignLawyerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to move to archive?")){
			assignLawyerObj.patientId=patientIdArray;
			requestHandler.postRequest("/Lawyer/moveToArchive.json",assignLawyerObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
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
			requestHandler.postRequest("/Lawyer/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
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
			requestHandler.postRequest("/Lawyer/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}else{
			var assignLawyerObj ={};
			var patientIdArray=[];
			$.each($scope.lAdminPatientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							patientIdArray.push(value2.patientId);
						}
					});
				});
				
			});
			assignLawyerObj.patientId=patientIdArray;
			
			requestHandler.postRequest("/Lawyer/releaseFromArchive.json",assignLawyerObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Release Single File From Archive
	$scope.releaseSingleFileFromArchive=function(patientId){
		var assignLawyerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to release from archive?")){
			assignLawyerObj.patientId=patientIdArray;
			requestHandler.postRequest("/Lawyer/releaseFromArchive.json",assignLawyerObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
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
			requestHandler.postRequest("/Lawyer/directReportMoveOrReleaseArchive.json",moveArchiveObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.mainSearchParam);
				$scope.loadPreferenceCountyList();
				angular.copy($scope.mainSearchParam,$scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.lAdminPatientSearchData, function(index,value) {
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
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"}, {id: 5, label: "Undetermined"}, {id: 0, label: "Others"}];	
		$scope.defaultTypeofUse=[{id:1, label: "1 - Commercial"},{id:2, label: "2 - Goverment"},{id:3, label: "3 - Emergency Resp."},{id:0, label: "Unknown"}];
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		$scope.defaultDamageScale=[{id: 1, label: "None",legendClass:"badge-success",haveLegend:true},{id: 2, label: "Minor",legendClass:"badge-yellow",haveLegend:true},{id: 3, label: "Functional",legendClass:"badge-primary",haveLegend:true},{id: 4, label: "Disabling",legendClass:"badge-danger",haveLegend:true},{id: 9, label: "Unknown",legendClass:"badge-default",haveLegend:true},{id: 5, label: "N/A",haveLegend:false}];
		$scope.defaultSeatingPosition=[{id:1, label:"Drivers"},{id:2, label:"Passengers"},{id:99, label:"Unknown"},{id:0,label:"Not Provided"}];
		$scope.defaultInjuries=[{id:1, label:"Fatal"},{id:2, label:"Serious Injury"},{id:3, label:"Minor Injury"},{id:4, label:"Possible Injury"},{id:5, label:"No Apparent Injury"},{id:0, label:"Unknown"},{id:7,label: "Not Provided"}];
		$scope.reportingAgencyList=[];
		
		$scope.patient={};
		$scope.patient.seatingPosition=[];
		$scope.totalRecords=0;
		$scope.patient.countyId=[];
		$scope.patient.tier=[];
		$scope.patient.damageScale=[];
		$scope.patient.typeOfUse=[];
		$scope.patient.isOwner=0;
		$scope.patient.reportingAgency=[];
		$scope.patient.injuries=[];
		$scope.reportingAgencyLoaded=false;
		angular.copy(searchService.getCounty(),$scope.patient.countyId);
		angular.copy(searchService.getTier(),$scope.patient.tier);
		angular.copy(searchService.getDamageScale(),$scope.patient.damageScale);
		angular.copy(searchService.getTypeOfUse(),$scope.patient.typeOfUse);
		angular.copy(searchService.getSeatingPosition(),$scope.patient.seatingPosition);
		angular.copy(searchService.getInjuries(),$scope.patient.injuries);
		$scope.patient.crashFromDate=searchService.getCrashFromDate();
		$scope.patient.crashToDate=searchService.getCrashToDate();
		$scope.patient.localReportNumber=searchService.getLocalReportNumber();
		$scope.patient.patientName=searchService.getPatientName();
		$scope.patient.age=searchService.getLAdminAge();
		$scope.patient.callerId=searchService.getCallerId();
		$scope.patient.phoneNumber=searchService.getPhoneNumber();
		$scope.patient.lawyerId=searchService.getLawyerId();
		$scope.patient.numberOfDays=searchService.getNumberOfDays();
		$scope.patient.itemsPerPage=searchService.getItemsPerPage();
		$scope.patient.addedOnFromDate=searchService.getAddedOnFromDate();
		$scope.patient.addedOnToDate=searchService.getAddedOnToDate();
		$scope.patient.isArchived=searchService.getIsArchived();
		$scope.patient.archivedFromDate=searchService.getArchivedFromDate();
		$scope.patient.archivedToDate=searchService.getArchivedToDate();
		$scope.patient.patientStatus=searchService.getPatientStatus();
		$scope.countyListType=searchService.getCountyListType();
		$scope.patient.directReportStatus=searchService.getDirectReportStatus();
		angular.copy(searchService.getReportingAgency(),$scope.patient.reportingAgency);
		$scope.isSelectedAddedFromDate=true;
		$scope.reportingAgencyListType=searchService.getReportingAgencyListType();
		// Report Type
		$scope.patient.isRunnerReport=searchService.getIsRunnerReport();
		
		$scope.patient.pageNumber= searchService.getPageNumber();
		$scope.oldPageNumber= $scope.patient.pageNumber;
		if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			// To Avoid Main Search Parameter Override
			angular.copy($scope.patient,$scope.mainSearchParam);
			if($scope.patient.countyId!=''){
				searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
					$scope.mycounties=response;
					$scope.searchCountyMinError=false;
		    		$scope.loadingCounties=false;
		    		searchService.getReportingAgencyListByPreference($scope.patient.countyId,$scope.reportingAgencyListType).then(function(response){
						 //Load Reporting Agency List		   
						 $scope.reportingAgencyList=response;  
						 $scope.reportingAgencyLoaded=true;
						 $scope.searchItems($scope.patient);
					});
				});
				
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
						angular.copy(response,$scope.patient.countyId);
						$scope.mainSearchParam.countyId=angular.copy($scope.patient.countyId);
						// Get Reporting Agency List Type
						searchService.checkReportingAgencyListType().then(function(response){
							searchService.setReportingAgencyListType(response);
							$scope.reportingAgencyListType=response;
							searchService.getReportingAgencyListByPreference($scope.patient.countyId,response).then(function(response){
								 //Load Reporting Agency List		   
								 $scope.reportingAgencyList=response; 
								 
								 $scope.reportingAgencyLoaded=true;
								// pushing received reporting Agency List to $scope.patient.reportingAgency Array.
								 $scope.patient.reportingAgency=[];
								 if($scope.reportingAgencyList.length>0){
									 $.each($scope.reportingAgencyList, function(index,value) {
										 $scope.patient.reportingAgency.push({"id":value.code});
									});
									 searchService.setReportingAgency(angular.copy($scope.patient.reportingAgency));
									 $scope.mainSearchParam.reportingAgency=angular.copy($scope.patient.reportingAgency);
									 $scope.searchItems($scope.patient);
								 }else{
									 $scope.disableSearch=true;
									 $scope.searchReportingAgencyMinError=true;
								 }
								 	
								
							  });
						});
						
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
		 $scope.patient={};
		 $scope.patient.numberOfDays="0";
		 $scope.patient.lawyerId="0";
	     $scope.patientSearchForm.$setPristine();
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
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.lAdminPatientSearchData,$scope.patientSearchDataOrginal);
	};

	// Reset user prefernce Error Msg
	$scope.userPrefenceExportButton=false;
	$scope.userPrefenceError=false;
	$scope.maxRecordsExceed=false;
	$scope.resetUserPreferenceError=function(){
		$scope.userPrefenceExportButton=false;
		$scope.userPrefenceError=false;
		$scope.maxRecordsExceed=false;
	};
	
	//Export Excel
	$scope.exportToExcel=function(){
		$scope.isExportPatientSelected=true;
		$scope.resetUserPreferenceError();
		$scope.exportType=searchService.checkResultsSelected($scope.lAdminPatientSearchData);
		if($scope.exportType==2){
			if($scope.totalRecords>searchService.getMaxRecordsDownload()){
				$("#exportAlertModal").modal('show');
			}else{
				searchService.getExportPreferenceType().then(function(response){
					$scope.formatType=response;
					$("#exportOptionModal").modal('show');
				});
			}
		}
		else{
			$scope.checkExportSelectedPatients();
			searchService.getExportPreferenceType().then(function(response){
				$scope.formatType=response;
				$("#exportOptionModal").modal('show');
			});
		}
	};
	
	$scope.$watch('exportType',function(){
		$scope.maxRecordsExceed=false;
		if($scope.exportType==2){
			if($scope.totalRecords>searchService.getMaxRecordsDownload()){
				$scope.maxRecordsExceed=true;
			}
		}
	});
	
	$scope.exportExcelByType=function(){
		$scope.exportButtonText="Exporting...";
		$scope.exportButton=true;
		$scope.searchParam.formatType=$scope.formatType;
		$scope.searchParam.exportType=$scope.exportType;
		$scope.searchParam.exportPatientIds=$scope.exportPatientIds;
		$scope.searchParam.excludeState=$scope.isExcludeState;
		requestHandler.postExportRequest('Patient/exportExcel.xlsx',$scope.searchParam).success(function(responseData){
			 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
			 FileSaver.saveAs(blob,"Export_"+moment().format('YYYY-MM-DD')+".xlsx");
			 $scope.exportButtonText="Export to Excel";
			 $scope.exportButton=false;
		});
	};
	
	// get Selected Patients
	$scope.checkExportSelectedPatients=function(){
		$scope.exportPatientIds=[];
		$scope.isExportPatientSelected=false;
		if($scope.exportType==1){
			$.each($scope.lAdminPatientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							$scope.exportPatientIds.push(value2.patientId);
							$scope.isExportPatientSelected=true;
						}
						});
				});
			});
		}else{
			$scope.isExportPatientSelected=true;
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
 	 $state.go('dashboard.userPreferrence',{fid:2});
	};
	
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
		//console.log("county hits"+$scope.patient.countyId.length);
	    if(!$scope.loadingCounties){
		   if($scope.patient.countyId.length==0){
			   $scope.patient.reportingAgency=[];
			   $scope.disableSearch=true;
			   $scope.searchCountyMinError=true;
		   }else{
			   $scope.searchCountyMinError=false;
			   if(!$scope.searchTierMinError)
				   $scope.disableSearch=false;			   
		   }
	   }
	
	}, true );
	


	
	//watch Reporting Agency Filter
	$scope.$watch('patient.reportingAgency',function()
			{
		$scope.disableSearch=false;
		if($scope.reportingAgencyLoaded)
		{
			if($scope.patient.reportingAgency.length==0)
			{
				$scope.disableSearch=true;
				$scope.searchReportingAgencyMinError=true;
			}
			else
			{
				$scope.disableSearch=false;
				$scope.searchReportingAgencyMinError=false;
			}
		}
	},true);
	
	// County Drop down events
	$scope.countyEvents = {onInitDone: function(item) {},
			onItemDeselect: function(item) {},
			onItemSelect: function(item) {},
			onSelectionChanged:function(){
				$scope.searchReportingAgencyMinError=false;
				   //Some change happened in county selection lets update reporting agency list too
				 //  console.log($scope.patient.countyId);
				  if($scope.patient.countyId.length>0&&$scope.patient.isRunnerReport==0){
					   searchService.getReportingAgencyListByPreference($scope.patient.countyId,$scope.reportingAgencyListType).then(function(response){
						   $scope.patient.reportingAgency=[];
						 //Load Reporting Agency List		   
						 $scope.reportingAgencyList=response;
						 $scope.reportingAgencyLoaded=true;

							// pushing received reporting Agency List to $scope.patient.reportingAgency Array.
							$scope.patient.reportingAgency=[];
							 if($scope.reportingAgencyList.length>0){
								$.each($scope.reportingAgencyList, function(index,value) {
									$scope.patient.reportingAgency.push({"id":value.code});
								 });
							 }
						 
					   });
				   }else{
					   $scope.reportingAgencyList="";
				   }
			},
			onPreferenceChange: function(item) {
				$scope.countyListType=item;
				searchService.getPreferenceCoutyList(item).then(function(response){
					$scope.mycounties=response;
					$scope.patient.countyId=[];
					if($scope.mycounties.length>0){
						$.each(response, function(index,value) {
							$scope.patient.countyId.push({"id":value.countyId});
						});
						// Get Reporting Agency List
						 if($scope.patient.countyId.length>0&&$scope.patient.isRunnerReport==0){
							   searchService.getReportingAgencyListByPreference($scope.patient.countyId,$scope.reportingAgencyListType).then(function(response){
								   $scope.patient.reportingAgency=[];
								 //Load Reporting Agency List		   
								 $scope.reportingAgencyList=response;
								 $scope.reportingAgencyLoaded=true;

									// pushing received reporting Agency List to $scope.patient.reportingAgency Array.
									$scope.patient.reportingAgency=[];
									 if($scope.reportingAgencyList.length>0){
										$.each($scope.reportingAgencyList, function(index,value) {
											$scope.patient.reportingAgency.push({"id":value.code});
										 });
									 }
								 
							   });
						   }
					}else{
						$state.go('dashboard.userPreferrence',{fid:1});
					}
					
				});
	}};
	
	$scope.reportingAgencyEvents = {onInitDone: function(item) {},
			onItemDeselect: function(item) {},
			onItemSelect: function(item) {},
			onPreferenceChange: function(item) {
				$scope.reportingAgencyListType=item;
				searchService.getReportingAgencyListByPreference($scope.patient.countyId,item).then(function(response){
					 //Load Reporting Agency List		   
					 $scope.reportingAgencyList=response;  
					 $scope.reportingAgencyLoaded=true;
					// pushing received reporting Agency List to $scope.patient.reportingAgency Array.
					 if($scope.reportingAgencyList.length>0){
						 $scope.patient.reportingAgency=[];
						 $.each($scope.reportingAgencyList, function(index,value) {
							 $scope.patient.reportingAgency.push({"id":value.code});
						 });
					 }else{
						 $state.go('dashboard.userPreferrence',{fid:1});
					 }
				});
	 }};
	
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
	
	//Watch Type of Use
	$scope.$watch('patient.typeOfUse' , function() {		
		   if($scope.patient.typeOfUse.length==0){
			   $scope.disableSearch=true;
			   $scope.searchTypeOfUseMinError=true;
		   }else{
			   $scope.searchTypeOfUseMinError=false;
			   if(!$scope.searchCountyMinError)
				   $scope.disableSearch=false;	
		   }
		}, true );
	// Watch Seating Position
	$scope.$watch('patient.seatingPosition' , function() {		
		   if($scope.patient.seatingPosition.length==0){
			   $scope.disableSearch=true;
			   $scope.searchSeatingPositionMinError=true;
		   }else{
			   $scope.searchSeatingPositionMinError=false;
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
	
	// Watch Injuries Filter
	$scope.$watch('patient.injuries' , function() {		
		   if($scope.patient.injuries.length==0){
			   $scope.disableSearch=true;
			   $scope.injuriesMinError=true;
		   }else{
			   $scope.injuriesMinError=false;
			   if(!$scope.searchCountyMinError)
				   $scope.disableSearch=false;	
		   }
		}, true );
	
	// While Change to Open to Archive vice versa
	$scope.resetResultData=function(){
		$scope.lAdminPatientSearchData="";
		$scope.totalRecords=0;
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
					$scope.modeOfPrinting="1";
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
		$scope.sendingReportsList.modeOfPrinting=$scope.modeOfPrinting;
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
			$.each($scope.lAdminPatientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					$.each(value1.patientSearchLists,function(index2,value2){
						if(value2.selected==true){
							if($scope.modeOfPrinting!="1"){
								$scope.sendingReportsList.push({"localReportNumber":value2.localReportNumber,"clientName":value2.name,"fileName":value2.crashReportFileName,"printStatus":0});
							}
							else{
								if(localReportNumber!=value2.localReportNumber){
									$scope.sendingReportsList.push({"localReportNumber":value2.localReportNumber,"fileName":value2.crashReportFileName,"printStatus":0});
									localReportNumber=value1.localReportNumber;
								}
								else
									console.log("available");
							}	
							}
							
						});
				});
				
			});
		}
		
		// Add Print Activity
		searchService.addPrintActivityLog($scope.sendingReportsList.length);
		
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
		requestHandler.postRequest("/Lawyer/directReportChangeStatus.json",$scope.directReportForm).then(function(response){
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