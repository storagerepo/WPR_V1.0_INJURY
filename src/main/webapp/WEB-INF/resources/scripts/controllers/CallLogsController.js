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
			    	    case 2:
			    	        value.response="Not interested/injured";
			    	        break;
			    	    case 3:
			    	    	value.response="Voice mail";
			    	        break;
			    	    case 4:
	  		    	    	value.response="Appointment Scheduled";
	  		    	    	break;
			    	    case 5:
			    	    	value.response="Do not call";
			    	    	break;
			    	    case 8:
			    	    	value.response="Call Back";
			    	    	break;
			    	    case 9:
			    	    	value.response="Unable To Reach";
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
	  		    	    case 2:
	  		    	        value.response="Not Interested/Injured";
	  		    	        break;
	  		    	    case 3:
	  		    	    	value.response="Voice mail";
	  		    	        break;
	  		    	    case 4:
	  		    	    	value.response="Appointment Scheduled";
	  		    	    	break;
	  		    	    case 5:
	  		    	    	value.response="Do not call";
	  		    	    	break;
	  		    	    case 8:
			    	    	value.response="Call Back";
			    	    	break;
	  		    	    case 9:
			    	    	value.response="Unable To Reach";
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
		$scope.isAlert=false;
		$scope.calllogs.appointmentsForm={};
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
		$scope.isAlert=true;
		$scope.response="";
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
		    if($scope.calllogs.response=="4"){
				$scope.response=$scope.calllogs.response;
				$scope.isCollapse=true;
			}
		   $('#timeStamp').data("DateTimePicker").setDate($scope.calllogs.timeStamp);
		   $scope.getDoctors();
		    $("#calllogsModel").modal("show");
		});
		
		 $scope.isClean = function() {
			  return angular.equals(callLogsOriginal, $scope.calllogs);
		    };
		
	
	};
	
	$scope.appointmentAlert=function(){
		if($scope.isAlert){
			if($scope.calllogs.appointmentsForm.appointmentId!=""){
				if($scope.response!=""){
					$scope.response="";
					if(confirm("Are you sure want to remove appointment?")){
						$scope.isCollapse=false;
						$scope.calllogs.appointmentsForm.doctorId="";
						$scope.calllogs.appointmentsForm.clinicId="";
						$scope.calllogs.appointmentsForm.scheduledDate="";
					}else{
						$scope.calllogs.response="4";
						$scope.response="4";
						$scope.isCollapse=true;
					}
				}
			}else if($scope.calllogs.response==4){
				$scope.response=$scope.calllogs.response;
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
	 
	
	
	
		
		$scope.viewAppointments=function(appointmentId)
		{
		if(appointmentId==null)
			{
			 $scope.appointments={};
			 $scope.appointments.notavailable=true;
			}
		else{
		requestHandler.getRequest("Caller/getAppointments.json?appointmentId="+appointmentId,"").then( function(response) {
			
			    $scope.appointments=response.data.appointmentsForm;
			    $scope.appointments.notavailable=false;
		  });
		}	
			 $("#viewAppointmentsModal").modal("show");
			 
		
		};
		
		
		$scope.init();
	
});
