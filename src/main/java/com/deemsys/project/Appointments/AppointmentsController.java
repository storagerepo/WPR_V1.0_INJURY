
package com.deemsys.project.Appointments;


import java.util.Date;

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
@RequestMapping("/Staff")
public class AppointmentsController {
		
	@Autowired
	AppointmentsService appointmentsService;

    @RequestMapping(value="/getAppointments",method=RequestMethod.GET)
	public String getAppointments(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("appointmentsForm",appointmentsService.getAppointments(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    @RequestMapping(value="/mergeAppointments",method=RequestMethod.POST)
   	public String mergeAppointments(@RequestBody AppointmentsForm appointmentsForm,ModelMap model)
   	{
    	appointmentsService.mergeAppointments(appointmentsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateAppointments",method=RequestMethod.POST)
   	public String saveAppointments(@RequestBody AppointmentsForm appointmentsForm,ModelMap model)
   	{
    	if(appointmentsForm.getId()==null)
    		appointmentsService.saveAppointments(appointmentsForm);
    	else
    		appointmentsService.updateAppointments(appointmentsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    @RequestMapping(value="/deleteAppointments",method=RequestMethod.POST)
   	public String deleteAppointments(@RequestParam("id") Integer id,ModelMap model)
   	{
    	appointmentsService.deleteAppointments(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllAppointmentss",method=RequestMethod.GET)
   	public String getAllAppointmentss(ModelMap model)
   	{
    	model.addAttribute("appointmentsForms",appointmentsService.getAppointmentsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllAppointmentsByPatient",method=RequestMethod.GET)
   	public String getAllAppointmentsByPatient(@RequestParam("patientId") Integer patientId,ModelMap model)
   	{
    	model.addAttribute("patientsForms",appointmentsService.getPatientDetails(patientId));
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/updateStatus",method=RequestMethod.POST)
   public String updateStatus(@RequestParam("id") Integer id,@RequestParam("status") Integer status,ModelMap model)
   	{
    	appointmentsService.updateStatu(id,status);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
   @RequestMapping(value="/todaysAppointment",method=RequestMethod.GET)
   	public String todaysAppointment(ModelMap model)
   	{
    	model.addAttribute("appointmentsForms",appointmentsService.todaysAppointment());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
   @RequestMapping(value="/getByDates",method=RequestMethod.GET)
 	public String getAppointments(@RequestParam("date") String date,ModelMap model)
 	{
     	model.addAttribute("appointmentsForms",appointmentsService.getByDates(date));
     	model.addAttribute("requestSuccess",true);
 		return "/returnPage";
 	}
   
   @RequestMapping(value="/getNoOfAppointments",method=RequestMethod.GET)
  	public String getNoOfAppointments(ModelMap model)
  	{
   	model.addAttribute("appointmentsForms",appointmentsService.getNoOfAppointments());
   	model.addAttribute("requestSuccess",true);
  		return "/returnPage";
  	}
 
   @RequestMapping(value="/getAppointmentListByStaffId",method=RequestMethod.GET)
	public String getAppointmentListByStaffId(@RequestParam("callerId") Integer staffId,ModelMap model)
	{
    	model.addAttribute("appointmentsForms",appointmentsService.getAppointmentListByStaffId(staffId));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
   
}
