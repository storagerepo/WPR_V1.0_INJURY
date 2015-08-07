package com.deemsys.project.patients;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deemsys.project.entity.Patients;
@Service
public class PatientsFileRead {

	public String[] readPatientUsingExcel(String fileName)
	{
			
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] patients={};
		try {

			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				patients=line.split(cvsSplitBy);

					System.out.println("" + patients[5] 
		                                 + "" + patients[13] );
					
			}

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
		for(int i = 0; i < patients.length; i++)
			System.out.println(patients[i]);

		System.out.println("Done");
		return patients;
	 }
}
