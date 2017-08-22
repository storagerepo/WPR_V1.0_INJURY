
package com.deemsys.project.VehicleMakeAbbreviation;


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
public class VehicleMakeAbbreviationController {
	
	@Autowired
	VehicleMakeAbbreviationService vehicleMakeAbbreviationService;

    @RequestMapping(value="/getVehicleMakeAbbreviation",method=RequestMethod.GET)
	public String getVehicleMakeAbbreviation(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("vehicleMakeAbbreviationForm",vehicleMakeAbbreviationService.getVehicleMakeAbbreviation(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeVehicleMakeAbbreviation",method=RequestMethod.POST)
   	public String mergeVehicleMakeAbbreviation(@ModelAttribute("vehicleMakeAbbreviationForm") VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm,ModelMap model)
   	{
		vehicleMakeAbbreviationService.saveVehicleMakeAbbreviation(vehicleMakeAbbreviationForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateVehicleMakeAbbreviation",method=RequestMethod.POST)
   	public String saveVehicleMakeAbbreviation(@RequestParam("RequestType")Integer requestType,@RequestBody VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm,ModelMap model)
   	{
    	if(requestType==0)
    		vehicleMakeAbbreviationService.saveVehicleMakeAbbreviation(vehicleMakeAbbreviationForm);
    	else
    		vehicleMakeAbbreviationService.updateVehicleMakeAbbreviation(vehicleMakeAbbreviationForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteVehicleMakeAbbreviation",method=RequestMethod.POST)
   	public String deleteVehicleMakeAbbreviation(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	vehicleMakeAbbreviationService.deleteVehicleMakeAbbreviation(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllVehicleMakeAbbreviations",method=RequestMethod.GET)
   	public String getAllVehicleMakeAbbreviations(ModelMap model)
   	{
    	model.addAttribute("vehicleMakeAbbreviationForms",vehicleMakeAbbreviationService.getVehicleMakeAbbreviationList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getVehicleMakeAbbreviationByMake",method=RequestMethod.POST)
	public String getVehicleMakeAbbreviationByMake(@RequestParam("make") String make,ModelMap model)
	{
    	model.addAttribute("vehicleMakeAbbreviationForm",vehicleMakeAbbreviationService.getVehicleMakeAbbreviationByMake(make));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
    
    @RequestMapping(value="/checkVehicleMakeAbbreviationByMake",method=RequestMethod.POST)
   	public String checkVehicleMakeAbbreviationByMake(@RequestParam("make") String make,ModelMap model)
   	{
       	model.addAttribute("isExists",vehicleMakeAbbreviationService.checkVehicleMakeAbbreviationByMake(make));
       	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    @RequestMapping(value="/deleteVehicleMakeAbbreviationByMake",method=RequestMethod.GET)
   	public String deleteVehicleMakeAbbreviationByMake(@RequestParam("make") String make,ModelMap model)
   	{
       	model.addAttribute("isDeleted",vehicleMakeAbbreviationService.deleteVehicleMakeAbbreviationByMake(make));
       	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    @RequestMapping(value="/getVehicleMakeAbbrevationsBySearch",method=RequestMethod.POST)
    public String getVehicleMakeAbbrevationsBySearch(@RequestBody SearchVehicleMakeAbbrevationForm searchVehicleMakeAbbrevationForm,ModelMap model)
    {
    	model.addAttribute("vehicleMakeAbbreviationList",vehicleMakeAbbreviationService.getVehicleMakeAbbrevationsBySearch(searchVehicleMakeAbbrevationForm));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
    }
}
