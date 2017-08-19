
package com.deemsys.project.ReportingAgency;


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
@RequestMapping("/Patient")
public class ReportingAgencyController {
	
	@Autowired
	ReportingAgencyService reportingAgencyService;

    @RequestMapping(value="/getReportingAgency",method=RequestMethod.GET)
	public String getReportingAgency(@RequestParam("reportingAgencyId") Integer id,ModelMap model)
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
   	public String saveReportingAgency(@RequestBody ReportingAgencyForm reportingAgencyForm,ModelMap model)
   	{
    	if(reportingAgencyForm.getReportingAgencyId()==0)
    	{
    		reportingAgencyService.saveReportingAgency(reportingAgencyForm);
    	}
    	else
    	{
    		reportingAgencyService.updateReportingAgency(reportingAgencyForm);

    	}
    	
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteReportingAgency",method=RequestMethod.GET)
   	public String deleteReportingAgency(@RequestParam("reportingAgencyId") Integer reportingAgencyId,ModelMap model)
   	{
    	
    	
    	model.addAttribute("requestSuccess",true);
    	model.addAttribute("status",reportingAgencyService.deleteReportingAgency(reportingAgencyId));
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
    
    @RequestMapping(value="/getReportingAgencyList2",method=RequestMethod.POST)
    public String getReportingAgencyList2(@RequestBody SearchParamForm searchParamForm,ModelMap model)
    {
    	model.addAttribute("reportingAgencyList", reportingAgencyService.getReportingAgencyList2(searchParamForm));
    	model.addAttribute("requestSuccess", true);
    	return "/returnPage";
    }
    
    @RequestMapping(value="/checkNcicCode",method=RequestMethod.POST)
    public String checkNcicCode(@RequestParam("nciccode") String ncicCode,@RequestParam("id") Integer reportingAgencyId,@RequestParam("countyId") Integer countyId, ModelMap model)
    {
    	model.addAttribute("result", reportingAgencyService.checkNcicCode(ncicCode, reportingAgencyId,countyId));
    	model.addAttribute("requestSuccess", true);
    	return "/returnPage";
    }
    
    @RequestMapping(value="/enableDisableReportingAgency",method=RequestMethod.GET)
    public String enableDisableReportingAgency(@RequestParam("reportingAgencyId") Integer reportingAgencyId,ModelMap model)
    {
    	model.addAttribute("result", reportingAgencyService.enableDisableReportingAgency(reportingAgencyId));
    	model.addAttribute("requestSuccess", true);
    	return "/returnPage";
    }
}
