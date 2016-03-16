package com.deemsys.project.Caller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class CallerController {

	@Autowired
	CallerService callerService;

	@RequestMapping(value = "/Admin/getCaller", method = RequestMethod.GET)
	public String getCaller(@RequestParam("id") Integer id, ModelMap model) {
		model.addAttribute("callerForm", callerService.getCaller(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/mergeCaller", method = RequestMethod.POST)
	public String mergeCaller(@RequestBody CallerForm callerForm, ModelMap model) {
		callerService.mergeCaller(callerForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/saveUpdateCaller", method = RequestMethod.POST)
	public String saveCaller(@RequestBody CallerForm callerForm, ModelMap model) {
		if (callerForm.getId() == null)
			callerService.saveCaller(callerForm);
		else
			callerService.updateCaller(callerForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/deleteCaller", method = RequestMethod.POST)
	public String deleteCaller(@RequestParam("id") Integer id, ModelMap model) {
		int i = 0;
		try {
			i = callerService.deleteCaller(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (i > 0) {
			model.addAttribute("requestSuccess", true);
			return "/returnPage";
		} else {
			model.addAttribute("requestSuccess", false);
			return "/returnPage";

		}
	}

	@RequestMapping(value = "/Admin/getAllCallers", method = RequestMethod.GET)
	public String getAllCallers(ModelMap model) {
		model.addAttribute("callerForms", callerService.getCallerList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/getNoOfCallers", method = RequestMethod.GET)
	public String getNoOfCallers(ModelMap model) {
		model.addAttribute("callerForms", callerService.getNoOfCallers());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getCurrentRole", method = RequestMethod.POST)
	public String getCurrentRole(ModelMap model, Principal principal) {

		String role = callerService.getCurrentRole();
		model.addAttribute("role", role);
		model.addAttribute("username", principal.getName());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/getCallerId", method = RequestMethod.GET)
	public String getCallerId(ModelMap model) {
		model.addAttribute("callerForms", callerService.getCallerId());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	
	@RequestMapping(value = "/Admin/getUsername", method = RequestMethod.GET)
	public String getUsername(@RequestParam("username") String username,
			ModelMap model) {
		model.addAttribute("callerForms", callerService.getUsername(username));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/getDetails", method = RequestMethod.GET)
	public String getAdminDetails(ModelMap model) {
		model.addAttribute("adminDetails", callerService.getDetails());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/getDetails", method = RequestMethod.GET)
	public String getCallerDetails(ModelMap model) {
		model.addAttribute("callerDetails", callerService.getDetails());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	public String checkPassword(
			@RequestParam("oldPassword") String oldPassword, ModelMap model) {
		if (callerService.checkPassword(oldPassword) == 1) {
			model.addAttribute("requestSuccess", true);
		} else {
			model.addAttribute("requestSuccess", false);
		}
		return "/returnPage";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(
			@RequestParam("newPassword") String newPassword, ModelMap model) {
		callerService.changePassword(newPassword);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/disableCaller", method = RequestMethod.GET)
	public String disableCaller(@RequestParam("id") Integer id, ModelMap model) {
		Integer status = callerService.disableCaller(id);
		if (status == 0) {
			model.addAttribute("enableOrDisable", 0);
			model.addAttribute("requestSuccess", true);
		} else {
			model.addAttribute("enableOrDisable", 1);
			model.addAttribute("requestSuccess", true);

		}
		return "/returnPage";
	}

	@RequestMapping(value = "/Admin/resetPassword", method = RequestMethod.GET)
	public String resetPassword(@RequestParam("id") Integer id, ModelMap model) {
		Integer status = callerService.resetPassword(id);
		if (status == 0) {
			model.addAttribute("requestSuccess", true);
		}
		return "/returnPage";
	}

}
