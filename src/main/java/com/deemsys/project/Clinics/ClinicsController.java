package com.deemsys.project.Clinics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ClinicsController {

	@Autowired
	ClinicsService clinicsService;
	
	/**
	 * Get Clinic
	 * @param clinicId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getClinic",method=RequestMethod.GET)
	public String getClinicDetails(@RequestParam("clinicId") Integer clinicId,Model model){
		ClinicsForm clinicsForm=clinicsService.getClinic(clinicId);
		model.addAttribute("clinicsForm",clinicsForm);
		model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
	/**
	 * Get All Clinics
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getAllClinics",method=RequestMethod.GET)
	public String getAllClinics(Model model){
		List<ClinicsForm> clinicsForm=clinicsService.getClinicsList();
		model.addAttribute("clinicsForm",clinicsForm);
		model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}

	/**
	 * @param clinicsForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/saveOrUpdateClinic",method=RequestMethod.POST)
	public String saveClinic(@RequestBody ClinicsForm clinicsForm,Model model){
		if(clinicsForm.getClinicId()==null)
			clinicsService.saveClinic(clinicsForm);
		else
			clinicsService.updateClinic(clinicsForm);
		
		model.addAttribute("requestSuccess",true);
		
		return "/returnPage";
	}
}
