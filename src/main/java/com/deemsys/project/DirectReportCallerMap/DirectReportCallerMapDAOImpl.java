package com.deemsys.project.DirectReportCallerMap;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.DirectReportCallerAdminMap;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class DirectReportCallerMapDAOImpl implements DirectReportCallerMapDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(DirectReportCallerAdminMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(DirectReportCallerAdminMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public DirectReportCallerAdminMap get(Integer id) {
		// TODO Auto-generated method stub
		return (DirectReportCallerAdminMap) this.sessionFactory.getCurrentSession().get(DirectReportCallerAdminMap.class, id);
	}

	@Override
	public DirectReportCallerAdminMap update(DirectReportCallerAdminMap entity) {
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
	public List<DirectReportCallerAdminMap> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(DirectReportCallerAdminMap.class).list();
	}

	@Override
	public List<DirectReportCallerAdminMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DirectReportCallerAdminMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DirectReportCallerAdminMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DirectReportCallerAdminMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DirectReportCallerAdminMap> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DirectReportCallerAdminMap> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DirectReportCallerAdminMap> find(String ParamName, Date date) {
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
	public List<DirectReportCallerAdminMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectReportCallerAdminMap getPatientMapsByLawyerAdminId(
			String crashId, Integer callerAdminId) {
		// TODO Auto-generated method stub
		return (DirectReportCallerAdminMap) this.sessionFactory.getCurrentSession().createCriteria(DirectReportCallerAdminMap.class).add(Restrictions.and(Restrictions.eq("id.crashId", crashId),Restrictions.eq("id.callerAdminId", callerAdminId))).uniqueResult();
		
	}

	

}
