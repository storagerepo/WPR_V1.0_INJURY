package com.deemsys.project.VehicleMakeAbbreviation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.entity.VehicleMakeAbbreviation;
import com.deemsys.project.patient.PatientDAO;
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
	
	@Autowired 
	PatientDAO patientDAO;
	//Get All Entries
	public List<VehicleMakeAbbreviationForm> getVehicleMakeAbbreviationList()
	{
		List<VehicleMakeAbbreviationForm> vehicleMakeAbbreviationForms=new ArrayList<VehicleMakeAbbreviationForm>();
		List<VehicleMakeAbbreviation> vehicleMakeAbbreviations=new ArrayList<VehicleMakeAbbreviation>();
		vehicleMakeAbbreviations=vehicleMakeAbbreviationDAO.getAll();
		for(VehicleMakeAbbreviation vehicleMakeAbbreviation:vehicleMakeAbbreviations)
		{
			 VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm=new VehicleMakeAbbreviationForm(vehicleMakeAbbreviation.getMake(), vehicleMakeAbbreviation.getAbbreviation(), vehicleMakeAbbreviation.getStatus());
		vehicleMakeAbbreviationForms.add(vehicleMakeAbbreviationForm);
		}
		
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
		VehicleMakeAbbreviation vehicleMakeAbbreviation=new VehicleMakeAbbreviation();
		vehicleMakeAbbreviation=new VehicleMakeAbbreviation(vehicleMakeAbbreviationForm.getMake(), vehicleMakeAbbreviationForm.getAbbreviation(), vehicleMakeAbbreviationForm.getStatus(), null);
	
		vehicleMakeAbbreviationDAO.merge(vehicleMakeAbbreviation);
		return 1;
	}
	
	//Save an Entry
	public int saveVehicleMakeAbbreviation(VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm)
	{
		
		
		VehicleMakeAbbreviation vehicleMakeAbbreviation=new VehicleMakeAbbreviation();
		
		vehicleMakeAbbreviation=new VehicleMakeAbbreviation(vehicleMakeAbbreviationForm.getMake(), vehicleMakeAbbreviationForm.getAbbreviation(), 1, null);
		vehicleMakeAbbreviationDAO.save(vehicleMakeAbbreviation);
		return 1;
	}
	
	//Update an Entry
	public int updateVehicleMakeAbbreviation(VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm)
	{
		
		
		VehicleMakeAbbreviation vehicleMakeAbbreviation=new VehicleMakeAbbreviation();
		
		vehicleMakeAbbreviation=new VehicleMakeAbbreviation(vehicleMakeAbbreviationForm.getMake(), vehicleMakeAbbreviationForm.getAbbreviation(), vehicleMakeAbbreviationForm.getStatus(),null);
		
		vehicleMakeAbbreviationDAO.update(vehicleMakeAbbreviation);
		return 1;
	}
	
	//Delete an Entry
	public int deleteVehicleMakeAbbreviation(Integer id)
	{
		vehicleMakeAbbreviationDAO.delete(id);
		return 1;
	}
	
	public VehicleMakeAbbreviationList getVehicleMakeAbbrevationsBySearch(SearchVehicleMakeAbbrevationForm searchVehicleMakeAbbrevationForm)
	{
		return vehicleMakeAbbreviationDAO.getVehicleMakeAbbrevationsBySearch(searchVehicleMakeAbbrevationForm);
	}
	
	public VehicleMakeAbbreviationForm getVehicleMakeAbbreviationByMake(String make)
	{
		VehicleMakeAbbreviation vehicleMakeAbbreviation=vehicleMakeAbbreviationDAO.getVehicleMakeAbbreviationByMake(make);
		VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm=new VehicleMakeAbbreviationForm(vehicleMakeAbbreviation.getMake(), vehicleMakeAbbreviation.getAbbreviation(), vehicleMakeAbbreviation.getStatus());
		return vehicleMakeAbbreviationForm;
	}
	
	public Integer checkVehicleMakeAbbreviationByMake(String make)
	{
		VehicleMakeAbbreviation vehicleMakeAbbreviation=vehicleMakeAbbreviationDAO.getVehicleMakeAbbreviationByMake(make);
		if(vehicleMakeAbbreviation!=null)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	public Integer deleteVehicleMakeAbbreviationByMake(String make)
	{
	 Integer isMappedWithPatient=patientDAO.checkVehicleMakeMappedToPatients(make);
	 if(isMappedWithPatient==1)
	 {
		 return 1;
	 }
	 else
	 {
		 vehicleMakeAbbreviationDAO.deleteVehicleMakeAbbreviationByMake(make);
		 return 0;
	 }
	}
	
}
