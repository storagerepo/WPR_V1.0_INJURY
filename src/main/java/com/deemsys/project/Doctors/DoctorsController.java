
package com.deemsys.project.Doctors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.Clinics.ClinicsService;

/**
 * 
 * @author Deemsys
 *
 */
@Controller
public class DoctorsController {
	
	@Autowired
	DoctorsService doctorsService;

    @RequestMapping(value="/Admin/getDoctors",method=RequestMethod.GET)
	public String getDoctors(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("doctorsForm",doctorsService.getDoctors(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/Admin/mergeDoctors",method=RequestMethod.POST)
   	public String mergeDoctors(@RequestBody DoctorsForm doctorsForm,ModelMap model)
   	{
    	doctorsService.mergeDoctors(doctorsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/saveUpdateDoctors",method=RequestMethod.POST)
   	public String saveDoctors(@RequestBody DoctorsForm doctorsForm,ModelMap model)
   	{
    	if(doctorsForm.getId()==null)
    		doctorsService.saveDoctors(doctorsForm);
    	else
    		doctorsService.updateDoctors(doctorsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/Admin/deleteDoctors",method=RequestMethod.POST)
   	public String deleteDoctors(@RequestParam("id") Integer id,ModelMap model)
   	{
    	int status=0;
    	status=doctorsService.deleteDoctors(id);
    	
    	if(status>0){
    		model.addAttribute("requestSuccess",true);
    	}
    	else{
    		model.addAttribute("requestSuccess",false);
    	}
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/removeAssignedDoctors",method=RequestMethod.POST)
   	public String removeAssignedDoctors(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	doctorsService.removeAssignedDoctor(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Staff/getAllDoctorss",method=RequestMethod.GET)
   	public String getAllDoctorss(ModelMap model)
   	{
    	model.addAttribute("doctorsForms",doctorsService.getDoctorsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    @RequestMapping(value="/Staff/getNoOfDoctors",method=RequestMethod.GET)
   	public String getNoOfDoctors(ModelMap model)
   	{
    	model.addAttribute("doctorsForms",doctorsService.getNoOfDoctors());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    
    @RequestMapping(value="/Staff/getDoctorId",method=RequestMethod.GET)
   	public String getDoctorId(ModelMap model)
   	{
    	model.addAttribute("doctorsForms",doctorsService.getDoctorId());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}

    @RequestMapping(value="/viewDoctors",method=RequestMethod.GET)
   	public String viewDoctors(@RequestParam("id") Integer id,ModelMap model)
   	{
       	model.addAttribute("doctorsForm",doctorsService.getDoctors(id));
       	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    @RequestMapping(value="/Admin/deleteDoctorsByClinic",method=RequestMethod.POST)
	public String deleteDoctorsByClinicId(@RequestParam("clinicId") Integer clinicId,Model model){
		
		String status=doctorsService.deleteDoctorByClinicId(clinicId);
		if(status.equals("0"))
			model.addAttribute("requestSuccess",true);
		else
			model.addAttribute("requestSuccess",false);
		
		return "/returnPage";
	}
    
}
