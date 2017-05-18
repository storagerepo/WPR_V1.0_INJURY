var adminApp=angular.module('sbAdminApp', ['requestModule','flash','angularjs-dropdown-multiselect']);

adminApp.controller('searchCrashReportController', ['$scope','requestHandler','$q', function($scope,requestHandler,$q) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.setScrollDown=false;
	
	$scope.init=function(callFrom){
		$scope.crashreport={};
		$scope.crashreport.countyId=[{"id":1},{"id":2},{"id":3},{"id":4},{"id":5},{"id":6},{"id":7},{"id":8},{"id":9},{"id":10},{"id":11},{"id":12},{"id":13},{"id":14},{"id":15},{"id":16},{"id":17},{"id":18},{"id":19},{"id":20},{"id":21},{"id":22},{"id":23},{"id":24},{"id":25},{"id":26},{"id":27},{"id":28},{"id":29},{"id":30},{"id":31},{"id":32},{"id":33},{"id":34},{"id":35},{"id":36},{"id":37},{"id":38},{"id":39},{"id":40},{"id":41},{"id":42},{"id":43},{"id":44},{"id":45},{"id":46},{"id":47},{"id":48},{"id":49},{"id":50},{"id":51},{"id":52},{"id":53},{"id":54},{"id":55},{"id":56},{"id":57},{"id":58},{"id":59},{"id":60},{"id":61},{"id":62},{"id":63},{"id":64},{"id":65},{"id":66},{"id":67},{"id":68},{"id":69},{"id":70},{"id":71},{"id":72},{"id":73},{"id":74},{"id":75},{"id":76},{"id":77},{"id":78},{"id":79},{"id":80},{"id":81},{"id":82},{"id":83},{"id":84},{"id":85},{"id":86},{"id":87},{"id":88}];;
		$scope.crashreport.localReportNumber="";
		$scope.crashreport.crashId="";
		$scope.crashreport.crashFromDate="";
		$scope.crashreport.numberOfDays="1";
		$scope.crashreport.crashToDate="";
		$scope.crashreport.addedFromDate="";
		$scope.crashreport.addedToDate="";
		$scope.crashreport.recordsPerPage="25";
		$scope.crashreport.isRunnerReport=0;
		$scope.crashreport.isArchived=0;
		$scope.crashreport.archivedFromDate="";
		$scope.crashreport.archivedToDate="";
		$scope.totalRecords=0;
		
		$scope.crashreport.pageNumber= 1;
		$scope.oldPageNumber= $scope.crashreport.pageNumber;
		
		// Call From Reset - 2
		if(callFrom==2){
			if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
				$scope.searchItems($scope.crashreport);
			}
		}
	};
	
	
	
	
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countylist=response.data.countyForms;
	});
	 
	$scope.checkCustomDate=function(custom){
	
		if(custom=='0' && $scope.crashreport.crashFromDate!=''){
			$scope.disableCustom=false;
		}
		else{
			$scope.disableCustom=true;
		}
	};
	 
	$scope.searchCrashReport = function(){
		
		if($scope.crashreport.addedFromDate!="" && $scope.crashreport.addedToDate==""){
			$scope.addedToRequired=true;
		}
		else if($scope.crashreport.crashFromDate!="" && $scope.crashreport.numberOfDays=="0" && $scope.crashreport.crashToDate==""){
			$scope.crashToRequired=true;
		}
		else{
			$scope.addedToRequired=false;
			$scope.crashToRequired=false;
			$scope.oldPageNumber=$scope.crashreport.pageNumber;
			$scope.crashreport.pageNumber=1;
			if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
				$scope.searchItems($scope.crashreport);
			}
		
		}
		
		
	};
	$scope.searchItems=function(searchObj){
		$scope.isLoading=true;
		
		//To avoid overwriting actual $scope.patient object.
		$scope.searchParam={};
		angular.copy(searchObj,$scope.searchParam);
		
		var defer=$q.defer();
		
		//Manipulate County Array
		$.each($scope.searchParam.countyId, function(index,value) {
			$scope.searchParam.countyId[index]=value.id;
		});
		
		requestHandler.postRequest("Admin/searchCrashReport.json",$scope.searchParam).then(function(response){
			$scope.isLoading=false;
			$scope.totalRecords=response.data.searchResults.totalNoOfRecords;
			$scope.crashSearchData=response.data.searchResults.directReportGroupListByArchives;
			defer.resolve(response);
		});
		return defer.promise;
	};
	
	$scope.secoundarySearchCrashReport=function(){
		$scope.oldPageNumber=$scope.crashreport.pageNumber;
		$scope.crashreport.pageNumber=1;
		if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			return $scope.searchItems($scope.crashreport);
		}
		return null;
	};
	
	$scope.itemsPerFilter=function(){
		$scope.setScrollDown=true;
		var promise=$scope.secoundarySearchCrashReport();
		if(promise!=null)
		promise.then(function(reponse){
			console.log("scroll down simple");
			console.log(reponse);
			setTimeout(function(){
       			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
       		 },100);
		});
	};
	
	/*$scope.$watch("crashreport.isRunnerReport",function(){
		$scope.oldPageNumber=$scope.crashreport.pageNumber;
		$scope.crashreport.pageNumber=1;
		if($scope.oldPageNumber==$scope.crashreport.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			alert("3 Isrunner");
			return $scope.searchItems($scope.crashreport);
		}
		
	});*/
	
	$scope.$watch("crashreport.pageNumber",function(){
		var promise=$scope.searchItems($scope.crashreport); 
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
	
	//Watch County Filter
	$scope.$watch('crashreport.countyId' , function() {		
	   if($scope.crashreport.countyId.length==0){
		   $scope.disableSearch=true;
		   $scope.searchCountyMinError=true;
	   }else{
		   $scope.searchCountyMinError=false;
		   if(!$scope.searchTierMinError)
			   $scope.disableSearch=false;			   
	   }
	}, true );
	
	$scope.resetSearchData = function(){
		 $scope.crashSearchForm.$setPristine();
	     $scope.crashSearchData="";
	     $scope.init(2);
	     
	};
	
	$scope.init(1);
	
}]); 