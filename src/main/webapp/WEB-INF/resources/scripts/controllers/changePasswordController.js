var adminApp=angular.module('sbAdminApp', ['requestModule']);
adminApp.controller('changePasswordController', function($scope,$state,requestHandler,successMessageService) {
	$scope.errorMessage=successMessageService.getMessage();
	$scope.isError=successMessageService.getIsError();
	$scope.isSuccess=successMessageService.getIsSuccess();
    successMessageService.reset();
  
	

	$scope.myFormButton=true;

	$scope.save=function()
	{
		
		requestHandler.postRequest("changePassword.json?newPassword="+encodeURIComponent($scope.staff.confirmpassword),"").then(function(response){
			 $scope.value=response.data.requestSuccess;
			  
			  if($scope.value==true)
				  {
				  successMessageService.setMessage("Password Changed Successfully!");
					 successMessageService.setIsError(0);
			          successMessageService.setIsSuccess(1);
					$state.reload('dashboard.Changepassword');

				  }
		
		});
	};
	
	
	

});
adminApp.directive("password", function ($q, $timeout,requestHandler) {
    var CheckPasswordExists = function (isNew) {
        if(isNew!=true)
            return true;
        else
            return false;
    };
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attributes, ngModel) {
            ngModel.$asyncValidators.password = function (modelValue) {
            	alert(encodeURIComponent(modelValue));
            	var defer = $q.defer();
                $timeout(function () {
                	
                    var isNew;
                    var sendRequest=requestHandler.postRequest("checkPassword.json?oldPassword="+encodeURIComponent(modelValue),0).then(function(results){
                        isNew=results.data.requestSuccess;
                    });

                    sendRequest.then(function(){

                        if (CheckPasswordExists(!isNew)){
                            defer.resolve();
                        }
                        else{
                            defer.reject();
                        } 
                    });
                    isNew = false;
                }, 10);

                return defer.promise;
            }
        }
    };

});

