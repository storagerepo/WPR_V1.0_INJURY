package com.deemsys.project.CallerAdminCountyMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.LawyerCountyMapping.LawyerCountyMappingForm;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdminCountyMap;
import com.deemsys.project.entity.CallerAdminCountyMapId;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.LawyerCountyMap;

@Service
@Transactional
public class CallerAdminCountyMapService {

	@Autowired
	CallerAdminCountyMapDAO callerAdminCountyMapDAO;
	
	//Get County Mapping By Caller Admin Id
		public List<CallerAdminCountyMapForm> getCallerAdminCountyMapByCallerAdminId(Integer callerAdminId){
			List<CallerAdminCountyMapForm> callerAdminCountyMapForms= new ArrayList<CallerAdminCountyMapForm>();
			List<CallerAdminCountyMap> callerAdminCountyMaps= new ArrayList<CallerAdminCountyMap>();
			
			callerAdminCountyMaps=callerAdminCountyMapDAO.getCallerAdminCountyMapByCallerAdminId(callerAdminId);
			
			for (CallerAdminCountyMap callerAdminCountyMap : callerAdminCountyMaps) {
				CallerAdminCountyMapForm callerAdminCountyMapForm=new CallerAdminCountyMapForm(callerAdminCountyMap.getId().getCallerAdminId(), callerAdminCountyMap.getId().getCountyId(), InjuryConstants.convertMonthFormat(callerAdminCountyMap.getSubscribedDate()),callerAdminCountyMap.getStatus());
				callerAdminCountyMapForms.add(callerAdminCountyMapForm);
			}
			
			return callerAdminCountyMapForms;
		}
		
	// Delete Unmapped County Id
		public void deleteCallerAdminCountyMap(List<Integer> county,Integer callerAdminId){
			List<CallerAdminCountyMapForm> callerAdminCountyMapForms=this.getCallerAdminCountyMapByCallerAdminId(callerAdminId);
			
			for (CallerAdminCountyMapForm callerAdminCountyMapForm : callerAdminCountyMapForms) {
					if(county.contains(callerAdminCountyMapForm.getCountyId()))
					{
						// Do Nothing
					}
					else{
						callerAdminCountyMapDAO.deleteCallerAdminCountyMapByCallerAdminIdAndCountyId(callerAdminCountyMapForm.getCallerAdminId(), callerAdminCountyMapForm.getCountyId());
					}
				}
		}
	
	// get Newely Added County
		public List<Integer> getNewlyAddedCountyId(List<Integer> county,Integer callerAdminId)
		{
			List<Integer> newCountyId =new ArrayList<Integer>();
			List<CallerAdminCountyMapForm> callerAdminCountyMapForms=this.getCallerAdminCountyMapByCallerAdminId(callerAdminId);
			// 
			List<Integer> insertedCounty=new ArrayList<Integer>();
			for (CallerAdminCountyMapForm callerAdminCountyMapForm : callerAdminCountyMapForms) {
				insertedCounty.add(callerAdminCountyMapForm.getCountyId());
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
