'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
angular
  .module('sbAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
    'angularUtils.directives.dirPagination',
    'requestModule'
  ])
  .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',"$httpProvider",function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,$httpProvider) {

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
                        	  alert("Some thing went wrong :(");
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
            templateUrl:'views/staff-view/staff.html',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                    $ocLazyLoad.load({
                        name:'sbAdminApp',
                        files:['scripts/controllers/staffController.js']
                    });
                }
            },
            controller:'ShowStaffController',
            url:'/staff'
        
        })
        .state('dashboard.add-staff',{
        templateUrl:'views/staff-view/add-staff.html',
        url:'/add-staff',
        title:'Add Staff',
        controller:'SaveStaffController',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/staffController.js']
              });
            }
          }
    }).state('dashboard.EditStaff/:id',{
        templateUrl:'views/staff-view/add-staff.html',
        url:'/EditStaff/:id',
        title:'Edit Staff',
        controller:'EditStaffController',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/staffController.js']
              });
            }
          }
    })//End Staff
    //Start Doctor
    .state('dashboard.doctor',{
        templateUrl:'views/doctor.html',
        url:'/doctor',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/Doctors.js']
              });
            }
          },
        controller:'ShowDoctorsCtrl'
       
        
    })
     .state('dashboard.add-doctor',{
        templateUrl:'views/add-doctor.html',
        url:'/add-doctor',
        controller:'AddDoctorsCtrl',
        title:'Add Doctor',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
              
              $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/Doctors.js']
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
              
              $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:['scripts/controllers/Doctors.js']
              });
            }
          }
    })//End Doctor
        .state('dashboard.patient',{
            templateUrl:'views/patient/patient.html',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  $ocLazyLoad.load({
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
            url:'/EditPatient/:id',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/patientController.js']
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
            
            $ocLazyLoad.load({
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
                  
                  $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/CallLogsController.js',
                             'components/datetime/datetimepicker.css',
                             'components/datetime/moment.js',
                             'components/datetime/datetimepicker.js']
                  });
                }
              }
            
        });// End Call logs
     
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