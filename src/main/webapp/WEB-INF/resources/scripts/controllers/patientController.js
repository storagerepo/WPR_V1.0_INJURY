var adminApp=angular.module('sbAdminApp', []);

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

adminApp.controller('ShowPatientController', function($scope,$http,$location,$state) {
	
	$scope.title="Add Patient";
	
	$scope.files = [];
	// In Onload get Patient Details
	$http.get("http://localhost:8081/Injury/Staff/getAllPatientss.json").success( function(response) {
		//alert(JSON.stringify(response));
	     $scope.patients= response.patientsForms;
	     $scope.sort("firstName");
	     });
	
	$scope.sort = function(keyname){
	        $scope.sortKey = keyname;   //set the sortKey to the param passed
	        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
	    };

	
	$scope.getPatientList=function(){
		
		$http.get("http://localhost:8081/Injury/Staff/getAllPatientss.json").success( function(response) {
		     $scope.patients= response.patientsForms;
		     });
	};
	
	$scope.deletePatient=function(id){
		if(confirm("Are you sure to delete patient?")){
		$http.post("http://localhost:8081/Injury/Staff/deletePatients.json?id="+id).then(function() {
			 $state.reload('dashboard.patient');
		});
		
		}
	};
	
	$scope.addModel=function()
	{
		$scope.title="Add Patient";
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
		$http.get("http://localhost:8081/Injury/Staff/getPatients.json?id="+id).success( function(response) {
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


adminApp.controller('EditPatientController', function($scope,$http,$location,$stateParams){
	
	$scope.patientid=$stateParams.id;
	
		$http.get("http://localhost:8081/Injury/Staff/getPatients.json?id="+$stateParams.id).success( function(response) {
		
		     $scope.patient= response.patientsForm;
		  
		     });
		
		$scope.updatePatient=function(){
			$http.post('http://localhost:8081/Injury/Staff/mergePatients.json',$scope.patient).then(function(response) {
				$location.path("dashboard/patient");
			});
		};
		
	
});