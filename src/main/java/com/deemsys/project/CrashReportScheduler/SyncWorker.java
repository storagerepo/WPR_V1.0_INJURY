package com.deemsys.project.CrashReportScheduler;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * A synchronous worker
 */
@Component("syncWorker")
public class SyncWorker implements Worker {

	protected static Logger logger = LoggerFactory.getLogger("service");

	public void work() {
	}
	
}
