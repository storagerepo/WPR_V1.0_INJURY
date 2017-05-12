var adminApp=angular.module('sbAdminApp', ['requestModule', 'angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver','googleModule']);

adminApp.controller('LawyerSearchPatientsController', ['$scope','requestHandler','searchService','$state','Flash','FileSaver','$q','googleService','$timeout','$window','$location', function($scope,requestHandler,searchService,$state,Flash,FileSaver,$q,googleService,$timeout,$window,$location) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.lawyerPatientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isCheckedAllPatients=false;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.ageFilterCurrentSelection=[];
	$scope.setScrollDown=false;
	$scope.lockSearch=false;
	$scope.loadingCounties=true;
	
	$scope.archivedToDateRequired=false;
	$scope.archivedFromDateRequired=false;
	// Main Search Param
	$scope.mainSearchParam={};
	
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
			console.log($scope.lAdminPatientSearchData);
			if($scope.lawyerPatientSearchData.length>0){
				$.each($scope.lawyerPatientSearchData, function(index,value) {
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
		
		//Manipulate Damage Scale Array
		$.each($scope.searchParam.damageScale, function(index,value) {
			$scope.searchParam.damageScale[index]=value.id;
		});
		
		if(!$scope.lockSearch){
			$scope.lockSearch=true;
			var defer=$q.defer();
			requestHandler.postRequest("Patient/searchPatients.json",$scope.searchParam).then(function(response){
				if($scope.searchParam.isRunnerReport!=3){
					$scope.totalRecords=0;
					$scope.totalRecords=response.data.patientGroupedSearchResult.totalNoOfRecord;
					$scope.lawyerPatientSearchData=response.data.patientGroupedSearchResult.patientSearchResults;
					$.each($scope.lawyerPatientSearchData, function(index,value) {
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
					$scope.patientSearchDataOrginal=angular.copy($scope.lawyerPatientSearchData);
					$scope.isCheckedIndividual();
				}else{
					$scope.lawyerPatientSearchData={};
					$scope.totalRecords=response.data.crashReportList.totalNoOfRecords;
					$scope.directRunnerReportSearchData=response.data.crashReportList.crashReportForms;
				}
				
			});
			$scope.lockSearch=false;
			return defer.promise;
			
		}		
		
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
	searchService.setDamageScale(angular.copy($scope.patient.damageScale));
	
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
				$scope.mycounties=response;
				$scope.loadingCounties=false;
			});
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
			var promise = $scope.searchItems($scope.mainSearchParam);
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
	
	// Select LocalReportNumber Group
	 $scope.selectGroup=function(id,archiveDate){
			$.each($scope.lawyerPatientSearchData, function(index,value) {
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
	
	// Select Archive Group
	 $scope.selectArchiveGroup=function(id,archiveDate){
			$.each($scope.lawyerPatientSearchData, function(index,value) {
				$.each(value.searchResult, function(index1,value1) {
					if(archiveDate!=''){
						if(value.archivedDate==archiveDate){
							var i=0;
							for(i;i<value1.numberOfPatients;i++){
								if(value1.patientSearchLists[i]!=undefined){
									 value1.patientSearchLists[i].selected=id.isCheckedAllGroupArchivePatients;
								}
								  
							}
						 }
					}
				});
			});
			
	};
	
	$scope.releaseLawyer=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
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
			Flash.create('success', "You successfully released clients that were checked.");
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	// Move Multiple File To Archive
	$scope.moveArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
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
	
	// Release Multiple From Archive
	$scope.releaseArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
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
	
	// Release Single From Archive
	$scope.releaseSingleFileFromArchive=function(patientId){
		var assignLawyerObj ={};
		var patientIdArray=[patientId];
		if(confirm("Are you sure want to release from archive?")){
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
		
		$.each($scope.lawyerPatientSearchData, function(index,value) {
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
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"},{id: 5, label: "Undetermined"}];	
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		$scope.defaultDamageScale=[{id: 1, label: "None",legendClass:"badge-success",haveLegend:true},{id: 2, label: "Minor",legendClass:"badge-yellow",haveLegend:true},{id: 3, label: "Functional",legendClass:"badge-primary",haveLegend:true},{id: 4, label: "Disabling",legendClass:"badge-danger",haveLegend:true},{id: 9, label: "Unknown",legendClass:"badge-default",haveLegend:true},{id: 5, label: "N/A",haveLegend:false}];
		
		$scope.patient={};
		$scope.totalRecords=0;
		$scope.lAdminPatientSearchData="";
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
		
		// Report Type
		$scope.patient.isRunnerReport=searchService.getIsRunnerReport();
		
		//Patient Search 
		$scope.patient.pageNumber= searchService.getPageNumber(); //This will call search function thru patient.pageNumber object $watch function 
		$scope.oldPageNumber= $scope.patient.pageNumber;
		if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			// To Avoid Main Search Parameter Override
			angular.copy($scope.patient,$scope.mainSearchParam);
			if($scope.patient.countyId!=''){
				searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
					$scope.mycounties=response;
					$scope.loadingCounties=false;
				});
				$scope.searchItems($scope.patient);
			}else{
				searchService.checkCoutyListType().then(function(response){
					searchService.setCountyListType(response);
					$scope.countyListType=response;
					searchService.getPreferenceCoutyList($scope.countyListType).then(function(response){
						$scope.mycounties=response;
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
		$scope.disableCustom=true;
		$scope.isSelectedAddedFromDate=true;
		if(searchService.getCrashToDate()!=""){
			$scope.disableCustom=false;
		}
		if(searchService.getAddedOnFromDate()!=""){
			$scope.isSelectedAddedFromDate=false;
		}
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
		return angular.equals($scope.lawyerPatientSearchData,$scope.patientSearchDataOrginal);
	};
	//Export Excel
	$scope.exportToExcel=function(){
		$scope.exportButtonText="Exporting...";
		$scope.exportButton=true;
		requestHandler.postExportRequest('Patient/exportExcel.xlsx',$scope.searchParam).success(function(responseData){
			 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
			 FileSaver.saveAs(blob,"Export_"+moment().format('YYYY-MM-DD')+".xlsx");
			 $scope.exportButtonText="Export to Excel";
			 $scope.exportButton=false;
		});
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
						alert("Not done with Preference, Please Contact Admin to set up.");
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
	
	// While Change to Open to Archive vice versa
	$scope.resetResultData=function(){
		$scope.lawyerPatientSearchData="";
		$scope.totalRecords="";
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
		var localReportNumber="";
		$.each($scope.lawyerPatientSearchData, function(index,value) {
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
		
		var randomnumber = Math.floor((Math.random()*100)+1);
		window.$windowScope = $scope;
		var $popup=$window.open('#/dashboard/printreports/','_blank','Crash Reports Online',randomnumber,'width=1200,height=600,scrollbars=1');
		
		// Send Only Newly Selected Reports
		/*$scope.sendingReportsList=[];
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
}]); 

 