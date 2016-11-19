var adminApp=angular.module('sbAdminApp', ['requestModule', 'angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver']);

adminApp.controller('CallerSearchPatientsController', ['$q','$rootScope','$scope','$window','requestHandler','searchService','Flash','$state','FileSaver', function($q,$rootScope,$scope,$window,requestHandler,searchService,Flash,$state,FileSaver) {
	
	console.log("root Scope",$rootScope.previousState);
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.callerPatientSearchData=$scope.callerPatientSearchDataOrginal=[];
	$scope.isSelectedAddedFromDate=true;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.ageFilterCurrentSelection=[];
	$scope.setScrollDown=false;
	
	 $scope.getMyCountyList=function(){
		 requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
	    	$scope.mycounties=response.data.countyList;
		 });
	 };
	    
		 $scope.getMyCountyList();
		 
		 $scope.isChecked=function(){
				if($scope.callerPatientSearchData.length>0){
					$.each($scope.callerPatientSearchData, function(index,value) {
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
				$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
				$scope.callerPatientSearchData=response.data.patientSearchResult.searchResult;
				$.each($scope.callerPatientSearchData, function(index,value) {
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
					    case 8:
					    	value1.patientStatusName="Call Back";
					    	break;
					    case 9:
					    	value1.patientStatusName="Unable To Reach";
					    	break;
					    default:
					        null;
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
					
					defer.resolve(response);
				});
				$scope.callerPatientSearchDataOrginal=angular.copy($scope.callerPatientSearchData);
				$scope.isCheckedIndividual();
				
			});
			return defer.promise;
	};
	 
	$scope.searchPatients = function(){
		if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="0" && $scope.patient.crashToDate==""){
				$scope.crashToRequired=true;
			}
		else if($scope.patient.addedOnFromDate!="" && $scope.patient.addedOnToDate==""){
			$scope.addedToRequired=true;
		}
		else{
				$scope.crashToRequired=false;
				$scope.addedToRequired=false;
				$scope.patient.patientName="";
				$scope.patient.phoneNumber= "";
				$scope.oldPageNumber=$scope.patient.pageNumber;
				$scope.patient.pageNumber= 1;//This will call search function thru patient.pageNumber object $watch function 
				if($scope.oldPageNumber==$scope.patient.pageNumber){
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
		searchService.setAge($scope.patient.age);
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
		searchService.setAge($scope.patient.age);
		searchService.setIsArchived($scope.patient.isArchived);
		searchService.setTier($scope.patient.tier);
		searchService.setPatientStatus($scope.patient.patientStatus);
		searchService.setPageNumber($scope.patient.pageNumber);
		searchService.setItemsPerPage($scope.patient.itemsPerPage);
		$scope.oldPageNumber=$scope.patient.pageNumber;
		$scope.patient.pageNumber= 1;//This will call search function thru patient.pageNumber object $watch function 
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
	
	
	 $scope.selectGroup=function(id){
			$.each($scope.callerPatientSearchData, function(index,value) {
			if(value.localReportNumber==id.resultData.localReportNumber){
				
				var i=0;
				for(i;i<value.numberOfPatients;i++){
					if(value.patientSearchLists[i]!=undefined)
					 value.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
				}
			}
			});
			
	};
	
	$scope.releaseMultiPatient=function(){
		var releaseCallerObj ={};
		var patientIdArray=[];
		$.each($scope.callerPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
		});
		releaseCallerObj.patientId=patientIdArray;
		$scope.releasePatientRequest(releaseCallerObj);
	};
	
	$scope.releaseSinglePatient=function(id){
		var releaseCallerObj ={};
		releaseCallerObj.patientId=[];
		releaseCallerObj.patientId.push(id);
		$scope.releasePatientRequest(releaseCallerObj);
	};
	
	$scope.releasePatientRequest=function(assignCallerObj){
		requestHandler.postRequest("/Caller/releaseCaller.json",assignCallerObj).then(function(response){
			Flash.create('success', "You successfully released patients that were checked.!");
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.releasePatientSingleConfirmPopup=function(id){
		$scope.isSingle=true;
		$scope.releasePatientId=id;
		$("#confirmModal").modal('show');
	};
	
	$scope.releasePatientMultiConfirmPopup=function(){
		$scope.isSingle=false;
		$("#confirmModal").modal('show');
	};
	
	$scope.moveArchive=function(){
		var assignCallerObj ={};
		var patientIdArray=[];
		$.each($scope.callerPatientSearchData, function(index,value) {
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
		$.each($scope.callerPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
		});
		assignCallerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Caller/releaseFromArchive.json",assignCallerObj).then(function(response){
			Flash.create('success', "You have Successfully released from Archive!");
			$scope.searchItems($scope.patient);
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.callerPatientSearchData, function(index,value) {
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
		$scope.patient.countyId=[{"id":1},{"id":2},{"id":3},{"id":4},{"id":5},{"id":6},{"id":7},{"id":8},{"id":9},{"id":10},{"id":11},{"id":12},{"id":13},{"id":14},{"id":15},{"id":16},{"id":17},{"id":18},{"id":19},{"id":20},{"id":21},{"id":22},{"id":23},{"id":24},{"id":25},{"id":26},{"id":27},{"id":28},{"id":29},{"id":30},{"id":31},{"id":32},{"id":33},{"id":34},{"id":35},{"id":36},{"id":37},{"id":38},{"id":39},{"id":40},{"id":41},{"id":42},{"id":43},{"id":44},{"id":45},{"id":46},{"id":47},{"id":48},{"id":49},{"id":50},{"id":51},{"id":52},{"id":53},{"id":54},{"id":55},{"id":56},{"id":57},{"id":58},{"id":59},{"id":60},{"id":61},{"id":62},{"id":63},{"id":64},{"id":65},{"id":66},{"id":67},{"id":68},{"id":69},{"id":70},{"id":71},{"id":72},{"id":73},{"id":74},{"id":75},{"id":76},{"id":77},{"id":78},{"id":79},{"id":80},{"id":81},{"id":82},{"id":83},{"id":84},{"id":85},{"id":86},{"id":87},{"id":88}];
		$scope.patient.tier=[{id:1},{id:2},{id:3},{id:4}];
		$scope.patient.patientStatus=0;
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.age=[{id:1},{id:2},{id:4}],
		$scope.patient.callerId="0";
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId="0";
		$scope.patient.numberOfDays="1";
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.lAdminPatientSearchData="";
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.patientStatus="7";
		$scope.patient.isArchived="0";
		$scope.isSelectedAddedFromDate=true;
		
		
		//Patient Search 
		$scope.patient.pageNumber= searchService.getPageNumber(); //This will call search function thru patient.pageNumber object $watch function 
		$scope.oldPageNumber= $scope.patient.pageNumber;
		
		if($scope.oldPageNumber==$scope.patient.pageNumber){
			$scope.searchItems($scope.patient);
		}
		
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
		
		// Get Clinics List For Call Logs
		$scope.getClinics();
		
	};
	
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.callerPatientSearchData,$scope.callerPatientSearchDataOrginal);
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
	
	//Call Logs
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
		 $.each($scope.callerPatientSearchData, function(index,value) {
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
		  $.each($scope.callerPatientSearchData, function(index,value) {
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
		
		// View Near By Clinics
		$scope.viewNearByClinic=function(patientId){
			$window.open('#/dashboard/viewlocations/'+patientId,'Crash Reports Online','width=1200,height=600');
		};
		
		$scope.resetSearchData = function(){
			 $scope.patient={};
			 //$scope.patientSearchForm.$setPristine();
			 $scope.crashToRequired=false;
			 $scope.addedToRequired=false;
		     $scope.callerPatientSearchData="";
		     $scope.totalRecords="";
		     	searchService.setCounty("0");
				searchService.setNumberOfDays("1");
				searchService.setCrashFromDate("");
				searchService.setCrashToDate("");
				searchService.setCallerId("0");
				searchService.setLawyerId("0");
				searchService.setPhoneNumber("");
				searchService.setPatientName("");
				searchService.setAge("3");
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
	
		$scope.init();	
		
		//Watch Age Filter
		$scope.$watch('patient.age' , function() {	
			if($scope.patient.age.length!=0){
				angular.copy($scope.patient.age,$scope.ageFilterCurrentSelection);
				$scope.secoundarySearchPatient();
			}else{
				angular.copy($scope.ageFilterCurrentSelection,$scope.patient.age);
			}
		    
		}, true );
		
		//Watch County Filter
		$scope.$watch('patient.countyId' , function() {		
		   if($scope.patient.countyId.length==0){
			   $scope.disableSearch=true;
			   $scope.searchCountyMinError=true;
		   }else{
			   $scope.searchCountyMinError=false;
			   if(!$scope.searchTierMinError)
				   $scope.disableSearch=false;			   
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
		
}]); 

 