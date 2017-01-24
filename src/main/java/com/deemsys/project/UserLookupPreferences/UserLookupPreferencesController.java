
package com.deemsys.project.UserLookupPreferences;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class UserLookupPreferencesController {
	
	@Autowired
	UserLookupPreferencesService userLookupPreferencesService;

    @RequestMapping(value={"Patient/getUserLookupPreferences"},method=RequestMethod.GET)
	public String getUserLookupPreferences(ModelMap model)
	{
    	model.addAttribute("userLookupPreferencesForm",userLookupPreferencesService.getUserLookupPreferences());
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value={"Patient/mergeUserLookupPreferences"},method=RequestMethod.POST)
   	public String mergeUserLookupPreferences(@RequestBody UserLookupPreferencesForm userLookupPreferencesForm,ModelMap model)
   	{
    	userLookupPreferencesService.mergeUserLookupPreferences(userLookupPreferencesForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateUserLookupPreferences",method=RequestMethod.POST)
   	public String saveUserLookupPreferences(@RequestBody UserLookupPreferencesForm userLookupPreferencesForm,ModelMap model)
   	{
    	if(userLookupPreferencesForm.getUserId()!=null)
    		userLookupPreferencesService.saveUserLookupPreferences(userLookupPreferencesForm);
    	else
    		userLookupPreferencesService.updateUserLookupPreferences(userLookupPreferencesForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteUserLookupPreferences",method=RequestMethod.POST)
   	public String deleteUserLookupPreferences(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	userLookupPreferencesService.deleteUserLookupPreferences(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllUserLookupPreferencess",method=RequestMethod.GET)
   	public String getAllUserLookupPreferencess(ModelMap model)
   	{
    	model.addAttribute("userLookupPreferencesForms",userLookupPreferencesService.getUserLookupPreferencesList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    @RequestMapping(value={"Patient/getPreferenceCounties"},method=RequestMethod.GET)
	public String getPreferenceCounties(@RequestParam("countyListType") Integer countyListType,ModelMap model)
	{
    	model.addAttribute("countyList",userLookupPreferencesService.getPreferenceCountyList(countyListType));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
    
    @RequestMapping(value={"Patient/checkCountyListType"},method=RequestMethod.GET)
	public String checkCountyListType(ModelMap model)
	{
    	model.addAttribute("countyListType",userLookupPreferencesService.checkCountyListType());
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
}