var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngFileSaver']);

adminApp.controller('ActivityLogsController',['$scope','requestHandler','Flash','FileSaver','$stateParams',function($scope,requestHandler,Flash,FileSaver,$stateParams){
	
	$scope.getRoles=function(){
		requestHandler.getRequest('/getRoles.json','').then(function(response){
			$scope.rolesForm=response.data.rolesForm;
		});
	};
	
	$scope.getActivies=function(){
		requestHandler.getRequest('Admin/getAllActivitys.json','').then(function(response){
			$scope.activityList=response.data.activityForms;
		});
	};
	
	$scope.searchActivityLogs=function(){
		$scope.activityOriginalSearchForm=angular.copy($scope.activityLogsSearchForm);
		requestHandler.postRequest('Admin/searchActivityLogs.json',$scope.activityLogsSearchForm).then(function(response){
			$scope.activityLogsForms=response.data.activityLogsForms.activityLogsForms;
			$scope.totalRecords=response.data.activityLogsForms.totalRecords;
		});
	};
	
	$scope.itemsPerFilter=function(){
		$scope.setScrollDown=true;
		$scope.activityLogsSearchForm.pageNumber=1;
		$scope.searchActivityLogs();
		setTimeout(function(){
  			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
  		 },100);
		/*var promise=$scope.secoundarySearchPatient();
		if(promise!=null)
		promise.then(function(reponse){
			console.log("scroll down simple");
			console.log(reponse);
			
		});	*/
	};
	
	$scope.$watch('activityLogsSearchForm.pageNumber',function(){
		$scope.searchActivityLogs();
	});
	
	$scope.viewActivityLog=function(activityLogForm){
		$scope.activityLog=activityLogForm;
		$("#viewActivityLogModal").modal('show');
	};
	
	$scope.exportExcelModal=function(){
		$scope.formatType="1";
		$("#exportExcelModal").modal('show');
	};
	
	$scope.exportExcel=function(){
		$scope.exportButtonText="Exporting...";
		$scope.isExporting=true;
		$scope.activityOriginalSearchForm.exportFormat=$scope.formatType;
		$scope.activityOriginalSearchForm.isSkipMyIp=$scope.isSkipMyIp;
		requestHandler.postExportRequest('Admin/exportActivityLog.xlsx',$scope.activityOriginalSearchForm).success(function(responseData){
			 var blob = new Blob([responseData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
			 FileSaver.saveAs(blob,"Export_Activity_"+moment().format('YYYY-MM-DD')+".xlsx");
			 $scope.exportButtonText="Export Excel";
			 $scope.isExporting=false;
		});
	};
	
	$scope.init=function(){
		$scope.isExporting=false;
		$scope.exportButtonText="Export Excel";
		$scope.getRoles();
		$scope.getActivies();
		$scope.activityOriginalSearchForm={};
		$scope.activityLogsSearchForm={
				"pageNumber":1,
				"itemsPerPage":"25",
				"roleId":"",
				"loginId":"",
				"primaryLoginId":$stateParams.loginId,
				"ipAddress":$stateParams.ipAddress,
				"fromDateTime":"",
				"toDateTime":"",
				"activityId":""
		};
		$scope.activityOriginalSearchForm=angular.copy($scope.activityLogsSearchForm);
		$scope.searchActivityLogs();
	};
	
	$scope.init();
	
	$scope.resetExport=function(){
		$scope.formatType="1";
		$scope.isSkipMyIp=false;
	};

}]);

adminApp.controller('IpAccessListController',['$scope','requestHandler','Flash',function($scope,requestHandler,Flash){
	
	$scope.searchIPList=function(){
		requestHandler.postRequest("Admin/searchIpAddressesList.json",$scope.ipAddressSearchForm).then(function(response){
			$scope.totalRecords=response.data.ipAddressList.totalRecords;
			$scope.ipAddressResult=response.data.ipAddressList.ipAddressesSearchResultGroups;
		});
	};
	
	$scope.init=function(){
		$scope.ipAddressSearchForm={
				"ipAddress":"",
				"primaryUsername":"",
				"blockStatus":"",
				"date":moment().format('MM/DD/YYYY'),
				"pageNumber":1,
				"itemsPerPage":"10"
		};
		$scope.noOfRows="10";
		$scope.searchIPList();
	};
	$scope.searchIpAddressList=function(){
		$scope.ipAddressSearchForm.pageNumber=1;
		$scope.searchIPList();
	};
	$scope.itemsPerFilter=function(){
		$scope.ipAddressSearchForm.pageNumber=1;
		$scope.searchIPList();
		setTimeout(function(){
  			 $('html,body').animate({scrollTop: $('#noOfRows').offset().top},'slow');
  		 },100);
	};
	$scope.$watch('ipAddressSearchForm.pageNumber',function(){
		$scope.searchIPList();
	});
	$scope.addBlockIPModal=function(){
		$scope.blockIPFormName.$setPristine();
		$scope.blockIPForm={
				"status":1
		};
		$("#blockIPModal").modal("show");
	};
	
	$scope.updateIPGeoLocation=function(ipAddress){
		$scope.ipAddress={};
		requestHandler.postRequest("Admin/updateIPGeoLocation.json?ipAddress="+ipAddress,"").then(function(response){
			requestHandler.getRequest("Admin/getIpAddresses.json?ipAddress="+ipAddress,"").then(function(response){
				$scope.ipAddress=response.data.ipAddressesForm;
				$("#viewIPDetailsModal").modal('show');
			});
		});
	};

	
	$scope.init();
}]);

adminApp.controller('BlockedIPController',['$scope','requestHandler','Flash',function($scope,requestHandler,Flash){
	
	$scope.getBlockedIPList=function(){
		requestHandler.getRequest("Admin/getBlockedIpList.json","").then(function(response){
			$scope.blockedIpList=response.data.ipBlockedList;
		});
	};
	
	$scope.init=function(){
		$scope.isExist=2;
		$scope.noOfRows="10";
		$scope.getBlockedIPList();
	};
	
	$scope.addBlockIPModal=function(){
		$scope.isExist=2;
		$scope.blockIPFormName.$setPristine();
		$scope.blockIPForm={
				"status":1,
				"ipAddress":""
		};
		$("#blockIPModal").modal("show");
	};
	
	$scope.blockIP=function(){
		requestHandler.getRequest("Admin/checkIPAlreadyBlockedOrNot.json?&ipAddress="+$scope.blockIPForm.ipAddress,"").then(function(response){
			$scope.isExist=response.data.isBlocked;
			if($scope.isExist==2){
				requestHandler.getRequest("Admin/blockOrUnblockIpAddress.json?ipAddress="+$scope.blockIPForm.ipAddress+"&status="+$scope.blockIPForm.status,"").then(function(response){
					Flash.create("success","You have Successfully Blocked!");
					$scope.getBlockedIPList();
					$("#blockIPModal").modal('hide');
				});
			}
		});
		
	};
	
	$scope.unblockIPModal=function(id){
		$scope.blockIPForm={
				"status":0,
				"ipAddress":id
		};
		$("#unblockIPModal").modal('show');
	};
	
	$scope.unBlockIP=function(){
		requestHandler.getRequest("Admin/blockOrUnblockIpAddress.json?ipAddress="+$scope.blockIPForm.ipAddress+"&status="+$scope.blockIPForm.status,"").then(function(response){
			Flash.create("success","You have Successfully Unblocked!");
			$scope.getBlockedIPList();
			$("#unblockIPModal").modal('hide');
		});
	};
	
	$scope.checkAlreadyExist=function(){
		requestHandler.getRequest("checkIPExistOrNot.json?&ipAddress="+$scope.blockIPForm.ipAddress,"").then(function(response){
			$scope.isExist=response.data.isExist;
		});
	};
	
	$scope.init();
}]);