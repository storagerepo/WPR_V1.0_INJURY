var adminApp=angular.module('sbAdminApp', ['requestModule']);
adminApp.controller('showCallLogsController', function($scope,$http,$location,$stateParams,$state,requestHandler,successMessageService) {
	$scope.errorMessage=successMessageService.getMessage();
	$scope.isError=successMessageService.getIsError();
	$scope.isSuccess=successMessageService.getIsSuccess();
    successMessageService.reset();
	
	$("#calllogsModel").modal("hide");
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
	    requestHandler.postRequest("Staff/getCallLogsById.json?id="+$stateParams.id,"").then( function(response) {
	  
	   $scope.callLogs= response.data.callLogsForms;
	   
    $scope.sort('notes');
    
    requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
		
	     $scope.patient= response.data.patientsForm;
	  
	     });
  
    
    
    });
	    
	    $scope.viewPatients=function(id)
	    {
	    	requestHandler.getRequest("/Staff/getPatients.json?id="+id,"").then( function(response) {
	    		$scope.patients=response.data.patientsForm;
	    	      $("#myModal").modal("show");
	         });
	    };
	    
   $scope.deleteCalllogs=function(id)
	  {
		
		  if(confirm("Are you sure to delete CallLogs ?")){
			  requestHandler.deletePostRequest("Staff/deleteCallLogs.json?id=",id)
			  .success(function(response){
				  successMessageService.setMessage("You have Successfully Deleted!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1); 
				  $state.transitionTo($state.current, $stateParams, { reload: true, inherit: true, notify: true });
			 
			  });
		  }
	}
   
	 $scope.alertFunction=function()
   {

   };
   $scope.addModel=function()
	{
		$scope.title="Add CallLogs";
		$("#timeStamp").val("");
		$("#response").val("");
		$("#notes").val("");
		

		$("#calllogsModel").modal("show");
		$scope.calllogs={};
		 $scope.calllogs.patientId =$stateParams.id;

		$scope.options=true;
		$scope.save=function()
		{
			$("#calllogsModel").modal("hide");
			$('.modal-backdrop').hide();
			requestHandler.postRequest("Staff/saveUpdateCallLogs.json",$scope.calllogs)
				.then(function(response)
					{
					successMessageService.setMessage("You have Successfully Added!");
			          successMessageService.setIsError(0);
			          successMessageService.setIsSuccess(1); 
					$state.transitionTo($state.current, $stateParams, { reload: true, inherit: true, notify: true });
					});
			
		};

	};
	
	$scope.editModal=function(id,appointmentId)
	{
		$scope.calllogs={};
		$scope.calllogs.appointmentId = appointmentId;
		$scope.title="Edit CallLogs";
		$scope.options=false;
		
		
		requestHandler.getRequest("Staff/getCallLogs.json?id="+id,"").then( function(response) {
		    $scope.calllogs=response.data.callLogsForm;
		    $('#timeStamp').data("DateTimePicker").setDate($scope.calllogs.timeStamp);
		    $("#calllogsModel").modal("show");
		});
		
		
		
	
	};
	

	
	
	  $scope.update=function(){
		  $("#calllogsModel").modal("hide");
		  $('.modal-backdrop').hide();
		  requestHandler.postRequest("Staff/saveUpdateCallLogs.json",$scope.calllogs).then(function (status) {
			  successMessageService.setMessage("You have Successfully Updated!");
	          successMessageService.setIsError(0);
	          successMessageService.setIsSuccess(1); 
			 $state.transitionTo($state.current, $stateParams, { reload: true, inherit: true, notify: true });
			});
	 
	};
	
	$scope.Appointments={};
	 $scope.Appointments.patientId =$stateParams.id;

	
	$scope.addAppointment=function(callLogId){
		$scope.Appointments.callLogId =callLogId;
		//getting patient details by id
		requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
			$scope.patient= response.data.patientsForm;
		 });
		//setting doctor id from patient  
		$scope.Appointments.doctorId=$scope.patient.doctorId;
		$("#scheduledDate").val("");
		$("#appointmentNotes").val("");
		//getting doctor id
		requestHandler.getRequest("Staff/getDoctorId.json","").then( function(response) {
			
		     $scope.doctor= response.data.doctorsForms;
		    });
		$("#AppointmentsModal").modal("show");
	};
	 
	$scope.saveAppointments=function()
	{

		$("#AppointmentsModal").modal("hide");
		  $('.modal-backdrop').hide();
		  requestHandler.postRequest("Staff/saveUpdateAppointments.json",$scope.Appointments)
			.then(function(response)
					{
				successMessageService.setMessage("You have Successfully Added!");
		          successMessageService.setIsError(0);
		          successMessageService.setIsSuccess(1); 
				$state.transitionTo($state.current, $stateParams, { reload: true, inherit: true, notify: true });
				});
		};
	
		$scope.removeAppointment=function(id)
		{
			if(confirm("Are you sure to cancel appointment ?")){
			  requestHandler.deletePostRequest("Staff/removeAppointment.json?appointmentId=",id)
				.then(function(response)
						{
					successMessageService.setMessage("You have Successfully Cancelled!");
			          successMessageService.setIsError(0);
			          successMessageService.setIsSuccess(1); 
					$state.transitionTo($state.current, $stateParams, { reload: true, inherit: true, notify: true });
					});
			}
		};
		
		$scope.viewAppointments=function(id)
		{
		
			requestHandler.getRequest("Staff/getAppointments.json?id="+id,"").then( function(response) {
				
			    $scope.appointments=response.data.appointmentsForm;
			    
			   
			   
		     });
			
			 $("#viewAppointmentsModal").modal("show");
			 
			
		};
	
});
