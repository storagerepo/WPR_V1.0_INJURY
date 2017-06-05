var myapp = angular.module('sbAdminApp', ['ui.sortable','requestModule','flash','searchModule']);


myapp.controller('sortableController', function ($rootScope,$scope,$state,$stateParams,$location,requestHandler,Flash,searchService) {
 
	$scope.rawScreens = [[],[]];
  
  
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
    	  if(_listName=='B'){
    		  $scope.updateDefaultValueList();
    	  }else{
    		  $scope.removeDefaultValueList();
    	  }
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
  
  
  	// Save Preferences
  	$scope.prefenceTabActive=$stateParams.fid;
  	var userLookupPreferencesOriginal={};
  	$scope.initUserLookupPreference=function(){
  	  	$scope.userLookupPreferences={};
  		$scope.userLookupPreferences.county=[];
  		$scope.countyform=[];
  		$scope.userLookupPreferences.userLookupPreferenceMappedForms=[];
  		$scope.getCountiesList();
  		$scope.getUserLookupPreference();
  	};
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
	$scope.getCountiesList=function(){
		requestHandler.getRequest("Patient/getMyCounties.json","").then(function(response){
			$scope.countyform=response.data.countyList;
		});
	};
	
	 
	// Get User Lookup Preference
	$scope.getUserLookupPreference=function(){
		requestHandler.getRequest("Patient/getUserLookupPreferences.json","").then(function(response){
			$scope.userLookupPreferences=response.data.userLookupPreferencesForm;
			userLookupPreferencesOriginal=angular.copy(response.data.userLookupPreferencesForm);
		});
	};
	
	// Init Function
	$scope.initUserLookupPreference();
	
	$scope.resetuserLookupPreferencesOriginal=function(){
		angular.copy(userLookupPreferencesOriginal,$scope.userLookupPreferences);
	};
	
	 $scope.saveUserLookupPreferences=function(){
			
		 requestHandler.postRequest("/Patient/mergeUserLookupPreferences.json",$scope.userLookupPreferences).then(function(response){
			  Flash.create('success', "You have Successfully Updated!");
			  $scope.getUserLookupPreference();
			 var countySelected=searchService.getCounty();
			 var countyListType=searchService.getCountyListType();
			 if(countySelected!='' && countyListType==2){
				  angular.forEach($scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId,function(item,index){
					  if(userLookupPreferencesOriginal.userLookupPreferenceMappedForms[0].preferredId.indexOf(item)>-1){
						  // Do Nothing
					  }else{
						  countySelected.push({"id":item});
					  }
				  });
				  var finalCountySelection=[];
				  angular.forEach(countySelected,function(item1,index1){
					  if($scope.userLookupPreferences.userLookupPreferenceMappedForms[0].preferredId.indexOf(item1.id)>-1){
						  // Do Nothing
						  finalCountySelection.push(item1);
					  }else{
						  // Do Nothing
					  }
				  });
				  searchService.setCounty(finalCountySelection);
			 }
			 $(function(){
					$("html,body").scrollTop(0);
			  });
		 });
		
	 };
	 
	 // Compare Objects
	 $scope.isClean = function() {
	    return angular.equals (userLookupPreferencesOriginal,$scope.userLookupPreferences);
	 };
	    
	 $scope.exportFieldsFormsOriginal="";
	 $scope.exportFieldsFormsSelected="";
	//Get Export Fields List
	 $scope.getExportFieldsList=function(){
		requestHandler.getRequest("Patient/getAllExportFieldss.json","").then(function(response){
				$scope.exportFieldsFormsOriginal=response.data.exportFieldsForms;
				$scope.getUserExportPreference();
		});
	 };
	
	// Get User Export Preferences
	$scope.getUserExportPreference=function(){
		requestHandler.getRequest("Patient/getAllUserExportPreferencess.json","").then(function(response){
			$scope.exportFieldsFormsSelected=response.data.userExportPreferencesForms;
			$scope.getDefaultValueMinorField();
			$scope.splitObjects();
		});
	};
	
	// Get Default Value Minor field 37 ---> Static Id for this field, 17 ---> Contact Phone Field Id
	$scope.exportDefaultValueList=[];
	var exportDefaultValueListOrginal=[];
	$scope.exportFormatValueList=[];
	var exportFormatValueListOrginal=[];
	$scope.getDefaultValueMinorField=function(){
		angular.forEach($scope.exportFieldsFormsSelected,function(item,index){
			if(item.fieldId==37){
				var result = $.grep(exportDefaultValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportDefaultCopy=angular.copy(item);
					$scope.exportDefaultValueList.push(exportDefaultCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
			
			if(item.fieldId==17){
				var result = $.grep(exportFormatValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportFormatCopy=angular.copy(item);
					if(exportFormatCopy.format==0){
						exportFormatCopy.format="";
					}
					exportFormatCopy.format=exportFormatCopy.format.toString();
					$scope.exportFormatValueList.push(exportFormatCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
		});
		exportDefaultValueListOrginal=angular.copy($scope.exportDefaultValueList);
		exportFormatValueListOrginal=angular.copy($scope.exportFormatValueList);
	 };
	
	$scope.updateDefaultValueList=function(){
		angular.forEach($scope.rawScreens[1],function(item,index){
			if(item.fieldId==37){
				var result = $.grep(exportDefaultValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportDefaultCopy=angular.copy(item);
					$scope.exportDefaultValueList.push(exportDefaultCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
			
			if(item.fieldId==17){
				var result = $.grep(exportFormatValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportFormatCopy=angular.copy(item);
					if(exportFormatCopy.format==0){
						exportFormatCopy.format="";
					}
					exportFormatCopy.format=exportFormatCopy.format.toString();
					$scope.exportFormatValueList.push(exportFormatCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
		});
		exportDefaultValueListOrginal=angular.copy($scope.exportDefaultValueList);
		exportFormatValueListOrginal=angular.copy($scope.exportFormatValueList);
	};
	
	$scope.removeDefaultValueList=function(){
		angular.forEach($scope.rawScreens[0],function(item,index){
			//var removeIndex=$scope.exportDefaultValueList.indexOf(item);
			if(item.fieldId==37){
				$scope.exportDefaultValueList.splice(0,1);
			}
			if(item.fieldId==17){
				$scope.exportFormatValueList.splice(0,1);
			}
		});
	};
	
	// Split List Objects
	var rawScreensOriginal=[];
	$scope.splitObjects=function(){
		$scope.rawScreens[0]=[];
		 $scope.rawScreens[1]=[];
		angular.forEach($scope.exportFieldsFormsOriginal,function(item,index){
			 var result = $.grep($scope.exportFieldsFormsSelected, function(e){ return e.fieldId == item.fieldId;});
					if (result.length == 0) {
		 			 // not found
			 			$scope.rawScreens[0].push(item);
					} else if (result.length == 1) {
						
		  			// access the foo property using result[0].foo
					}
		});
		angular.copy($scope.exportFieldsFormsSelected,$scope.rawScreens[1]);
		rawScreensOriginal=angular.copy($scope.rawScreens);
	};
	
	// Get Exports Fields List
	$scope.getExportFieldsList();

	// Save User Export Preferences
	$scope.saveUserExportPreferences=function(fromLocation){
		$scope.userPreferenceSaveForm={};
		$scope.userPreferenceSaveForm.exportFieldsForms=$scope.rawScreens[1];
		angular.forEach($scope.exportDefaultValueList,function(item,index){
			angular.forEach($scope.userPreferenceSaveForm.exportFieldsForms,function(item1,index1){
				if(item.fieldId==item1.fieldId){
					item1.defaultValue=item.defaultValue;
				}
			});
		});
		
		angular.forEach($scope.exportFormatValueList,function(item,index){
			angular.forEach($scope.userPreferenceSaveForm.exportFieldsForms,function(item1,index1){
				if(item.fieldId==item1.fieldId){
					item1.format=item.format;
				}
			});
		});

		requestHandler.postRequest("/Patient/saveUpdateUserExportPreferences.json",$scope.userPreferenceSaveForm).then(function(response){
			  if(fromLocation==1){
				  Flash.create('success', "You have Successfully Updated!");
				  $scope.getExportFieldsList();
				  $(function(){
						$("html,body").scrollTop(0);
				  });
			  }
		 });
		
	 };
	
	 
	 // Move All
	 $scope.moveAll=function(){
		 $scope.rawScreens[0]=[];
		 $scope.rawScreens[1]=[];
		angular.copy($scope.exportFieldsFormsOriginal,$scope.rawScreens[1]);
	 };
	
	 // Reset
	 $scope.resetAll=function(){
		 $scope.splitObjects();
	 };
	 
	 // Remove All
	 $scope.removeAll=function(){
		 angular.copy($scope.exportFieldsFormsOriginal,$scope.rawScreens[0]);
		 $scope.rawScreens[1]=[];
	 };
	 // Compare Two List
	 $rootScope.exportPreferenceChanged=true;
	 $scope.isCleanForExport = function() {
		 $rootScope.exportPreferenceChanged=angular.equals(rawScreensOriginal,$scope.rawScreens)&&angular.equals(exportDefaultValueListOrginal,$scope.exportDefaultValueList)&&angular.equals(exportFormatValueListOrginal,$scope.exportFormatValueList);
	    return angular.equals(rawScreensOriginal,$scope.rawScreens)&&angular.equals(exportDefaultValueListOrginal,$scope.exportDefaultValueList)&&angular.equals(exportFormatValueListOrginal,$scope.exportFormatValueList);
	 };
	 
	 $rootScope.rootSaveUserExportPreference=function(){
		 $scope.saveUserExportPreferences(2);
	 };
	
});