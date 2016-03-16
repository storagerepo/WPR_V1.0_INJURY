package com.deemsys.project.Doctors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Clinics.ClinicsService;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Doctor;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.patient.PatientDAO;

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
	PatientDAO patientsDAO;

	@Autowired
	ClinicsDAO clinicsDAO;

	@Autowired
	ClinicsService clinicsService;

	// Get All Entries
	public List<DoctorsForm> getDoctorsList() {
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctor> doctorss = new ArrayList<Doctor>();

		doctorss = doctorsDAO.getAll();
		for (Doctor doctors : doctorss) {
			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getDoctorId(),
					doctors.getDoctorName(), doctors.getTitleDr(),
					doctors.getTitleDc());

			doctorsForms.add(doctorsForm);
		}

		return doctorsForms;
	}

	public DoctorsForm getDoctors(Integer getId) {
		Doctor doctors = new Doctor();
		doctors = doctorsDAO.get(getId);

		DoctorsForm doctorsForm = new DoctorsForm();

		if (doctors != null) {
			doctorsForm = new DoctorsForm(doctors.getDoctorId(),
					doctors.getDoctorName(), doctors.getTitleDr(),
					doctors.getTitleDc());
		} else {
			doctorsForm = new DoctorsForm();
		}
		return doctorsForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeDoctors(DoctorsForm doctorsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Clinic clinics = new Clinic();
		clinics.setClinicId(doctorsForm.getClinicId());
		/*
		 * Doctors doctors = new Doctors(clinics, doctorsForm.getDoctorName(),
		 * doctorsForm.getEmailId(), doctorsForm.getContactNumber(),
		 * doctorsForm.getSpecialistIn(), doctorsForm.getNotes(), null);
		 */
		Doctor doctors = new Doctor();
		doctors.setClinic(clinics);
		doctors.setDoctorName(doctorsForm.getDoctorName());
		doctors.setTitleDr(doctorsForm.getTitleDr());
		doctors.setTitleDc(doctorsForm.getTitleDc());
		doctors.setDoctorId(doctorsForm.getId());
		// Logic Ends

		doctorsDAO.merge(doctors);
		return 1;
	}

	// View Particular Doctor Details
	public DoctorsForm getDoctorDetails(Integer doctorId) {
		Doctor doctors = new Doctor();
		doctors = doctorsDAO.get(doctorId);

		DoctorsForm doctorsForm = new DoctorsForm();

		if (doctors != null) {
			doctorsForm = new DoctorsForm(doctors.getDoctorId(),
					doctors.getDoctorName(), doctors.getTitleDr(),
					doctors.getTitleDc());
		} else {
			doctorsForm = new DoctorsForm();
		}
		return doctorsForm;
	}

	// Save an Entry
	public int saveDoctors(DoctorsForm doctorsForm)

	{
		Clinic clinics = new Clinic();
		clinics.setClinicId(doctorsForm.getClinicId());
		/*
		 * Doctors doctors = new Doctors(clinics, doctorsForm.getDoctorName(),
		 * doctorsForm.getEmailId(), doctorsForm.getContactNumber(),
		 * doctorsForm.getSpecialistIn(), doctorsForm.getNotes(), null);
		 */
		Doctor doctors = new Doctor();
		doctors.setClinic(clinics);
		doctors.setDoctorName(doctorsForm.getDoctorName());
		doctors.setTitleDr(doctorsForm.getTitleDr());
		doctors.setTitleDc(doctorsForm.getTitleDc());
		doctorsDAO.save(doctors);
		return 1;
	}

	// Update an Entry
	public int updateDoctors(DoctorsForm doctorsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Clinic clinics = new Clinic();
		clinics.setClinicId(doctorsForm.getClinicId());
		/*
		 * Doctors doctors = new Doctors(clinics, doctorsForm.getDoctorName(),
		 * doctorsForm.getEmailId(), doctorsForm.getContactNumber(),
		 * doctorsForm.getSpecialistIn(), doctorsForm.getNotes(), null);
		 */
		Doctor doctors = new Doctor();
		doctors.setClinic(clinics);
		doctors.setDoctorName(doctorsForm.getDoctorName());
		doctors.setTitleDr(doctorsForm.getTitleDr());
		doctors.setTitleDc(doctorsForm.getTitleDc());
		doctors.setDoctorId(doctorsForm.getId());
		// Logic Ends

		doctorsDAO.update(doctors);
		return 1;
	}

	// Delete an Entry
	public int deleteDoctors(Integer id) {
		int status = 0;
		List<Patient> patientss = new ArrayList<Patient>();

		patientss = patientsDAO.getpatientByDoctorId(id);
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

		List<Doctor> doctorss = new ArrayList<Doctor>();

		doctorss = doctorsDAO.getAll();
		for (Doctor doctors : doctorss) {

			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getDoctorId(), doctors
					.getClinic().getClinicId(), doctors.getDoctorName());
			doctorsForms.add(doctorsForm);
			count++;
		}
		System.out.println("count:" + count);

		return count;

	}

	public List<DoctorsForm> getDoctorId() {
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctor> doctorss = new ArrayList<Doctor>();

		doctorss = doctorsDAO.getDoctorId();

		for (Doctor doctors : doctorss) {
			// TODO: Fill the List
			String doctorName = this.getDoctorNameWithTitle(doctors);
		DoctorsForm doctorsForm = new DoctorsForm(doctors.getDoctorId(),
					doctorName);
			doctorsForms.add(doctorsForm);
		}

		return doctorsForms;
	}

	// Remove Assigned Doctor
	public Integer removeAssignedDoctor(Integer doctorId) {

		Integer status = 0;
		List<Patient> patients = patientsDAO.getpatientByDoctorId(doctorId);
		for (Patient patients2 : patients) {
			//patientsDAO.removeAssignedDoctor(patients2.getPatientId());
			// Need to change remove Assigned Doctor Function
			status = 1;
		}

		return status;
	}

	// DeleteDoctor By Clinic Id
	public String deleteDoctorByClinicId(Integer clinicId) {
		String status = "0";
		try {
			// Unassign and Remove Doctors
			List<Doctor> doctors = doctorsDAO.getDoctorsByClinicId(clinicId);
			for (Doctor doctors2 : doctors) {
				// Remove Assigned Doctor From Patient
				this.removeAssignedDoctor(doctors2.getDoctorId());
				doctorsDAO.removeClinicIdFromDoctor(doctors2.getDoctorId());
				// Delete Doctor
				this.deleteDoctors(doctors2.getDoctorId());
			}
			// Unassign Patients
			// Need to Confirm with Client
			// /clinicsService.removeAssignedClinic(clinicId);
		} catch (Exception e) {
			status = e.toString();
		}

		return status;
	}

	public List<DoctorsForm> getNameByClinicId(Integer clinicId) {
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctor> doctorss = doctorsDAO.getDoctorsByClinicId(clinicId);
		for (Doctor doctors : doctorss) {
			// TODO: Fill the List
			String doctorName = this.getDoctorNameWithTitle(doctors);
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getDoctorId(),
					doctorName);
			doctorsForms.add(doctorsForm);
		}
		return doctorsForms;

	}

	// Get Doctors BY Clinic For Edit Operations
	public List<DoctorsForm> getDoctorsByClinic(Integer clinicId) {
		List<DoctorsForm> doctorsForm = new ArrayList<DoctorsForm>();
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors = doctorsDAO.getDoctorsByClinic(clinicId);

		DoctorsForm doctorsForms = new DoctorsForm();
		for (Doctor doctorss : doctors) {
			doctorsForms = new DoctorsForm(doctorss.getDoctorId(),
					doctorss.getDoctorName(), doctorss.getTitleDr(),
					doctorss.getTitleDc());
			doctorsForms.setIsRemoveable(this
					.getDoctorsRemoveableStatus(doctorss.getDoctorId()));
			doctorsForm.add(doctorsForms);

		}
		return doctorsForm;

	}

	// Get Doctors BY Clinic For View Details
	public List<DoctorsForm> getDoctorsDetailsByClinic(Integer clinicId) {
		List<DoctorsForm> doctorsForm = new ArrayList<DoctorsForm>();
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors = doctorsDAO.getDoctorsByClinic(clinicId);

		DoctorsForm doctorsForms = new DoctorsForm();
		for (Doctor doctorss : doctors) {
			String doctorName = this.getDoctorNameWithTitle(doctorss);
			doctorsForms = new DoctorsForm(doctorss.getDoctorId(), doctorName,
					doctorss.getTitleDr(), doctorss.getTitleDc());
			doctorsForm.add(doctorsForms);

		}
		return doctorsForm;

	}

	// Append the DoctorName and Title
	public String getDoctorNameWithTitle(Doctor doctors) {
		String doctorName = doctors.getDoctorName();
		if (doctors.getTitleDr() != null) {
			if (doctors.getTitleDr() == 1) {
				doctorName = "Dr" + " " + doctorName;
			}
		}
		if (doctors.getTitleDc() != null) {
			if (doctors.getTitleDc() == 1) {
				doctorName = doctorName + " " + "DC";
			}
		}

		return doctorName;
	}

	// Get Doctor Removeable Status Based on the Appointment
	public Integer getDoctorsRemoveableStatus(Integer id) {
		Integer status = 0;
		List<Patient> patientss = new ArrayList<Patient>();

		patientss = patientsDAO.getpatientByDoctorId(id);
		if (patientss.size() == 0) {
			status = 1;
		} else {
			status = 0;
		}

		return status;
	}

}
