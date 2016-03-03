
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

import com.deemsys.project.entity.Staff;

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
    	int i=0;
    	try{
    	i= staffService.deleteStaff(id);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	if(i>0)
    	{
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
    	}
    	else
    	{
    		model.addAttribute("requestSuccess",false);
       		return "/returnPage";
    		
    	}
    	}
    
    @RequestMapping(value="/Admin/getAllStaffs",method=RequestMethod.GET)
   	public String getAllStaffs(ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getStaffList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    
    
    @RequestMapping(value="/Staff/getNoOfStaffs",method=RequestMethod.GET)
   	public String getNoOfStaffs(ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getNoOfStaffs());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    @RequestMapping(value="/getCurrentRole",method=RequestMethod.POST)
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
    
    @RequestMapping(value="/Admin/getUsername",method=RequestMethod.GET)
   	public String getUsername(@RequestParam("username") String username,ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getUsername(username));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
 
    @RequestMapping(value="/Admin/getDetails",method=RequestMethod.GET)
   	public String getAdminDetails(ModelMap model)
   	{
    	model.addAttribute("adminDetails",staffService.getDetails());
    	model.addAttribute("requestSuccess",true);
    	return "/returnPage";
   	}
    
    @RequestMapping(value="/Staff/getDetails",method=RequestMethod.GET)
   	public String getStaffDetails(ModelMap model)
   	{
    	model.addAttribute("staffDetails",staffService.getDetails());
    	model.addAttribute("requestSuccess",true);
    	return "/returnPage";
   	}
    
    
    @RequestMapping(value="/checkPassword",method=RequestMethod.POST)
   	public String checkPassword(@RequestParam("oldPassword") String oldPassword,ModelMap model)
   	{
	 if(staffService.checkPassword(oldPassword)== 1){
		 model.addAttribute("requestSuccess",true);
		   }
		   else
		   {
			   model.addAttribute("requestSuccess",false);
		   }
	return "/returnPage";
   	}
    
    @RequestMapping(value="/changePassword",method=RequestMethod.POST)
   	public String changePassword(@RequestParam("newPassword") String newPassword,ModelMap model)
   	{
    	staffService.changePassword(newPassword);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/disableStaff",method=RequestMethod.GET)
   	public String disableStaff(@RequestParam("id") Integer id,ModelMap model)
   	{
       	Integer status=staffService.disableStaff(id);
       	if(status==0){
       		model.addAttribute("enableOrDisable",0);
       		model.addAttribute("requestSuccess",true);
       	}else{
       		model.addAttribute("enableOrDisable",1);
       		model.addAttribute("requestSuccess",true);
           		
       	}
       return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/resetPassword",method=RequestMethod.GET)
   	public String resetPassword(@RequestParam("id") Integer id,ModelMap model)
   	{
    	Integer status=staffService.resetPassword(id);
    	if(status==0){
       		model.addAttribute("requestSuccess",true);
    	}
       return "/returnPage";
   	}
    
}
