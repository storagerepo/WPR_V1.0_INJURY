
package com.deemsys.project.exportFields;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Deemsys
 *
 */
@Controller
@RequestMapping("/Patient")
public class ExportFieldsController {
	
	@Autowired
	ExportFieldsService exportFieldsService;

    @RequestMapping(value="/getExportFields",method=RequestMethod.GET)
	public String getExportFields(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("exportFieldsForm",exportFieldsService.getExportFields(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    @RequestMapping(value="/getStandardExportFields",method=RequestMethod.GET)
   	public String getStandardExportFields(ModelMap model)
   	{
       	model.addAttribute("exportFieldsForm",exportFieldsService.getStandardExportFieldsList());
       	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/mergeExportFields",method=RequestMethod.POST)
   	public String mergeExportFields(@ModelAttribute("exportFieldsForm") ExportFieldsForm exportFieldsForm,ModelMap model)
   	{
    	exportFieldsService.mergeExportFields(exportFieldsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateExportFields",method=RequestMethod.POST)
   	public String saveExportFields(@ModelAttribute("exportFieldsForm") ExportFieldsForm exportFieldsForm,ModelMap model)
   	{
    	if(exportFieldsForm.getFieldId().equals(""))
    		exportFieldsService.saveExportFields(exportFieldsForm);
    	else
    		exportFieldsService.updateExportFields(exportFieldsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteExportFields",method=RequestMethod.POST)
   	public String deleteExportFields(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	exportFieldsService.deleteExportFields(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllExportFieldss",method=RequestMethod.GET)
   	public String getAllExportFieldss(ModelMap model)
   	{
    	model.addAttribute("exportFieldsForms",exportFieldsService.getExportFieldsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
}
