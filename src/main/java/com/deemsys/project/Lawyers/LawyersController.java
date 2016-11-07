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
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.CallerPatientSearchForm;
import com.deemsys.project.patient.PatientSearchResult;
import com.deemsys.project.patient.PatientService;
import com.deemsys.project.patientLawyerMap.PatientLawyerService;
import com.deemsys.project.Caller.AssignCallerForm;
import com.deemsys.project.Caller.CallerForm;
import com.deemsys.project.Caller.CallerService;

@Controller
public class LawyersController {

	@Autowired
	LawyersService lawyersService;

	@Autowired
	CallerService callerService;

	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	PatientLawyerService patientLawyerService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	PatientService patientService;

	@RequestMapping(value = "/LAdmin/getLawyers", method = RequestMethod.GET)
	public String getLawyers(@RequestParam("lawyerId") Integer lawyerId, ModelMap model) {
		model.addAttribute("lawyersForm", lawyersService.getLawyers(lawyerId));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/mergeLawyers", method = RequestMethod.POST)
	public String mergeLawyers(@RequestBody LawyersForm lawyersForm,
			ModelMap model) {
		lawyersService.mergeLawyers(lawyersForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/saveUpdateLawyers", method = RequestMethod.POST)
	public String saveLawyers(@RequestBody LawyersForm lawyersForm,
			ModelMap model) {
		if (lawyersForm.getLawyerId() == null)
			lawyersService.saveLawyers(lawyersForm);
		else
			lawyersService.updateLawyers(lawyersForm);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/deleteLawyers", method = RequestMethod.POST)
	public String deleteLawyers(@RequestParam("lawyerId") Integer lawyerId,
			ModelMap model) {

		lawyersService.deleteLawyers(lawyerId);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/getAllLawyerss", method = RequestMethod.GET)
	public String getAllLawyerss(ModelMap model) {
		model.addAttribute("lawyersForms", lawyersService.getLawyersList());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/getLawyersByLawyerAdmin", method = RequestMethod.GET)
	public String getLawyersByLawyerAdmin(ModelMap model) {
		Integer currentUserId = callerService.getCurrentUserId();
		model.addAttribute("lawyersForms",
				lawyersService.getLawyersListByLawyerAdmin(lawyerAdminService
						.getLawyerAdminIdByUserId(currentUserId).getLawyerAdminId()));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = "/LAdmin/getLawyersForAssign", method = RequestMethod.GET)
	public String getLawyersForAssign(ModelMap model) {
		Integer currentUserId = callerService.getCurrentUserId();
		model.addAttribute("lawyersForms",lawyersService.getLawyersListForAssign(lawyerAdminService
						.getLawyerAdminIdByUserId(currentUserId).getLawyerAdminId()));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/getNumberOfLawyers", method = RequestMethod.GET)
	public String getNoOfLawyerAdmin(ModelMap model) {
		Integer currentUserId = callerService.getCurrentUserId();
		model.addAttribute("noOfLawyers",lawyersService.getNumberOfLawyers(lawyerAdminService.getLawyerAdminIdByUserId(currentUserId).getLawyerAdminId()));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/enableOrDisableLawyers", method = RequestMethod.POST)
	public String enableDisableLawyers(
			@RequestParam("lawyerId") Integer lawyerId, ModelMap model) {

		lawyersService.enableOrDisabelLawyer(lawyerId);
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

	@RequestMapping(value = "/LAdmin/resetLawyerPassword", method = RequestMethod.POST)
	public String resetLawyerPassword(@RequestParam("lawyerId") Integer lawyerId, ModelMap model) {
		Integer status = lawyersService.resetLawyerPassword(lawyerId);
		if (status == 0) {
			model.addAttribute("requestSuccess", true);
		} else {
			model.addAttribute("requestSuccess", false);
		}
		return "/returnPage";
	}
	
	@RequestMapping(value = "/LAdmin/assignLawyer", method = RequestMethod.POST)
	public String assignLawyer(@RequestBody AssignLawyerForm assignLawyerForm, ModelMap model) {
		boolean allow=false;
		Integer lawyerAdminId=lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID()).getLawyerAdminId();
		for (LawyersForm lawyersForm : lawyersService.getLawyersListByLawyerAdmin(lawyerAdminId)) {
			if(lawyersForm.getLawyerId()==assignLawyerForm.getLawyerId()){
				allow=true;
			}
		}		
		if(allow){
			model.addAttribute("success",patientLawyerService.assignLawyer(assignLawyerForm));
		}else{
			model.addAttribute("Unautorized Action");
		}
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = {"/LAdmin/releaseLawyer","/Lawyer/releaseLawyer"},method = RequestMethod.POST)
	public String releaseLawyer(@RequestBody AssignLawyerForm assignLawyerForm, ModelMap model) {
		model.addAttribute("success",patientLawyerService.releaseLawyer(assignLawyerForm));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = {"/LAdmin/moveToArchive","/Lawyer/moveToArchive"},method = RequestMethod.POST)
	public String moveToArchive(@RequestBody AssignLawyerForm assignLawyerForm, ModelMap model) {
		model.addAttribute("success",patientLawyerService.moveToArchive(assignLawyerForm,1));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = {"/LAdmin/releaseFromArchive","/Lawyer/releaseFromArchive"},method = RequestMethod.POST)
	public String releaseArchive(@RequestBody AssignLawyerForm assignLawyerForm, ModelMap model) {
		model.addAttribute("success",patientLawyerService.moveToArchive(assignLawyerForm,0));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	@RequestMapping(value = "/Lawyer/getNumberOfAssignedPatients", method = RequestMethod.GET)
	public String getNumberOfAssignedPatientsForLawyer(ModelMap model) {
		CallerPatientSearchForm callerPatientSearchForm=new CallerPatientSearchForm(0, 0, 0, 7, "", 0, "", "", "",3, 0, 0, 0, "", 1, 10, "", "",0);
		PatientSearchResult patientSearchResult=patientService.getCurrentPatientList(callerPatientSearchForm);
		model.addAttribute("numberOfAssignedPatiets",patientSearchResult.getTotalNoOfRecord());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
}
