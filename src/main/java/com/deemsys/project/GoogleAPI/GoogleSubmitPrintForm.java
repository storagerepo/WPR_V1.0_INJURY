package com.deemsys.project.GoogleAPI;

public class GoogleSubmitPrintForm {

	private String xsrf;
	private String printerId;
	private String jobId;
	private String title;
	private String contentType;
	private String content;
	private String ticket="{'version':'1.0','print':{}}";
	private String accessToken;
	public String getXsrf() {
		return xsrf;
	}
	public void setXsrf(String xsrf) {
		this.xsrf = xsrf;
	}
	public String getPrinterId() {
		return printerId;
	}
	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public GoogleSubmitPrintForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoogleSubmitPrintForm(String xsrf, String printerId, String jobId,
			String title, String contentType, String content, String ticket,
			String accessToken) {
		super();
		this.xsrf = xsrf;
		this.printerId = printerId;
		this.jobId = jobId;
		this.title = title;
		this.contentType = contentType;
		this.content = content;
		this.ticket = ticket;
		this.accessToken = accessToken;
	}
	
}
