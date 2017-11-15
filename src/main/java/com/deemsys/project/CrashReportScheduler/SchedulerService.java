package com.deemsys.project.CrashReportScheduler;


import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deemsys.project.CrashReport.CrashReportService;
import com.deemsys.project.Map.SearchClinicsService;
import com.deemsys.project.PoliceAgency.PoliceAgencyForm;
import com.deemsys.project.PoliceAgency.PoliceAgencyService;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;

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
	CrashReportService crashReportService;
	
	@Autowired
	PoliceAgencyService policeAgencyService;
	
	@Autowired
	SearchClinicsService searchClinicsService;
	
	/**
	 * You can opt for cron expression or fixedRate or fixedDelay
	 * <p>
	 * See Spring Framework 3 Reference:
	 * Chapter 25.5 Annotation Support for Scheduling and Asynchronous Execution
	 * 
	 */
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void doSchedule() {
		worker.work();
			try
			{
				System.out.println("Start Read PDF From Folder");
				if(injuryProperties.getProperty("autoDownloadCrash").equals("on")){
					//crashReportReader.downloadPDFFile(crashReportReader.getCrashId());
					//crashReportReader.downloadPDFAndUploadToAWS(crashReportReader.getCrashId());
					Integer schedulerType=2;
					List<PoliceAgencyForm> policeAgencyForms = policeAgencyService.getPoliceAgenciesForScheduler(schedulerType);
					Integer i=0;
					LocalDate previousDate=new LocalDate().minusDays(30);
					for (PoliceAgencyForm agencyForm : policeAgencyForms) {
						for (LocalDate j = previousDate; j.isBefore(new LocalDate().plusDays(1)); j=j.plusDays(1)) {
							System.out.println(j.toString("MM/dd/yyyy"));
							crashReportService.getPoliceDepartmentReportDetails(agencyForm.getAgencyId(),agencyForm.getCountyId(),j.toString("MM/dd/yyyy"),agencyForm.getMapId());
						}
						i++;
					}
					
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
	
	// Scheduler will run every one hour
	@Scheduled(cron="0 0 0/1 * * ?")
	public void doScheduleForDeleteOldData() {
		worker.work();
			try
			{
				System.out.println("Start Delete Old Data");
				LocalDateTime todayDateTime = new LocalDateTime();
				int currentTime = todayDateTime.getHourOfDay();
				if(injuryProperties.getProperty("sixMonthOldDataDelete").equals("on")){
					System.out.println("Auto Delete ON");
					if(currentTime==Integer.parseInt(injuryProperties.getProperty("oldDataDeleteTime"))){
						System.out.println("Moving and Deleting Start...........");
						LocalDate localDate1=new LocalDate().minusMonths(6);
						String date=localDate1.toString("yyyy-MM-dd");
						System.out.println("Previous 6 Month Date ......"+date);
						crashReportService.backupSixMonthOldReportsDataByStoredProcedure(date);
						System.out.println("Moving and Deleting End...........");
					}else{
						System.out.println("Scheduled Date and Time Not Matched");
					}
				}else{
					System.out.println("Auto Delete Off");
				}
				System.out.println("End Delete Old Data");
				System.out.println("Map Search Result Delete Start");
				if(injuryProperties.getProperty("mapSearchResultOldDataDelete").equals("on")){
					System.out.println("Map Search Result Auto Delete On");
					if(currentTime==Integer.parseInt(injuryProperties.getProperty("mapSearchResultDeleteTime"))){
						System.out.println("Map Search Result Delete Start.....");
						LocalDate localDate1=new LocalDate().minusMonths(1);
						String date=localDate1.toString("yyyy-MM-dd");
						searchClinicsService.deleteOldSearchResults("2017-10-15");
						System.out.println("Map Search Result Delete End.....");
					}
				}else{
					System.out.println("Map Search Result Auto Delete Off");
				}
				System.out.println("Map Search Result Delete End");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
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
