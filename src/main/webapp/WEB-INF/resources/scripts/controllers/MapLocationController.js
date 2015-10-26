var adminApp=angular.module('sbAdminApp',['requestModule','uiGmapgoogle-maps']);

adminApp.controller('showNearByClinicController',function($scope,$log,$stateParams,requestHandler,$compile,$q){
	
	// Get Patient Id
	requestHandler.getRequest("Staff/getPatientWithLatLong.json?id="+$stateParams.id,"").then( function(response) {
		console.log(response.data.patientsForm);
		$scope.patient= response.data.patientsForm;
		$scope.map = {center: {latitude: $scope.patient.latitude, longitude: $scope.patient.longitude }, zoom: 13, markers:[] };
	    $scope.options = {scrollwheel: true};
	    $scope.clinicMarkers = [];
	    var markers = [];
		var idKey="id";
		var marker = {
		        latitude: $scope.patient.latitude,
		        longitude: $scope.patient.longitude,
		        title: $scope.patient.address,
		        show:false,
		        icon:'resources/images/map/map_icon_green.png'
		    };
		marker[idKey]=0;
		markers.push(marker);
		$scope.clinicMarkers = markers;
	});
	$scope.clinicsForms="";
	// Search Near By Clinics
	$scope.getNearByClinics=function(){
    	
    	requestHandler.getRequest("Staff/getNearByClincs.json.json?patientId="+$stateParams.id+"&searchRange="+$scope.searchRange,"").then( function(response) {
    		$scope.clinicLocationForm= response.data.clinicLocationForm;
    		$scope.clinicsForms = response.data.clinicLocationForm.clinicsForms;
    		console.log(response.data.clinicLocationForm);
    		var clinicLocationForm= response.data.clinicLocationForm;
    		var clinicsForms =response.data.clinicLocationForm.clinicsForms;
    		$scope.map = {center: {latitude: clinicLocationForm.centerLatitude, longitude: clinicLocationForm.centerLongitude }, zoom: 11 };
    		$scope.clinicMarkers = [];
    		$scope.circles = [];
    		var markers = [];
    		var idKey="id";
    		var i=1;
    		var marker = {
	    	        latitude: clinicLocationForm.centerLatitude,
	    	        longitude: clinicLocationForm.centerLongitude,
	    	        title: "Patient Location",
	   	           	show:false,
	   	           	icon:'resources/images/map/map_icon_green.png',
	   	          };
    		marker[idKey]=0;
    		markers.push(marker);
    		for ( var int = 0; int < clinicsForms.length; int++) {
				var clinicForm = clinicsForms[int];
				var windowContent="<b>"+clinicForm.clinicName+",</b><br/>"+clinicForm.address+",</br>"+clinicForm.city+",</br><b>"+clinicForm.farAway+" miles</b> far away ";
				marker = {
			    	        latitude: clinicForm.latitude,
			    	        longitude: clinicForm.longitude,
			    	        title: windowContent,
			    	        text:clinicForm.clinicId,
			   	           	show:false
			   	         };
				marker[idKey]=i;
				markers.push(marker);
				
				i=i+1;
			}
    		console.log(markers);
    		$scope.clinicMarkers=markers;
    		$scope.circles = [
    	                        {
    	                            id: 1,
    	                            center: {
    	                                latitude: clinicLocationForm.centerLatitude,
    	                                longitude: clinicLocationForm.centerLongitude
    	                            },
    	                            radius: clinicLocationForm.searchRadius,
    	                            stroke: {
    	                                color: '#08B21F',
    	                                weight: 2,
    	                                opacity: 1
    	                            },
    	                            fill: {
    	                                color: '#08B21F',
    	                                opacity: 0.5
    	                            },
    	                            geodesic: true, // optional: defaults to false
    	                            draggable: false, // optional: defaults to false
    	                            clickable: true, // optional: defaults to true
    	                            editable: false, // optional: defaults to false
    	                            visible: true, // optional: defaults to true
    	                            control: {}
    	                        }
    	                    ];
    	});
    };
    
    // Open a Info Window while clicking on marker
    $scope.onClick = function(model) {
        
        model.show = !model.model.show;
    };

    // Marker Events
    $scope.markersEvents = {
    			mouseover: function (gMarker, eventName, model) {
    				model.show = true;
                    $scope.$apply();
                },
                mouseout: function (gMarker, eventName, model) {
                	model.show = false;
                	$scope.$apply();
                }
        };
    
    $scope.closeClick = function() {
        $scope.windowOptions.visible = false;
    };
    
    // Get More Details About Clinic
    $scope.viewClinicDetails=function(clinicId) {
		 requestHandler.getRequest("Admin/getClinic.json?clinicId="+clinicId,"").then(function(results){
		 	 $scope.clinicDetails= results.data.clinicsForm;
		 	 $("#viewClinicDetails").modal('show');
		  });
	};
    
});