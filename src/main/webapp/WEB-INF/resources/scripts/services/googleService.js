var adminAppGoogle=angular.module('googleModule',[]);

adminAppGoogle.service('googleService', function($q){
	var clientId= '1025762346478-39pbkfjt5v2hf1sn9e49lu6thj3t59ag.apps.googleusercontent.com';
    var scopes='https://www.googleapis.com/auth/cloudprint';
  
    
    this.login = function () {
    	 var deferred = $q.defer();
        gapi.auth.authorize({ 
            client_id: clientId, 
            scope: scopes, 
            immediate: false
        },handleAuthResult = function(authResult) {
            if (authResult && !authResult.error) {
                var data = authResult;
                gapi.client.load('oauth2', 'v2', function () {
                    var request = gapi.client.oauth2.userinfo.get();
                    request.execute(function (resp) {
                        data.email = resp.email;
                    });
                });
                deferred.resolve(data);
            } else {
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };

});