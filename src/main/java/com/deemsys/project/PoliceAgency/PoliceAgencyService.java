package com.deemsys.project.PoliceAgency;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.PoliceAgency;
/**
 * 
 * @author Deemsys
 *
 * PoliceAgency 	 - Entity
 * policeAgency 	 - Entity Object
 * policeAgencys 	 - Entity List
 * policeAgencyDAO   - Entity DAO
 * policeAgencyForms - EntityForm List
 * PoliceAgencyForm  - EntityForm
 *
 */
@Service
@Transactional
public class PoliceAgencyService {

	@Autowired
	PoliceAgencyDAO policeAgencyDAO;
	
	//Get All Entries
	public List<PoliceAgencyForm> getPoliceAgencyList()
	{
		List<PoliceAgencyForm> policeAgencyForms=new ArrayList<PoliceAgencyForm>();
		
		List<PoliceAgency> policeAgencys=new ArrayList<PoliceAgency>();
		
		policeAgencys=policeAgencyDAO.getAll();
		
		for (PoliceAgency policeAgency : policeAgencys) {
			PoliceAgencyForm agencyForm=new PoliceAgencyForm(policeAgency.getId(), policeAgency.getCounty().getCountyId(), policeAgency.getAgencyId(), policeAgency.getName(), policeAgency.getMapId(), policeAgency.getStatus());
			policeAgencyForms.add(agencyForm);
		}
		
		return policeAgencyForms;
	}
	
	//Get Particular Entry
	public PoliceAgencyForm getPoliceAgency(Integer getId)
	{
		PoliceAgency policeAgency=new PoliceAgency();
		
		policeAgency=policeAgencyDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		
		PoliceAgencyForm policeAgencyForm=new PoliceAgencyForm();
		
		//End
		
		return policeAgencyForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergePoliceAgency(PoliceAgencyForm policeAgencyForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		PoliceAgency policeAgency=new PoliceAgency();
		
		//Logic Ends
		
		
		policeAgencyDAO.merge(policeAgency);
		return 1;
	}
	
	//Save an Entry
	public int savePoliceAgency(PoliceAgencyForm policeAgencyForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		PoliceAgency policeAgency=new PoliceAgency();
		
		//Logic Ends
		
		policeAgencyDAO.save(policeAgency);
		return 1;
	}
	
	//Update an Entry
	public int updatePoliceAgency(PoliceAgencyForm policeAgencyForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		PoliceAgency policeAgency=new PoliceAgency();
		
		//Logic Ends
		
		policeAgencyDAO.update(policeAgency);
		return 1;
	}
	
	//Delete an Entry
	public int deletePoliceAgency(Integer id)
	{
		policeAgencyDAO.delete(id);
		return 1;
	}
	
	// Get Scheduler Police Agencies
	public List<PoliceAgencyForm> getPoliceAgenciesByStatus(Integer status){
		List<PoliceAgencyForm> policeAgencyForms = new ArrayList<PoliceAgencyForm>();
		List<PoliceAgency> policeAgencies = policeAgencyDAO.getPoliceAgenciesBystatus(status);
		for (PoliceAgency policeAgency : policeAgencies) {
			PoliceAgencyForm agencyForm=new PoliceAgencyForm(policeAgency.getId(), policeAgency.getCounty().getCountyId(), policeAgency.getAgencyId(), policeAgency.getName(), policeAgency.getMapId(), policeAgency.getStatus());
			policeAgencyForms.add(agencyForm);
		}
		
		return policeAgencyForms;
	}
	
}
