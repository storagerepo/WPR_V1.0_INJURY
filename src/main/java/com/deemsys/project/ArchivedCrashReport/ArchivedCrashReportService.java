package com.deemsys.project.ArchivedCrashReport;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.CrashReport.CrashReportDAO;
import com.deemsys.project.entity.ArchivedCrashReport;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.pdfcrashreport.AWSFileUpload;

@Service
@Transactional
public class ArchivedCrashReportService {
	
	@Autowired
	CrashReportDAO crashReportDAO;
	
	@Autowired
	AWSFileUpload awsFileUpload;
	
	public void deleteOldCrashReport(String crashId){
		
		/*CrashReport crashReport=crashReportDAO.getCrashReport(crashId);
		ArchivedCrashReport archivedCrashReport=new ArchivedCrashReport(crashReport.getCrashId(), crashReport.getCrashReportError().getCrashReportErrorId(), crashReport.getLocalReportNumber(), crashReport.getCrashDate(), crashReport.getCounty().getCountyId(), crashReport.getAddedDate(), crashReport.getFilePath(), crashReport.getNumberOfPatients(), crashReport.getStatus());
		archivedCrashReportDAO.save(archivedCrashReport);
		*/
		// Delete Crash Report
		crashReportDAO.deleteCrashReportByCrashId(crashId);
		
	}
	
	// Delete Six Month Old Crash Report With 0 (Zero) Patients
	public void getSixMonthOldReportAndDelete(){
		List<CrashReport> crashReports=crashReportDAO.getSixMonthOldCrashReports();
		for (CrashReport crashReport : crashReports) {
			String crashReportFileName=crashReport.getFilePath();
			// Delete Crash Report
			if(crashReport.getNumberOfPatients()==0){
				crashReportDAO.deleteCrashReportByCrashId(crashReport.getCrashId());
				// Delete File In AWS S3
				awsFileUpload.deleteFileInAWSS3(crashReportFileName);
			}
		}
	}
	
}
