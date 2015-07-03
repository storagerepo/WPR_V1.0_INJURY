
package com.deemsys.project.CallLogs;


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
public class CallLogsController {
	
	@Autowired
	CallLogsService callLogsService;

    @RequestMapping(value="/getCallLogs",method=RequestMethod.GET)
	public String getCallLogs(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("callLogsForm",callLogsService.getCallLogs(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeCallLogs",method=RequestMethod.POST)
   	public String mergeCallLogs(@RequestBody CallLogsForm callLogsForm,ModelMap model)
   	{
    	callLogsService.mergeCallLogs(callLogsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateCallLogs",method=RequestMethod.POST)
   	public String saveCallLogs(@RequestBody CallLogsForm callLogsForm,ModelMap model)
   	{
    	if(callLogsForm.getId()==null)
    		callLogsService.saveCallLogs(callLogsForm);
    	else
    		callLogsService.updateCallLogs(callLogsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteCallLogs",method=RequestMethod.POST)
   	public String deleteCallLogs(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	callLogsService.deleteCallLogs(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllCallLogss",method=RequestMethod.GET)
   	public String getAllCallLogss(ModelMap model)
   	{
    	model.addAttribute("callLogsForms",callLogsService.getCallLogsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
