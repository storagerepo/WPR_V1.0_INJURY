package com.deemsys.project.IPAccessList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.IPAddresses.IPAddressesDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.entity.IpAccessList;
import com.deemsys.project.entity.IpAccessListId;
import com.deemsys.project.entity.IpAddress;
import com.deemsys.project.entity.IpAddressesId;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * IpAccessList 	 - Entity
 * ipAccessList 	 - Entity Object
 * ipAccessLists 	 - Entity List
 * ipAccessListDAO   - Entity DAO
 * ipAccessListForms - EntityForm List
 * IpAccessListForm  - EntityForm
 *
 */
@Service
@Transactional
public class IPAccessListService {

	@Autowired
	IPAccessListDAO ipAccessListDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	IPAddressesDAO ipAddressesDAO;
	
	//Get All Entries
	public List<IPAccessListForm> getIpAccessListList()
	{
		List<IPAccessListForm> ipAccessListForms=new ArrayList<IPAccessListForm>();
		
		List<IpAccessList> ipAccessLists=new ArrayList<IpAccessList>();
		
		ipAccessLists=ipAccessListDAO.getAll();
		
		for (IpAccessList ipAccessList : ipAccessLists) {
			//TODO: Fill the List
			
		}
		
		return ipAccessListForms;
	}
	
	//Get Particular Entry
	public IPAccessListForm getIpAccessList(Integer getId)
	{
		IpAccessList ipAccessList=new IpAccessList();
		
		ipAccessList=ipAccessListDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		
		IPAccessListForm ipAccessListForm=new IPAccessListForm();
		
		//End
		
		return ipAccessListForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeIpAccessList(IPAccessListForm ipAccessListForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		IpAccessList ipAccessList=new IpAccessList();
		
		//Logic Ends
		
		
		ipAccessListDAO.merge(ipAccessList);
		return 1;
	}
	
	//Save an Entry
	public int saveIpAccessList(IPAccessListForm ipAccessListForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		Users users = usersDAO.getByUserName(ipAccessListForm.getPrimaryUsername());
		IpAddress ipAddress = ipAddressesDAO.getByIpAddress(ipAccessListForm.getIpAddress());
		IpAccessListId ipAccessListId = new IpAccessListId(ipAccessListForm.getIpAddress(), users.getUserId());
		IpAccessList ipAccessList=new IpAccessList(ipAccessListId, ipAddress, users, ipAccessListForm.getAddedDateYearFormat(), ipAccessListForm.getStatus(), null);
		
		//Logic Ends
		
		ipAccessListDAO.save(ipAccessList);
		return 1;
	}
	
	//Update an Entry
	public int updateIpAccessList(IPAccessListForm ipAccessListForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Users users = usersDAO.getByUserName(ipAccessListForm.getPrimaryUsername());
		IpAddress ipAddress = ipAddressesDAO.getByIpAddress(ipAccessListForm.getIpAddress());
		IpAccessListId ipAccessListId = new IpAccessListId(ipAccessListForm.getIpAddress(), users.getUserId());
		IpAccessList ipAccessList=new IpAccessList(ipAccessListId, ipAddress, users, ipAccessListForm.getAddedDateYearFormat(), ipAccessListForm.getStatus(), null);
		
		//Logic Ends
		
		ipAccessListDAO.update(ipAccessList);
		return 1;
	}
	
	//Delete an Entry
	public int deleteIpAccessList(Integer id)
	{
		ipAccessListDAO.delete(id);
		return 1;
	}
	// Check whether IP Already Exist or Not  
	// true - already available, false - not available
	public boolean checkIPAlreadyExistOrNot(String ipAddress,String username){
		Users usersLoginId = usersDAO.getByUserName(username);
		String loginRole = usersLoginId.getRoles().getRole();
		Integer primaryLoginId=loginService.getUserIdOfAdmin(loginRole, usersLoginId.getUserId());
		if(primaryLoginId==null){
			primaryLoginId=usersLoginId.getUserId();
		}
		return ipAccessListDAO.checkIPAlreadyExistOrNot(ipAddress,primaryLoginId);
	}	
	
	
}
