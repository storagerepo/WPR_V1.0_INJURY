package com.deemsys.project.Doctors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
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

	// Get All Entries
	public List<DoctorsForm> getDoctorsList() {
		List<DoctorsForm> doctorsForms = new ArrayList<DoctorsForm>();

		List<Doctors> doctorss = new ArrayList<Doctors>();

		doctorss = doctorsDAO.getAll();
		for (Doctors doctors : doctorss) {
			// TODO: Fill the List
			String workingDayEntity = doctors.getWorkingDays();
			String[] workingDaysString = workingDayEntity.split(",");

			DoctorsForm doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinicName(), doctors.getDoctorName(),
					doctors.getAddress(), doctors.getCity(),
					doctors.getCountry(), doctors.getState(),
					doctors.getCounty(), doctors.getZip(),
					doctors.getOfficeNumber(), doctors.getFaxNumber(),
					workingDaysString, InjuryConstants.convertToStringFormatTime(doctors.getOfficeHoursFromTime()), 
					InjuryConstants.convertToStringFormatTime(doctors.getOfficeHoursToTime()), doctors.getDirection(),
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
		String workingDayEntity = doctors.getWorkingDays();
		String[] workingDaysString = workingDayEntity.split(",");

		if (doctors != null) {
			doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinicName(), doctors.getDoctorName(),
					doctors.getAddress(), doctors.getCity(),
					doctors.getCountry(), doctors.getState(),
					doctors.getCounty(), doctors.getZip(),
					doctors.getOfficeNumber(), doctors.getFaxNumber(),
					workingDaysString, InjuryConstants.convertToStringFormatTime(doctors.getOfficeHoursFromTime()), 
					InjuryConstants.convertToStringFormatTime(doctors.getOfficeHoursToTime()), doctors.getDirection(),
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
		String[] workingDays = doctorsForm.getWorkingDays();
		String workingDaysString = "";
		for (int i = 0; i < workingDays.length; i++) {
			if (!workingDays[i].trim().equals(""))
				workingDaysString = workingDaysString + "," + workingDays[i];
		}
		Doctors doctors = new Doctors(doctorsForm.getClinicName(),
				doctorsForm.getDoctorName(), doctorsForm.getAddress(),
				doctorsForm.getCity(), doctorsForm.getCountry(),
				doctorsForm.getState(), doctorsForm.getCounty(),
				doctorsForm.getZip(), doctorsForm.getOfficeNumber(),
				doctorsForm.getFaxNumber(), workingDaysString,
				InjuryConstants.convertToDateFormatTime(doctorsForm
						.getOfficeHoursFromTime()),
				InjuryConstants.convertToDateFormatTime(doctorsForm
						.getOfficeHoursToTime()), doctorsForm.getDirection(),
				doctorsForm.getNotes(), null);
		doctors.setId(doctorsForm.getId());
		// Logic Ends

		doctorsDAO.merge(doctors);
		return 1;
	}

	// Save an Entry
	public int saveDoctors(DoctorsForm doctorsForm)

	{
		String[] workingDays = doctorsForm.getWorkingDays();
		String workingDaysString = "";
		for (int i = 0; i < workingDays.length; i++) {
			if (!workingDays[i].trim().equals(""))
				workingDaysString = workingDaysString + "," + workingDays[i];
		}
		Doctors doctors = new Doctors(doctorsForm.getClinicName(),
				doctorsForm.getDoctorName(), doctorsForm.getAddress(),
				doctorsForm.getCity(), doctorsForm.getCountry(),
				doctorsForm.getState(), doctorsForm.getCounty(),
				doctorsForm.getZip(), doctorsForm.getOfficeNumber(),
				doctorsForm.getFaxNumber(), workingDaysString,
				InjuryConstants.convertToDateFormatTime(doctorsForm
						.getOfficeHoursFromTime()),
				InjuryConstants.convertToDateFormatTime(doctorsForm
						.getOfficeHoursToTime()), doctorsForm.getDirection(),
				doctorsForm.getNotes(), null);

		doctorsDAO.save(doctors);
		return 1;
	}

	// Update an Entry
	public int updateDoctors(DoctorsForm doctorsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		String[] workingDays = doctorsForm.getWorkingDays();
		String workingDaysString = "";
		for (int i = 0; i < workingDays.length; i++) {
			if (!workingDays[i].trim().equals(""))
				workingDaysString = workingDaysString + "," + workingDays[i];
		}
		Doctors doctors = new Doctors(doctorsForm.getClinicName(),
				doctorsForm.getDoctorName(), doctorsForm.getAddress(),
				doctorsForm.getCity(), doctorsForm.getCountry(),
				doctorsForm.getState(), doctorsForm.getCounty(),
				doctorsForm.getZip(), doctorsForm.getOfficeNumber(),
				doctorsForm.getFaxNumber(), workingDaysString,
				InjuryConstants.convertToDateFormatTime(doctorsForm
						.getOfficeHoursFromTime()),
				InjuryConstants.convertToDateFormatTime(doctorsForm
						.getOfficeHoursToTime()), doctorsForm.getDirection(),
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
			String workingDayEntity = doctors.getWorkingDays();
			String[] workingDaysString = workingDayEntity.split(",");

			// TODO: Fill the List
			DoctorsForm doctorsForm = new DoctorsForm(doctors.getId(),
					doctors.getClinicName(), doctors.getDoctorName(),
					doctors.getAddress(), doctors.getCity(),
					doctors.getCountry(), doctors.getState(),
					doctors.getCounty(), doctors.getZip(),
					doctors.getOfficeNumber(), doctors.getFaxNumber(),
					workingDaysString, doctors.getOfficeHoursFromTime()
							.toString(), doctors.getOfficeHoursToTime()
							.toString(), doctors.getDirection(),
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

}
