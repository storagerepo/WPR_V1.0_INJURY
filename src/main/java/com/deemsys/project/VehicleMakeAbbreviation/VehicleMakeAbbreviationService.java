package com.deemsys.project.VehicleMakeAbbreviation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.VehicleMakeAbbreviation;
/**
 * 
 * @author Deemsys
 *
 * VehicleMakeAbbreviation 	 - Entity
 * vehicleMakeAbbreviation 	 - Entity Object
 * vehicleMakeAbbreviations 	 - Entity List
 * vehicleMakeAbbreviationDAO   - Entity DAO
 * vehicleMakeAbbreviationForms - EntityForm List
 * VehicleMakeAbbreviationForm  - EntityForm
 *
 */
@Service
@Transactional
public class VehicleMakeAbbreviationService {

	@Autowired
	VehicleMakeAbbreviationDAO vehicleMakeAbbreviationDAO;
	
	//Get All Entries
	public List<VehicleMakeAbbreviationForm> getVehicleMakeAbbreviationList()
	{
		List<VehicleMakeAbbreviationForm> vehicleMakeAbbreviationForms=new ArrayList<VehicleMakeAbbreviationForm>();
		return vehicleMakeAbbreviationForms;
	}
	
	//Get Particular Entry
	public VehicleMakeAbbreviationForm getVehicleMakeAbbreviation(Integer getId)
	{
		//TODO: Convert Entity to Form
		//Start
		VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm=new VehicleMakeAbbreviationForm();
		//End
		return vehicleMakeAbbreviationForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeVehicleMakeAbbreviation(VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		VehicleMakeAbbreviation vehicleMakeAbbreviation=new VehicleMakeAbbreviation();
		
		//Logic Ends
		
		
		vehicleMakeAbbreviationDAO.merge(vehicleMakeAbbreviation);
		return 1;
	}
	
	//Save an Entry
	public int saveVehicleMakeAbbreviation(VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		VehicleMakeAbbreviation vehicleMakeAbbreviation=new VehicleMakeAbbreviation();
		
		//Logic Ends
		
		vehicleMakeAbbreviationDAO.save(vehicleMakeAbbreviation);
		return 1;
	}
	
	//Update an Entry
	public int updateVehicleMakeAbbreviation(VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		VehicleMakeAbbreviation vehicleMakeAbbreviation=new VehicleMakeAbbreviation();
		
		//Logic Ends
		
		vehicleMakeAbbreviationDAO.update(vehicleMakeAbbreviation);
		return 1;
	}
	
	//Delete an Entry
	public int deleteVehicleMakeAbbreviation(Integer id)
	{
		vehicleMakeAbbreviationDAO.delete(id);
		return 1;
	}
	
	
	
}
