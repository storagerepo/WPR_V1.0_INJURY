var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('LAdminSearchPatientsController', ['$scope','requestHandler','$state','Flash', function($scope,requestHandler,$state,Flash) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.lAdminPatientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isCheckedAllPatients=false;
	

	
	 $scope.getLawyerList=function(){
	    	requestHandler.getRequest("LAdmin/getLawyersForAssign.json","").then(function(response){
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
		
		$scope.isChecked=function(){
			if($scope.lAdminPatientSearchData.length>0){
				$.each($scope.lAdminPatientSearchData, function(index,value) {
					var i=0;
					for(i;i<value.numberOfPatients;i++){
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
			requestHandler.postRequest("Patient/searchPatients.json",searchObj).then(function(response){
				$scope.totalRecords=response.data.patientSearchResult.totalNoOfRecord;
				$scope.lAdminPatientSearchData=response.data.patientSearchResult.searchResult;
				$.each($scope.lAdminPatientSearchData, function(index,value) {
					$.each(value.patientSearchLists,function(index1,value1){
					switch(value1.patientStatus) {
					    case null:
					        value1.patientStatusName="New";
					        break;
					    case 1:
					    	value1.patientStatusName="Active";
					        break;
					   
					    case 6:
					    	value1.patientStatusName="Need Re-assign";
					        break;
					    default:
					        null;
					};
					});
				});
				$scope.patientSearchDataOrginal=angular.copy($scope.lAdminPatientSearchData);
				$scope.isCheckedIndividual();
			});
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
			$scope.patient.patientName="";
			$scope.patient.phoneNumber= "";
			$scope.searchItems($scope.patient);
		}
	};
	
	$scope.secoundarySearchPatient=function(){
		$scope.patient.pageNumber= 1;
		/*$scope.setPage=1;*/
		$scope.searchItems($scope.patient);
	};
	
	$scope.searchPatientsFromPage = function(pageNum){
		 $scope.patient.pageNumber=pageNum;
		 $scope.searchItems($scope.patient);
	};
	
	 $scope.selectGroup=function(id){
			$.each($scope.lAdminPatientSearchData, function(index,value) {
			if(value.localReportNumber==id.resultData.localReportNumber){
				
				var i=0;
				for(i;i<value.numberOfPatients;i++){
					value.patientSearchLists[i].selected=id.isCheckedAllGroupPatients;
				}
			}
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
			$scope.searchPatients();
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
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
		});
		assignLawyerObj.patientId=patientIdArray;
		$scope.assign(assignLawyerObj);
	
	};
	
	
	$scope.releaseLawyer=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lAdminPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/releaseLawyer.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully Released Lawyer!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.moveArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lAdminPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/moveToArchive.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully Moved to Archive!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.releaseArchive=function(){
		var assignLawyerObj ={};
		var patientIdArray=[];
		$.each($scope.lAdminPatientSearchData, function(index,value) {
			$.each(value.patientSearchLists,function(index1,value1){
			if(value1.selected==true){
				patientIdArray.push(value1.patientId);
			}
			});
		});
		assignLawyerObj.patientId=patientIdArray;
		
		requestHandler.postRequest("/Lawyer/releaseFromArchive.json",assignLawyerObj).then(function(response){
			Flash.create('success', "You have Successfully released from Archive!");
			$scope.searchPatients();
			$(function(){
				$("html,body").scrollTop(0);
			});
		});
	};
	
	$scope.viewPatientModal=function(patientId){
		$("#myModal").modal("show");
		
		$.each($scope.lAdminPatientSearchData, function(index,value) {
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

		$scope.patient={};
		$scope.patient.countyId="0";
		$scope.patient.tier="0";
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
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.lAdminPatientSearchData="";
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.patientStatus="7";
		$scope.patient.isArchived="0";

		//Initial Search
		
		$scope.searchItems($scope.patient);
		
	};
	
	$scope.init();
	
	$scope.resetSearchData = function(){
		 $scope.patient={};
		 $scope.patient.numberOfDays="1";
		 $scope.patient.lawyerId="0";
	     $scope.patientSearchForm.$setPristine();
	     $scope.lAdminPatientSearchData="";
	     $scope.totalRecords="";
	     $scope.init();
	     
	};
	
	$scope.isCleanCheckbox=function(){
		return angular.equals($scope.lAdminPatientSearchData,$scope.patientSearchDataOrginal);
	};
	
}]); 

 