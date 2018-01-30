package com.deemsys.project.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestHandlerInterceptor extends HandlerInterceptorAdapter{

	public RequestHandlerInterceptor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String requestedURI =request.getRequestURI();
		if(!requestedURI.contains("resources")&&!requestedURI.contains("styles")
				&&!requestedURI.contains("scripts")&&!requestedURI.contains("views")){
			/*System.out.println("Enter Time---->>"+new DateTime());
			System.out.println("Request method---->>"+request.getMethod());
			System.out.println("Request Session Id---->>"+request.getRequestedSessionId());
			System.out.println("Request Url---->>"+request.getRequestURL());
			System.out.println("Request URI---->>"+request.getRequestURI());*/
			if(SecurityContextHolder.getContext().getAuthentication()!=null&&SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
					&&!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
				/*User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				System.out.println("User Name---->"+user.getUsername());*/
			}
			//System.out.println("Request Paameter Names---->>"+getParameters(request));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e){
		
	}
	
	
	private String getParameters(HttpServletRequest request) {
	    StringBuffer posted = new StringBuffer();
	    Enumeration<?> e = request.getParameterNames();
	    if (e != null) {
	        posted.append("?");
	    }
	    while (e.hasMoreElements()) {
	        if (posted.length() > 1) {
	            posted.append("&");
	        }
	        String curr = (String) e.nextElement();
	        posted.append(curr + "=");
	        if (curr.contains("password") 
	          || curr.contains("pass")
	          || curr.contains("pwd")) {
	            posted.append("*****");
	        } else {
	            posted.append(request.getParameter(curr));
	        }
	    }
	    String ip = request.getHeader("X-FORWARDED-FOR");
	    String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
	    if (ipAddr!=null && !ipAddr.equals("")) {
	        posted.append("&_psip=" + ipAddr); 
	    }
	    return posted.toString();
	}
	
	private String getRemoteAddr(HttpServletRequest request) {
		/*final String[] IP_HEADER_CANDIDATES = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" };"unknown".equalsIgnoreCase(ip)*/
	    String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
	    if (ipFromHeader != null && ipFromHeader.length() > 0) {
	        System.out.println("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
	        return ipFromHeader;
	    }
	    return request.getRemoteAddr();
	}
}
