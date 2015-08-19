
package com.deemsys.project.common;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Deemsys
 *
 */


@Controller
public class CommonController {

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
}
