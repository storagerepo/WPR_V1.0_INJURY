
package com.deemsys.project.userExportPreferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 
 * @author Deemsys
 *
 */
@Controller
@RequestMapping("/Patient")
public class UserExportPreferencesController {
	
	@Autowired
	UserExportPreferencesService userExportPreferencesService;

    
    @RequestMapping(value="/saveUpdateUserExportPreferences",method=RequestMethod.POST)
   	public String saveUserExportPreferences(@RequestBody UserPreferencesSaveForm userPreferenceSaveForm,ModelMap model)
   	{
    	userExportPreferencesService.saveUserExportPreferences(userPreferenceSaveForm.getExportFieldsForms());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}   
   
    
    @RequestMapping(value="/getAllUserExportPreferencess",method=RequestMethod.GET)
   	public String getAllUserExportPreferencess(ModelMap model)
   	{
    	model.addAttribute("userExportPreferencesForms",userExportPreferencesService.getUserExportPreferencesList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    // Check Custom Fields available are not
    @RequestMapping(value="/checkCustomExportPreferencess",method=RequestMethod.GET)
   	public String checkCustomUserExportPreferencess(ModelMap model)
   	{
    	model.addAttribute("status",userExportPreferencesService.checkUserExportPreferenceStatus());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
	
}
