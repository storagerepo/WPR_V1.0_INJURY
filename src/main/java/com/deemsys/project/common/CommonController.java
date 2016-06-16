
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
    	model.addAttribute("Success",true);
		return "/index";
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
    
    @RequestMapping(value="/marketing",method=RequestMethod.GET)
   	public String marketing(ModelMap model)
   	{
       	model.addAttribute("Success",true);
   		return "/marketing";
   	}
    
}
