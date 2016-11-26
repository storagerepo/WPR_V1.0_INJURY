var adminApp=angular.module('sbAdminApp', ['requestModule', 'angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver']);

adminApp.controller('LawyerSearchPatientsController', ['$scope','requestHandler','searchService','$state','Flash','FileSaver','$q', function($scope,requestHandler,searchService,$state,Flash,FileSaver,$q) {
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
		
		if(!$scope.lockSearch){
			$scope.lockSearch=true;
			var defer=$q.defer();
			requestHandler.postRequest("Patient/searchPatients.json",$scope.searchParam).then(function(response){
				$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
				$scope.lawyerPatientSearchData=response.data.patientSearchResult.searchResult;
				$.each($scope.lawyerPatientSearchData, function(index,value) {
					$.each(value.patientSearchLists,function(index1,value1){
					
					switch(value1.injuries) {
				    case "1":
				    	value1.injuriesName="No Injury/None Reported";
				        break;
				    case "2":
				    	value1.injuriesName="Possible";
				        break;
				    case "3":
				    	value1.injuriesName="Non-Incapacitating";
				        break;
				    case "4":
				    	value1.injuriesName="Incapacitating";
				        break;
				    default:
				        break;
					};
					switch(value1.crashSeverity) {
				    case "1":
				    	value1.crashSeverityName="Fatal";
				        break;
				    case "2":
				    	value1.crashSeverityName="Injury";
				        break;
				    case "3":
				    	value1.crashSeverityName="PDO";
				        break;
				  
				    default:
				        break;
					};
					
					
				});
					defer.resolve(response);
				});
				$scope.patientSearchDataOrginal=angular.copy($scope.lawyerPatientSearchData);
				$scope.isCheckedIndividual();
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
	
	
	$scope.$watch("patient.pageNumber",function(){
		$scope.mainSearchParam.pageNumber=$scope.patient.pageNumber;
		searchService.setPageNumber($scope.patient.pageNumber);
		searchService.setItemsPerPage($scope.patient.itemsPerPage); 
		// Copy Mainsearchparam to Patient
		angular.copy($scope.mainSearchParam,$scope.patient);
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
		
	});
	
	
	 $scope.selectGroup=function(id){
			$.each($scope.lawyerPatientSearchData, function(index,value) {
			if(value.localReportNumber==id.resultData.localReportNumber){
				
				var i=0;
				for(i;i<value.numberOfPatients;i++){
					value.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
				}
			}
			});
			
	};
	
	$scope.releaseLawyer=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
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
	
	$scope.moveArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
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
	
	$scope.releaseArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
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
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.lawyerPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
				if(value1.patientId ==patientId){
					$scope.patientDetails=value1;
				}
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
		$scope.lAdminPatientSearchData="";
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
		$scope.patient.patientStatus=searchService.getPatientStatus();
		$scope.countyListType=searchService.getCountyListType();
		$scope.isSelectedAddedFromDate=true;
		
		
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
	
}]); 

 