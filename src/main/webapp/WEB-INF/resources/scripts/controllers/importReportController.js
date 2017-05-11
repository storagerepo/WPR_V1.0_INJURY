var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ImportReportsController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.buttonText="Import Report";
	$scope.buttonDisable=false;
	
	$scope.importReport=function(){
		$scope.buttonText="Importing....";
		$scope.buttonDisable=true;
		requestHandler.postRequest("importReportByCrashId.json?crashId="+$scope.crashId,"").then(function(response){
			$scope.buttonText="Import Report";
			$scope.buttonDisable=false;
			if(response.data.requestSuccess){
				$scope.isError=false;
				$scope.importReportStatusList=response.data.importCrashReportStatus;
			}else{
				$scope.isError=true;
				$scope.errorMsg=response.data.error;
				$scope.importReportStatusList=response.data.importCrashReportStatus;
			}
			$("#importReportAlrtModal").modal('show');
			console.log($scope.importReportStatusList);
		});
	};
});