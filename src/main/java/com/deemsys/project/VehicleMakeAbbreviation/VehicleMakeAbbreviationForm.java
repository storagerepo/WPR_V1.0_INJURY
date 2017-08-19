package com.deemsys.project.VehicleMakeAbbreviation;



/**
 * 
 * @author Deemsys
 * 
 */
public class VehicleMakeAbbreviationForm {

	private String make;
	private String abbreviation;
	private Integer status;
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public VehicleMakeAbbreviationForm(String make, String abbreviation,
			Integer status) {
		super();
		this.make = make;
		this.abbreviation = abbreviation;
		this.status = status;
	}

	public VehicleMakeAbbreviationForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
