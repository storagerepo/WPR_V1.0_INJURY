var adminApp=angular.module("searchModule",[]);

adminApp.service('searchService',function(){
	var countyId="0";
	var numberOfDays="1";
	var crashFromDate="";
	var callerId="0";
	var tier="0";
	var crashToDate="";
	var localReportNumber="";
	var patientName="";
	var age="3";
	var phoneNumber= "";
	var lawyerId=0;
	var pageNumber= 1;
	var itemsPerPage="25";
	var addedOnFromDate="";
	var addedOnToDate="";
	var isArchived="0";
	var patientStatus="7";
	
	// County
	this.setCounty=function(county){
		countyId=county;
	};
	
	this.getCounty=function(){
		return countyId;
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
});