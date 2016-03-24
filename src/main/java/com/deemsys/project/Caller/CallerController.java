package com.deemsys.project.Caller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.PatientCallerMap.PatientCallerService;


/**
 * 
 * @author Deemsys
 * 
 */
@Controller
public class CallerController {

	@Autowired
	CallerService callerService;
	
	@Autowired
	PatientCallerService patientCallerService;

	@RequestMapping(value = "/CAdmin/getCaller", method = RequestMethod.GET)
	public String getCaller(@RequestParam("callerId") Integer callerId, ModelMap model) {
		model.addAttribute("callerForm", callerService.getCaller(callerId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/CAdmin/mergeCaller", method = RequestMethod.POST)
	public String mergeCaller(@RequestBody CallerForm callerForm, ModelMap model) {
		callerService.mergeCaller(callerForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/CAdmin/saveUpdateCaller", method = RequestMethod.POST)
	public String saveCaller(@RequestBody CallerForm callerForm, ModelMap model) {
		if (callerForm.getCallerId() == null)
			callerService.saveCaller(callerForm);
		else
			callerService.updateCaller(callerForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/CAdmin/deleteCaller", method = RequestMethod.POST)
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

	@RequestMapping(value = "/CAdmin/getAllCallers", method = RequestMethod.GET)
	public String getAllCallers(ModelMap model) {
		model.addAttribute("callerForms", callerService.getCallerList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/CAdmin/getCallersByCallerAdmin", method = RequestMethod.GET)
	public String getCallersByCallerAdmin(ModelMap model) {
		model.addAttribute("callerForms", callerService.getCallerListByCallerAdmin());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = "/CAdmin/getNumberOfCallers", method = RequestMethod.GET)
	public String getNoOfCallers(ModelMap model) {
		model.addAttribute("numberOfCallers", callerService.getNoOfCallers());
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

	@RequestMapping(value = "/CAdmin/getCallerId", method = RequestMethod.GET)
	public String getCallerId(ModelMap model) {
		model.addAttribute("callerForms", callerService.getCallerId());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	
	@RequestMapping(value = "/CAdmin/getUsername", method = RequestMethod.GET)
	public String getUsername(@RequestParam("username") String username,
			ModelMap model) {
		model.addAttribute("callerForms", callerService.getUsername(username));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/CAdmin/getDetails", method = RequestMethod.GET)
	public String getAdminDetails(ModelMap model) {
		model.addAttribute("adminDetails", callerService.getDetails());
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

	@RequestMapping(value = "/CAdmin/enableOrDisableCaller", method = RequestMethod.POST)
	public String disableCaller(@RequestParam("callerId") Integer callerId, ModelMap model) {
		callerService.enableOrDisableCaller(callerId);
		model.addAttribute("requestSuccess", true);

		return "/returnPage";
	}

	@RequestMapping(value = "/CAdmin/resetCallerPassword", method = RequestMethod.POST)
	public String resetPassword(@RequestParam("callerId") Integer callerId, ModelMap model) {
		Integer status = callerService.resetPassword(callerId);
		if (status == 0) {
			model.addAttribute("requestSuccess", true);
		}
		return "/returnPage";
	}
	
	@RequestMapping(value = "/CAdmin/assignCaller", method = RequestMethod.POST)
	public String assignCaller(@RequestBody AssignCallerForm assignCallerForm, ModelMap model) {
		boolean allow=false;
		for (CallerForm callerForm : callerService.getCallerListByCallerAdmin()) {
			if(callerForm.getCallerId()==assignCallerForm.getCallerId()){
				allow=true;
			}
		}		
		if(allow){
			model.addAttribute("success",patientCallerService.assignCaller(assignCallerForm));
		}else{
			model.addAttribute("Unautorized Action");
		}
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = {"/CAdmin/releaseCaller","/Caller/releaseCaller"},method = RequestMethod.POST)
	public String releaseCaller(@RequestBody AssignCallerForm assignCallerForm, ModelMap model) {
		model.addAttribute("success",patientCallerService.releaseCaller(assignCallerForm));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = {"/CAdmin/moveToArchive","/Caller/moveToArchive"},method = RequestMethod.POST)
	public String moveToArchive(@RequestBody AssignCallerForm assignCallerForm, ModelMap model) {
		model.addAttribute("success",patientCallerService.moveToArchive(assignCallerForm,1));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = {"/CAdmin/releaseFromArchive","/Caller/releaseFromArchive"},method = RequestMethod.POST)
	public String releaseArchive(@RequestBody AssignCallerForm assignCallerForm, ModelMap model) {
		model.addAttribute("success",patientCallerService.moveToArchive(assignCallerForm,0));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

}
