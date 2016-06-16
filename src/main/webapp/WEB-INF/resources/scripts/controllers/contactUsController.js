var adminApp=angular.module("sbAdminApp",['requestModule','flash']);

adminApp.controller('ContactUsController',function($scope,requestHandler,Flash){
	$scope.init=function(){
		$scope.getContactUsList();
		$scope.noOfRows="25";
	};
	
	$scope.getContactUsList=function(){
		requestHandler.getRequest("/Admin/getAllContactUss.json","").then(function(response){
			$scope.contactUsList=response.data.contactUsForms;
		});
	};
	
	$scope.getContactUsDetail=function(id){
		requestHandler.getRequest("/Admin/getContactUs.json?id="+id,"").then(function(response){
			$scope.contactUsDetail=response.data.contactUsForm;
			$('#viewContactUsModal').modal('show');
		});
	};
	
	$scope.changeContactStatus=function(id){
		requestHandler.postRequest("/Admin/changeContactStatus.json?id="+id,"").then(function(response){
			Flash.create('success',"You have successfully changed the status!!!");
			$scope.getContactUsList();
		});
	};
	
	$scope.deleteContactUsConfirmation=function(id){
		$("#deleteContactUsModal").modal('show');
		$scope.deleteContactUs=function(){
			requestHandler.postRequest("/Admin/deleteContactUs.json?id="+id,"").then(function(response){
				$("#deleteContactUsModal").modal('hide');
				Flash.create('success',"You have successfully deleted!!!");
				$scope.getContactUsList();
			});
		};
	};
	
	$scope.init();
});