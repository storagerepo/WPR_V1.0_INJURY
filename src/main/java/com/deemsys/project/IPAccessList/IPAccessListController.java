
package com.deemsys.project.IPAccessList;


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
public class IPAccessListController {
	
	@Autowired
	IPAccessListService ipAccessListService;

    @RequestMapping(value="/getIpAccessList",method=RequestMethod.GET)
	public String getIpAccessList(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("ipAccessListForm",ipAccessListService.getIpAccessList(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeIpAccessList",method=RequestMethod.POST)
   	public String mergeIpAccessList(@ModelAttribute("ipAccessListForm") IPAccessListForm ipAccessListForm,ModelMap model)
   	{
    	ipAccessListService.mergeIpAccessList(ipAccessListForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateIpAccessList",method=RequestMethod.POST)
   	public String saveIpAccessList(@ModelAttribute("ipAccessListForm") IPAccessListForm ipAccessListForm,ModelMap model)
   	{
    	if(ipAccessListForm.getIpAddress().equals(""))
    		ipAccessListService.saveIpAccessList(ipAccessListForm);
    	else
    		ipAccessListService.updateIpAccessList(ipAccessListForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteIpAccessList",method=RequestMethod.POST)
   	public String deleteIpAccessList(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	ipAccessListService.deleteIpAccessList(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllIpAccessLists",method=RequestMethod.GET)
   	public String getAllIpAccessLists(ModelMap model)
   	{
    	model.addAttribute("ipAccessListForms",ipAccessListService.getIpAccessListList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
