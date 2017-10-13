
package com.deemsys.project.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deemsys.project.BackupReportsAndPatients.BackupReportsAndPatientsService;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminForm;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapService;
import com.deemsys.project.CrashReport.PoliceDepartmentRunnerDirectReports;
import com.deemsys.project.Export.PrintPDFFiles;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.LawyerAdminCountyMapping.LawyerAdminCountyMappingService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Lawyer;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;


/**
 * 
 * @author Deemsys
 *
 */


@Controller
public class CommonController {

	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CallerAdminCountyMapService callerAdminCountyMapService;
	
	@Autowired
	LawyerAdminCountyMappingService lawyerAdminCountyMappingService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	LawyersService lawyersService;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	PrintPDFFiles printPDFFiles;
	
	@Autowired
	BackupReportsAndPatientsService backupReportsAndPatientsService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getInit(ModelMap model)
	{
    	model.addAttribute("Success",true);
		return "/login";
	}
    
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String getIndex(ModelMap model)
	{
    	model.addAttribute("Success",true);
		return "/login";
	}
	
    @RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public String getDashboard(ModelMap model)
	{
    	Integer disclaimerStatus=loginService.checkDisclaimerAcceptedStatus();
    	model.addAttribute("Success",true);
    	if(disclaimerStatus==0){
    		return "/disclaimer";
    	}else{
    		return "/index";
    	}
	}
	
    @RequestMapping(value="/login-failed",method=RequestMethod.GET)
	public String getLogin(ModelMap model)
	{
    	model.addAttribute("failed",true);
		return "/login";
	}
    
    @RequestMapping(value="/logout",method=RequestMethod.GET)
   	public String logout(ModelMap model)
   	{
       	model.addAttribute("Success",true);
   		return "/login";
   	}
    
    @RequestMapping(value = {"/checkUserNameExist"}, method = RequestMethod.GET)
	public String getUsername(@RequestParam("username") String username,
			ModelMap model) {
		model.addAttribute("status",loginService.checkUsernameExist(username));
		model.addAttribute("requestSuccess", true);
		return "/returnPage";
	}

    @RequestMapping(value = {"/checkPasswordChangedStatus"}, method = RequestMethod.GET)
   	public String checkPasswordChangedStatus(ModelMap model) {
   		model.addAttribute("status",loginService.checkPasswordChangedStatus());
   		model.addAttribute("requestSuccess", true);
   		return "/returnPage";
   	}
   
