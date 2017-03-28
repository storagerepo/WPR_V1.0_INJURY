var adminApp=angular.module("searchModule",['requestModule']);

adminApp.service('searchService',function($rootScope,requestHandler){
	var countySession=[];
	var numberOfDays="1";
	var crashFromDate="";
	var callerId="0";
	var tier=[{id:1},{id:2},{id:3},{id:4}];
	var crashToDate="";
	var localReportNumber="";
	var patientName="";
	var age=[{id:1},{id:2},{id:4}];
	var lAdminAge=[{id:1}];
	var phoneNumber= "";
	var lawyerId="0";
	var pageNumber= 1;
	var itemsPerPage="25";
	var addedOnFromDate="";
	var addedOnToDate="";
	var isArchived="0";
	var patientStatus="7";
	var countyListType="1";
	var archivedFromDate="";
	var archivedToDate="";
	var isRunnerReport=0;
	
	// Constant
	var maxRecordsDownload=100000;
	this.getMaxRecordsDownload=function(){
		return maxRecordsDownload;
	};
	
	// County
	this.setCounty=function(countyInput){
		countySession=countyInput;
	};
	
	this.getCounty=function(){
		return countySession;
	};
	
	//Number Of Days
	this.setNumberOfDays=function(numberOfDaysInput){
		numberOfDays=numberOfDaysInput;
	};
	
	this.getNumberOfDays=function(){
		return numberOfDays;
	};
	
	// Crash From Date
	this.setCrashFromDate=function(crashFromDateInput){
		crashFromDate=crashFromDateInput;
	};
	
	this.getCrashFromDate=function(){
		return crashFromDate;
	};
	
	// Crash To Date
	this.setCrashToDate=function(crashToDateInput){
		crashToDate=crashToDateInput;
	};
	
	this.getCrashToDate=function(){
		return crashToDate;
	};
	
	// CallerId
	this.setCallerId=function(callerIdInput){
		callerId=callerIdInput;
	};
	
	this.getCallerId=function(){
		return callerId;
	};
	
	//Tier
	this.setTier=function(tierInput){
		tier=tierInput;
	};
	
	this.getTier=function(){
		return tier;
	};
	
	//Local Report Number
	this.setLocalReportNumber=function(localReportNumberInput){
		localReportNumber=localReportNumberInput;
	};
	
	this.getLocalReportNumber=function(){
		return localReportNumber;
	};
	
	//Patient Name
	this.setPatientName=function(patientNameInput){
		patientName=patientNameInput;
	};
	
	this.getPatientName=function(){
		return patientName;
	};
	
	//Age
	this.setAge=function(ageInput){
		age=ageInput;
	};
	
	this.getAge=function(){
		return age;
	};
	
	//Age
	this.setLAdminAge=function(ladminAgeInput){
		lAdminAge=ladminAgeInput;
	};
	
	this.getLAdminAge=function(){
		return lAdminAge;
	};
	
	//Phone Number
	this.setPhoneNumber=function(phoneNumberInput){
		phoneNumber=phoneNumberInput;
	};
	
	this.getPhoneNumber=function(){
		return phoneNumber;
	};
	
	//Lawyer Id
	this.setLawyerId=function(lawyerIdInput){
		lawyerId=lawyerIdInput;
	};
	
	this.getLawyerId=function(){
		return lawyerId;
	};
	
	// Added on From Date
	this.setAddedOnFromDate=function(addedOnFromDateInput){
		addedOnFromDate=addedOnFromDateInput;
	};
	
	this.getAddedOnFromDate=function(){
		return addedOnFromDate;
	};
	// Added on To Date
	this.setAddedOnToDate=function(addedOnToDateInput){
		addedOnToDate=addedOnToDateInput;
	};
	
	this.getAddedOnToDate=function(){
		return addedOnToDate;
	};
	
	// Is Archived
	this.setIsArchived=function(isArchivedInput){
		isArchived=isArchivedInput;
	};
	
	this.getIsArchived=function(){
		return isArchived;
	};
	
	// PatientStatus
	this.setPatientStatus=function(patientStatusInput){
		patientStatus=patientStatusInput;
	};
	
	this.getPatientStatus=function(){
		return patientStatus;
	};
	
	// Page Number
	this.setPageNumber=function(pageNumberInput){
		pageNumber=pageNumberInput;
	};
	
	this.getPageNumber=function(){
		return pageNumber;
	};
	
	// Items Per Page
	this.setItemsPerPage=function(itemsPerPageInput){
		itemsPerPage=itemsPerPageInput;
	};
	
	this.getItemsPerPage=function(){
		return itemsPerPage;
	};
	// County List Type
	this.setCountyListType=function(countyListTypeInput){
		countyListType=countyListTypeInput;
	};
	
	this.getCountyListType=function(){
		return countyListType;
	};
	// Archived From Date
	this.setArchivedFromDate=function(archivedFromDateInput){
		archivedFromDate=archivedFromDateInput;
	};
	this.getArchivedFromDate=function(){
		return archivedFromDate;
	};
	// Archived To Date
	this.setArchivedToDate=function(archivedToDateInput){
		archivedToDate=archivedToDateInput;
	};
	this.getArchivedToDate=function(){
		return archivedToDate;
	};
	
	// Is Runner Report
	this.getIsRunnerReport=function(){
		return isRunnerReport;
	};
	this.setIsRunnerReport=function(isRunnerReportInput){
		isRunnerReport=isRunnerReportInput;
	};
	// For Swapping Patient Name from Last, First, Middle to First, Middle, Middle
	this.spiltAndSwapName=function(patientName){
		var swapName="";
		if(patientName!=null){
			var splitname=patientName.split(",");
			if(splitname.length==2){
				swapName=splitname[1]+", "+splitname[0];
			}else if(splitname.length==1){
				swapName=splitname[0];
			}else if(splitname[2].replace(/\s/g,'')==''){
				swapName=splitname[1]+", "+splitname[0];
			}else{
				swapName=splitname[1]+", "+splitname[2]+", "+splitname[0];
			}
		}
		return swapName;
	};
	
	// Check County List Type
	this.checkCoutyListType=function(){
		return requestHandler.getRequest("Patient/checkCountyListType.json","").then(function(response){
			 return response.data.countyListType;
		});
	};
	
	// Get Preference List
	this.getPreferenceCoutyList=function(countyListType){
		return requestHandler.getRequest("Patient/getPreferenceCounties.json?countyListType="+countyListType,"").then(function(response){
			 return response.data.countyList;
		});
	};
	
	// Get Initial County Preference List For Select
	this.getInitPreferenceCoutyList=function(countyListType){
		var countySelection=[];
		return requestHandler.getRequest("Patient/getPreferenceCounties.json?countyListType="+countyListType,"").then(function(response){
			$.each(response.data.countyList, function(index,value) {
				countySelection.push({"id":value.countyId});
			});
			return countySelection;
		});
	};
	
	
	// Reset Search Data
	this.resetSearchData=function(){
		countySession=[];
		numberOfDays="1";
		crashFromDate="";
		callerId="0";
		tier=[{id:1},{id:2},{id:3},{id:4}];
		crashToDate="";
		localReportNumber="";
		patientName="";
		age=[{id:1},{id:2},{id:4}];
		lAdminAge=[{id:1}];
		phoneNumber= "";
		lawyerId="0";
		pageNumber= 1;
		itemsPerPage="25";
		addedOnFromDate="";
		addedOnToDate="";
		isArchived="0";
		patientStatus="7";
		countyListType="1";
		archivedFromDate="";
		archivedToDate="";
		isRunnerReport=0;
		return true;
	};
	
	// Calculate Number of days between dates
	this.calculateNumberOfDays=function(startDateTime,endDateTime){
		var numberOfDays=0;
		numberOfDays=moment(new Date(endDateTime)).diff(startDateTime,"days")+1;
		return numberOfDays;
	};

});