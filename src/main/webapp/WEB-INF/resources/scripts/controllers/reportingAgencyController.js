var adminApp = angular.module('sbAdminApp', [ 'requestModule', 'flash',
		'ngAnimate' ]);
adminApp
		.controller(
				'ShowReportingAgencyController',
				function($http, $state, $scope, $location, requestHandler,
						Flash, $q) {
					$scope.reverse = true;
					$scope.setScrollDown = false;
					$scope.reportingAgencySearchParamForm = {
						"countyId" : 0,
						"ncicCode" : "",
						"reportingAgencyName" : "",
						"pageNumber" : 1,
						"recordsPerPage" : "25"
					};

					$scope.sort = function(keyname) {
						$scope.sortKey = keyname; // set the sortKey to the
													// param passed
						$scope.reverse = !$scope.reverse; // if true make it
															// false and vice
															// versa
					};

					$scope.sort('countyName');
					// getting county List
					$scope.getCountyList = function() {
						requestHandler.getRequest("Admin/getAllCountys.json",
								"").then(function(response) {
							$scope.countyform = response.data.countyForms;
							console.log($scope.countyform);

						});
					}
					$scope.getCountyList();

					// Filter County
					$scope.CountyFilterFunction = function(selectedCounty) {
						if (selectedCounty == null) {
							$scope.selectedCounty = 0;
						}
					}

					// Enable Disable Reporting Agency

					$scope.enableOrDisableReportingAgency = function(
							reportingAgencyId) {
						requestHandler.getRequest(
								"Patient/enableDisableReportingAgency.json?reportingAgencyId="
										+ reportingAgencyId, "").then(
								function(response) {
									$scope.searchReportingAgency();
									Flash.create('success',
											'Changes made successfully');
								})
					}

					// delete function
					$scope.deleteReportingAgency = function(reportingAgencyId) {
						$scope.modalHeader = "Confirmation";
						$scope.modalContent = "Do you want to delete this Reporting Agency?";
						$scope.confirmDeleteReportingAgency = function() {
							$('#deleteReportingAgencyModal').modal('hide');
							requestHandler
									.getRequest(
											"Patient/deleteReportingAgency.json?reportingAgencyId="
													+ reportingAgencyId)
									.then(
											function(response) {
												$scope.status = response.data.status;
												Flash
														.create('success',
																"Action completed successfully");
												$scope.searchReportingAgency();
											});
						}

					}
					// Getting New Records From Back-end

					$scope.itemsPerPage = function() {
						$scope.setScrollDown = true;
						var promise = $scope.searchReportingAgency();
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

					$scope.$watch("reportingAgencySearchParamForm.pageNumber", function() {
						var promise = $scope.searchReportingAgency();
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

					// Search Based On KeyWord
					$scope.searchReportingAgency = function() {
						var defer = $q.defer();
						requestHandler
								.postRequest(
										"Patient/getReportingAgencyList2.json",
										$scope.reportingAgencySearchParamForm)
								.then(
										function(response) {
											$scope.reportingAgencyList = response.data.reportingAgencyList.reportingAgencyForms;
											$scope.totalRecords = response.data.reportingAgencyList.totalRecords;
											defer.resolve(response);
										});
						return defer.promise;
					}

					$scope.searchReportingAgency();

					// Reset Search Param

					$scope.reset = function() {
						$scope.reportingAgencySearchParamForm = {
							"countyId" : 0,
							"ncicCode" : "",
							"reportingAgencyName" : "",
							"pageNumber" : 1,
							"recordsPerPage" : "25"
						};
						$scope.searchReportingAgency();
					}

				});

adminApp
		.controller(
				'AddReportingAgencyController',
				function($http, $state, $scope, $location, requestHandler,
						Flash) {
					$scope.title = "Add Reporting Agency";
					$scope.options = true;
					$scope.disableNcicField = false;
					$scope.showNcicError = false;
					$scope.reportingAgencyForm = {};
					$scope.reportingAgencyForm.reportingAgencyId = 0;

					// getting county List
					$scope.getCountyList = function() {
						requestHandler.getRequest("Admin/getAllCountys.json",
								"").then(function(response) {
							$scope.countyform = response.data.countyForms;

						});
					}
					$scope.getCountyList();

					// Save Reporting Agency
					$scope.saveReportingAgency = function() {
						requestHandler
								.postRequest(
										"Patient/checkNcicCode.json?nciccode="
												+ $scope.reportingAgencyForm.code
												+ "&id="
												+ $scope.reportingAgencyForm.reportingAgencyId
												+ "&countyId="
												+ $scope.reportingAgencyForm.countyId,
										0)
								.then(
										function(response) {
											var result = response.data.result;
											if (result == 1) {
												$scope.showNcicError = true;
											} else {
												requestHandler
														.postRequest(
																"Patient/saveUpdateReportingAgency.json",
																$scope.reportingAgencyForm)
														.then(
																function(
																		response) {
																	Flash
																			.create(
																					'success',
																					"New Reporting Agency Added Successfully");
																	$location
																			.path('dashboard/reportingAgency');
																});
											}
										});

					}

				});

adminApp
		.controller(
				'EditReportingAgencyController',
				function($http, $state, $scope, $stateParams, $location,
						requestHandler, Flash) {
					$scope.title = "Edit Reporting Agency";
					$scope.disableNcicField = true;
					$scope.options = false;

					// getting County List
					$scope.getCountyList = function() {
						requestHandler.getRequest("Admin/getAllCountys.json",
								"").then(function(response) {
							$scope.countyform = response.data.countyForms;

						});
					}
					$scope.getCountyList();

					// Get Reporting Agency
					var reportingAgencyOriginal = "";
					requestHandler
							.getRequest(
									"Patient/getReportingAgency.json?reportingAgencyId="
											+ $stateParams.reportingAgencyId,
									"")
							.then(
									function(response) {
										$scope.reportingAgencyForm = response.data.reportingAgencyForm;
										reportingAgencyOriginal = angular
												.copy($scope.reportingAgencyForm);
									});

					//update Reporting Agency
					var reportingAgencyOriginal = "";
					$scope.updateReportingAgency = function() {
						requestHandler
								.postRequest(
										"Patient/checkNcicCode.json?nciccode="
												+ $scope.reportingAgencyForm.code
												+ "&id="
												+ $scope.reportingAgencyForm.reportingAgencyId
												+ "&countyId="
												+ $scope.reportingAgencyForm.countyId,
										0)
								.then(
										function(response) {
											var result = response.data.result;
											if (result == 1) {
												$scope.showNcicError = true;
											} else {
												requestHandler
														.postRequest(
																"Patient/saveUpdateReportingAgency.json",
																$scope.reportingAgencyForm)
														.then(
																function(
																		response) {
																	Flash
																			.create(
																					'success',
																					"Reporting Agency updated successfully");
																	$location
																			.path('dashboard/reportingAgency');
																});
											}
										});
					}
					//Check for changes in Ng-Model
					$scope.isClean = function() {

						return angular.equals(reportingAgencyOriginal,
								$scope.reportingAgencyForm);
					}
				});
