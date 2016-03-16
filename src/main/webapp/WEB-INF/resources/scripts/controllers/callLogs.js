var adminApp=angular.module('sbAdminApp', []);

//Controller Starts
adminApp.controller('ShowCallLogs', function($scope,$http,$location) {
	$scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

    $http.get("http://localhost:8080/Injury/getAllCallers.json").success( function(response) {
     $scope.staffs= response.staffForms;
     $scope.sort('username');
     
     });
    $scope.deleteStaff=function(id)
	  {
		  alert(id);
		  if(confirm("Are you sure to delete customer ?")){
		  $http({
			  method : "POST",
			  url : "http://localhost:8080/Injury/deleteCaller.json?id="+id,
			  }).success(function(response){
			 
			  alert("Deleted");
			  $location.path("dashboard/staff");
			 
			  });
		  }
	}; 
});

adminApp.controller('SaveCallLogs', function($scope,$http,$location) {
	$scope.save=function()
	{
		alert("called");
		$http.post("http://localhost:8080/Injury/saveUpdateCaller",$scope.jsonString)
			.success(function(response)
					{
				alert("saved");

				});
		};
});

adminApp.controller('EditCallLogs', function($scope,$http,$location,$stateParams) {
	 
	
	$http.get('http://localhost:8080/Injury/getCaller.json?id='+ $stateParams.id).success( function(response) {
	    $scope.jsonString=response.staffForm;
	});
	
	$scope.update=function(){
	$http.post('http://localhost:8080/Injury/saveUpdateCaller.json', {id:$scope.jsonString.id, customer:$scope.jsonString}).success(function (status) {
	          $location.path('/ShowOrders');
	      });
	};
});

//Controller Ends
