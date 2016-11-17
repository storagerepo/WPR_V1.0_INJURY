var myapp = angular.module('sbAdminApp', ['ui.sortable','requestModule','flash']);


myapp.controller('sortableController', function ($scope,$location,requestHandler,Flash) {
  var tmpList = [];
  
  $scope.rawScreens = [
    [{
      icon: './img/icons/facebook.jpg',
      title: 'Facebook (a)',
      link: 'http://www.facebook.com'
    }, {
      icon: './img/icons/youtube.jpg',
      title: 'Youtube (a)',
      link: 'http://www.youtube.com'
    }, {
      icon: './img/icons/gmail.jpg',
      title: 'Gmail (a)',
      link: 'http://www.gmail.com'
    }, {
      icon: './img/icons/google+.jpg',
      title: 'Google+ (a)',
      link: 'http://plus.google.com'
    }, {
      icon: './img/icons/twitter.jpg',
      title: 'Twitter (a)',
      link: 'http://www.twitter.com'
    }, {
      icon: './img/icons/yahoomail.jpg',
      title: 'Yahoo Mail (a)',
      link: 'http://mail.yahoo.com'
    }, {
      icon: './img/icons/pinterest.jpg',
      title: 'Pinterest (a)',
      link: 'http://www.pinterest.com'
    }],
    []
  ];
  
  
  $scope.sortingLog = [];
  
  function createOptions (listName) {
    var _listName = listName;
    var options = {
      placeholder: "app",
      connectWith: ".apps-container",
      activate: function() {
          console.log("list " + _listName + ": activate");
      },
      beforeStop: function() {
          console.log("list " + _listName + ": beforeStop");
      },
      change: function() {
          console.log("list " + _listName + ": change");
      },
      create: function() {
          console.log("list " + _listName + ": create");
      },
      deactivate: function() {
          console.log("list " + _listName + ": deactivate");
      },
      out: function() {
          console.log("list " + _listName + ": out");
      },
      over: function() {
          console.log("list " + _listName + ": over");
      },
      receive: function() {
          console.log("list " + _listName + ": receive");
      },
      remove: function() {
          console.log("list " + _listName + ": remove");
      },
      sort: function() {
          console.log("list " + _listName + ": sort");
      },
      start: function() {
          console.log("list " + _listName + ": start");
      },
      stop: function() {
          console.log("list " + _listName + ": stop");
      },
      update: function() {
          console.log("list " + _listName + ": update");
      }
    };
    return options;
  }

  $scope.sortableOptionsList = [createOptions('A'), createOptions('B')];
  
  $scope.logModels = function () {
    $scope.sortingLog = [];
    for (var i = 0; i < $scope.rawScreens.length; i++) {
      var logEntry = $scope.rawScreens[i].map(function (x) {
        return x.title;
      }).join(', ');
      logEntry = 'container ' + (i+1) + ': ' + logEntry;
      $scope.sortingLog.push(logEntry);
    }
  };
  
  	// Save Preferences
  	var userLookupPreferencesOriginal={};
  	$scope.userLookupPreferences={};
	$scope.userLookupPreferences.county=[];
	$scope.countyform=[];
	$scope.userLookupPreferences.userLookupPreferenceMappedForms=[{"type":1,"preferredId":[]},
	                                                              {"type":2,"preferredId":[1]},
	                                                             {"type":3,"preferredId":[2]}];
	// Selected County
	$scope.selectedCounties=function(countyId){
		var idx=$scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId.indexOf(countyId);
		// Already Selected Items
		if(idx>-1){
			$scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId.push(countyId);
		}
		
	};
	// Selected Tier
	$scope.selectedTier=function(tier){
		var idx=$scope.userLookupPreferences.userLookupPreferenceMappedForms[1].preferredId.indexOf(tier);
		// Already Selected Items
		if(idx>-1){
			$scope.userLookupPreferences.userLookupPreferenceMappedForms[1].preferredId.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.userLookupPreferences.userLookupPreferenceMappedForms[1].preferredId.push(tier);
		}
		
	};
	// Selected Age
	$scope.selectedAge=function(ageId){
		var idx=$scope.userLookupPreferences.userLookupPreferenceMappedForms[2].preferredId.indexOf(ageId);
		// Already Selected Items
		if(idx>-1){
			$scope.userLookupPreferences.userLookupPreferenceMappedForms[2].preferredId.splice(idx,1);
		}
		// Add New Items
		else{
			$scope.userLookupPreferences.userLookupPreferenceMappedForms[2].preferredId.push(ageId);
		}
		
	};
	
	 $scope.isChecked=function(){
			if($scope.countyform.length>0){
				$.each($scope.countyform, function(index,value) {
					if(document.getElementById('checkAll').checked==true){
						var idx=$scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId.indexOf(value.countyId);
						// Already Selected Items
						if(idx==-1){
							$scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId.push(value.countyId);
						}
						
						$("#"+value.countyId).checked==true;
						//$("#"+value.id).prop('checked',$(this).prop("checked"));
					}
					else if(document.getElementById('checkAll').checked==false){
						$scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId=[];
						//$("#"+value.id).prop('checked', $(this).prop("checked"));
						$("#"+value.countyId).checked==false;
					}
				});
				
			}
		};
	
	// Get County List
	 requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
			$scope.countyform=response.data.countyList;
	});
	 
	// Get User Lookup Preference
	 requestHandler.getRequest("Patient/getUserLookupPreferences.json","").then(function(response){
			$scope.userLookupPreferences=response.data.userLookupPreferencesForm;
			userLookupPreferencesOriginal=response.data.userLookupPreferencesForm;
	});
	
	 $scope.saveUserLookupPreferences=function(){
			
		 requestHandler.postRequest("/Patient/mergeUserLookupPreferences.json",$scope.userLookupPreferences).then(function(response){
			  Flash.create('success', "You have Successfully Updated!");
			  $location.path('dashboard/UserPreferrence');
		 });
		
	 };
	 
	 // Compare Objects
	 $scope.isClean = function() {
	        return angular.equals (userLookupPreferencesOriginal,$scope.userLookupPreferences);
	    };
	 
});