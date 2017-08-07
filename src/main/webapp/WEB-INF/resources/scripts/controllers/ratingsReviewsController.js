var adminApp=angular.module('sbAdminApp',['requestModule']);

adminApp.controller('RatingsReviewsController',function($scope,$http,requestHandler){
	
	$scope.noOfRows="25";
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };
    
    // Scroll To Top
	$(function(){
		$("html,body").scrollTop(0);
	});
    
    // Default Sorting by ReviewDatTime
    $scope.sortKey ="reviewDateTime";
    $scope.reverse = !$scope.reverse;
    
    // Enable View All Div
    $scope.enableViewAll=false;
    
	$scope.getOverallRatings=function(){
    	requestHandler.getRequest("getOverAllRatingReviewss.json","").then(function(response){
    		$scope.ratingViewForm=response.data.ratingViewForm;
    		$scope.enableViewAll=false;
    	});
    };
	
    // Call Over all Ratings Initial    
    $scope.getOverallRatings();
    
	$scope.getAllRatingsAndReviews=function(){
		requestHandler.getRequest("getAllRatingReviewss.json","").then(function(response){
			$scope.ratingReviewsForm=response.data.ratingReviewsForms;
			$.each($scope.ratingReviewsForm,function(key,value){
				if(value.role=='Caller Admin'){
					value.roleId=1;
				}else if(value.role=='Caller'){
					value.roleId=2;
				}else if(value.role=='Lawyer Admin'){
					value.roleId=3;
				}else if(value.role=='Lawyer'){
					value.roleId=4;
				}
				else if(value.role=='Dealer Manager'){
					value.roleId=5;
				}
				else if(value.role=='Dealer'){
					value.roleId=6;
				}
			});
			$scope.enableViewAll=true;
		});
	};
	
	$scope.viewRating=function(userId){
		  requestHandler.getRequest("/getRatingReviewsByPassingUserId.json?userId="+userId,"").then(function(response){
		  			if(response.data.ratingReviewsForm.ratingReviewId!=null){
		  				$scope.isAvailable=true;
		  				$scope.ratingQ1Style={'width':response.data.ratingReviewsForm.ratingQ1*20+'%'};
		  				$scope.ratingQ2Style={'width':response.data.ratingReviewsForm.ratingQ2*20+'%'};
		  				$scope.ratingQ3Style={'width':response.data.ratingReviewsForm.ratingQ3*20+'%'};
		  				$scope.ratingQ4Style={'width':response.data.ratingReviewsForm.ratingQ4*20+'%'};
		  				$scope.ratingQ5Style={'width':response.data.ratingReviewsForm.ratingQ5*20+'%'};
		  				$scope.ratingQ1=response.data.ratingReviewsForm.ratingQ1;
		  				$scope.ratingQ2=response.data.ratingReviewsForm.ratingQ2;
		  				$scope.ratingQ3=response.data.ratingReviewsForm.ratingQ3;
		  				$scope.ratingQ4=response.data.ratingReviewsForm.ratingQ4;
		  				$scope.ratingQ5=response.data.ratingReviewsForm.ratingQ5;
		  				$scope.reviewQ1=response.data.ratingReviewsForm.reviewQ1;
		  				$scope.reviewQ2=response.data.ratingReviewsForm.reviewQ2;
		  				$scope.reviewQ3=response.data.ratingReviewsForm.reviewQ3;
		  				$scope.reviewQ4=response.data.ratingReviewsForm.reviewQ4;
		  			}else{
		  				$scope.isAvailable=false;
		  			}

		  			$("#viewRatingModal").modal('show');
		  		});
	};
	
});