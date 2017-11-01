package com.deemsys.project.Map;

public class NearByClinicSearchForm {
	
	private String patientId;
	private Integer searchRange;
	private String correctedAddress;
	private String correctedLat;
	private String correctedLong;
	private Integer searchBy;
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Integer getSearchRange() {
		return searchRange;
	}
	public void setSearchRange(Integer searchRange) {
		this.searchRange = searchRange;
	}
	public String getCorrectedAddress() {
		return correctedAddress;
	}
	public void setCorrectedAddress(String correctedAddress) {
		this.correctedAddress = correctedAddress;
	}
	public String getCorrectedLat() {
		return correctedLat;
	}
	public void setCorrectedLat(String correctedLat) {
		this.correctedLat = correctedLat;
	}
	public String getCorrectedLong() {
		return correctedLong;
	}
	public void setCorrectedLong(String correctedLong) {
		this.correctedLong = correctedLong;
	}
	public Integer getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(Integer searchBy) {
		this.searchBy = searchBy;
	}
	public NearByClinicSearchForm(String patientId, Integer searchRange,
			String correctedAddress, String correctedLat, String correctedLong,
			Integer searchBy) {
		super();
		this.patientId = patientId;
		this.searchRange = searchRange;
		this.correctedAddress = correctedAddress;
		this.correctedLat = correctedLat;
		this.correctedLong = correctedLong;
		this.searchBy = searchBy;
	}
	public NearByClinicSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
}
