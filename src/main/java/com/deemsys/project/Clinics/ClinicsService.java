package com.deemsys.project.Clinics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.ClinicTimings.ClinicTimingList;
import com.deemsys.project.ClinicTimings.ClinicTimingsDAO;
import com.deemsys.project.Doctors.DoctorsDAO;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.entity.ClinicTimings;
import com.deemsys.project.entity.ClinicTimingsId;
import com.deemsys.project.entity.Clinics;
/**
 * @author Deemsys
 *
 */
@Service
@Transactional
public class ClinicsService {

	@Autowired
	ClinicsDAO clinicsDAO;
	
	@Autowired
	ClinicTimingsDAO clinicTimingsDAO;
	
	@Autowired
	DoctorsDAO doctorsDAO;
	
	@Autowired
	GeoLocation geoLocation;
	
	/** Get Clinic Details
	 * @param clinicId
	 * @return
	 */
	public ClinicsForm getClinic(Integer clinicId){
		
		
		Clinics clinics=clinicsDAO.get(clinicId);
		List<ClinicTimings> clinicTimings=clinicTimingsDAO.getClinicTimings(clinicId);
		
		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		// Set ClinicTiming List
		for (ClinicTimings clinicTimingss: clinicTimings) {
		ClinicTimingList clinicTimingList=new ClinicTimingList(clinicTimingss.getId().getDay(), clinicTimingss.getId().getClinicId(), clinicTimingss.getStartTime(), clinicTimingss.getEndTime(), clinicTimingss.getIsWorkingDay());
		clinicTimingLists.add(clinicTimingList);
		}
		// Set Clinic
		ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(), clinics.getClinicName(), clinics.getAddress(), clinics.getCity(), clinics.getState(), clinics.getCounty(), 
				clinics.getCountry(), clinics.getZipcode(), clinics.getOfficeNumber(), clinics.getFaxNumber(), clinics.getDirections(), clinics.getNotes(), clinicTimingLists);
		
		
		return clinicsForm;
	}
	
	/**
	 * Get All Clinics
	 * @return
	 */
	public List<ClinicsForm> getClinicsList(){
		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();
		List<Clinics> clinicses=new ArrayList<Clinics>();
		clinicses=clinicsDAO.getAll();
		
		for (Clinics clinics : clinicses) {
			ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(), clinics.getClinicName(), clinics.getAddress(), clinics.getCity(), clinics.getState(), clinics.getCounty(), 
					clinics.getCountry(), clinics.getZipcode(), clinics.getOfficeNumber(), clinics.getFaxNumber(), clinics.getDirections(), clinics.getNotes(), null);
			clinicsForms.add(clinicsForm);
		}
		
		return clinicsForms;
	}
	
	
	/**Save Clinic
	 * @param clinicsForm
	 * @return
	 */
	public Integer saveClinic(ClinicsForm clinicsForm){
		Integer status=0;
		
		// Get Lat Lang of Address
		String latLong=geoLocation.getLocation(this.getAddressForGeoCode(clinicsForm));
		System.out.println("CONVERT ADDRESS--"+this.getAddressForGeoCode(clinicsForm));
		String[] latiudeLongitude=latLong.split(",");
		
		// Save Clinic
		Clinics clinics=new Clinics(clinicsForm.getClinicName(), clinicsForm.getAddress(), clinicsForm.getCity(), clinicsForm.getState(), clinicsForm.getCounty(), 
									clinicsForm.getCountry(), clinicsForm.getZipcode(), Double.parseDouble(latiudeLongitude[0]), Double.parseDouble(latiudeLongitude[1]), clinicsForm.getOfficeNumber(), clinicsForm.getFaxNumber(), clinicsForm.getDirections(), clinicsForm.getNotes(), null, null);
		clinicsDAO.save(clinics);
		
		// Save Clinic Timings
		List<ClinicTimingList> clinicTimingList=clinicsForm.getClinicTimingList();
		for (ClinicTimingList clinicTimingList2 : clinicTimingList) {
			ClinicTimingsId clinicTimingsId=new ClinicTimingsId(clinics.getClinicId(), clinicTimingList2.getDay());
			ClinicTimings clinicTimings=new ClinicTimings(clinicTimingsId, clinics, clinicTimingList2.getStartTime(), clinicTimingList2.getEndTime(), clinicTimingList2.getIsWorkingDay());
			clinicTimingsDAO.save(clinicTimings);
		}
		
		return status;
	}
	
	/**Update Clinic
	 * @param clinicsForm
	 * @return
	 */
	public Integer updateClinic(ClinicsForm clinicsForm){
		Integer status=0;
		
		// Get Lat Long For Address
		String latLong=geoLocation.getLocation(this.getAddressForGeoCode(clinicsForm));
		String[] latiudeLongitude=latLong.split(",");
	
		// Update Clinics
		Clinics clinics=new Clinics(clinicsForm.getClinicName(), clinicsForm.getAddress(), clinicsForm.getCity(), clinicsForm.getState(), clinicsForm.getCounty(), 
									clinicsForm.getCountry(), clinicsForm.getZipcode(), Double.parseDouble(latiudeLongitude[0]), Double.parseDouble(latiudeLongitude[1]), clinicsForm.getOfficeNumber(), clinicsForm.getFaxNumber(), clinicsForm.getDirections(), clinicsForm.getNotes(), null, null);
		clinics.setClinicId(clinicsForm.getClinicId());
		clinicsDAO.update(clinics);
		
		// Update Clinic Timings
		List<ClinicTimingList> clinicTimingList=clinicsForm.getClinicTimingList();
		for (ClinicTimingList clinicTimingList2 : clinicTimingList) {
			ClinicTimingsId clinicTimingsId=new ClinicTimingsId(clinicTimingList2.getClinicId(), clinicTimingList2.getDay());
			ClinicTimings clinicTimings=new ClinicTimings(clinicTimingsId, clinics, clinicTimingList2.getStartTime(), clinicTimingList2.getEndTime(), clinicTimingList2.getIsWorkingDay());
			clinicTimingsDAO.update(clinicTimings);
		}
		return status;
	}
	
	/** Delete a Clinic
	 * @param clinicId
	 * @return
	 */
	public Integer deleteClinic(Integer clinicId){
		Integer status=0;
		
		Integer size=doctorsDAO.getDoctorsSizeByClinicId(clinicId);
		if(size>0){
			status=0;
		}
		else{
			clinicTimingsDAO.deleteClinicTimingsByClinicId(clinicId);
			clinicsDAO.delete(clinicId);
			status=1;
		}
		
		return status;
	}
	public List<ClinicsForm> getClinicId() {
		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();

		List<Clinics> clinicss = new ArrayList<Clinics>();

		clinicss = clinicsDAO.getClinicId();

		for (Clinics clinics : clinicss) {
			// TODO: Fill the List
			ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(),
					clinics.getClinicName());
			clinicsForms.add(clinicsForm);
		}

		return clinicsForms;
	}
	
	/**
	 * Get No Of clinics
	 * @return 
	 */
	public Integer getNoOfClinics(){
		Integer noOfClinics=0;
		noOfClinics=clinicsDAO.getAll().size();
		return noOfClinics;
	}
	
	public String getAddressForGeoCode(ClinicsForm clinicsForm){
		String address= "";
		address=clinicsForm.getAddress().replace(" ","+")+"+"+clinicsForm.getCounty().replace(" ","+")+"+"+clinicsForm.getCity().replace(" ","+")+"+"+clinicsForm.getState().replace(" ","+")+"+"+clinicsForm.getCountry().replace(" ","+")+"+"+clinicsForm.getZipcode();
		return address;
	}
	
	/*
	 * Get Clinic Timing Lists
	 */
	public List<ClinicTimingList> getClinicTimingLists(Integer clinicId){
		
		List<ClinicTimings> clinicTimings=clinicTimingsDAO.getClinicTimings(clinicId);
		
		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		// Set ClinicTiming List
		for (ClinicTimings clinicTimingss: clinicTimings) {
		ClinicTimingList clinicTimingList=new ClinicTimingList(clinicTimingss.getId().getDay(), clinicTimingss.getId().getClinicId(), clinicTimingss.getStartTime(), clinicTimingss.getEndTime(), clinicTimingss.getIsWorkingDay());
		clinicTimingLists.add(clinicTimingList);
		}
		
		
		return clinicTimingLists;
	}
	
}
