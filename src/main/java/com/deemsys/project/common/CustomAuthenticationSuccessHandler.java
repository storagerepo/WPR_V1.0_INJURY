package com.deemsys.project.common;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.deemsys.project.IPAccessList.IPAccessListForm;
import com.deemsys.project.IPAccessList.IPAccessListService;
import com.deemsys.project.IPAddresses.IPAddressesForm;
import com.deemsys.project.IPAddresses.IPAddressesService;
import com.deemsys.project.Logging.ActivityLogsForm;
import com.deemsys.project.Logging.ActivityLogsService;
import com.deemsys.project.entity.IpAddresses;
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	ActivityLogsService activityLogsService;
	
	@Autowired
	IPAddressesService ipAddressesService;
	
	@Autowired
	IPAccessListService ipAccessListService;
	
	public CustomAuthenticationSuccessHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("username---"+authUser.getUsername());
		// Get IP Address
		String IPAddress=InjuryConstants.getRemoteAddr(request);
		System.out.println("Session Id---"+request.getSession().getId()+"--Ip Address-->"+IPAddress);
		
		// Check IPAddress Available System
		if(!ipAddressesService.checkIPAlreadyExistOrNot(IPAddress)){
			// Save Into IPAddress List
			IPAddressesForm ipAddressesForm = new IPAddressesForm(IPAddress, null, authUser.getUsername(), "", "", 
														"", "", "", "", "", "", "", "", "", 0, InjuryConstants.convertMonthFormat(new Date()));
			ipAddressesService.saveIpAddresses(ipAddressesForm);
		}
		if(!ipAccessListService.checkIPAlreadyExistOrNot(IPAddress, authUser.getUsername())){
			IPAccessListForm ipAccessListForm = new IPAccessListForm(IPAddress, null, authUser.getUsername(), "", new Date(), 1);
			ipAccessListService.saveIpAccessList(ipAccessListForm);
		}
		
		ActivityLogsForm activityLogsForm = new ActivityLogsForm(request.getSession().getId(), "", null, authUser.getUsername(), null, "", 1, "", null, IPAddress, "", InjuryConstants.convertMonthFormat(new Date()), InjuryConstants.convertUSAFormatWithTime(new Date()), "", 0, "", 1);
		activityLogsService.saveActivityLogs(activityLogsForm);
		//set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
 
        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login
        response.sendRedirect("dashboard");
	}

}
