package com.deemsys.project.ClinicTimings;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.ClinicTimings;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class ClinicTimingsDAOImpl implements ClinicTimingsDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(ClinicTimings entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
		
	}

	@Override
	public void merge(ClinicTimings entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClinicTimings get(Integer id) {
		// TODO Auto-generated method stub
		ClinicTimings clinicTimings=(ClinicTimings) this.sessionFactory.getCurrentSession().get(ClinicTimings.class, id);
		return clinicTimings;
	}

	@Override
	public ClinicTimings update(ClinicTimings entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClinicTimings> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> find(String ParamName, Date date) {
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
	public List<ClinicTimings> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClinicTimings> getClinicTimings(Integer clinicId) {
		// TODO Auto-generated method stub
		List<ClinicTimings> clinicTimings=this.sessionFactory.getCurrentSession().createCriteria(ClinicTimings.class).add(Restrictions.eq("id.clinicId", clinicId)).list();
		return clinicTimings;
	}

}
