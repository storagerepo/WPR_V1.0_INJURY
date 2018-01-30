package com.deemsys.project.IPGeoLocation;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.IpGeoLocation;
/**
 * 
 * @author Deemsys
 *
 */
public interface IPGeoLocationDAO extends IGenericDAO<IpGeoLocation>{
	public IpGeoLocation getIPGeoLocationByIP(String ipAddress);
}
