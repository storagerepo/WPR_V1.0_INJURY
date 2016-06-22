var adminApp=angular.module("sbAdminApp",['requestModule','flash']);

adminApp.controller('ContactUsController',function($scope,requestHandler,Flash){
	$scope.init=function(){
		$scope.search="";
		$scope.contactUsList="";
		$scope.search.status="";
		$scope.getContactUsList();
		$scope.noOfRows="25";
		// For Send Mail Response
		$scope.showError=false;
		$scope.error="";
		$scope.sendDisable=false;
		$scope.sendText="Send";
	};
	
	$scope.getContactUsList=function(){
		requestHandler.getRequest("/Admin/getAllContactUss.json","").then(function(response){
			$scope.contactUsList=response.data.contactUsForms;
		});
	};
	
	$scope.getContactUsDetail=function(id){
		requestHandler.getRequest("/Admin/getContactUs.json?id="+id,"").then(function(response){
			$scope.contactUsDetail=response.data.contactUsForm;
			$scope.conatctUsLogDetails=response.data.contactUsForm.contactUsLogForms;
			$('#viewContactUsModal').modal('show');
		});
	};
	
	// Change Status
	$scope.changeContactStatusModal=function(id,status){
		$scope.contactUsForm={
				"id":id,
				"status":String(status)
		};
		$("#changeStatusModal").modal('show');
		if(status==1){
			$scope.titleAndSaveText="Set Up Demo";
			$scope.dateTimeText="Demo Set Up On";
			$scope.contactedByText="Demo Set Up By";
			$scope.nextStatusText="Demo Set Up";
			$scope.contactUsForm.status=2;
		}
		else if(status==2){
			$scope.titleAndSaveText="Complete Demo";
			$scope.dateTimeText="Demo Completed On";
			$scope.contactedByText="Demo Completed By";
			$scope.nextStatusText="Demo Completed";
			$scope.contactUsForm.status=3;
		}
		
		$scope.contactUsChangeStatusForm.$setPristine();
		
		$scope.changeStatus=function(){
			requestHandler.postRequest("/Admin/changeContactStatus.json",$scope.contactUsForm).then(function(response){
				$("#changeStatusModal").modal('hide');
				Flash.create('success',"You have successfully changed the status!!!");
				$scope.getContactUsList();
			});
		};
		
	};
	
	// Send Response Mail
	$scope.sendResponseMailModal=function(id,email){
		$scope.userEmail="";
		$scope.showError=false;
		$scope.error="";
		$scope.sendDisable=false;
		$scope.sendText="Send";
		$scope.userEmail=email;
		$("#sendResponseMailModal").modal('show');
		$scope.contactUsSendMailForm.$setPristine();
		$scope.contactUsFormMail={
				"id":id,
				"subject":"Crash Reports Online - Demo",
				"bodyMessage":"Please call 614-322-9928 to set up an appointment for a Demo of our product."
		};
		$scope.sendResponseMail=function(){
			$scope.sendDisable=true;
			$scope.sendText="Sending...";
			requestHandler.postRequest("/Admin/sendResponseMail.json",$scope.contactUsFormMail).then(function(response){
				if(response.data.response!=""){
					$scope.showError=true;
					$scope.error="Please check E-mail Id";
				}else{
				$scope.userEmail="";
				$scope.sendDisable=false;
				$scope.sendText="Send";
				$("#sendResponseMailModal").modal('hide');
				Flash.create('success',"You have successfully send the response!!!");
				$scope.getContactUsList();
				}
			});
		};
		
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
	
	$scope.getLogDetails=function(){
		$scope.contactUsChangeStatusForm.$setPristine();
	};
	
	$scope.init();
});