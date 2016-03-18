package com.deemsys.project.LawyerAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.LawyerAdmin.LawyerAdminForm;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Caller.CallerService;

@Controller
public class LawyerAdminController {

	@Autowired
	LawyerAdminService lawyerAdminService;

	@Autowired
	CallerService callerService;

	@RequestMapping(value = "/Admin/getLawyerAdmin", method = RequestMethod.GET)
	public String getLawyerAdmin(@RequestParam("lawyerAdminId") Integer lawyerAdminId, ModelMap model) {
		model.addAttribute("lawyerAdminForm",
				lawyerAdminService.getLawyerAdmin(lawyerAdminId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/mergeLawyerAdmin", method = RequestMethod.POST)
	public String mergeLawyerAdmin(
			@RequestBody LawyerAdminForm lawyerAdminForm, ModelMap model) {
		lawyerAdminService.mergeLawyerAdmin(lawyerAdminForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/saveUpdateLawyerAdmin", method = RequestMethod.POST)
	public String saveLawyerAdmin(@RequestBody LawyerAdminForm lawyerAdminForm,
			ModelMap model) {
		if (lawyerAdminForm.getLawyerAdminId() == null)
			lawyerAdminService.saveLawyerAdmin(lawyerAdminForm);
		else
			lawyerAdminService.updateLawyerAdmin(lawyerAdminForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/enableOrDisableLawyerAdmin", method = RequestMethod.POST)
	public String enableOrDisableLawyerAdmin(@RequestParam("lawyerAdminId") Integer lawyerAdminId,
			ModelMap model) {

		lawyerAdminService.enableOrDisableLawyerAdmin(lawyerAdminId);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = "/Admin/resetLawyerAdminPassword", method = RequestMethod.POST)
	public String resetLawyerAdminPassword(@RequestParam("lawyerAdminId") Integer lawyerAdminId,
			ModelMap model) {
		
		lawyerAdminService.resetLawyerAdminPassword(lawyerAdminId);
		model.addAttribute("requestSuccess", true);
		
		return "/returnPage";
	}

	
	
	@RequestMapping(value = "/Admin/deleteLawyerAdmin", method = RequestMethod.POST)
	public String deleteLawyerAdmin(@RequestParam("id") Integer id,
			ModelMap model) {

		lawyerAdminService.deleteLawyerAdmin(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/getAllLawyerAdmins", method = RequestMethod.GET)
	public String getAllLawyerAdmins(ModelMap model) {
		model.addAttribute("lawyerAdminForms",
				lawyerAdminService.getLawyerAdminList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/getNoOfLawyerAdmin", method = RequestMethod.GET)
	public String getNoOfLawyerAdmin(ModelMap model) {
		model.addAttribute("noOfLawyerAdmin",
				lawyerAdminService.getNumberOfLawyerAdmin());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	// CheckUserName Exists
	@RequestMapping(value = { "/Admin/checkUsernameExist",
			"/Lawyer/checkUsernameExist" }, method = RequestMethod.GET)
	public String getUsername(@RequestParam("username") String username,
			ModelMap model) {
		model.addAttribute("isUserNameExist",
				callerService.getUsername(username));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}


}
