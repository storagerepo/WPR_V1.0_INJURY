package com.deemsys.project.Appointments;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Deemsyas
 * 
 */
@Controller
@RequestMapping("/Caller")
public class AppointmentsController {

	@Autowired
	AppointmentsService appointmentsService;

	@RequestMapping(value = "/getAppointments", method = RequestMethod.GET)
	public String getAppointments(@RequestParam("appointmentId") Long appointmentId, ModelMap model) {
		model.addAttribute("appointmentsForm",
				appointmentsService.getAppointmentsByWithFullDetails(appointmentId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/mergeAppointments", method = RequestMethod.POST)
	public String mergeAppointments(
			@RequestBody AppointmentsForm appointmentsForm, ModelMap model) {
		appointmentsService.mergeAppointments(appointmentsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/saveUpdateAppointments", method = RequestMethod.POST)
	public String saveAppointments(
			@RequestBody AppointmentsForm appointmentsForm, ModelMap model) {
		if (appointmentsForm.getId() == null)
			appointmentsService.saveAppointments(appointmentsForm);
		else
			appointmentsService.updateAppointments(appointmentsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/deleteAppointments", method = RequestMethod.POST)
	public String deleteAppointments(@RequestParam("id") Integer id,
			ModelMap model) {
		appointmentsService.deleteAppointments(id);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getAllAppointmentss", method = RequestMethod.GET)
	public String getAllAppointmentss(ModelMap model) {
		model.addAttribute("appointmentsForms",
				appointmentsService.getAppointmentsList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = "/searchAppointments", method = RequestMethod.POST)
	public String searchAppointment(@RequestBody AppointmentSearchForm appointmentSearchForm, ModelMap model) {
		model.addAttribute("appointmentsSearchRessult",appointmentsService.searchAppointments(appointmentSearchForm));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getNumberOfAppointments", method = RequestMethod.GET)
	public String getNoOfAppointments(ModelMap model) {
		model.addAttribute("numberOfAppointments",appointmentsService.getNoOfAppointments());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/getAppointmentListByStaffId", method = RequestMethod.GET)
	public String getAppointmentListByStaffId(
			@RequestParam("callerId") Integer staffId, ModelMap model) {
		model.addAttribute("appointmentsForms",
				appointmentsService.getAppointmentListByCallerId(staffId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/removeAppointment", method = RequestMethod.POST)
	public String removeAppointment(
			@RequestParam("appointmentId") Integer appointmentId, ModelMap model) {
		Integer status = appointmentsService.removeAppointment(appointmentId);
		if (status > 0) {
			model.addAttribute("requestSuccess", true);
		} else {
			model.addAttribute("requestSuccess", false);
		}
		return "/returnPage";
	}
	
	@RequestMapping(value = "/changeAppointmentStatus", method = RequestMethod.POST)
	public String changeAppointmentStatus(
			@RequestParam("appointmentId") Long appointmentId,@RequestParam("status") Integer status, ModelMap model) {
		appointmentsService.changeAppointmentStatus(appointmentId, status);
		model.addAttribute("requestSuccess", true);
		
		return "/returnPage";
	}
}
