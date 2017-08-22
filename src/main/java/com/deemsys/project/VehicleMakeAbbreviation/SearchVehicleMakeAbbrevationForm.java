package com.deemsys.project.VehicleMakeAbbreviation;

public class SearchVehicleMakeAbbrevationForm {

	private String make;
	private String abbreviation;
	private Integer pageNumber;
	private Integer recordsPerPage;
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public SearchVehicleMakeAbbrevationForm(String make, String abbreviation, Integer pageNumber,
			Integer recordsPerPage) {
		super();
		this.make = make;
		this.abbreviation = abbreviation;
		this.pageNumber = pageNumber;
		this.recordsPerPage = recordsPerPage;
	}
	public SearchVehicleMakeAbbrevationForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
