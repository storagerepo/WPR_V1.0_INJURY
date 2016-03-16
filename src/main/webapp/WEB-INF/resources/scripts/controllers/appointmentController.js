var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowAppointmentsCtrl', function($scope,$http,$location,$state,requestHandler) {
	
	// Number of Records per Page
	$scope.noOfRows="10";
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    var date=new Date();
    $scope.searchMonth=(date.getMonth()+1).toString();
    $scope.searchYear=date.getFullYear();
/*if(date.getDate()<=9 && date.getMonth()<=8){
  	$scope.searchDate=date.getFullYear()+"-0"+(date.getMonth()+1)+"-0"+date.getDate();
  }
    else if(date.getMonth()<=8){
    	$scope.searchDate=date.getFullYear()+"-0"+(date.getMonth()+1)+"-"+date.getDate();
    }
    else if(date.getDate()<=9){
  	$scope.searchDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-0"+date.getDate();
  }
    else{
    	$scope.searchDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
*/
    requestHandler.getRequest("Caller/monthwiseAppointment.json?month=0&year=0","").then(function(response){
		//alert(JSON.stringify(response));
    	 $scope.appointments = response.data.appointmentsForms;
         $scope.appointments.status = true;
         $.each($scope.appointments,function(index,value){
        	 if(value.status==0){
        		 value.status="Not Arrived";
        	 }
         });
          $scope.sort("scheduledDate");
	     });
 
$scope.viewPatients=function(id)
{
	requestHandler.getRequest("/Caller/getPatients.json?id="+id,"").then( function(response) {
		$scope.patients=response.data.patientsForm;
	      $("#myModal").modal("show");
     });
}
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
