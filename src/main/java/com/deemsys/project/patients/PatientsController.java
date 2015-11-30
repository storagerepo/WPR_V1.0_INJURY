package com.deemsys.project.patients;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.PDFReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.pdfcrashreport.PDFCrashReportJson;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;

import com.deemsys.project.Map.ClinicLocationForm;
import com.deemsys.project.Map.SearchClinicsService;

/**
 * 
 * @author Deemsys
 *
 */
@Controller
@RequestMapping("/Staff")
public class PatientsController {
	
	@Autowired
	PatientsService patientsService;
	
	@Autowired
	PDFCrashReportReader crashReportReader;
	
	@Autowired
	SearchClinicsService searchClinicsService;

	
	@RequestMapping(value="/getAllPatientss",method=RequestMethod.GET)
   	public String getAllPatientss(ModelMap model)
   	{
    	model.addAttribute("patientsForms",patientsService.getPatientsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}

    @RequestMapping(value="/getPatients",method=RequestMethod.GET)
	public String getPatients(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("patientsForm",patientsService.getPatients(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	 
  @RequestMapping(value="/saveUpdatePatients",method=RequestMethod.POST)
   	public String updatePatients(@RequestBody  PatientsForm patientsForm,ModelMap model)
   	{
	  if(patientsForm.getId()==null)
			patientsService.savePatients(patientsForm);
		else
			patientsService.updatePatients(patientsForm);
		
    		model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}

    @RequestMapping(value="/deletePatients",method=RequestMethod.POST)
   	public String deletePatients(@RequestParam("id") Integer id,ModelMap model)
   	{
    	patientsService.deletePatients(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
 @RequestMapping(value = "/addPatientFromFile" ,headers = "content-type=multipart/form-data",method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(
            @RequestParam("file") MultipartFile file,ModelMap model) {
	 String returnText="<p>";
	
	 String errors = patientsService.addPatientFromFile(file);
		 
    	 if(!errors.equals("")){
    		 returnText+="<b>Error Description</b>";
    		returnText+=errors;
    		 returnText+="</p>";
    	  	 
    	    }
    	 else
    	 {
    		 returnText+="File Upload Successfully!!";
    		 returnText+="</p>";
    	 }
	
	 	return returnText;

    }
  @RequestMapping(value="/getNoOfPatientss",method=RequestMethod.GET)
   	public String getNoOfPatientss(ModelMap model)
   	{
    	model.addAttribute("patientsForms",patientsService.getNoOfPatientss());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
  
  @RequestMapping(value="/deletePatientsByStaffId",method=RequestMethod.POST)
 	public String deletePatientsByStaffId(@RequestParam("id") Integer id,ModelMap model)
 	{
  	patientsService.deletePatientsByStaffId(id);
  	model.addAttribute("requestSuccess",true);
 		return "/returnPage";
 	}
  
  @RequestMapping(value="/unAssignPatient",method=RequestMethod.GET)
 	public String unAssignPatient(@RequestParam("id") Integer id,ModelMap model)
 	{
  	patientsService.deletePatientsByStaffId(id);
  	model.addAttribute("requestSuccess",true);
 		return "/returnPage";
 	}
  
  //Upload PDF file
  /*@RequestMapping(value = "/uploadCrashReport" ,headers = "content-type=multipart/form-data",method = RequestMethod.POST)
  public @ResponseBody PDFCrashReportJson uploadCrashReportFileHandler(
          @RequestParam("file") MultipartFile file,ModelMap model) throws IOException {
	 
	  //String returnText=patientPDFReader.saveFile(file);
	  
	 	return crashReportReader.getValuesFromPDF(crashReportReader.parsePdf(file));

  }*/
  
//Upload PDF file
  @RequestMapping(value = "/readCrashReportFromURL" ,method = RequestMethod.POST)
  public @ResponseBody PDFCrashReportJson uploadCrashReportFileHandler(
          @RequestParam("crashId") String crashReportId,ModelMap model) throws IOException {
	 
	  return crashReportReader.getValuesFromPDF(crashReportReader.parsePdfFromURL(crashReportId));

  }
  
//Read JSON Crash Report
  @RequestMapping(value = "/readAsJSONCrashReportFromURL" ,method = RequestMethod.POST)
  public @ResponseBody List<List<String>> uploadJSONCrashReportFileHandler(
          @RequestParam("crashId") String crashReportId,ModelMap model) throws IOException {
	 
	  return crashReportReader.parsePdfFromURL(crashReportId);

  }
  
  
//Upload PDF file
  @RequestMapping(value = "/insertCrashReportFromURL" ,method = RequestMethod.POST)
  public String insertCrashReportFileHandler(
          @RequestParam("crashId") String crashReportId,ModelMap model) throws IOException {
	 
	  PDFCrashReportJson pdfCrashReportJson=crashReportReader.getValuesFromPDF(crashReportReader.parsePdfFromURL(crashReportId));
	  boolean crashReportStatus=crashReportReader.checkStatus(pdfCrashReportJson);
	  if(crashReportStatus){
		  List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		  patientsForms=crashReportReader.getPatientForm(pdfCrashReportJson);
		  for (PatientsForm patientsForm : patientsForms) {
			  patientsService.savePatients(patientsForm);
		  }		  
		  model.addAttribute("requestSuccess",true);
	  }else{
		  model.addAttribute("requestSuccess",false);
		  model.addAttribute("status","Report not statisfied the conditions");
	  }
	  return "ok";

  }
  
  
  // Get Patients With Latitude Longitude
  @RequestMapping(value="/getPatientWithLatLong",method=RequestMethod.GET)
	public String getPatientsWithLatLong(@RequestParam("id") Integer id,ModelMap model)
	{
  	model.addAttribute("patientsForm",patientsService.getPatientWithLatLong(id));
  	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}

// get Near By clinics  
@RequestMapping(value="/getNearByClincs",method=RequestMethod.GET)
	public String searchNearByClinics(@RequestParam("patientId") Integer patientId,@RequestParam("searchRange") Integer searchRange,ModelMap model)
	{
	
	ClinicLocationForm clinicLocationForms = searchClinicsService.getNearByClinics(patientId, searchRange);
	model.addAttribute("clinicLocationForm",clinicLocationForms);
	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}

    
//Upload PDF file
  @RequestMapping(value = "/uploadCrashReportPDFDocuments" ,headers = "content-type=multipart/form-data",method = RequestMethod.POST)
  public @ResponseBody String readUploadCrashReportFileHandler(
          @RequestParam("file") MultipartFile file,ModelMap model) throws IOException {
	
	  PDFCrashReportJson pdfCrashReportJson=crashReportReader.getValuesFromPDF(crashReportReader.parsePdfFromFile(file));
	  List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
	  boolean crashReportStatus=crashReportReader.checkStatus(pdfCrashReportJson);
	  if(crashReportStatus){		  
		  patientsForms=crashReportReader.getPatientForm(pdfCrashReportJson);
		  
		  String fileName="";
		  File archiveFile = null;
		  //Save File in Location
		  if(patientsForms.size()>0){	
			  fileName="D://InjuryCrashReport//Archive//"+patientsForms.get(0).getCountry()+"_CrashReport_"+patientsForms.get(0).getLocalReportNumber()+".pdf";
			  archiveFile=new File(fileName);
			  byte[] bytes=file.getBytes();
			  BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archiveFile));
              stream.write(bytes);
              stream.close();
		  }
		  
		  for (PatientsForm patientsForm : patientsForms) {
			  patientsForm.setCrashReportFileName(archiveFile.getName());
			  patientsService.savePatients(patientsForm);
		  }		  
		  model.addAttribute("requestSuccess",true);
	  }else{
		  model.addAttribute("requestSuccess",false);
		  model.addAttribute("status","Report not statisfied the conditions");
	  }
	  return "Details Added Successfully from Crash Reports!";

  }
  
  //Upload File Get Array
  @RequestMapping(value = "/readCrashReportArray" ,headers = "content-type=multipart/form-data",method = RequestMethod.POST)
  public @ResponseBody List<List<String>> readUploadCrashReportArray(
          @RequestParam("file") MultipartFile file,ModelMap model) throws IOException {
	  
	  return crashReportReader.parsePdfFromFile(file);

  }
  
  @RequestMapping(value="/adminPatientStatus",method=RequestMethod.GET)
 	public String adminPatientStatus(@RequestParam("patientStatus") Integer patientStatus,ModelMap model)
 	{
  	model.addAttribute("patientsForms",patientsService.adminPatientStatus(patientStatus));
  	model.addAttribute("requestSuccess",true);
 		return "/returnPage";
 	}
 
  @RequestMapping(value="/staffPatientStatus",method=RequestMethod.GET)
	public String staffPatientStatus(@RequestParam("patientStatus") Integer patientStatus,ModelMap model)
	{
	model.addAttribute("patientsForms",patientsService.staffPatientStatus(patientStatus));
	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
  @RequestMapping(value="/activeStatusByPatientId",method=RequestMethod.GET)
 	public String activeStatusByPatientId(@RequestParam("id") Integer id,ModelMap model)
 	{
	Integer status=patientsService.activeStatusByPatientId(id);
	if(status==0){
		
		model.addAttribute("requestSuccess",true);
	}
  	return "/returnPage";
 	}




}
