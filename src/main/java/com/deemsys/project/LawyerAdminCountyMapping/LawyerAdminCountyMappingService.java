package com.deemsys.project.LawyerAdminCountyMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.UserLookupPreferences.UserLookupPreferencesDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.LawyerAdminCountyMap;
import com.deemsys.project.entity.LawyerAdminCountyMapId;
@Service
@Transactional
public class LawyerAdminCountyMappingService {

	@Autowired
	LawyerAdminCountyMappingDAO lawyerAdminCountyMappingDAO;
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	UserLookupPreferencesDAO userLookupPreferencesDAO;
	
	//Get County Mapping By Lawyer Id
		public List<LawyerAdminCountyMappingForm> getLawyerAdminCountyMappingByLawyerAdminId(Integer lawyerAdminId){
			List<LawyerAdminCountyMappingForm> lawyerAdminCountyMappingForms= new ArrayList<LawyerAdminCountyMappingForm>();
			List<LawyerAdminCountyMap> lawyerAdminCountyMappings= new ArrayList<LawyerAdminCountyMap>();
			
			lawyerAdminCountyMappings=lawyerAdminCountyMappingDAO.getLawyerAdminCountyMappingsByLawyerAdminId(lawyerAdminId);
			
			for (LawyerAdminCountyMap lawyerAdminCountyMapping : lawyerAdminCountyMappings) {
				LawyerAdminCountyMappingForm lawyerAdminCountyMappingForm=new LawyerAdminCountyMappingForm(lawyerAdminCountyMapping.getId().getLawyerAdminId(), lawyerAdminCountyMapping.getId().getCountyId(), InjuryConstants.convertMonthFormat(lawyerAdminCountyMapping.getSubscribedDate()), lawyerAdminCountyMapping.getStatus());
				lawyerAdminCountyMappingForms.add(lawyerAdminCountyMappingForm);
			}
			
			return lawyerAdminCountyMappingForms;
		}
		
		// Save County Map
		public void saveLawyerAdminCountyMap(Integer countyId,LawyerAdmin lawyerAdmin){
			County county = countyDAO.get(countyId);
			LawyerAdminCountyMapId lawyerAdminCountyMapId=new LawyerAdminCountyMapId(lawyerAdmin.getLawyerAdminId(), countyId);
			LawyerAdminCountyMap lawyerAdminCountyMapping = new LawyerAdminCountyMap(lawyerAdminCountyMapId,lawyerAdmin, county,new Date(),1);
			lawyerAdminCountyMappingDAO.save(lawyerAdminCountyMapping);
		}
		// Delete Lawyer County Mapping
		public void deleteLawyerAdminCountyMapping(Integer lawyerAdminId){
			List<LawyerAdminCountyMappingForm> lawyerAdminCountyMappingForms=this.getLawyerAdminCountyMappingByLawyerAdminId(lawyerAdminId);
			
			for (LawyerAdminCountyMappingForm lawyerAdminCountyMappingForm : lawyerAdminCountyMappingForms) {
				
						lawyerAdminCountyMappingDAO.deleteLawyerAdminCountyMappingsByLawyerAdminId(lawyerAdminCountyMappingForm.getLawyerAdminId());
					
			}
		}
		
		
		public void deleteLawyerAdminCountyMapping(List<Integer> countyId,Integer lawyerAdminId, Integer userId){
			List<LawyerAdminCountyMappingForm> lawyerAdminCountyMappingForms=this.getLawyerAdminCountyMappingByLawyerAdminId(lawyerAdminId);
			
			for (LawyerAdminCountyMappingForm lawyerAdminCountyMappingForm : lawyerAdminCountyMappingForms) {
				
					if(countyId.contains(lawyerAdminCountyMappingForm.getCountyId()))
					{
						// Do Nothing
					}
					else{
						lawyerAdminCountyMappingDAO.deleteLawyerAdminCountyMappingsByLawyerAdminIdAndCountyId(lawyerAdminCountyMappingForm.getLawyerAdminId(), lawyerAdminCountyMappingForm.getCountyId());
						userLookupPreferencesDAO.deleteUserLookupPreferenceByUserAndPPreferedId(userId, 1, lawyerAdminCountyMappingForm.getCountyId());
					}
				
			}
		}
		
		
		public List<Integer> getNewlyAddedCountyId(List<Integer> countyId,Integer lawyerAdminId)
		{
			List<Integer> newCountyId =new ArrayList<Integer>();
			List<LawyerAdminCountyMappingForm> lawyerAdminCountyMappingForms=this.getLawyerAdminCountyMappingByLawyerAdminId(lawyerAdminId);
			List<Integer> insertedcounty=new ArrayList<Integer>();
			for (LawyerAdminCountyMappingForm lawyerAdminCountyMappingForm : lawyerAdminCountyMappingForms) {
				
				insertedcounty.add(lawyerAdminCountyMappingForm.getCountyId());
			}
			
			// New County list
				for (Integer county : countyId) {
					
					if(insertedcounty.contains(county))
					{
						// Do Nothing
					}
					else{
						newCountyId.add(county);
					}
				}
			
				return newCountyId;
		}
		
		// Delete County Map by ladmin Id and county id
		public void deleteLawyerAdminCountyMapByCountyAndLAdminId(Integer countyId,Integer lawyerAdminId){
			lawyerAdminCountyMappingDAO.deleteLawyerAdminCountyMappingsByLawyerAdminIdAndCountyId(lawyerAdminId, countyId);
		}
		
		// Delete County Map by ladmin Id
		public void deleteLawyerAdminCountyMapByLAdminId(Integer lawyerAdminId){
			lawyerAdminCountyMappingDAO.deleteLawyerAdminCountyMappingsByLawyerAdminId(lawyerAdminId);
		}
}
