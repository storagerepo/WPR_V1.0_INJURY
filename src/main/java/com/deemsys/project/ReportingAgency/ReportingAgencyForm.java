package com.deemsys.project.ReportingAgency;


import javax.xml.bind.annotation.XmlElement;

import com.deemsys.project.entity.County;

/**
 * 
 * @author Deemsys
 * 
 */
public class ReportingAgencyForm {
	private Integer reportingAgencyId;
	private Integer countyId;
	private String reportingAgencyName;
	private String code;
	private Integer status;
	
	public ReportingAgencyForm(Integer reportingAgencyId, Integer countyId,
			String reportingAgencyName, String code, Integer status) {
		super();
		this.reportingAgencyId = reportingAgencyId;
		this.countyId = countyId;
		this.reportingAgencyName = reportingAgencyName;
		this.code = code;
		this.status = status;
	}

	public Integer getReportingAgencyId() {
		return reportingAgencyId;
	}

	public void setReportingAgencyId(Integer reportingAgencyId) {
		this.reportingAgencyId = reportingAgencyId;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public String getReportingAgencyName() {
		return reportingAgencyName;
	}

	public void setReportingAgencyName(String reportingAgencyName) {
		this.reportingAgencyName = reportingAgencyName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ReportingAgencyForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
