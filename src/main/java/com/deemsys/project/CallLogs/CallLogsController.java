package com.deemsys.project.CallLogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.login.LoginService;

/**
 * 
 * @author Deemsys
 * 
 */
@Controller
public class CallLogsController {

	@Autowired
	CallLogsService callLogsService;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	LoginService loginService;
	

	@RequestMapping(value = "/Caller/getCallLogs", method = RequestMethod.GET)
	public String getCallLogs(@RequestParam("callLogId") Long callLogId, ModelMap model) {
		model.addAttribute("callLogsForm",callLogsService.getCallLogs(callLogId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	
	@RequestMapping(value = "/Caller/mergeCallLogs", method = RequestMethod.POST)
	public String mergeCallLogs(@RequestBody CallLogsForm callLogsForm,
			ModelMap model) {
		callLogsService.mergeCallLogs(callLogsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/saveUpdateCallLogs", method = RequestMethod.POST)
	public String saveCallLogs(@RequestBody CallLogsForm callLogsForm,
			ModelMap model) {
		if (callLogsForm.getCallLogId() == null)
			callLogsService.saveCallLogs(callLogsForm);
		else
			callLogsService.updateCallLogs(callLogsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/deleteCallLogs", method = RequestMethod.POST)
	public String deleteCallLogs(@RequestParam("callLogsId") Long callLogsId, ModelMap model) {

		callLogsService.deleteCallLogs(callLogsId);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/getAllCallLogss", method = RequestMethod.GET)
	public String getAllCallLogss(@RequestParam("patientId") String patientId, ModelMap model) {
		model.addAttribute("callLogsForms", callLogsService.getCallLogsFormsByUser(patientId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/getCallLogsByAppointment", method = RequestMethod.GET)
	public String getCallLogsByAppointment(
			@RequestParam("appointmentId") Integer appointmentId, ModelMap model) {
		model.addAttribute("callLogsForms",
				callLogsService.getCallLogsByAppointment(appointmentId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/Caller/getCallLogsById", method = RequestMethod.POST)
	public String getCallLogsById(@RequestParam("id") Integer id, ModelMap model) {

		model.addAttribute("callLogsForms",callLogsService.getCallLogsByPatientId(id));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

}
