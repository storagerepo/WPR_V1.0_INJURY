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
				     $scope.sort('patientStatus');
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
			   
		   }
		   else if(!$rootScope.isAdmin){
			   $scope.staff=true;
			   requestHandler.getRequest("Staff/getPatientsByAccessToken.json","").then(function(response){
					
				     $scope.patientss= response.data.patientsForm;
				     $scope.sort('patientStatus');
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
		   }
			
	
		    
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
	 
	$scope.staffGetPatientList=function(){
		requestHandler.getRequest("Staff/getPatientsByAccessToken.json","").then(function(response){
			$scope.patientss= response.data.patientsForm;
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
	
	$scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

	
	$scope.getPatientList=function(){
		alert("ppp");
		 requestHandler.getRequest("Staff/getAllPatientss.json","").then(function(response){
		     $scope.patients= response.patientsForms;
		     $scope.sort('name');
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
			
$("#fileUploadError").hide();
		
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
		var doUpdate=requestHandler.postRequest('Staff/saveUpdatePatients.json',$scope.patients).then(function(response) {
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
		 $("#assignDoctorModel").modal("show");
			
		$scope.assignPatientId=id;
		$scope.selectedCaller=callerId;
		requestHandler.getRequest("Staff/getPatients.json?id="+$scope.assignPatientId).then( function(response) {
			$scope.patients= response.data.patientsForm;
		
		requestHandler.getRequest("Staff/getClinicId.json","").then( function(response) {
			$scope.clinic= response.data.clinicsForms;
		 
		   var ClinicId=0;
			if($scope.patients.clinicId==null){
				ClinicId=0;
			}
			else{
				ClinicId=$scope.patients.clinicId;
			}
				 requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
					$scope.doctor= response.data.doctorsForm;
				  });
		});	
		});		
		$scope.doctorName=function(){
			var ClinicId=0;
			if($scope.patients.clinicId==null){
				ClinicId=0;
			}
			else{
				ClinicId=$scope.patients.clinicId;
			}
			requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
					$scope.doctor= response.data.doctorsForm;
				   });
                    }
		};

	
	//Update the Doctor
	$scope.updateDoctor=function()
	{
		var doUpdate=requestHandler.postRequest('Staff/saveUpdatePatients.json',$scope.patients).then(function(response) {
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
	$scope.patientStatus=function(){
		 if($rootScope.isAdmin){
			 if($scope.Status==4){
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
				   
				 }
			 else{
	 requestHandler.getRequest("Staff/adminPatientStatus.json?patientStatus="+$scope.Status,"").then( function(response) {
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
	
			 }
		 }
		
		 if(!$rootScope.isAdmin){
			
			 	
			 if($scope.Status==4){
				 requestHandler.getRequest("Staff/getPatientsByAccessToken.json","").then(function(response){
						
				     $scope.patientss= response.data.patientsForm;
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
			
				 
			 }
			 else{
				 requestHandler.getRequest("Staff/staffPatientStatus.json?patientStatus="+$scope.Status,"").then( function(response) {
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
			 }
		 }
	}
});


adminApp.controller('AppController', ['$scope', 'FileUploader','requestHandler', function($scope, FileUploader,requestHandler,$state) {
	
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
            return this.queue.length < 10;
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
        console.info('onSuccessItem', fileItem, response, status, headers);
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
        $("#uploadSuccessStatus").html(response);
        $scope.updateList();
      
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
       $("#uploadSuccessAlert").show();
       // uploader.clearQueue();
        
    };

    console.info('uploader', uploader);
}]);

adminApp.controller('AddPatientController', function($scope,$state,$http,$location,$stateParams,requestHandler,Flash) {
	$scope.myFormButton=true;
	
	
	$scope.patient={};
	$scope.title=$state.current.title;
	 $scope.patient.patientId =$stateParams.id;
	
  requestHandler.getRequest("Admin/getStaffId.json","").then( function(response) {
			
	     $scope.staff= response.data.staffForms;
	   
	     
	  
	     });

		//getting patient details by id
	 
	
	
		requestHandler.getRequest("Staff/getClinicId.json","").then( function(response) {
			
		     $scope.clinic= response.data.clinicsForms;
		 
		     });
	
		$scope.doctorName=function(){
			var ClinicId=0;
			if($scope.patient.clinicId==null){
				ClinicId=0;
			}
			else{
				ClinicId=$scope.patient.clinicId;
			}
				 requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
						
				    $scope.doctor= response.data.doctorsForm;
				  
				   });

				}
		
	
	$scope.savePatient=function()
	{
		//$scope.patient.gender=$scope.patientGender;
		
	requestHandler.postRequest("Staff/saveUpdatePatients.json",$scope.patient).then(function(response){
		  
			Flash.create('success', "You have Successfully Added Patient!");
            
			$location.path('dashboard/patient');
		});
	};
	
	
});

adminApp.controller('EditPatientController', function($scope,$http,$state,$location,$stateParams,requestHandler,Flash){
	$scope.myFormButton=false;
	var patientOriginal="";
	
	$scope.patient={};
	$scope.title=$state.current.title;
	 $scope.patient.patientId =$stateParams.id;

	
	 requestHandler.getRequest("Admin/getStaffId.json","").then( function(response) {
			
	     $scope.staff= response.data.staffForms;
	   
	     
	  
	     });

		//getting patient details by id
		requestHandler.getRequest("Staff/getPatients.json?id="+$stateParams.id,"").then( function(response) {
			//alert(JSON.stringify(response));
			patientOriginal=angular.copy(response.data.patientsForm);
			
			$scope.patient= response.data.patientsForm;
			      
		if($scope.patient.clinicId==0)
		{
	$scope.patient.clinicId="";
	}
else
	{
	$scope.patient.clinicId=$scope.patient.clinicId;
	}
		//setting doctor id from patient 
		if($scope.patient.doctorId==0)
			{
		$scope.patient.doctorId="";
		}
	else
		{
		$scope.patient.doctorId=$scope.patient.doctorId;
		}
		
	
		requestHandler.getRequest("Staff/getClinicId.json","").then( function(response) {
			
		     $scope.clinic= response.data.clinicsForms;
		 
		     });
		var ClinicId=$scope.patient.clinicId;
		requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
			
		    $scope.doctor= response.data.doctorsForm;
		  
		   });
		$scope.doctorName=function(){
			var ClinicId=0;
			if($scope.patient.clinicId==null){
				ClinicId=0;
			}
			else{
				ClinicId=$scope.patient.clinicId;
			}
				 requestHandler.getRequest("getNameByClinicId.json?clinicId="+ClinicId,"").then( function(response) {
						
				    $scope.doctor= response.data.doctorsForm;
				  
				   });

				}
		 });
	

$scope.updatePatient=function(){
	requestHandler.postRequest('Staff/saveUpdatePatients.json',$scope.patient).then(function(response) {
	            Flash.create("success","You have Successfully updated!");
				$location.path("dashboard/patient");
			});
		};
		
		
		$scope.isClean = function() {
	        return angular.equals (patientOriginal, $scope.patient);
	    
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