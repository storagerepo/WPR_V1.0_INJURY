
package com.deemsys.project.patients;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Deemsys
 *
 */
@Controller
@RequestMapping("/Staff")
public class PatientsController {
	
	@Autowired
	PatientsService patientsService;
	
	@RequestMapping(value="/getAllPatientss",method=RequestMethod.GET)
   	public String getAllPatientss(ModelMap model)
   	{
    	model.addAttribute("patientsForms",patientsService.getPatientsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}

    @RequestMapping(value="/getPatients",method=RequestMethod.GET)
	public String getPatients(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("patientsForm",patientsService.getPatients(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	 
  @RequestMapping(value="/updatePatients",method=RequestMethod.POST)
   	public String updatePatients(@ModelAttribute("patientsForm") PatientsForm patientsForm,ModelMap model)
   	{
    	patientsService.updatePatients(patientsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}

    @RequestMapping(value="/deletePatients",method=RequestMethod.POST)
   	public String deletePatients(@RequestParam("id") Integer id,ModelMap model)
   	{
    	patientsService.deletePatients(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
 
    @RequestMapping(value = "/uploadFile" ,method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(
            @RequestParam("file") MultipartFile file,ModelMap model) {
    	File serverFile=null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                 serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());
                String fileName=serverFile.getAbsolutePath();
                
                model.addAttribute("patientsExcelForm",patientsService.addPatientUsingExcel(fileName));
                
                return "You successfully uploaded file=" + fileName;
            } catch (Exception e) {
                return "You failed to upload  => " + e.getMessage();
            }
        } else {
        	
        	
            return "You failed to upload " 
                    + " because the file was empty.";
        }
        
    }
    
}
