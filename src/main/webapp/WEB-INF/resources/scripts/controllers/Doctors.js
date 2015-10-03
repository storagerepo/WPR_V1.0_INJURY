

var adminApp=angular.module('sbAdminApp', ['requestModule','flash']);

adminApp.controller('ShowDoctorsCtrl', function($scope,$http,$location,$state,requestHandler,successMessageService,Flash) {

	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };	
    requestHandler.getRequest("Staff/getAllDoctorss.json","").then(function(response){
    	 $scope.doctors = response.data.doctorsForms;
         $scope.sort('doctorName');
        });
	
    $scope.getDoctorList=function(){
    	requestHandler.getRequest("Staff/getAllDoctorss.json","").then(function(response){
       	 $scope.doctors = response.data.doctorsForms;
           });
    };
    
    $scope.viewDoctors=function(id)
    {
    	 requestHandler.getRequest("viewDoctors.json?id="+id,"").then( function(response) {
    		 	$scope.doctorDetails=response.data.doctorsForm;
    		 	$("#myModall").modal("show");
       	      
         });
    };
    
   
	// Delete Doctor
	$scope.deleteDoctor=function(id)
	{
		
		  if(confirm("Are you sure to delete Doctor ?")){
			 
			requestHandler.deletePostRequest("Admin/deleteDoctors.json?id=",id).then(function(results){
			  $scope.value=results.data.requestSuccess;
			  console.log($scope.value);
			  if($scope.value==true)
				  {
			        Flash.create('success', "You have Successfully Deleted!");
			        $scope.getDoctorList();
				  }
			  else
				  {
				  $("#deleteDoctorModal").modal("show");
				    $scope.deleteDoctorFromPatients=function()
				    {
				    	requestHandler.postRequest("Admin/removeAssignedDoctors.json?id="+id).then(function(response){
				    		
				    		requestHandler.deletePostRequest("Admin/deleteDoctors.json?id=",id).then(function(results){
				    			$("#deleteDoctorModal").modal("hide");
				    			$('.modal-backdrop').hide();
				    			Flash.create('success', "You have Successfully Deleted!");  
				    			$scope.getDoctorList();
				    		 });
				    		 
							});
				    };
				  
				  }
		     });
		  }
	};
   
    
 
});

adminApp.controller('AddDoctorsCtrl', function($scope,$http,$location,$state,requestHandler,successMessageService,Flash) {

	$scope.myFormButton=true;
	$scope.doctor={};
	
	$scope.title=$state.current.title;
	$scope.saveDoctor=function()
	{
		requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			Flash.create('success', "You have Successfully Added Doctor!");
            
			$location.path('dashboard/doctor');
		});
	};

});

adminApp.controller('EditDoctorController', function($scope,$http,$location,$stateParams,$state,requestHandler,successMessageService,Flash) {
	
	$scope.myFormButton=false;
	
	$scope.title=$state.current.title;
	var doctorOriginal="";
	
	requestHandler.getRequest("Admin/getDoctors.json?id="+$stateParams.id,"").then(function(response){
		 $scope.doctor = response.data.doctorsForm;
		
		doctorOriginal=angular.copy(response.data.doctorsForm);
		
		$('#officeHoursFromTime').data("DateTimePicker").setDate($scope.doctor.officeHoursFromTime);
		$('#officeHoursToTime').data("DateTimePicker").setDate($scope.doctor.officeHoursToTime);
	});
	
	
	
	  $scope.update=function(){
		  requestHandler.postRequest("Admin/saveUpdateDoctors.json",$scope.doctor).then(function(response){
			  Flash.create('success', "You have Successfully Updated!");
			  $location.path('dashboard/doctor');
			});
			
	  
	};

	$scope.isClean = function() {
        return angular.equals (doctorOriginal, $scope.doctor);
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



