var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowAppointmentsCtrl', function($scope,$http,$location,$state,requestHandler) {
	 
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
    requestHandler.getRequest("Staff/todaysAppointment.json","").then(function(response){
		//alert(JSON.stringify(response));
    	 $scope.appointments = response.data.appointmentsForms;
         $scope.appointments.status = true;
          $scope.sort("scheduledDate");
	     });
 
$scope.viewPatients=function(id)
{
	requestHandler.getRequest("/Staff/getPatients.json?id="+id,"").then( function(response) {
		$scope.patients=response.data.patientsForm;
	      $("#myModal").modal("show");
     });
}
$scope.getByDates=function()
{
if($scope.searchDate)
	{
	requestHandler.getRequest("Staff/getByDates.json?date="+$scope.searchDate,"").then(function (response) {
			$scope.appointments=response.data.appointmentsForms;
	});
	 }

else
{
	requestHandler.getRequest("Staff/todaysAppointment.json").then( function(response) {
	     $scope.appointments = response.data.appointmentsForms;
	    $scope.appointments.status = true;
	    });
	}

}
});
