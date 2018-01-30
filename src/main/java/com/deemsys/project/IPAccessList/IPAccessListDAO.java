package com.deemsys.project.IPAccessList;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.IpAccessList;
/**
 * 
 * @author Deemsys
 *
 */
public interface IPAccessListDAO extends IGenericDAO<IpAccessList>{

	public IpAccessList getByIpAddressAndLoginId(String ipAddress,
			Integer primaryLoginId);

	public boolean checkIPAlreadyExistOrNot(String ipAddress,
			Integer primaryLoginId);

}
