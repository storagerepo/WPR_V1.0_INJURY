package com.deemsys.project.CrashReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.CrashReport.CrashReportForm;
import com.deemsys.project.CrashReport.CrashReportService;

@Controller
public class CrashReportController {

	@Autowired
	CrashReportService crashReportService;

    @RequestMapping(value="/Admin/getCrashReport",method=RequestMethod.GET)
	public String getCrashReport(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("crashReportForm",crashReportService.getCrashReport(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/Admin/mergeCrashReport",method=RequestMethod.POST)
   	public String mergeCrashReport(@ModelAttribute("crashReportForm") CrashReportForm crashReportForm,ModelMap model)
   	{
    	crashReportService.mergeCrashReport(crashReportForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/saveUpdateCrashReport",method=RequestMethod.POST)
   	public String saveCrashReport(@ModelAttribute("crashReportForm") CrashReportForm crashReportForm,ModelMap model)
   	{
    	if(crashReportForm.getCrashReportId().equals(""))
    		crashReportService.saveCrashReport(crashReportForm);
    	else
    		crashReportService.updateCrashReport(crashReportForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/Admin/deleteCrashReport",method=RequestMethod.POST)
   	public String deleteCrashReport(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	crashReportService.deleteCrashReport(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/getAllCrashReports",method=RequestMethod.GET)
   	public String getAllCrashReports(ModelMap model)
   	{
    	model.addAttribute("crashReportForms",crashReportService.getCrashReportList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/searchCrashReport",method=RequestMethod.POST)
	public String getCrashReportSearch(@RequestBody CrashReportSearchForm crashReportSearchForm,ModelMap model)
	{
    	model.addAttribute("searchResults",crashReportService.searchCrashReports(crashReportSearchForm));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
    
    @RequestMapping(value="/Admin/getNumberOfCrashReport",method=RequestMethod.GET)
	public String getNumberOFCrashReport(ModelMap model)
	{
    	CrashReportSearchForm crashReportSearchForm=new CrashReportSearchForm("", "", "", "",new Integer[]{}, "", "", "", 1, 10,-1);
    	model.addAttribute("numberOfCrashReports",crashReportService.searchCrashReports(crashReportSearchForm).getTotalNoOfRecords());
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    // Save Runnner Report
    @RequestMapping(value="/saveUpdateRunnerCrashReport",method=RequestMethod.POST)
   	public String saveRunnerCrashReport(@RequestBody RunnerCrashReportForm runnerCrashReportForm,ModelMap model)
   	{
    	crashReportService.saveRunnerCrashReport(runnerCrashReportForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
}
