var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);
adminApp.controller('showCallLogsController', function($scope,$http,$location,$stateParams,$state,requestHandler,Flash) {

	
	$("#calllogsModel").modal("hide");
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
	   $scope.getCallLogsList=function(){
		    requestHandler.postRequest("Staff/getCallLogsById.json?id="+$stateParams.id,"").then( function(response) {
		    	$scope.callLogs= response.data.callLogsForms;
		    });
	   };
	   
	    requestHandler.postRequest("Staff/getCallLogsById.json?id="+$stateParams.id,"").then( function(response) {
	    	$scope.callLogs= response.data.callLogsForms;
	    	$scope.sort('timeStamp');
	    	requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
	    		$scope.patient= response.data.patientsForm;
	    		if($scope.patient.patientStatus==3)
	    			{
	    		$scope.doNotCall=true;
	    			}
	    		else
	    			{
	    			$scope.doNotCall=false;
	    			}
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
				  Flash.create("success","You have Successfully Deleted!");
				  $scope.getCallLogsList();
				 
			  });
		  }
	};
   
	
   $scope.addModel=function()
	{
		$scope.title="Add CallLogs";
		$scope.options=true;
		$scope.myForm.$setPristine();
		$scope.calllogs={};
		 $scope.calllogs.patientId =$stateParams.id;
		 var date=new Date();
		if(date.getMonth()<=8 && date.getDate()<=9 && date.getHours()<=9 && date.getMinutes()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":0"+date.getSeconds();
		   }
		else if(date.getMonth()<=8 && date.getDate()<=9 && date.getHours()<=9 && date.getMinutes()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8 && date.getHours()<=9 && date.getMinutes()<=9 && date.getSeconds()<=9){
			$scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":0"+date.getSeconds();
			   }
	 	 else if(date.getMonth()<=8 && date.getDate()<=9 && date.getHours()<=9){
			$scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	 		   }
		 else if(date.getMonth()<=8 && date.getDate()<=9 && date.getMinutes()<=9){
			$scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
	 		   }
		 else if(date.getMonth()<=8 && date.getDate()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
	 		   }
		 else if(date.getMonth()<=8 && date.getHours()<=9 && date.getMinutes()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8 && date.getHours()<=9 && date.getSeconds()<=9){
			$scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8 && date.getMinutes()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":0"+date.getMinutes()+":0"+date.getSeconds();
			   }
	 	else if(date.getMonth()<=8 && date.getDate()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8 && date.getHours()<=9){
			$scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8 && date.getMinutes()<=9){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8 && date.getSeconds()<=9){
			$scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
			   }
		 else if(date.getMonth()<=8){
			 $scope.calllogs.timeStamp="0"+(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		     }
		 else if(date.getDate()<=9 && date.getHours()<=9 && date.getMinutes()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":0"+date.getSeconds();
			   }
		  else if(date.getDate()<=9 && date.getHours()<=9 && date.getMinutes()<=9){
			  $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
}
		 else if(date.getDate()<=9 && date.getHours()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
}	 
  else if(date.getDate()<=9 && date.getHours()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getDate()<=9 && date.getMinutes()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getDate()<=9 && date.getSeconds()<=9){
			  $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
			   }
		 else if(date.getDate()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/0"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
			   }
		  else if(date.getHours()<=9 && date.getMinutes()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":0"+date.getSeconds();
			   }
		 else if(date.getHours()<=9 && date.getMinutes()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
			   }
		 else if(date.getHours()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
			   }
		  else if(date.getHours()<=9){
			  $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+"0"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
		  else if(date.getMinutes()<=9 && date.getSeconds()<=9){
			 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":0"+date.getMinutes()+":0"+date.getSeconds();
			   }
		 else if(date.getMinutes()<=9){
		     $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":0"+date.getMinutes()+":"+date.getSeconds();
			}
		     else if(date.getSeconds()<=9){
		    	  $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":0"+date.getSeconds();
			 }
		     else{
		    	 $scope.calllogs.timeStamp=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		     }
		 
		 	$("#calllogsModel").modal("show");
	};
	
	$scope.save=function()
	{
		$("#calllogsModel").modal("hide");
		$('.modal-backdrop').hide();
		requestHandler.postRequest("Staff/saveUpdateCallLogs.json",$scope.calllogs)
			.then(function(response)
				{
				Flash.create("success","You have Successfully Added!");
				  $scope.getCallLogsList();
				});
		
	};
	
	$scope.editModal=function(id,appointmentId)
	{
		$scope.calllogs={};
		$scope.calllogs.appointmentId = appointmentId;
		$scope.title="Edit CallLogs";
		$scope.options=false;
		var callLogsOriginal="";
		
		
		requestHandler.getRequest("Staff/getCallLogs.json?id="+id,"").then( function(response) {
			callLogsOriginal=angular.copy(response.data.callLogsForm);
			
		    $scope.calllogs=response.data.callLogsForm;
		    $('#timeStamp').data("DateTimePicker").setDate($scope.calllogs.timeStamp);
		    $("#calllogsModel").modal("show");
		});
		
		 $scope.isClean = function() {
			  return angular.equals(callLogsOriginal, $scope.calllogs);
		    };
		
	
	};
	

	
	
	  $scope.update=function(){
		  $("#calllogsModel").modal("hide");
		  $('.modal-backdrop').hide();
		  requestHandler.postRequest("Staff/saveUpdateCallLogs.json",$scope.calllogs).then(function (status) {
			  Flash.create("success","You have Successfully Updated!");
			  $state.reload('dashboard.Calllogs/:id');
			  $scope.getCallLogsList();
			});
		 		 
	};
	 
	
	
	$scope.Appointments={};
	$scope.Appointments.clinicId="";
	 $scope.Appointments.patientId =$stateParams.id;

	
	$scope.addAppointment=function(callLogId){
		$scope.Appointments.callLogId =callLogId;
		//getting patient details by id
		requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
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
		requestHandler.getRequest("Staff/getClinicId.json","").then( function(response) {
			
		     $scope.clinic= response.data.clinicsForms;
		 
		     });
		var ClinicId=$scope.patient.clinicId;
		requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
			
		    $scope.doctor= response.data.doctorsForm;
		  
		   });
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
		  requestHandler.postRequest("Staff/saveUpdateAppointments.json",$scope.Appointments)
			.then(function(response)
					{
				Flash.create("success","You have Successfully Added!");
				  $scope.getCallLogsList();
				});
		 
		};
		
		$scope.removeAppointment=function(id,iD)
		{
			
			if(confirm("Are you sure to cancel appointment ?")){
				 
			  requestHandler.deletePostRequest("Staff/removeAppointment.json?appointmentId=",id)
				.then(function(response)
						{
					requestHandler.getRequest("Staff/activeStatusByPatientId.json?id="+iD,"")
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
			 $scope.appointments=null;
			}
		else{
		requestHandler.getRequest("Staff/getAppointments.json?id="+id,"").then( function(response) {
				
			    $scope.appointments=response.data.appointmentsForm;
		  });
		}	
			 $("#viewAppointmentsModal").modal("show");
			 
		
		};
	
});
