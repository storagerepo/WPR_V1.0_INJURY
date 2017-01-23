var adminApp=angular.module('sbAdminApp', ['requestModule', 'angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver']);

adminApp.controller('LAdminSearchPatientsController', ['$rootScope','$scope','requestHandler','searchService','$state','$location','Flash','FileSaver','$q', function($rootScope,$scope,requestHandler,searchService,$state,$location,Flash,FileSaver,$q) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.lAdminPatientSearchData=$scope.patientSearchDataOrginal=[];
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
	 
	 
		 $scope.checkCustomDate=function(custom){
		//Reset to date if less than from date
			var fromDate=new Date($scope.patient.crashFromDate);
			var toDate=new Date($scope.patient.crashToDate);
			if(toDate<fromDate)
				$scope.patient.crashToDate="";
			//End Reset to date if less than from date
		 if(custom=='0' && $scope.patient.crashFromDate!=''){
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
		
		var defer=$q.defer();
			requestHandler.postRequest("Patient/searchPatients.json",$scope.searchParam).then(function(response){
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
						  
						    default:
						        break;
							};
							
						});
					});
					defer.resolve(response);
				});
				$scope.patientSearchDataOrginal=angular.copy($scope.lAdminPatientSearchData);
				$scope.isCheckedIndividual();
			});
			return defer.promise;
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
			return $scope.searchItems($scope.mainSearchParam);
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
			$scope.searchItems($scope.patient);
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
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	// Move Multiple File to Archive
	$scope.moveArchive=function(){
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
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	// Move Single File To Archive
	$scope.moveSingleFileToArchive=function(patientId){
		var assignLawyerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to move to archive?")){
			assignLawyerObj.patientId=patientIdArray;
			requestHandler.postRequest("/Lawyer/moveToArchive.json",assignLawyerObj).then(function(response){
				Flash.create('success', "You have Successfully Moved to Archive!");
				$scope.searchItems($scope.patient);
				$(function(){
					$("html,body").scrollTop(0);
				});
			});
		}
	};
	
	// Release Multiple File From Archive
	$scope.releaseArchive=function(){
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
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	// Release Single File From Archive
	$scope.releaseSingleFileFromArchive=function(patientId){
		var assignLawyerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to relaese from archive?")){
			assignLawyerObj.patientId=patientIdArray;
			requestHandler.postRequest("/Lawyer/releaseFromArchive.json",assignLawyerObj).then(function(response){
				Flash.create('success', "You have Successfully released from Archive!");
				$scope.searchItems($scope.patient);
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
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"}];	
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		
		
		$scope.patient={};
		$scope.totalRecords=0;
		$scope.patient.countyId=[];
		$scope.patient.tier=[];
		angular.copy(searchService.getCounty(),$scope.patient.countyId);
		angular.copy(searchService.getTier(),$scope.patient.tier);
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
		$scope.isSelectedAddedFromDate=true;

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
				});
				$scope.searchItems($scope.patient);
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
		
	};
	
	$scope.init();
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
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
	
	// County Drop down events
	$scope.countyEvents = {onInitDone: function(item) {},
			onItemDeselect: function(item) {},
			onItemSelect: function(item) {},
			onPreferenceChange: function(item) {
				$scope.countyListType=item;
				searchService.getPreferenceCoutyList(item).then(function(response){
					$scope.mycounties=response;
					$scope.patient.countyId=[];
					if($scope.mycounties.length>0){
						$.each(response, function(index,value) {
							$scope.patient.countyId.push({"id":value.countyId});
						});
					}else{
						$location.path("dashboard/UserPreferrence/1");
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
	
	// While Change to Open to Archive vice versa
	$scope.resetResultData=function(){
		$scope.lAdminPatientSearchData="";
		$scope.totalRecords="";
	};
}]); 

 