package com.deemsys.project.pdfcrashreport;

import java.util.List;

public class PDFCrashReportJson {

	private ReportFirstPageForm firstPageForm;
	private List<ReportUnitPageForm> reportUnitPageForms;
	private List<ReportMotoristPageForm> reportMotoristPageForms;
	public ReportFirstPageForm getFirstPageForm() {
		return firstPageForm;
	}
	public void setFirstPageForm(ReportFirstPageForm firstPageForm) {
		this.firstPageForm = firstPageForm;
	}
	public List<ReportUnitPageForm> getReportUnitPageForms() {
		return reportUnitPageForms;
	}
	public void setReportUnitPageForms(List<ReportUnitPageForm> reportUnitPageForms) {
		this.reportUnitPageForms = reportUnitPageForms;
	}
	public List<ReportMotoristPageForm> getReportMotoristPageForms() {
		return reportMotoristPageForms;
	}
	public void setReportMotoristPageForms(
			List<ReportMotoristPageForm> reportMotoristPageForms) {
		this.reportMotoristPageForms = reportMotoristPageForms;
	}
	public PDFCrashReportJson(ReportFirstPageForm firstPageForm,
			List<ReportUnitPageForm> reportUnitPageForms,
			List<ReportMotoristPageForm> reportMotoristPageForms) {
		super();
		this.firstPageForm = firstPageForm;
		this.reportUnitPageForms = reportUnitPageForms;
		this.reportMotoristPageForms = reportMotoristPageForms;
	}
	
	
}
