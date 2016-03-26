package com.deemsys.project.Appointments;

import java.util.List;

public class AppointmentsSearchResult {

	private Integer totalRecords;
	private List<AppointmentsForm> appointmentsForms;
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<AppointmentsForm> getAppointmentsForms() {
		return appointmentsForms;
	}
	public void setAppointmentsForms(List<AppointmentsForm> appointmentsForms) {
		this.appointmentsForms = appointmentsForms;
	}
	public AppointmentsSearchResult(Integer totalRecords,
			List<AppointmentsForm> appointmentsForms) {
		super();
		this.totalRecords = totalRecords;
		this.appointmentsForms = appointmentsForms;
	}
	public AppointmentsSearchResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
