package com.deemsys.project.IPAddresses;

import java.util.List;

public class IPAddressesGrouppedResult {
	private Long totalRecords;
	private List<IPAddressesSearchResultGroup> ipAddressesSearchResultGroups;
	
	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<IPAddressesSearchResultGroup> getIpAddressesSearchResultGroups() {
		return ipAddressesSearchResultGroups;
	}

	public void setIpAddressesSearchResultGroups(
			List<IPAddressesSearchResultGroup> ipAddressesSearchResultGroups) {
		this.ipAddressesSearchResultGroups = ipAddressesSearchResultGroups;
	}

	public IPAddressesGrouppedResult(Long totalRecords,
			List<IPAddressesSearchResultGroup> ipAddressesSearchResultGroups) {
		super();
		this.totalRecords = totalRecords;
		this.ipAddressesSearchResultGroups = ipAddressesSearchResultGroups;
	}

	public IPAddressesGrouppedResult() {
		// TODO Auto-generated constructor stub
	}

}
