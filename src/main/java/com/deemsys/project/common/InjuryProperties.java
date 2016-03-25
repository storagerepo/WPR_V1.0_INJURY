package com.deemsys.project.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class InjuryProperties {


	//Default Get Property
	public String getProperty(String propertyName){
		String propertyValue="";
		try {
			Properties prop = new Properties();
			InputStream input=null;	
			input=this.getClass().getResourceAsStream("/application.properties");
			prop.load(input);
			propertyValue=prop.getProperty(propertyName);			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}
	
}
