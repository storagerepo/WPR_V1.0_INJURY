package com.deemsys.project.County;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.County.CountyForm;
import com.deemsys.project.County.CountyService;

@Controller
@RequestMapping()
public class CountyController {

	@Autowired
	CountyService countyService;

	@RequestMapping(value = "/Admin/getCounty", method = RequestMethod.GET)
	public String getCounty(@RequestParam("id") Integer id, ModelMap model) {
		model.addAttribute("countyForm", countyService.getCounty(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/mergeCounty", method = RequestMethod.POST)
	public String mergeCounty(@RequestBody CountyForm countyForm, ModelMap model) {
		countyService.mergeCounty(countyForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/saveUpdateCounty", method = RequestMethod.POST)
	public String saveCounty(@RequestBody CountyForm countyForm, ModelMap model) {
		if (countyForm.getId() == null)
			countyService.saveCounty(countyForm);
		else
			countyService.updateCounty(countyForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/deleteCounty", method = RequestMethod.POST)
	public String deleteCounty(@RequestParam("id") Integer id, ModelMap model) {

		countyService.deleteCounty(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = { "/Admin/getAllCountys", "/Lawyer/getAllCountys","/Caller/getAllCountys" }, method = RequestMethod.GET)
	public String getAllCountys(ModelMap model) {
		model.addAttribute("countyForms", countyService.getCountyList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = { "/Patient/getMyCounties", "/Lawyer/getMyCounties","/LAdmin/getMyCounties","/CAdmin/getMyCounties","/Caller/getMyCounties" }, method = RequestMethod.GET)
	public String getMyCounties(ModelMap model) {
		
		model.addAttribute("countyList",countyService.getMyCountyList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	

}
