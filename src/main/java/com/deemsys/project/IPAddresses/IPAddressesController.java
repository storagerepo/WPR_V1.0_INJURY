
package com.deemsys.project.IPAddresses;


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
public class IPAddressesController {
	
	@Autowired
	IPAddressesService ipAddressesService;

    @RequestMapping(value="/getIpAddresses",method=RequestMethod.GET)
	public String getIpAddresses(@RequestParam("ipAddress") String ipAddress,ModelMap model)
	{
    	model.addAttribute("ipAddressesForm",ipAddressesService.getIpAddresses(ipAddress));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeIpAddresses",method=RequestMethod.POST)
   	public String mergeIpAddresses(@RequestBody IPAddressesForm ipAddressesForm,ModelMap model)
   	{
    	ipAddressesService.mergeIpAddresses(ipAddressesForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateIpAddresses",method=RequestMethod.POST)
   	public String saveIpAddresses(@RequestBody IPAddressesForm ipAddressesForm,ModelMap model)
   	{
    	if(ipAddressesForm.getIpAddress()==null)
    		ipAddressesService.saveIpAddresses(ipAddressesForm);
    	else
    		ipAddressesService.updateIpAddresses(ipAddressesForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteIpAddresses",method=RequestMethod.POST)
   	public String deleteIpAddresses(@RequestParam("ipAddress") String ipAddress,ModelMap model)
   	{
    	
    	ipAddressesService.deleteIpAddress(ipAddress);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/searchIpAddressesList",method=RequestMethod.POST)
   	public String searchIpAddressesList(@RequestBody IPAddressesSearchForm ipAddressesSearchForm,ModelMap model)
   	{
    	model.addAttribute("ipAddressList",ipAddressesService.searchIpAddressesList(ipAddressesSearchForm));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getBlockedIpList",method=RequestMethod.GET)
   	public String getBlockedIpList(ModelMap model)
   	{
    	model.addAttribute("ipBlockedList",ipAddressesService.getBlockedIpList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/updateIPGeoLocation",method=RequestMethod.POST)
   	public String updateIPGeoLocation(@RequestParam("ipAddress") String ipAddress,ModelMap model)
   	{
    	ipAddressesService.updateIPGeoLocation(ipAddress);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/checkIPAlreadyBlockedOrNot",method=RequestMethod.GET)
   	public String checkIPAlreadyBlockedOrNot(@RequestParam("ipAddress") String ipAddress,ModelMap model)
   	{
    	model.addAttribute("isBlocked",ipAddressesService.checkIPBlockedOrNot(ipAddress));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/blockOrUnblockIpAddress",method=RequestMethod.GET)
   	public String blockOrUnblockIpAddress(@RequestParam("ipAddress") String ipAddress,@RequestParam("status") Integer status,ModelMap model)
   	{
    	ipAddressesService.blockOrUnblockIpAddress(ipAddress,status);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
}
