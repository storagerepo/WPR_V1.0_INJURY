
var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowStaffController', function($rootScope,$scope,$http,$location,$state,requestHandler) {
	 
	
	
	 $scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };
	    
    requestHandler.getRequest("Admin/getAllStaffs.json","").then(function(results){
    	 $scope.staffs= results.data.staffForms;
         $scope.sort('username');
     });
    
    $scope.deleteStaff=function(id)
	  {
		
		  if(confirm("Are you sure to delete Staff ?")){
		  requestHandler.deletePostRequest("Admin/deleteStaff.json?id=",id).then(function(results){
			  $state.reload('dashboard.staff');
		     });
		  }
	}
    
    $scope.alertFunction=function()
    {
    	
    };
   
 
});

adminApp.controller('SaveStaffController', function($scope,$http,$location,$state,requestHandler) {

	$scope.options=true;
	$scope.title=$state.current.title;
	$scope.save=function()
	{
		
		
		  requestHandler.postRequest("Admin/saveUpdateStaff.json",$scope.staff).then(function(response){
			  $location.path('dashboard/staff');
			});
	};

});


adminApp.controller('EditStaffController', function($scope,$http,$location,$stateParams,$state,requestHandler) {
	
	$scope.options=false;
	$scope.title=$state.current.title;
	
	requestHandler.getRequest("Admin/getStaff.json?id="+$stateParams.id,"").then(function(response){
		$scope.staff=response.data.staffForm;
	});
	

	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateStaff.json",$scope.staff).then(function(response){
			  $location.path('dashboard/staff');
			});
			
	  
	};
});
