package com.deemsys.project.IPGeoLocation;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.IpGeoLocation;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class IPGeoLocationDAOImpl implements IPGeoLocationDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(IpGeoLocation entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(IpGeoLocation entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public IpGeoLocation get(Integer id) {
		// TODO Auto-generated method stub
		return (IpGeoLocation) this.sessionFactory.getCurrentSession().get(IpGeoLocation.class, id);
	}

	@Override
	public IpGeoLocation update(IpGeoLocation entity) {
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
	public List<IpGeoLocation> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(IpGeoLocation.class).list();
	}

	@Override
	public List<IpGeoLocation> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpGeoLocation> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpGeoLocation> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpGeoLocation> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpGeoLocation> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpGeoLocation> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpGeoLocation> find(String ParamName, Date date) {
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
	public List<IpGeoLocation> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IpGeoLocation getIPGeoLocationByIP(String ipAddress) {
		// TODO Auto-generated method stub
		return (IpGeoLocation) this.sessionFactory.getCurrentSession().createCriteria(IpGeoLocation.class).add(Restrictions.eq("ipAddress", ipAddress));
	}

	

}
