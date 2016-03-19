package com.deemsys.project.LawyerCountyMap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.LawyerCountyMapping.LawyerCountyMappingForm;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.LawyerAdminCountyMap;
import com.deemsys.project.entity.LawyerAdminCountyMapId;
import com.deemsys.project.entity.LawyerCountyMap;
import com.deemsys.project.entity.County;

@Service
@Transactional
public class LawyerCountyMapService {

	@Autowired
	LawyerCountyMapDAO lawyerCountyMapDAO;
	
	//Get County Mapping By Lawyer Admin Id
		public List<LawyerCountyMapForm> getLawyerCountyMapByLawyerId(Integer lawyerAdminId){
			List<LawyerCountyMapForm> lawyerCountyMapForms= new ArrayList<LawyerCountyMapForm>();
			List<LawyerCountyMap> lawyerCountyMaps= new ArrayList<LawyerCountyMap>();
			
			lawyerCountyMaps=lawyerCountyMapDAO.getLawyerCountyMapByLawyerId(lawyerAdminId);
			
			for (LawyerCountyMap lawyerCountyMap : lawyerCountyMaps) {
				LawyerCountyMapForm lawyerCountyMapForm=new LawyerCountyMapForm(lawyerCountyMap.getId().getLawyerId(), lawyerCountyMap.getId().getCountyId(), InjuryConstants.convertMonthFormat(lawyerCountyMap.getSubscribedDate()),lawyerCountyMap.getStatus());
				lawyerCountyMapForms.add(lawyerCountyMapForm);
			}
			
			return lawyerCountyMapForms;
		}
		
	// Delete Unmapped County Id
		public void deleteLawyerCountyMap(List<Integer> county,Integer lawyerId){
			List<LawyerCountyMapForm> lawyerCountyMapForms=this.getLawyerCountyMapByLawyerId(lawyerId);
			
			for (LawyerCountyMapForm lawyerCountyMapForm : lawyerCountyMapForms) {
					if(county.contains(lawyerCountyMapForm.getCountyId()))
					{
						// Do Nothing
					}
					else{
						lawyerCountyMapDAO.deleteLawyerCountyMapByLawyerIdAndCountyId(lawyerCountyMapForm.getLawyerAdminId(), lawyerCountyMapForm.getCountyId());
					}
				}
		}
	
	// get Newely Added County
		public List<Integer> getNewlyAddedCountyId(List<Integer> county,Integer lawyerId)
		{
			List<Integer> newCountyId =new ArrayList<Integer>();
			List<LawyerCountyMapForm> lawyerCountyMapForms=this.getLawyerCountyMapByLawyerId(lawyerId);
			// 
			List<Integer> insertedCounty=new ArrayList<Integer>();
			for (LawyerCountyMapForm lawyerCountyMapForm : lawyerCountyMapForms) {
				insertedCounty.add(lawyerCountyMapForm.getCountyId());
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
