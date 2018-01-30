package com.deemsys.project.IPAddresses;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.BlockedIp;
import com.deemsys.project.entity.IpAddress;
import com.deemsys.project.entity.IpAddresses;
/**
 * 
 * @author Deemsys
 *
 */
public interface IPAddressesDAO extends IGenericDAO<IpAddress>{
	public boolean checkIPAlreadyExistOrNot(String ipAddress);
	public IpAddress getByIpAddress(String ipAddress);
	public IPAddressesSearchResult searchIPAddresses(IPAddressesSearchForm ipAddressesSearchForm);
	public void deleteIPAddresses(String ipAddress);
	public void blockIpAddress(String ipAddress);
	public void unblockIpAddress(String ipAddress);
	public List<IpAddress> getBlockedIpAddress();
}
