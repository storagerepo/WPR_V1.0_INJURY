
package com.deemsys.project.PoliceAgency;


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
public class PoliceAgencyController {
	
	@Autowired
	PoliceAgencyService policeAgencyService;

    @RequestMapping(value="/getPoliceAgency",method=RequestMethod.GET)
	public String getPoliceAgency(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("policeAgencyForm",policeAgencyService.getPoliceAgency(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergePoliceAgency",method=RequestMethod.POST)
   	public String mergePoliceAgency(@ModelAttribute("policeAgencyForm") PoliceAgencyForm policeAgencyForm,ModelMap model)
   	{
    	policeAgencyService.mergePoliceAgency(policeAgencyForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdatePoliceAgency",method=RequestMethod.POST)
   	public String savePoliceAgency(@ModelAttribute("policeAgencyForm") PoliceAgencyForm policeAgencyForm,ModelMap model)
   	{
    	if(policeAgencyForm.getId()!=null)
    		policeAgencyService.savePoliceAgency(policeAgencyForm);
    	else
    		policeAgencyService.updatePoliceAgency(policeAgencyForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deletePoliceAgency",method=RequestMethod.POST)
   	public String deletePoliceAgency(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	policeAgencyService.deletePoliceAgency(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllPoliceAgencys",method=RequestMethod.GET)
   	public String getAllPoliceAgencys(ModelMap model)
   	{
    	model.addAttribute("policeAgencyForms",policeAgencyService.getPoliceAgencyList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getPoliceAgenciesByStatus",method=RequestMethod.GET)
   	public String getPoliceAgencyiesByStatus(@RequestParam("status") Integer status,ModelMap model)
   	{
    	model.addAttribute("policeAgencyForms",policeAgencyService.getPoliceAgenciesByStatus(status));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
