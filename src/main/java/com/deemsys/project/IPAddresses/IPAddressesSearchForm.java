package com.deemsys.project.IPAddresses;

public class IPAddressesSearchForm {
	
	private String ipAddress;
	private String primaryUsername;
	private Integer blockStatus;
	private String date;
	private Integer pageNumber;
	private Integer itemsPerPage;
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPrimaryUsername() {
		return primaryUsername;
	}
	public void setPrimaryUsername(String primaryUsername) {
		this.primaryUsername = primaryUsername;
	}
	public Integer getBlockStatus() {
		return blockStatus;
	}
	public void setBlockStatus(Integer blockStatus) {
		this.blockStatus = blockStatus;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public IPAddressesSearchForm(String ipAddress, String primaryUsername,
			Integer blockStatus, String date, Integer pageNumber, Integer itemsPerPage) {
		super();
		this.ipAddress = ipAddress;
		this.primaryUsername = primaryUsername;
		this.blockStatus = blockStatus;
		this.date = date;
		this.pageNumber = pageNumber;
		this.itemsPerPage = itemsPerPage;
	}
	public IPAddressesSearchForm() {
		// TODO Auto-generated constructor stub
	}

}
