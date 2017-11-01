package com.deemsys.project.Map;

import java.util.List;

import com.deemsys.project.Clinics.ClinicsForm;

public class ClinicLocationForm {

	private Double centerLatitude;
	private Double centerLongitude;
	private Double searchRadius;
	private String correctedAddress;
	private List<ClinicsForm> clinicsForms;
	
	public Double getCenterLatitude() {
		return centerLatitude;
	}

	public void setCenterLatitude(Double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public Double getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(Double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public Double getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(Double searchRadius) {
		this.searchRadius = searchRadius;
	}

	public String getCorrectedAddress() {
		return correctedAddress;
	}

	public void setCorrectedAddress(String correctedAddress) {
		this.correctedAddress = correctedAddress;
	}

	public List<ClinicsForm> getClinicsForms() {
		return clinicsForms;
	}

	public void setClinicsForms(List<ClinicsForm> clinicsForms) {
		this.clinicsForms = clinicsForms;
	}

	
	public ClinicLocationForm(Double centerLatitude, Double centerLongitude,
			Double searchRadius, String correctedAddress, List<ClinicsForm> clinicsForms) {
		super();
		this.centerLatitude = centerLatitude;
		this.centerLongitude = centerLongitude;
		this.searchRadius = searchRadius;
		this.correctedAddress = correctedAddress;
		this.clinicsForms = clinicsForms;
	}

	public ClinicLocationForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
