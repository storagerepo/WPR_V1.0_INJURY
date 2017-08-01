
package com.deemsys.project.ReportingAgency;


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
@RequestMapping("/Patient")
public class ReportingAgencyController {
	
	@Autowired
	ReportingAgencyService reportingAgencyService;

    @RequestMapping(value="/getReportingAgency",method=RequestMethod.GET)
	public String getReportingAgency(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("reportingAgencyForm",reportingAgencyService.getReportingAgency(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeReportingAgency",method=RequestMethod.POST)
   	public String mergeReportingAgency(@ModelAttribute("reportingAgencyForm") ReportingAgencyForm reportingAgencyForm,ModelMap model)
   	{
    	reportingAgencyService.mergeReportingAgency(reportingAgencyForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateReportingAgency",method=RequestMethod.POST)
   	public String saveReportingAgency(@ModelAttribute("reportingAgencyForm") ReportingAgencyForm reportingAgencyForm,ModelMap model)
   	{
    	reportingAgencyService.saveReportingAgency(reportingAgencyForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteReportingAgency",method=RequestMethod.POST)
   	public String deleteReportingAgency(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	reportingAgencyService.deleteReportingAgency(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllReportingAgencys",method=RequestMethod.GET)
   	public String getAllReportingAgencys(ModelMap model)
   	{
    	model.addAttribute("reportingAgencyForms",reportingAgencyService.getReportingAgencyList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getReportingAgencyByCounties",method=RequestMethod.GET)
   	public String getReportingAgencyByCounties(@RequestParam("countyIds") Integer[] countyIds,ModelMap model)
   	{
    	model.addAttribute("reportingAgencyForms",reportingAgencyService.getReportingAgencyByCounties(countyIds));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    // Reporting Agency List By Preference Type
    @RequestMapping(value="/getReportingAgencyByCountiesAndPreference",method=RequestMethod.GET)
   	public String getReportingAgencyByCountiesAndPreference(@RequestParam("countyIds") Integer[] countyIds,@RequestParam("agencyPreferenceType") Integer agencyPreferenceType,ModelMap model)
   	{
    	model.addAttribute("reportingAgencyForms",reportingAgencyService.getReportingAgencyByCountiesAndPreference(countyIds, agencyPreferenceType));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
}
