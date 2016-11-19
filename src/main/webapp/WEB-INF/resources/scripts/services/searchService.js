var adminApp=angular.module("searchModule",[]);

adminApp.service('searchService',function(){
	var countyId=[{"id":1},{"id":2},{"id":3},{"id":4},{"id":5},{"id":6},{"id":7},{"id":8},{"id":9},{"id":10},{"id":11},{"id":12},{"id":13},{"id":14},{"id":15},{"id":16},{"id":17},{"id":18},{"id":19},{"id":20},{"id":21},{"id":22},{"id":23},{"id":24},{"id":25},{"id":26},{"id":27},{"id":28},{"id":29},{"id":30},{"id":31},{"id":32},{"id":33},{"id":34},{"id":35},{"id":36},{"id":37},{"id":38},{"id":39},{"id":40},{"id":41},{"id":42},{"id":43},{"id":44},{"id":45},{"id":46},{"id":47},{"id":48},{"id":49},{"id":50},{"id":51},{"id":52},{"id":53},{"id":54},{"id":55},{"id":56},{"id":57},{"id":58},{"id":59},{"id":60},{"id":61},{"id":62},{"id":63},{"id":64},{"id":65},{"id":66},{"id":67},{"id":68},{"id":69},{"id":70},{"id":71},{"id":72},{"id":73},{"id":74},{"id":75},{"id":76},{"id":77},{"id":78},{"id":79},{"id":80},{"id":81},{"id":82},{"id":83},{"id":84},{"id":85},{"id":86},{"id":87},{"id":88}];
	var numberOfDays="1";
	var crashFromDate="";
	var callerId="0";
	var tier=[{id:1},{id:2},{id:3},{id:4}];
	var crashToDate="";
	var localReportNumber="";
	var patientName="";
	var age=[{id:1},{id:2},{id:4}];
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