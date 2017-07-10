
package com.deemsys.project.VehicleMakeAbbreviation;


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
    	vehicleMakeAbbreviationService.mergeVehicleMakeAbbreviation(vehicleMakeAbbreviationForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateVehicleMakeAbbreviation",method=RequestMethod.POST)
   	public String saveVehicleMakeAbbreviation(@ModelAttribute("vehicleMakeAbbreviationForm") VehicleMakeAbbreviationForm vehicleMakeAbbreviationForm,ModelMap model)
   	{
    	if(vehicleMakeAbbreviationForm.getMake().equals(""))
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
	
}
