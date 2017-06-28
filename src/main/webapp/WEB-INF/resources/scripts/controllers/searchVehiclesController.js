var adminApp=angular.module('sbAdminApp', ['requestModule','angularjs-dropdown-multiselect','searchModule','flash','ngFileSaver']);

adminApp.controller('searchVehiclesController', ['$q','$scope','requestHandler','searchService','$state','FileSaver', function($q,$scope,requestHandler,searchService,$state,FileSaver) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.vehicleSearchData=[];	
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.ageFilterCurrentSelection=[];
	$scope.setScrollDown=false;
	
	$scope.init=function(callFrom){
		
		//Initialize DropDown
		$scope.defaultTiers=[{id: 1, label: "Tier 1"}, {id: 2, label: "Tier 2"}, {id: 3, label: "Tier 3"}, {id: 4, label: "Tier 4"}, {id: 5, label: "Undetermined"},{id: 0, label: "Others"}];
		$scope.defaultAge=[{id:1,label:"Adults"},{id:1,label:"Minors"},{id:4,label:"Not Known"}];
		
		$scope.defaultDamageScale=[{id: 1, label: "None",legendClass:"badge-success",haveLegend:true},{id: 2, label: "Minor",legendClass:"badge-yellow",haveLegend:true},{id: 3, label: "Functional",legendClass:"badge-primary",haveLegend:true},{id: 4, label: "Disabling",legendClass:"badge-danger",haveLegend:true},{id: 9, label: "Unknown",legendClass:"badge-default",haveLegend:true},{id: 5, label: "N/A",haveLegend:false}];
		
		$scope.vehicle={};
		$scope.vehicle.countyId=[{"id":1},{"id":2},{"id":3},{"id":4},{"id":5},{"id":6},{"id":7},{"id":8},{"id":9},{"id":10},{"id":11},{"id":12},{"id":13},{"id":14},{"id":15},{"id":16},{"id":17},{"id":18},{"id":19},{"id":20},{"id":21},{"id":22},{"id":23},{"id":24},{"id":25},{"id":26},{"id":27},{"id":28},{"id":29},{"id":30},{"id":31},{"id":32},{"id":33},{"id":34},{"id":35},{"id":36},{"id":37},{"id":38},{"id":39},{"id":40},{"id":41},{"id":42},{"id":43},{"id":44},{"id":45},{"id":46},{"id":47},{"id":48},{"id":49},{"id":50},{"id":51},{"id":52},{"id":53},{"id":54},{"id":55},{"id":56},{"id":57},{"id":58},{"id":59},{"id":60},{"id":61},{"id":62},{"id":63},{"id":64},{"id":65},{"id":66},{"id":67},{"id":68},{"id":69},{"id":70},{"id":71},{"id":72},{"id":73},{"id":74},{"id":75},{"id":76},{"id":77},{"id":78},{"id":79},{"id":80},{"id":81},{"id":82},{"id":83},{"id":84},{"id":85},{"id":86},{"id":87},{"id":88}];
		$scope.vehicle.tier=[];
		$scope.vehicle.damageScale=[{id:1},{id:2},{id:3},{id:4},{id:9},{id:5}];
		$scope.vehicle.patientStatus=0;
		$scope.vehicle.crashFromDate="";
		$scope.vehicle.crashToDate="";
		$scope.vehicle.localReportNumber="";
		$scope.vehicle.reportingAgency=[];
		$scope.vehicle.patientName="";
		$scope.vehicle.age=[{id:1},{id:2},{id:4}],
		$scope.vehicle.callerId=0;
		$scope.vehicle.phoneNumber= "";
		$scope.vehicle.lawyerId="0";
		$scope.vehicle.numberOfDays="1";
		$scope.vehicle.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.vehicle.addedOnFromDate="";
		$scope.vehicle.addedOnToDate="";
		$scope.vehicle.patientStatus="7";
		$scope.vehicle.isArchived="0";
		$scope.vehicle.directReportStatus=0;
		$scope.vehicle.archivedFromDate="";
		$scope.vehicle.archivedToDate="";
		$scope.isSelectedAddedFromDate=true;
		$scope.vehicle.isRunnerReport=0;
		$scope.vehicle.isOwner=1;
		$scope.vehicle.vehicleMake="";
		$scope.vehicle.vehicleYear="";
		
		$scope.vehicle.pageNumber= 1;
		$scope.oldPageNumber= $scope.vehicle.pageNumber;
		
		// Calling From Reset
		if(callFrom==2){
			if($scope.oldPageNumber==$scope.vehicle.pageNumber){//This will call search function thru vehicle.pageNumber object $watch function 
				$scope.searchItems($scope.vehicle);
			}
		}
		
	};

	requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
		$scope.countylist=response.data.countyForms;
	});
	 
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
			$scope.vehicle.crashToDate="";
		}
	};
	
	$scope.checkAddedOnToDate=function(){
		if($scope.vehicle.addedOnFromDate!="")
			$scope.isSelectedAddedFromDate=false;
		else{
			$scope.vehicle.addedOnToDate="";
			$scope.isSelectedAddedFromDate=true;
		}
		//Reset to date if less than from date
		var fromDate=new Date($scope.vehicle.addedOnFromDate);
		var toDate=new Date($scope.vehicle.addedOnToDate);
		if(toDate<fromDate)
			$scope.vehicle.addedOnToDate="";
		//End Reset to date if less than from date
	};
	
	$scope.searchItems=function(searchObj){
		$scope.isLoading=true;
		//To avoid overwriting actual $scope.vehicle object.
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
		
		var defer=$q.defer();
		
		//Reset Check Box - Scanned
		$scope.isCheckedAllDirectReport=false;
		
		requestHandler.postRequest("/Patient/searchPatients.json",$scope.searchParam).then(function(response){
			$scope.isLoading=false;
			if($scope.searchParam.isRunnerReport!=3){
				$scope.totalRecords=response.data.patientGroupedSearchResult.totalNoOfRecord;
				$scope.vehicleSearchData=response.data.patientGroupedSearchResult.patientSearchResults;
			
				$.each($scope.vehicleSearchData, function(index,value) {
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
			}else{
				$scope.patientSearchData={};
				$scope.totalRecords=response.data.directReportGroupResult.totalNoOfRecords;
				$scope.directRunnerReportSearchData=response.data.directReportGroupResult.directReportGroupListByArchives;
			}
			
		});
		return defer.promise;
	};
	 
	$scope.searchPatients = function(){
		if($scope.vehicle.addedFromDate!="" && $scope.vehicle.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.vehicle.crashFromDate!="" && $scope.vehicle.numberOfDays=="0" && $scope.vehicle.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else if($scope.vehicle.addedOnFromDate!="" && $scope.vehicle.addedOnToDate==""){
			$scope.AddedOnToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
			$scope.vehicle.patientName="";
			$scope.vehicle.phoneNumber= "";
			$scope.oldPageNumber=$scope.vehicle.pageNumber;
			$scope.vehicle.pageNumber=1;
			if($scope.oldPageNumber==$scope.vehicle.pageNumber){//This will call search function thru vehicle.pageNumber object $watch function 
				$scope.searchItems($scope.vehicle);
			}
		}
	};
	
	$scope.secoundarySearchPatient=function(){
		$scope.oldPageNumber=$scope.vehicle.pageNumber;
		$scope.vehicle.pageNumber=1;
		if($scope.oldPageNumber==$scope.vehicle.pageNumber){//This will call search function thru vehicle.pageNumber object $watch function 
			return $scope.searchItems($scope.vehicle);
		}
		return null;
	};
	
	// Watch Report Type
	/*$scope.$watch('vehicle.isRunnerReport',function(){
		if($scope.vehicle.countyId!=''){
			$scope.vehicle.pageNumber=1;
			var promise=$scope.searchItems($scope.vehicle);
			if(promise!=null)
			promise.then(function(reponse){
				console.log(reponse);
			});	
		}
	});*/
	
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
	
	$scope.$watch("vehicle.pageNumber",function(){
		var promise=$scope.searchItems($scope.vehicle); 
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
		
		$.each($scope.vehicleSearchData, function(index,value) {
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
	     $scope.vehicleSearchForm.$setPristine();
	     $scope.vehicleSearchData="";
	     $scope.init(2);
	};
	
	$scope.init(1);
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
	$scope.$watch('vehicle.age' , function() {	
		if($scope.vehicle.age.length!=0){
			angular.copy($scope.vehicle.age,$scope.ageFilterCurrentSelection);
		}else{
			angular.copy($scope.ageFilterCurrentSelection,$scope.vehicle.age);
		}
	    
	}, true );
	
	// Age Drop down Events
	$scope.ageEvents = {onInitDone: function(item) {console.log("initi",item);},
			onItemDeselect: function(item) {console.log("deselected",item);if($scope.vehicle.age!=''){$scope.secoundarySearchPatient();}},
			onItemSelect: function(item) {console.log("selected",item);$scope.secoundarySearchPatient();}};
	
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
	
	//Watch County Filter
	$scope.$watch('vehicle.countyId' , function() {		
	   if($scope.vehicle.countyId.length==0){
		   $scope.disableSearch=true;
		   $scope.searchCountyMinError=true;
	   }else{
		   $scope.searchCountyMinError=false;
		   if(!$scope.searchTierMinError)
			   $scope.disableSearch=false;			   
	   }
	 /*  $scope.vehicle.reportingAgency=[];
	   //Some change happened in county selection lets update reporting agency list too
	   searchService.getReportingAgencyList($scope.vehicle.countyId).then(function(response){
		 //Load Reporting Agency List		   
		 $scope.reportingAgencyList=response;  
	   });*/
	   
	}, true );
	

}]); 

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
 