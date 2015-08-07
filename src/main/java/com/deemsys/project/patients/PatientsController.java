
package com.deemsys.project.patients;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
	 
  @RequestMapping(value="/updatePatients",method=RequestMethod.POST)
   	public String updatePatients(@ModelAttribute("patientsForm") PatientsForm patientsForm,ModelMap model)
   	{
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
   
 
    @RequestMapping(value = "/addPatientFromFile" ,method = RequestMethod.POST)
    public String uploadFileHandler(
            @RequestParam("file") MultipartFile file,ModelMap model) {
    
    	patientsService.addPatientFromFile(file);
    	model.addAttribute("requestSuccess",true);
    	return "/returnPage";
    
    }
    
}
