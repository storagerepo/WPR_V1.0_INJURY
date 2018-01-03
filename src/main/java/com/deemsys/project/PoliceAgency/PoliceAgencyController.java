
package com.deemsys.project.PoliceAgency;

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
public class PoliceAgencyController {

	@Autowired
	PoliceAgencyService policeAgencyService;

	@RequestMapping(value = "/getPoliceAgency", method = RequestMethod.GET)
	public String getPoliceAgency(@RequestParam("mapId") Integer mapId,ModelMap model) {
		
		model.addAttribute("policeAgencyForm", policeAgencyService.getPoliceAgency(mapId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/mergePoliceAgency", method = RequestMethod.POST)
	public String mergePoliceAgency(@ModelAttribute("policeAgencyForm") PoliceAgencyForm policeAgencyForm,
			ModelMap model) {
		policeAgencyService.mergePoliceAgency(policeAgencyForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/saveUpdatePoliceAgency", method = RequestMethod.POST)
	public String savePoliceAgency(@RequestBody PoliceAgencyForm policeAgencyForm, ModelMap model) {
		if (policeAgencyForm.getMapId() == null)
			policeAgencyService.savePoliceAgency(policeAgencyForm);
		else
			policeAgencyService.updatePoliceAgency(policeAgencyForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/deletePoliceAgency", method = RequestMethod.GET)
	public String deletePoliceAgency(@RequestParam("mapId") Integer id, ModelMap model) {

		model.addAttribute("status", policeAgencyService.deletePoliceAgency(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}


	@RequestMapping(value = "/getAllPoliceAgencys", method = RequestMethod.GET)
	public String getAllPoliceAgencys(ModelMap model) {
		model.addAttribute("policeAgencyForms", policeAgencyService.getPoliceAgencyList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getPoliceAgenciesByStatus", method = RequestMethod.GET)
	public String getPoliceAgencyiesByStatus(@RequestParam("status") Integer status, ModelMap model) {
		model.addAttribute("policeAgencyForms", policeAgencyService.getPoliceAgenciesByStatus(status));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/searchPoliceDepartments", method = RequestMethod.GET)
	public String searchPoliceDepartments(@RequestParam("countyParam") Integer countyParam,
			@RequestParam("reportPullingTypeParam") Integer reportPullingTypeParam,@RequestParam("reportStatus") Integer reportStatus, ModelMap model) {
		model.addAttribute("policeAgencyForms",
				policeAgencyService.searchPoliceDepartments(countyParam, reportPullingTypeParam,reportStatus));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
}
