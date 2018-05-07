var adminApp=angular.module('sbAdminApp',['requestModule','searchModule','uiGmapgoogle-maps','gm','flash']);

adminApp.controller('showNearByClinicController',function($rootScope,$scope,$log,$stateParams,requestHandler,searchService,$compile,$q,$timeout,Flash){
	
	$scope.init=function(){
		$scope.titleText="View Near By Clinics";
		$scope.clientNameText="Patient Name";
		$scope.clinicNameText="Clinic Name";
		$scope.detailsHeadText="Clinic & Doctor Details";
		$scope.detailsDoctorText="Doctors List";
		$scope.detailsNoDoctorText="No Doctors added";
		$scope.doctorIcon="fa-user-md";
		$scope.patientText="Patient";
		if($rootScope.isAdmin==8||$rootScope.isAdmin==9){
			$scope.titleText="View Near By Shops";
			$scope.clientNameText="Client Name";
			$scope.clinicNameText="Shop Name";
			$scope.detailsHeadText="Shop & Mechanic Details";
			$scope.detailsDoctorText="Mechanics List";
			$scope.detailsNoDoctorText="No Mechanic added";
			$scope.doctorIcon="fa-wrench";
			$scope.patientText="Client";
		}
		$scope.searchRange=25;
		$scope.clinicsForms="";
		$scope.lat="";
		$scope.lng="";
		$scope.isUpdated=1;
		$scope.correctedAddress="";
		$scope.isDrivingDistance=0;
		$scope.getNearByClinics(1);
	};
	$scope.originalCorrectedAddress="";
	$scope.$on('gmPlacesAutocomplete::placeChanged', function(){
	      var location = $scope.autocomplete.getPlace().geometry.location;
	      $scope.lat = location.lat();
	      $scope.lng = location.lng();
	      $scope.isUpdated=0;
	      $scope.correctedAddress=$scope.autocomplete.getPlace().formatted_address;
	      $scope.$apply();
	});
	
	// Options For Auto Complete Search
	$scope.autocompleteOptions={
			componentRestrictions:{
				country: "us"
			}
	};
	
	$scope.resetLatLng=function(){
		$scope.lat="";
		$scope.lng="";
		$scope.correctedAddress="";
		$scope.isUpdated=1;
	};
	
	// Get Patient Id
	$scope.getPatient=function(){
		requestHandler.getRequest("Patient/getPatient.json?patientId="+$stateParams.id,"").then( function(response) {
			$scope.patient= response.data.patientForm;
		});
	};
	
	// Search Near By Clinics
	$scope.getNearByClinics=function(searchFrom){
		if(searchFrom==2){
			if($scope.lat==''&&$scope.lng==''){
				alert("Please enter correct address & select from list");
			}else{
				$scope.searchRange=25;
				$scope.getLocationWithDetails(searchFrom);
			}
		}else{
			$scope.getLocationWithDetails(searchFrom);
		}
    };
    
    $scope.getLocationWithDetails=function(searchFrom){
    	$scope.clinicSearchForm={
    			patientId:$stateParams.id,
    			searchRange:$scope.searchRange,
    			correctedAddress:$scope.correctedAddress,
    			correctedLat:$scope.lat,
    			correctedLong:$scope.lng,
    			searchBy:searchFrom
    	};
    	requestHandler.postRequest("Caller/getNearByClincs.json",$scope.clinicSearchForm).then( function(response) {
    		$scope.isUpdated=1;
    		$scope.getPatient();
    		$scope.autocomplete="";
    		$scope.clinicLocationForm= response.data.clinicLocationForm;
    		$scope.clinicsForms = response.data.clinicLocationForm.clinicsForms;
    		$scope.originalCorrectedAddress=response.data.clinicLocationForm.correctedAddress;
    		$scope.updateAddressForm.$setPristine();
    		// Change Address Block Display
    		if($scope.originalCorrectedAddress==null||$scope.originalCorrectedAddress==''){
    			$scope.showAddress=0;
    		}else{
    			$scope.showAddress=1;
    		}
    		var clinicLocationForm= response.data.clinicLocationForm;
    		var clinicsForms =response.data.clinicLocationForm.clinicsForms;
    		$scope.map = {center: {latitude: clinicLocationForm.centerLatitude, longitude: clinicLocationForm.centerLongitude }, zoom: 9 };
    		$scope.options = {scrollwheel: true};
    		$scope.clinicMarkers = [];
    		$scope.circles = [];
    		var markers = [];
    		var idKey="id";
    		var i=1;
    		var patientMarker={};
    		patientMarker = {
	    	        latitude: clinicLocationForm.centerLatitude,
	    	        longitude: clinicLocationForm.centerLongitude,
	    	        title: $scope.patientText+" Location",
	   	           	icon:'resources/images/map/map_icon_yellow.png',
	   	           	show:false
	   	          };
    		patientMarker.onClick = function() {
                 patientMarker.show = !patientMarker.show;
                 $scope.$apply();
             };
            patientMarker[idKey]=0;
    		markers.push(patientMarker);
    		for ( var int = 0; int < clinicsForms.length; int++) {
				var clinicForm = clinicsForms[int];
				var windowContent="<b>"+clinicForm.clinicName+",</b><br/>"+clinicForm.address+",</br>"+clinicForm.city+",</br><b>"+clinicForm.farAway+" miles</b> far away";
				var clinicMarker = {};
				if(clinicForm.isDrivingDistance==1){
					$scope.isDrivingDistance=1;
					windowContent="<b>"+clinicForm.clinicName+",</b><br/>"+clinicForm.address+",</br>"+clinicForm.city+",</br><b>"+clinicForm.farAway+" miles</b> far away  <b> / "+clinicForm.travellingTime+"</b>";
					clinicMarker = {
			    	        latitude: clinicForm.latitude,
			    	        longitude: clinicForm.longitude,
			    	        title: windowContent,
			    	        icon:'resources/images/map/map_icon_green_plus_white_border.png',
			    	        text:clinicForm.clinicId,
			   	           	show:false
			   	         };
					clinicMarker.onClick = function(googleMarker, eventType, dataModelItem) {
						dataModelItem.show = !dataModelItem.show;
		                 $scope.$apply();
		             };
				}else{
					clinicMarker = {
			    	        latitude: clinicForm.latitude,
			    	        longitude: clinicForm.longitude,
			    	        title: windowContent,
			    	        text:clinicForm.clinicId,
			   	           	show:false
			   	         };
					clinicMarker.onClick = function(googleMarker, eventType, dataModelItem) {
						dataModelItem.show = !dataModelItem.show;
		                 $scope.$apply();
		             };
				}
				clinicMarker[idKey]=i;
				markers.push(clinicMarker);
				
				i=i+1;
			}
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
    	                                color: '#1A4D81',
    	                                weight: 2,
    	                                opacity: 1
    	                            },
    	                            fill: {
    	                                color: '#1A4D81',
    	                                opacity: 0.2
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
    
    $scope.isClean = function() {
        return angular.equals($scope.originalCorrectedAddress, $scope.autocomplete);
    };
    
    // Open a Info Window while clicking on marker
    /*$scope.onClick = function(model) {
        
        model.show = !model.model.show;
    };
*/
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
        $scope.show = false;
    };
    
    // Get More Details About Clinic
    $scope.viewClinicDetails=function(clinicId) {
		 requestHandler.getRequest("Caller/getClinic.json?clinicId="+clinicId,"").then(function(results){
		 	 $scope.clinicDetails= results.data.clinicsForm;
		 	 $("#viewClinicDetails").modal('show');
		  });
	};
	
	$scope.removeCorrectedAddress=function(){
		if(confirm("Do you sure want to remove the corrected address?")){
			requestHandler.postRequest("Caller/removeCorrectedAddress.json?patientId="+$stateParams.id,"").then(function(results){
				Flash.create('success', "You have Successfully Removed!");
				$scope.showAddress=0;
				$scope.resetLatLng();
				$scope.searchRange=25;
				$scope.getNearByClinics(1);
				
			});
		}
	};
	
	// Initialized Function
	$scope.init();
});

adminApp.directive('searchRangeValidation',function(){
	return{
		require:'ngModel',
		restrict:'',
		link:function(scope,attr,elem,ngModel){
			ngModel.$validators.searchRangeValidation=function(modelValue){
				if(modelValue!=''){
					return modelValue>0 && modelValue<=100;
				}else{
					return true;
				}
				
			};
		}
	}
	
});