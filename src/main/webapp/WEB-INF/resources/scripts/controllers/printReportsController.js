var adminApp=angular.module('sbAdminApp',['requestModule']);

adminApp.controller('PrintReportsController',function($rootScope,$scope,$stateParams,$window,$timeout,$interval,requestHandler){
	console.log(window.opener.$windowScope.sendingReportsList);
	$scope.parentWindow=window.opener.$windowScope;
	$scope.printReportsList=$scope.parentWindow.sendingReportsList;
	$scope.listSize=$scope.printReportsList.length;
	$scope.isPrintStopped=false;
	
	// printerJobSubmitted List
	$scope.printReportsSubmitted=[];
	// printerJobNotSubmitted List
	$scope.printReportsNotSubmitted=[];
	var promise=$timeout();
	$scope.addPrintJob=function(){
		var localReportNumber=""
		$.each($scope.printReportsList,function(index,value){
			if(value.localReportNumber!=localReportNumber){
			promise=promise.then(function(){
				if(value.printStatus==0 && $scope.isPrintStopped==false){
					$.each($scope.printReportsList,function(index1,value1){
						if(value.localReportNumber==value1.localReportNumber){
							value.printStatus=1;
							var printJob={};
							printJob.xsrf=$scope.printReportsList.xsrf;
							printJob.printerId=$scope.printReportsList.printerId;
							printJob.jobId='';
							printJob.title=value.localReportNumber;
							printJob.contentType='url';
							printJob.content=value.fileName;
							printJob.accessToken=$scope.printReportsList.xsrf;
							console.log(printJob);
							requestHandler.postRequest("/submitPrintJob.json",printJob).then(function(response){
								if(response.data.success){
									value.jobId=response.data.job.id;
									value.printStatus=2;
									$scope.printReportsSubmitted.push(value);
								}else{
									value.printStatus=0;
									$scope.printReportsNotSubmitted.push(value);
									alert(response.data.message);
								}
							},function(error){
								value.printStatus=0;
								$scope.printReportsNotSubmitted.push(value);
							});
						}else{
							console.log("Removed",value.localReportNumber);
						}
					});
					
				}else if(value.printStatus==7){
					// Do Nothing
				}else if($scope.isPrintStopped){
					value.printStatus=6;
					$scope.printReportsNotSubmitted.push(value);
				}
				return $timeout(4000);
			});
			localReportNumber=value.localReportNumber;
			}
		});
	};
	
	// Checking for Stopped and Changing status
	$scope.stopStatusChange=function(){
		$.each($scope.printReportsList,function(index,value){
			if(value.printStatus==0 && $scope.isPrintStopped){
				value.printStatus=6;
				$scope.printReportsNotSubmitted.push(value);
			}
		});
	};
	
$scope.submitNotSubmittedJobs=function(){
	$.each($scope.printReportsNotSubmitted,function(index,value){
		promise=promise.then(function(){
			if(value.printStatus==0 && $scope.isPrintStopped==false){
				value.printStatus=1;
				var printJob={};
				printJob.xsrf=$scope.printReportsList.xsrf;
				printJob.printerId=$scope.printReportsList.printerId;
				printJob.jobId='';
				printJob.title=value.localReportNumber;
				printJob.contentType='url';
				printJob.content=value.fileName;
				printJob.accessToken=$scope.printReportsList.xsrf;
				requestHandler.postRequest("/submitPrintJob.json",printJob).then(function(response){
					if(response.data.success){
						$.each($scope.printReportsList,function(index1,value1){
							if(value.localReportNumber==value1.localReportNumber){
								value.jobId=response.data.job.id;
								value.printStatus=2;
								value1.printStatus=2;
								value1.jobId=response.data.job.id;
								$scope.printReportsSubmitted.push(value);
							}
						});
					}else{
						value.printStatus=0;
						$scope.printReportsNotSubmitted.push(value);
						alert(response.data.message);
					}
				},function(error){
					value.printStatus=0;
					$scope.printReportsNotSubmitted.push(value);
				});
			}else if(value.printStatus==7){
				// Do Nothing
			}else{
				$.each($scope.printReportsList,function(index1,value1){
					if(value.localReportNumber==value1.localReportNumber){
						value.printStatus=6;
						value1.printStatus=6;
					}
				});
			}
			return $timeout(5000);
		});
		
	});
 };
	// Get Job Status
	$scope.getJobStatus=function(){
		$.each($scope.printReportsSubmitted,function(index,value){
			if(value.printStatus!=4&&value.printStatus!=5&&value.printStatus!=6&&value.printStatus!=7){
				var printJob={};
				printJob.xsrf=$scope.printReportsList.xsrf;
				printJob.accessToken=$scope.printReportsList.xsrf;
				printJob.jobId=value.jobId;
				requestHandler.postRequest("/getPrintJobStatus.json",printJob).then(function(response){
					var job=response.data.job;
					if(response.data.success){
						$.each($scope.printReportsList,function(index1,value1){
							if(value.localReportNumber==value1.localReportNumber){
								if(job.status=="QUEUED"){
									value.printStatus=2;
									value1.printStatus=2;
								}else if(job.status=="IN_PROGRESS"){
									value.printStatus=3;
									value1.printStatus=3;
								}else if(job.status=="DONE"){
									value.printStatus=4;
									value1.printStatus=4;
								}else if(job.status=="ERROR"){
									value.printStatus=5;
									value1.printStatus=5;
								}
							}
						});
					}else{
						alert(response.data.message);
					}
				});
			}
		});
	};
	
	// Call at initially
	$scope.addPrintJob();
	
	$interval($scope.getJobStatus,20000);
	$interval($scope.submitNotSubmittedJobs,20000);
	$interval($scope.stopStatusChange,1000);
	
	$scope.removeReports=function(idx){
		$scope.printReportsList.splice(idx,1);
		$scope.listSize=$scope.printReportsList.length;
		console.log($scope.printReportsList);
	};
	
	$scope.$watchCollection('printReportsList',function(){
		$scope.isChanged=true;
	});
});