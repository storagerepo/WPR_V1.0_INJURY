var adminApp=angular.module('sbAdminApp',['requestModule','flash','ngAnimate']);

adminApp.controller('PaymentController',function($http,$state,$scope,requestHandler,Flash){
	$scope.noOfRows="25";
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
	$scope.paymentDetails=[{"date":"04/23/2016","billAmount":"344","paidAmount":"344","status":"Paid"},{"date":"04/20/2016","billAmount":"1025","paidAmount":"1025","status":"Due"}]
	
	$scope.viewDetails=function(){
		$('#viewPaymentModal').modal('show');
	};

});

adminApp.controller('CardController',function($http,$state,$scope,requestHandler,Flash){
	
	$scope.editable=false;
	$scope.cardDetails={"cardNo":"XXXX-XXXX-3456","cardHolderName":"Holder Name L"};
	var cardDetailsOriginal=angular.copy($scope.cardDetails);
	$scope.editCardDetails=function(){
		$scope.editable=true;
	};
	
	$scope.isClean=function(){
		return angular.equals(cardDetailsOriginal,$scope.cardDetails);
	};

});