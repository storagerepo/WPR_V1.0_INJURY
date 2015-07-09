var adminApp=angular.module('sbAdminApp', []);

adminApp.controller('ShowStaffController', function($scope,$http,$location) {
	 
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

    $http.get("http://localhost:8085/Injury/getAllStaffs.json").success( function(response) {
     $scope.staffs= response.staffForms;
     $scope.sort('username');
     
     });
    $scope.deleteStaff=function(id)
	  {
		  alert(id);
		  if(confirm("Are you sure to delete customer ?")){
		  $http({
			  method : "POST",
			  url : "http://localhost:8085/Injury/deleteStaff.json?id="+id,
			  }).success(function(response){
			 
			  alert("Deleted");
			  $location.path("dashboard/staff");
			 
			  });
		  }
	}
   
 
});

adminApp.controller('SaveStaffController', function($scope,$http,$location) {
	 
	
	$scope.save=function()
	{
		alert("called");
		$http.post("http://localhost:8085/Injury/saveUpdateStaff",$scope.jsonString)
			.success(function(response)
					{
				alert("saved");

				});
		};

});


adminApp.controller('EditStaffController', function($scope,$http,$location,$routeParams) {
	 
	
	$http.get('http://localhost:8086/Injury/getStaff.json?id='+ $routeParams.id).success( function(response) {
	    $scope.jsonString=response;
});

	  $scope.update=function(){
	  $http.post('http://localhost:8085/Injury/saveUpdateStaff.json', {id:$scope.jsonString.id, customer:$scope.jsonString}).success(function (status) {
	          
	          $location.path('/ShowOrders');
	      });
	};
});


