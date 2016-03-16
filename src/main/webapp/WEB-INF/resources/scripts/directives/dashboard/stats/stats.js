'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('sbAdminApp')
    .directive('stats',function() {
    	return {
  		templateUrl:'scripts/directives/dashboard/stats/stats.html',
  		restrict:'E',
  		replace:true,
  		scope: {
        'model': '=',
        'comments': '@',
        'number': '@',
        'name': '@',
        'colour': '@',
        'details':'@',
        'type':'@',
        'goto':'@'
  		}
  		
  	}
  });
angular.module('sbAdminApp')
.directive('county',function() {
	return {
		templateUrl:'scripts/directives/dashboard/stats/county.html',
		restrict:'E',
		replace:true,
		scope: {
    'model': '=',
    'county': '@',
    'colour':'@',
    'totalcount': '@',
    'goto':'@',
    'activecount':'@',
    'voicemailcount':'@',
    'scheduledcount':'@'
		}
		
	}
});
// Tier Directive
angular.module('sbAdminApp')
.directive('tier',function() {
	return {
		templateUrl:'scripts/directives/dashboard/stats/tier.html',
		restrict:'E',
		replace:true,
		scope: {
    'model': '=',
    'comments': '@',
    'number': '@',
    'name': '@',
    'colour': '@',
    'details':'@',
    'type':'@',
    'goto':'@'
		}
		
	}
});
