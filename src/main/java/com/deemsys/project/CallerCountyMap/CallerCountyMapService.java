package com.deemsys.project.CallerCountyMap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerCountyMap;

@Service
@Transactional
public class CallerCountyMapService {

	@Autowired
	CallerCountyMapDAO callerCountyMapDAO;
	
	//Get County Mapping By Caller Admin Id
		public List<CallerCountyMapForm> getCallerCountyMapByCallerId(Integer callerAdminId){
			List<CallerCountyMapForm> callerCountyMapForms= new ArrayList<CallerCountyMapForm>();
			List<CallerCountyMap> callerCountyMaps= new ArrayList<CallerCountyMap>();
			
			callerCountyMaps=callerCountyMapDAO.getCallerCountyMapByCallerId(callerAdminId);
			
			for (CallerCountyMap callerCountyMap : callerCountyMaps) {
				CallerCountyMapForm callerCountyMapForm=new CallerCountyMapForm(callerCountyMap.getId().getCallerId(), callerCountyMap.getId().getCountyId(), InjuryConstants.convertMonthFormat(callerCountyMap.getSubscribedDate()),callerCountyMap.getStatus());
				callerCountyMapForms.add(callerCountyMapForm);
			}
			
			return callerCountyMapForms;
		}
		
	// Delete Unmapped County Id
		public void deleteCallerCountyMap(List<Integer> county,Integer callerId){
			List<CallerCountyMapForm> callerCountyMapForms=this.getCallerCountyMapByCallerId(callerId);
			
			for (CallerCountyMapForm callerCountyMapForm : callerCountyMapForms) {
					if(county.contains(callerCountyMapForm.getCountyId()))
					{
						// Do Nothing
					}
					else{
						callerCountyMapDAO.deleteCallerCountyMapByCallerIdAndCountyId(callerCountyMapForm.getCallerAdminId(), callerCountyMapForm.getCountyId());
					}
				}
		}
	
	// get Newely Added County
		public List<Integer> getNewlyAddedCountyId(List<Integer> county,Integer callerId)
		{
			List<Integer> newCountyId =new ArrayList<Integer>();
			List<CallerCountyMapForm> callerCountyMapForms=this.getCallerCountyMapByCallerId(callerId);
			// 
			List<Integer> insertedCounty=new ArrayList<Integer>();
			for (CallerCountyMapForm callerCountyMapForm : callerCountyMapForms) {
				insertedCounty.add(callerCountyMapForm.getCountyId());
			}
			
			// New County list
			for(Integer countyId:county){
					if(insertedCounty.contains(countyId)){
						// Do not add in list
					}else{
						newCountyId.add(countyId);
					}
			}
				
				return newCountyId;
		}
		
		
}
