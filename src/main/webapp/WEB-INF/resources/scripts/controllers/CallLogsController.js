var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);
adminApp.controller('showCallLogsController', function($scope,$http,$location,$stateParams,$state,requestHandler,Flash) {

	$scope.appointment={};
	$scope.appointment.clinicId="";
	$scope.appointment.doctorId="";
	$scope.appointment.scheduledDate="";
	
	
	$("#calllogsModel").modal("hide");
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
	   $scope.getCallLogsList=function(){
		    requestHandler.postRequest("Caller/getCallLogsById.json?id="+$stateParams.id,"").then( function(response) {
		    	$scope.callLogs= response.data.callLogsForms;
		    	$.each($scope.callLogs,function(index,value) {
			    	 switch(value.response) {
			    	    case "1":
			    	        value.response="Not interested/injured";
			    	        break;
			    	    case "2":
			    	    	value.response="Voice mail";
			    	        break;
			    	    case "3":
			    	    	value.response="Do not call";
			    	    	break;
			    	    default:
			    	    	break;
			    	} 
			     });
		    });
	   };
	   
	   $scope.init=function(){
		   $scope.getCallLogsList();
		   $scope.getPatientDetails();
		   $scope.getClinics();
	   };
	   
	  
	    
	    $scope.viewPatients=function(id)
	    {
	    	      $("#myModal").modal("show");
	    };
	    
	    $scope.getPatientDetails=function(){
	    	requestHandler.getRequest("/Patient/getPatient.json?patientId="+$stateParams.id,"").then( function(response) {
	    		$scope.patient=response.data.patientForm;
	    	});
	    };
	    
	    $scope.getCallLogsList=function(){
	    	  requestHandler.getRequest("Caller/getAllCallLogss.json?patientId="+$stateParams.id,"").then( function(response) {
	  	    	$scope.callLogs= response.data.callLogsForms;
	  	    	 $.each($scope.callLogs,function(index,value) {
	  		    	 switch(value.response) {
	  		    	    case 1:
	  		    	        value.response="Not Interested/Injured";
	  		    	        break;
	  		    	    case 2:
	  		    	    	value.response="Voice mail";
	  		    	        break;
	  		    	    case 3:
	  		    	    	value.response="Appointment Scheduled";
	  		    	    	break;
	  		    	    case 4:
	  		    	    	value.response="Do not call";
	  		    	    	break;
	  		    	    default:
	  		    	    	break;
	  		    	} 
	  		     });
	  	  });	
	    };
	    
	    $scope.getClinics=function(){
	    	requestHandler.getRequest("Caller/getClinicId.json","").then( function(response) {
				
			     $scope.clinic= response.data.clinicsForms;
			 
			     });
	    };
	    
	    $scope.getDoctors=function(){
	    	if($scope.calllogs.appointmentsForm.clinicId==null){
	    		$scope.calllogs.appointmentsForm.clinicId="";
	    	}
	    	if($scope.calllogs.appointmentsForm.clinicId!=""){
	    		requestHandler.getRequest("getNameByClinicId.json?clinicId="+$scope.calllogs.appointmentsForm.clinicId,"").then( function(response) {
					    $scope.doctor= response.data.doctorsForm;
				 });
	    	}
	    	
	    };
	    
   $scope.deleteCalllogs=function(id)
	  {
		
		  if(confirm("Are you sure to delete the call log?")){
			  requestHandler.deletePostRequest("Caller/deleteCallLogs.json?callLogsId=",id)
			  .success(function(response){
				  Flash.create("success","You have Successfully Deleted!");
				  $scope.getCallLogsList();
				 
			  });
		  }
	};
   
	
   $scope.addModel=function()
	{
		$scope.title="Add Call Log";
		$scope.options=true;
		$scope.myForm.$setPristine();
		$scope.calllogs={};
		$scope.calllogs.appointmentsForm="";
		$scope.calllogs.patientId =$stateParams.id;
		$scope.calllogs.timeStamp=moment().format('MM/DD/YYYY h:mm A');
		
		$("#calllogsModel").modal("show");
	};
	
	$scope.save=function()
	{
		$("#calllogsModel").modal("hide");
		$('.modal-backdrop').hide();
		requestHandler.postRequest("Caller/saveUpdateCallLogs.json",$scope.calllogs)
			.then(function(response)
				{
				Flash.create("success","You have Successfully Added!");
				  $scope.getCallLogsList();
				});
		
	};
	
	$scope.editModal=function(callLogId,appointmentId)
	{
		$scope.calllogs={};
		$scope.calllogs.appointmentsForm="";
		$scope.calllogs.appointmentId = appointmentId;
		$scope.title="Edit Call Log";
		$scope.options=false;
		var callLogsOriginal="";
		if(appointmentId==null){
			appointmentId="";
		}
		requestHandler.getRequest("Caller/getCallLogs.json?callLogId="+callLogId+"&appointmentId="+appointmentId,"").then( function(response) {
			callLogsOriginal=angular.copy(response.data.callLogsForm);
		    $scope.calllogs=response.data.callLogsForm;		    
		    $scope.calllogs.response=$scope.calllogs.response.toString();
		    $scope.calllogs.appointmentsForm=response.data.callLogsForm.appointmentsForm;
		   $('#timeStamp').data("DateTimePicker").setDate($scope.calllogs.timeStamp);
		   $scope.getDoctors();
		    $("#calllogsModel").modal("show");
		});
		
		 $scope.isClean = function() {
			  return angular.equals(callLogsOriginal, $scope.calllogs);
		    };
		
	
	};
	
	$scope.appointmentAlert=function(){
		if($scope.calllogs.response!=3){
			if(confirm("Are you sure want to remove appointment?")){
				$scope.calllogs.appointmentsForm.doctorId="";
				$scope.calllogs.appointmentsForm.clinicId="";
				$scope.calllogs.appointmentsForm.scheduledDate="";
			}
		}
	};

	
	
	  $scope.update=function(){
		  $("#calllogsModel").modal("hide");
		  $('.modal-backdrop').hide();
		  requestHandler.postRequest("Caller/saveUpdateCallLogs.json",$scope.calllogs).then(function (status) {
			  Flash.create("success","You have Successfully Updated!");
			  $state.reload('dashboard.Calllogs/:id');
			  $scope.getCallLogsList();
			});
		 		 
	};
	 
	
	
	$scope.Appointments={};

	
	$scope.addAppointment=function(callLogId){
		$scope.Appointments.callLogId =callLogId;
		//getting patient details by id
		requestHandler.getRequest("Caller/getPatients.json?id="+$stateParams.id,"").then( function(response) {
			$scope.patient= response.data.patientsForm;
			
		 });
		//setting clinic id from patient 
		
		if($scope.patient.clinicId==0)
		{
	$scope.Appointments.clinicId="";
	}
else
	{
	$scope.Appointments.clinicId=$scope.patient.clinicId;
	}
		//setting doctor id from patient 
		if($scope.patient.doctorId==0)
			{
		$scope.Appointments.doctorId="";
		}
	else
		{
		$scope.Appointments.doctorId=$scope.patient.doctorId;
		}
		
	$("#scheduledDate").val("");
		$("#appointmentNotes").val("");
		//getting doctor id
		
		var ClinicId=$scope.patient.clinicId;
		
		$scope.doctorName=function(){
			var ClinicId=0;
			if($scope.Appointments.clinicId==null){
				ClinicId=0;
			}else{
				ClinicId=$scope.Appointments.clinicId;
			}
			
				 requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
						
				    $scope.doctor= response.data.doctorsForm;
				  
				   });

				}
		$("#AppointmentsModal").modal("show");
	};
	
	$scope.saveAppointments=function()
	{

		$("#AppointmentsModal").modal("hide");
		  $('.modal-backdrop').hide();
		  requestHandler.postRequest("Caller/saveUpdateAppointments.json",$scope.Appointments)
			.then(function(response)
					{
				Flash.create("success","You have Successfully Added!");
				  $scope.getCallLogsList();
				});
		 
		};
		
		$scope.removeAppointment=function(id,iD)
		{
			
			if(confirm("Are you sure to cancel appointment ?")){
				 
			  requestHandler.deletePostRequest("Caller/removeAppointment.json?appointmentId=",id)
				.then(function(response)
						{
					requestHandler.getRequest("Caller/activeStatusByPatientId.json?id="+iD,"")
					.then(function(results)
							{
						$scope.response=results.data.requestSuccess;
						
						Flash.create("success","You have Successfully Cancelled!");
						
					  $scope.getCallLogsList();
					});
						});
			
			}
		};
		
		$scope.viewAppointments=function(id)
		{
		if(id==null)
			{
			 $scope.appointments={};
			 $scope.appointments.notavailable=true;
			}
		else{
		requestHandler.getRequest("Caller/getAppointments.json?id="+id,"").then( function(response) {
			
			    $scope.appointments=response.data.appointmentsForm;
			    $scope.appointments.notavailable=false;
		  });
		}	
			 $("#viewAppointmentsModal").modal("show");
			 
		
		};
		
		
		$scope.init();
	
});
