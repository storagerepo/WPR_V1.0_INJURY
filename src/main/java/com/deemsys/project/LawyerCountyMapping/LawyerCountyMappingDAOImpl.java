package com.deemsys.project.LawyerCountyMapping;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.LawyerCountyMap;
@Repository
public class LawyerCountyMappingDAOImpl implements LawyerCountyMappingDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(LawyerCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(LawyerCountyMap entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LawyerCountyMap get(Integer id) {
		// TODO Auto-generated method stub
		LawyerCountyMap lawyerCountyMapping=(LawyerCountyMap) this.sessionFactory.getCurrentSession().get(LawyerCountyMap.class, id);
		return lawyerCountyMapping;
	}

	@Override
	public LawyerCountyMap update(LawyerCountyMap entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		LawyerCountyMap lawyerCountyMapping=this.get(id);
		if(lawyerCountyMapping!=null){
			this.sessionFactory.getCurrentSession().delete(lawyerCountyMapping);
		}
	}

	@Override
	public List<LawyerCountyMap> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String queryString,
			String[] paramNames, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String ParamName, Date date1,
			Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String ParamName, Date date) {
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
	public List<LawyerCountyMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> getLawyerCountyMappingsByLawyerId(
			Integer lawyerId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<LawyerCountyMap> lawyerCountyMappings=this.sessionFactory.getCurrentSession().createCriteria(LawyerCountyMap.class).add(Restrictions.eq("lawyers.id", lawyerId)).list();
		return lawyerCountyMappings;
	}

	@Override
	public void deleteLawyerCountyMappingsByLawyerIdAndCountyId(
			Integer lawyerId, Integer countyId) {
		// TODO Auto-generated method stub
		LawyerCountyMap lawyerCountyMap=(LawyerCountyMap) this.sessionFactory.getCurrentSession().createCriteria(LawyerCountyMap.class).add(Restrictions.and(Restrictions.eq("county.id", countyId),Restrictions.eq("lawyers.id", lawyerId))).uniqueResult();
		this.sessionFactory.getCurrentSession().delete(lawyerCountyMap);
		
	}

	
}
