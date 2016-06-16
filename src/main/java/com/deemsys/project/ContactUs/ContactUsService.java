package com.deemsys.project.ContactUs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.ContactUs;
import com.deemsys.project.mail.MailService;
/**
 * 
 * @author Deemsys
 *
 * ContactUs 	 - Entity
 * contactUs 	 - Entity Object
 * contactUss 	 - Entity List
 * contactUsDAO   - Entity DAO
 * contactUsForms - EntityForm List
 * ContactUsForm  - EntityForm
 *
 */
@Service
@Transactional
public class ContactUsService {

	@Autowired
	ContactUsDAO contactUsDAO;
	@Autowired
	MailService mailService;
	//Get All Entries
	public List<ContactUsForm> getContactUsList()
	{
		List<ContactUsForm> contactUsForms=new ArrayList<ContactUsForm>();
		
		List<ContactUs> contactUss=new ArrayList<ContactUs>();
		
		contactUss=contactUsDAO.getAll();
		
		for (ContactUs contactUs : contactUss) {
			//TODO: Fill the List
			ContactUsForm contactUsForm=new ContactUsForm(contactUs.getId(), contactUs.getFirstName(), contactUs.getLastName(), contactUs.getEmail(), contactUs.getPhoneNumber(), contactUs.getSubject(), contactUs.getMessage(), InjuryConstants.convertMonthFormat(contactUs.getDate()), contactUs.getStatus());
			contactUsForms.add(contactUsForm);
		}
		
		return contactUsForms;
	}
	
	//Get Particular Entry
	public ContactUsForm getContactUs(Integer getId)
	{
		ContactUs contactUs=new ContactUs();
		
		contactUs=contactUsDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		
		ContactUsForm contactUsForm=new ContactUsForm(contactUs.getId(), contactUs.getFirstName(), contactUs.getLastName(), contactUs.getEmail(), contactUs.getPhoneNumber(), contactUs.getSubject(), contactUs.getMessage(), InjuryConstants.convertMonthFormat(contactUs.getDate()), contactUs.getStatus());
		
		//End
		
		return contactUsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeContactUs(ContactUsForm contactUsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		ContactUs contactUs=new ContactUs(contactUsForm.getId(), contactUsForm.getFirstName(), contactUsForm.getLastName(), contactUsForm.getEmail(), contactUsForm.getPhoneNumber(), contactUsForm.getSubject(), contactUsForm.getMessage(), new Date(),0);
		contactUs.setId(contactUsForm.getId());
		
		//Logic Ends
		contactUsDAO.merge(contactUs);
		
		// Send Mail
		mailService.sendContactUsMail(contactUsForm);
		return 1;
	}
	
	//Save an Entry
	public int saveContactUs(ContactUsForm contactUsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ContactUs contactUs=new ContactUs(contactUsForm.getId(), contactUsForm.getFirstName(), contactUsForm.getLastName(), contactUsForm.getEmail(), contactUsForm.getPhoneNumber(), contactUsForm.getSubject(), contactUsForm.getMessage(), new Date(),0);
		
		//Logic Ends
		
		contactUsDAO.save(contactUs);
		return 1;
	}
	
	//Update an Entry
	public int updateContactUs(ContactUsForm contactUsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ContactUs contactUs=new ContactUs(contactUsForm.getId(), contactUsForm.getFirstName(), contactUsForm.getLastName(), contactUsForm.getEmail(), contactUsForm.getPhoneNumber(), contactUsForm.getSubject(), contactUsForm.getMessage(), new Date(),0);
		contactUs.setId(contactUsForm.getId());
		//Logic Ends
		
		contactUsDAO.update(contactUs);
		return 1;
	}
	
	//Delete an Entry
	public int deleteContactUs(Integer id)
	{
		contactUsDAO.delete(id);
		return 1;
	}
	
	// Change the Status
	public void changeContactStatus(Integer id){
		ContactUs contactUs = contactUsDAO.get(id);
		contactUs.setStatus(1);
		contactUsDAO.merge(contactUs);
	}
}
