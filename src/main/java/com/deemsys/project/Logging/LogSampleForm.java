package com.deemsys.project.Logging;



/**
 * 
 * @author Deemsys
 * 
 */
public class LogSampleForm {

	private Integer id;
	private String sessionId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public LogSampleForm(Integer id, String sessionId) {
		super();
		this.id = id;
		this.sessionId = sessionId;
	}

	public LogSampleForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
