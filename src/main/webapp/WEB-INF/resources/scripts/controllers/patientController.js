var adminApp=angular.module('sbAdminApp', ['requestModule','angularFileUpload','flash']);

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

adminApp.controller('ShowPatientController', function($scope,$http,$location,$state,$rootScope,requestHandler,$http,successMessageService,Flash) {
	
	$scope.title="Add Patient";
		    if($rootScope.isAdmin){
			   $scope.admin=true;
			   $scope.staff=true;
			   requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
					
				     $scope.patientss= response.data.patientsForms;
				     $scope.sort('name');
				     });
			   
		   }
		   else if(!$rootScope.isAdmin){
			   $scope.staff=true;
			   requestHandler.getRequest("Staff/getPatientsByAccessToken.json","").then(function(response){
					
				     $scope.patientss= response.data.patientsForm;
				     
				     });
		   }
			
	
		    
	$scope.updateList=function(){
		
		requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		     $scope.patientss= response.data.patientsForms;
		     });
	};
	 
	$scope.staffGetPatientList=function(){
		requestHandler.getRequest("Staff/getPatientsByAccessToken.json","").then(function(response){
			$scope.patientss= response.data.patientsForm;
		     });
	};
	
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
				  Flash.create('success', "You have Successfully Deleted!");  
				  if($rootScope.isAdmin){
					  $scope.updateList();
				  }
				  else if(!$rootScope.isAdmin){
					  $scope.staffGetPatientList();
				  }
		         
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
		
	$scope.assignCaller=function(id,callerId)
	{
		$scope.title="Assign Caller";
		$scope.assignPatientId=id;
		$scope.selectedCaller=callerId;
		requestHandler.getRequest("Staff/getPatients.json?id="+$scope.assignPatientId).then( function(response) {
			$scope.patients= response.data.patientsForm;
		});
		requestHandler.getRequest("Admin/getStaffId.json","").then( function(response) {			
			     $scope.staffs= response.data.staffForms;
			     $("#assignCallerModel").modal("show");
			});
		
		
		
	};
	
	//Update the caller
	$scope.updateCaller=function()
	{
		var doUpdate=requestHandler.postRequest('Staff/updatePatients.json',$scope.patients).then(function(response) {
			$("#assignCallerModel").modal("hide");
			$('.modal-backdrop').hide();

			 Flash.create('success', "You have Successfully Assigned!"); 
		});
		
		doUpdate.then(function(){ 
			  if($rootScope.isAdmin){
				  $scope.updateList();
			  }
			  else if(!$rootScope.isAdmin){
				  $scope.staffGetPatientList();
			  }
			
		});
		
		
	};
	
	$scope.assignDoctor=function(id,callerId)
	{
		$scope.title="Assign Doctor";
		$scope.assignPatientId=id;
		$scope.selectedCaller=callerId;
		requestHandler.getRequest("Staff/getPatients.json?id="+$scope.assignPatientId).then( function(response) {
			$scope.patients= response.data.patientsForm;
		});
		
			requestHandler.getRequest("/Staff/getAllDoctorss.json","").then( function(response) {			
			     $scope.doctors= response.data.doctorsForms;
			     $("#assignDoctorModel").modal("show");
			});
		
		
		
	};
	
	//Update the Doctor
	$scope.updateDoctor=function()
	{
		var doUpdate=requestHandler.postRequest('Staff/updatePatients.json',$scope.patients).then(function(response) {
			$("#assignDoctorModel").modal("hide");
			$('.modal-backdrop').hide();
		});
		
		doUpdate.then(function(){
			$("#assignDoctorModel").modal("hide");
			Flash.create('success', "You have Successfully Assigned!");  
			  if($rootScope.isAdmin){
				  $scope.updateList();
			  }
			  else if(!$rootScope.isAdmin){
				  $scope.staffGetPatientList();
			  }
		});
		
		
	};

});


adminApp.controller('AppController', ['$scope', 'FileUploader', function($scope, FileUploader,requestHandler,$state) {
    var uploader = $scope.uploader = new FileUploader({
        url: 'http://192.168.1.122:8081/Injury/Staff/addPatientFromFile.json',
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
       
        $("#uploadSuccessStatus").html(response);
        $scope.updateList();
      
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
      
        $("#uploadSuccessAlert").show();
        uploader.clearQueue();
       
        
        
    };

    console.info('uploader', uploader);
}]);



adminApp.controller('EditPatientController', function($scope,$http,$location,$stateParams,requestHandler,Flash){
	
	$scope.patientid=$stateParams.id;
	
	requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
		
		     $scope.patient= response.data.patientsForm;
		     
		     
		  
		     });
	requestHandler.getRequest("Admin/getStaffId.json","").then( function(response) {
		
	     $scope.staff= response.data.staffForms;
	     
	     
	  
	     });
	requestHandler.getRequest("Staff/getDoctorId.json","").then( function(response) {
		
	     $scope.doctor= response.data.doctorsForms;
	     
	     
	  
	     });
		
		$scope.updatePatient=function(){
			
			
			requestHandler.postRequest('Staff/updatePatients.json',$scope.patient).then(function(response) {
	            Flash.create("success","You have Successfully updated!");
				$location.path("dashboard/patient");
			});
		};
		
	
});

adminApp.controller('roleController', function($scope,$http,$location,requestHandler) {
	 
	  requestHandler.postRequest("Staff/getCurrentRole.json","").then(function(response) {
		 
	   $scope.admin=false;
	   $scope.staff=false;
	   if(response.data.role=="ROLE_ADMIN"){
		   $scope.admin=true;
		   $scope.staff=true;
	   }
	   else if(response.data.role=="ROLE_STAFF"){
		   $scope.staff=true;
	   }
	   else{
		   $location.path('/logout');
	   }
	   
 	});	

});