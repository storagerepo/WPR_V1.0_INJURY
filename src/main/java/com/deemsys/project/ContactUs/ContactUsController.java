
package com.deemsys.project.ContactUs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
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
public class ContactUsController {
	
	@Autowired
	ContactUsService contactUsService;

    @RequestMapping(value="/Admin/getContactUs",method=RequestMethod.GET)
	public String getContactUs(@RequestParam("id") Integer id,ModelMap model)
	{
    	model.addAttribute("contactUsForm",contactUsService.getContactUs(id));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    
    @RequestMapping(value="/mergeContactUs",method=RequestMethod.POST)
   	public String mergeContactUs(@RequestBody ContactUsForm contactUsForm,ModelMap model)
   	{
    	contactUsService.mergeContactUs(contactUsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateContactUs",method=RequestMethod.POST)
   	public String saveContactUs(@RequestBody ContactUsForm contactUsForm,ModelMap model)
   	{
    	if(contactUsForm.getId().equals(""))
    		contactUsService.saveContactUs(contactUsForm);
    	else
    		contactUsService.updateContactUs(contactUsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/Admin/deleteContactUs",method=RequestMethod.POST)
   	public String deleteContactUs(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	contactUsService.deleteContactUs(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/getAllContactUss",method=RequestMethod.GET)
   	public String getAllContactUss(ModelMap model)
   	{
    	model.addAttribute("contactUsForms",contactUsService.getContactUsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/Admin/changeContactStatus",method=RequestMethod.POST)
   	public String changeContactStatus(@RequestBody ContactUsForm contactUsForm,ModelMap model)
   	{
    	contactUsService.changeContactStatus(contactUsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    @RequestMapping(value="/Admin/sendResponseMail",method=RequestMethod.POST)
   	public String sendResponseMail(@RequestBody ContactUsForm contactUsForm,ModelMap model)
   	{
    	try {
			contactUsService.sendResponseMail(contactUsForm);
			model.addAttribute("response","");
			model.addAttribute("requestSuccess",true);
		} catch (MailSendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("response",e.toString());
			model.addAttribute("requestSuccess",false);
		}catch (MailException e) {
			model.addAttribute("response",e.toString());
			model.addAttribute("requestSuccess",false);
		}
    	
   		return "/returnPage";
   	}
}
