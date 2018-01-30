
package com.deemsys.project.IPGeoLocation;


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
public class IPGeoLocationController {
	
	@Autowired
	IPGeoLocationService ipGeoLocationService;

    @RequestMapping(value="/getIpGeoLocation",method=RequestMethod.GET)
	public String getIpGeoLocation(@RequestParam("ipAddress") String ipAddress,ModelMap model)
	{
    	model.addAttribute("ipGeoLocationForm",ipGeoLocationService.getIpGeoLocation(ipAddress));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeIpGeoLocation",method=RequestMethod.POST)
   	public String mergeIpGeoLocation(@ModelAttribute("ipGeoLocationForm") IPGeoLocationForm ipGeoLocationForm,ModelMap model)
   	{
    	ipGeoLocationService.mergeIpGeoLocation(ipGeoLocationForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateIpGeoLocation",method=RequestMethod.POST)
   	public String saveIpGeoLocation(@ModelAttribute("ipGeoLocationForm") IPGeoLocationForm ipGeoLocationForm,ModelMap model)
   	{
    	if(ipGeoLocationForm.getIpAddress().equals(""))
    		ipGeoLocationService.saveIpGeoLocation(ipGeoLocationForm);
    	else
    		ipGeoLocationService.updateIpGeoLocation(ipGeoLocationForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteIpGeoLocation",method=RequestMethod.POST)
   	public String deleteIpGeoLocation(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	ipGeoLocationService.deleteIpGeoLocation(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllIpGeoLocations",method=RequestMethod.GET)
   	public String getAllIpGeoLocations(ModelMap model)
   	{
    	model.addAttribute("ipGeoLocationForms",ipGeoLocationService.getIpGeoLocationList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
