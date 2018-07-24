package com.deemsys.project.common;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import com.deemsys.project.Logging.ActivityLogsForm;
import com.deemsys.project.Logging.ActivityLogsService;

@Component
public class ApplicationSecurityListener implements ApplicationListener<ApplicationEvent>{

	@Autowired
	ActivityLogsService activityLogsService;
	
	public ApplicationSecurityListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		if(event instanceof AuthorizationFailureEvent){
			/*AuthorizationFailureEvent authorizationFailureEvent = (AuthorizationFailureEvent) event;
			System.out.println("Not Authorized--"+authorizationFailureEvent);*/
		}else if(event instanceof AuthenticationFailureBadCredentialsEvent){
			/*AuthenticationFailureBadCredentialsEvent badCredentialsEvent = (AuthenticationFailureBadCredentialsEvent) event;
			System.out.println("Bad Credentials--"+badCredentialsEvent);*/
		}else if(event instanceof AuthenticationSuccessEvent) {
			
			/*
			 * AuthenticationSuccessEvent authenticationSuccessEvent = (AuthenticationSuccessEvent) event;
			User authUser = (User) authenticationSuccessEvent.getAuthentication().getPrincipal();
			System.out.println("Authentication Success Event"+authenticationSuccessEvent.getAuthentication().getDetails());
			WebAuthenticationDetails webauth=(WebAuthenticationDetails) authenticationSuccessEvent.getAuthentication().getDetails();
			System.out.println("Session Id while create----->"+webauth.getSessionId());
			System.out.println("username---"+authUser.getUsername());
			System.out.println("Authentication Success Event"+authenticationSuccessEvent.getSource());*/
		}else if(event instanceof SessionDestroyedEvent){
			SessionDestroyedEvent sessionDestroyedEvent = (SessionDestroyedEvent) event;
			System.out.println("Session Destroyed event Id-->"+sessionDestroyedEvent.getId());
			ActivityLogsForm activityLogsForm = new ActivityLogsForm(sessionDestroyedEvent.getId(), "", null, "", null, "", 1, "", null, null, "", null, null, InjuryConstants.convertUSAFormatWithTime(new Date()), 0,  "", 1);
			activityLogsService.updateActivityLogsBySessionId(activityLogsForm);
		}else if(event instanceof ServletRequestHandledEvent){
			ServletRequestHandledEvent servletRequestHandledEvent = (ServletRequestHandledEvent) event;
			String requestedURI =servletRequestHandledEvent.getRequestUrl();
			if(!requestedURI.contains("resources")&&!requestedURI.contains("styles")
					&&!requestedURI.contains("scripts")&&!requestedURI.contains("views")){
				/*System.out.println("Http Servlet Event Session Id--"+servletRequestHandledEvent.getSessionId());
				System.out.println("Http Servlet Event User Name--"+servletRequestHandledEvent.getUserName());
				System.out.println("Http Servlet Event IP--"+servletRequestHandledEvent.getClientAddress());
				System.out.println("Http Servlet Event Method--"+servletRequestHandledEvent.getMethod());
				System.out.println("Http Servlet Event Processing Time--"+servletRequestHandledEvent.getProcessingTimeMillis());
				System.out.println("Http Servlet Event Request URL--"+servletRequestHandledEvent.getRequestUrl());*/
			}
		}
	}

	/*@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		// TODO Auto-generated method stub
		List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
        UserDetails ud;
        for (SecurityContext securityContext : lstSecurityContext)
        {
        	System.out.println("Session Event Id"+event.getId());
            ud = (UserDetails) securityContext.getAuthentication().getPrincipal();
            System.out.println("User Details"+ud.getUsername());
            // ...
        }
	}*/

}