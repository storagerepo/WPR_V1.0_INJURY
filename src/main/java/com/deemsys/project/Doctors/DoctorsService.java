package com.deemsys.project.Doctors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Clinics;
import com.deemsys.project.entity.Doctors;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.patients.PatientsDAO;

/**
 * 
 * @author Deemsys
 * 
 *         Doctors - Entity doctors - Entity Object doctorss - Entity List
 *         doctorsDAO - Entity DAO doctorsForms - EntityForm List DoctorsForm -
 *         EntityForm
 * 
 */
@Service
@Transactional
public class DoctorsService {

	@Autowired
	DoctorsDAO doctorsDAO;

	@Autowired
	PatientsDAO patientsDAO;
	
	@Autowired
	ClinicsDAO clinicsDAO;

	// Get All Entries
	public List<DoctorsForm> getDoctorsList() {
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctors> doctorss = new ArrayList<Doctors>();

		doctorss = doctorsDAO.getAll();
		for (Doctors doctors : doctorss) {
			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinics().getClinicId(), doctors.getDoctorName(),
					doctors.getEmailId(), doctors.getContactNumber(),doctors.getSpecialistIn(),
					doctors.getNotes());
			doctorsForms.add(doctorsForm);
		}

		return doctorsForms;
	}

	@SuppressWarnings("unused")
	public DoctorsForm getDoctors(Integer getId) {
		Doctors doctors = new Doctors();
		doctors = doctorsDAO.get(getId);

		DoctorsForm doctorsForm = new DoctorsForm();

		if (doctors != null) {
			doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinics().getClinicId(), doctors.getDoctorName(),
					doctors.getEmailId(), doctors.getContactNumber(), doctors.getSpecialistIn(),
					doctors.getNotes());
		} else {
			doctorsForm = new DoctorsForm();
		}
		return doctorsForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeDoctors(DoctorsForm doctorsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Clinics clinics= new Clinics();
		clinics.setClinicId(doctorsForm.getClinicId());
		Doctors doctors = new Doctors(clinics,
				doctorsForm.getDoctorName(), doctorsForm.getEmailId(),
				doctorsForm.getContactNumber(), doctorsForm.getSpecialistIn(),
				doctorsForm.getNotes(), null);
		doctors.setId(doctorsForm.getId());
		// Logic Ends

		doctorsDAO.merge(doctors);
		return 1;
	}

	//View Particular Doctor Details
	@SuppressWarnings("unused")
	public DoctorsForm getDoctorDetails(Integer doctorId) {
		Doctors doctors = new Doctors();
		doctors = doctorsDAO.get(doctorId);

		DoctorsForm doctorsForm = new DoctorsForm();

		if (doctors != null) {
			doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinics().getClinicId(),doctors.getClinics().getClinicName(), doctors.getDoctorName(),
					doctors.getEmailId(), doctors.getContactNumber(), doctors.getSpecialistIn(),
					doctors.getNotes());
		} else {
			doctorsForm = new DoctorsForm();
		}
		return doctorsForm;
	}
	
	// Save an Entry
	public int saveDoctors(DoctorsForm doctorsForm)

	{
		Clinics clinics= new Clinics();
		clinics.setClinicId(doctorsForm.getClinicId());
		Doctors doctors = new Doctors(clinics,
				doctorsForm.getDoctorName(), doctorsForm.getEmailId(),
				doctorsForm.getContactNumber(), doctorsForm.getSpecialistIn(),
				doctorsForm.getNotes(), null);

		doctorsDAO.save(doctors);
		return 1;
	}

	// Update an Entry
	public int updateDoctors(DoctorsForm doctorsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Clinics clinics= new Clinics();
		clinics.setClinicId(doctorsForm.getClinicId());
		Doctors doctors = new Doctors(clinics,
				doctorsForm.getDoctorName(), doctorsForm.getEmailId(),
				doctorsForm.getContactNumber(), doctorsForm.getSpecialistIn(),
				doctorsForm.getNotes(), null);
		doctors.setId(doctorsForm.getId());
		// Logic Ends

		doctorsDAO.update(doctors);
		return 1;
	}

	// Delete an Entry
	public int deleteDoctors(Integer id) {
		int status = 0;
		List<Patients> patientss = new ArrayList<Patients>();

		patientss = patientsDAO.getpatientsByDoctorId(id);
		if (patientss.size() == 0) {
			doctorsDAO.delete(id);
			status = 1;
		} else {
			status = 0;
		}

		return status;
	}

	public Integer getNoOfDoctors() {
		Integer count = 0;

		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctors> doctorss = new ArrayList<Doctors>();

		doctorss = doctorsDAO.getAll();
		for (Doctors doctors : doctorss) {
			
			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinics().getClinicId(), doctors.getDoctorName(),
					doctors.getEmailId(), doctors.getContactNumber(),
					doctors.getSpecialistIn(), 
					doctors.getNotes());
			doctorsForms.add(doctorsForm);
			count++;
		}
		System.out.println("count:" + count);

		return count;

	}

	public List<DoctorsForm> getDoctorId() {
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctors> doctorss = new ArrayList<Doctors>();

		doctorss = doctorsDAO.getDoctorId();

		for (Doctors doctors : doctorss) {
			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getDoctorName());
			doctorsForms.add(doctorsForm);
		}

		return doctorsForms;
	}

	// Remove Assigned Doctor
	public Integer removeAssignedDoctor(Integer doctorId) {

		Integer status = 0;
		List<Patients> patients = patientsDAO.getpatientsByDoctorId(doctorId);
		for (Patients patients2 : patients) {
			patientsDAO.removeAssignedDoctor(patients2.getId());

			status = 1;
		}

		return status;
	}

	// DeleteDoctor By Clinic Id
	public String deleteDoctorByClinicId(Integer clinicId)
	{
		String status="0";
		try{
		List<Doctors> doctors=doctorsDAO.getDoctorsByClinicId(clinicId);
		for (Doctors doctors2 : doctors) {
			// Remove Assigned Doctor From Patient
			this.removeAssignedDoctor(doctors2.getId());
			doctorsDAO.removeClinicIdFromDoctor(doctors2.getId());
			// Delete Doctor
			this.deleteDoctors(doctors2.getId());
			}
		}
		catch(Exception e){
			status=e.toString();
		}
		
		
		return status;
	}
	public List<DoctorsForm> getNameByClinicId(Integer clinicId)
	{
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctors> doctorss=doctorsDAO.getDoctorsByClinicId(clinicId);
		for (Doctors doctors : doctorss) {
			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(
					doctors.getId(), doctors.getDoctorName());
			doctorsForms.add(doctorsForm);
			}
		return doctorsForms;
	
	}
public List<DoctorsForm> getDoctorsByClinic(Integer clinicId){
	List<DoctorsForm> doctorsForm= new ArrayList<DoctorsForm>();
	List<Doctors>  doctors =new ArrayList<Doctors>();
	doctors= doctorsDAO.getDoctorsByClinic(clinicId);
	
	DoctorsForm doctorsForms=new DoctorsForm();
	for(Doctors doctorss:doctors)
	{
	doctorsForms = new DoctorsForm(doctorss.getId(),
			doctorss.getClinics().getClinicId(), doctorss.getDoctorName(),
			doctorss.getEmailId(), doctorss.getContactNumber(),doctorss.getSpecialistIn(),
			doctorss.getNotes());
	doctorsForm.add(doctorsForms);
	
	}
	return doctorsForm;
	
}}
