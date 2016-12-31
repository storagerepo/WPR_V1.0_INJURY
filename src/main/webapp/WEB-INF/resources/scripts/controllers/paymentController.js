var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('PaymentController',function($rootScope,$http,$state,$scope,$q,requestHandler,Flash){
	$scope.noOfRows="25";
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
	
	$scope.init=function(){
		$scope.subscriptionsList="";
		$scope.billToRequired=false;
		$scope.subcriptionToRequired=false;
		$scope.sendUserButtonDisable=false;
		$scope.billsSearchForm={
				"customerProductToken":$rootScope.productToken,
				"subcriptionFromDate":"",
				"subscriptionToDate":"",
				"billFromDate":"",
				"billToDate":"",
				"billId":"",
				"itemsPerPage":"10",
				"pageNumber":1
				};
		$scope.getBillList();
	};
	$scope.getBillList=function(){
		var defer=$q.defer();
		 requestHandler.postRequest("getSubscriptionsList.json",$scope.billsSearchForm).then(function(response){
		    	$scope.totalRecords=response.data.subscriptionBillsList.totalNoOfRecords;
		    	$scope.subscriptionsList=response.data.subscriptionBillsList.subscriptionsSearchResultForms;
		    	$scope.customerDetails=response.data.customerDetails;
		    	defer.resolve(response);
		});
		 return defer.promise;
	};
	
	$scope.searchBills=function(){
		if($scope.billsSearchForm.subcriptionFromDate!="" && $scope.billsSearchForm.subscriptionToDate==""){
			$scope.subcriptionToRequired=true;
		}
		else if($scope.billsSearchForm.billFromDate!="" && $scope.billsSearchForm.billToDate==""){
			$scope.subcriptionToRequired=false;
			$scope.billToRequired=true;
		}
		else{
			$scope.billToRequired=false;
			$scope.subcriptionToRequired=false;
			$scope.secondarySearchBillsList();
		}
	};
	// Secondary Search
	$scope.secondarySearchBillsList = function() {
		$scope.billsSearchForm.pageNumber=1;
		var defer=$q.defer();
		requestHandler.postRequest("getSubscriptionsList.json", $scope.billsSearchForm)
				.then(function(response) {
					console.log(response);
					$scope.subscriptionsList = response.data.subscriptionBillsList.subscriptionsSearchResultForms;
					$scope.totalRecords=response.data.subscriptionBillsList.totalNoOfRecords;
					$scope.customerDetails=response.data.customerDetails;
					defer.resolve(response);
				});
		return defer.promise;
	};
	// Watch PageNumber
	$scope.$watch("billsSearchForm.pageNumber",function(){
		 var promise=$scope.getBillList();
			promise.then(function(reponse){
			 setTimeout(function(){
       			 $('html,body').animate({scrollTop: $('#recordsPerPage').offset().top},'slow');
       		 },100);
		   });
	});
	// View Particular Details
	$scope.viewBillDetails=function(billId){
		 requestHandler.getRequest("getBillDetails.json?billId="+billId,"").then(function(response){
			 $scope.billDetails = response.data.billsForm;
				if(parseFloat($scope.billDetails.billAmount)>=parseFloat($scope.billDetails.discountAmount)){
					$scope.subscriptionTotalAmount=parseFloat(parseFloat($scope.billDetails.billAmount)-parseFloat($scope.billDetails.discountAmount)).toFixed(2);
				}else{
					$scope.subscriptionTotalAmount=parseFloat($scope.billDetails.billAmount).toFixed(2);
				}
				$scope.billAmount=parseFloat(parseFloat($scope.subscriptionTotalAmount)+parseFloat($scope.billDetails.miscellaneousAmount)).toFixed(2);
				if($scope.billDetails.taxPercentage>0){
					var billTotal=parseFloat((parseFloat($scope.billAmount)-parseFloat($scope.billDetails.adjustAmount))+parseFloat($scope.billDetails.dueAmount)).toFixed(2);
					$scope.taxAmount=parseFloat((billTotal/100)*parseFloat($scope.billDetails.taxPercentage)).toFixed(2);
				}
				
				$("#billDetailsModal").modal('show');
		 });
		
	};
	// Reset Search
	$scope.resetSearch=function(){
		$scope.init();
	};
	// Subscription Date Validation
	$scope.checkSearchSubscriptionToDate=function(){
		var fromDate=new Date($scope.billsSearchForm.subcriptionFromDate);
		var toDate=new Date($scope.billsSearchForm.subcriptionToDate);
		if(toDate<fromDate)
			$scope.billsSearchForm.subcriptionFromDate="";
	};
	// Bill Date Validation
	$scope.checkSearchBillToDate=function(){
		var fromDate=new Date($scope.billsSearchForm.billFromDate);
		var toDate=new Date($scope.billsSearchForm.billToDate);
		if(toDate<fromDate)
			$scope.billsSearchForm.billToDate="";
	};
	//Call Initializer
	$scope.init();
	
	$scope.getTransactionDetails=function(billId){
		requestHandler.getRequest("getTransactionDetails.json?billId="+billId,"").then(function(response){
			if(response.data.requestSuccess){
				$scope.transactionDetails = response.data.transactionDetails.transaction;
				$scope.settlementDateTime=response.data.settlementDateTime;
				$('#viewTransactionModal').modal('show');
			}else{
				$scope.transactionDetails = response.data.transactionDetails;
				$scope.settlementDateTime=response.data.settlementDateTime;
				$('#viewTransactionErrorModal').modal('show');
			}
		 });
	};
});

