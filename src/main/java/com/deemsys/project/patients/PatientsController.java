
package com.deemsys.project.patients;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value="/getPatients",method=RequestMethod.GET)
	public String getPatients(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("patientsForm",patientsService.getPatients(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergePatients",method=RequestMethod.POST)
   	public String mergePatients(@RequestBody PatientsForm patientsForm,ModelMap model)
   	{
    	patientsService.mergePatients(patientsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdatePatients",method=RequestMethod.POST)
   	public String savePatients(@ModelAttribute("patientsForm") PatientsForm patientsForm,ModelMap model)
   	{
    	if(patientsForm.getId().equals(""))
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
    
    @RequestMapping(value="/getAllPatientss",method=RequestMethod.GET)
   	public String getAllPatientss(ModelMap model)
   	{
    	model.addAttribute("patientsForms",patientsService.getPatientsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    @RequestMapping(value="/fileUpload",method=RequestMethod.POST)
   	public String getFileUpload(@RequestParam("path") String path,ModelMap model) throws Exception
   	{
    	model.addAttribute("patientsExcelForm",patientsService.addPatientUsingExcel(path));
    	model.addAttribute("requestSuccess",true);
    	System.out.println("file upload successfuly.....");
   		return "/returnPage";
   	}
	
    
	
}
