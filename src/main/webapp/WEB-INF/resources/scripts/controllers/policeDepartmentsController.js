var adminApp = angular.module('sbAdminApp', [ 'requestModule', 'flash',
		'ngAnimate' ]);

adminApp
		.controller(
				'ShowPoliceDepartmentsController',
				function($http, $state, $scope, $location, requestHandler,
						Flash) {
$scope.reverse=true;	
					$scope.noOfRows = "25";
					$scope.selectedReportPullingType = "0";
					$scope.selectedCounty = 0;
					$scope.selectedReportStatus = "-1";
					$scope.sort = function(keyname) {
						$scope.sortKey = keyname; // set the sortKey to the
													// param passed
						$scope.reverse = !$scope.reverse; // if true make it
															// false and vice
															// versa
					};
					$scope.sort('mapId');

					// Getting CountyList
					requestHandler.getRequest("Admin/getAllCountys.json", "")
							.then(function(response) {
								$scope.countyform = response.data.countyForms;
							});

					// Filter County
					$scope.CountyFilterFunction = function(selectedCounty) {
						if (selectedCounty == null) {
							$scope.selectedCounty = 0;
						}
						console.log($scope.selectedCounty);
					}

					$scope.searchparam = function() {
						requestHandler
								.getRequest(
										"Admin/searchPoliceDepartments.json?countyParam="
												+ $scope.selectedCounty
												+ "&reportPullingTypeParam="
												+ $scope.selectedReportPullingType
												+"&reportStatus="+$scope.selectedReportStatus)
								.then(
										function(response) {
											$scope.policeDepartmentsForm = response.data.policeAgencyForms;
											console
													.log($scope.policeDepartmentsForm);
										});
					};

					// calling Search function while initializing controller
					$scope.searchparam();

					// View 
					$scope.viewPoliceDepartment=function(mapId){
						requestHandler
						.getRequest(
								"Admin/getPoliceAgency.json?mapId="
										+ mapId, "")
						.then(
								function(response) {

									$scope.policeAgencyForm = response.data.policeAgencyForm;
									if ($scope.policeAgencyForm.agencyId == 0) {
										$scope.policeAgencyForm.agencyId = "";
									}
									
									$("#viewPoliceDepartment").modal('show');
								});
					}
					
					// delete function
					$scope.deletePoliceDepartment = function(mapId) {
						$scope.modalHeader = "Confirmation";
						$scope.modalContent = "Do you want to delete this Department?";
						$scope.modalButtonText = "No";
						$scope.switchingmodalbutton = true;

						$scope.confirmDeletePoliceDepartment = function() {
							$('#deletePoliceDepartmentModal').modal('hide');
							requestHandler
									.getRequest(
											"Admin/deletePoliceAgency.json?mapId="
													+ mapId)
									.then(
											function(response) {
												$scope.status = response.data.status;
												if ($scope.status == 0) {
													$scope.modalHeader = "Warning";
													$scope.modalContent = "There is a available records uploaded by this Department!";
													$scope.modalButtonText = "Ok";
													$scope.switchingmodalbutton = false;
													setTimeout(
															function() {
																$(
																		'#deletePoliceDepartmentModal')
																		.modal(
																				'show');
															}, 2000);

												} else {
													Flash
															.create('success',
																	"Action completed successfully");

													$scope.searchparam();
												}

											});
						}

					}

				});

adminApp.controller('SavePoliceDepartmentController', function($http, $state,
		$scope, $location, requestHandler, Flash) {
	$scope.title = $state.current.title;
	$scope.options = true;
	
	// Reset Agency Id Value
	$scope.agencyChange=function(){
		if($scope.policeAgencyForm.schedulerType==4){
			$scope.policeAgencyForm.agencyId="";
		}
	};
	
	// getting county List
	$scope.getCountyList = function() {
		requestHandler.getRequest("Admin/getAllCountys.json", "").then(
				function(response) {
					$scope.countyform = response.data.countyForms;

				});
	}
	$scope.getCountyList();

	// saving Police Department
	$scope.savePoliceDepartment = function() {
		if ($scope.policeAgencyForm.schedulerType == 4) {
			$scope.policeAgencyForm.agencyId = 0;
		}
		$scope.policeAgencyForm.mapId = null;
		console.log($scope.policeAgencyForm);
		requestHandler.postRequest("Admin/saveUpdatePoliceAgency.json",
				$scope.policeAgencyForm).then(function(response) {
	       			 Flash.create('success', "New Police Department Added Successfully");

			$location.path('dashboard/policeDepartments');
		});
	}
});

adminApp
		.controller(
				'EditPoliceDepartmentController',
				function($http, $state, $location, $scope, $stateParams,
						requestHandler, Flash) {
					$scope.options = false;
					$scope.title = $state.current.title;

					// Reset Agency Id Value
					$scope.agencyChange=function(){
						if($scope.policeAgencyForm.schedulerType==4){
							$scope.policeAgencyForm.agencyId="";
						}
					};
					
					// getting county List
					$scope.getCountyList = function() {
						requestHandler.getRequest("Admin/getAllCountys.json",
								"").then(function(response) {
							$scope.countyform = response.data.countyForms;

						});
					}
					$scope.getCountyList();

					// Get Police Department
					var policeDepartmentOriginal = "";
					requestHandler
							.getRequest(
									"Admin/getPoliceAgency.json?mapId="
											+ $stateParams.mapId, "")
							.then(
									function(response) {

										$scope.policeAgencyForm = response.data.policeAgencyForm;
										if ($scope.policeAgencyForm.agencyId == 0) {
											$scope.policeAgencyForm.agencyId = "";
										}
										$scope.policeAgencyForm.schedulerType = $scope.policeAgencyForm.schedulerType
												.toString();
										policeDepartmentOriginal = angular
												.copy($scope.policeAgencyForm);
									});

					//update Police Department

					$scope.updatePoliceDepartment = function() {
						if ($scope.policeAgencyForm.schedulerType == 4) {
							$scope.policeAgencyForm.agencyId = 0;
						}
						requestHandler
								.postRequest(
										"Admin/saveUpdatePoliceAgency.json",
										$scope.policeAgencyForm)
								.then(
										function(response) {
							       			 Flash.create('success', "Police Department Updated Successfully");
											$location
													.path('dashboard/policeDepartments');
										});

					}

					$scope.isClean = function() {

						return angular.equals(policeDepartmentOriginal,
								$scope.policeAgencyForm);

					};
				});