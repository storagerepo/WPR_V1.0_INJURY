
package com.deemsys.project.common;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @RequestMapping(value = {"api/checkPasswordChangedStatus"}, method = RequestMethod.GET)
   	public String testIPRestriction(ModelMap model) {
   		//model.addAttribute("status",loginService.checkPasswordChangedStatus());
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
}
