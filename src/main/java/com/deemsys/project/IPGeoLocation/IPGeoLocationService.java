package com.deemsys.project.IPGeoLocation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.IpGeoLocation;
/**
 * 
 * @author Deemsys
 *
 * IpGeoLocation 	 - Entity
 * ipGeoLocation 	 - Entity Object
 * ipGeoLocations 	 - Entity List
 * ipGeoLocationDAO   - Entity DAO
 * ipGeoLocationForms - EntityForm List
 * IpGeoLocationForm  - EntityForm
 *
 */
@Service
@Transactional
public class IPGeoLocationService {

	@Autowired
	IPGeoLocationDAO ipGeoLocationDAO;
	
	//Get All Entries
	public List<IPGeoLocationForm> getIpGeoLocationList()
	{
		List<IPGeoLocationForm> ipGeoLocationForms=new ArrayList<IPGeoLocationForm>();
		
		List<IpGeoLocation> ipGeoLocations=new ArrayList<IpGeoLocation>();
		
		ipGeoLocations=ipGeoLocationDAO.getAll();
		
		for (IpGeoLocation ipGeoLocation : ipGeoLocations) {
			//TODO: Fill the List
			
		}
		
		return ipGeoLocationForms;
	}
	
	//Get Particular Entry
	public IPGeoLocationForm getIpGeoLocation(String ipAddress)
	{
		IpGeoLocation ipGeoLocation=new IpGeoLocation();
		ipGeoLocation=ipGeoLocationDAO.getIPGeoLocationByIP(ipAddress);
		//TODO: Convert Entity to Form
		//Start
		IPGeoLocationForm ipGeoLocationForm=new IPGeoLocationForm(ipGeoLocation.getIpAddress(), ipGeoLocation.getCity(), 
				ipGeoLocation.getRegion(), ipGeoLocation.getCountryName(), ipGeoLocation.getContinentCode(), ipGeoLocation.getContinentName(), 
				ipGeoLocation.getContinentCode(), ipGeoLocation.getPostal(), ipGeoLocation.getLatitude(), ipGeoLocation.getLongitude(), 
				ipGeoLocation.getTimeZone(), ipGeoLocation.getFlag(), ipGeoLocation.getStatus());
		
		//End
		
		return ipGeoLocationForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeIpGeoLocation(IPGeoLocationForm ipGeoLocationForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		IpGeoLocation ipGeoLocation=new IpGeoLocation();
		
		//Logic Ends
		
		
		ipGeoLocationDAO.merge(ipGeoLocation);
		return 1;
	}
	
	//Save an Entry
	public int saveIpGeoLocation(IPGeoLocationForm ipGeoLocationForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		IpGeoLocation ipGeoLocation=new IpGeoLocation();
		
		//Logic Ends
		
		ipGeoLocationDAO.save(ipGeoLocation);
		return 1;
	}
	
	//Update an Entry
	public int updateIpGeoLocation(IPGeoLocationForm ipGeoLocationForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		IpGeoLocation ipGeoLocation=new IpGeoLocation();
		
		//Logic Ends
		
		ipGeoLocationDAO.update(ipGeoLocation);
		return 1;
	}
	
	//Delete an Entry
	public int deleteIpGeoLocation(Integer id)
	{
		ipGeoLocationDAO.delete(id);
		return 1;
	}
	
	// Check is Already Available
	public boolean checkAlreadyAvailableOrNot(String ipAddress)
	{
		boolean isExist=false;
		IpGeoLocation ipGeoLocation=ipGeoLocationDAO.getIPGeoLocationByIP(ipAddress);
		//TODO: Convert Entity to Form
		//Start
		if(ipGeoLocation!=null){
			isExist=true;
		}
		//End
		
		return isExist;
	}
	
	
}
