var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('ShowAppointmentsCtrl', function($scope,$http,$location,$state,Flash,requestHandler) {
	
	$scope.init=function(){
		$scope.searchAppointment={};
		var date=new Date();
	    $scope.searchAppointment.month=(date.getMonth()+1).toString();
	    $scope.searchAppointment.year=date.getFullYear();
	    $scope.searchAppointment.date="";
	    $scope.searchAppointment.status="0";
	    $scope.searchAppointment.clinicName="";
	    $scope.searchAppointment.patientName="";
	    $scope.searchAppointment.pageNumber=1;
		$scope.searchAppointment.itemsPerPage="25";
		$scope.searchAppointment.callerFirstName="";
		$scope.searchAppointment.callerLastName="";
		$scope.totalRecords=0;

		$scope.searchAppointments();
	};
	$scope.searchAppointments=function(){
	    requestHandler.postRequest("Caller/searchAppointments.json",$scope.searchAppointment).then(function(response){
			//alert(JSON.stringify(response));
	    	$scope.totalRecords=response.data.appointmentsSearchRessult.totalRecords;
	    	 $scope.appointments = response.data.appointmentsSearchRessult.appointmentsForms;
	       $.each($scope.appointments,function(index,value){
	        	 value.status=(value.status).toString();
	        });
		  });
	};
	

	$scope.init();
	$scope.searchAppointmentByPage=function(newPageNumber){
		 $scope.searchAppointment.pageNumber=newPageNumber;
		$scope.searchAppointments();
	};
	
	
	$scope.changeAppointmentStatus=function(appointmentId,status){
		requestHandler.postRequest("Caller/changeAppointmentStatus.json?appointmentId="+appointmentId+"&status="+status,"").then(function(results){
			$scope.searchAppointments();
			Flash.create('success', "You have Successfully Changed!");
		  });
	};
	
// Get Clinic Details
	$scope.viewClinicDetailsModal=function(clinicId) {
		 requestHandler.getRequest("CAdmin/getClinicDetails.json?clinicId="+clinicId,"").then(function(results){
		 	 $scope.clinicDetails= results.data.clinicsForm;
		 	 $("#viewClinicDetailsModal").modal('show');
		  });
	};
	

$scope.viewPatientDetailsModal=function(patientId)
{
	requestHandler.getRequest("Patient/getPatient.json?patientId="+patientId,"").then( function(response) {
		$scope.patient=response.data.patientForm;
	      $("#viewPatientDetailsModal").modal("show");
     });
};
$scope.getByDates=function()
{
if($scope.searchMonth)
	{
	requestHandler.getRequest("Caller/monthwiseAppointment.json?month="+$scope.searchMonth+"&year="+$scope.searchYear,"").then(function (response) {
			$scope.appointments=response.data.appointmentsForms;
			 $.each($scope.appointments,function(index,value){
	        	 if(value.status==0){
	        		 value.status="Not Arrived";
	        	 }
	         });
	});
	 }

else
{
	requestHandler.getRequest("Caller/monthwiseAppointment.json?month=0&year=0").then( function(response) {
	     $scope.appointments = response.data.appointmentsForms;
	     $.each($scope.appointments,function(index,value){
        	 if(value.status==0){
        		 value.status="Not Arrived";
        	 }
         });
	    $scope.appointments.status = true;
	    });
	}

};
});
