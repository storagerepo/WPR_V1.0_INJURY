var adminApp=angular.module('sbAdminApp', ['requestModule']);

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
		$("#myModal").modal("show");
		
		$scope.files = [];
		
		$scope.fileUpload=function()
		  {
			alert("ll");
			  requestHandler.deletePostRequest("Staff/uploadFile.json?file=",id).then(function(results){
				  $state.reload('dashboard.staff');
			     });
			  
		}
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
	
	$scope.fileUpload=function(){
		
		  
		   console.log($scope.files[0]);
		        
		        
		             /* $http.post('http://localhost:8081/Injury/fileUpload.json?path='+$scope.files[0].name).success(function (results) {
		          
		          alert("ok");
		    });
*/
		};
		   

});


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