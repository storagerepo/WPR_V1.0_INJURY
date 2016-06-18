package com.deemsys.project.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import com.deemsys.project.ContactUs.ContactUsForm;

@Component
public class MailService {

	@Autowired
	MailSender mailSender;
	
	public void sendContactUsMail(ContactUsForm contactUsForm){
		mailSender.sendContactUsDetails(contactUsForm);
	}
	public void sendResponseMail(ContactUsForm contactUsForm) throws MailSendException,MailException{
		mailSender.sendResponseMail(contactUsForm);
	}
	
}
