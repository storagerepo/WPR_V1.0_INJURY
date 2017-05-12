var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ImportReportsController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.buttonText="Import Report";
	$scope.buttonDisable=false;
	$scope.reportType="1";
	
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countylist=response.data.countyForms;
	});
	
	$scope.importReport=function(){
		$scope.buttonText="Importing....";
		$scope.buttonDisable=true;
		if($scope.reportType==1){
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
				$("#importReportAlertModal").modal('show');
				console.log($scope.importReportStatusList);
			});
		}else{
			$scope.importReportForm.reportFrom=0;
			requestHandler.postRequest("saveDirectRunnerCrashReport.json",$scope.importReportForm).then(function(response){
				$scope.buttonText="Import Report";
				$scope.buttonDisable=false;
				$scope.isError=false;
				if(response.data.requestSuccess){
					$scope.importReportStatusList=response.data.importCrashReportStatus;
					if($scope.importReportStatusList[0].success){
						$scope.resetFormValues();
					}
				}else{
					$scope.importReportStatusList=response.data.importCrashReportStatus;
				}
				$("#importReportAlertModal").modal('show');
			});
		}
		
	};
	
	$scope.resetFormValues=function(){
		$scope.importReportForm.localReportNumber="";
		$scope.importReportForm.county="";
		$scope.importReportForm.docNumber="";
		$scope.importReportForm.docImageFileName="";
		$scope.importReportForm.crashDate="";
		$scope.myForm.$setPristine();
	};
});