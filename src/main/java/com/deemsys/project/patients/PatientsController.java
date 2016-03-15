package com.deemsys.project.patients;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.pdfcrashreport.PDFCrashReportJson;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;

import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.Map.ClinicLocationForm;
import com.deemsys.project.Map.SearchClinicsService;
import com.deemsys.project.Staff.StaffService;

/**
 * 
 * @author Deemsys
 * 
 */
@Controller
public class PatientsController {

	@Autowired
	PatientsService patientsService;

	@Autowired
	PDFCrashReportReader crashReportReader;

	@Autowired
	SearchClinicsService searchClinicsService;

	@Autowired
	LawyersService lawyersService;

	@Autowired
	StaffService staffService;
	
	@Autowired
	InjuryProperties injuryProperties;

	@RequestMapping(value = { "/Patient/getAllPatientss",
			"/Staff/getAllPatientss" }, method = RequestMethod.GET)
	public String getAllPatientss(ModelMap model) {
		model.addAttribute("patientsForms", patientsService.getPatientsList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = { "/Patient/getPatients", "/Staff/getPatients" }, method = RequestMethod.GET)
	public String getPatients(@RequestParam("id") Integer id, ModelMap model) {
		model.addAttribute("patientsForm", patientsService.getPatients(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Staff/saveUpdatePatients", method = RequestMethod.POST)
	public String updatePatients(@RequestBody PatientsForm patientsForm,
			ModelMap model) {
		if (patientsForm.getId() == null)
			patientsService.savePatients(patientsForm);
		else
			patientsService.updatePatients(patientsForm);

		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Staff/deletePatients", method = RequestMethod.POST)
	public String deletePatients(@RequestParam("id") Integer id, ModelMap model) {
		patientsService.deletePatients(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Staff/getNoOfPatientss", method = RequestMethod.GET)
	public String getNoOfPatientss(ModelMap model) {
		model.addAttribute("patientsForms", patientsService.getNoOfPatientss());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Staff/releasePatientsFromStaff", method = RequestMethod.POST)
	public String releasePatientsFromStaff(@RequestParam("id") Integer id,
			ModelMap model) {
		patientsService.releasePatientsFromStaff(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	// Upload PDF file
	@RequestMapping(value = "/Staff/readCrashReportFromURL", method = RequestMethod.POST)
	public @ResponseBody
	PDFCrashReportJson uploadCrashReportFileHandler(
			@RequestParam("crashId") String crashReportId, ModelMap model)
			throws IOException {

		return crashReportReader.getValuesFromPDF(crashReportReader
				.parsePdfFromURL(crashReportId));

	}

	// Read JSON Crash Report
	@RequestMapping(value = "/Staff/readAsJSONCrashReportFromURL", method = RequestMethod.POST)
	public @ResponseBody
	List<List<String>> uploadJSONCrashReportFileHandler(
			@RequestParam("crashId") String crashReportId, ModelMap model)
			throws IOException {

		return crashReportReader.parsePdfFromURL(crashReportId);

	}

	//Upload Crash Id Notepad
	  @RequestMapping(value = "/Staff/uploadCrashIdNotepad" ,method = RequestMethod.POST)
	  public @ResponseBody String uploadCrashIdNotepad(
			  @RequestParam("file") MultipartFile file,ModelMap model) throws IOException {
		 
		  List<String> crashIdList=crashReportReader.getCrashIdList(file);
		  for (String crashId : crashIdList) {
			crashReportReader.downloadPDFFile(crashId);
		  }
		  return "success";
	  }
	  
	
	// Get Patients With Latitude Longitude
	@RequestMapping(value = "/Staff/getPatientWithLatLong", method = RequestMethod.GET)
	public String getPatientsWithLatLong(@RequestParam("id") Integer id,
			ModelMap model) {
		model.addAttribute("patientsForm",
				patientsService.getPatientWithLatLong(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	// get Near By clinics
	@RequestMapping(value = "/Staff/getNearByClincs", method = RequestMethod.GET)
	public String searchNearByClinics(
			@RequestParam("patientId") Integer patientId,
			@RequestParam("searchRange") Integer searchRange, ModelMap model) {

		ClinicLocationForm clinicLocationForms = searchClinicsService
				.getNearByClinics(patientId, searchRange);
		model.addAttribute("clinicLocationForm", clinicLocationForms);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	// Upload PDF file
	@RequestMapping(value = "/Staff/uploadCrashReportPDFDocuments", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public String readUploadCrashReportFileHandler(
			@RequestParam("file") MultipartFile file, ModelMap model)
			throws IOException {
		
		
		return "Details Added Successfully from Crash Reports!";

	}

	// Upload File Get Array
	@RequestMapping(value = "/Staff/readCrashReportJson", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	PDFCrashReportJson readUploadCrashReportArray(
			@RequestParam("file") MultipartFile file, ModelMap model)
			throws IOException {

		return crashReportReader.getValuesFromPDF(crashReportReader.parsePdfFromMultipartFile(file));

	}

	@RequestMapping(value = "/Staff/getPatientsByStatus", method = RequestMethod.GET)
	public String getPatientsByStatus(
			@RequestParam("patientStatus") Integer patientStatus, ModelMap model) {
		model.addAttribute("patientsForms",
				patientsService.getPatientsByStatus(patientStatus));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Staff/activeStatusByPatientId", method = RequestMethod.GET)
	public String activeStatusByPatientId(@RequestParam("id") Integer id,
			ModelMap model) {
		Integer status = patientsService.activeStatusByPatientId(id);
		if (status == 0) {

			model.addAttribute("requestSuccess", true);
		}
		return "/returnPage";
	}

	// Get Patients By Mapped County For Lawyers
	@RequestMapping(value = "/Patient/getPatientsByLawyer", method = RequestMethod.GET)
	public String getPatientsByLawyer(ModelMap model) {

		Integer userId = staffService.getCurrentUserId();
		Integer lawyerId = lawyersService.getLawyerIdByUserId(userId);
		List<PatientsForm> patientsForms = lawyersService
				.getPatientsByLawyer(lawyerId);
		model.addAttribute("patientsForms", patientsForms);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";

	}

	// Get Patients By Mapped County For Lawyers
	@RequestMapping(value = "/Patient/getNoOfPatientsByLawyer", method = RequestMethod.GET)
	public String getNoOfPatientsByLawyer(ModelMap model) {

		Integer userId = staffService.getCurrentUserId();
		Integer lawyerId = lawyersService.getLawyerIdByUserId(userId);
		List<PatientsForm> patientsForms = lawyersService
				.getPatientsByLawyer(lawyerId);
		model.addAttribute("noOfPatientsByLawyer", patientsForms.size());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";

	}

	@RequestMapping(value = { "/Staff/getAllPatientsByLimit" }, method = RequestMethod.GET)
	public String getAllPatientssByLimit(
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("itemsPerPage") Integer itemsPerPage,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("localReportNumber") String localReportNumber,
			@RequestParam("callerName") String callerName, ModelMap model) {
		model.addAttribute("patientsForms", patientsService.getPatientsByLimit(
				pageNumber, itemsPerPage, name, phoneNumber, localReportNumber,
				callerName));
		model.addAttribute("totalCount", patientsService.getTotalPatients(name,
				phoneNumber, localReportNumber, callerName));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = { "/Staff/checkRead" }, method = RequestMethod.GET)
	public @ResponseBody String getAllPatientssByLimit(ModelMap model) {
		
		return injuryProperties.getProperty("tempFolder");
	}

}
