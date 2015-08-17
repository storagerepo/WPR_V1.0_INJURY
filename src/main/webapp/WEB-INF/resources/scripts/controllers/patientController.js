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

adminApp.controller('ShowPatientController', function($scope,$http,$location,$state,$rootScope,requestHandler,$http) {
	
	$scope.title="Add Patient";
	
	
	
	
	
	 requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		
	     $scope.patientss= response.data.patientsForms;
	     
	     });
	
	$scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

	
	$scope.getPatientList=function(){
		
		 requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		     $scope.patients= response.patientsForms;
		     $scope.sort('name');

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
$("#file").val(""); 
		$("#myModal").modal("show");
		
		
		
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


adminApp.controller('AppController', ['$scope', 'FileUploader', function($scope, FileUploader,requestHandler) {
    var uploader = $scope.uploader = new FileUploader({
        url: 'http://localhost:8080/Injury/Staff/addPatientFromFile.json',
        queueLimit: 1
    });
    
    $scope.close=function(){
    	
    	
    	 uploader.clearQueue();
    	
    };

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
       
        $scope.us= response.msg1;
        $scope.u= response.msg0;
        
        
        if($scope.us== null && $scope.u== null)
        	{
        	$scope.a= "File Processed Successfully!";
            
        	}
        else
        	{
        	$scope.a="File Upload Failed!";
            $scope.b="Reason:";
        	$scope.upload_status= response.msg1;
        	$scope.upload_1= response.msg2;
        	$scope.upload_2= response.msg3;
        	$scope.upload_3= response.msg4;
        	$scope.upload_4= response.msg5;
        	$scope.upload_5= response.msg6;
        	$scope.upload_6= response.msg7;
        	$scope.upload_7= response.msg8;
        	$scope.upload_8= response.msg9;
        	$scope.upload_9= response.msg10;
        	$scope.upload_10= response.ms11;
        	$scope.upload_11= response.msg12;
        	$scope.upload_12= response.msg13;
        	$scope.upload_13= response.msg14;
        	$scope.upload_14= response.msg15;
        	$scope.upload_15= response.msg16;
        	$scope.upload_16= response.msg17;
        	$scope.upload_17= response.msg18;
        	$scope.upload_18= response.msg19;
        	$scope.upload_19= response.msg20;
        	$scope.upload_20= response.msg21;
        	$scope.upload_21= response.msg22;
        	$scope.upload_22= response.msg23;
        	$scope.upload_23= response.msg24;
        	$scope.upload_24= response.msg25;
        	$scope.upload_25= response.msg26;
        	$scope.upload_26= response.msg27;
        	$scope.upload_27= response.msg28;
        	$scope.upload_28= response.msg29;
        	$scope.upload_29= response.msg30;
        	$scope.upload_30= response.msg31;
        	$scope.upload_31= response.msg32;
        	$scope.upload_32= response.msg33;
        	$scope.upload_33= response.msg34;
        	$scope.upload_34= response.msg35;
        	$scope.upload_35= response.msg36;
        	$scope.upload_36= response.msg37;
        	$scope.upload_37= response.msg38;
        	$scope.upload_38= response.msg39;
        	$scope.upload_39= response.msg40;
        	$scope.upload_40= response.msg41;
        	$scope.upload_41= response.msg42;
        	$scope.upload_42= response.msg43;
        	$scope.upload_43= response.msg44;
        	$scope.upload_44= response.msg45;
        	$scope.upload_45= response.msg46;
        	$scope.upload_46= response.msg47;
        	$scope.upload_47= response.msg48;
        	$scope.upload_48= response.msg49;
        	$scope.upload_49= response.msg0;
        	  	}
      
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
      
        $("#uploadSuccessAlert").show();
        uploader.clearQueue();

        
    };

    console.info('uploader', uploader);
}]);



adminApp.controller('EditPatientController', function($scope,$http,$location,$stateParams,requestHandler){
	
	$scope.patientid=$stateParams.id;
	
	requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
		
		     $scope.patient= response.data.patientsForm;
		     
		     
		  
		     });
	requestHandler.getRequest("Staff/getCallLogsId.json","").then( function(response) {
		
	     $scope.callLogs= response.data.callLogsForms;
	     
	     
	  
	     });
	requestHandler.getRequest("Admin/getDoctorId.json","").then( function(response) {
		
	     $scope.doctor= response.data.doctorsForms;
	     
	     
	  
	     });
		
		$scope.updatePatient=function(){
			
			
			requestHandler.postRequest('Staff/updatePatients.json',$scope.patient).then(function(response) {
				
				$location.path("dashboard/patient");
			});
		};
		
	
});