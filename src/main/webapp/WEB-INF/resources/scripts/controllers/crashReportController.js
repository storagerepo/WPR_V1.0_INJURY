var adminApp=angular.module('sbAdminApp', ['requestModule','angularFileUpload','flash']);

adminApp.controller('crashReportController', ['$scope', 'FileUploader','requestHandler', function($scope, FileUploader,requestHandler,$state) {
	$("#fileUploadError").hide();
	$("#uploadSuccessAlert").hide();
	$("#file").val(""); 
	$scope.updateList=function(){
		requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		     $scope.patientss= response.data.patientsForms;
		     $.each($scope.patientss,function(index,value) {
		    	 switch(value.patientStatus) {
		    	    case 1:
		    	        value.patientStatus="Active";
		    	        break;
		    	    case 2:
		    	    	value.patientStatus="Appointment Scheduled";
		    	        break;
		    	    default:
		    	    	value.patientStatus="Do-Not-Call";
		    	    	break;
		    	} 
		     });
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
	var uploadURL=requestHandler.getURL()+'/Injury/Staff/uploadCrashReportPDFDocuments.json';
    var uploader = $scope.uploader = new FileUploader({
        url: uploadURL
    });
    $scope.close=function(){
    	$scope.fileUploadError="";
    	uploader.clearQueue();
     };
     $scope.isError=true;
    // FILTERS
     uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return true;
        }
    },{
    	name: 'imageFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
        	$("#fileUploadError").hide();
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            var boolean='|pdf|'.indexOf(type) !== -1;
            if(!boolean){
            	$("#fileUploadError").show();
            }
            return boolean;
        }
    });

     $('.modal-backdrop .fade').click(function(){
    	 alert("dd");
    	 uploader.clearQueue();
     });
     
    // CALLBACKS

    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
    	console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
    	$scope.isError=false;
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
    	if(response.requestSuccess==false){
    		fileItem.isError=true;
        	fileItem.responseMessage=response.responseMessage;
    	}else{
    		fileItem.isError=false;
    		fileItem.responseMessage=response.responseMessage;
    	}
    	console.info('onSuccessItem', fileItem);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
        $("#uploadSuccessAlert").hide();
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
        $scope.updateList();
      
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
        
    };

    console.info('uploader', uploader);
}]); 