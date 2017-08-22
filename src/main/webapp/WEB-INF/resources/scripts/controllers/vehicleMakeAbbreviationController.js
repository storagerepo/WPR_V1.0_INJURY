var adminApp = angular.module('sbAdminApp', [ 'requestModule', 'flash','ngAnimate' ]);
adminApp.controller('ShowVehicleMakeAbbreviationController',function($http, $state, $scope, $location, requestHandler,Flash, $q) 
		{
	$scope.reverse=true;
	$scope.searchVehicleMakeAbbrevationForm=
		{
			"make":"",
			"abbreviation":"",
			"pageNumber" : 1,
			"recordsPerPage" : "25"
		}
	
	$scope.sort = function(keyname) {
		$scope.sortKey = keyname; // set the sortKey to the
									// param passed
		$scope.reverse = !$scope.reverse; // if true make it
											// false and vice
											// versa
	};
	$scope.sort('make');
	
	// Getting New Records From Back-end

	$scope.itemsPerPage = function() {
		$scope.setScrollDown = true;
		var promise = $scope.searchVehicleMakeAbbreviation();
		if (promise != null)
			promise.then(function(reponse) {
				setTimeout(function() {
					$('html,body').animate({
						scrollTop : $('#noOfRows').offset().top
					}, 'slow');
				}, 100);
			});
	}

	// Watch Page Number And fetching new Records From Back-end

	$scope.$watch("searchVehicleMakeAbbrevationForm.pageNumber", function() {
		var promise = $scope.searchVehicleMakeAbbreviation();
		if ($scope.setScrollDown) {
			promise.then(function(reponse) {
				$scope.setScrollDown = false;
				setTimeout(function() {
					$('html,body').animate({
						scrollTop : $('#noOfRows').offset().top
					}, 'slow');
				}, 100);
			});
		}

	});
	
	//Fetch All vehicles based on search param
	
	$scope.searchVehicleMakeAbbreviation=function(){
		
		var defer=$q.defer();
		
	requestHandler.postRequest("getVehicleMakeAbbrevationsBySearch.json",$scope.searchVehicleMakeAbbrevationForm,"").then(function(response)
			{
		$scope.vehicleMakeAbbreviationList=response.data.vehicleMakeAbbreviationList.vehicleMakeAbbreviationForms;
		$scope.totalRecords=response.data.vehicleMakeAbbreviationList.totalRecords;
		defer.resolve(response);
			});
	return defer.promise;
	}
	$scope.searchVehicleMakeAbbreviation();
	
	           
	// delete function
	$scope.deleteVehicleMake = function(make) {
		$scope.modalHeader = "Confirmation";
		$scope.modalContent = "Do you want to delete this Vehicle Make?";
		$scope.modalButtonText = "No";
		$scope.switchingmodalbutton = true;

		$scope.confirmDeleteVehicleMake= function() {
			$('#deleteVehicleMakeModal').modal('hide');
			requestHandler
					.getRequest(
							"deleteVehicleMakeAbbreviationByMake.json?make="
									+ make)
					.then(
							function(response) {
								$scope.status = response.data.isDeleted;
								if ($scope.status == 1) {
									$scope.modalHeader = "Warning!";
									$scope.modalContent = "There is available patient records having this Vehicle Make name!";
									$scope.modalButtonText = "Ok";
									$scope.switchingmodalbutton = false;
									setTimeout(
											function() {
												$(
														'#deleteVehicleMakeModal')
														.modal(
																'show');
											}, 2000);

								} else {
									Flash
											.create('success',
													"Action completed successfully");

									$scope.searchVehicleMakeAbbreviation();
								}

							});
		}

	}
	
	//reset Search param
	
	$scope.reset = function() {
		$scope.searchVehicleMakeAbbrevationForm=
		{
			"make":"",
			"abbreviation":"",
			"pageNumber" : 1,
			"recordsPerPage" : "25"
		}
		$scope.searchVehicleMakeAbbreviation();
	}
	
		});
		
adminApp.controller('AddVehicleMakeAbbreviationController',function($http, $state, $scope, $location, requestHandler,Flash, $q) 
		{
	$scope.options=true;
	$scope.title="Add New Vehicle Make";
	$scope.vehicleMakeAbbreviationForm={};
	$scope.RequestType=0;
	$scope.disableVehicleMakeInput=false;
	$scope.showVehicleMakeAlreadyExistsError=false;
	
	//save Vehicle Make Abbreviation function
	$scope.saveVehicleMakeAbbreviation=function()
	{
		//check if vehicle Make Name already exists
		
		requestHandler.postRequest("checkVehicleMakeAbbreviationByMake.json?make="+$scope.vehicleMakeAbbreviationForm.make,"").then(function(response)
				{
			$scope.isExists=response.data.isExists;
			if($scope.isExists==1)
				{
				$scope.showVehicleMakeAlreadyExistsError=true;
				}
			else
				{
				requestHandler.postRequest("saveUpdateVehicleMakeAbbreviation.json?RequestType="+$scope.RequestType,$scope.vehicleMakeAbbreviationForm,"").then(function(response)
						{
					Flash.create('success','New Vehicle Added Successfully');
					$location.path('dashboard/vehicleMakeAbbreviation');
						});
				}
				});
		
		
	}
		});

adminApp.controller('EditVehicleMakeAbbreviationController',function($http, $state, $scope, $location, $stateParams, requestHandler,Flash, $q) 
		{
	$scope.options=false;
	$scope.title="Edit Vehicle Make";
	$scope.RequestType=1;
$scope.disableVehicleMakeInput=true;
$scope.showVehicleMakeAlreadyExistsError=false;

var originalVehicleMakeAbbreviationForm="";

//get Vehicle details by Make param

	requestHandler.postRequest("getVehicleMakeAbbreviationByMake.json?make="+$stateParams.vehiclemake,"").then(function(response)
			{
		$scope.vehicleMakeAbbreviationForm=response.data.vehicleMakeAbbreviationForm;
		originalVehicleMakeAbbreviationForm=angular.copy($scope.vehicleMakeAbbreviationForm);
		
			});
	
	//update Vehicle Details
	
	$scope.updateVehicleMakeAbbreviation=function()
	{
		requestHandler.postRequest("saveUpdateVehicleMakeAbbreviation.json?RequestType="+$scope.RequestType,$scope.vehicleMakeAbbreviationForm,"").then(function(response)
				{
			Flash.create('success',' Vehicle Updated Successfully');
			$location.path('dashboard/vehicleMakeAbbreviation');
				});
	}
	
	//Check for changes in Ng-Model
	$scope.isClean = function() {

		return angular.equals(originalVehicleMakeAbbreviationForm,$scope.vehicleMakeAbbreviationForm);
	}
		});