adminApp.controller('CardController',function($rootScope,$http,$state,$scope,requestHandler,Flash){
	
	$scope.editable=false;
	$scope.isError=false;
	$scope.submitted=false;
	$scope.cardDetails={"customerProductToken":$rootScope.productToken,"accountType":"","accountNumber":"","expiryMonth":"","expiryYear":"","billingAddress":"","billingCity":"","billingState":"","billingZipcode":""};
	
	
	$scope.editCardDetails=function(){
		$scope.editable=true;
	};
	var cardDetailsOriginal="";
	// Get Card Details
	$scope.getCardDetails=function(){
		 requestHandler.getRequest("getCardDetails.json?productToken="+$rootScope.productToken,"").then(function(response){
			 if(response.data.status){
				 var expirationDateYear= response.data.cardDetails.paymentProfile.payment.creditCard.expirationDate.split("-");
				 $scope.cardDetails.accountType=response.data.cardDetails.paymentProfile.payment.creditCard.cardType;
				 $scope.cardDetails.expiryMonth=expirationDateYear[1];
				 $scope.cardDetails.expiryYear=parseInt(expirationDateYear[0]);
				 $scope.cardDetails.accountNumber=response.data.cardDetails.paymentProfile.payment.creditCard.cardNumber;
				 $scope.backGroundAccountNumber=response.data.cardDetails.paymentProfile.payment.creditCard.cardNumber;
				 $scope.cardDetails.billingAddress=response.data.cardDetails.paymentProfile.billTo.address;
				 $scope.cardDetails.billingCity=response.data.cardDetails.paymentProfile.billTo.city;
				 $scope.cardDetails.billingState=response.data.cardDetails.paymentProfile.billTo.state;
				 $scope.cardDetails.billingZipcode=response.data.cardDetails.paymentProfile.billTo.zip;
				 cardDetailsOriginal=angular.copy($scope.cardDetails);
			 }else{
				 $scope.getCardDetails();
			 }
			
		});
		
	};
	// Initial Load
	$scope.getCardDetails();
	
	//Update Card details
	$scope.updateCard=function(){
		$scope.submitted=true;
		 $scope.cardDetails.accountType=1;
		requestHandler.postRequest("updateCardDetails.json",$scope.cardDetails).then(function(response){
			var result=response.data;
			if(result!=""){
				if(result.messages.resultCode=='OK'){
					$scope.getCardDetails();
					$scope.submitted=false;
					$scope.isError=false;
					Flash.create('success', "You have Successfully Updated!");
					$scope.editable=false;
				}else if(result.messages.resultCode=='ERROR'){
					$scope.submitted=false;
					$scope.isError=true;
					$scope.errorMessages=result.messages.message;
				}
			}else{
				$scope.submitted=false;
				$scope.isError=true;
				$scope.errorMessages="";
				$scope.errorMessages=[{"text":"Please try again later"}];
			}
			
			
		});
	};
	
	$scope.changeAccountNumber=function(){
		$scope.cardDetails.accountNumber='';
	};
	
	$scope.resetAccountNumber=function(){
		if($scope.cardDetails.accountNumber==''){
			$scope.cardDetails.accountNumber=$scope.backGroundAccountNumber;
		}
	};
	
	$scope.cancelChanges=function(){
		$scope.editable=false;
		$scope.isError=false;
	};
	
	// check difference in value
	$scope.isClean=function(){
		return angular.equals(cardDetailsOriginal,$scope.cardDetails);
	};

});

adminApp.controller('PaymentHomeController',function($rootScope,$http,$state,$scope,requestHandler,Flash){
	
	$rootScope.cardDetails={"accountType":"","cardNo":"","cardHolderName":"","expiryMonth":"","expiryYear":"","billingAddress":"","city":"","state":"","zip":""};
	
	// Get Card Details
	$scope.getCardDetails=function(){
		 requestHandler.getRequest("getCardDetails.json?productToken="+$rootScope.productToken,"").then(function(response){
			 if(response.data.status){
				 var expirationDateYear= response.data.cardDetails.paymentProfile.payment.creditCard.expirationDate.split("-");
				 $rootScope.cardDetails.accountType=response.data.cardDetails.paymentProfile.payment.creditCard.cardType;
				 $rootScope.cardDetails.expiryMonth=expirationDateYear[1];
				 $rootScope.cardDetails.expiryYear=parseInt(expirationDateYear[0]);
				 $rootScope.cardDetails.cardNo=response.data.cardDetails.paymentProfile.payment.creditCard.cardNumber;
				 $rootScope.cardDetails.billingAddress=response.data.cardDetails.paymentProfile.billTo.address;
				 $rootScope.cardDetails.city=response.data.cardDetails.paymentProfile.billTo.city;
				 $rootScope.cardDetails.state=response.data.cardDetails.paymentProfile.billTo.state;
				 $rootScope.cardDetails.zip=response.data.cardDetails.paymentProfile.billTo.zip;
				 $rootScope.cardDetails.cardHolderName=response.data.cardDetails.paymentProfile.billTo.firstName+" "+response.data.cardDetails.paymentProfile.billTo.lastName;
			 }else{
				 $scope.getCardDetails();
			 }
			
		});
		
	};
	$scope.getCardDetails();
	
});