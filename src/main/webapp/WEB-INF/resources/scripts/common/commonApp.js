var commonApp = angular.module('commonApp', ['requestModule', 'flash']);

commonApp.controller('userContactUsController',function($scope,requestHandler,Flash){
	$scope.init=function(){
		$scope.contactUsForm={};
		$scope.submitted=false;
		$scope.isSubmit=false;
		$scope.submitText="Submit Us";
	};
	
	$scope.saveContactUs=function(){
		$scope.isSubmit=true;
		$scope.submitText="Submitting....";
		requestHandler.postRequest("/mergeContactUs.json",$scope.contactUsForm).then(function(){
			Flash.create('success','Thanks! Our support team will get back soon!!!');
			$scope.init();
		});
	};
	
	$scope.init();
});

commonApp
.directive(
		'validateEmail',
		function() {
			var EMAIL_REGEXP = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,5})$/;
			return {
				require : 'ngModel',
				restrict : '',
				link : function(scope, elm, attrs, ctrl) {
					// only apply the validator if ngModel is present
					// and Angular has added the email validator
					if (ctrl && ctrl.$validators.email) {

						// this will overwrite the default Angular email
						// validator
						ctrl.$validators.email = function(modelValue) {
							return ctrl.$isEmpty(modelValue)
									|| EMAIL_REGEXP.test(modelValue);
						};
					}
				}
			};
		});

commonApp.directive('validateMobile', function() {
var USA_MOB_EXPR = /^(\([0-9]{3}\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
var USA_MOB_EXPR_NOSPACE = /^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
var USA_MOB_EXPR_NO = /^[0-9]{10}$/;
return {
require : 'ngModel',
restrict : '',
link : function(scope, elm, attrs, ngModel) {
	ngModel.$validators.validateMobile = function(modelValue) {
		if (modelValue == "" || modelValue == undefined) {
			return true;
		} else {
			return USA_MOB_EXPR.test(modelValue)
					|| USA_MOB_EXPR_NO.test(modelValue)
					|| USA_MOB_EXPR_NOSPACE.test(modelValue);
		}

	};
}
};
});

commonApp.directive('validateName', function() {
var NAME_EXPR = /^ *([a-zA-Z]+ ?)+ *$/;
// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
return {
require : 'ngModel',
restrict : '',
link : function(scope, elm, attrs, ngModel) {
	ngModel.$validators.validateName = function(modelValue) {
		return NAME_EXPR.test(modelValue);// ||USA_MOB_EXPR_WITH_BR.test(modelValue);
	};
}
};
});
