var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowAppointmentsCtrl', function($scope,$http,$location,$state,requestHandler) {
	 
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    var date=new Date();
    $scope.searchDate=(date.getMonth()+1).toString();
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
    requestHandler.getRequest("Staff/monthwiseAppointment.json?month=0","").then(function(response){
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
	requestHandler.getRequest("Staff/monthwiseAppointment.json?month="+$scope.searchDate,"").then(function (response) {
			$scope.appointments=response.data.appointmentsForms;
	});
	 }

else
{
	requestHandler.getRequest("Staff/monthwiseAppointment.json?month=0").then( function(response) {
	     $scope.appointments = response.data.appointmentsForms;
	    $scope.appointments.status = true;
	    });
	}

};
});
