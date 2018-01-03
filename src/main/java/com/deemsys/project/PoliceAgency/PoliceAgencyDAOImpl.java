package com.deemsys.project.PoliceAgency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.PoliceAgency;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class PoliceAgencyDAOImpl implements PoliceAgencyDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(PoliceAgency entity) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.save(entity);
		session.flush();
	}

	@Override
	public void merge(PoliceAgency entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public PoliceAgency get(Integer id) {
		// TODO Auto-generated method stub
		return (PoliceAgency) this.sessionFactory.getCurrentSession().get(PoliceAgency.class, id);
	}

	@Override
	public PoliceAgency update(PoliceAgency entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(id));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceAgency> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(PoliceAgency.class).list();
	}

	@Override
	public List<PoliceAgency> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceAgency> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceAgency> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceAgency> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceAgency> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceAgency> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceAgency> find(String ParamName, Date date) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceAgency> getActiveList() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(PoliceAgency.class).add(Restrictions.eq("reportStatus", 1)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceAgency> getPoliceAgenciesBystatus(Integer status) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(PoliceAgency.class);
		if(status==3){
			criteria.add(Restrictions.or(Restrictions.eq("status", status), Restrictions.eq("mapId", 0)));
		}else{
			criteria.add(Restrictions.eq("status", status));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceAgency> getPoliceAgenciesForScheduler(Integer schedulerType) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(PoliceAgency.class).add(Restrictions.eq("schedulerType", schedulerType)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceAgency> searchPoliceDepartments(Integer countyParam, Integer reportPullingTypeParam, Integer reportStatus) {
		 
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(PoliceAgency.class);
		if(countyParam!=0)
		{
			criteria.add(Restrictions.eq("county.countyId", countyParam));
		}
		
		if(reportPullingTypeParam!=0)
		{
			criteria.add(Restrictions.eq("schedulerType", reportPullingTypeParam));
		}else
		{
			criteria.add(Restrictions.ne("schedulerType", 0));
		}
		
		if(reportStatus!=-1){
			criteria.add(Restrictions.eq("reportStatus", reportStatus));
		}
		
		return criteria.list();
	}

	@Override
	public Integer getMaximumMapId(Integer schedularType) {
		
		
		return(Integer) this.sessionFactory.getCurrentSession().createCriteria(PoliceAgency.class).setProjection( Projections.max("mapId")).add(Restrictions.eq("schedulerType",schedularType)).uniqueResult()+1;
		
	}

	@Override
	public void updateMapId(Integer oldMapId,Integer newMapId) {
		
		Session session=this.sessionFactory.getCurrentSession();
		String hql="UPDATE PoliceAgency set mapId = :newMapId WHERE mapId = :oldMapId";
		Query query=session.createQuery(hql);
		query.setParameter("oldMapId",oldMapId);
		query.setParameter("newMapId", newMapId);
		query.executeUpdate();
	}

}
