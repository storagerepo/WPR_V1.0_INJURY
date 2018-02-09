
package com.deemsys.project.activity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Deemsys
 *
 */
@Controller
@RequestMapping("/Admin")
public class ActivityController {
	
	@Autowired
	ActivityService activityService;

    @RequestMapping(value="/getActivity",method=RequestMethod.GET)
	public String getActivity(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("activityForm",activityService.getActivity(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeActivity",method=RequestMethod.POST)
   	public String mergeActivity(@ModelAttribute("activityForm") ActivityForm activityForm,ModelMap model)
   	{
    	activityService.mergeActivity(activityForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateActivity",method=RequestMethod.POST)
   	public String saveActivity(@ModelAttribute("activityForm") ActivityForm activityForm,ModelMap model)
   	{
    	if(activityForm.getId()==null)
    		activityService.saveActivity(activityForm);
    	else
    		activityService.updateActivity(activityForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteActivity",method=RequestMethod.POST)
   	public String deleteActivity(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	activityService.deleteActivity(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllActivitys",method=RequestMethod.GET)
   	public String getAllActivitys(ModelMap model)
   	{
    	model.addAttribute("activityForms",activityService.getActivityList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
