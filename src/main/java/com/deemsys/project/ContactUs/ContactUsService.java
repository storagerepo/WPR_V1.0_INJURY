package com.deemsys.project.ContactUs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.ContactUsLog.ContactUsLogDAO;
import com.deemsys.project.ContactUsLog.ContactUsLogForm;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.ContactUs;
import com.deemsys.project.entity.ContactUsLog;
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
	ContactUsLogDAO contactUsLogDAO;
	@Autowired
	MailService mailService;
	
	// Executor
	private final ExecutorService executor = Executors.newFixedThreadPool(2);
	//Get All Entries
	public List<ContactUsForm> getContactUsList()
	{
		List<ContactUsForm> contactUsForms=contactUsDAO.getContactUsListWitLatestStatus();
		List<ContactUsForm> contactUsFormsReslut = new ArrayList<ContactUsForm>();
		for (ContactUsForm contactUsForm : contactUsForms) {
			String lastName="";
			String phoneNumber="";
			String firmName="";
			if(contactUsForm.getLastName()!=null&&!contactUsForm.getLastName().equals("")){
				lastName=contactUsForm.getLastName();
			}
			if(contactUsForm.getPhoneNumber()!=null&&!contactUsForm.getPhoneNumber().equals("")){
				phoneNumber=contactUsForm.getPhoneNumber();
			}
			if(contactUsForm.getFirmName()!=null&&!contactUsForm.getFirmName().equals("")){
				firmName=contactUsForm.getFirmName();
			}
			if(contactUsForm.getLogDateTimeTable()!=null){
				contactUsForm.setLogDateTime(InjuryConstants.convertUSAFormatWithTime(contactUsForm.getLogDateTimeTable()));
			}
			if(contactUsForm.getAddedDateTable()!=null){
				contactUsForm.setAddedDate(InjuryConstants.convertUSAFormatWithTime(contactUsForm.getAddedDateTable()));
			}
			contactUsForm.setStatusText(this.getStatusText(contactUsForm.getStatus()));
			contactUsForm.setLastName(lastName);
			contactUsForm.setFirmName(firmName);
			contactUsForm.setPhoneNumber(phoneNumber);
			contactUsFormsReslut.add(contactUsForm);
		}
		/*contactUss=contactUsDAO.getAll();
		
		for (ContactUs contactUs : contactUss) {
			//TODO: Fill the List
			String lastName="";
			String phoneNumber="";
			
			if(contactUs.getLastName()!=null&&!contactUs.getLastName().equals("")){
				lastName=contactUs.getLastName();
			}
			if(contactUs.getPhoneNumber()!=null&&!contactUs.getPhoneNumber().equals("")){
				phoneNumber=contactUs.getPhoneNumber();
			}
			ContactUsForm contactUsForm=new ContactUsForm(contactUs.getId(), contactUs.getFirstName(), lastName, contactUs.getEmail(), phoneNumber, contactUs.getFirmName(), InjuryConstants.convertUSAFormatWithTime(contactUs.getAddedDateTime()), contactUs.getStatus(),this.getStatusText(contactUs.getStatus()));
			contactUsForms.add(contactUsForm);
		}*/
		
		return contactUsFormsReslut;
	}
	
	//Get Particular Entry
	public ContactUsForm getContactUs(Integer getId)
	{
		ContactUs contactUs=new ContactUs();
		
		contactUs=contactUsDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		String lastName="";
		String phoneNumber="";
		if(contactUs.getLastName()!=null&&!contactUs.getLastName().equals("")){
			lastName=contactUs.getLastName();
		}
		if(contactUs.getPhoneNumber()!=null&&!contactUs.getPhoneNumber().equals("")){
			phoneNumber=contactUs.getPhoneNumber();
		}
		ContactUsForm contactUsForm=new ContactUsForm(contactUs.getId(), contactUs.getFirstName(), lastName, contactUs.getEmail(), phoneNumber, contactUs.getFirmName(), InjuryConstants.convertUSAFormatWithTime(contactUs.getAddedDateTime()), contactUs.getStatus(),this.getStatusText(contactUs.getStatus()));
		contactUsForm.setContactUsLogForms(this.getContactUsLogListByContactUsId(getId));
		//End
		
		return contactUsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeContactUs(final ContactUsForm contactUsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		ContactUs contactUs= new ContactUs(contactUsForm.getFirstName(), contactUsForm.getLastName(), contactUsForm.getEmail(), contactUsForm.getPhoneNumber(), contactUsForm.getFirmName(), new Date(), 0,null);
		
		
		//Logic Ends
		contactUsDAO.save(contactUs);
		
		// Contact Us Log
		ContactUsLog contactUsLog = new ContactUsLog();
		contactUsLog.setLogDateTime(new Date());
		contactUsLog.setContactUs(contactUs);
		contactUsLog.setLogStatus(0);
		contactUsLog.setStatus(1);
		
		contactUsLogDAO.merge(contactUsLog);
		
		// Send Mail
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mailService.sendContactUsMail(contactUsForm);
				System.out.println("Mail Was Sent.....");
			}
		});
		
		return 1;
	}
	
	//Save an Entry
	public int saveContactUs(ContactUsForm contactUsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ContactUs contactUs= new ContactUs(contactUsForm.getFirstName(), contactUsForm.getLastName(), contactUsForm.getEmail(), contactUsForm.getPhoneNumber(), contactUsForm.getFirmName(), new Date(), 0, null);
		
		//Logic Ends
		
		contactUsDAO.save(contactUs);
		return 1;
	}
	
	//Update an Entry
	public int updateContactUs(ContactUsForm contactUsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ContactUs contactUs= new ContactUs(contactUsForm.getFirstName(), contactUsForm.getLastName(), contactUsForm.getEmail(), contactUsForm.getPhoneNumber(), contactUsForm.getFirmName(), new Date(), 0, null);
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
	public void changeContactStatus(ContactUsForm contactUsForm){
		ContactUs contactUs = contactUsDAO.get(contactUsForm.getId());
		ContactUsLog contactUsLog = new ContactUsLog();
		if(contactUsForm.getLogDateTime()!=null){
			contactUsLog.setLogDateTime(InjuryConstants.convertYearFormatWithTime(contactUsForm.getLogDateTime()));
		}
		if(contactUsForm.getUpdatedBy()!=null){
			contactUsLog.setByWhom(contactUsForm.getUpdatedBy());
		}
		
		contactUsLog.setContactUs(contactUs);
		contactUsLog.setLogStatus(contactUsForm.getStatus());
		contactUsLog.setStatus(1);
		
		contactUsLogDAO.merge(contactUsLog);
	}
	
	//get Status Text
	public String getStatusText(Integer status){
		String statusText="";
		
		switch (status) {
		case 0:
			statusText="Interest Received";
			break;
		case 1:
			statusText="Response Sent";
			break;
		case 2:
			statusText="Demo Set Up";
			break;
		case 3:
			statusText="Demo Completed";
			break;

		default:
			break;
		}
		
		return statusText;
	}
	
	// Send Response Mail
	public void sendResponseMail(final ContactUsForm contactUsForm) throws MailSendException,MailException{
		ContactUs contactUs = contactUsDAO.get(contactUsForm.getId());
		contactUsForm.setFirmName(contactUs.getFirmName());
		contactUsForm.setFirstName(contactUs.getFirstName());
		contactUsForm.setLastName(contactUs.getLastName());
		contactUsForm.setEmail(contactUs.getEmail());
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mailService.sendResponseMail(contactUsForm);
				System.out.println("Mail was sent.......");
			}
		});
		
		ContactUsLog contactUsLog = new ContactUsLog();
		contactUsLog.setLogDateTime(new Date());
		if(contactUsForm.getUpdatedBy()!=null){
			contactUsLog.setByWhom(contactUsForm.getUpdatedBy());
		}
		contactUsLog.setContactUs(contactUs);
		contactUsLog.setLogStatus(1);
		contactUsLog.setStatus(1);
		
		contactUsLogDAO.merge(contactUsLog);
	}
	
	// get Contact Us Log Details By Contact Us Id
	public List<ContactUsLogForm> getContactUsLogListByContactUsId(Integer contactUsId){
		
		List<ContactUsLogForm> contactUsLogForms=new ArrayList<ContactUsLogForm>();
		List<ContactUsLog> contactUsLogs=contactUsLogDAO.getContactUsLogByContactUsId(contactUsId);
		for (ContactUsLog contactUsLog : contactUsLogs) {
			String logDateTime="";
			if(contactUsLog.getLogDateTime()!=null&&!contactUsLog.getLogDateTime().equals("")){
			    logDateTime=InjuryConstants.convertUSAFormatWithTime(contactUsLog.getLogDateTime());
			}
			ContactUsLogForm contactUsLogForm = new ContactUsLogForm(logDateTime, contactUsId, contactUsLog.getByWhom(), contactUsLog.getLogStatus());
			contactUsLogForms.add(contactUsLogForm);
		}
		
		return contactUsLogForms;
	}
}

