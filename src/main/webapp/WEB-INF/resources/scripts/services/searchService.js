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
	
	//Phone Number
	this.setPhoneNumber=function(phoneNumberInput){
		patientName=phoneNumberInput;
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
});