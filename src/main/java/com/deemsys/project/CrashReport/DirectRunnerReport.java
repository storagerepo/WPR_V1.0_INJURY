package com.deemsys.project.CrashReport;

import java.util.List;

public class DirectRunnerReport {
	private List<RunnerCrashReportForm> runnerCrashReportForms;

	public List<RunnerCrashReportForm> getRunnerCrashReportForms() {
		return runnerCrashReportForms;
	}

	public void setRunnerCrashReportForms(
			List<RunnerCrashReportForm> runnerCrashReportForms) {
		this.runnerCrashReportForms = runnerCrashReportForms;
	}

	public DirectRunnerReport(List<RunnerCrashReportForm> runnerCrashReportForms) {
		super();
		this.runnerCrashReportForms = runnerCrashReportForms;
	}

	public DirectRunnerReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
