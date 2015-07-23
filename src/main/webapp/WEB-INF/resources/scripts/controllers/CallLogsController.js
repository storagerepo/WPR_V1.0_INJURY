var adminApp=angular.module('sbAdminApp', []);
adminApp.controller('showCallLogsController', function($scope,$http,$location,$stateParams,$state) {
	 
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	   
   $http.post("http://localhost:8080/Injury/getCallLogsById.json?id="+ $stateParams.id).success( function(response) {
	   
	   $scope.callLogs= response.callLogsForms;
	   
    $scope.sort('notes');
    
   
  
    
    
    });
   $scope.deleteCalllogs=function(id)
	  {
		
		  if(confirm("Are you sure to delete CallLogs ?")){
		  $http({
			  method : "POST",
			  url : "http://localhost:8080/Injury/deleteCallLogs.json?id="+id,
			  }).success(function(response){
			 
				  $state.reload('dashboard.Calllogs');
			 
			  });
		  }
	}
   
	 $scope.alertFunction=function()
   {
   	alert("Done");
   };
   $scope.addModel=function()
	{
		$scope.title="Add CallLogs";
		$("#calllogsModel").modal("show");
		$scope.calllogs={};
		 $scope.calllogs.patientId =$stateParams.id;
		    alert($scope.calllogs.patientId);
		$scope.options=true;
		$scope.save=function()
		{
			
			
			$http.post("http://localhost:8080/Injury/saveUpdateCallLogs.json",$scope.calllogs)
				.success(function(response)
						{
					$("#calllogsModel").modal("hide");
					 $state.reload("dashboard/Calllogs");
					});
			
		};

	};
	
	$scope.editModal=function(id)
	{
		alert(id);
		$scope.title="Edit CallLogs";
		$scope.options=false;
		$http.get('http://localhost:8080/Injury/getCallLogs.json?id='+ id).success( function(response) {
		    $scope.calllogs=response.callLogsForm;
		    
	});
		$("#calllogsModel").modal("show");
		
		 $state.reload('dashboard.Calllogs');
	
	};
	

	
	
	  $scope.update=function(){
		  
	  $http.post("http://localhost:8080/Injury/saveUpdateCallLogs.json",$scope.calllogs).success(function (status) {
		  $("#calllogsModel").modal("hide");
			 $state.reload("dashboard/Calllogs");
		  
	      });
	 
	};
	
	$scope.Appointments={};
	 $scope.Appointments.patientId =$stateParams.id;
	    alert($scope.Appointments.patientId);
	
	$scope.saveAppointments=function()
	{
		alert( $scope.Appointments.patientId);
		
		$http.post("http://localhost:8080/Injury/saveUpdateAppointments.json",$scope.Appointments)
			.success(function(response)
					{

				$("#AppointmentsModal").modal("hide");
				 $state.reload("dashboard/Calllogs");
				});
		};
	
		$scope.viewAppointments=function(id)
		{
		
			$http.get('http://localhost:8080/Injury/getAppointments.json?id='+ id).success( function(response) {
			    $scope.appointments=response.appointmentsForm;
			   
			   
		     });
			
			 $("#viewAppointmentsModal").modal("show");
			 
			
		};
	
});
