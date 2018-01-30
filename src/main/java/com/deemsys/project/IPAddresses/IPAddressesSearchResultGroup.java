package com.deemsys.project.IPAddresses;

import java.util.List;

public class IPAddressesSearchResultGroup {
	
	private Integer primaryLoginId;
	private String primaryUsername;
	private List<IPAddressesForm> ipAddressesForms;
	
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

	public List<IPAddressesForm> getIpAddressesForms() {
		return ipAddressesForms;
	}

	public void setIpAddressesForms(List<IPAddressesForm> ipAddressesForms) {
		this.ipAddressesForms = ipAddressesForms;
	}

	public IPAddressesSearchResultGroup(Integer primaryLoginId,
			String primaryUsername, List<IPAddressesForm> ipAddressesForms) {
		super();
		this.primaryLoginId = primaryLoginId;
		this.primaryUsername = primaryUsername;
		this.ipAddressesForms = ipAddressesForms;
	}

	public IPAddressesSearchResultGroup() {
		// TODO Auto-generated constructor stub
	}

}
