
package com.deemsys.project.Staff;


import java.security.Principal;

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
public class StaffController {
	
	@Autowired
	StaffService staffService;

    @RequestMapping(value="/Admin/getStaff",method=RequestMethod.GET)
	public String getStaff(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("staffForm",staffService.getStaff(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/Admin/mergeStaff",method=RequestMethod.POST)
   	public String mergeStaff(@RequestBody StaffForm staffForm,ModelMap model)
   	{
    	staffService.mergeStaff(staffForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/saveUpdateStaff",method=RequestMethod.POST)
   	public String saveStaff(@RequestBody StaffForm staffForm,ModelMap model)
   	{
    	if(staffForm.getId()==null)
    		staffService.saveStaff(staffForm);
    	else
    		staffService.updateStaff(staffForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/Admin/deleteStaff",method=RequestMethod.POST)
   	public String deleteStaff(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	staffService.deleteStaff(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/getAllStaffs",method=RequestMethod.GET)
   	public String getAllStaffs(ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getStaffList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    
    
    @RequestMapping(value="/Admin/getNoOfStaffs",method=RequestMethod.GET)
   	public String getNoOfStaffs(ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getNoOfStaffs());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    @RequestMapping(value="/Staff/getCurrentRole",method=RequestMethod.POST)
    public String getCurrentRole(ModelMap model,Principal principal){
    	
    	String role=staffService.getCurrentRole();
    	model.addAttribute("role", role);
    	model.addAttribute("username",principal.getName());
    	model.addAttribute("requestSuccess",true);
    	return "/returnPage";
    }
    
    
    
    
    @RequestMapping(value="/Admin/getStaffId",method=RequestMethod.GET)
   	public String getStaffId(ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getStaffId());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    @RequestMapping(value="/Staff/getPatientsByAccessToken",method=RequestMethod.GET)
   	public String getPatientsByAccessToken(ModelMap model)
   	{
       	model.addAttribute("patientsForm",staffService.getPatientsByAccessToken());
       	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
}
