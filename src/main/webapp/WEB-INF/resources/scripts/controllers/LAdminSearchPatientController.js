var adminApp=angular.module('sbAdminApp', ['requestModule','flash','ngFileSaver']);

adminApp.controller('LAdminSearchPatientsController', ['$scope','requestHandler','$state','Flash','FileSaver','$q', function($scope,requestHandler,$state,Flash,FileSaver,$q) {
	$scope.disableCustom=true;
	$scope.crashSearchData="";
	$scope.lAdminPatientSearchData=$scope.patientSearchDataOrginal=[];
	$scope.isCheckedAllPatients=false;
	$scope.exportButtonText="Export to Excel";
	$scope.exportButton=false;
	$scope.setScrollDown=false;

	
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
		
		$scope.isChecked=function(){
			if($scope.lAdminPatientSearchData.length>0){
				$.each($scope.lAdminPatientSearchData, function(index,value) {
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
		var defer=$q.defer();
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
					defer.resolve(response);
				});
				$scope.patientSearchDataOrginal=angular.copy($scope.lAdminPatientSearchData);
				$scope.isCheckedIndividual();
			});
			return defer.promise;
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
			$scope.searchItems($scope.patient);
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
			$scope.searchItems($scope.patient);
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
			$scope.searchItems($scope.patient);
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
			$scope.searchItems($scope.patient);
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
		$scope.patient.itemsPerPage="25";
		$scope.totalRecords=0;
		$scope.lAdminPatientSearchData="";
		$scope.patient.addedOnFromDate="";
		$scope.patient.addedOnToDate="";
		$scope.patient.patientStatus="7";
		$scope.patient.isArchived="0";


		$scope.patient.pageNumber= 1;
		$scope.oldPageNumber= $scope.patient.pageNumber;
		if($scope.oldPageNumber==$scope.patient.pageNumber){//This will call search function thru patient.pageNumber object $watch function 
			$scope.searchItems($scope.patient);
		}
		
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
	
}]); 

 