var adminApp=angular.module('sbAdminApp',['requestModule','flash']);

adminApp.controller('RateReviewController',function($scope,$http,requestHandler,Flash){
	
	// Scroll To Top
	$(function(){
		$("html,body").scrollTop(0);
	});
	 $scope.rating = 0;
	 $scope.overallPercentage=50;
	 $scope.isReadonly=true;
	 $scope.isDisable=false;
	 $scope.isError=false;
	    $scope.ratings = [{
	    	question:"1. Speed/Performance",
	        current: 0,
	        max: 5
	    }, {
	    	question:"2. Organising and Accuracy of Data",
	        current: 0,
	        max: 5
	    },{
	    	question:"3. Effectiveness and Efficiency of Information Search",
	        current: 0,
	        max: 5
	    },
	    {
	    	question:"4. User Friendliness",
	        current: 0,
	        max: 5
	    },
	    {
	    	question:"5. Features & Functionality",
	        current: 0,
	        max: 5
	    }];

	   
	    $scope.getSelectedRating = function (rating,index) {
	    	var totalRatingValue=rating;
	        $.each($scope.ratings,function(key,value){
	        	if(index!=key){
	        		totalRatingValue=parseInt(value.current)+parseInt(totalRatingValue);
	        	}
	        });
	        $scope.overallPercentage=(parseInt(totalRatingValue)/25)*100;
	    };
	    
	    $scope.overAllRatingPercentage=function(){
	    	 var totalRatingValue=0;
		        $.each($scope.ratings,function(key,value){
		        	totalRatingValue=parseInt(value.current)+parseInt(totalRatingValue);
		        });
		        $scope.overallPercentage=(parseInt(totalRatingValue)/25)*100;
	    };
	    
	    // Init Calculation
	    $scope.overAllRatingPercentage();
	    $scope.getRatingValue=function(){
	    	 requestHandler.getRequest("/getRatingReviews.json","").then(function(response){
	  			if(response.data.ratingReviewsForm.ratingReviewId!=null){
	  				$scope.isDisable=true;
	  				 $scope.ratings[0].current=response.data.ratingReviewsForm.ratingQ1;
	  				$scope.ratings[1].current=response.data.ratingReviewsForm.ratingQ2;
	  				$scope.ratings[2].current=response.data.ratingReviewsForm.ratingQ3;
	  				$scope.ratings[3].current=response.data.ratingReviewsForm.ratingQ4;
	  				$scope.ratings[4].current=response.data.ratingReviewsForm.ratingQ5;
	  				$scope.reviewQ1=response.data.ratingReviewsForm.reviewQ1;
	  				$scope.reviewQ2=response.data.ratingReviewsForm.reviewQ2;
	  				$scope.reviewQ3=response.data.ratingReviewsForm.reviewQ3;
	  				$scope.reviewQ4=response.data.ratingReviewsForm.reviewQ4;
	  				$scope.overAllRatingPercentage();
	  			}else{
	  				$scope.isDisable=false;
	  			}
	  		});
	    };
	    
	    $scope.getRatingValue();
	  
	    $scope.submitRating=function(){
	    	// validate Rating
	    	 var ratingCount=0;
	    	 $.each($scope.ratings,function(key,value){
		        	if(value.current!=0){
		        		ratingCount++;
		        	}
		        });
	    	 
	    	 if(ratingCount>=5){
	    		 $scope.isError=false;
	    		$scope.ratingReviewsForm={};
	 	    	$scope.ratingReviewsForm.ratingQ1=$scope.ratings[0].current;
	 	    	$scope.ratingReviewsForm.ratingQ2=$scope.ratings[1].current;
	 	    	$scope.ratingReviewsForm.ratingQ3=$scope.ratings[2].current;
	 	    	$scope.ratingReviewsForm.ratingQ4=$scope.ratings[3].current;
	 	    	$scope.ratingReviewsForm.ratingQ5=$scope.ratings[4].current;
	 	    	$scope.ratingReviewsForm.reviewQ1=$scope.reviewQ1;
	 	    	$scope.ratingReviewsForm.reviewQ2=$scope.reviewQ2;
	 	    	$scope.ratingReviewsForm.reviewQ3=$scope.reviewQ3;
	 	    	$scope.ratingReviewsForm.reviewQ4=$scope.reviewQ4;
	 	    	// 20 ==>> 100% divide by Number of Stars(5)
	 	    	$scope.ratingReviewsForm.overallRating=($scope.overallPercentage / 20).toFixed(2);
	 	    	console.log($scope.ratings);
	 	    	console.log($scope.ratingReviewsForm);
	 	    	requestHandler.postRequest("saveUpdateRatingReviews.json",$scope.ratingReviewsForm).then(function(response){
	 	    		 $scope.response=response.data.requestSuccess;
	 				 if($scope.response==true)
	 				 {
	 				 $(function(){
	 						$("html,body").scrollTop(0);
	 				 });
	 				 Flash.create('success', "Thanks For Rating!");
	 				 $scope.getRatingValue();
	 				 }
	 	    	});
		     }else{
		    	 $(function(){
						$("html,body").scrollTop(0);
				 });
		    	 $scope.isError=true;
		     }
	    };
});

