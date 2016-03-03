package com.deemsys.project.Lawyers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersForm;
import com.deemsys.project.Staff.StaffService;

@Controller
@RequestMapping("/Lawyer")
public class LawyersController {

	@Autowired
	LawyersService lawyersService;

	@Autowired
	StaffService staffService;

	@Autowired
	LawyerAdminService lawyerAdminService;

	@RequestMapping(value = "/getLawyers", method = RequestMethod.GET)
	public String getLawyers(@RequestParam("id") Integer id, ModelMap model) {
		model.addAttribute("lawyersForm", lawyersService.getLawyers(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/mergeLawyers", method = RequestMethod.POST)
	public String mergeLawyers(@RequestBody LawyersForm lawyersForm,
			ModelMap model) {
		lawyersService.mergeLawyers(lawyersForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/saveUpdateLawyers", method = RequestMethod.POST)
	public String saveLawyers(@RequestBody LawyersForm lawyersForm,
			ModelMap model) {
		if (lawyersForm.getId() == null)
			lawyersService.saveLawyers(lawyersForm);
		else
			lawyersService.updateLawyers(lawyersForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/deleteLawyers", method = RequestMethod.POST)
	public String deleteLawyers(@RequestParam("lawyerId") Integer lawyerId, ModelMap model) {

		lawyersService.deleteLawyers(lawyerId);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getAllLawyerss", method = RequestMethod.GET)
	public String getAllLawyerss(ModelMap model) {
		model.addAttribute("lawyersForms", lawyersService.getLawyersList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getLawyersByLawyerAdmin", method = RequestMethod.GET)
	public String getLawyersByLawyerAdmin(ModelMap model) {
		Integer currentUserId = staffService.getCurrentUserId();
		Integer lawyerAdminId = lawyerAdminService
				.getLawyerAdminIdByUserId(currentUserId);
		model.addAttribute("lawyersForms",
				lawyersService.getLawyersListByLawyerAdmin(lawyerAdminId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getNoOfLawyers", method = RequestMethod.GET)
	public String getNoOfLawyerAdmin(ModelMap model) {
		Integer currentUserId = staffService.getCurrentUserId();
		Integer lawyerAdminId = lawyerAdminService
				.getLawyerAdminIdByUserId(currentUserId);
		model.addAttribute("noOfLawyers",
				lawyersService.getNumberOfLawyers(lawyerAdminId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/enableDisableLawyers", method = RequestMethod.GET)
	public String enableDisableLawyers(
			@RequestParam("lawyerId") Integer lawyerId, ModelMap model) {

		lawyersService.enableOrDisabelLawyer(lawyerId);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/resetLawyerPassword", method = RequestMethod.GET)
	public String resetLawyerPassword(@RequestParam("lawyerId") Integer lawyerId,ModelMap model) {
		Integer status = lawyersService.resetLawyerPassword(lawyerId);
		if (status == 0) {
			model.addAttribute("requestSuccess", true);
		} else {
			model.addAttribute("requestSuccess", false);
		}
		return "/returnPage";
	}
}
