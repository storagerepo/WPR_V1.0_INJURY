package com.deemsys.project.CallLogs;

/**
 * 
 * @author Deemsys
 * 
 */
public class CallLogsForm {

	public CallLogsForm(Long id, Integer patientId, String timeStamp,
			Integer response, String notes) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
	}

	private Long id;
	private Integer patientId;
	private Integer appointmentId;
	private Integer callerId;
	private String timeStamp;
	private Integer response;
	private String notes;
	private String callerName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getCallerId() {
		return callerId;
	}

	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getResponse() {
		return response;
	}

	public void setResponse(Integer response) {
		this.response = response;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public CallLogsForm(Long id, Integer patientId, Integer appointmentId,
			String timeStamp, Integer response, String notes) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.appointmentId = appointmentId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
	}

	public CallLogsForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CallLogsForm(Long id) {
		this.id = id;
	}

	public CallLogsForm(Long id, Integer patientId, Integer appointmentId,
			String timeStamp, Integer response, String notes, String callerName) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.patientId = patientId;
		this.appointmentId = appointmentId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
		this.callerName = callerName;

	}

	public CallLogsForm(Long id, Integer patientId, String timeStamp,
			Integer response, String notes, String callerName) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
		this.callerName = callerName;

		// TODO Auto-generated constructor stub
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

}
