

var adminApp=angular.module('sbAdminApp', ['requestModule']);

adminApp.controller('ShowDoctorsCtrl', function($scope,$http,$location,$state,requestHandler,successMessageService) {
	$scope.errorMessage=successMessageService.getMessage();
	$scope.isError=successMessageService.getIsError();
	$scope.isSuccess=successMessageService.getIsSuccess();
    successMessageService.reset();
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };	
    requestHandler.getRequest("Staff/getAllDoctorss.json","").then(function(response){
    	 $scope.doctors = response.data.doctorsForms;
         $scope.sort('doctorName');
        });
	
    $scope.viewDoctors=function(id)
    {
    	 requestHandler.getRequest("viewDoctors.json?id="+id,"").then( function(response) {
    		 	$scope.doctorDetails=response.data.doctorsForm;
    	
    		$scope.workingDays="";
    			 var workingDaysArray=$scope.doctorDetails.workingDays;
    			
    			 for ( var i = 0; i < workingDaysArray.length; i++) {
    				 	if(workingDaysArray[i]==1){
    				 		$scope.workingDays="Sun,";
    						
    					}
    					else if(workingDaysArray[i]==2){
    						$scope.workingDays=$scope.workingDays+"Mon,";
    						
    					}
    					else if(workingDaysArray[i]==3){
    						$scope.workingDays=$scope.workingDays+"Tue,";
    		    			
    					}
    					else if(workingDaysArray[i]==4){
    						$scope.workingDays=$scope.workingDays+"Wed,";
    		    			
    					}
    					else if(workingDaysArray[i]==5){
    						$scope.workingDays=$scope.workingDays+"Thu,";
    		    			
    					
    					}
    					else if(workingDaysArray[i]==6){
    						$scope.workingDays=$scope.workingDays+"Fri,";
    		    			
    					}
    					else if(workingDaysArray[i]==7){
    						$scope.workingDays=$scope.workingDays+"Sat,";
    		    					
    					}
    			 }
    			$scope.doctorDetails.workingDays= $scope.workingDays;
    			$scope.doctorDetails.workingDays= $scope.doctorDetails.workingDays.substring(0, $scope.doctorDetails.workingDays.length - 1);
    			$("#myModall").modal("show");
       	      
         });
    };
    
   
    $scope.deleteDoctor=function(id)
	  {
if(confirm("Are you sure to delete Doctor ?")){
		  requestHandler.deletePostRequest("Admin/deleteDoctors.json?id=",id).then(function(results){
			  successMessageService.setMessage("You have Successfully Deleted!");
	            successMessageService.setIsError(0);
	            successMessageService.setIsSuccess(1);
	        
			  $state.reload('dashboard.doctor');
			  
		     });
	}
	}
   
    
 
});

adminApp.controller('AddDoctorsCtrl', function($scope,$http,$location,$state,requestHandler,successMessageService) {

	$scope.myFormButton=true;
	$scope.doctor={};
	$scope.doctor.workingDays=["",2,3,4,5,6,7];
	$scope.sun=true;
	$scope.mon=false;
	$scope.tue=false;
	$scope.wed=false;
	$scope.thu=false;
	$scope.fri=false;
	$scope.sat=false;
	$scope.addWorkingDays=function(value){
		if(value==0){
			$scope.sun=false;
		}
		else if(value==1){
			$scope.mon=false;
		}
		else if(value==2){
			$scope.tue=false;
		}
		else if(value==3){
			$scope.wed=false;
		}
		else if(value==4){
			$scope.thu=false;
		}
		else if(value==5){
			$scope.fri=false;
		}
		else if(value==6){
			$scope.sat=false;
		}
		$scope.doctor.workingDays[value]=value+1;
	};
	
	$scope.removeWorkingDays=function(value){
		if(value==0){
			$scope.sun=true;
		}
		else if(value==1){
			$scope.mon=true;
		}
		else if(value==2){
			$scope.tue=true;
		}
		else if(value==3){
			$scope.wed=true;
		}
		else if(value==4){
			$scope.thu=true;
		}
		else if(value==5){
			$scope.fri=true;
		}
		else if(value==6){
			$scope.sat=true;
		}
		$scope.doctor.workingDays[value]="";
	};
	
	$scope.title=$state.current.title;
	$scope.saveDoctor=function()
	{
		requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			successMessageService.setMessage("You have Successfully Added Doctor");
            successMessageService.setIsError(0);
            successMessageService.setIsSuccess(1);
			$location.path('dashboard/doctor');
		});
	};

});

