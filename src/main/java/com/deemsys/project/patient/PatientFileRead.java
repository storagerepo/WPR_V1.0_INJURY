package com.deemsys.project.patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deemsys.project.entity.Patient;

@Service
public class PatientFileRead {

	@SuppressWarnings("unused")
	public List<Patient> readPatientUsingExcel(String fileName) {

		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		String[] patientArray = new String[30];
		String[] patientHeader = new String[30];
		List<Patient> patient = new ArrayList<Patient>();
		try {

			br = new BufferedReader(new FileReader(fileName));
			Integer lineNumber = 1;
			while ((line = br.readLine()) != null) {
				if (lineNumber > 1) {
					patientArray = line.split(csvSplitBy);
					// patient.add(new Patient(null, null, patientArray[0],
					// patientArray[1], patientArray[2],patientArray[3],
					// patientArray[4],patientArray[5], patientArray[6],
					// patientArray[7], patientArray[8],
					// patientArray[9],patientArray[10], patientArray[11],
					// patientArray[12], patientArray[13].trim(),
					// patientArray[14], patientArray[15], patientArray[16],
					// patientArray[17], patientArray[18],
					// patientArray[19],patientArray[20],patientArray[21],
					// patientArray[22], patientArray[23], patientArray[24],
					// patientArray[25], patientArray[26], patientArray[27]));
				} else {
					patientHeader = line.split(csvSplitBy);
					lineNumber++;
				}
			}

		} catch (NumberFormatException e) {
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
		return patient;
	}
}
