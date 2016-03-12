package com.deemsys.project.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class InjuryProperties {
	
	Properties prop = new Properties();
	InputStream input=null;	
	
	//Default Constructor
	public InjuryProperties() {
		super();
		try {
			input=this.getClass().getResourceAsStream("/application.properties");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//Default Get Property
	public String getProperty(String propertyName){
		String propertyValue="";
		try {
			input=this.getClass().getResourceAsStream("/application.properties");
			prop.load(input);
			propertyValue=prop.getProperty(propertyName);			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return propertyValue;
	}
	
}
