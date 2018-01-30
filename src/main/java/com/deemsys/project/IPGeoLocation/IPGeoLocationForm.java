package com.deemsys.project.IPGeoLocation;



/**
 * 
 * @author Deemsys
 * 
 */
public class IPGeoLocationForm {

	private String ipAddress;
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
	private Integer status;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public IPGeoLocationForm(String ipAddress, String city, String region,
			String countryName, String countryCode, String continentName,
			String continentCode, String postal, String latitude,
			String longitude, String timeZone, String flag, Integer status) {
		super();
		this.ipAddress = ipAddress;
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
		this.status = status;
	}
	public IPGeoLocationForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
