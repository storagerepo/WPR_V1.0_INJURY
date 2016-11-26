'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description # sbAdminApp
 * 
 * Main module of the application.
 */
var sbAdminApp = angular.module('sbAdminApp', [ 'oc.lazyLoad', 'ui.router',
		'ui.bootstrap', 'angular-loading-bar',
		'angularUtils.directives.dirPagination', 'requestModule', 'flash',
		'uiGmapgoogle-maps','ngAnimate']);

sbAdminApp
		.config(
				[
						'$stateProvider',
						'$urlRouterProvider',
						'$ocLazyLoadProvider',
						"$httpProvider",
						function($stateProvider, $urlRouterProvider,
								$ocLazyLoadProvider, $httpProvider) {

							/*
							 * uiGmapGoogleMapApiProvider.configure({ key:
							 * 'AIzaSyCSrffdwIzj8p4RZgvglbhQEEjgEx7ZUKQ', v:
							 * '3.20', //defaults to latest 3.X anyhow
							 * libraries: 'weather,geometry,visualization' });
							 */
							$httpProvider.defaults.withCredentials = true;

							$httpProvider.interceptors
									.push([
											'$q',
											'$location',
											'$injector',
											function($q, $location, $injector) {
												return {
													'request' : function(
															request) {
														return request;
													},
													'response' : function(
															response) {
														return response;
													},
													'responseError' : function(
															rejection) {
														switch (rejection.status) {
														case 400: {
															break;
														}
														case 401: {
															window.location.href = window.location.origin+"/Injury/logout?sessionout";

														}
														case 403: {
															window.location.href = window.location.origin+"/Injury/logout?sessionout";
															break;
														}
														case 500: {
															alert("Please try again!");
															window.location.href = window.location.origin+"/Injury/logout";
															break;
														}
														default: {
															break;
														}
														}

														return $q
																.reject(rejection);
													}
												};
											} ]);

							$ocLazyLoadProvider.config({
								debug : false,
								events : true
							});

							$urlRouterProvider.otherwise('/dashboard/home');

							$stateProvider
									.state(
											'dashboard',
											{
												url : '/dashboard',
												templateUrl : 'views/dashboard/main.html',
												resolve : {
													loadMyDirectives : function(
															$ocLazyLoad) {
														return
																$ocLazyLoad
																		.load({
																			name : 'sbAdminApp',
																			files : [ 'scripts/directives/header/header-notification/header-notification.js' ]
																		}),
																$ocLazyLoad
																		.load({
																			name : 'toggle-switch',
																			files : [
																					"resources/components/angular-toggle-switch/angular-toggle-switch.min.js",
																					"resources/components/angular-toggle-switch/angular-toggle-switch.css" ]
																		}),
																$ocLazyLoad
																		.load({
																			name : 'ngAnimate',
																			files : [ 'resources/components/angular-animate/angular-animate.js' ]
																		})
														$ocLazyLoad
																.load({
																	name : 'ngCookies',
																	files : [ 'resources/components/angular-cookies/angular-cookies.js' ]
																})
														$ocLazyLoad
																.load({
																	name : 'ngResource',
																	files : [ 'resources/components/angular-resource/angular-resource.js' ]
																})
														$ocLazyLoad
																.load({
																	name : 'ngSanitize',
																	files : [ 'resources/components/angular-sanitize/angular-sanitize.js' ]
																})
														$ocLazyLoad
																.load({
																	name : 'ngTouch',
																	files : [ 'resources/components/angular-touch/angular-touch.js' ]
																})
													}
												}
											})
									.state(
											'dashboard.home',
											{
												url : '/home',
												controller : 'MainCtrl',
												templateUrl : 'views/dashboard/home.html',
												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/main.js',
																	          'scripts/directives/header/header-notification/header-notification.js',
																	          'scripts/directives/sidebar/sidebar.js',
																	          'scripts/directives/dashboard/stats/stats.js',
																	          "resources/components/angular-toggle-switch/angular-toggle-switch.min.js",
																				"resources/components/angular-toggle-switch/angular-toggle-switch.css" ,
																				'resources/components/angular-animate/angular-animate.js',
																				'resources/components/angular-cookies/angular-cookies.js',
																				 'resources/components/angular-resource/angular-resource.js',
																				 'resources/components/angular-sanitize/angular-sanitize.js' ,
																				 'resources/components/angular-touch/angular-touch.js' ]
																});
													}
												}
											})
									.state(
											'dashboard.form',
											{
												controller : 'FormCtrl',
												templateUrl : 'views/form.html',
												url : '/form',
												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/form.js' ]
																});
													}
												}
											})
									.state('dashboard.blank', {
										templateUrl : 'views/pages/blank.html',
										url : '/blank'
									})
									.state('login', {
										templateUrl : 'views/pages/login.html',
										url : '/login',
										isLogin : true
									})
									.state(
											'dashboard.chart',
											{
												templateUrl : 'views/chart.html',
												url : '/chart',
												controller : 'ChartCtrl',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return
																$ocLazyLoad
																		.load({
																			name : 'chart.js',
																			files : [
																					'resources/components/angular-chart.js/dist/angular-chart.min.js',
																					'resources/components/angular-chart.js/dist/angular-chart.css' ]
																		}),
																$ocLazyLoad
																		.load({
																			name : 'sbAdminApp',
																			files : [ 'scripts/controllers/chartContoller.js' ]
																		})
													}
												}
											})
									.state('dashboard.table', {
										templateUrl : 'views/table.html',
										controller : 'tableCtrl',
										url : '/table'
									})
									.state(
											'dashboard.panels-wells',
											{
												templateUrl : 'views/ui-elements/panels-wells.html',
												url : '/panels-wells'
											})
									.state(
											'dashboard.buttons',
											{
												templateUrl : 'views/ui-elements/buttons.html',
												url : '/buttons'
											})
									.state(
											'dashboard.notifications',
											{
												templateUrl : 'views/ui-elements/notifications.html',
												url : '/notifications'
											})
									.state(
											'dashboard.typography',
											{
												templateUrl : 'views/ui-elements/typography.html',
												url : '/typography'
											})
									.state(
											'dashboard.icons',
											{
												templateUrl : 'views/ui-elements/icons.html',
												url : '/icons'
											})
									.state(
											'dashboard.grid',
											{
												templateUrl : 'views/ui-elements/grid.html',
												url : '/grid'
											})
									// staff starts
									.state(
											'dashboard.staff',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/staffController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'ShowStaffController',
												templateUrl : 'views/staff-view/staff.html',
												url : '/staff'

											})
									.state(
											'dashboard.add-staff',
											{

												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/staffController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'SaveStaffController',
												templateUrl : 'views/staff-view/add-staff.html',
												url : '/add-staff',
												title : 'Add Caller'
											})
									.state(
											'dashboard.EditStaff/:id',
											{

												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/staffController.js' ]
																});
													}
												},
												controller : 'EditStaffController',
												templateUrl : 'views/staff-view/add-staff.html',
												url : '/EditStaff/:id',
												title : 'Edit Caller'
											})
									// End Staff
									.state(
											'dashboard.clinics',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/clinicController.js' ]
																});
													}
												},
												controller : 'CallerClinicController',
												templateUrl : 'views/clinic/clinic.html',
												url : '/clinics'
											})
											
									.state(
											'dashboard.clinic',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/clinicController.js' ]
																});
													}
												},
												controller : 'ShowClinicController',
												templateUrl : 'views/clinic/clinic.html',
												url : '/clinic'
											})
											
									.state(
											'dashboard.add-clinic',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/clinicController.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												},
												controller : 'SaveClinicController',
												templateUrl : 'views/clinic/add-clinic.html',
												url : '/add-clinic'

											})
									.state(
											'dashboard.edit-clinic/:id',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/clinicController.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												},
												controller : 'EditClinicController',
												templateUrl : 'views/clinic/add-clinic.html',
												url : '/edit-clinic/:id'

											})
									// Start Doctor
									.state(
											'dashboard.doctor',
											{

												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/Doctors.js' ]
																});
													}
												},
												controller : 'ShowDoctorsCtrl',
												templateUrl : 'views/doctor.html',
												url : '/doctor'

											})
									.state(
											'dashboard.add-doctor',
											{
												templateUrl : 'views/add-doctor.html',
												url : '/add-doctor',
												controller : 'AddDoctorsCtrl',
												title : 'Add Doctor',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/Doctors.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												}
											})
									.state(
											'dashboard.EditDoctor/:id',
											{
												templateUrl : 'views/add-doctor.html',
												url : '/EditDoctor/:id',
												controller : 'EditDoctorController',
												title : 'Edit Doctor',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/Doctors.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												}
											})
									// End Doctor
									.state(
											'dashboard.patient',
											{
												templateUrl : 'views/patient/patient.html',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/patientController.js' ]
																});
													}
												},
												controller : 'ShowPatientController',
												url : '/patient'

											})
									.state(
											'dashboard.EditPatient/:id',
											{
												templateUrl : 'views/patient/add-patients.html',
												controller : "EditPatientController",
												title : "Edit Patient",
												url : '/EditPatient/:id',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/patientController.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												}
											})
									.state(
											'dashboard.add-patient',
											{
												templateUrl : 'views/patient/add-patients.html',
												controller : "AddPatientController",
												title : "Add Patient",
												url : '/add-patient',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/patientController.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												}
											})
									// End Patient
									.state(
											'dashboard.appointment',
											{
												templateUrl : 'views/appointment/appointment.html',
												url : '/appointment',
												controller : 'ShowAppointmentsCtrl',
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/appointmentController.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												}
											})
									// End Appointment

									.state(
											'dashboard.Calllogs/:id',
											{
												templateUrl : 'views/calllogs/calllogs.html',
												url : '/Calllogs/:id',
												controller : "showCallLogsController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/CallLogsController.js',
																			'components/datetime/datetimepicker.css',
																			'components/datetime/moment.js',
																			'components/datetime/datetimepicker.js' ]
																});
													}
												}

											})
									.state(
											'dashboard.viewlocations/:id',
											{
												templateUrl : 'views/maplocation/view-nearby-clinics.html',
												url : '/viewlocations/:id',
												controller : "showNearByClinicController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/MapLocationController.js' ]
																});
													}
												}

											})
									// End Call logs
									.state(
											'dashboard.Crashreport',
											{
												templateUrl : 'views/crashreport/crashreport.html',
												url : '/Crashreport',
												controller : "crashReportController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/crashReportController.js' ]
																});
													}
												}

											})
											
											
											.state(
											'dashboard.SearchCrashreport',
											{
												templateUrl : 'views/crashreport/searchcrashreport.html',
												url : '/SearchCrashreport',
												controller : "searchCrashReportController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/searchCrashReportController.js',
																	          'components/datetime/datetimepicker.css',
																				'components/datetime/moment.js',
																				'components/datetime/datetimepicker.js'
																	          ]
																});
													}
												}

											})
											
											.state(
											'dashboard.SearchPatients',
											{
												templateUrl : 'views/patient/searchpatients.html',
												url : '/SearchPatients',
												controller : "searchPatientsController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/searchPatientsController.js',
																	          'components/datetime/datetimepicker.css',
																				'components/datetime/moment.js',
																				'components/datetime/datetimepicker.js'
																	          ]
																});
													}
												}

											})
										
											.state(
											'dashboard.LawyerAdminSearchPatients',
											{
												templateUrl : 'views/patient/Lawyeradmin-searchpatients.html',
												url : '/LawyerAdminSearchPatients',
												controller : "LAdminSearchPatientsController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/LAdminSearchPatientController.js',
																	          'components/datetime/datetimepicker.css',
																				'components/datetime/moment.js',
																				'components/datetime/datetimepicker.js'
																	          ]
																});
													}
												}

											})
											
											.state(
											'dashboard.LawyerSearchPatients',
											{
												templateUrl : 'views/patient/lawyer-searchpatients.html',
												url : '/LawyerSearchPatients',
												controller : "LawyerSearchPatientsController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/LawyerSearchPatientController.js',
																	          'components/datetime/datetimepicker.css',
																				'components/datetime/moment.js',
																				'components/datetime/datetimepicker.js'
																	          ]
																});
													}
												}

											})
											.state(
											'dashboard.CallerSearchPatients',
											{
												templateUrl : 'views/patient/caller-searchpatients.html',
												url : '/CallerSearchPatients',
												controller : "CallerSearchPatientsController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/CallerSearchPatientController.js',
																	          'components/datetime/datetimepicker.css',
																				'components/datetime/moment.js',
																				'components/datetime/datetimepicker.js'
																	          ]
																});
													}
												}

											})
											
									// Lawyer Admin starts
									.state(
											'dashboard.LawyerAdmin',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/lawyerAdminController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'ShowLawyerAdminController',
												templateUrl : 'views/lawyeradmin/viewlawyeradmin.html',
												url : '/LawyerAdmin'

											})
									.state(
											'dashboard.add-lawyer-admin',
											{

												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/lawyerAdminController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'SaveLawyerAdminController',
												templateUrl : 'views/lawyeradmin/add-lawyer-admin.html',
												url : '/add-lawyer-admin',
												title : 'Add Lawyer Admin'
											})
									.state(
											'dashboard.EditLawyerAdmin/:lawyerAdminId',
											{

												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/lawyerAdminController.js' ]
																});
													}
												},
												controller : 'EditLawyerAdminController',
												templateUrl : 'views/lawyeradmin/add-lawyer-admin.html',
												url : '/EditLawyerAdmin/:lawyerAdminId',
												title : 'Edit Lawyer Admin'
											})
									// End Lawyer Admin
									// Caller Admin starts
									.state(
											'dashboard.CallerAdmin',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/callerAdminController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'ShowCallerAdminController',
												templateUrl : 'views/calleradmin/view-caller-admin.html',
												url : '/CallerAdmin'

											})
											.state(
											'dashboard.patientResponse',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/callerAdminController.js', ]
																});
													}
												},
												controller : 'PatientResponseController',
												templateUrl : 'views/calleradmin/patient-response.html',
												url : '/PatientResponse'

											})
											.state(
													'dashboard.userPreferrence/:fid',
													{
														resolve : {
															loadMyFile : function(
																	$ocLazyLoad) {
																return $ocLazyLoad
																		.load({
																			name : 'sbAdminApp',
																			files : [
																					'scripts/controllers/userPreferrenceController.js', ]
																		});
															}
														},
														controller : 'sortableController',
														templateUrl : 'views/settings/user-preferrence.html',
														url : '/UserPreferrence/:fid'

													})
									.state(
											'dashboard.add-caller-admin',
											{

												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/callerAdminController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'SaveCallerAdminController',
												templateUrl : 'views/calleradmin/add-caller-admin.html',
												url : '/add-caller-admin',
												title : 'Add Call Center Admin'
											})
									.state(
											'dashboard.EditCallerAdmin/:callerAdminId',
											{

												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/callerAdminController.js' ]
																});
													}
												},
												controller : 'EditCallerAdminController',
												templateUrl : 'views/calleradmin/add-caller-admin.html',
												url : '/EditCallerAdmin/:callerAdminId',
												title : 'Edit Call Center Admin'
											})
											.state(
											'dashboard.callerAdminSearchPatients',
											{
												templateUrl : 'views/calleradmin/caller-admin-search-patients.html',
												url : '/callerAdminSearchPatients',
												controller : "searchPatientsController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/callerAdminSearchPatientsController.js',
																	          'components/datetime/datetimepicker.css',
																				'components/datetime/moment.js',
																				'components/datetime/datetimepicker.js'
																	          ]
																});
													}
												}

											})
									// Caller Admin Ends

									// Lawyers starts
									.state(
											'dashboard.Lawyer',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/lawyerController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'ShowLawyerController',
												templateUrl : 'views/lawyer/viewlawyer.html',
												url : '/Lawyer'

											})
									.state(
											'dashboard.add-lawyer',
											{

												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/lawyerController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'SaveLawyerController',
												templateUrl : 'views/lawyer/add-lawyer.html',
												url : '/add-lawyer',
												title : 'Add Lawyer'
											})
									.state(
											'dashboard.EditLawyer/:lawyerId',
											{

												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/lawyerController.js' ]
																});
													}
												},
												controller : 'EditLawyerController',
												templateUrl : 'views/lawyer/add-lawyer.html',
												url : '/EditLawyer/:lawyerId',
												title : 'Edit Lawyer'
											})
									// End Lawyers
											
									// Caller starts
									.state(
											'dashboard.callers',
											{
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/callersController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'ShowCallersController',
												templateUrl : 'views/caller/view-callers.html',
												url : '/Callers'

											})
									.state(
											'dashboard.addCaller',
											{

												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [
																			'scripts/controllers/callersController.js',
																			'js/mask.js' ]
																});
													}
												},
												controller : 'SaveCallerController',
												templateUrl : 'views/caller/add-caller.html',
												url : '/addCaller',
												title : 'Add Caller'
											})
									.state(
											'dashboard.EditCaller/:callerId',
											{

												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/callersController.js' ]
																});
													}
												},
												controller : 'EditCallerController',
												templateUrl : 'views/caller/add-caller.html',
												url : '/EditCaller/:callerId',
												title : 'Edit Caller'
											})
									// Caller Ends
											
									.state(
											'dashboard.Changepassword',
											{
												templateUrl : 'views/changepassword/changepassword.html',
												url : '/Changepassword',
												controller : "changePasswordController",
												resolve : {
													loadMyFile : function(
															$ocLazyLoad) {

														return $ocLazyLoad
																.load({
																	name : 'sbAdminApp',
																	files : [ 'scripts/controllers/changePasswordController.js' ]
																});
													}
												}

											})
									.state(
											'dashboard.payment',
											{
												url : '/payment',
												templateUrl : 'views/payment/paymenthome.html',
												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad
														.load({
															name : 'sbAdminApp',
															files : [ ]
														});
													}
												},
											})
									.state(
											'dashboard.viewpayments',
											{
												url : '/viewpayments',
												templateUrl : 'views/payment/viewpayments.html',
												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
														return $ocLazyLoad.load({
															name:'sbAdminApp',
															files:['scripts/controllers/paymentController.js']
														});
														
													}
												},
												controller : 'PaymentController',
											})
									.state(
											'dashboard.carddetails',
											{
												url : '/carddetails',
												templateUrl : 'views/payment/carddetails.html',
												resolve : {
													loadMyFiles : function(
															$ocLazyLoad) {
													}
												}
											});// End Change password

						} ]).run( [ '$rootScope', function ($rootScope,$state, $stateParams) {
							$rootScope.$state = $state;
				            $rootScope.$stateParams = $stateParams;
					        $rootScope.$on('$stateChangeSuccess', function(event, to, toParams, from, fromParams) {
					        	if(to.name=='dashboard.viewlocations/:id'){
					        		$rootScope.hideMenu=true;
					        	}else{
					        		$rootScope.hideMenu=false;
					        	}
					        	$rootScope.nextState = to.name;
					            $rootScope.previousState = from.name;
					            if($rootScope.previousState=='dashboard.userPreferrence/:fid' && $rootScope.nextState!='dashboard.userPreferrence/:fid'){
					            	if(!$rootScope.exportPreferenceChanged){
					            		if(confirm("Do you want to save the preference?")){
					            			$rootScope.rootSaveUserExportPreference();
					            		}else{
					            			// nothing
					            		}
					            	}
					            }
					         });
					      }]).controller('authenticationController', function($rootScope, $scope, $http, $location, requestHandler) {
					    	  var getCountyPreferenceList=function(){
					    		  requestHandler.getRequest("Patient/checkCountyListType.json","").then(function(response){
					    				 $rootScope.countyListType=response.data.countyListType;
					    			});
					    	  };
					    	  var authenticate = function() {
						requestHandler
								.postRequest("getCurrentRole.json", "")
								.then(
										function(response) {
											if (response.data.role == "ROLE_SUPER_ADMIN") {
												$rootScope.authenticated = true;
												$rootScope.isAdmin = 1;
												$rootScope.username = response.data.username;
											} else if (response.data.role == "ROLE_CALLER_ADMIN") {
												$rootScope.authenticated = true;
												$rootScope.isAdmin = 2;
												$rootScope.username = response.data.username;
											} else if (response.data.role == "ROLE_LAWYER_ADMIN") {
												$rootScope.authenticated = true;
												$rootScope.isAdmin = 3;
												$rootScope.username = response.data.username;
											} else if (response.data.role == "ROLE_CALLER") {
												$rootScope.authenticated = true;
												$rootScope.isAdmin = 4;
												$rootScope.username = response.data.username;
											} else if (response.data.role == "ROLE_LAWYER") {
												$rootScope.authenticated = true;
												$rootScope.isAdmin = 5;
												$rootScope.username = response.data.username;
											}
										});
					};

					authenticate();
					//getCountyPreferenceList();
				});
