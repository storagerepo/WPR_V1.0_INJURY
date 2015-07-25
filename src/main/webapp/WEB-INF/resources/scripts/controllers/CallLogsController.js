var adminApp=angular.module('sbAdminApp', ['requestModule']);
adminApp.controller('showCallLogsController', function($scope,$http,$location,$stateParams,$state,requestHandler) {
	 
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
	    requestHandler.postRequest("Staff/getCallLogsById.json?id="+$stateParams.id,"").then( function(response) {
	  
	   $scope.callLogs= response.data.callLogsForms;
	   
    $scope.sort('notes');
    
   
  
    
    
    });
   $scope.deleteCalllogs=function(id)
	  {
		
		  if(confirm("Are you sure to delete CallLogs ?")){
			  requestHandler.deletePostRequest("Staff/deleteCallLogs.json?id=",id)
			  .success(function(response){
			 
				  $state.reload('dashboard/Calllogs');
			 
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
			
			
			  requestHandler.postRequest("Staff/saveUpdateCallLogs.json",$scope.calllogs)
				.then(function(response)
						{
					$("#calllogsModel").modal("hide");
					 $state.reload("dashboard/Calllogs");
					});
			
		};

	};
	
	$scope.editModal=function(id)
	{

		$scope.title="Edit CallLogs";
		$scope.options=false;
		
		
		requestHandler.getRequest("Staff/getCallLogs.json?id="+id,"").then( function(response) {
		    $scope.calllogs=response.data.callLogsForm;
		    $("#calllogsModel").modal("show");
		});
		
		
		
	
	};
	

	
	
	  $scope.update=function(){
		  
		  requestHandler.postRequest("Staff/saveUpdateCallLogs.json",$scope.calllogs).then(function (status) {
		  $("#calllogsModel").modal("hide");
			 $state.reload("dashboard/Calllogs");
		  
	      });
	 
	};
	
	$scope.Appointments={};
	 $scope.Appointments.patientId =$stateParams.id;

	
	$scope.saveAppointments=function()
	{

		
		  requestHandler.postRequest("Staff/saveUpdateAppointments.json",$scope.Appointments)
			.then(function(response)
					{

				$("#AppointmentsModal").modal("hide");
				 $state.reload("dashboard/Calllogs");
				});
		};
	
		$scope.viewAppointments=function(id)
		{
		
			requestHandler.getRequest("Staff/getAppointments.json?id="+id,"").then( function(response) {
				
			    $scope.appointments=response.data.appointmentsForm;
			    
			   
			   
		     });
			
			 $("#viewAppointmentsModal").modal("show");
			 
			
		};
	
});
