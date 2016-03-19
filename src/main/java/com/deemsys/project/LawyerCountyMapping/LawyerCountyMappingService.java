package com.deemsys.project.LawyerCountyMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.LawyerCountyMap;

@Service
@Transactional
public class LawyerCountyMappingService {

	@Autowired
	LawyerCountyMappingDAO lawyerCountyMappingDAO;
	
	//Get County Mapping By Lawyer Id
	public List<LawyerCountyMappingForm> getLawyerCountyMappingByLaweyerId(Integer lawyerId){
		List<LawyerCountyMappingForm> lawyerCountyMappingForms= new ArrayList<LawyerCountyMappingForm>();
		List<LawyerCountyMap> lawyerCountyMappings= new ArrayList<LawyerCountyMap>();
		
		lawyerCountyMappings=lawyerCountyMappingDAO.getLawyerCountyMappingsByLawyerId(lawyerId);
		
		for (LawyerCountyMap lawyerCountyMapping : lawyerCountyMappings) {
			LawyerCountyMappingForm lawyerCountyMappingForm=new LawyerCountyMappingForm(lawyerCountyMapping.getId().getLawyerId(), lawyerCountyMapping.getId().getCountyId(), lawyerCountyMapping.getStatus());
			lawyerCountyMappingForms.add(lawyerCountyMappingForm);
		}
		
		return lawyerCountyMappingForms;
	}
	
	// Delete Lawyer County Mapping
	public void deleteLawyerCountyMapping(List<Integer> countyId,Integer lawyerId){
		List<LawyerCountyMappingForm> lawyerCountyMappingForms=this.getLawyerCountyMappingByLaweyerId(lawyerId);
		
		for (LawyerCountyMappingForm lawyerCountyMappingForm : lawyerCountyMappingForms) {
				if(countyId.contains(lawyerCountyMappingForm.getCountyId()))
				{
					// Do Nothing
				}
				else{
					lawyerCountyMappingDAO.deleteLawyerCountyMappingsByLawyerIdAndCountyId(lawyerCountyMappingForm.getLawyerId(), lawyerCountyMappingForm.getCountyId());
				}
			}
		}
	
	// Return a New County Ids
	public List<Integer> getNewlyAddedCountyId(List<Integer> county,Integer lawyerId)
	{
		List<Integer> newCountyId =new ArrayList<Integer>();
		List<LawyerCountyMappingForm> lawyerCountyMappingForms=this.getLawyerCountyMappingByLaweyerId(lawyerId);
		// Inserted County
		List<Integer> insertedCounty=new ArrayList<Integer>();
		for (LawyerCountyMappingForm lawyerCountyMappingForm : lawyerCountyMappingForms) {
			insertedCounty.add(lawyerCountyMappingForm.getCountyId());
		}
		
		// New County list
		for (Integer countyId : county) {
			if(insertedCounty.contains(countyId)){
				// Do Nothing
			}
			else{
				newCountyId.add(countyId);
			}
		}
			
			return newCountyId;
	}
}
