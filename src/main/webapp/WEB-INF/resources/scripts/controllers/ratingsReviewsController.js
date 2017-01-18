var adminApp=angular.module('sbAdminApp',['requestModule']);

adminApp.controller('RatingsReviewsController',function($scope,$http,requestHandler){
	requestHandler.getRequest("getOverAllRatingReviewss.json","").then(function(response){
		$scope.ratingViewForm=response.data.ratingViewForm;
	});
});