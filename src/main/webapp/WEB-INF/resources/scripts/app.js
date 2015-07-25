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
    'angularUtils.directives.dirPagination'
  ])
  .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider) {

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
                    'scripts/directives/header/header.js',
                    'scripts/directives/header/header-notification/header-notification.js',
                    'scripts/directives/sidebar/sidebar.js',
                    'scripts/directives/sidebar/sidebar-search/sidebar-search.js'
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
              'scripts/controllers/main.js',
              'scripts/directives/timeline/timeline.js',
              'scripts/directives/notifications/notifications.js',
              'scripts/directives/chat/chat.js',
              'scripts/directives/dashboard/stats/stats.js'
              ]
            })
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
            })
          }
        }
    })
      .state('dashboard.blank',{
        templateUrl:'views/pages/blank.html',
        url:'/blank'
    })
      .state('login',{
        templateUrl:'views/pages/login.html',
        url:'/login'
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
            controller:'ShowStaffController',
            url:'/staff',
        resolve: {
            loadMyFile:function($ocLazyLoad) {
                $ocLazyLoad.load({
                    name:'sbAdminApp',
                    files:['scripts/controllers/staffController.js']
                });
            }
        }
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
        controller:'ShowDoctorsCtrl',
       
        resolve: {
          loadMyFile:function($ocLazyLoad) {
            
            $ocLazyLoad.load({
                name:'sbAdminApp',
                files:['scripts/controllers/Doctors.js']
            });
          }
        }
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
            controller: 'ShowPatientController',
            url:'/patient',
            resolve: {
                loadMyFile:function($ocLazyLoad) {
                  
                  $ocLazyLoad.load({
                      name:'sbAdminApp',
                      files:['scripts/controllers/patientController.js']
                  });
                }
              }
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
                files:['components/datetime/datetimepicker.css',
                       'components/datetime/moment.js',
                       'components/datetime/datetimepicker.js',
                       'scripts/controllers/appointmentController.js'
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
                      files:['components/datetime/datetimepicker.css',
                             'components/datetime/moment.js',
                             'components/datetime/datetimepicker.js',
                             'scripts/controllers/CallLogsController.js']
                  });
                }
              }
            
        });// End Call logs
     


  }]);


angular.module('sbAdminApp')
  .controller('roleController', function($scope,$http,$location) {
  /* $http.post('http://localhost:8081/Injury/Staff/getCurrentRole.json').then(function(response) {
	  
	   $scope.admin=false;
	   $scope.staff=false;
	   if(response.data.role=="ROLE_ADMIN"){
		   $scope.admin=true;
		   $scope.staff=true;
	   }
	   else if(response.data.role=="ROLE_STAFF"){
		   $scope.staff=true;
	   }
	   else{
		   $location.path('/logout');
	   }
	   
   	});	
*/
});

angular.module('sbAdminApp').factory('loginFactory',function(){
	var isRole={};
	
	isRole.isAdmin=function(role){
		alert(role);
		if(role=="ROLE_ADMIN"){
			return true;
		}
		else{
			return false;
		}
	};
	
	isRole.isStaff=function(role){
		if(role=="ROLE_STAFF"){
			return true;
		}
	};
	
	return isRole;
});