adminApp.directive('starRating', function () {
    return {
        restrict: 'A',
        template: '<ul class="rating">' +
            '<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">' +
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
                        filled: i < scope.ratingValue
                    });
                }
            };

            scope.toggle = function (index) {
            	 if (scope.ratingReadonly == undefined || scope.ratingReadonly === false){
            		 scope.ratingValue = index + 1;
                     scope.onRatingSelected({
                         rating: index + 1
                     });
            	 }
            };

            scope.$watch('ratingValue', function (oldVal, newVal) {
            	updateStars();
            });
        }
    };
});

//@ngInject
adminApp.factory('stars', function() {

    /**
     * Draw wrapping rectangle
     *
     * @param ctx {Object} 2d context
     * @param dim {Number}
     * @param backColor {String}
     */
    function _drawRect(ctx, dim, backColor) {
        if (!ctx) throw Error('No Canvas context found!');
        ctx.save();
        ctx.width = dim;
        ctx.height = dim;
        ctx.fillStyle = backColor;
        ctx.fillRect(0,0, dim, dim);
        ctx.restore();
    }

    /**
     * Draw one star with certain general params
     *
     * @param ctx {Object} 2d context
     * @param r {Number} Radius
     * @private
     */
    function _star(ctx, r) {
        if (!ctx) throw Error('No Canvas context found!');
        ctx.save();
        ctx.globalCompositeOperation = 'destination-out';

        ctx.beginPath();
        ctx.translate(r, r);
        ctx.moveTo(0, 0 - r);
        for (var i = 0; i < 5; i++) {
            ctx.rotate(Math.PI / 5);
            ctx.lineTo(0, 0 - (r * 0.4));
            ctx.rotate(Math.PI / 5);
            ctx.lineTo(0, 0 - r);
        }
        ctx.fill();
        ctx.restore();
    }

    // Draw one empty star
    function drawRatingElement(ctx, r, rectBackColor, ratingElDrawerFunc) {
        _drawRect(ctx, r * 2, rectBackColor);
        if (typeof ratingElDrawerFunc === 'function') {
            ratingElDrawerFunc(ctx, r); // draw custom rating item
        } else {
            _star(ctx, r); // draw star as a default rating item
        }
        
    }

    // Return API
    return {
        drawRatingElement: drawRatingElement
    }
});

// @ngInject
adminApp.factory('starsUtility', function() {
    /**
     * Creates an array with index values
     *
     * @param n {Number}
     * @returns {Array}
     */
    var createRange = function(n) {
        var i = 0;
        var range = new Array(n);
        while(i < n) {
            range[i++] = i;
        }
        return range;
    };

    /**
     * Calculate percent of area to filled with color star
     *
     * @param attrs {Object}
     * @returns {Number}
     */
    var calculatePercent = function(attrs) {
        var percent, stars, selectedStars;
        if (!attrs) {
            return 0;
        } else if (attrs.ratingPercent) {
            percent = parseInt(attrs.ratingPercent) ? parseInt(attrs.ratingPercent) : 0;
            return percent > 100 ? 100 : percent;
        } else if (attrs.ratingStars) {
            stars = parseInt(attrs.stars || 5);
            selectedStars = parseFloat(attrs.ratingStars) > stars ? stars : parseFloat(attrs.ratingStars);
            return (100 / stars) * selectedStars || 0.0;
        }
    };

    /**
     * Calculate how many stars should be filled (in percent)
     *
     * @param totalStars
     * @param totalWidth
     * @param starWidth
     * @param width
     * @returns {number}
     */
    var percentFullStars = function(totalStars, totalWidth, starWidth, width) {
        var pxPerOneStar = totalWidth / totalStars;
        var percentPerOneStar = 100 / totalStars;
        return Math.ceil(width / pxPerOneStar) * percentPerOneStar;
    };

    /**
     * Calculate stars in percents
     *
     * @param totalStars
     * @param percent
     * @param precision
     * @returns {string}
     */
    var starsByPercent = function(totalStars, percent, precision) {
        var percentPerOneStar = 100 / totalStars;
        return (percent / percentPerOneStar).toFixed(precision || 2);
    };

    return {
        createRange: createRange,
        calculatePercent: calculatePercent,
        percentFullStars: percentFullStars,
        starsByPercent: starsByPercent
    };
});

