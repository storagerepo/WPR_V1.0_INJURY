package com.deemsys.project.common;

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
			//  tooclose Input Stream Propeties file, 
            input.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}
	
	//Get property for Type 2 Parser data
	public String getParserTwoProperty(String propertyName){
		String propertyValue="";
		try {
			Properties prop = new Properties();
			InputStream input=null;	
			input=this.getClass().getResourceAsStream("/typetwoparser.properties");
			prop.load(input);
			propertyValue=prop.getProperty(propertyName);
			//  tooclose Input Stream Propeties file, 
            input.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}
	
}