    // Update Disclaimer Status
    @RequestMapping(value="/updateDisclaimerStatus",method=RequestMethod.POST)
	public String updateDisclaimerStatus(ModelMap model)
	{
    	loginService.updateDisclaimerAcceptedStatus();
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
    // Get Product Token
    @RequestMapping(value = {"/getProductToken"}, method = RequestMethod.GET)
   	public String getProductToken(ModelMap model) {
    	Users users = loginService.getProductToken();
    	if(users!=null){
    		model.addAttribute("productToken",users.getProductToken());
       		model.addAttribute("isPrivilegedUser",users.getIsPrivilegedUser());
    	}else{
    		model.addAttribute("productToken","");
       		model.addAttribute("isPrivilegedUser","");
    	}
    	model.addAttribute("requestSuccess", true);
   		return "/returnPage";
   	}
    
    // Delete County Map
    @RequestMapping(value="/deleteCountyMap",method=RequestMethod.POST)
   	public String deleteCountyMap(@RequestParam("customerProductToken") String customerProductToken,@RequestParam("countyId") Integer countyId,ModelMap model)
   	{
    	
    	Users users=loginService.getUserByProductToken(customerProductToken);
    	Integer roleId=users.getRoles().getRoleId();
    	if(roleId.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)||roleId.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminCountyMapService.deleteCallerAdminCountyMapByCountyAndCAdminId(countyId, callerAdmin.getCallerAdminId());
    	}else if(roleId.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
    		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(users.getUserId());
    		lawyerAdminCountyMappingService.deleteLawyerAdminCountyMapByCountyAndLAdminId(countyId, lawyerAdmin.getLawyerAdminId());
    	}
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    // Save County Map
    @RequestMapping(value="/saveCountyMap",method=RequestMethod.POST)
   	public String saveCountyMap(@RequestParam("customerProductToken") String customerProductToken,@RequestParam("countyId") Integer countyId,ModelMap model)
   	{
    	
    	Users users=loginService.getUserByProductToken(customerProductToken);
    	Integer roleId=users.getRoles().getRoleId();
    	if(roleId.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)||roleId.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminCountyMapService.saveCallerAdminCountyMap(countyId, callerAdmin);
    	}else if(roleId.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
    		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(users.getUserId());
    		lawyerAdminCountyMappingService.saveLawyerAdminCountyMap(countyId, lawyerAdmin);
    	}
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
    // Reset County Map
    @RequestMapping(value="/resetCountyMap",method=RequestMethod.POST)
   	public String resetCountyMap(@RequestBody CallerAdminForm callerAdminForm,ModelMap model)
   	{
    	
    	Users users=loginService.getUserByProductToken(callerAdminForm.getProductToken());
    	Integer roleId=users.getRoles().getRoleId();
    	if(roleId.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)||roleId.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminCountyMapService.deleteCallerAdminCountyMapByCAdminId(callerAdmin.getCallerAdminId());
    		for (Integer countyId : callerAdminForm.getCounty()) {
    			callerAdminCountyMapService.saveCallerAdminCountyMap(countyId, callerAdmin);
			}
    	}else if(roleId.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
    		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(users.getUserId());
    		lawyerAdminCountyMappingService.deleteLawyerAdminCountyMapByLAdminId(lawyerAdmin.getLawyerAdminId());
    		for (Integer countyId : callerAdminForm.getCounty()) {
    			lawyerAdminCountyMappingService.saveLawyerAdminCountyMap(countyId, lawyerAdmin);
    		}
    		
    	}
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    // enable or Disable User
    @RequestMapping(value="/enableOrDisableUser",method=RequestMethod.POST)
   	public String enableOrDisableUser(@RequestParam("customerProductToken") String customerProductToken,@RequestParam("status") Integer status,ModelMap model)
   	{
    	
    	Users users=loginService.getUserByProductToken(customerProductToken);
    	Integer roleId=users.getRoles().getRoleId();
    	if(roleId.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)||roleId.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminService.enableOrDisableCallerAdminAndCallers(callerAdmin.getCallerAdminId(),status);
    	}else if(roleId.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
    		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(users.getUserId());
    		lawyerAdminService.enableOrDisableLawyerAdminAndLawyers(lawyerAdmin.getLawyerAdminId(),status);
    	}
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    // enable or Disable User
    @RequestMapping(value="/changePrivilegedUserStatus",method=RequestMethod.POST)
   	public String changePrivilegedUserStatus(@RequestParam("customerProductToken") String customerProductToken,@RequestParam("privilegedStatus") Integer privilegedStatus,ModelMap model)
   	{
    	
    	loginService.changePrivilegedUserStatus(customerProductToken,privilegedStatus);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    // Get Current User Details
    @RequestMapping(value = {"/getCurrentUserDetails"}, method = RequestMethod.GET)
   	public String getCurrentUserDetails(ModelMap model) {
    	String currentRole=loginService.getCurrentRole();
    	CurrentUserDetailsForm currentUserDetailsForm = new CurrentUserDetailsForm();
    	if(currentRole.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||currentRole.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
    		CallerAdmin callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"), InjuryConstants.getRoleAsText(currentRole), callerAdmin.getFirstName(), callerAdmin.getLastName(), callerAdmin.getPhoneNumber(), callerAdmin.getEmailAddress());
    	}else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
    		LawyerAdmin lawyerAdmin = lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"), InjuryConstants.getRoleAsText(currentRole), lawyerAdmin.getFirstName(), lawyerAdmin.getLastName(), lawyerAdmin.getPhoneNumber(), lawyerAdmin.getEmailAddress());
    	}else if(currentRole.equals(InjuryConstants.INJURY_CALLER_ROLE)||currentRole.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)){
    		Caller caller = callerService.getCallerByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"), InjuryConstants.getRoleAsText(currentRole), caller.getFirstName(), caller.getLastName(), caller.getPhoneNumber(), caller.getEmailAddress());
    	}else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
    		Lawyer lawyer = lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"), InjuryConstants.getRoleAsText(currentRole), lawyer.getFirstName(), lawyer.getLastName(), lawyer.getPhoneNumber(), lawyer.getEmailAddress());
    	}
    		
    	model.addAttribute("currentUserDetailsForm",currentUserDetailsForm);
       	model.addAttribute("requestSuccess", true);
   		return "/returnPage";
   	}
    
    // Back Up Old Reports By Limit
    @RequestMapping(value="/backupOldReportsData",method=RequestMethod.POST)
   	public String backupOldReportsData(@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate,@RequestParam("noOfRecords") Integer noOfRecords, @RequestParam("crashId") String crashId,ModelMap model)
   	{
    	backupReportsAndPatientsService.backupSixMonthOldReportsDataByStoredProcedure(fromDate, toDate);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    
}
