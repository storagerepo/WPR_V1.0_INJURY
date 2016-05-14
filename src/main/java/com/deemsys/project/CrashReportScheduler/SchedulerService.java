package com.deemsys.project.CrashReportScheduler;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deemsys.project.ArchivedPatient.ArchivedPatientService;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;
import com.deemsys.project.pdfcrashreport.PDFReadAndInsertService;

/**
 * Scheduler for handling jobs
 */
@Service
public class SchedulerService {

	protected static Logger logger = LoggerFactory.getLogger("service");

	@Autowired
	@Qualifier("syncWorker")
	private Worker worker;
	
	@Autowired
	PDFCrashReportReader crashReportReader;
	
	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	ArchivedPatientService archivedPatientService;
	
	/**
	 * You can opt for cron expression or fixedRate or fixedDelay
	 * <p>
	 * See Spring Framework 3 Reference:
	 * Chapter 25.5 Annotation Support for Scheduling and Asynchronous Execution
	 * 
	 */
	
	@Scheduled(cron="*/5 * * * * ?")
	public void doSchedule() {
		worker.work();
			try
			{
				System.out.println("Start Read PDF From Folder");
				if(injuryProperties.getProperty("autoDownloadCrash").equals("on")){
					crashReportReader.downloadPDFFile(crashReportReader.getCrashId());
				}else{
					System.out.println("Auto Download Off");
				}
				System.out.println("End PDF Read from folder");
			}
			catch(Exception ex)
			{
				System.out.println(ex.toString());
			}
			
	}
	
	// Schedule At Every Day Mid Night 12 AM For Delete Six Month Old Data
	@Scheduled(cron="0 0 0 * * ?")
	public void doScheduleForDeleteOldData() {
		worker.work();
			try
			{
				System.out.println("Start Delete Old Data");
				if(injuryProperties.getProperty("sixMonthOldDataDelete").equals("on")){
					System.out.println("Auto Delete ON");
					archivedPatientService.getSixMonthOldPatientsAndDelete();
				}else{
					System.out.println("Auto Delete Off");
				}
				System.out.println("End Delete Old Data");
			}
			catch(Exception ex)
			{
				System.out.println(ex.toString());
			}
			
	}
	
	/*// Schedule At Every One Hour
	@Scheduled(cron="0 0 0/1 * * ?")
	public void doScheduleForMissingReports() {
		worker.work();
		try
			{
				System.out.println("Start Second Scheduler for Missing Reports");
				if(injuryProperties.getProperty("autoDownloadCrash").equals("on")){
					archivedPatientService.saveArchivedPatientAndDeleteOldData();
				}else{
					System.out.println("Second Scheduler is Off");
				}
				System.out.println("End Second Scheduler");
			 }
			catch(Exception ex)
			{
				System.out.println(ex.toString());
			}
				
	}
	*/

}
