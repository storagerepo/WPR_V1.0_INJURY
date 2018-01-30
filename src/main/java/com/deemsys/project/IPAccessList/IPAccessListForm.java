package com.deemsys.project.IPAccessList;

import java.util.Date;

import com.deemsys.project.entity.IpAccessListId;
import com.deemsys.project.entity.Users;



/**
 * 
 * @author Deemsys
 * 
 */
public class IPAccessListForm {

	private String ipAddress;
	private Integer primaryLoginId;
	private String primaryUsername;
	private String addedDateMonthFormat;
	private Date addedDateYearFormat;
	private Integer status;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPrimaryLoginId() {
		return primaryLoginId;
	}
	public void setPrimaryLoginId(Integer primaryLoginId) {
		this.primaryLoginId = primaryLoginId;
	}
	public String getPrimaryUsername() {
		return primaryUsername;
	}
	public void setPrimaryUsername(String primaryUsername) {
		this.primaryUsername = primaryUsername;
	}
	public String getAddedDateMonthFormat() {
		return addedDateMonthFormat;
	}
	public void setAddedDateMonthFormat(String addedDateMonthFormat) {
		this.addedDateMonthFormat = addedDateMonthFormat;
	}
	public Date getAddedDateYearFormat() {
		return addedDateYearFormat;
	}
	public void setAddedDateYearFormat(Date addedDateYearFormat) {
		this.addedDateYearFormat = addedDateYearFormat;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public IPAccessListForm(String ipAddress, Integer primaryLoginId,
			String primaryUsername, String addedDateMonthFormat,
			Date addedDateYearFormat, Integer status) {
		super();
		this.ipAddress = ipAddress;
		this.primaryLoginId = primaryLoginId;
		this.primaryUsername = primaryUsername;
		this.addedDateMonthFormat = addedDateMonthFormat;
		this.addedDateYearFormat = addedDateYearFormat;
		this.status = status;
	}
	public IPAccessListForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
