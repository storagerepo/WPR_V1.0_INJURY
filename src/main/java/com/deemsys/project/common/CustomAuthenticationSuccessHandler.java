package com.deemsys.project.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.deemsys.project.Logging.LogSampleDAO;
import com.deemsys.project.entity.LogSample;

@Service
@org.springframework.transaction.annotation.Transactional
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	LogSampleDAO logSampleDAO;
	
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
		System.out.println("Session Id---"+request.getSession().getId()+"-Creation Time--"+request.getSession().getCreationTime()+"--Ip Address-->"+request.getRemoteAddr());
		
		LogSample logSample = new LogSample(request.getSession().getId());
		logSampleDAO.merge(logSample);
		//set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
 
        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login
        response.sendRedirect("dashboard");
	}

}
