package com.deemsys.project.VehicleMakeAbbreviation;

import java.util.List;

public class VehicleMakeAbbreviationList {
	
	private Long totalRecords;
	private List<VehicleMakeAbbreviationForm> vehicleMakeAbbreviationForms;
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<VehicleMakeAbbreviationForm> getVehicleMakeAbbreviationForms() {
		return vehicleMakeAbbreviationForms;
	}
	public void setVehicleMakeAbbreviationForms(List<VehicleMakeAbbreviationForm> vehicleMakeAbbreviationForms) {
		this.vehicleMakeAbbreviationForms = vehicleMakeAbbreviationForms;
	}
	public VehicleMakeAbbreviationList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VehicleMakeAbbreviationList(Long totalRecords,
			List<VehicleMakeAbbreviationForm> vehicleMakeAbbreviationForms) {
		super();
		this.totalRecords = totalRecords;
		this.vehicleMakeAbbreviationForms = vehicleMakeAbbreviationForms;
	}
	

}
