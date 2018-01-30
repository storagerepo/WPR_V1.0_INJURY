
package com.deemsys.project.Logging;


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
@RequestMapping("/Admin")
public class ActivityLogsController {
	
	@Autowired
	ActivityLogsService activityLogsService;

    @RequestMapping(value="/getActivityLogs",method=RequestMethod.GET)
	public String getActivityLogs(@RequestParam("sessionId") String sessionId,ModelMap model)
	{
    	model.addAttribute("activityLogsForm",activityLogsService.getActivityLogs(sessionId));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeActivityLogs",method=RequestMethod.POST)
   	public String mergeActivityLogs(@ModelAttribute("activityLogsForm") ActivityLogsForm activityLogsForm,ModelMap model)
   	{
    	activityLogsService.mergeActivityLogs(activityLogsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateActivityLogs",method=RequestMethod.POST)
   	public String saveActivityLogs(@ModelAttribute("activityLogsForm") ActivityLogsForm activityLogsForm,ModelMap model)
   	{
    	if(activityLogsForm.getSessionId().equals(""))
    		activityLogsService.saveActivityLogs(activityLogsForm);
    	else
    		activityLogsService.updateActivityLogs(activityLogsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteActivityLogs",method=RequestMethod.POST)
   	public String deleteActivityLogs(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	activityLogsService.deleteActivityLogs(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/searchActivityLogs",method=RequestMethod.POST)
   	public String getAllActivityLogss(@RequestBody ActivityLogsSearchForm activityLogsSearchForm,ModelMap model)
   	{
    	model.addAttribute("activityLogsForms",activityLogsService.searchActivityLogs(activityLogsSearchForm));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getIPAccessList",method=RequestMethod.POST)
   	public String getIPAccessList(@RequestBody ActivityLogsSearchForm activityLogsSearchForm,ModelMap model)
   	{
    	model.addAttribute("ipAccessList",activityLogsService.getIPAccessList(activityLogsSearchForm));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
