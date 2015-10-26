var myApp=angular.module("requestModule",[]);

myApp.factory("requestHandler",['$http',function($http){
    
    var requestObj={};
    var appURL="http://192.168.1.122:8081";

    requestObj.getRequest=function(requestURL,params){

         requestURL=appURL+"/Injury/"+requestURL;
         return $http.get(requestURL,params).then(function (results) {  
            return results;   
         });
    };

    
requestObj.postFileUpload=function(requestURL,data,params){      
        
        requestURL=appURL+"/Injury/"+requestURL;
        transformRequest: angular.identity,
        headers={'Content-Type': undefined},
        data= data;
        

        return $http.post(requestURL,data,params).then(function (results) {
                return results;
         });
    };
    
    requestObj.postRequest=function(requestURL,params){      
       
        requestURL=appURL+"/Injury/"+requestURL;
      
        return $http.post(requestURL,params).then(function (results) {
                return results;
         });
    };
 
    
    requestObj.deletePostRequest=function(requestURL,params){
    	 requestURL=appURL+"/Injury/"+requestURL+params;
    	 return $http({
			  method : "POST",
			  url : requestURL,
			  }).success(function(response){
				 return true;
		});
    };
    
    // Get Geo Location
    requestObj.getGeoLocation=function(requestURL,params){
   	 requestURL=requestURL+params;
   	 return $http({
			  method : "POST",
			  url : requestURL,
			  }).success(function(response){
				 return true;
		});
   };
    
    return requestObj;

}]);
