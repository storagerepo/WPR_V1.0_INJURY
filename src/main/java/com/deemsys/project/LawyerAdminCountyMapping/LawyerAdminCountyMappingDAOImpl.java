package com.deemsys.project.LawyerAdminCountyMapping;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.LawyerAdminCountyMap;
import com.deemsys.project.entity.LawyerCountyMap;

@Repository
public class LawyerAdminCountyMappingDAOImpl implements LawyerAdminCountyMappingDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(LawyerAdminCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(LawyerAdminCountyMap entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LawyerAdminCountyMap get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LawyerAdminCountyMap update(LawyerAdminCountyMap entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LawyerAdminCountyMap> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(String queryString,
			String[] paramNames, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(String ParamName, Date date1,
			Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> find(String ParamName, Date date) {
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
	public List<LawyerAdminCountyMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdminCountyMap> getLawyerAdminCountyMappingsByLawyerAdminId(
			Integer lawyerAdminId) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<LawyerAdminCountyMap> lawyerAdminCountyMappings=this.sessionFactory.getCurrentSession().createCriteria(LawyerAdminCountyMap.class).add(Restrictions.eq("lawyerAdmin.id", lawyerAdminId)).list();
		return lawyerAdminCountyMappings;
		
	}

	@Override
	public void deleteLawyerAdminCountyMappingsByLawyerAdminIdAndCountyId(
			Integer lawyerAdminId, Integer countyId) {
		// TODO Auto-generated method stub
		LawyerAdminCountyMap lawyerAdminCountyMap=(LawyerAdminCountyMap) this.sessionFactory.getCurrentSession().createCriteria(LawyerAdminCountyMap.class).add(Restrictions.and(Restrictions.eq("county.id", countyId),Restrictions.eq("lawyerAdmin.id", lawyerAdminId))).uniqueResult();
		this.sessionFactory.getCurrentSession().delete(lawyerAdminCountyMap);
		
		
	}

	@Override
	public void deleteLawyerAdminCountyMappingsByLawyerAdminId(
			Integer lawyerAdminId) {
		// TODO Auto-generated method stub
		
		LawyerAdminCountyMap lawyerAdminCountyMap=(LawyerAdminCountyMap) this.sessionFactory.getCurrentSession().createCriteria(LawyerAdminCountyMap.class).add(Restrictions.eq("lawyerAdmin.id", lawyerAdminId)).uniqueResult();
		this.sessionFactory.getCurrentSession().delete(lawyerAdminCountyMap);
		
	}

}
