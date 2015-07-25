
var adminApp=angular.module('sbAdminApp', []);

adminApp.controller('ShowStaffController', function($scope,$http,$location,$state) {
	 
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

    $http.get("http://localhost:8081/Injury/Admin/getAllStaffs.json").success( function(response) {
     $scope.staffs= response.staffForms;
     $scope.sort('username');
     
     });
    $scope.deleteStaff=function(id)
	  {
		
		  if(confirm("Are you sure to delete Staff ?")){
		  $http({
			  method : "POST",
			  url : "http://localhost:8081/Injury/Admin/deleteStaff.json?id="+id,
			  }).success(function(response){
			 
				  $state.reload('dashboard.staff');
			 
			  });
		  }
	}
    
    $scope.alertFunction=function()
    {
    	
    };
   
 
});

adminApp.controller('SaveStaffController', function($scope,$http,$location,$state) {

	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.save=function()
	{
		
		$http.post("http://localhost:8081/Injury/Admin/saveUpdateStaff",$scope.staff)
			.success(function(response)
					{
				
				
				 
				});
		
		  $location.path("dashboard/staff");
		  
		};

});


adminApp.controller('EditStaffController', function($scope,$http,$location,$stateParams,$state) {
	
	$scope.options=false;
	$scope.title=$state.current.title;
	$http.get('http://localhost:8081/Injury/Admin/getStaff.json?id='+ $stateParams.id).success( function(response) {
	    $scope.staff=response.staffForm;
});

	  $scope.update=function(){
		  
	  $http.post('http://localhost:8081/Injury/Admin/saveUpdateStaff.json', $scope.staff).success(function (status) {
	           
		  
	      });
	  $location.path('dashboard/staff');
	  
	};
});