// ------------------------
//        DIRECTIVE
// ------------------------
// @ngInject
adminApp.directive('starPercentageRating', function($compile, $timeout, stars, starsUtility) {
    return {
        restrict: 'A',
        
        scope: {
            percent: "=outerPercent",
            starsSelected: "=outerStarSelection",
            customFigureDrawer: "=?",
            ratingPercent:'@'
        },
        
        template: '<div class="stars" ng-style="{\'background-color\': emptyBackColor}"><div class="stars-selected" ng-style="{\'width\': percent + \'%\', \'background-color\': selColor}"></div></div>',
        
        link: function($scope, el, attrs) {
            // Configs
            var starEls = [];
            var wrapper = angular.element(el[0].querySelector('.stars'));
            var filler = angular.element(el[0].querySelector('.stars-selected'));
            attrs.$observe('ratingPercent', function(newVal) {
            	$scope.percent = $scope.prevPercent = starsUtility.calculatePercent( attrs );
             });
            $scope.howManyStars = starsUtility.createRange( attrs.stars ) || starsUtility.createRange(5);
            $scope.starRadius = parseInt( attrs.starRadius ) || 20;
            $scope.percent = $scope.prevPercent = starsUtility.calculatePercent( attrs );
            $scope.backColor = attrs.backColor || 'white';
            $scope.emptyBackColor = attrs.emptyBackColor || '#a9a9a9';
            $scope.selColor = attrs.selColor || 'gold';
            $scope.ratingDefine = attrs.ratingDefine || false;

            // Allowed to define a new rating?
            // -------------------------------
            if ($scope.ratingDefine) {

                // watch percent value to update the view
                $scope.$watch('percent', function(newValue) {
                    filler.css('width', newValue + '%');
                    $scope.starsSelected = starsUtility.starsByPercent($scope.howManyStars.length, $scope.percent);
                });

                // handle events to change the rating
                $scope.changeRating = function(e) {
                    var el = wrapper[0];
                    var w = el.offsetWidth;
                    var selected = e.clientX - el.getBoundingClientRect().left + 2;
                    var newPercent = $scope.ratingDefine == 'star' ? starsUtility.percentFullStars($scope.howManyStars.length, w, $scope.starRadius*2, selected) : Math.floor((selected * 100) / w);
                    $scope.percent = newPercent > 100 ? 100 : newPercent;
                };

                $scope.leaveRating = function() {
                    $scope.percent = $scope.prevPercent;
                };

                $scope.secureNewRating = function() {
                    $scope.prevPercent = $scope.percent;
                };
            }

            // add canvas to DOM first
            $scope.howManyStars.forEach(function() {
                var star = angular.element('<canvas class="star" ng-click="secureNewRating()" height="{{starRadius*2}}" width="{{starRadius*2}}"></canvas>');
                $compile(star)($scope);
                wrapper.append(star);
                starEls.push(star);
            });

            // we should wait for next JS 'tick' to show up the stars
            $timeout(function() {
                starEls.forEach(function(el) {
                    stars.drawRatingElement(el[0].getContext("2d"), $scope.starRadius, $scope.backColor, $scope.customFigureDrawer);
                });
                wrapper.css('visibility', 'visible'); // this to avoid to show partly rendered layout
            });

        }
    };
});