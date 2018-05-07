var adminApp=angular.module('sbAdminApp', ['requestModule','searchModule','flash']);

adminApp.controller('ShowAppointmentsCtrl', function($rootScope,$scope,$http,$location,$state,Flash,requestHandler,searchService) {
	
	$scope.init=function(){
		$scope.searchAppointment={};
		$scope.appointments="";
		var date=new Date();
	    $scope.searchAppointment.month="13";
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
		$scope.patientTableText="Patient Name";
		$scope.clinicTableText="Clinic Name";
		$scope.patientLabelText="Patient";
		$scope.clinicLabelText="Clinic";
		$scope.detailsHeadText="Clinic & Doctor Details";
		$scope.detailsDoctorText="Doctors List";
		$scope.detailsNoDoctorText="No Doctors added";
		$scope.doctorIcon="fa-user-md";
		if($rootScope.isAdmin==8){
			$scope.patientTableText="Client Name";
			$scope.clinicTableText="Shop Name";
			$scope.patientLabelText="Client";
			$scope.clinicLabelText="Shop";
			$scope.detailsHeadText="Shop & Mechanic Details";
			$scope.detailsDoctorText="Mechanics List";
			$scope.detailsNoDoctorText="No Mechanic added";
			$scope.doctorIcon="fa-wrench";
		}
	};
	$scope.searchAppointments=function(){
		$scope.searchAppointment.pageNumber=1;
		if($scope.searchAppointment.year!=''){
			requestHandler.postRequest("Caller/searchAppointments.json",$scope.searchAppointment).then(function(response){
				//alert(JSON.stringify(response));
		    	$scope.totalRecords=response.data.appointmentsSearchRessult.totalRecords;
		    	 $scope.appointments = response.data.appointmentsSearchRessult.appointmentsForms;
		       $.each($scope.appointments,function(index,value){
		        	 value.status=(value.status).toString();
		        	
		        });
			  });
		}
	    
	};
	

	$scope.init();
	$scope.searchAppointmentByPage=function(newPageNumber){
		 $scope.searchAppointment.pageNumber=newPageNumber;
		 if($scope.searchAppointment.year!=''){
			requestHandler.postRequest("Caller/searchAppointments.json",$scope.searchAppointment).then(function(response){
				//alert(JSON.stringify(response));
		    	$scope.totalRecords=response.data.appointmentsSearchRessult.totalRecords;
		    	 $scope.appointments = response.data.appointmentsSearchRessult.appointmentsForms;
		       $.each($scope.appointments,function(index,value){
		        	 value.status=(value.status).toString();
		   		});
			});
		 }
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
		if($rootScope.isAdmin==2)
			$("#viewPatientDetailsModal").modal("show");
		else if($rootScope.isAdmin==6||$rootScope.isAdmin==8)
			$("#myModal").modal("show");
     });
};

});
