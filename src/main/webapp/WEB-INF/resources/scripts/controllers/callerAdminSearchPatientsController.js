var adminApp=angular.module('sbAdminApp', ['requestModule','angularjs-dropdown-multiselect','searchModule','flash','ngAnimate','ngFileSaver']);

adminApp.controller('searchPatientsController', ['$q','$rootScope','$scope','$http','$window','requestHandler','searchService','$state','$location','Flash','FileSaver', function($q,$rootScope,$scope,$http,$window,requestHandler,searchService,$state,$location,Flash,FileSaver) {
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
	// Main Search Param
	$scope.mainSearchParam={};
	$scope.init=function(){
		
		//Initialize DropDown
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"}];	
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		
		$scope.patient={};
		$scope.patient.countyId=[];
		$scope.patient.tier=[];
		angular.copy(searchService.getCounty(),$scope.patient.countyId);
		angular.copy(searchService.getTier(),$scope.patient.tier);
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
			$scope.searchItems($scope.patient);
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
			$scope.searchItems($scope.patient);
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
						if(value.patientSearchLists[i]!=undefined)
							 value.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
					}
				}
				});
				
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
		requestHandler.postRequest("/Patient/searchPatients.json",$scope.searchParam).then(function(response){
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
				    	value1.patientStatusName="To be Re-Assigned";
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
		/*	$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";*/
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
	};
	
	
		$scope.secoundarySearchPatient=function(){
			searchService.setPhoneNumber($scope.patient.phoneNumber);
			searchService.setPatientName($scope.patient.patientName);
			searchService.setIsArchived($scope.patient.isArchived);
			searchService.setAge($scope.patient.age);
			searchService.setPatientStatus($scope.patient.patientStatus);
			$scope.oldPageNumber=$scope.patient.pageNumber;
			$scope.patient.pageNumber= 1;//This will call search function thru patient.pageNumber object $watch function 
			searchService.setPageNumber($scope.patient.pageNumber);
			searchService.setItemsPerPage($scope.patient.itemsPerPage);
			
			// Use mainsearchparam to Search 
			$scope.mainSearchParam.pageNumber=$scope.patient.pageNumber;
			$scope.mainSearchParam.phoneNumber=$scope.patient.phoneNumber;
			$scope.mainSearchParam.patientName=$scope.patient.patientName;
			$scope.mainSearchParam.isArchived=$scope.patient.isArchived;
			$scope.mainSearchParam.age=$scope.patient.age;
			$scope.mainSearchParam.patientStatus=$scope.patient.patientStatus;
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
	
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.patientSearchData,$scope.patientSearchDataOrginal);
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
	$scope.enableCallLogsButton=function(localReportNumber){
		var isChecked=false;
		var changedLocalReportNumber=localReportNumber.replace( /(\.|\(|\)|\ )/g, "\\$1" );
		 $.each($scope.patientSearchData, function(index,value) {
				if(value.localReportNumber==localReportNumber){
					$.each(value.patientSearchLists,function(index1,value1){
						if(value1.selected==true){
							isChecked=true;
						}
					});
				}
			});
		 if(isChecked){
			 $("#addCallLog"+changedLocalReportNumber+"").removeAttr("disabled",false);
		 }else{
			$("#addCallLog"+changedLocalReportNumber+"").attr("disabled",true);
		 }
	};
	
	// Call Logs Add Modal
	  $scope.addModel=function(id)
		{
		  var patientIdArray=[];
		  $.each($scope.patientSearchData, function(index,value) {
				if(value.localReportNumber==id.resultData.localReportNumber){
					$.each(value.patientSearchLists,function(index1,value1){
						if(value1.selected==true){
							patientIdArray.push(value1.patientId);
						}
						});
				}
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
}]); 

 