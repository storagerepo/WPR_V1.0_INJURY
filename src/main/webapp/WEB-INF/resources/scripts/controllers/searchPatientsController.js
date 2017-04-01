var adminApp=angular.module('sbAdminApp', ['requestModule','angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver']);

adminApp.controller('searchPatientsController', ['$q','$scope','requestHandler','searchService','$state','FileSaver', function($q,$scope,requestHandler,searchService,$state,FileSaver) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=[];	
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.ageFilterCurrentSelection=[];
	$scope.setScrollDown=false;
	
	$scope.init=function(){
		
		//Initialize DropDown
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"}, {id: 5, label: "Undetermined"}];	
		$scope.defaultAge=[{id:1,label:"Adults"},{id:2,label:"Minors"},{id:4,label:"Not Known"}];
		
		$scope.patient={};
		$scope.patient.countyId=[{"id":1},{"id":2},{"id":3},{"id":4},{"id":5},{"id":6},{"id":7},{"id":8},{"id":9},{"id":10},{"id":11},{"id":12},{"id":13},{"id":14},{"id":15},{"id":16},{"id":17},{"id":18},{"id":19},{"id":20},{"id":21},{"id":22},{"id":23},{"id":24},{"id":25},{"id":26},{"id":27},{"id":28},{"id":29},{"id":30},{"id":31},{"id":32},{"id":33},{"id":34},{"id":35},{"id":36},{"id":37},{"id":38},{"id":39},{"id":40},{"id":41},{"id":42},{"id":43},{"id":44},{"id":45},{"id":46},{"id":47},{"id":48},{"id":49},{"id":50},{"id":51},{"id":52},{"id":53},{"id":54},{"id":55},{"id":56},{"id":57},{"id":58},{"id":59},{"id":60},{"id":61},{"id":62},{"id":63},{"id":64},{"id":65},{"id":66},{"id":67},{"id":68},{"id":69},{"id":70},{"id":71},{"id":72},{"id":73},{"id":74},{"id":75},{"id":76},{"id":77},{"id":78},{"id":79},{"id":80},{"id":81},{"id":82},{"id":83},{"id":84},{"id":85},{"id":86},{"id":87},{"id":88}];
		$scope.patient.tier=[{id:1},{id:2},{id:3},{id:4},{id:5}];
		$scope.patient.patientStatus=0;
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.age=[{id:1},{id:2},{id:4}],
		$scope.patient.callerId=0;
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId="0";
		$scope.patient.numberOfDays="1";
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.patientStatus="7";
		$scope.patient.isArchived="0";
		$scope.patient.archivedFromDate="";
		$scope.patient.archivedToDate="";
		$scope.isSelectedAddedFromDate=true;
		$scope.patient.isRunnerReport=0;
		
		$scope.patient.pageNumber= 1;
		$scope.oldPageNumber= $scope.patient.pageNumber;
		
		if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			$scope.searchItems($scope.patient);
		}
		
		$scope.searchItems($scope.patient);
	};

	requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
		$scope.countylist=response.data.countyForms;
	});
	 
	$scope.checkCustomDate=function(custom){
		//Reset to date if less than from date
		var fromDate=new Date($scope.patient.crashFromDate);
		var toDate=new Date($scope.patient.crashToDate);
		if(toDate<fromDate)
			$scope.patient.crashToDate="";
		//End Reset to date if less than from date
		if(custom=='0'&&$scope.patient.crashFromDate!=''){
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
		requestHandler.postRequest("/Patient/searchPatients.json",$scope.searchParam).then(function(response){
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
				
				defer.resolve(response);
			});
		});
		});
		return defer.promise;
	};
	 
	$scope.searchPatients = function(){
		if($scope.patient.addedFromDate!="" && $scope.patient.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="0" && $scope.patient.crashToDate==""){
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
	};
	
	$scope.secoundarySearchPatient=function(){
		$scope.oldPageNumber=$scope.patient.pageNumber;
		$scope.patient.pageNumber=1;
		if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			return $scope.searchItems($scope.patient);
		}
		return null;
	};
	
	// Watch Report Type
	$scope.$watch('patient.isRunnerReport',function(){
		if($scope.patient.countyId!=''){
			$scope.patient.pageNumber=1;
			var promise=$scope.searchItems($scope.patient);
			if(promise!=null)
			promise.then(function(reponse){
				console.log(reponse);
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
		var promise=$scope.searchItems($scope.patient); 
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
		
		/*requestHandler.getRequest("/Patient/getPatient.json?patientId="+patientId,"").then(function(response){
			$scope.patientDetails=response.data.patientForm;
		
			});*/

	};
	
	
	$scope.resetSearchData = function(){
	     $scope.patientSearchForm.$setPristine();
	     $scope.patientSearchData="";
	     $scope.init();
	};
	
	$scope.init();
	//Export Excel
	$scope.exportToExcel=function(){
		if($scope.totalRecords>searchService.getMaxRecordsDownload()){
			$("#exportAlertModal").modal('show');
		}else{
			$scope.exportButtonText="Exporting...";
			$scope.exportButton=true;
			$scope.searchParam.formatType=1;
			requestHandler.postExportRequest('Patient/exportExcel.xlsx',$scope.searchParam).success(function(responseData){
				 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
				 FileSaver.saveAs(blob,"Export_"+moment().format('YYYY-MM-DD')+".xlsx");
				 $scope.exportButtonText="Export to Excel";
				 $scope.exportButton=false;
			});
		}
		
	};
	
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

 