var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('ShowClinicController',function($scope,requestHandler,Flash){
	$scope.noOfRows="10";
	$scope.sortKey='clinicName';

	$scope.sort = function(keyname){
		$scope.sortKey = keyname;   //set the sortKey to the param passed
	    $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	};
	    
	$scope.getClinicList=function() {
		 requestHandler.getRequest("CAdmin/getAllClinics.json","").then(function(results){
		 	 $scope.clinics= results.data.clinicsForm;
		 });
	};
	
	$scope.getClinicList();
	
	$scope.viewClinicDetails=function(clinicId) {
		 requestHandler.getRequest("CAdmin/getClinicDetails.json?clinicId="+clinicId,"").then(function(results){
		 	 $scope.clinicDetails= results.data.clinicsForm;
		 	 $("#viewClinicDetails").modal('show');
		  });
	};
	
	//enable disable clinic
	 $scope.enableOrDisbaleClinic=function(clinicId){

		 requestHandler.postRequest("CAdmin/enableOrDisableClinic.json?clinicId="+clinicId,"").then(function(response){
			 $scope.response=response.data.requestSuccess;
			 if($scope.response==true){
				 Flash.create('success', "You have Successfully Updated!");
				 $scope.getClinicList();
			 }
		 });
	 };
	
	// Delete the Clinic
	$scope.deleteClinic=function(clinicId)
	  {
		 $("#deleteClinicModal1").modal("show");
		  $scope.deleteClinicNormal=function(){
			  requestHandler.deletePostRequest("CAdmin/deleteClinic.json?clinicId=",clinicId).then(function(results){
			  $scope.value=results.data.requestSuccess;
			  
			  if($scope.value==true)
				  {
				  $("#deleteClinicModal1").modal("hide");
				  $('.modal-backdrop').hide();
				  Flash.create('success', "You have Successfully Deleted!");
		          $scope.getClinicList();
				  }
			  else
				  {
				  $("#deleteClinicModal1").modal("hide");
				  $('.modal-backdrop').hide();
				  $("#deleteClinicModal2").modal("show");
				    $scope.deleteDoctorFromClinic=function()
				    {
				    	
				    	requestHandler.postRequest("Admin/deleteDoctorsByClinic.json?clinicId="+clinicId).then(function(response){
				    		
				    		 requestHandler.deletePostRequest("CAdmin/deleteClinic.json?clinicId=",clinicId).then(function(results){
				    			 $("#deleteClinicModal2").modal("hide");
							    	$('.modal-backdrop').hide();
						          Flash.create('success', "You have Successfully Deleted!");
						          $scope.getClinicList();
				    		 });
				    		 
							});
				    };
				  
				  }
		     });
		  };
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
	
	$scope.sunError=false;$scope.monError=false;$scope.tueError=false;$scope.wedError=false;$scope.thuError=false;$scope.friError=false;
	$scope.satError=false;$scope.isError=false;
	$scope.options=true;
	
	$scope.title="Add Clinic & Doctor";
	$scope.clinic={};
	$scope.clinic.doctorsForms=[{doctorName:"",titleDr:"",titleDc:""}];
	$scope.addDoctorInput=function(){
		$scope.clinic.doctorsForms.push({doctorName:"",titleDr:"",titleDc:""});
	};
	
	$scope.removeDoctorInput=function(index){
		$scope.clinic.doctorsForms.splice(index,1);
	};
	
	$scope.clinic.clinicTimingList=[{"day":0,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0},{"day":1,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0},{"day":2,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0},{"day":3,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0},{"day":4,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0},{"day":5,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0},{"day":6,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0,"isAppointmentDay":0}];
	$scope.saveClinic=function(){
		requestHandler.postRequest("CAdmin/saveOrUpdateClinic.json",$scope.clinic).then(function(response){
			Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/clinic');
		});
	};
 
 $scope.resetDatePicker=function(workingDayId){
	
		switch(workingDayId) {
	    case 0:
	    	$scope.clinic.clinicTimingList[0].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[0].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[0].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[0].endsBreak="05:00 PM";
	    	$scope.sunError=false;
	    	break;
	    case 1:
	    	$scope.clinic.clinicTimingList[1].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[1].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[1].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[1].endsBreak="05:00 PM";
	    	$scope.monError=false;
	        break;
	    case 2:
	    	$scope.clinic.clinicTimingList[2].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[2].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[2].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[2].endsBreak="05:00 PM";
	    	$scope.tueError=false;
	    	break;
	    case 3:
	    	$scope.clinic.clinicTimingList[3].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[3].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[3].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[3].endsBreak="05:00 PM";
	    	$scope.wedError=false;
	        break;
	    case 4:
	    	$scope.clinic.clinicTimingList[4].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[4].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[4].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[4].endsBreak="05:00 PM";
	    	$scope.thuError=false;
	        break;
	    case 5:
	    	$scope.clinic.clinicTimingList[5].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[5].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[5].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[5].endsBreak="05:00 PM";
	    	$scope.friError=false;
	        break;
	    case 6:
	    	$scope.clinic.clinicTimingList[6].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[6].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[6].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[6].endsBreak="05:00 PM";
	    	$scope.satError=false;
	        break;
	    default:
	        break;
	}
	
		if($scope.sunError==false&&$scope.monError==false&&$scope.tueError==false&&$scope.wedError==false&&$scope.thuError==false&&$scope.friError==false&&
				$scope.satError==false)
			{
				$scope.isError=false;
			}
		
	};

	// Convert to 24 Hr Format For Validations
	 $scope.convertTo24Hr=function(timeForConvert){
		 var convertedTime="";
		 var hours = Number(timeForConvert.match(/^(\d+)/)[1]);
		 var minutes = Number(timeForConvert.match(/:(\d+)/)[1]);
		 var AMPM = timeForConvert.match(/\s(.*)$/)[1];
		 if(AMPM == "PM" && hours<12) hours = hours+12;
		 if(AMPM == "AM" && hours==12) hours = hours-12;
		 var sHours = hours.toString();
		 var sMinutes = minutes.toString();
		 if(hours<10) sHours = "0" + sHours;
		 if(minutes<10) sMinutes = "0" + sMinutes;
		 convertedTime=sHours + ":" + sMinutes;
		 return convertedTime;
	 };
	 
	// Validate the time
	$scope.validateTime=function(fieldArray,errorField){
		
		
		var startTime=$("#"+fieldArray[0]).val();
		var endTime=$("#"+fieldArray[1]).val();
		var breakStartTime=$("#"+fieldArray[2]).val();
		var breakEndTime=$("#"+fieldArray[3]).val();
		
		startTime=$scope.convertTo24Hr(startTime);
		endTime=$scope.convertTo24Hr(endTime);
		breakStartTime=$scope.convertTo24Hr(breakStartTime);
		breakEndTime=$scope.convertTo24Hr(breakEndTime);
		
		if(startTime<endTime && breakStartTime>startTime && breakStartTime<endTime && breakEndTime>breakStartTime && breakEndTime<endTime){
			if(errorField==0){
				$scope.sunError=false;
			}
			else if(errorField==1){
				$scope.monError=false;
			}
			else if(errorField==2){
				$scope.tueError=false;
			}
			else if(errorField==3){
				$scope.wedError=false;
			}
			else if(errorField==4){
				$scope.thuError=false;
			}
			else if(errorField==5){
				$scope.friError=false;
			}
			else if(errorField==6){
				$scope.satError=false;
			}
			
			if($scope.sunError==false&&$scope.monError==false&&$scope.tueError==false&&$scope.wedError==false&&$scope.thuError==false&&$scope.friError==false&&
					$scope.satError==false)
				{
					$scope.isError=false;
				}
			
		}else{
			if(errorField==0){
				$scope.sunError=true;
			}
			else if(errorField==1){
				$scope.monError=true;
			}
			else if(errorField==2){
				$scope.tueError=true;
			}
			else if(errorField==3){
				$scope.wedError=true;
			}
			else if(errorField==4){
				$scope.thuError=true;
			}
			else if(errorField==5){
				$scope.friError=true;
			}
			else if(errorField==6){
				$scope.satError=true;
			}
			$scope.isError=true;
		}
		
	 };
	
});

adminApp.controller('EditClinicController',function($scope,$stateParams,$location,requestHandler,Flash){
	$scope.sunError=false;$scope.monError=false;$scope.tueError=false;$scope.wedError=false;$scope.thuError=false;$scope.friError=false;
	$scope.satError=false;$scope.isError=false;
	$scope.clinic={};
	$scope.clinic.doctorsForms=[];
	$scope.options=false;
	$scope.title="Edit Clinic & Doctor";
	var clinicOriginal="";
		requestHandler.getRequest("CAdmin/getClinic.json?clinicId="+$stateParams.id,"").then(function(response){
			
			clinicOriginal=angular.copy(response.data.clinicsForm);
			$scope.clinic= response.data.clinicsForm;
			$('#sunStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[0].startTime);
			$('#sunEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[0].endTime);
			$('#sunStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[0].startsBreak);
			$('#sunEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[0].endsBreak);
			$('#monStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[1].startTime);
			$('#monEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[1].endTime);
			$('#monStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[1].startsBreak);
			$('#monEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[1].endsBreak);
			$('#tueStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[2].startTime);
			$('#tueEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[2].endTime);
			$('#tueStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[2].startsBreak);
			$('#tueEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[2].endsBreak);
			$('#wedStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[3].startTime);
			$('#wedEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[3].endTime);
			$('#wedStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[3].startsBreak);
			$('#wedEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[3].endsBreak);
			$('#thuStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[4].startTime);
			$('#thuEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[4].endTime);
			$('#thuStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[4].startsBreak);
			$('#thuEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[4].endsBreak);
			$('#friStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[5].startTime);
			$('#friEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[5].endTime);
			$('#friStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[5].startsBreak);
			$('#friEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[5].endsBreak);
			$('#satStartTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[6].startTime);
			$('#satEndTime').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[6].endTime);
			$('#satStartsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[6].startsBreak);
			$('#satEndsBreak').data("DateTimePicker").setDate($scope.clinic.clinicTimingList[6].endsBreak);
			
		});
 
		$scope.addDoctorInput=function(){
			$scope.clinic.doctorsForms.push({doctorName:"",titleDr:"",titleDc:""});
		};
		
		$scope.removeDoctorInput=function(index,removeableStatus,doctorId){
			if(doctorId!="" && doctorId!==undefined){
				if(removeableStatus==1){
					$("#confirmDeleteDoctorModal").modal("show");
					$scope.confirmDeleteDoctor=function()
				    {
						requestHandler.deletePostRequest("CAdmin/deleteDoctors.json?id=",doctorId).then(function(results){
							  $scope.value=results.data.requestSuccess;
							  });
						$scope.clinic.doctorsForms.splice(index,1);
				    };
				}
				else{
					$("#deleteDoctorModal").modal("show");
				    $scope.deleteDoctorFromPatients=function()
				    {
				    	requestHandler.postRequest("CAdmin/removeAssignedDoctors.json?id="+doctorId).then(function(response){
				    		
				    		requestHandler.deletePostRequest("CAdmin/deleteDoctors.json?id=",doctorId).then(function(results){
				    			$("#deleteDoctorModal").modal("hide");
				    			$('.modal-backdrop').hide();
				    			 $scope.clinic.doctorsForms.splice(index,1);
				    		 });
				    		});
				    };
				   
				}
			}
			else{
				$scope.clinic.doctorsForms.splice(index,1);
			}
			
		};
		
		$scope.updateClinic=function(){
			requestHandler.postRequest("CAdmin/saveOrUpdateClinic.json",$scope.clinic).then(function(response){
				Flash.create('success', "You have Successfully Updated!");
					  $location.path('dashboard/clinic');
			
			});
		};
			$scope.resetDatePicker=function(workingDayId){
				
				switch(workingDayId) {
			    case 0:
			    	$scope.clinic.clinicTimingList[0].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[0].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[0].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[0].endsBreak="05:00 PM";
			    	$scope.sunError=false;
			        break;
			    case 1:
			    	$scope.clinic.clinicTimingList[1].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[1].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[1].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[1].endsBreak="05:00 PM";
			    	$scope.monError=false;
			    	break;
			    case 2:
			    	$scope.clinic.clinicTimingList[2].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[2].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[2].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[2].endsBreak="05:00 PM";
			    	$scope.tueError=false;
			    	break;
			    case 3:
			    	$scope.clinic.clinicTimingList[3].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[3].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[3].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[3].endsBreak="05:00 PM";
			    	$scope.wedError=false;
			    	break;
			    case 4:
			    	$scope.clinic.clinicTimingList[4].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[4].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[4].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[4].endsBreak="05:00 PM";
			    	$scope.thuError=false;
			    	break;
			    case 5:
			    	$scope.clinic.clinicTimingList[5].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[5].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[5].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[5].endsBreak="05:00 PM";
			    	$scope.friError=false;
			    	break;
			    case 6:
			    	$scope.clinic.clinicTimingList[6].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[6].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[6].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[6].endsBreak="05:00 PM";
			    	$scope.satError=false;
			    	break;
			    default:
			        break;
			}
				if($scope.sunError==false&&$scope.monError==false&&$scope.tueError==false&&$scope.wedError==false&&$scope.thuError==false&&$scope.friError==false&&
						$scope.satError==false)
					{
						$scope.isError=false;
					}
		};
		
		$scope.isClean = function() {
	        return angular.equals(clinicOriginal, $scope.clinic);
	    };
	
	    // Convert to 24 Hr Format For Validations
		 $scope.convertTo24Hr=function(timeForConvert){
			 var convertedTime="";
			 var hours = Number(timeForConvert.match(/^(\d+)/)[1]);
			 var minutes = Number(timeForConvert.match(/:(\d+)/)[1]);
			 var AMPM = timeForConvert.match(/\s(.*)$/)[1];
			 if(AMPM == "PM" && hours<12) hours = hours+12;
			 if(AMPM == "AM" && hours==12) hours = hours-12;
			 var sHours = hours.toString();
			 var sMinutes = minutes.toString();
			 if(hours<10) sHours = "0" + sHours;
			 if(minutes<10) sMinutes = "0" + sMinutes;
			 convertedTime=sHours + ":" + sMinutes;
			 return convertedTime;
		 };
		 
		// Validate the Time
		$scope.validateTime=function(fieldArray,errorField){
			
			var startTime=$("#"+fieldArray[0]).val();
			var endTime=$("#"+fieldArray[1]).val();
			var breakStartTime=$("#"+fieldArray[2]).val();
			var breakEndTime=$("#"+fieldArray[3]).val();
			
			startTime=$scope.convertTo24Hr(startTime);
			endTime=$scope.convertTo24Hr(endTime);
			breakStartTime=$scope.convertTo24Hr(breakStartTime);
			breakEndTime=$scope.convertTo24Hr(breakEndTime);
			
			if(startTime<endTime && breakStartTime>startTime && breakStartTime<endTime && breakEndTime>breakStartTime && breakEndTime<endTime){
				if(errorField==0){
					$scope.sunError=false;
				}
				else if(errorField==1){
					$scope.monError=false;
				}
				else if(errorField==2){
					$scope.tueError=false;
				}
				else if(errorField==3){
					$scope.wedError=false;
				}
				else if(errorField==4){
					$scope.thuError=false;
				}
				else if(errorField==5){
					$scope.friError=false;
				}
				else if(errorField==6){
					$scope.satError=false;
				}
				
				if($scope.sunError==false&&$scope.monError==false&&$scope.tueError==false&&$scope.wedError==false&&$scope.thuError==false&&$scope.friError==false&&
						$scope.satError==false)
					{
						$scope.isError=false;
					}
			}else{
				if(errorField==0){
					$scope.sunError=true;
				}
				else if(errorField==1){
					$scope.monError=true;
				}
				else if(errorField==2){
					$scope.tueError=true;
				}
				else if(errorField==3){
					$scope.wedError=true;
				}
				else if(errorField==4){
					$scope.thuError=true;
				}
				else if(errorField==5){
					$scope.friError=true;
				}
				else if(errorField==6){
					$scope.satError=true;
				}
				$scope.isError=true;
			}
			
		 };
	    
});




adminApp.directive('higherThan',function() {
	return {
	require: "ngModel",
	scope: {
	 otherModelValue: "=higherThan"
		 },
	 
	link: function(scope, element, attributes, ngModel) {
		
		 ngModel.$validators.higherThan = function(modelValue) {
			
			  var convert="";
			var convert1="";
			 var hour=Number(modelValue.match(/^(\d+)/)[1]);
			 var hour1=Number(scope.otherModelValue.match(/^(\d+)/)[1]);
			var minute1=Number(scope.otherModelValue.match(/:(\d+)/)[1]);
			 var minute = Number(modelValue.match(/:(\d+)/)[1]);
			 var AMPM=modelValue.match(/\s(.*)$/)[1];
			 var AMPM1=scope.otherModelValue.match(/\s(.*)$/)[1];
				
			 if(AMPM1=="PM" && AMPM == "PM"){
				 if(hour<12){
				 		var hourPM=hour+12;
				 }
				 else{
					 var hourPM=hour;
			 }
			 if(hour1<12){
				 var hour1PM=hour1+12;
			 }
			 else
				 {
				 var hour1PM=hour1;
				 }
			 if(hourPM == hour1PM && minute <= minute1){
				 return false;
			 }
			 else if(hourPM<hour1PM){
				 return false;
			 }
			 else{
				 return true;
			 }
			 }
					
		 if(AMPM1=="AM" && AMPM == "AM" && hour != 12 && hour1 != 12){
				 convert1=hour1+":"+minute1;
			 
				 convert=hour+":"+minute;
				
				 if(hour1 == hour && minute1 < minute){
					 return true;
				 }
				 else if(hour>hour1)
					 {
					 return true;
					 }
				 else
					 {
					 return false;
					 }
			 }
		 if(AMPM1=="AM" && AMPM == "AM" && hour == 12 && hour1 == 12){
			 convert1=hour1+":"+minute1;
		 
			 convert=hour+":"+minute;
			
			 if(minute1 >= minute){
				 return false;
			 }
			 else
				 {
				 return true;
				 }
		 }
	
			 if(AMPM1=="AM"  && AMPM == "PM"){
				  
				 return true;		 }
			 
			 if(AMPM1=="PM" && AMPM == "AM"){
				  
				 return true;		 }
			 
			 if(AMPM1=="AM" && hour1 != 12){
				  
				 convert1=hour1+":"+minute1;
			 
			 }
			 if (AMPM == "PM"){
				 if(hour<12){
				 		
				 var totalHour = hour + 12;
				 }
				 else
					 {
					 var totalHour=hour;
					 }
					 convert=totalHour+":"+minute;
			  }		
			

			  if(AMPM1=="PM"){
				  if(hour1<12){
				 		var totalHour1 = hour1 + 12;
				  }
				  else
					  {
					  var totalHour1=hour1;
					  }
					  convert1=totalHour1+":"+minute1;
			  }	
			
			 if (AMPM == "AM"){ 
				
				 convert=hour+":"+minute;
		  	 }
			 
			 if (AMPM == "AM" && hour == 12){ 
				 var totalHour = hour - 12;
				 convert=totalHour+":"+minute;
		  	 }
		      if(AMPM1=="AM" && hour1 != 12){
				  
				 convert1=hour1+":"+minute1;
			  }
			 
			 if(AMPM1=="AM" && hour1 == 12){
				 var totalHour1 = hour1 - 12;
				 convert1=totalHour1+":"+minute1;
			  }
			  
		 
	  return convert1<convert && convert>convert1;
			 };
				
	scope.$watch("otherModelValue", function() {
	ngModel.$validate();
	});
	}
	};
	});






