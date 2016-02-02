'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
var sbAdminApp=angular
  .module('sbAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
    'angularUtils.directives.dirPagination',
    'requestModule',
    'flash',
    'uiGmapgoogle-maps'
  ]);
  
sbAdminApp.config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',"$httpProvider",function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,$httpProvider) {

	 /* uiGmapGoogleMapApiProvider.configure({
	        key: 'AIzaSyCSrffdwIzj8p4RZgvglbhQEEjgEx7ZUKQ',
	        v: '3.20', //defaults to latest 3.X anyhow
	        libraries: 'weather,geometry,visualization'
	    });
	  */
	  $httpProvider.defaults.withCredentials = true;

      $httpProvider.interceptors.push(['$q','$location','$injector',function ($q, $location,$injector) {
              return {
                  'request': function(request) {

                      return request;
                  },
                  'response': function (response) {
                      return response;
                  },
                  'responseError': function (rejection) {
                      switch (rejection.status) {
                          case 400: {
                              break;
                          }
                          case 401:{
                            alert("restricted");
                          }
                          case 403: {
                            
                            alert("Access Denied!!!");
                              break;
                          }
                          case 500: {
                        	  alert("Please try again!");
                              break;
                          }
                          default : {
                             

                              break;
                          }
                      }

                      return $q.reject(rejection);
                  }
              };
          }]);  
	  
	  
    $ocLazyLoadProvider.config({
      debug:false,
      events:true
    });

    $urlRouterProvider.otherwise('/dashboard/home');

    $stateProvider
      .state('dashboard', {
        url:'/dashboard',
        templateUrl: 'views/dashboard/main.html',
        resolve: {
            loadMyDirectives:function($ocLazyLoad){
                return $ocLazyLoad.load(
                {
                    name:'sbAdminApp',
                    files:[
                    'scripts/directives/header/header-notification/header-notification.js'
                    ]
                }),
                $ocLazyLoad.load(
                {
                   name:'toggle-switch',
                   files:["resources/components/angular-toggle-switch/angular-toggle-switch.min.js",
                          "resources/components/angular-toggle-switch/angular-toggle-switch.css"
                      ]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngAnimate',
                  files:['resources/components/angular-animate/angular-animate.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngCookies',
                  files:['resources/components/angular-cookies/angular-cookies.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngResource',
                  files:['resources/components/angular-resource/angular-resource.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngSanitize',
                  files:['resources/components/angular-sanitize/angular-sanitize.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngTouch',
                  files:['resources/components/angular-touch/angular-touch.js']
                })
            }
        }
    })
      .state('dashboard.home',{
        url:'/home',
        controller: 'MainCtrl',
        templateUrl:'views/dashboard/home.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              'scripts/controllers/main.js'
              ]
            });
          }
        }
      })
      .state('dashboard.form',{
        controller:'FormCtrl',
        templateUrl:'views/form.html',
        url:'/form',
       resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              'scripts/controllers/form.js'
              ]
            });
          }
        }
    })
      .state('dashboard.blank',{
        templateUrl:'views/pages/blank.html',
        url:'/blank'
    })
      .state('login',{
        templateUrl:'views/pages/login.html',
        url:'/login',
       isLogin:true
    })
      .state('dashboard.chart',{
        templateUrl:'views/chart.html',
        url:'/chart',
        controller:'ChartCtrl',
        resolve: {
          loadMyFile:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'chart.js',
              files:[
                'resources/components/angular-chart.js/dist/angular-chart.min.js',
                'resources/components/angular-chart.js/dist/angular-chart.css'
              ]
            }),
            $ocLazyLoad.load({
                name:'sbAdminApp',
                files:['scripts/controllers/chartContoller.js']
            })
          }
        }
    })
      .state('dashboard.table',{
        templateUrl:'views/table.html',
         controller:'tableCtrl',
        url:'/table'
    })
      .state('dashboard.panels-wells',{
          templateUrl:'views/ui-elements/panels-wells.html',
          url:'/panels-wells'
      })
      .state('dashboard.buttons',{
        templateUrl:'views/ui-elements/buttons.html',
        url:'/buttons'
    })
      .state('dashboard.notifications',{
        templateUrl:'views/ui-elements/notifications.html',
        url:'/notifications'
    })
      .state('dashboard.typography',{
       templateUrl:'views/ui-elements/typography.html',
       url:'/typography'
   })
      .state('dashboard.icons',{
       templateUrl:'views/ui-elements/icons.html',
       url:'/icons'
   })
      .state('dashboard.grid',{
       templateUrl:'views/ui-elements/grid.html',
       url:'/grid'
   })//staff starts
        .state('dashboard.staff',{
        	 resolve: {
                 loadMyFile:function($ocLazyLoad) {
                   return $ocLazyLoad.load({
                       name:'sbAdminApp',
                       files:['scripts/controllers/staffController.js',
                              'js/mask.js']
                   });
                 }
               },
            controller:'ShowStaffController',
            templateUrl:'views/staff-view/staff.html',
            url:'/staff'
        
        })
        .state('dashboard.add-staff',{
       
        resolve: {
            loadMyFiles:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/staffController.js',
                         'js/mask.js']
              });
            }
          },
          controller:'SaveStaffController',
          templateUrl:'views/staff-view/add-staff.html',
          url:'/add-staff',
          title:'Add Caller'
    }).state('dashboard.EditStaff/:id',{
        
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/staffController.js']
              });
            }
          },
          controller:'EditStaffController',
          templateUrl:'views/staff-view/add-staff.html',
          url:'/EditStaff/:id',
          title:'Edit Caller'
    })//End Staff
    .state('dashboard.clinic',{
    	resolve: {
            loadMyFile:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/clinicController.js']
              });
            }
          },
       controller:'ShowClinicController',
       templateUrl:'views/clinic/clinic.html',
       url:'/clinic'
    })
    .state('dashboard.add-clinic',{
    	 resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:[
                         'scripts/controllers/clinicController.js',
                         'components/datetime/datetimepicker.css',
                         'components/datetime/moment.js',
                         'components/datetime/datetimepicker.js']
              });
            }
          },
        controller:'SaveClinicController',
        templateUrl:'views/clinic/add-clinic.html',
        url:'/add-clinic'
       
    }).state('dashboard.edit-clinic/:id',{
   	 resolve: {
         loadMyFile:function($ocLazyLoad) {
           
           return $ocLazyLoad.load({
               name:'sbAdminApp',
               files:[
                      'scripts/controllers/clinicController.js',
                      'components/datetime/datetimepicker.css',
                      'components/datetime/moment.js',
                      'components/datetime/datetimepicker.js']
           });
         }
       },
     controller:'EditClinicController',
     templateUrl:'views/clinic/add-clinic.html',
     url:'/edit-clinic/:id'
    
 })
    //Start Doctor
    .state('dashboard.doctor',{
        
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:[
                         'scripts/controllers/Doctors.js']
              });
            }
          },
        controller:'ShowDoctorsCtrl',
        templateUrl:'views/doctor.html',
        url:'/doctor'
       
        
    })
     .state('dashboard.add-doctor',{
        templateUrl:'views/add-doctor.html',
        url:'/add-doctor',
        controller:'AddDoctorsCtrl',
        title:'Add Doctor',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/Doctors.js',
                        'components/datetime/datetimepicker.css',
                         'components/datetime/moment.js',
                         'components/datetime/datetimepicker.js']
              });
            }
          }
    })
    .state('dashboard.EditDoctor/:id',{
        templateUrl:'views/add-doctor.html',
        url:'/EditDoctor/:id',
        controller:'EditDoctorController',
        title:'Edit Doctor',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
            	return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/Doctors.js',
                         'components/datetime/datetimepicker.css',
                         'components/datetime/moment.js',
                         'components/datetime/datetimepicker.js']
              });
            }
          }
    })//End Doctor
        .state('dashboard.patient',{
            templateUrl:'views/patient/patient.html',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  return $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/patientController.js']
                  });
                }
              },
            controller: 'ShowPatientController',
            url:'/patient'
            
            
        }).state('dashboard.EditPatient/:id',{
            templateUrl:'views/patient/add-patients.html',
            controller:"EditPatientController",
            title:"Edit Patient",
            url:'/EditPatient/:id',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  return $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/patientController.js',
                             'components/datetime/datetimepicker.css',
                             'components/datetime/moment.js',
                             'components/datetime/datetimepicker.js']
                  });
                }
              }
        })
        .state('dashboard.add-patient',{
            templateUrl:'views/patient/add-patients.html',
            controller:"AddPatientController",
            title: "Add Patient",
            url:'/add-patient',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  return $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/patientController.js',
                             'components/datetime/datetimepicker.css',
                             'components/datetime/moment.js',
                             'components/datetime/datetimepicker.js']
                  });
                }
              }
        })// End Patient
        .state('dashboard.appointment',{
        templateUrl:'views/appointment/appointment.html',
        url:'/appointment',
        controller:'ShowAppointmentsCtrl',
        resolve: {
          loadMyFile:function($ocLazyLoad) {
            
            return $ocLazyLoad.load({
                name:'sbAdminApp',
                files:[
                       'scripts/controllers/appointmentController.js',
                       'components/datetime/datetimepicker.css',
                       'components/datetime/moment.js',
                       'components/datetime/datetimepicker.js'
                       ]
            });
          }
        }
    })// End Appointment
    
 
        .state('dashboard.Calllogs/:id',{
            templateUrl:'views/calllogs/calllogs.html',
            url:'/Calllogs/:id',
            controller:"showCallLogsController",
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  return $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/CallLogsController.js',
                             'components/datetime/datetimepicker.css',
                             'components/datetime/moment.js',
                             'components/datetime/datetimepicker.js']
                  });
                }
              }
            
        }) .state('dashboard.viewlocations/:id',{
            templateUrl:'views/maplocation/view-nearby-clinics.html',
            url:'/viewlocations/:id',
            controller:"showNearByClinicController",
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  return $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/MapLocationController.js']
                  });
                }
              }
            
        })// End Call logs
        .state('dashboard.Changepassword',{
            templateUrl:'views/changepassword/changepassword.html',
            url:'/Changepassword',
            controller:"changePasswordController",
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  return $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/changePasswordController.js']
                  });
                }
              }
            
        });// End Change password
     
  }]).controller('authenticationController', function($rootScope,$scope,$http,$location,requestHandler) {
     var authenticate=function(){
       requestHandler.postRequest("Staff/getCurrentRole.json","").then(function(response) {
       
        if(response.data.role=="ROLE_ADMIN"){
            $rootScope.authenticated=true;
            $rootScope.isAdmin=true;
            $rootScope.username=response.data.username;
         }
       else if(response.data.role=="ROLE_STAFF"){
            $rootScope.authenticated=true;
            $rootScope.isAdmin=false;
            $rootScope.username=response.data.username;
         }
      else
         {
            $rootScope.authenticated=false;
            $rootScope.isAdmin=false;
            
         }
       });
     };

     authenticate(); 
  });
