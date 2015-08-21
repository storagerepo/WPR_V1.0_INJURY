'use strict';

angular.module('sbAdminApp')
  .directive('errordisplay',['$location',function() {
    return {
      templateUrl:'scripts/directives/errorDisplay/errordisplay.html',
      restrict: 'E',
      link: function(scope, element, attrs){    	  
    	  scope.$watch(attrs.fadeShown, function() {
    		  setTimeout(function(){
    			  $(element).fadeOut(500);
    		  },1000);
          });
        },
      replace: true,
      scope: {
        message:'=',
        error:'=',
        success:'='
      }
    };
  }]);

	

//Service for exchange success message
angular.module('sbAdminApp').service('successMessageService', function() {

	
	 this.setMessage = function(message) {
	        this.message = message;
	    };

	    this.getMessage = function() {
	        return this.message;
	    };

	    this.setIsError = function(error) {
	        this.error = error;
	    };

	    this.getIsError = function() {
	        return this.error;
	    };
	    
	    this.setIsSuccess = function(success) {
	        this.success = success;
	    };

	    this.getIsSuccess = function() {
	        return this.success;
	    };
	    
	    
	    this.reset=function(){
	    	this.success=false;
	    	this.error=false;
	    	this.message="";
	    };
});