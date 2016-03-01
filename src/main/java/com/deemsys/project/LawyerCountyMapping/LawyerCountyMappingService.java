package com.deemsys.project.LawyerCountyMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.LawyerCountyMapping;

@Service
@Transactional
public class LawyerCountyMappingService {

	@Autowired
	LawyerCountyMappingDAO lawyerCountyMappingDAO;
	
	//Get County Mapping By Lawyer Id
	public List<LawyerCountyMappingForm> getLawyerCountyMappingByLaweyerId(Integer lawyerId){
		List<LawyerCountyMappingForm> lawyerCountyMappingForms= new ArrayList<LawyerCountyMappingForm>();
		List<LawyerCountyMapping> lawyerCountyMappings= new ArrayList<LawyerCountyMapping>();
		
		lawyerCountyMappings=lawyerCountyMappingDAO.getLaweCountyMappingsByLawyerId(lawyerId);
		
		for (LawyerCountyMapping lawyerCountyMapping : lawyerCountyMappings) {
			LawyerCountyMappingForm lawyerCountyMappingForm=new LawyerCountyMappingForm(lawyerCountyMapping.getMappingId(), lawyerCountyMapping.getLawyers().getId(), lawyerCountyMapping.getCounty().getId());
			lawyerCountyMappingForms.add(lawyerCountyMappingForm);
		}
		
		return lawyerCountyMappingForms;
	}
	
	// Delete Lawyer County Mapping
	public void deleteLawyerCountyMapping(Integer[] countyId,Integer lawyerId){
		List<LawyerCountyMappingForm> lawyerCountyMappingForms=this.getLawyerCountyMappingByLaweyerId(lawyerId);
		
		for (LawyerCountyMappingForm lawyerCountyMappingForm : lawyerCountyMappingForms) {
			for(int i=0;i<countyId.length;i++){
				if(lawyerCountyMappingForm.getCountyId().equals(countyId[i]))
				{
					// Do Nothing
				}
				else{
					lawyerCountyMappingDAO.delete(lawyerCountyMappingForm.getMappingId());
				}
			}
		}
	}
	
	// Return a New County Ids
	public List<Integer> getNewlyAddedCountyId(Integer[] countyId,Integer lawyerId)
	{
		List<Integer> newCountyId =new ArrayList<Integer>();
		List<LawyerCountyMappingForm> lawyerCountyMappingForms=this.getLawyerCountyMappingByLaweyerId(lawyerId);
		// New County list
			for(int i=0;i<countyId.length;i++){
				if(lawyerCountyMappingForms.size()>0){
				for (LawyerCountyMappingForm lawyerCountyMappingForm : lawyerCountyMappingForms) {
						if(lawyerCountyMappingForm.getCountyId().equals(countyId[i]))
						{
							// Do Nothing
						}
						else{
							newCountyId.add(countyId[i]);
						}
					}
				}
				else{
					newCountyId.add(countyId[i]);
				}
			}
			
			return newCountyId;
	}
}