sbAdminApp
		.directive(
				'validateEmail',
				function() {
					var EMAIL_REGEXP = /^[_a-zA-Z0-9]+(\.[_a-zA-Z0-9]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-zA-Z]{2,5})$/;
					return {
						require : 'ngModel',
						restrict : '',
						link : function(scope, elm, attrs, ctrl) {
							// only apply the validator if ngModel is present
							// and Angular has added the email validator
							if (ctrl && ctrl.$validators.email) {

								// this will overwrite the default Angular email
								// validator
								ctrl.$validators.email = function(modelValue) {
									return ctrl.$isEmpty(modelValue)
											|| EMAIL_REGEXP.test(modelValue);
								};
							}
						}
					};
				});

sbAdminApp
		.directive(
				'validateEmail',
				function() {
					var EMAIL_REGEXP = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,5})$/;
					return {
						require : 'ngModel',
						restrict : '',
						link : function(scope, elm, attrs, ctrl) {
							// only apply the validator if ngModel is present
							// and Angular has added the email validator
							if (ctrl && ctrl.$validators.email) {

								// this will overwrite the default Angular email
								// validator
								ctrl.$validators.email = function(modelValue) {
									return ctrl.$isEmpty(modelValue)
											|| EMAIL_REGEXP.test(modelValue);
								};
							}
						}
					};
				});