sbAdminApp.directive('validateEmail',function(){
	 var EMAIL_REGEXP = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,5})$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ctrl) {
	            // only apply the validator if ngModel is present and Angular has added the email validator
	            if (ctrl && ctrl.$validators.email) {

	                // this will overwrite the default Angular email validator
	                ctrl.$validators.email = function(modelValue) {
	                    return ctrl.$isEmpty(modelValue) || EMAIL_REGEXP.test(modelValue);
	                };
	            }
	        }
	    };
});

sbAdminApp.directive('validateEmail',function(){
	 var EMAIL_REGEXP = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,5})$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ctrl) {
	            // only apply the validator if ngModel is present and Angular has added the email validator
	            if (ctrl && ctrl.$validators.email) {

	                // this will overwrite the default Angular email validator
	                ctrl.$validators.email = function(modelValue) {
	                    return ctrl.$isEmpty(modelValue) || EMAIL_REGEXP.test(modelValue);
	                };
	            }
	        }
	    };
});

sbAdminApp.directive('validateMobile',function(){
	 var USA_MOB_EXPR = /^(\([0-9]{3}\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	 var USA_MOB_EXPR_NOSPACE = /^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	 var USA_MOB_EXPR_NO=/^[0-9]{10}$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ngModel) {
	        	ngModel.$validators.validateMobile = function(modelValue) {
	        		if(modelValue=="" || modelValue==undefined){
	        			return true;
	        		}else {
	                    return USA_MOB_EXPR.test(modelValue)||USA_MOB_EXPR_NO.test(modelValue)||USA_MOB_EXPR_NOSPACE.test(modelValue);
	        		}
	        		
	                };
	        }
	    };
});

