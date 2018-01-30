package com.deemsys.project.IPAddresses;

import java.util.List;

public class IPAddressesSearchResult {

	private Long totalRecords;
	private List<IPAddressesForm> ipAddressesForms;
	
	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<IPAddressesForm> getIpAddressesForms() {
		return ipAddressesForms;
	}

	public void setIpAddressesForms(List<IPAddressesForm> ipAddressesForms) {
		this.ipAddressesForms = ipAddressesForms;
	}

	public IPAddressesSearchResult(Long totalRecords,
			List<IPAddressesForm> ipAddressesForms) {
		super();
		this.totalRecords = totalRecords;
		this.ipAddressesForms = ipAddressesForms;
	}

	public IPAddressesSearchResult() {
		// TODO Auto-generated constructor stub
	}

}
