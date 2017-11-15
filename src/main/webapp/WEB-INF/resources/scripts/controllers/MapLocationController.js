var adminApp=angular.module('sbAdminApp',['requestModule','searchModule','uiGmapgoogle-maps','gm','flash']);

adminApp.controller('showNearByClinicController',function($scope,$log,$stateParams,requestHandler,searchService,$compile,$q,$timeout,Flash){
	
	$scope.init=function(){
		$scope.searchRange=25;
		$scope.clinicsForms="";
		$scope.lat="";
		$scope.lng="";
		$scope.correctedAddress="";
		$scope.isDrivingDistance=0;
		$scope.getNearByClinics(1);
	};
	$scope.originalCorrectedAddress="";
	$scope.$on('gmPlacesAutocomplete::placeChanged', function(){
	      var location = $scope.autocomplete.getPlace().geometry.location;
	      console.log($scope.autocomplete.getPlace());
	      $scope.lat = location.lat();
	      $scope.lng = location.lng();
	      $scope.correctedAddress=$scope.autocomplete.getPlace().formatted_address;
	      $scope.$apply();
	    //  alert("Lat:"+$scope.lat+"-->Long"+$scope.lng);
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
	};
	
	// Get Patient Id
	$scope.getPatient=function(){
		requestHandler.getRequest("Patient/getPatient.json?patientId="+$stateParams.id,"").then( function(response) {
			console.log(response.data.patientForm);
			$scope.patient= response.data.patientForm;
		});
	};
	
	// Search Near By Clinics
	$scope.getNearByClinics=function(searchFrom){
		if(searchFrom==2){
			if($scope.lat==''&&$scope.lng==''){
				alert("Please enter correct address & select from list");
			}else{
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
    	requestHandler.postRequest("Caller/getNearByClincs.json.json",$scope.clinicSearchForm).then( function(response) {
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
    		$scope.map = {center: {latitude: clinicLocationForm.centerLatitude, longitude: clinicLocationForm.centerLongitude }, zoom: 8 };
    		$scope.options = {scrollwheel: true};
    		$scope.clinicMarkers = [];
    		$scope.circles = [];
    		var markers = [];
    		var idKey="id";
    		var i=1;
    		var marker = {
	    	        latitude: clinicLocationForm.centerLatitude,
	    	        longitude: clinicLocationForm.centerLongitude,
	    	        title: "Patient Location",
	   	           	icon:'resources/images/map/map_icon_green_search.png',
	   	           	show:false
	   	          };
    		marker[idKey]=0;
    		markers.push(marker);
    		for ( var int = 0; int < clinicsForms.length; int++) {
				var clinicForm = clinicsForms[int];
				var windowContent="<b>"+clinicForm.clinicName+",</b><br/>"+clinicForm.address+",</br>"+clinicForm.city+",</br><b>"+clinicForm.farAway+" miles</b> far away";
				var marker = {};
				if(clinicForm.isDrivingDistance==1){
					$scope.isDrivingDistance=1;
					windowContent="<b>"+clinicForm.clinicName+",</b><br/>"+clinicForm.address+",</br>"+clinicForm.city+",</br><b>"+clinicForm.farAway+" miles</b> far away  <b> / "+clinicForm.travellingTime+"</b>";
					marker = {
			    	        latitude: clinicForm.latitude,
			    	        longitude: clinicForm.longitude,
			    	        title: windowContent,
			    	        icon:'resources/images/map/map_icon_yellow_clinic.png',
			    	        text:clinicForm.clinicId,
			   	           	show:false
			   	         };
				}else{
					marker = {
			    	        latitude: clinicForm.latitude,
			    	        longitude: clinicForm.longitude,
			    	        title: windowContent,
			    	        text:clinicForm.clinicId,
			   	           	show:false
			   	         };
				}
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
    
    $scope.isClean = function() {
        return angular.equals($scope.originalCorrectedAddress, $scope.autocomplete);
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