sbAdminApp.directive('validateName',function(){
	 var NAME_EXPR = /^ *([a-zA-Z]+ ?)+ *$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ngModel) {
	           	ngModel.$validators.validateName = function(modelValue) {
	                    return NAME_EXPR.test(modelValue);//||USA_MOB_EXPR_WITH_BR.test(modelValue);
	                };
	        }
	    };
});

sbAdminApp.directive('validateNameusa',function(){
	 var NAME_EXPR = /^ *([a-zA-Z,.]+ ?)+ *$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ngModel) {
	           	ngModel.$validators.validateNameusa = function(modelValue) {
	                    return NAME_EXPR.test(modelValue);//||USA_MOB_EXPR_WITH_BR.test(modelValue);
	                };
	        }
	    };
});

sbAdminApp.directive('validateNumber',function(){
	 var NUMBER_EXPR = /^[0-9]*$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ngModel) {
	           	ngModel.$validators.validateNumber = function(modelValue) {
	                    return NUMBER_EXPR.test(modelValue);//||USA_MOB_EXPR_WITH_BR.test(modelValue);
	                };
	        }
	    };
});

sbAdminApp.directive('validateCitytownship',function(){
	 var NUMBER_EXPR = /^ *([a-zA-Z,()]+ ?)+ *$/;
	// var USA_MOB_EXPR_WITH_BR=/^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;
	    return {
	        require: 'ngModel',
	        restrict: '',
	        link: function(scope, elm, attrs, ngModel) {
	           	ngModel.$validators.validateCitytownship = function(modelValue) {
	                    return NUMBER_EXPR.test(modelValue);//||USA_MOB_EXPR_WITH_BR.test(modelValue);
	                };
	        }
	    };
});