adminApp.controller('EditDoctorController', function($scope,$http,$location,$stateParams,$state,requestHandler,successMessageService) {
	
	$scope.myFormButton=false;
	$scope.sun=true;
	$scope.mon=true;
	$scope.tue=true;
	$scope.wed=true;
	$scope.thu=true;
	$scope.fri=true;
	$scope.sat=true;
	$scope.workingDays=["","","","","","",""];
	$scope.title=$state.current.title;
	
	requestHandler.getRequest("Admin/getDoctors.json?id="+$stateParams.id,"").then(function(response){
		 $scope.doctor = response.data.doctorsForm;
		 var workingDaysArray=$scope.doctor.workingDays;
		 for ( var i = 0; i < workingDaysArray.length; i++) {
			 	if(workingDaysArray[i]==1){
			 		$scope.workingDays[0]=1;
					$scope.sun=false;
				}
				else if(workingDaysArray[i]==2){
					$scope.workingDays[1]=2;
					$scope.mon=false;
				}
				else if(workingDaysArray[i]==3){
					$scope.workingDays[2]=3;
					$scope.tue=false;
				}
				else if(workingDaysArray[i]==4){
					$scope.workingDays[3]=4;
					$scope.wed=false;
				}
				else if(workingDaysArray[i]==5){
					$scope.workingDays[4]=5;
					$scope.thu=false;
				}
				else if(workingDaysArray[i]==6){
					$scope.workingDays[5]=6;
					$scope.fri=false;
				}
				else if(workingDaysArray[i]==7){
					$scope.workingDays[6]=7;
					$scope.sat=false;
				}
		 }
		$scope.doctor.workingDays= $scope.workingDays;
	});
	
	$scope.addWorkingDays=function(value){
		if(value==0){
			$scope.sun=false;
		}
		else if(value==1){
			$scope.mon=false;
		}
		else if(value==2){
			$scope.tue=false;
		}
		else if(value==3){
			$scope.wed=false;
		}
		else if(value==4){
			$scope.thu=false;
		}
		else if(value==5){
			$scope.fri=false;
		}
		else if(value==6){
			$scope.sat=false;
		}
		$scope.doctor.workingDays[value]=value+1;
		
	};
	
	$scope.removeWorkingDays=function(value){
		if(value==0){
			$scope.sun=true;
		}
		else if(value==1){
			$scope.mon=true;
		}
		else if(value==2){
			$scope.tue=true;
		}
		else if(value==3){
			$scope.wed=true;
		}
		else if(value==4){
			$scope.thu=true;
		}
		else if(value==5){
			$scope.fri=true;
		}
		else if(value==6){
			$scope.sat=true;
		}
		$scope.doctor.workingDays[value]="";
	};
	
	
	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			  successMessageService.setMessage("You have Successfully updated!");
	            successMessageService.setIsError(0);
	            successMessageService.setIsSuccess(1);
			  $location.path('dashboard/doctor');
			});
			
	  
	};
});


//Service for exchange success message
angular.module('sbAdminApp').service('successMessageService', function() {


	 this.setMessage = function(message) {
	        this.message = message;
	    };

	    this.getMessage = function() {
	        return this.message;
	    };

	    this.setIsError = function(error) {
	        this.error = error;
	    };

	    this.getIsError = function() {
	        return this.error;
	    };
	    
	    this.setIsSuccess = function(success) {
	        this.success = success;
	    };

	    this.getIsSuccess = function() {
	        return this.success;
	    };

});



