package com.deemsys.project.Clinics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/CAdmin")
public class ClinicsController {

	@Autowired
	ClinicsService clinicsService;

	/**
	 * Get Clinic For Edit
	 * 
	 * @param clinicId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getClinic", method = RequestMethod.GET)
	public String getClinicDetails(@RequestParam("clinicId") Integer clinicId,
			Model model) {
		ClinicsForm clinicsForm = clinicsService.getClinic(clinicId);
		model.addAttribute("clinicsForm", clinicsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	/**
	 * Get Clinic For View Only
	 * 
	 * @param clinicId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getClinicDetails", method = RequestMethod.GET)
	public String getOneClinicDetails(
			@RequestParam("clinicId") Integer clinicId, Model model) {
		ClinicsForm clinicsForm = clinicsService.getClinicDetails(clinicId);
		model.addAttribute("clinicsForm", clinicsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	/**
	 * Get All Clinics
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getAllClinics", method = RequestMethod.GET)
	public String getAllClinics(Model model) {
		List<ClinicsForm> clinicsForm = clinicsService.getClinicsList();
		model.addAttribute("clinicsForm", clinicsForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	/**
	 * @param clinicsForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateClinic", method = RequestMethod.POST)
	public String saveClinic(@RequestBody ClinicsForm clinicsForm, Model model) {
		if (clinicsForm.getClinicId() == null)
			clinicsService.saveClinic(clinicsForm);
		else
			clinicsService.updateClinic(clinicsForm);

		model.addAttribute("requestSuccess", true);

		return "/returnPage";
	}

	/**
	 * Delete Clinic
	 * 
	 * @param clinicId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteClinic", method = RequestMethod.POST)
	public String deleteClinic(@RequestParam("clinicId") Integer clinicId,
			Model model) {

		Integer status = clinicsService.deleteClinic(clinicId);
		if (status == 0) {
			model.addAttribute("requestSuccess", false);
		} else {
			model.addAttribute("requestSuccess", true);
		}
		return "/returnPage";
	}

	@RequestMapping(value="/enableOrDisableClinic",method=RequestMethod.POST)
   	public String enableOrDisableClinic(@RequestParam("clinicId") Integer clinicId,ModelMap model)
   	{
    	clinicsService.enableOrDisableClinic(clinicId);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
	@RequestMapping(value = "/getClinicId", method = RequestMethod.GET)
	public String getClinicId(ModelMap model) {
		model.addAttribute("clinicsForms", clinicsService.getClinicId());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getNumberOfClinics", method = RequestMethod.GET)
	public String getNoOfClinics(Model model) {

		model.addAttribute("NoOfClinics", clinicsService.getNoOfClinics());
		return "/retuenPage";
	}

}
