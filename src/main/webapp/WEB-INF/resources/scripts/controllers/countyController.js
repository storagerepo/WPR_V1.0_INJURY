var adminApp=angular.module('sbAdminApp', ['requestModule']);

//Controller Starts
adminApp.controller('CountyController', function($scope,$http,$location,requestHandler) {
	
    $scope.getMyCounty=function(){
   		 requestHandler.getRequest("Patient/getMyCounties.json","").then(function(results){
   			$scope.countyList=results.data.countyList;
   		 });
	  };
	  
	  $scope.getMyCounty();
});