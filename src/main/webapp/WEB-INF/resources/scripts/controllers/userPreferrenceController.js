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
	
	// Get Default Value Minor field 34 ---> Static Id for this field, 17 ---> Contact Phone Field Id
	$scope.exportDefaultValueList=[];
	var exportDefaultValueListOrginal=[];
	$scope.exportFormatValueList=[];
	var exportFormatValueListOrginal=[];
	$scope.exportDefaultValueListSelected=[];
	$scope.exportFormatValueListSelected=[];
	$scope.getDefaultValueMinorField=function(){
		angular.forEach($scope.exportFieldsFormsSelected,function(item,index){
			if(item.fieldId==34){
				var result = $.grep(exportDefaultValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportDefaultCopy=angular.copy(item);
					$scope.exportDefaultValueList.push(exportDefaultCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
			
			if(item.fieldId==17||item.fieldId==12){
				
				var result = $.grep(exportFormatValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportFormatCopy=angular.copy(item);
					if(item.fieldId==17){
						exportFormatCopy.dropDownValue=[{"name":"999-999-9999","groupName":"Default","value":0},{"name":"+1-(999)-999-9999","groupName":"Format Type 1","value":1},{"name":"+1 (999) 999 9999","groupName":"Format Type 1","value":2},{"name":"+1(999)9999999","groupName":"Format Type 1","value":3},{"name":"+19999999999","groupName":"Format Type 1","value":4}
						,{"name":"1 (999) 999 9999","groupName":"Format Type 2","value":5},{"name":"1(999)9999999","groupName":"Format Type 2","value":6},{"name":"19999999999","groupName":"Format Type 2","value":7}
						,{"name":"(999)-999-9999","groupName":"Format Type 3","value":8},{"name":"(999) 999 9999","groupName":"Format Type 3","value":9},{"name":"(999)9999999","groupName":"Format Type 3","value":10},{"name":"9999999999","groupName":"Format Type 3","value":11}];
					}
					if(item.fieldId==12){
						exportFormatCopy.dropDownValue=[{"name":"LAST FIRST MIDDLE","groupName":"","value":0},{"name":"FIRST LAST","groupName":"","value":1},{"name":"FIRST MIDDLE LAST","groupName":"","value":2},{"name":"LAST FIRST","groupName":"","value":3}];
					}
					
					//exportFormatCopy.format=exportFormatCopy.format.toString();
					$scope.exportFormatValueList.push(exportFormatCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
		});
		exportDefaultValueListOrginal=angular.copy($scope.exportDefaultValueList);
		exportFormatValueListOrginal=angular.copy($scope.exportFormatValueList);
		$scope.exportDefaultValueListSelected=angular.copy($scope.exportDefaultValueList);
		$scope.exportFormatValueListSelected=angular.copy($scope.exportFormatValueList);
	 };
	
	$scope.updateDefaultValueList=function(){
		angular.forEach($scope.rawScreens[1],function(item,index){
			if(item.fieldId==34){
				var result = $.grep(exportDefaultValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportDefaultCopy=angular.copy(item);
					$scope.exportDefaultValueList.push(exportDefaultCopy);
				} else if (result.length == 1) {
					
	  			// access the foo property using result[0].foo
				}
			}
			
			if(item.fieldId==17||item.fieldId==12){
				
				var result = $.grep(exportFormatValueListOrginal, function(e){ return e.fieldId == item.fieldId;});
				if (result.length == 0) {
	 			 // not found
					var exportFormatCopy=angular.copy(item);
					if(item.fieldId==17){
						exportFormatCopy.dropDownValue=[{"name":"999-999-9999","groupName":"Default","value":0},{"name":"+1-(999)-999-9999","groupName":"Format Type 1","value":1},{"name":"+1 (999) 999 9999","groupName":"Format Type 1","value":2},{"name":"+1(999)9999999","groupName":"Format Type 1","value":3},{"name":"+19999999999","groupName":"Format Type 1","value":4}
						,{"name":"1 (999) 999 9999","groupName":"Format Type 2","value":5},{"name":"1(999)9999999","groupName":"Format Type 2","value":6},{"name":"19999999999","groupName":"Format Type 2","value":7}
						,{"name":"(999)-999-9999","groupName":"Format Type 3","value":8},{"name":"(999) 999 9999","groupName":"Format Type 3","value":9},{"name":"(999)9999999","groupName":"Format Type 3","value":10},{"name":"9999999999","groupName":"Format Type 3","value":11}];
					}
					if(item.fieldId==12){
						exportFormatCopy.dropDownValue=[{"name":"LAST FIRST MIDDLE","groupName":"","value":0},{"name":"FIRST LAST","groupName":"","value":1},{"name":"FIRST MIDDLE LAST","groupName":"","value":2},{"name":"LAST FIRST","groupName":"","value":3}];
					}
					
					//exportFormatCopy.format=exportFormatCopy.format.toString();
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
			var defaultValueindex = arrayObjectIndexOf($scope.exportDefaultValueList,item.fieldId);
			if(item.fieldId==34){
				if(defaultValueindex!=-1){
					$scope.exportDefaultValueList.splice(defaultValueindex,1);
				}
			}
			var defaultFormatindex = arrayObjectIndexOf($scope.exportFormatValueList,item.fieldId);
			if(item.fieldId==17||item.fieldId==12){
				if(defaultFormatindex!=-1){
					$scope.exportFormatValueList.splice(defaultFormatindex,1);
				}
			}
		});
	};
	
	
	function arrayObjectIndexOf(myArray, searchTerm) {
	    for(var i = 0, len = myArray.length; i < len; i++) {
	        if (myArray[i].fieldId === searchTerm) return i;
	    }
	    return -1;
	}
	
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
		
		if($scope.exportDefaultValueListSelected.length>0){
			 angular.forEach($scope.exportDefaultValueListSelected,function(item,index){
				 var result = $.grep($scope.exportDefaultValueList, function(e){ return e.fieldId == item.fieldId;});
						if (result.length == 0) {
			 			 // not found
							var exportDefaultCopy=angular.copy(item);
							$scope.exportDefaultValueList.push(exportDefaultCopy);
						} else if (result.length == 1) {
							
			  			// access the foo property using result[0].foo
						}
			});
		 }else{
			 angular.forEach($scope.rawScreens[1],function(item,index){
				 if(item.fieldId==34){
					 var result = $.grep($scope.exportDefaultValueList, function(e){ return e.fieldId == item.fieldId;});
						if (result.length == 0) {
			 			 // not found
							var exportDefaultCopy=angular.copy(item);
							$scope.exportDefaultValueList.push(exportDefaultCopy);
						} else if (result.length == 1) {
							
			  			// access the foo property using result[0].foo
						}
				 }
			}); 
		 }
		 
		 if($scope.exportFormatValueListSelected.length>0){
			angular.forEach($scope.exportFormatValueListSelected,function(item,index){
				 var result = $.grep($scope.exportFormatValueList, function(e){ return e.fieldId == item.fieldId;});
				 
						if (result.length == 0) {
			 			 // not found
							var exportDefaultCopy=angular.copy(item);
							$scope.exportFormatValueList.push(exportDefaultCopy);
						} else if (result.length == 1) {
							
			  			// access the foo property using result[0].foo
						}
			});
		 }else{
			 angular.forEach($scope.rawScreens[1],function(item,index){
				 if(item.fieldId==17||item.fieldId==12){
				 	var result = $.grep($scope.exportFormatValueList, function(e){ return e.fieldId == item.fieldId;});
				 
						if (result.length == 0) {
			 			 // not found
							var exportFormatCopy=angular.copy(item);
							if(item.fieldId==17){
								exportFormatCopy.dropDownValue=[{"name":"999-999-9999","groupName":"Default","value":0},{"name":"+1-(999)-999-9999","groupName":"Format Type 1","value":1},{"name":"+1 (999) 999 9999","groupName":"Format Type 1","value":2},{"name":"+1(999)9999999","groupName":"Format Type 1","value":3},{"name":"+19999999999","groupName":"Format Type 1","value":4}
								,{"name":"1 (999) 999 9999","groupName":"Format Type 2","value":5},{"name":"1(999)9999999","groupName":"Format Type 2","value":6},{"name":"19999999999","groupName":"Format Type 2","value":7}
								,{"name":"(999)-999-9999","groupName":"Format Type 3","value":8},{"name":"(999) 999 9999","groupName":"Format Type 3","value":9},{"name":"(999)9999999","groupName":"Format Type 3","value":10},{"name":"9999999999","groupName":"Format Type 3","value":11}];
							}
							if(item.fieldId==12){
								exportFormatCopy.dropDownValue=[{"name":"LAST FIRST MIDDLE","groupName":"","value":0},{"name":"FIRST LAST","groupName":"","value":1},{"name":"FIRST MIDDLE LAST","groupName":"","value":2},{"name":"LAST FIRST","groupName":"","value":3}];
							}
							$scope.exportFormatValueList.push(exportFormatCopy);
						} else if (result.length == 1) {
							
			  			// access the foo property using result[0].foo
						}
				 }
			});
		 }
		 exportDefaultValueListOrginal=angular.copy($scope.exportDefaultValueList);
		 exportFormatValueListOrginal=angular.copy($scope.exportFormatValueList);
	 };
	
	 // Reset
	 $scope.resetAll=function(){
		 $scope.splitObjects();
		
		 if($scope.exportDefaultValueListSelected.length>0){
			 angular.forEach($scope.exportDefaultValueListSelected,function(item,index){
				 var result = $.grep($scope.exportDefaultValueList, function(e){ return e.fieldId == item.fieldId;});
						if (result.length == 0) {
			 			 // not found
							var exportDefaultCopy=angular.copy(item);
							$scope.exportDefaultValueList.push(exportDefaultCopy);
						} else if (result.length == 1) {
							
			  			// access the foo property using result[0].foo
						}
			});
		 }else{
			 $scope.exportDefaultValueList=[];
		 }
		 
		 if($scope.exportFormatValueListSelected.length>0){
			angular.forEach($scope.exportFormatValueListSelected,function(item,index){
				 var result = $.grep($scope.exportFormatValueList, function(e){ return e.fieldId == item.fieldId;});
				 
						if (result.length == 0) {
			 			 // not found
							var exportDefaultCopy=angular.copy(item);
							$scope.exportFormatValueList.push(exportDefaultCopy);
						} else if (result.length == 1) {
							
			  			// access the foo property using result[0].foo
						}
			});
		 }else{
			 $scope.exportFormatValueList=[]; 
		 }
		
		exportDefaultValueListOrginal=angular.copy($scope.exportDefaultValueList);
		exportFormatValueListOrginal=angular.copy($scope.exportFormatValueList);
	 };
	 
	 // Remove All
	 $scope.removeAll=function(){
		 angular.copy($scope.exportFieldsFormsOriginal,$scope.rawScreens[0]);
		 $scope.rawScreens[1]=[];
		 $scope.exportFormatValueList=[];
		 $scope.exportDefaultValueList=[];
		 $scope.exportFormatValueList=[];
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