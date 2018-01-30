package com.deemsys.project.IPAddresses;

import java.util.Date;

import com.deemsys.project.entity.Users;



/**
 * 
 * @author Deemsys
 * 
 */
public class IPAddressesForm {

	private String ipAddress;
	private Integer primaryLoginId;
	private String primaryUsername;
	private String city;
	private String region;
	private String countryName;
	private String countryCode;
	private String continentName;
	private String continentCode;
	private String postal;
	private String latitude;
	private String longitude;
	private String timeZone;
	private String flag;
	private Integer blockStatus;
	private String addedDateMonthFormat;
	private Date addedDateYearFormat;
	private Long accessCount;
	private Integer isNew;
	
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public String getContinentCode() {
		return continentCode;
	}

	public void setContinentCode(String continentCode) {
		this.continentCode = continentCode;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(Integer blockStatus) {
		this.blockStatus = blockStatus;
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

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}
	// Status For Show In View
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public IPAddressesForm(String ipAddress, Integer primaryLoginId,
			String primaryUsername, String city, String region,
			String countryName, String countryCode, String continentName,
			String continentCode, String postal, String latitude,
			String longitude, String timeZone, String flag, Integer blockStatus,
			String addedDateMonthFormat) {
		super();
		this.ipAddress = ipAddress;
		this.primaryLoginId = primaryLoginId;
		this.primaryUsername = primaryUsername;
		this.city = city;
		this.region = region;
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.continentName = continentName;
		this.continentCode = continentCode;
		this.postal = postal;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeZone = timeZone;
		this.flag = flag;
		this.blockStatus = blockStatus;
		this.addedDateMonthFormat = addedDateMonthFormat;
	}

	public IPAddressesForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
