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
	PDFReadAndInsertService readAndInsertService;
	
	/**
	 * You can opt for cron expression or fixedRate or fixedDelay
	 * <p>
	 * See Spring Framework 3 Reference:
	 * Chapter 25.5 Annotation Support for Scheduling and Asynchronous Execution
	 */
	
	@Scheduled(cron="*/5 * * * * ?")
	public void doSchedule() {
		worker.work();
			try
			{
				//System.out.println("Start Read PDF From Folder");
				//readAndInsertService.doReadOperation();
				//System.out.print("End PDF Read from folder");
			}
			catch(Exception ex)
			{
				System.out.println(ex.toString());
			}
			
	}
	

}
