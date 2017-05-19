var myApp=angular.module("requestModule",[]);

myApp.factory("requestHandler",['$http',function($http){
    
    var requestObj={};
    //var appURL=window.location.origin+"/";
    var appURL=window.location.origin+"/Injury/";
    
    
    requestObj.getURL=function(){
    	return appURL;
    };

    requestObj.getRequest=function(requestURL,params){

         requestURL=appURL+requestURL;
         return $http.get(requestURL,params).then(function (results) {  
            return results;   
         });
    };

   
requestObj.postFileUpload=function(requestURL,data,params){      
        
        requestURL=appURL+requestURL;
        transformRequest: angular.identity,
        headers={'Content-Type': undefined},
        data= data;
        

        return $http.post(requestURL,data,params).then(function (results) {
                return results;
         });
    };
    
    requestObj.postRequest=function(requestURL,params){      
       
        requestURL=appURL+requestURL;
        return $http.post(requestURL,params).then(function (results) {
                return results;
         });
    };
    
    
    requestObj.postExportRequest=function(requestURL,params){
        requestURL=appURL+requestURL;
        return $http({
		    	    url: requestURL,
		    	    method: "POST",
		    	    data: params, //this is your json data string
		    	    headers: {
		    	       'Content-type': 'application/json'
		    	    },
		    	    responseType: 'arraybuffer'
    		}).success(function (results) {
                return results;
         });
    };
    
    
    requestObj.deletePostRequest=function(requestURL,params){
    	 requestURL=appURL+requestURL+params;
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
