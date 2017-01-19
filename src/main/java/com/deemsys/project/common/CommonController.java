
package com.deemsys.project.common;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.identitymanagement.model.User;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapService;
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
    	if(users.getRoles().getRoleId().equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminCountyMapService.deleteCallerAdminCountyMapByCountyAndCAdminId(countyId, callerAdmin.getCallerAdminId());
    	}else if(users.getRoles().getRoleId().equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
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
    	if(users.getRoles().getRoleId().equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminCountyMapService.saveCallerAdminCountyMap(countyId, callerAdmin);
    	}else if(users.getRoles().getRoleId().equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
    		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(users.getUserId());
    		lawyerAdminCountyMappingService.saveLawyerAdminCountyMap(countyId, lawyerAdmin);
    	}
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    // enable or Disable User
    @RequestMapping(value="/enableOrDisableUser",method=RequestMethod.POST)
   	public String enableOrDisableUser(@RequestParam("customerProductToken") String customerProductToken,ModelMap model)
   	{
    	
    	Users users=loginService.getUserByProductToken(customerProductToken);
    	if(users.getRoles().getRoleId().equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)){
    		CallerAdmin callerAdmin = callerAdminService.getCallerAdminByUserId(users.getUserId());
    		callerAdminService.enableOrDisableCallerAdmin(callerAdmin.getCallerAdminId(),2);
    	}else if(users.getRoles().getRoleId().equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
    		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(users.getUserId());
    		lawyerAdminService.enableOrDisableLawyerAdmin(lawyerAdmin.getLawyerAdminId(),2);
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
    	if(currentRole.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
    		CallerAdmin callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"),currentRole, callerAdmin.getFirstName(), callerAdmin.getLastName(), callerAdmin.getPhoneNumber(), callerAdmin.getEmailAddress());
    	}else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
    		LawyerAdmin lawyerAdmin = lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"),currentRole, lawyerAdmin.getFirstName(), lawyerAdmin.getLastName(), lawyerAdmin.getPhoneNumber(), lawyerAdmin.getEmailAddress());
    	}else if(currentRole.equals(InjuryConstants.INJURY_CALLER_ROLE)){
    		Caller caller = callerService.getCallerByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"),currentRole, caller.getFirstName(), caller.getLastName(), caller.getPhoneNumber(), caller.getEmailAddress());
    	}else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
    		Lawyer lawyer = lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID());
    		currentUserDetailsForm = new CurrentUserDetailsForm(injuryProperties.getProperty("CRO_PRODUCT_ID"),currentRole, lawyer.getFirstName(), lawyer.getLastName(), lawyer.getPhoneNumber(), lawyer.getEmailAddress());
    	}
    		
    	model.addAttribute("currentUserDetailsForm",currentUserDetailsForm);
       	model.addAttribute("requestSuccess", true);
   		return "/returnPage";
   	}
}