sbAdminApp.directive('validateMobile', function() {
	var USA_MOB_EXPR = /^(\([0-9]{3}\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	var USA_MOB_EXPR_NOSPACE = /^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	var USA_MOB_EXPR_NO = /^[0-9]{10}$/;
	return {
		require : 'ngModel',
		restrict : '',
		link : function(scope, elm, attrs, ngModel) {
			ngModel.$validators.validateMobile = function(modelValue) {
				if (modelValue == "" || modelValue == undefined) {
					return true;
				} else {
					return USA_MOB_EXPR.test(modelValue)
							|| USA_MOB_EXPR_NO.test(modelValue)
							|| USA_MOB_EXPR_NOSPACE.test(modelValue);
				}

			};
		}
	};
});

sbAdminApp.directive('validateName', function() {
	var NAME_EXPR = /^ *([a-zA-Z]+ ?)+ *$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	return {
		require : 'ngModel',
		restrict : '',
		link : function(scope, elm, attrs, ngModel) {
			ngModel.$validators.validateName = function(modelValue) {
				return NAME_EXPR.test(modelValue);// ||USA_MOB_EXPR_WITH_BR.test(modelValue);
			};
		}
	};
});

sbAdminApp.directive('validateNameusa', function() {
	var NAME_EXPR = /^ *([a-zA-Z,.]+ ?)+ *$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	return {
		require : 'ngModel',
		restrict : '',
		link : function(scope, elm, attrs, ngModel) {
			ngModel.$validators.validateNameusa = function(modelValue) {
				return NAME_EXPR.test(modelValue);// ||USA_MOB_EXPR_WITH_BR.test(modelValue);
			};
		}
	};
});

sbAdminApp.directive('validateNumber', function() {
	var NUMBER_EXPR = /^[0-9]*$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	return {
		require : 'ngModel',
		restrict : '',
		link : function(scope, elm, attrs, ngModel) {
			ngModel.$validators.validateNumber = function(modelValue) {
				return NUMBER_EXPR.test(modelValue);// ||USA_MOB_EXPR_WITH_BR.test(modelValue);
			};
		}
	};
});

sbAdminApp.directive('validateZipcode', function() {
	var ZIPCODE_EXPR = /^[0-9]{5}$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	return {
		require : 'ngModel',
		restrict : '',
		link : function(scope, elm, attrs, ngModel) {
			ngModel.$validators.validateZipcode = function(modelValue) {
				return ZIPCODE_EXPR.test(modelValue);// ||USA_MOB_EXPR_WITH_BR.test(modelValue);
			};
		}
	};
});

sbAdminApp.directive('validateCitytownship', function() {
	var NUMBER_EXPR = /^ *([a-zA-Z,()]+ ?)+ *$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	return {
		require : 'ngModel',
		restrict : '',
		link : function(scope, elm, attrs, ngModel) {
			ngModel.$validators.validateCitytownship = function(modelValue) {
				return NUMBER_EXPR.test(modelValue);// ||USA_MOB_EXPR_WITH_BR.test(modelValue);
			};
		}
	};
});

sbAdminApp.directive('usernameexists',['$q','$timeout','requestHandler',function($q, $timeout, requestHandler) {
		var CheckUsernameExists = function(isNew) {
			if (isNew === 1)
				return true;
			else
				return false;
			};
			return {
				restrict : 'A',
				require : 'ngModel',
				link : function(scope, element, attributes,ngModel) {
					ngModel.$asyncValidators.usernameexists = function(modelValue) {
					var defer = $q.defer();
					if(scope.isAdd){
						$timeout(function() {
							var isNew;
							var sendRequest = requestHandler.getRequest('checkUserNameExist.json?username='+modelValue).then(function(response) {
								isNew = response.data.status;
							});
							sendRequest.then(function() {
									if (CheckUsernameExists(isNew)) {
										defer.resolve();
									} else {
										defer.reject();
									}
									});
									isNew = false;
									}, 10);
					}else{
						defer.resolve();
					}
					
					
					
					
										return defer.promise;
									};
								}
							};
} ]);

// Checking Current Password
sbAdminApp.directive("password", function ($q, $timeout,requestHandler) {
    var CheckPasswordExists = function (isNew) {
        if(isNew!=true)
            return true;
        else
            return false;
    };
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attributes, ngModel) {
            ngModel.$asyncValidators.password = function (modelValue) {
                var defer = $q.defer();
                $timeout(function () {
                    var isNew;
                    var sendRequest=requestHandler.postRequest("checkPassword.json?oldPassword="+modelValue,0).then(function(results){
                        isNew=results.data.requestSuccess;
                    });

                    sendRequest.then(function(){

                        if (CheckPasswordExists(!isNew)){
                            defer.resolve();
                        }
                        else{
                            defer.reject();
                        } 
                    });
                    isNew = false;
                }, 10);

                return defer.promise;
            }
        }
    };

});