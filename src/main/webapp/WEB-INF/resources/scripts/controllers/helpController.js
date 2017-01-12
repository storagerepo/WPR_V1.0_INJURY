var adminApp=angular.module('sbAdminApp',[]);

adminApp.controller('HelpController',function($scope,$http){
	 $scope.rating = 0;
	 $scope.isReadonly=true;
	    $scope.ratings = [{
	        current: 5,
	        max: 10
	    }, {
	        current: 3,
	        max: 5
	    }];

	    $scope.getSelectedRating = function (rating) {
	        console.log(rating);
	    };
});

adminApp.directive('starRating', function () {
    return {
        restrict: 'A',
        template: '<ul class="rating">' +
            '<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)" ng-disabled="isReadonly">' +
            '\u2605' +
            '</li>' +
            '</ul>',
        scope: {
            ratingValue: '=',
            max: '=',
            onRatingSelected: '&',
            ratingReadonly:'='
        },
        link: function (scope, elem, attrs) {

            var updateStars = function () {
                scope.stars = [];
                for (var i = 0; i < scope.max; i++) {
                    scope.stars.push({
                        filled: i < scope.ratingValue,
                        isReadonly:scope.ratingReadonly
                    });
                }
            };

            scope.toggle = function (index) {
                scope.ratingValue = index + 1;
                scope.onRatingSelected({
                    rating: index + 1
                });
            };

            scope.$watch('ratingValue', function (oldVal, newVal) {
                if (newVal) {
                    updateStars();
                }
            });
        }
    };
});