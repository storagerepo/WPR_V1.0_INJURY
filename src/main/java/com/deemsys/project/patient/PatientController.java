package com.deemsys.project.patient;

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
import com.deemsys.project.login.LoginService;
import com.deemsys.project.pdfcrashreport.PDFCrashReportJson;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;
import com.deemsys.project.LawyerAdmin.LawyerAdminForm;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.Map.ClinicLocationForm;
import com.deemsys.project.Map.SearchClinicsService;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;

/**
 * 
 * @author Deemsys
 * 
 */
@Controller
public class PatientController {

	@Autowired
	PatientService patientService;

	@Autowired
	PDFCrashReportReader crashReportReader;

	@Autowired
	SearchClinicsService searchClinicsService;

	@Autowired
	LawyersService lawyersService;

	@Autowired
	CallerService callerService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;

	@RequestMapping(value = { "/Patient/getAllPatients",
			"/Caller/getAllPatients" }, method = RequestMethod.GET)
	public String getAllPatients(ModelMap model) {
		model.addAttribute("patientForms", patientService.getPatientList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = { "/Patient/getPatient", "/Caller/getPatient" }, method = RequestMethod.GET)
	public String getPatient(@RequestParam("patientId") String patientId, ModelMap model) {
		model.addAttribute("patientForm", patientService.getPatient(patientId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/saveUpdatePatient", method = RequestMethod.POST)
	public String updatePatient(@RequestBody PatientForm patientForm,
			ModelMap model) throws Exception {
		if (patientForm.getPatientId() == null){
			try {
				patientService.savePatient(patientForm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		else
			patientService.updatePatient(patientForm);

		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/deletePatient", method = RequestMethod.POST)
	public String deletePatient(@RequestParam("id") Integer id, ModelMap model) {
		patientService.deletePatient(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/getNoOfPatients", method = RequestMethod.GET)
	public String getNoOfPatients(ModelMap model) {
		model.addAttribute("patientForms", patientService.getNoOfPatients());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/releasePatientFromCaller", method = RequestMethod.POST)
	public String releasePatientFromCaller(@RequestParam("id") Integer id,
			ModelMap model) {
		patientService.releasePatientFromCaller(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	// Upload PDF file
	@RequestMapping(value = "/Caller/readCrashReportFromURL", method = RequestMethod.POST)
	public @ResponseBody
	PDFCrashReportJson uploadCrashReportFileHandler(
			@RequestParam("crashId") String crashReportId, ModelMap model)
			throws IOException {

		return crashReportReader.getValuesFromPDF(crashReportReader
				.parsePdfFromURL(crashReportId));

	}

	// Read JSON Crash Report
	@RequestMapping(value = "/Caller/readAsJSONCrashReportFromURL", method = RequestMethod.POST)
	public @ResponseBody
	List<List<String>> uploadJSONCrashReportFileHandler(
			@RequestParam("crashId") String crashReportId, ModelMap model)
			throws IOException {

		return crashReportReader.parsePdfFromURL(crashReportId);

	}

	//Upload Crash Id Notepad
	  @RequestMapping(value = "/Caller/uploadCrashIdNotepad" ,method = RequestMethod.POST)
	  public @ResponseBody String uploadCrashIdNotepad(
			  @RequestParam("file") MultipartFile file,ModelMap model) throws IOException {
		 
		  List<String> crashIdList=crashReportReader.getCrashIdList(file);
		  for (String crashId : crashIdList) {
			crashReportReader.downloadPDFFile(crashId);
		  }
		  return "success";
	  }
	  
	
	// get Near By clinics
	@RequestMapping(value = "/Caller/getNearByClincs", method = RequestMethod.GET)
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
	@RequestMapping(value = "/Caller/uploadCrashReportPDFDocuments", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public String readUploadCrashReportFileHandler(
			@RequestParam("file") MultipartFile file, ModelMap model)
			throws IOException {
		
		
		return "Details Added Successfully from Crash Reports!";

	}

	// Upload File Get Array
	@RequestMapping(value = "/Caller/readCrashReportJson", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	PDFCrashReportJson readUploadCrashReportArray(
			@RequestParam("file") MultipartFile file, ModelMap model)
			throws IOException {

		return crashReportReader.getValuesFromPDF(crashReportReader.parsePdfFromMultipartFile(file));

	}

	@RequestMapping(value = "/Caller/getPatientByStatus", method = RequestMethod.GET)
	public String getPatientByStatus(
			@RequestParam("patientStatus") Integer patientStatus, ModelMap model) {
		model.addAttribute("patientForms",
				patientService.getPatientByStatus(patientStatus));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/activeStatusByPatientId", method = RequestMethod.GET)
	public String activeStatusByPatientId(@RequestParam("id") Integer id,
			ModelMap model) {
		Integer status = patientService.activeStatusByPatientId(id);
		if (status == 0) {

			model.addAttribute("requestSuccess", true);
		}
		return "/returnPage";
	}

	// Get Patient By Mapped County For Lawyers
	@RequestMapping(value = "/Patient/getPatientByLawyer", method = RequestMethod.GET)
	public String getPatientByLawyer(ModelMap model) {

		Integer userId = callerService.getCurrentUserId();
		Integer lawyerId = lawyersService.getLawyerIdByUserId(userId).getLawyerId();
		List<PatientForm> patientForms = lawyersService
				.getPatientByLawyer(lawyerId);
		model.addAttribute("patientForms", patientForms);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";

	}

	// Get Patient By Mapped County For Lawyers
	@RequestMapping(value = "/Patient/getNoOfPatientByLawyer", method = RequestMethod.GET)
	public String getNoOfPatientByLawyer(ModelMap model) {

		Integer userId = callerService.getCurrentUserId();
		Integer lawyerId = lawyersService.getLawyerIdByUserId(userId).getLawyerId();
		List<PatientForm> patientForms = lawyersService
				.getPatientByLawyer(lawyerId);
		model.addAttribute("noOfPatientByLawyer", patientForms.size());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";

	}

	@RequestMapping(value = { "/Caller/getAllPatientByLimit" }, method = RequestMethod.GET)
	public String getAllPatientsByLimit(
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("itemsPerPage") Integer itemsPerPage,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("localReportNumber") String localReportNumber,
			@RequestParam("callerName") String callerName, ModelMap model) {
		model.addAttribute("patientForms", patientService.getPatientByLimit(
				pageNumber, itemsPerPage, name, phoneNumber, localReportNumber,
				callerName));
		
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = { "/Admin/searchPatients" }, method = RequestMethod.POST)
	public String searchPatients(@RequestBody PatientSearchForm patientsearchForm,ModelMap model) {
		
		model.addAttribute("patientViewForms", patientService.searchPatients(patientsearchForm.getPageNumber(),patientsearchForm.getItemsPerPage(),patientsearchForm.getLocalReportNumber(), patientsearchForm.getCounty(), patientsearchForm.getCrashDate(),patientsearchForm.getDays() , patientsearchForm.getRecordedFromDate(),patientsearchForm.getRecordedToDate() , patientsearchForm.getName(), patientsearchForm.getCustomDate()));
		model.addAttribute("totalNoOfRecords", patientService.getTotalPatient(patientsearchForm.getLocalReportNumber(), patientsearchForm.getCounty(), patientsearchForm.getCrashDate(),patientsearchForm.getDays() , patientsearchForm.getRecordedFromDate(),patientsearchForm.getRecordedToDate() , patientsearchForm.getName(),patientsearchForm.getCustomDate()));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	
	@RequestMapping(value = { "/Caller/checkRead" }, method = RequestMethod.GET)
	public @ResponseBody String getAllPatientsByLimit(ModelMap model) {
		
		return injuryProperties.getProperty("tempFolder");
	}
	
	@RequestMapping(value = { "/Patient/searchPatients" }, method = RequestMethod.POST)
	public String searchPatientsByAdmin(@RequestBody CallerPatientSearchForm callerPatientSearchForm,ModelMap model) {
		
		PatientSearchResult patientSearchResult=patientService.getCurrentPatientList(callerPatientSearchForm);
		
		model.addAttribute("status", 1);
		model.addAttribute(patientSearchResult);
		model.addAttribute("requestSuccess", true);
		return "ok";
		
	}
	
	@RequestMapping(value = { "/Patient/getNumberOfPatients" }, method = RequestMethod.GET)
	public String getNumberPatients(ModelMap model) {
		
		CallerPatientSearchForm callerPatientSearchForm=new CallerPatientSearchForm(0, 0, 0, 0, "", 0, "", "", "", 0, 0, 0, "", 1, 10, "", "",0);
		PatientSearchResult patientSearchResult=patientService.getCurrentPatientList(callerPatientSearchForm);
		model.addAttribute("numberOfPatients",patientSearchResult.getTotalNoOfRecord());
		model.addAttribute("requestSuccess", true);
		return "ok";
		
	}
	
	
	

}
