
package com.deemsys.project.CallerAdmin;


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
public class CallerAdminController {
	
	@Autowired
	CallerAdminService callerAdminService;

    @RequestMapping(value="/Admin/getCallerAdmin",method=RequestMethod.GET)
	public String getCallerAdmin(@RequestParam("callerAdminId") Integer callerAdminId,ModelMap model)
	{
    	model.addAttribute("callerAdminForm",callerAdminService.getCallerAdmin(callerAdminId));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value={"/mergeCallerAdmin","/Admin/mergeCallerAdmin"},method=RequestMethod.POST)
   	public String mergeCallerAdmin(@ModelAttribute("callerAdminForm") CallerAdminForm callerAdminForm,ModelMap model)
   	{
    	callerAdminService.mergeCallerAdmin(callerAdminForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value={"/saveUpdateCallerAdmin","/Admin/saveUpdateCallerAdmin"},method=RequestMethod.POST)
   	public String saveCallerAdmin(@RequestBody CallerAdminForm callerAdminForm,ModelMap model)
   	{
    	if(callerAdminForm.getCallerAdminId()==null)
    		callerAdminService.saveCallerAdmin(callerAdminForm);
    	else
    		callerAdminService.updateCallerAdmin(callerAdminForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/Admin/deleteCallerAdmin",method=RequestMethod.POST)
   	public String deleteCallerAdmin(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	callerAdminService.deleteCallerAdmin(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/getAllCallerAdmins",method=RequestMethod.GET)
   	public String getAllCallerAdmins(ModelMap model)
   	{
    	model.addAttribute("callerAdminForms",callerAdminService.getCallerAdminList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/enableOrDisableCallerAdmin",method=RequestMethod.POST)
   	public String enableOrDisableCallerAdmin(@RequestParam("callerAdminId") Integer callerAdminId,ModelMap model)
   	{
    	
    	callerAdminService.enableOrDisableCallerAdmin(callerAdminId);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/resetCallerAdminPassword",method=RequestMethod.POST)
   	public String resetCallerAdminPassowrd(@RequestParam("callerAdminId") Integer callerAdminId,ModelMap model)
   	{
    	
    	callerAdminService.resetCallerAdminPassword(callerAdminId);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    @RequestMapping(value = "/Admin/getNumberOfCallerAdmin", method = RequestMethod.GET)
	public String getNoOfCallerAdmin(ModelMap model) {
		model.addAttribute("numberOfCallerAdmin", callerAdminService.getNumberOfCallerAdmins());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
}
