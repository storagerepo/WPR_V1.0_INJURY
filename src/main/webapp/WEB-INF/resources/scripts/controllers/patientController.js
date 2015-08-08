var adminApp=angular.module('sbAdminApp', ['requestModule','angularFileUpload']);

adminApp.directive('fileChange', function () {

    var linker = function ($scope, element, attributes) {
        // onChange, push the files to $scope.files.
        element.bind('change', function (event) {
            var files = event.target.files;
            $scope.$apply(function () {
                for (var i = 0, length = files.length; i < length; i++) {
                    $scope.files.push(files[i]);
                }
            });
        });
    };

    return {
        restrict: 'A',
        link: linker
    };

});

adminApp.controller('ShowPatientController', function($scope,$http,$location,$state,$rootScope,requestHandler) {
	
	$scope.title="Add Patient";
	
	
	
	
	
	 requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		
	     $scope.patients= response.data.patientsForms;
	     $scope.sort("firstName");
	     });
	
	$scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

	
	$scope.getPatientList=function(){
		
		 requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		     $scope.patients= response.patientsForms;
		     });
	};
	
	$scope.deletePatient=function(id){
		if(confirm("Are you sure to delete patient?")){
			  requestHandler.deletePostRequest("Staff/deletePatients.json?id=",id).then(function(response){
			 $state.reload('dashboard.patient');
		});
		
		}
	};
	
	$scope.addModel=function()
	{
		$scope.title="Add Patient";
$("#uploadSuccessAlert").hide();
		$("#myModal").modal("show");
		
		$scope.files = [];
		
		
	};
	
	$scope.editModal=function()
	{
		$scope.title="Edit Patient";
		$("#myModal").modal("show");
	};
	
	$scope.viewPatientModal=function(id){
		$scope.viewPatientTitle="View Patient";
		$("#viewPatientModal").modal("show");
		  requestHandler.deletePostRequest("Staff/getPatients.json?id="+id).success( function(response) {
			$scope.patients= response.patientsForm;
		  });

		
	};
	
	$scope.uploadFile=function($files){
		alert("upload File");
		alert($files[0]);
	};
	
	$scope.fileUpload=function(){
		
		alert("ok");
		alert($scope.path);
	};
		   

});


adminApp.controller('AppController', ['$scope', 'FileUploader', function($scope, FileUploader) {
    var uploader = $scope.uploader = new FileUploader({
        url: 'http://localhost:8089/Injury/Staff/addPatientFromFile.json',
        queueLimit: 1
    });

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });

    // CALLBACKS

    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function(addedFileItems) {
        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function(item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function(progress) {
        console.info('onProgressAll', progress);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
        $scope.upload_status="File Processed Successfully!";
        $("#uploadSuccessAlert").show();
    };

    console.info('uploader', uploader);
}]);



adminApp.controller('EditPatientController', function($scope,$http,$location,$stateParams,requestHandler){
	
	$scope.patientid=$stateParams.id;
	
	requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
		
		     $scope.patient= response.data.patientsForm;
		  
		     });
		
		$scope.updatePatient=function(){
			requestHandler.getRequest('Staff/mergePatients.json',$scope.patient).then(function(response) {
				$location.path("dashboard/patient");
			});
		};
		
	
});