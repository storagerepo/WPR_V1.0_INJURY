var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('ShowClinicController',function($scope,requestHandler,Flash){
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
	    requestHandler.getRequest("Staff/getAllClinics.json","").then(function(results){
	    	 $scope.clinics= results.data.clinicsForm;
	         $scope.sort('clinicName');
	     });
	    
	$scope.getClinicList=function() {
		 requestHandler.getRequest("Staff/getAllClinics.json","").then(function(results){
		 	 $scope.clinics= results.data.clinicsForm;
		  });
	};
	
	$scope.viewClinicDetails=function(clinicId) {
		 requestHandler.getRequest("Staff/getClinic.json?clinicId="+clinicId,"").then(function(results){
		 	 $scope.clinicDetails= results.data.clinicsForm;
		 	 $("#viewClinicDetails").modal('show');
		  });
	};
	
	// Delete the Clinic
	$scope.deleteClinic=function(clinicId)
	  {
		
		  if(confirm("Are you sure to delete Clinic ?")){
		  requestHandler.deletePostRequest("Staff/deleteClinic.json?clinicId=",clinicId).then(function(results){
			  $scope.value=results.data.requestSuccess;
			  
			  if($scope.value==true)
				  {
				  Flash.create('success', "You have Successfully Deleted!");
		          $scope.getClinicList();
				  }
			  else
				  {
				  $("#deleteClinicModal2").modal("show");
				    $scope.deleteDoctorFromClinic=function()
				    {
				    	
				    	requestHandler.postRequest("Admin/deleteDoctorsByClinic.json?clinicId="+clinicId).then(function(response){
				    		
				    		 requestHandler.deletePostRequest("Staff/deleteClinic.json?clinicId=",clinicId).then(function(results){
				    			 $("#deleteClinicModal2").modal("hide");
							    	$('.modal-backdrop').hide();
						          Flash.create('success', "You have Successfully Deleted!");
						          $scope.getClinicList();
				    		 });
				    		 
							});
				    };
				  
				  }
		     });
		  }
	};
	$scope.viewDoctors=function(clinicId)
    {
    	 requestHandler.getRequest("getDoctorsByClinic.json?clinicId="+clinicId,"").then( function(results) {
    		 	$scope.doctorDetails=results.data.doctorsForms;
         });
    	 $("#viewDoctors").modal('show');
    };
});

adminApp.controller('SaveClinicController',function($scope,$location,requestHandler,Flash){
	$scope.options=true;
	$scope.title="Add Clinic";
	$scope.clinic={};
	$scope.clinic.clinicTimingList=[{"day":0,"startTime":"","endTime":"","isWorkingDay":0},{"day":1,"startTime":"","endTime":"","isWorkingDay":0},{"day":2,"startTime":"","endTime":"","isWorkingDay":0},{"day":3,"startTime":"","endTime":"","isWorkingDay":0},{"day":4,"startTime":"","endTime":"","isWorkingDay":0},{"day":5,"startTime":"","endTime":"","isWorkingDay":0},{"day":6,"startTime":"","endTime":"","isWorkingDay":0}];
	$scope.saveClinic=function(){
		requestHandler.postRequest("Staff/saveOrUpdateClinic.json",$scope.clinic).then(function(response){
			Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/clinic');
		});
	};
	
});

adminApp.controller('EditClinicController',function($scope,$stateParams,$location,requestHandler,Flash){
	$scope.options=false;
	$scope.title="Edit Clinic";
	var clinicOriginal="";
		requestHandler.getRequest("Staff/getClinic.json?clinicId="+$stateParams.id,"").then(function(response){
			clinicOriginal=angular.copy(response.data.clinicsForm);
			$scope.clinic= response.data.clinicsForm;
			$('#sunStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[0].startTime);
			$('#sunEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[0].endTime);
			$('#monStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[1].startTime);
			$('#monEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[1].endTime);
			$('#tueStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[2].startTime);
			$('#tueEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[2].endTime);
			$('#wedStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[3].startTime);
			$('#wedEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[3].endTime);
			$('#thuStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[4].startTime);
			$('#thuEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[4].endTime);
			$('#friStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[5].startTime);
			$('#friEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[5].endTime);
			$('#satStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[6].startTime);
			$('#satEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[6].endTime);
		});
		
		$scope.updateClinic=function(){
			requestHandler.postRequest("Staff/saveOrUpdateClinic.json",$scope.clinic).then(function(response){
				Flash.create('success', "You have Successfully Updated!");
					  $location.path('dashboard/clinic');
			});
		};
		
		$scope.isClean = function() {
	        return angular.equals(clinicOriginal, $scope.clinic);
	    };
	
});

//Directive For Comapre From and To Time
adminApp.directive('higherThan',function() {
return {
require: "ngModel",
scope: {
otherModelValue: "=higherThan"
},
link: function(scope, element, attributes, ngModel) {

ngModel.$validators.higherThan = function(modelValue) {
	
return modelValue > scope.otherModelValue;
};

scope.$watch("otherModelValue", function() {
ngModel.$validate();
});
}
};
});