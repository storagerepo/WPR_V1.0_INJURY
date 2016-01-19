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
	$scope.clinic.clinicTimingList=[{"day":0,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0},{"day":1,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0},{"day":2,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0},{"day":3,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0},{"day":4,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0},{"day":5,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0},{"day":6,"startTime":"09:00 AM","endTime":"08:00 PM","startsBreak":"02:00 PM","endsBreak":"05:00 PM","isWorkingDay":0}];
	$scope.saveClinic=function(){
		requestHandler.postRequest("Staff/saveOrUpdateClinic.json",$scope.clinic).then(function(response){
			Flash.create('success', "You have Successfully Added!");
				  $location.path('dashboard/clinic');
		});
	};
	$scope.lowerThanStartBreak=function(i){
		
		  var convert="";
			var convert1="";
			 var hour=Number($scope.clinic.clinicTimingList[i].startsBreak.match(/^(\d+)/)[1]);
			 var hour1=Number($scope.clinic.clinicTimingList[i].endTime.match(/^(\d+)/)[1]);
			var minute1=Number($scope.clinic.clinicTimingList[i].endTime.match(/:(\d+)/)[1]);
			 var minute = Number($scope.clinic.clinicTimingList[i].startsBreak.match(/:(\d+)/)[1]);
			 var AMPM=$scope.clinic.clinicTimingList[i].startsBreak.match(/\s(.*)$/)[1];
	 		 var AMPMendTime=$scope.clinic.clinicTimingList[i].endTime.match(/\s(.*)$/)[1];
	 		if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
	 		convert1=hour1+":"+minute1;
	 		var totalHour=hour+12;
	 		convert=totalHour+":"+minute;
	 if(totalHour>hour1){
		 if(i==0){
			 $scope.error0=" ";
			 }
			 if(i==1){
			 $scope.error1=" ";
			 }
			 if(i==2){
			 $scope.error2=" ";
			 }
			 if(i==3){
			 $scope.error3=" ";
			 }
			 if(i==4){
				 $scope.error4=" ";
			 }
			 if(i==5){
			 $scope.error5=" ";
			 }
			 if(i==6){
				 $scope.error6=" ";
			 }
			}
	 else
		 {
		 if(i==0){
			 $scope.error0="Starts Break Should be smaller";
			 }
			 if(i==1){
			 $scope.error1="Starts Break Should be smaller";
			 }
			 if(i==2){
			 $scope.error2="Starts Break Should be smaller";
			 }
			 if(i==3){
			 $scope.error3="Starts Break Should be smaller";
			 }
			 if(i==4){
				 $scope.error4="Starts Break Should be smaller";
			 }
			 if(i==5){
			 $scope.error5="Starts Break Should be smaller";
			 }
			 if(i==6){
				 $scope.error6="Starts Break Should be smaller";
			 }
		
		 }
	 		}
			 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
				 var totalHour1 = hour1 + 12;
				 convert1=totalHour1+":"+minute1;
				 var totalHour = hour + 12;
				 convert=totalHour+":"+minute;
				 if(totalHour<totalHour1){
					 if(i==0){
						 $scope.error0=" ";
						 }
						 if(i==1){
						 $scope.error1=" ";
						 }
						 if(i==2){
						 $scope.error2=" ";
						 }
						 if(i==3){
						 $scope.error3=" ";
						 }
						 if(i==4){
							 $scope.error4=" ";
						 }
						 if(i==5){
						 $scope.error5=" ";
						 }
						 if(i==6){
							 $scope.error6=" ";
						 }
						 }
				 else if(totalHour == totalHour1 && minute >= minute1){
					 if(i==0){
						 $scope.error0="Starts Break Should be smaller";
						 }
						 if(i==1){
						 $scope.error1="Starts Break Should be smaller";
						 }
						 if(i==2){
						 $scope.error2="Starts Break Should be smaller";
						 }
						 if(i==3){
						 $scope.error3="Starts Break Should be smaller";
						 }
						 if(i==4){
							 $scope.error4="Starts Break Should be smaller";
						 }
						 if(i==5){
						 $scope.error5="Starts Break Should be smaller";
						 }
						 if(i==6){
							 $scope.error6="Starts Break Should be smaller";
						 }
					  }
				 else if(totalHour == totalHour1 && minute<minute1){
					 if(i==0){
						 $scope.error0=" ";
						 }
						 if(i==1){
						 $scope.error1=" ";
						 }
						 if(i==2){
						 $scope.error2=" ";
						 }
						 if(i==3){
						 $scope.error3=" ";
						 }
						 if(i==4){
							 $scope.error4=" ";
						 }
						 if(i==5){
						 $scope.error5=" ";
						 }
						 if(i==6){
							 $scope.error6=" ";
						 }
					  }
				
					 else
					 {
					 if(i==0){
						 $scope.error0="Starts Break Should be smaller";
						 }
						 if(i==1){
						 $scope.error1="Starts Break Should be smaller";
						 }
						 if(i==2){
						 $scope.error2="Starts Break Should be smaller";
						 }
						 if(i==3){
						 $scope.error3="Starts Break Should be smaller";
						 }
						 if(i==4){
							 $scope.error4="Starts Break Should be smaller";
						 }
						 if(i==5){
						 $scope.error5="Starts Break Should be smaller";
						 }
						 if(i==6){
							 $scope.error6="Starts Break Should be smaller";
						 }
					 		 }
					  
			  }	
			 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
				 var totalHour1 = hour1 + 12;
				 convert1=totalHour1+":"+minute1;
				 var totalHour = hour + 12;
				 convert=totalHour+":"+minute;
				 if(totalHour<totalHour1){
					 if(i==0){
						 $scope.error0=" ";
						 }
						 if(i==1){
						 $scope.error1=" ";
						 }
						 if(i==2){
						 $scope.error2=" ";
						 }
						 if(i==3){
						 $scope.error3=" ";
						 }
						 if(i==4){
							 $scope.error4=" ";
						 }
						 if(i==5){
						 $scope.error5=" ";
						 }
						 if(i==6){
							 $scope.error6=" ";
						 }
						 }
				 else if(totalHour == totalHour1 && minute >= minute1){
					 if(i==0){
						 $scope.error0="Starts Break Should be smaller";
						 }
						 if(i==1){
						 $scope.error1="Starts Break Should be smaller";
						 }
						 if(i==2){
						 $scope.error2="Starts Break Should be smaller";
						 }
						 if(i==3){
						 $scope.error3="Starts Break Should be smaller";
						 }
						 if(i==4){
							 $scope.error4="Starts Break Should be smaller";
						 }
						 if(i==5){
						 $scope.error5="Starts Break Should be smaller";
						 }
						 if(i==6){
							 $scope.error6="Starts Break Should be smaller";
						 }
					  }
				 else if(totalHour == totalHour1 && minute<minute1){
					 if(i==0){
						 $scope.error0=" ";
						 }
						 if(i==1){
						 $scope.error1=" ";
						 }
						 if(i==2){
						 $scope.error2=" ";
						 }
						 if(i==3){
						 $scope.error3=" ";
						 }
						 if(i==4){
							 $scope.error4=" ";
						 }
						 if(i==5){
						 $scope.error5=" ";
						 }
						 if(i==6){
							 $scope.error6=" ";
						 }
					  }
				
					 else
					 {
					 if(i==0){
						 $scope.error0="Starts Break Should be smaller";
						 }
						 if(i==1){
						 $scope.error1="Starts Break Should be smaller";
						 }
						 if(i==2){
						 $scope.error2="Starts Break Should be smaller";
						 }
						 if(i==3){
						 $scope.error3="Starts Break Should be smaller";
						 }
						 if(i==4){
							 $scope.error4="Starts Break Should be smaller";
						 }
						 if(i==5){
						 $scope.error5="Starts Break Should be smaller";
						 }
						 if(i==6){
							 $scope.error6="Starts Break Should be smaller";
						 }
					 		 }
					  
			  }	

			 if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
			 	 if(i==0){
			 		 $scope.error0="Starts Break Should be smaller";
			 		 }
			 		 if(i==1){
			 		 $scope.error1="Starts Break Should be smaller";
			 		 }
			 		 if(i==2){
			 		 $scope.error2="Starts Break Should be smaller";
			 		 }
			 		 if(i==3){
			 		 $scope.error3="Starts Break Should be smaller";
			 		 }
			 		 if(i==4){
			 			 $scope.error4="Starts Break Should be smaller";
			 		 }
			 		 if(i==5){
			 		 $scope.error5="Starts Break Should be smaller";
			 		 }
			 		 if(i==6){
			 			 $scope.error6="Starts Break Should be smaller";
			 		 }
			 }

			 if(AMPMendTime=="PM" && AMPM == "AM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
if(i==0){
	 $scope.error0="Starts Break Should be smaller";
	 }
	 if(i==1){
	 $scope.error1="Starts Break Should be smaller";
	 }
	 if(i==2){
	 $scope.error2="Starts Break Should be smaller";
	 }
	 if(i==3){
	 $scope.error3="Starts Break Should be smaller";
	 }
	 if(i==4){
		 $scope.error4="Starts Break Should be smaller";
	 }
	 if(i==5){
	 $scope.error5="Starts Break Should be smaller";
	 }
	 if(i==6){
		 $scope.error6="Starts Break Should be smaller";
	 }
}
				 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour !=12){
				 
					 convert1=hour1+":"+minute1;
			 
				 convert=hour+":"+minute;
				  if(hour<hour1){
					 if(i==0){
						 $scope.error0=" ";
						 }
						 if(i==1){
						 $scope.error1=" ";
						 }
						 if(i==2){
						 $scope.error2=" ";
						 }
						 if(i==3){
						 $scope.error3=" ";
						 }
						 if(i==4){
							 $scope.error4=" ";
						 }
						 if(i==5){
						 $scope.error5=" ";
						 }
						 if(i==6){
							 $scope.error6=" ";
						 }
						 }
				 else if(hour == hour1 && minute >= minute1){
					 if(i==0){
						 $scope.error0="Starts Break Should be smaller";
						 }
						 if(i==1){
						 $scope.error1="Starts Break Should be smaller";
						 }
						 if(i==2){
						 $scope.error2="Starts Break Should be smaller";
						 }
						 if(i==3){
						 $scope.error3="Starts Break Should be smaller";
						 }
						 if(i==4){
							 $scope.error4="Starts Break Should be smaller";
						 }
						 if(i==5){
						 $scope.error5="Starts Break Should be smaller";
						 }
						 if(i==6){
							 $scope.error6="Starts Break Should be smaller";
						 }
					  }
				 else if(hour == hour1 && minute<minute1){
					 if(i==0){
						 $scope.error0=" ";
						 }
						 if(i==1){
						 $scope.error1=" ";
						 }
						 if(i==2){
						 $scope.error2=" ";
						 }
						 if(i==3){
						 $scope.error3=" ";
						 }
						 if(i==4){
							 $scope.error4=" ";
						 }
						 if(i==5){
						 $scope.error5=" ";
						 }
						 if(i==6){
							 $scope.error6=" ";
						 }
					  }
				
				 else
					 {
					 if(i==0){
						 $scope.error0="Starts Break Should be smaller";
						 }
						 if(i==1){
						 $scope.error1="Starts Break Should be smaller";
						 }
						 if(i==2){
						 $scope.error2="Starts Break Should be smaller";
						 }
						 if(i==3){
						 $scope.error3="Starts Break Should be smaller";
						 }
						 if(i==4){
							 $scope.error4="Starts Break Should be smaller";
						 }
						 if(i==5){
						 $scope.error5="Starts Break Should be smaller";
						 }
						 if(i==6){
							 $scope.error6="Starts Break Should be smaller";
						 }
					 		 }
				 }
				 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour == 12){
					 var totalHour1 = hour1 - 12;
					 	convert1=totalHour1+":"+minute1;
					 	 var totalHour = hour - 12;
						 	convert=totalHour+":"+minute;
						 	if(totalHour<totalHour1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
									 }
						 	 else if(totalHour == totalHour1 && minute >= minute1){
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								  }
							 else if(totalHour == totalHour1 && minute<minute1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
								  }
							
									 else
								 {
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								 		 }
													 }
				 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour ==12){
					 convert1=hour1+":"+minute1;
					 var totalHour = hour - 12;
					 	convert=totalHour+":"+minute;
					 	if(totalHour<hour1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
								 }
					 	 else if(totalHour == hour1 && minute >= minute1){
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							  }
						 else if(totalHour == hour1 && minute<minute1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
							  }
						
						 else
							 {
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							 		 }
							
				 }		
				 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour != 12){
					 var totalHour1 = hour1 - 12;
					 	convert1=totalHour1+":"+minute1;
					 	 convert=hour+":"+minute;
					 	if(hour<totalHour1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
								 }
					 	 else if(hour == totalHour1 && minute >= minute1){
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							  }
						 else if(hour == totalHour1 && minute<minute1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
							  }
						
							 else
							 {
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							 		 }
							}
				 if(AMPMendTime=="PM" && AMPM == "AM" && hour != 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM" ){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 convert=hour+":"+minute;
					 if(hour<totalHour1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
							 }
					 else if(hour == totalHour1 && minute >= minute1){
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						  }
					 else if(hour == totalHour1 && minute<minute1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
						  }
					
					 else
						 {
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						 		 }
					}
				 if(AMPMendTime=="PM" && AMPM == "AM" && hour == 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 var totalHour = hour - 12;
					 	convert=totalHour+":"+minute;
					 	if(totalHour<totalHour1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
								 }
					 	 else if(totalHour == totalHour1 && minute >= minute1){
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							  }
						 else if(totalHour == totalHour1 && minute<minute1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
							  }
						
						 else
							 {
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							 		 }
									
	}
	}
	$scope.lowerThanEndBreak=function(i){
		$scope.errors0="";
		$scope.errors1="";
		$scope.errors2="";
		$scope.errors3="";
		$scope.errors4="";
		$scope.errors5="";
		$scope.errors6="";
		  var convert="";
			var convert1="";
			 var hour=Number($scope.clinic.clinicTimingList[i].endsBreak.match(/^(\d+)/)[1]);
			 var hour1=Number($scope.clinic.clinicTimingList[i].endTime.match(/^(\d+)/)[1]);
			var minute1=Number($scope.clinic.clinicTimingList[i].endTime.match(/:(\d+)/)[1]);
			 var minute = Number($scope.clinic.clinicTimingList[i].endsBreak.match(/:(\d+)/)[1]);
			 var AMPM=$scope.clinic.clinicTimingList[i].endsBreak.match(/\s(.*)$/)[1];
	 		 var AMPMendTime=$scope.clinic.clinicTimingList[i].endTime.match(/\s(.*)$/)[1];
	 		if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
	 			if(i==0){
					 $scope.errors0="Ends Break Should be smaller";
					 }
					 if(i==1){
					 $scope.errors1="Ends Break Should be smaller";
					 }
					 if(i==2){
					 $scope.errors2="Ends Break Should be smaller";
					 }
					 if(i==3){
					 $scope.errors3="Ends Break Should be smaller";
					 }
					 if(i==4){
						 $scope.errors4="Ends Break Should be smaller";
					 }
					 if(i==5){
					 $scope.errors5="Ends Break Should be smaller";
					 }
					 if(i==6){
						 $scope.errors6="Ends Break Should be smaller";
					 }
				 }

	 		 if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
		 		convert1=hour1+":"+minute1;
		 		var totalHour=hour+12;
		 		convert=totalHour+":"+minute;
		 if(totalHour>hour1){
			 if(i==0){
				 $scope.errors0=" ";
				 }
				 if(i==1){
				 $scope.errors1=" ";
				 }
				 if(i==2){
				 $scope.errors2=" ";
				 }
				 if(i==3){
				 $scope.errors3=" ";
				 }
				 if(i==4){
					 $scope.errors4=" ";
				 }
				 if(i==5){
				 $scope.errors5=" ";
				 }
				 if(i==6){
					 $scope.errors6=" ";
				 }
		}
		 else
			 {
			 if(i==0){
				 $scope.errors0="Ends Break Should be smaller";
				 }
				 if(i==1){
				 $scope.errors1="Ends Break Should be smaller";
				 }
				 if(i==2){
				 $scope.errors2="Ends Break Should be smaller";
				 }
				 if(i==3){
				 $scope.errors3="Ends Break Should be smaller";
				 }
				 if(i==4){
					 $scope.errors4="Ends Break Should be smaller";
				 }
				 if(i==5){
				 $scope.errors5="Ends Break Should be smaller";
				 }
				 if(i==6){
					 $scope.errors6="Ends Break Should be smaller";
				 }
	
	 }
		 		}
			
	 		 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
				 var totalHour1 = hour1 + 12;
				 convert1=totalHour1+":"+minute1;
				 var totalHour = hour + 12;
				 convert=totalHour+":"+minute;
				 if(totalHour<totalHour1){
					 if(i==0){
						 $scope.errors0=" ";
						 }
						 if(i==1){
						 $scope.errors1=" ";
						 }
						 if(i==2){
						 $scope.errors2=" ";
						 }
						 if(i==3){
						 $scope.errors3=" ";
						 }
						 if(i==4){
							 $scope.errors4=" ";
						 }
						 if(i==5){
						 $scope.errors5=" ";
						 }
						 if(i==6){
							 $scope.errors6=" ";
						 }
						 }
				 else if(totalHour == totalHour1 && minute >= minute1){
					 if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
			
					  }
				 else if(totalHour == totalHour1 && minute<minute1){
					 if(i==0){
						 $scope.errors0=" ";
						 }
						 if(i==1){
						 $scope.errors1=" ";
						 }
						 if(i==2){
						 $scope.errors2=" ";
						 }
						 if(i==3){
						 $scope.errors3=" ";
						 }
						 if(i==4){
							 $scope.errors4=" ";
						 }
						 if(i==5){
						 $scope.errors5=" ";
						 }
						 if(i==6){
							 $scope.errors6=" ";
						 }
					  }
				
					 else
					 {
					 if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
					 		 }
					  
			  }
			 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
				 var totalHour1 = hour1 + 12;
				 convert1=totalHour1+":"+minute1;
				 var totalHour = hour + 12;
				 convert=totalHour+":"+minute;
				 if(totalHour<totalHour1){
					 if(i==0){
						 $scope.errors0=" ";
						 }
						 if(i==1){
						 $scope.errors1=" ";
						 }
						 if(i==2){
						 $scope.errors2=" ";
						 }
						 if(i==3){
						 $scope.errors3=" ";
						 }
						 if(i==4){
							 $scope.errors4=" ";
						 }
						 if(i==5){
						 $scope.errors5=" ";
						 }
						 if(i==6){
							 $scope.errors6=" ";
						 }
						 }
				 else if(totalHour == totalHour1 && minute >= minute1){
					 if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
				
					  }
				 else if(totalHour == totalHour1 && minute<minute1){
					 if(i==0){
						 $scope.errors0=" ";
						 }
						 if(i==1){
						 $scope.errors1=" ";
						 }
						 if(i==2){
						 $scope.errors2=" ";
						 }
						 if(i==3){
						 $scope.errors3=" ";
						 }
						 if(i==4){
							 $scope.errors4=" ";
						 }
						 if(i==5){
						 $scope.errors5=" ";
						 }
						 if(i==6){
							 $scope.errors6=" ";
						 }
					  }
				
					 else
					 {
					 if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
					 		 }
					  
			  }	
	 		 if(AMPMendTime=="PM" && AMPM == "AM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
	 			
	 			 if(i==0){
	 				 $scope.errors0="Ends Break Should be smaller";
	 				 }
	 				 if(i==1){
	 				 $scope.errors1="Ends Break Should be smaller";
	 				 }
	 				 if(i==2){
	 				 $scope.errors2="Ends Break Should be smaller";
	 				 }
	 				 if(i==3){
	 				 $scope.errors3="Ends Break Should be smaller";
	 				 }
	 				 if(i==4){
	 					 $scope.errors4="Ends Break Should be smaller";
	 				 }
	 				 if(i==5){
	 				 $scope.errors5="Ends Break Should be smaller";
	 				 }
	 				 if(i==6){
	 					 $scope.errors6="Ends Break Should be smaller";
	 				 }
	 		}
				 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour !=12){
				 
					 convert1=hour1+":"+minute1;
			 
				 convert=hour+":"+minute;
				  if(hour<hour1){
					 if(i==0){
						 $scope.errors0=" ";
						 }
						 if(i==1){
						 $scope.errors1=" ";
						 }
						 if(i==2){
						 $scope.errors2=" ";
						 }
						 if(i==3){
						 $scope.errors3=" ";
						 }
						 if(i==4){
							 $scope.errors4=" ";
						 }
						 if(i==5){
						 $scope.errors5=" ";
						 }
						 if(i==6){
							 $scope.errors6=" ";
						 }
						 }
				 else if(hour == hour1 && minute >= minute1){
					 if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
					  }
				 else if(hour == hour1 && minute<minute1){
					 if(i==0){
						 $scope.errors0=" ";
						 }
						 if(i==1){
						 $scope.errors1=" ";
						 }
						 if(i==2){
						 $scope.errors2=" ";
						 }
						 if(i==3){
						 $scope.errors3=" ";
						 }
						 if(i==4){
							 $scope.errors4=" ";
						 }
						 if(i==5){
						 $scope.errors5=" ";
						 }
						 if(i==6){
							 $scope.errors6=" ";
						 }
					  }
				
					 else
					 {
					 if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
						 }
					  }	
				 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour == 12){
					 var totalHour1 = hour1 - 12;
					 	convert1=totalHour1+":"+minute1;
					 	 var totalHour = hour - 12;
						 	convert=totalHour+":"+minute;
							 if(totalHour<totalHour1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
									 }
							 else if(totalHour == totalHour1 && minute >= minute1){
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
								  }
							 else if(totalHour == totalHour1 && minute<minute1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
								  }
										 else
								 {
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
									 }											 }
				 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour ==12){
					 convert1=hour1+":"+minute1;
					 var totalHour = hour - 12;
					 	convert=totalHour+":"+minute;
						 if(totalHour<hour1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
								 }
						 else if(totalHour == hour1 && minute >= minute1){
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
							  }
						 else if(totalHour == hour1 && minute<minute1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
							  }
						 else
							 {
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
								 }
						 }		
				 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour != 12){
					 var totalHour1 = hour1 - 12;
					 	convert1=totalHour1+":"+minute1;
					 	 convert=hour+":"+minute;
						 if(hour<totalHour1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
								 }
						 else if(hour == totalHour1 && minute >= minute1){
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
							  }
						 else if(hour == totalHour1 && minute<minute1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
							  }
							 else
							 {
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
								 }
						 }
				 if(AMPMendTime=="PM" && AMPM == "AM" && hour != 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 convert=hour+":"+minute;
					 if(hour<totalHour1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
							 }
					 else if(hour == totalHour1 && minute >= minute1){
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
						  }
					 else if(hour == totalHour1 && minute<minute1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
						  }
					 else
						 {
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
							 }
					 }

				 if(AMPMendTime=="PM" && AMPM == "AM" && hour == 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 var totalHour = hour - 12;
					 	convert=totalHour+":"+minute;
						 if(totalHour<totalHour1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
								 }
						 else if(totalHour == totalHour1 && minute >= minute1){
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
							  }
						 else if(totalHour == totalHour1 && minute<minute1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
							  }
						 else
							 {
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
								 
							 }
						 }		
	}	
			  
 $scope.resetDatePicker=function(workingDayId){
		switch(workingDayId) {
	    case 0:
	    	$scope.clinic.clinicTimingList[0].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[0].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[0].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[0].endsBreak="05:00 PM";
	    	break;
	    case 1:
	    	$scope.clinic.clinicTimingList[1].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[1].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[1].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[1].endsBreak="05:00 PM";

	        break;
	    case 2:
	    	$scope.clinic.clinicTimingList[2].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[2].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[2].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[2].endsBreak="05:00 PM";

	        break;
	    case 3:
	    	$scope.clinic.clinicTimingList[3].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[3].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[3].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[3].endsBreak="05:00 PM";

	        break;
	    case 4:
	    	$scope.clinic.clinicTimingList[4].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[4].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[4].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[4].endsBreak="05:00 PM";

	        break;
	    case 5:
	    	$scope.clinic.clinicTimingList[5].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[5].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[5].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[5].endsBreak="05:00 PM";

	        break;
	    case 6:
	    	$scope.clinic.clinicTimingList[6].startTime="09:00 AM";
	    	$scope.clinic.clinicTimingList[6].endTime="08:00 PM";
	    	$scope.clinic.clinicTimingList[6].startsBreak="02:00 PM";
	    	$scope.clinic.clinicTimingList[6].endsBreak="05:00 PM";

	        break;
	    default:
	        break;
	}
		
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
 
		$scope.updateClinic=function(){
			requestHandler.postRequest("Staff/saveOrUpdateClinic.json",$scope.clinic).then(function(response){
				Flash.create('success', "You have Successfully Updated!");
					  $location.path('dashboard/clinic');
			
			});
		};
		$scope.lowerThanStartBreak=function(i){
			
			  var convert="";
				var convert1="";
				 var hour=Number($scope.clinic.clinicTimingList[i].startsBreak.match(/^(\d+)/)[1]);
				 var hour1=Number($scope.clinic.clinicTimingList[i].endTime.match(/^(\d+)/)[1]);
				var minute1=Number($scope.clinic.clinicTimingList[i].endTime.match(/:(\d+)/)[1]);
				 var minute = Number($scope.clinic.clinicTimingList[i].startsBreak.match(/:(\d+)/)[1]);
				 var AMPM=$scope.clinic.clinicTimingList[i].startsBreak.match(/\s(.*)$/)[1];
		 		 var AMPMendTime=$scope.clinic.clinicTimingList[i].endTime.match(/\s(.*)$/)[1];
		 		if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
		 		convert1=hour1+":"+minute1;
		 		var totalHour=hour+12;
		 		convert=totalHour+":"+minute;
		 if(totalHour>hour1){
			 if(i==0){
				 $scope.error0=" ";
				 }
				 if(i==1){
				 $scope.error1=" ";
				 }
				 if(i==2){
				 $scope.error2=" ";
				 }
				 if(i==3){
				 $scope.error3=" ";
				 }
				 if(i==4){
					 $scope.error4=" ";
				 }
				 if(i==5){
				 $scope.error5=" ";
				 }
				 if(i==6){
					 $scope.error6=" ";
				 }
				}
		 else
			 {
			 if(i==0){
				 $scope.error0="Starts Break Should be smaller";
				 }
				 if(i==1){
				 $scope.error1="Starts Break Should be smaller";
				 }
				 if(i==2){
				 $scope.error2="Starts Break Should be smaller";
				 }
				 if(i==3){
				 $scope.error3="Starts Break Should be smaller";
				 }
				 if(i==4){
					 $scope.error4="Starts Break Should be smaller";
				 }
				 if(i==5){
				 $scope.error5="Starts Break Should be smaller";
				 }
				 if(i==6){
					 $scope.error6="Starts Break Should be smaller";
				 }
			
			 }
		 		}
				 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 var totalHour = hour + 12;
					 convert=totalHour+":"+minute;
					 if(totalHour<totalHour1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
							 }
					 else if(totalHour == totalHour1 && minute >= minute1){
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						  }
					 else if(totalHour == totalHour1 && minute<minute1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
						  }
					
						 else
						 {
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						 		 }
						  
				  }	
				 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 var totalHour = hour + 12;
					 convert=totalHour+":"+minute;
					 if(totalHour<totalHour1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
							 }
					 else if(totalHour == totalHour1 && minute >= minute1){
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						  }
					 else if(totalHour == totalHour1 && minute<minute1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
						  }
					
						 else
						 {
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						 		 }
						  
				  }	

				 if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
				 	 if(i==0){
				 		 $scope.error0="Starts Break Should be smaller";
				 		 }
				 		 if(i==1){
				 		 $scope.error1="Starts Break Should be smaller";
				 		 }
				 		 if(i==2){
				 		 $scope.error2="Starts Break Should be smaller";
				 		 }
				 		 if(i==3){
				 		 $scope.error3="Starts Break Should be smaller";
				 		 }
				 		 if(i==4){
				 			 $scope.error4="Starts Break Should be smaller";
				 		 }
				 		 if(i==5){
				 		 $scope.error5="Starts Break Should be smaller";
				 		 }
				 		 if(i==6){
				 			 $scope.error6="Starts Break Should be smaller";
				 		 }
				 }

				 if(AMPMendTime=="PM" && AMPM == "AM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
	 if(i==0){
		 $scope.error0="Starts Break Should be smaller";
		 }
		 if(i==1){
		 $scope.error1="Starts Break Should be smaller";
		 }
		 if(i==2){
		 $scope.error2="Starts Break Should be smaller";
		 }
		 if(i==3){
		 $scope.error3="Starts Break Should be smaller";
		 }
		 if(i==4){
			 $scope.error4="Starts Break Should be smaller";
		 }
		 if(i==5){
		 $scope.error5="Starts Break Should be smaller";
		 }
		 if(i==6){
			 $scope.error6="Starts Break Should be smaller";
		 }
}
					 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour !=12){
					 
						 convert1=hour1+":"+minute1;
				 
					 convert=hour+":"+minute;
					  if(hour<hour1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
							 }
					 else if(hour == hour1 && minute >= minute1){
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						  }
					 else if(hour == hour1 && minute<minute1){
						 if(i==0){
							 $scope.error0=" ";
							 }
							 if(i==1){
							 $scope.error1=" ";
							 }
							 if(i==2){
							 $scope.error2=" ";
							 }
							 if(i==3){
							 $scope.error3=" ";
							 }
							 if(i==4){
								 $scope.error4=" ";
							 }
							 if(i==5){
							 $scope.error5=" ";
							 }
							 if(i==6){
								 $scope.error6=" ";
							 }
						  }
					
					 else
						 {
						 if(i==0){
							 $scope.error0="Starts Break Should be smaller";
							 }
							 if(i==1){
							 $scope.error1="Starts Break Should be smaller";
							 }
							 if(i==2){
							 $scope.error2="Starts Break Should be smaller";
							 }
							 if(i==3){
							 $scope.error3="Starts Break Should be smaller";
							 }
							 if(i==4){
								 $scope.error4="Starts Break Should be smaller";
							 }
							 if(i==5){
							 $scope.error5="Starts Break Should be smaller";
							 }
							 if(i==6){
								 $scope.error6="Starts Break Should be smaller";
							 }
						 		 }
					 }
					 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour == 12){
						 var totalHour1 = hour1 - 12;
						 	convert1=totalHour1+":"+minute1;
						 	 var totalHour = hour - 12;
							 	convert=totalHour+":"+minute;
							 	if(totalHour<totalHour1){
									 if(i==0){
										 $scope.error0=" ";
										 }
										 if(i==1){
										 $scope.error1=" ";
										 }
										 if(i==2){
										 $scope.error2=" ";
										 }
										 if(i==3){
										 $scope.error3=" ";
										 }
										 if(i==4){
											 $scope.error4=" ";
										 }
										 if(i==5){
										 $scope.error5=" ";
										 }
										 if(i==6){
											 $scope.error6=" ";
										 }
										 }
							 	 else if(totalHour == totalHour1 && minute >= minute1){
									 if(i==0){
										 $scope.error0="Starts Break Should be smaller";
										 }
										 if(i==1){
										 $scope.error1="Starts Break Should be smaller";
										 }
										 if(i==2){
										 $scope.error2="Starts Break Should be smaller";
										 }
										 if(i==3){
										 $scope.error3="Starts Break Should be smaller";
										 }
										 if(i==4){
											 $scope.error4="Starts Break Should be smaller";
										 }
										 if(i==5){
										 $scope.error5="Starts Break Should be smaller";
										 }
										 if(i==6){
											 $scope.error6="Starts Break Should be smaller";
										 }
									  }
								 else if(totalHour == totalHour1 && minute<minute1){
									 if(i==0){
										 $scope.error0=" ";
										 }
										 if(i==1){
										 $scope.error1=" ";
										 }
										 if(i==2){
										 $scope.error2=" ";
										 }
										 if(i==3){
										 $scope.error3=" ";
										 }
										 if(i==4){
											 $scope.error4=" ";
										 }
										 if(i==5){
										 $scope.error5=" ";
										 }
										 if(i==6){
											 $scope.error6=" ";
										 }
									  }
								
										 else
									 {
									 if(i==0){
										 $scope.error0="Starts Break Should be smaller";
										 }
										 if(i==1){
										 $scope.error1="Starts Break Should be smaller";
										 }
										 if(i==2){
										 $scope.error2="Starts Break Should be smaller";
										 }
										 if(i==3){
										 $scope.error3="Starts Break Should be smaller";
										 }
										 if(i==4){
											 $scope.error4="Starts Break Should be smaller";
										 }
										 if(i==5){
										 $scope.error5="Starts Break Should be smaller";
										 }
										 if(i==6){
											 $scope.error6="Starts Break Should be smaller";
										 }
									 		 }
														 }
					 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour ==12){
						 convert1=hour1+":"+minute1;
						 var totalHour = hour - 12;
						 	convert=totalHour+":"+minute;
						 	if(totalHour<hour1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
									 }
						 	 else if(totalHour == hour1 && minute >= minute1){
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								  }
							 else if(totalHour == hour1 && minute<minute1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
								  }
							
							 else
								 {
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								 		 }
								
					 }		
					 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour != 12){
						 var totalHour1 = hour1 - 12;
						 	convert1=totalHour1+":"+minute1;
						 	 convert=hour+":"+minute;
						 	if(hour<totalHour1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
									 }
						 	 else if(hour == totalHour1 && minute >= minute1){
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								  }
							 else if(hour == totalHour1 && minute<minute1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
								  }
							
								 else
								 {
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								 		 }
								}
					 if(AMPMendTime=="PM" && AMPM == "AM" && hour != 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM" ){
						 var totalHour1 = hour1 + 12;
						 convert1=totalHour1+":"+minute1;
						 convert=hour+":"+minute;
						 if(hour<totalHour1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
								 }
						 else if(hour == totalHour1 && minute >= minute1){
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							  }
						 else if(hour == totalHour1 && minute<minute1){
							 if(i==0){
								 $scope.error0=" ";
								 }
								 if(i==1){
								 $scope.error1=" ";
								 }
								 if(i==2){
								 $scope.error2=" ";
								 }
								 if(i==3){
								 $scope.error3=" ";
								 }
								 if(i==4){
									 $scope.error4=" ";
								 }
								 if(i==5){
								 $scope.error5=" ";
								 }
								 if(i==6){
									 $scope.error6=" ";
								 }
							  }
						
						 else
							 {
							 if(i==0){
								 $scope.error0="Starts Break Should be smaller";
								 }
								 if(i==1){
								 $scope.error1="Starts Break Should be smaller";
								 }
								 if(i==2){
								 $scope.error2="Starts Break Should be smaller";
								 }
								 if(i==3){
								 $scope.error3="Starts Break Should be smaller";
								 }
								 if(i==4){
									 $scope.error4="Starts Break Should be smaller";
								 }
								 if(i==5){
								 $scope.error5="Starts Break Should be smaller";
								 }
								 if(i==6){
									 $scope.error6="Starts Break Should be smaller";
								 }
							 		 }
						}
					 if(AMPMendTime=="PM" && AMPM == "AM" && hour == 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
						 var totalHour1 = hour1 + 12;
						 convert1=totalHour1+":"+minute1;
						 var totalHour = hour - 12;
						 	convert=totalHour+":"+minute;
						 	if(totalHour<totalHour1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
									 }
						 	 else if(totalHour == totalHour1 && minute >= minute1){
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								  }
							 else if(totalHour == totalHour1 && minute<minute1){
								 if(i==0){
									 $scope.error0=" ";
									 }
									 if(i==1){
									 $scope.error1=" ";
									 }
									 if(i==2){
									 $scope.error2=" ";
									 }
									 if(i==3){
									 $scope.error3=" ";
									 }
									 if(i==4){
										 $scope.error4=" ";
									 }
									 if(i==5){
									 $scope.error5=" ";
									 }
									 if(i==6){
										 $scope.error6=" ";
									 }
								  }
							
							 else
								 {
								 if(i==0){
									 $scope.error0="Starts Break Should be smaller";
									 }
									 if(i==1){
									 $scope.error1="Starts Break Should be smaller";
									 }
									 if(i==2){
									 $scope.error2="Starts Break Should be smaller";
									 }
									 if(i==3){
									 $scope.error3="Starts Break Should be smaller";
									 }
									 if(i==4){
										 $scope.error4="Starts Break Should be smaller";
									 }
									 if(i==5){
									 $scope.error5="Starts Break Should be smaller";
									 }
									 if(i==6){
										 $scope.error6="Starts Break Should be smaller";
									 }
								 		 }
										
		}
		}
		$scope.lowerThanEndBreak=function(i){
			$scope.errors0="";
			$scope.errors1="";
			$scope.errors2="";
			$scope.errors3="";
			$scope.errors4="";
			$scope.errors5="";
			$scope.errors6="";
			  var convert="";
				var convert1="";
				 var hour=Number($scope.clinic.clinicTimingList[i].endsBreak.match(/^(\d+)/)[1]);
				 var hour1=Number($scope.clinic.clinicTimingList[i].endTime.match(/^(\d+)/)[1]);
				var minute1=Number($scope.clinic.clinicTimingList[i].endTime.match(/:(\d+)/)[1]);
				 var minute = Number($scope.clinic.clinicTimingList[i].endsBreak.match(/:(\d+)/)[1]);
				 var AMPM=$scope.clinic.clinicTimingList[i].endsBreak.match(/\s(.*)$/)[1];
		 		 var AMPMendTime=$scope.clinic.clinicTimingList[i].endTime.match(/\s(.*)$/)[1];
		 		if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
		 			if(i==0){
						 $scope.errors0="Ends Break Should be smaller";
						 }
						 if(i==1){
						 $scope.errors1="Ends Break Should be smaller";
						 }
						 if(i==2){
						 $scope.errors2="Ends Break Should be smaller";
						 }
						 if(i==3){
						 $scope.errors3="Ends Break Should be smaller";
						 }
						 if(i==4){
							 $scope.errors4="Ends Break Should be smaller";
						 }
						 if(i==5){
						 $scope.errors5="Ends Break Should be smaller";
						 }
						 if(i==6){
							 $scope.errors6="Ends Break Should be smaller";
						 }
					 }

		 		 if(AMPMendTime=="AM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
			 		convert1=hour1+":"+minute1;
			 		var totalHour=hour+12;
			 		convert=totalHour+":"+minute;
			 if(totalHour>hour1){
				 if(i==0){
					 $scope.errors0=" ";
					 }
					 if(i==1){
					 $scope.errors1=" ";
					 }
					 if(i==2){
					 $scope.errors2=" ";
					 }
					 if(i==3){
					 $scope.errors3=" ";
					 }
					 if(i==4){
						 $scope.errors4=" ";
					 }
					 if(i==5){
					 $scope.errors5=" ";
					 }
					 if(i==6){
						 $scope.errors6=" ";
					 }
			}
			 else
				 {
				 if(i==0){
					 $scope.errors0="Ends Break Should be smaller";
					 }
					 if(i==1){
					 $scope.errors1="Ends Break Should be smaller";
					 }
					 if(i==2){
					 $scope.errors2="Ends Break Should be smaller";
					 }
					 if(i==3){
					 $scope.errors3="Ends Break Should be smaller";
					 }
					 if(i==4){
						 $scope.errors4="Ends Break Should be smaller";
					 }
					 if(i==5){
					 $scope.errors5="Ends Break Should be smaller";
					 }
					 if(i==6){
						 $scope.errors6="Ends Break Should be smaller";
					 }
		
		 }
			 		}
				
		 		 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 var totalHour = hour + 12;
					 convert=totalHour+":"+minute;
					 if(totalHour<totalHour1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
							 }
					 else if(totalHour == totalHour1 && minute >= minute1){
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
				
						  }
					 else if(totalHour == totalHour1 && minute<minute1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
						  }
					
						 else
						 {
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
						 		 }
						  
				  }
				 if(AMPMendTime=="PM" && AMPM == "PM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
					 var totalHour1 = hour1 + 12;
					 convert1=totalHour1+":"+minute1;
					 var totalHour = hour + 12;
					 convert=totalHour+":"+minute;
					 if(totalHour<totalHour1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
							 }
					 else if(totalHour == totalHour1 && minute >= minute1){
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
					
						  }
					 else if(totalHour == totalHour1 && minute<minute1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
						  }
					
						 else
						 {
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
						 		 }
						  
				  }	
		 		 if(AMPMendTime=="PM" && AMPM == "AM" && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="PM"){
		 			
		 			 if(i==0){
		 				 $scope.errors0="Ends Break Should be smaller";
		 				 }
		 				 if(i==1){
		 				 $scope.errors1="Ends Break Should be smaller";
		 				 }
		 				 if(i==2){
		 				 $scope.errors2="Ends Break Should be smaller";
		 				 }
		 				 if(i==3){
		 				 $scope.errors3="Ends Break Should be smaller";
		 				 }
		 				 if(i==4){
		 					 $scope.errors4="Ends Break Should be smaller";
		 				 }
		 				 if(i==5){
		 				 $scope.errors5="Ends Break Should be smaller";
		 				 }
		 				 if(i==6){
		 					 $scope.errors6="Ends Break Should be smaller";
		 				 }
		 		}
					 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour !=12){
					 
						 convert1=hour1+":"+minute1;
				 
					 convert=hour+":"+minute;
					  if(hour<hour1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
							 }
					 else if(hour == hour1 && minute >= minute1){
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
						  }
					 else if(hour == hour1 && minute<minute1){
						 if(i==0){
							 $scope.errors0=" ";
							 }
							 if(i==1){
							 $scope.errors1=" ";
							 }
							 if(i==2){
							 $scope.errors2=" ";
							 }
							 if(i==3){
							 $scope.errors3=" ";
							 }
							 if(i==4){
								 $scope.errors4=" ";
							 }
							 if(i==5){
							 $scope.errors5=" ";
							 }
							 if(i==6){
								 $scope.errors6=" ";
							 }
						  }
					
						 else
						 {
						 if(i==0){
							 $scope.errors0="Ends Break Should be smaller";
							 }
							 if(i==1){
							 $scope.errors1="Ends Break Should be smaller";
							 }
							 if(i==2){
							 $scope.errors2="Ends Break Should be smaller";
							 }
							 if(i==3){
							 $scope.errors3="Ends Break Should be smaller";
							 }
							 if(i==4){
								 $scope.errors4="Ends Break Should be smaller";
							 }
							 if(i==5){
							 $scope.errors5="Ends Break Should be smaller";
							 }
							 if(i==6){
								 $scope.errors6="Ends Break Should be smaller";
							 }
							 }
						  }	
					 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour == 12){
						 var totalHour1 = hour1 - 12;
						 	convert1=totalHour1+":"+minute1;
						 	 var totalHour = hour - 12;
							 	convert=totalHour+":"+minute;
								 if(totalHour<totalHour1){
									 if(i==0){
										 $scope.errors0=" ";
										 }
										 if(i==1){
										 $scope.errors1=" ";
										 }
										 if(i==2){
										 $scope.errors2=" ";
										 }
										 if(i==3){
										 $scope.errors3=" ";
										 }
										 if(i==4){
											 $scope.errors4=" ";
										 }
										 if(i==5){
										 $scope.errors5=" ";
										 }
										 if(i==6){
											 $scope.errors6=" ";
										 }
										 }
								 else if(totalHour == totalHour1 && minute >= minute1){
									 if(i==0){
										 $scope.errors0="Ends Break Should be smaller";
										 }
										 if(i==1){
										 $scope.errors1="Ends Break Should be smaller";
										 }
										 if(i==2){
										 $scope.errors2="Ends Break Should be smaller";
										 }
										 if(i==3){
										 $scope.errors3="Ends Break Should be smaller";
										 }
										 if(i==4){
											 $scope.errors4="Ends Break Should be smaller";
										 }
										 if(i==5){
										 $scope.errors5="Ends Break Should be smaller";
										 }
										 if(i==6){
											 $scope.errors6="Ends Break Should be smaller";
										 }
									  }
								 else if(totalHour == totalHour1 && minute<minute1){
									 if(i==0){
										 $scope.errors0=" ";
										 }
										 if(i==1){
										 $scope.errors1=" ";
										 }
										 if(i==2){
										 $scope.errors2=" ";
										 }
										 if(i==3){
										 $scope.errors3=" ";
										 }
										 if(i==4){
											 $scope.errors4=" ";
										 }
										 if(i==5){
										 $scope.errors5=" ";
										 }
										 if(i==6){
											 $scope.errors6=" ";
										 }
									  }
											 else
									 {
									 if(i==0){
										 $scope.errors0="Ends Break Should be smaller";
										 }
										 if(i==1){
										 $scope.errors1="Ends Break Should be smaller";
										 }
										 if(i==2){
										 $scope.errors2="Ends Break Should be smaller";
										 }
										 if(i==3){
										 $scope.errors3="Ends Break Should be smaller";
										 }
										 if(i==4){
											 $scope.errors4="Ends Break Should be smaller";
										 }
										 if(i==5){
										 $scope.errors5="Ends Break Should be smaller";
										 }
										 if(i==6){
											 $scope.errors6="Ends Break Should be smaller";
										 }
										 }											 }
					 if(AMPMendTime=="AM" && hour1 != 12 && AMPM == "AM" && hour ==12){
						 convert1=hour1+":"+minute1;
						 var totalHour = hour - 12;
						 	convert=totalHour+":"+minute;
							 if(totalHour<hour1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
									 }
							 else if(totalHour == hour1 && minute >= minute1){
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
								  }
							 else if(totalHour == hour1 && minute<minute1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
								  }
							 else
								 {
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
									 }
							 }		
					 if(AMPMendTime=="AM" && hour1 == 12 && AMPM == "AM" && hour != 12){
						 var totalHour1 = hour1 - 12;
						 	convert1=totalHour1+":"+minute1;
						 	 convert=hour+":"+minute;
							 if(hour<totalHour1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
									 }
							 else if(hour == totalHour1 && minute >= minute1){
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
								  }
							 else if(hour == totalHour1 && minute<minute1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
								  }
								 else
								 {
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
									 }
							 }
					 if(AMPMendTime=="PM" && AMPM == "AM" && hour != 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
						 var totalHour1 = hour1 + 12;
						 convert1=totalHour1+":"+minute1;
						 convert=hour+":"+minute;
						 if(hour<totalHour1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
								 }
						 else if(hour == totalHour1 && minute >= minute1){
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
							  }
						 else if(hour == totalHour1 && minute<minute1){
							 if(i==0){
								 $scope.errors0=" ";
								 }
								 if(i==1){
								 $scope.errors1=" ";
								 }
								 if(i==2){
								 $scope.errors2=" ";
								 }
								 if(i==3){
								 $scope.errors3=" ";
								 }
								 if(i==4){
									 $scope.errors4=" ";
								 }
								 if(i==5){
								 $scope.errors5=" ";
								 }
								 if(i==6){
									 $scope.errors6=" ";
								 }
							  }
						 else
							 {
							 if(i==0){
								 $scope.errors0="Ends Break Should be smaller";
								 }
								 if(i==1){
								 $scope.errors1="Ends Break Should be smaller";
								 }
								 if(i==2){
								 $scope.errors2="Ends Break Should be smaller";
								 }
								 if(i==3){
								 $scope.errors3="Ends Break Should be smaller";
								 }
								 if(i==4){
									 $scope.errors4="Ends Break Should be smaller";
								 }
								 if(i==5){
								 $scope.errors5="Ends Break Should be smaller";
								 }
								 if(i==6){
									 $scope.errors6="Ends Break Should be smaller";
								 }
								 }
						 }

					 if(AMPMendTime=="PM" && AMPM == "AM" && hour == 12 && $scope.clinic.clinicTimingList[i].startTime.match(/\s(.*)$/)[1] =="AM"){
						 var totalHour1 = hour1 + 12;
						 convert1=totalHour1+":"+minute1;
						 var totalHour = hour - 12;
						 	convert=totalHour+":"+minute;
							 if(totalHour<totalHour1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
									 }
							 else if(totalHour == totalHour1 && minute >= minute1){
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
								  }
							 else if(totalHour == totalHour1 && minute<minute1){
								 if(i==0){
									 $scope.errors0=" ";
									 }
									 if(i==1){
									 $scope.errors1=" ";
									 }
									 if(i==2){
									 $scope.errors2=" ";
									 }
									 if(i==3){
									 $scope.errors3=" ";
									 }
									 if(i==4){
										 $scope.errors4=" ";
									 }
									 if(i==5){
									 $scope.errors5=" ";
									 }
									 if(i==6){
										 $scope.errors6=" ";
									 }
								  }
							 else
								 {
								 if(i==0){
									 $scope.errors0="Ends Break Should be smaller";
									 }
									 if(i==1){
									 $scope.errors1="Ends Break Should be smaller";
									 }
									 if(i==2){
									 $scope.errors2="Ends Break Should be smaller";
									 }
									 if(i==3){
									 $scope.errors3="Ends Break Should be smaller";
									 }
									 if(i==4){
										 $scope.errors4="Ends Break Should be smaller";
									 }
									 if(i==5){
									 $scope.errors5="Ends Break Should be smaller";
									 }
									 if(i==6){
										 $scope.errors6="Ends Break Should be smaller";
									 }
									 
								 }
							 }		
		}	
				  
			$scope.resetDatePicker=function(workingDayId){
				switch(workingDayId) {
			    case 0:
			    	$scope.clinic.clinicTimingList[0].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[0].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[0].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[0].endsBreak="05:00 PM";
			        break;
			    case 1:
			    	$scope.clinic.clinicTimingList[1].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[1].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[1].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[1].endsBreak="05:00 PM";
			    	break;
			    case 2:
			    	$scope.clinic.clinicTimingList[2].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[2].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[2].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[2].endsBreak="05:00 PM";
			    	break;
			    case 3:
			    	$scope.clinic.clinicTimingList[3].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[3].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[3].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[3].endsBreak="05:00 PM";
			    	break;
			    case 4:
			    	$scope.clinic.clinicTimingList[4].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[4].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[4].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[4].endsBreak="05:00 PM";
			    	break;
			    case 5:
			    	$scope.clinic.clinicTimingList[5].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[5].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[5].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[5].endsBreak="05:00 PM";
			    	break;
			    case 6:
			    	$scope.clinic.clinicTimingList[6].startTime="09:00 AM";
			    	$scope.clinic.clinicTimingList[6].endTime="08:00 PM";
			    	$scope.clinic.clinicTimingList[6].startsBreak="02:00 PM";
			    	$scope.clinic.clinicTimingList[6].endsBreak="05:00 PM";
			    	break;
			    default:
			        break;
			}
		};
		
		$scope.isClean = function() {
	        return angular.equals(clinicOriginal, $scope.clinic);
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


		 if(scope.otherModelValue.match(/\s(.*)$/)[1]=="AM" && AMPM == "AM" && hour != 12 && hour1 != 12){
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
			 if(scope.otherModelValue.match(/\s(.*)$/)[1]=="AM" && hour1 != 12 && AMPM == "PM"){
				  
				 convert1=hour1+":"+minute1;
				 var totalHour = hour + 12;
				 convert=totalHour+":"+minute;
				 if(totalHour>hour1){
						return totalHour>hour1;
						 }
			 }
			 if(scope.otherModelValue.match(/\s(.*)$/)[1]=="PM" && hour1 != 12 && AMPM == "AM"){
				  
				 var totalHour1 = hour1 + 12;
				 convert1=totalHour1+":"+minute1;
				 
				 convert=hour+":"+minute;
				 if(totalHour1>hour){
						return totalHour1>hour;
						 }
			 }
			 if(scope.otherModelValue.match(/\s(.*)$/)[1]=="AM" && hour1 != 12){
				  
				 convert1=hour1+":"+minute1;
			 
			 }
			 if (AMPM == "PM"){ 
				 var totalHour = hour + 12;
				 convert=totalHour+":"+minute;
			  }		
			

			  if(scope.otherModelValue.match(/\s(.*)$/)[1]=="PM"){
				 var totalHour1 = hour1 + 12;
				 convert1=totalHour1+":"+minute1;
			  }	
			
			 if (AMPM == "AM"){ 
				
				 convert=hour+":"+minute;
		  	 }
			 
			 if (AMPM == "AM" && hour == 12){ 
				 var totalHour = hour - 12;
				 convert=totalHour+":"+minute;
		  	 }
		      if(scope.otherModelValue.match(/\s(.*)$/)[1]=="AM" && hour1 != 12){
				  
				 convert1=hour1+":"+minute1;
			  }
			 
			 if(scope.otherModelValue.match(/\s(.*)$/)[1]=="AM" && hour1 == 12){
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





