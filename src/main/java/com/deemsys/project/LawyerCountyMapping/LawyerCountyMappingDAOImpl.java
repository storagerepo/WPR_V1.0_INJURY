package com.deemsys.project.LawyerCountyMapping;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.LawyerCountyMapping;
@Repository
public class LawyerCountyMappingDAOImpl implements LawyerCountyMappingDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(LawyerCountyMapping entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(LawyerCountyMapping entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LawyerCountyMapping get(Integer id) {
		// TODO Auto-generated method stub
		LawyerCountyMapping lawyerCountyMapping=(LawyerCountyMapping) this.sessionFactory.getCurrentSession().get(LawyerCountyMapping.class, id);
		return lawyerCountyMapping;
	}

	@Override
	public LawyerCountyMapping update(LawyerCountyMapping entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		LawyerCountyMapping lawyerCountyMapping=this.get(id);
		if(lawyerCountyMapping!=null){
			this.sessionFactory.getCurrentSession().delete(lawyerCountyMapping);
		}
	}

	@Override
	public List<LawyerCountyMapping> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(String queryString,
			String[] paramNames, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(String ParamName, Date date1,
			Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> find(String ParamName, Date date) {
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
	public List<LawyerCountyMapping> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMapping> getLaweCountyMappingsByLawyerId(
			Integer lawyerId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<LawyerCountyMapping> lawyerCountyMappings=this.sessionFactory.getCurrentSession().createCriteria(LawyerCountyMapping.class).add(Restrictions.eq("lawyers.id", lawyerId)).list();
		return lawyerCountyMappings;
	}

	
}
