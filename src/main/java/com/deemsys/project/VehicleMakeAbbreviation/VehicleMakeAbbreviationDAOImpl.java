package com.deemsys.project.VehicleMakeAbbreviation;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.VehicleMakeAbbreviation;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class VehicleMakeAbbreviationDAOImpl implements VehicleMakeAbbreviationDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(VehicleMakeAbbreviation entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(VehicleMakeAbbreviation entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public VehicleMakeAbbreviation get(Integer id) {
		// TODO Auto-generated method stub
		return (VehicleMakeAbbreviation) this.sessionFactory.getCurrentSession().get(VehicleMakeAbbreviation.class, id);
	}

	@Override
	public VehicleMakeAbbreviation update(VehicleMakeAbbreviation entity) {
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
	public List<VehicleMakeAbbreviation> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(VehicleMakeAbbreviation.class).list();
	}

	@Override
	public List<VehicleMakeAbbreviation> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleMakeAbbreviation> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleMakeAbbreviation> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleMakeAbbreviation> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleMakeAbbreviation> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleMakeAbbreviation> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleMakeAbbreviation> find(String ParamName, Date date) {
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
	public List<VehicleMakeAbbreviation> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleMakeAbbreviation getVehicleMakeAbbreviationByMake(
			String vehicleMake) {
		// TODO Auto-generated method stub
		return (VehicleMakeAbbreviation) this.sessionFactory.getCurrentSession().createCriteria(VehicleMakeAbbreviation.class).add(Restrictions.eq("make", vehicleMake)).uniqueResult();
	}

	

}
