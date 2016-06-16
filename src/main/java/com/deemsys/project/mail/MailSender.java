package com.deemsys.project.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.deemsys.project.ContactUs.ContactUsForm;
import com.deemsys.project.common.InjuryProperties;


@Component
public class MailSender {

	@Autowired
	private JavaMailSender mailSender;
 
	@Autowired
	private InjuryProperties injuryProperties;
	 
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	// Send ContactUs Details As Mail
			public void sendContactUsDetails(final ContactUsForm contactUsForm){

				MimeMessagePreparator preparator = new MimeMessagePreparator() {
		    		public void prepare(MimeMessage mimeMessage) throws Exception {
					 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
					 message.setTo(injuryProperties.getProperty("fromMail"));
					 message.setFrom(new InternetAddress(injuryProperties.getProperty("toMail")));
					 message.setSubject("Contact Us Information");
					 Map<String,String> model = new HashMap<String,String>();
					 model.put("firstName",contactUsForm.getFirstName());
					 model.put("lastName",contactUsForm.getLastName());
					 model.put("subject", contactUsForm.getSubject());
					 model.put("message", contactUsForm.getMessage());
					 model.put("phoneNumber", contactUsForm.getPhoneNumber());
					 model.put("emailId", contactUsForm.getEmail());
					String body = VelocityEngineUtils.mergeTemplateIntoString(
					         velocityEngine, "mailtemplate/" + "contactUs.vm", "UTF-8", model);
					 message.setText(body, true);
					  
					}

					
				};
				this.mailSender.send(preparator);
		}
}
