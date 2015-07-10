
package com.deemsys.project.Staff;


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
public class StaffController {
	
	@Autowired
	StaffService staffService;

    @RequestMapping(value="/getStaff",method=RequestMethod.GET)
	public String getStaff(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("staffForm",staffService.getStaff(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeStaff",method=RequestMethod.POST)
   	public String mergeStaff(@RequestBody StaffForm staffForm,ModelMap model)
   	{
    	staffService.mergeStaff(staffForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateStaff",method=RequestMethod.POST)
   	public String saveStaff(@RequestBody StaffForm staffForm,ModelMap model)
   	{
    	if(staffForm.getId()==null)
    		staffService.saveStaff(staffForm);
    	else
    		staffService.updateStaff(staffForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteStaff",method=RequestMethod.POST)
   	public String deleteStaff(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	staffService.deleteStaff(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllStaffs",method=RequestMethod.GET)
   	public String getAllStaffs(ModelMap model)
   	{
    	model.addAttribute("staffForms",staffService.getStaffList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
