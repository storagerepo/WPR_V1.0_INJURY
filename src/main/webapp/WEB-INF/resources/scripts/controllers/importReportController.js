var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('ImportReportsController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.buttonText="Import Report";
	$scope.buttonDisable=false;
	$scope.reportType="1";
	$scope.inputType="1";
	
	 requestHandler.getRequest("Admin/getAllCountys.json","").then(function(response){
			$scope.countylist=response.data.countyForms;
	});
	
	$scope.importReport=function(){
		
		if($scope.reportType==1){
			$scope.buttonText="Importing....";
			$scope.buttonDisable=true;
			requestHandler.postRequest("importReportByCrashId.json?crashId="+$scope.crashId+"&addedDate="+new Date(),"").then(function(response){
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
			});
		}else{
			if($scope.inputType==1){
				$scope.buttonText="Importing....";
				$scope.buttonDisable=true;
				$scope.importReportForm.reportFrom=0;
				$scope.importReportList={};
				$scope.importReportList.runnerCrashReportForms=[];
				$scope.importReportList.runnerCrashReportForms.push($scope.importReportForm);
				
				requestHandler.postRequest("saveDirectRunnerCrashReport.json",$scope.importReportList).then(function(response){
					$scope.buttonText="Import Report";
					$scope.buttonDisable=false;
					if(response.data.requestSuccess){
						$scope.importReportStatusList=response.data.importCrashReportStatus;
						$scope.isError=false;
						if($scope.importReportStatusList[0].success){
							$scope.resetFormValues();
						}
					}else{
						$scope.isError=true;
						$scope.errorMsg=response.data.error;
						$scope.importReportStatusList=response.data.importCrashReportStatus;
					}
					$("#importReportAlertModal").modal('show');
				});
			}else{
				$scope.importReportList={};
				$scope.importReportList.runnerCrashReportForms=[];
				if($scope.isJson($scope.jsonForm)){
					
					$.each(JSON.parse($scope.jsonForm),function(key,value){
						$scope.importReportList.runnerCrashReportForms.push(value);
					});
					
					$scope.buttonText="Importing....";
					$scope.buttonDisable=true;
					var requestURL=requestHandler.getURL()+"saveDirectRunnerCrashReport.json";
					$http.post(requestURL,$scope.importReportList).success(function (response){
						$scope.buttonText="Import Report";
						$scope.buttonDisable=false;
						if(response.requestSuccess){
							$scope.isError=false;
							$scope.importReportStatusList=response.importCrashReportStatus;
							if($scope.importReportStatusList[0].success){
								$scope.resetFormValues();
							}
						}else{
							$scope.isError=true;
							$scope.errorMsg=response.error;
							$scope.importReportStatusList=response.importCrashReportStatus;
						}
						$("#importReportAlertModal").modal('show');
			        })
			        .error(function (error, status){
			        	$scope.buttonText="Import Report";
						$scope.buttonDisable=false;
						if(status==400){
							alert("Bad Request - Provide Valid Parameters");
						}
					});
					/*requestHandler.postRequest("saveDirectRunnerCrashReport.json",$scope.importReportList).then(function(response){
						$scope.buttonText="Import Report";
						$scope.buttonDisable=false;
						if(response.data.requestSuccess){
							$scope.isError=false;
							$scope.importReportStatusList=response.data.importCrashReportStatus;
							if($scope.importReportStatusList[0].success){
								$scope.resetFormValues();
							}
						}else{
							$scope.isError=true;
							$scope.errorMsg=response.data.error;
							$scope.importReportStatusList=response.data.importCrashReportStatus;
						}
						$("#importReportAlertModal").modal('show');
					});*/
				}else{
					alert("It's Not a JSON Format, Please Provide Valid JSON");
				}
				
			}
			
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
	
	// Check JSON is valid or Not
	$scope.isJson=function(str) {
	    try {
	        JSON.parse(str);
	    } catch (e) {
	        return false;
	    }
	    return true;
	};
});