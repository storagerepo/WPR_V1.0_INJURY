var myApp=angular.module("requestModule",[]);

myApp.factory("requestHandler",['$http',function($http){
    
    var requestObj={};

    requestObj.getRequest=function(requestURL,params){

         requestURL="http://localhost:8081/Injury/"+requestURL;
         return $http.get(requestURL,params).then(function (results) {  
            return results;   
         });
    };

    
requestObj.postFileUpload=function(requestURL,data,params){      
        
        requestURL="http://localhost:8081/Injury/"+requestURL;
        transformRequest: angular.identity,
        headers={'Content-Type': undefined},
        data= data;
        

        return $http.post(requestURL,data,params).then(function (results) {
                return results;
         });
    };
    
    requestObj.postRequest=function(requestURL,params){      
       
        requestURL="http://localhost:8081/Injury/"+requestURL;
      
        return $http.post(requestURL,params).then(function (results) {
                return results;
         });
    };
 
    
    requestObj.deletePostRequest=function(requestURL,params){
    	 requestURL="http://localhost:8081/Injury/"+requestURL+params;
    	 return $http({
			  method : "POST",
			  url : requestURL,
			  }).success(function(response){
				 return true;
		});
    };
    
    return requestObj;

}]);
