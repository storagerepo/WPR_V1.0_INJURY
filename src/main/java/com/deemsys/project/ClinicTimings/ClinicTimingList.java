package com.deemsys.project.ClinicTimings;


public class ClinicTimingList{
	
	private Integer day;
	private Integer clinicId;
	private String startTime;
	private String endTime;
	private String startsBreak;
	private String endsBreak;
	private Integer isWorkingDay;
	private Integer isAppointmentDay;
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getClinicId() {
		return clinicId;
	}
	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getStartsBreak() {
		return startsBreak;
	}
	public void setStartsBreak(String startsBreak) {
		this.startsBreak = startsBreak;
	}
	
	public String getEndsBreak() {
		return endsBreak;
	}
	public void setEndsBreak(String endsBreak) {
		this.endsBreak = endsBreak;
	}
	public Integer getIsWorkingDay() {
		return isWorkingDay;
	}
	public void setIsWorkingDay(Integer isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}
	public Integer getIsAppointmentDay() {
		return isAppointmentDay;
	}
	public void setIsAppointmentDay(Integer isAppointmentDay) {
		this.isAppointmentDay = isAppointmentDay;
	}
	
	public ClinicTimingList(Integer day, Integer clinicId, String startTime,
			String endTime, String startsBreak, String endsBreak, Integer isWorkingDay,Integer isAppointmentDay) {
		super();
		this.day = day;
		this.clinicId = clinicId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startsBreak=startsBreak;
		this.endsBreak=endsBreak;
		this.isWorkingDay = isWorkingDay;
		this.isAppointmentDay = isAppointmentDay;
	}
	public ClinicTimingList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}