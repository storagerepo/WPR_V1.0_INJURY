package com.deemsys.project.IPAccessList;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.IpAccessList;
import com.deemsys.project.entity.IpAddresses;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class IPAccessListDAOImpl implements IPAccessListDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(IpAccessList entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(IpAccessList entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public IpAccessList get(Integer id) {
		// TODO Auto-generated method stub
		return (IpAccessList) this.sessionFactory.getCurrentSession().get(IpAccessList.class, id);
	}

	@Override
	public IpAccessList update(IpAccessList entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(id));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IpAccessList> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(IpAccessList.class).list();
	}

	@Override
	public List<IpAccessList> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAccessList> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAccessList> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAccessList> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAccessList> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAccessList> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAccessList> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkName(Integer id, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IpAccessList> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IpAccessList getByIpAddressAndLoginId(String ipAddress,
			Integer primaryLoginId) {
		// TODO Auto-generated method stub
		return (IpAccessList) this.sessionFactory.getCurrentSession().createCriteria(IpAccessList.class).add(Restrictions.and(Restrictions.eq("id.primaryLoginId", primaryLoginId),Restrictions.eq("id.ipAddress", ipAddress))).uniqueResult();
	}

	@Override
	public boolean checkIPAlreadyExistOrNot(String ipAddress,
			Integer primaryLoginId) {
		// TODO Auto-generated method stub
		IpAccessList ipAccessList= this.getByIpAddressAndLoginId(ipAddress, primaryLoginId);
		
		if(ipAccessList!=null){
			return true;
		}else{
			return false;
		}
	}

	

}
