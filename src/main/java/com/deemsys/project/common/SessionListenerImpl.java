package com.deemsys.project.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionListenerImpl implements HttpSessionListener {


	public SessionListenerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent){
		if(SecurityContextHolder.getContext().getAuthentication()!=null){
			
			/*
			 * String name = SecurityContextHolder.getContext().getAuthentication().getName();
			String sessionId = httpSessionEvent.getSession().getId();
			System.out.println("Session Details..... Name--->"+name+" Id --->"+sessionId);
			long beginTime=System.currentTimeMillis()/1000;
			System.out.println("Sesssion Created Time----->>"+beginTime);*/
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent){
		if(SecurityContextHolder.getContext().getAuthentication()!=null){
			
			/*
			 * String name = SecurityContextHolder.getContext().getAuthentication().getName();
			String sessionId = httpSessionEvent.getSession().getId();
			System.out.println("Session Details..... Name--->"+name+" Id --->"+sessionId);
			
			long endTime=System.currentTimeMillis()/1000;
			System.out.println("Sesssion Destroyed Time----->>"+endTime);*/
		}
	}

}
