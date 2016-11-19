var adminApp=angular.module('sbAdminApp', ['requestModule','searchModule','flash','ngFileSaver']);

adminApp.controller('searchPatientsController', ['$q','$scope','requestHandler','searchService','$state','FileSaver', function($q,$scope,requestHandler,searchService,$state,FileSaver) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.patientSearchData=[];	
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.setScrollDown=false;
	
	$scope.init=function(){
		$scope.patient={};
		$scope.patient.countyId="0";
		$scope.patient.tier=0;
		$scope.patient.patientStatus=0;
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.age="3";
		$scope.patient.callerId=0;
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId=0;
		$scope.patient.numberOfDays="1";
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.isSelectedAddedFromDate=true;
		
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
		var defer=$q.defer();
		requestHandler.postRequest("/Patient/searchPatients.json",searchObj).then(function(response){
			$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
			$scope.patientSearchData=response.data.patientSearchResult.searchResult;
			$.each($scope.patientSearchData, function(index,value) {
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
			$scope.patient.age="3";
			$scope.patient.phoneNumber= "";
			if($scope.patient.countyId=="")
			$scope.patient.countyId=0;
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
	
	
	$scope.resetSearchData = function(){
	     $scope.patientSearchForm.$setPristine();
	     $scope.patientSearchData="";
	     $scope.init();
	};
	
	$scope.init();
	//Export Excel
	$scope.exportToExcel=function(){
		$scope.exportButtonText="Exporting...";
		$scope.exportButton=true;
		$scope.patient.formatType=1;
		requestHandler.postExportRequest('Patient/exportExcel.xlsx',$scope.patient).success(function(responseData){
			 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
			 FileSaver.saveAs(blob,"Export_"+moment().format('YYYY-MM-DD')+".xlsx");
			 $scope.exportButtonText="Export to Excel";
			 $scope.exportButton=false;
		});
	};
}]); 

 