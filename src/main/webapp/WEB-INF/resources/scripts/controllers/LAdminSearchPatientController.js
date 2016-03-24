var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('LAdminSearchPatientsController', ['$scope','requestHandler', function($scope,requestHandler,$state) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";

	
	$scope.init=function(){

		$scope.patient={};
		$scope.patient.countyId="";
		$scope.patient.tier=0;
		$scope.patient.patientStatus=0;
		$scope.patient.crashFromDate="";
		$scope.patient.crashToDate="";
		$scope.patient.localReportNumber="";
		$scope.patient.patientName="";
		$scope.patient.callerId=0;
		$scope.patient.phoneNumber= "";
		$scope.patient.lawyerId="0";
		$scope.patient.numberOfDays="1";
		$scope.patient.pageNumber= 1;
		$scope.patient.itemsPerPage="10";
		$scope.totalRecords=0;
		$scope.lAdminPatientSearchData="";
	//Init functions
		
	};
	
	$scope.init();
	
	
	 $scope.getLawyerList=function(){
	    	requestHandler.getRequest("LAdmin/getLawyersByLawyerAdmin.json","").then(function(response){
	    		$scope.lawyers=response.data.lawyersForms;
	    	});
	    };
	    
	 $scope.getMyCountyList=function(){
	    	requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
	    		$scope.mycounties=response.data.countyList;
	    	});
	    };
	    
	    $scope.getLawyerList(); 
		 $scope.getMyCountyList();
	 
	 
	 $scope.checkCustomDate=function(custom){
			
		 if(custom=='0' && $scope.patient.crashFromDate!=''){
				$scope.disableCustom=false;
			}
			else{
				$scope.disableCustom=true;
			}
		};
	
	$scope.searchItems=function(searchObj){
			requestHandler.postRequest("Patient/searchPatients.json",searchObj).then(function(response){
				$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
				$scope.lAdminPatientSearchData=response.data.patientSearchResult.patientSearchLists;
			});
	};
	 
	$scope.searchPatients = function(){
		
	if($scope.patient.crashFromDate!="" && $scope.patient.numberOfDays=="" && $scope.patient.crashToDate==""){
			$scope.crashToRequired=true;
		}
	else{
			$scope.crashToRequired=false;
			$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";
			$scope.patient.localReportNumber="";
			$scope.searchItems($scope.patient);
		}
	};
	
	$scope.secoundarySearchPatient=function(){
		$scope.patient.pageNumber= 1;
		$scope.setPage=1;
		$scope.searchItems($scope.patient);
	};
	
	$scope.searchPatientsFromPage = function(pageNum){
		 $scope.patient.pageNumber=pageNum;
		 $scope.searchItems($scope.patient);
	};
	
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
		 $scope.patient.lawyerId="0";
	     $scope.patientSearchForm.$setPristine();
	     $scope.lAdminPatientSearchData="";
	     $scope.totalRecords="";
	     $scope.init();
	     
	};
	
}]); 

 