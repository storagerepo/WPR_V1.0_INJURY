package com.deemsys.project.Lawyers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.DirectReportLawyerMap.DirectReportLaywerMapForm;
import com.deemsys.project.DirectReportLawyerMap.DirectReportLaywerMapService;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.CallerPatientSearchForm;
import com.deemsys.project.patient.PatientGroupedSearchResult;
import com.deemsys.project.patient.PatientService;
import com.deemsys.project.patientLawyerMap.PatientLawyerService;

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
	
	@Autowired
	DirectReportLaywerMapService directReportLaywerMapService;

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
		model.addAttribute("noOfLawyers",lawyersService.getNumberOfLawyers());
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
		// -1 is Runner Report Condition, Direct Report Status
		CallerPatientSearchForm callerPatientSearchForm=new CallerPatientSearchForm(0, new Integer[]{}, new Integer[]{}, 7, "", 0, "", "",new String[]{},"", new Integer[]{}, 0, 0, 0, "", 1, 10, "", "",0,"","",-1,new Integer[]{},-1,"","",0,null,null);
		PatientGroupedSearchResult patientSearchResult=patientService.getCurrentPatientList(callerPatientSearchForm);
		model.addAttribute("numberOfAssignedPatiets",patientSearchResult.getTotalNoOfRecord());
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	// Direct Report Move Or Release From Archive
	@RequestMapping(value = {"/LAdmin/directReportMoveOrReleaseArchive","/Lawyer/directReportMoveOrReleaseArchive"},method = RequestMethod.POST)
	public String DirectReportMoveOrReleaseArchive(@RequestBody DirectReportLaywerMapForm directReportLaywerMapForm, ModelMap model) {
		model.addAttribute("success",directReportLaywerMapService.directReportMoveOrReleaseFromArchive(directReportLaywerMapForm));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
	
	// Direct Report Change Status
	@RequestMapping(value = {"/LAdmin/directReportChangeStatus","/Lawyer/directReportChangeStatus"},method = RequestMethod.POST)
	public String DirectReportChangeStatus(@RequestBody DirectReportLaywerMapForm directReportLaywerMapForm, ModelMap model) {
		model.addAttribute("success",directReportLaywerMapService.changeStatusOfDirectReport(directReportLaywerMapForm));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}
}
