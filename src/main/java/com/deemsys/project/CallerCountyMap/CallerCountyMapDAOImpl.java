package com.deemsys.project.CallerCountyMap;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.CallerCountyMap;

@Repository
public class CallerCountyMapDAOImpl implements CallerCountyMapDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(CallerCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(CallerCountyMap entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CallerCountyMap get(Integer id) {
		// TODO Auto-generated method stub
		CallerCountyMap callerCountyMap=(CallerCountyMap) this.sessionFactory.getCurrentSession().get(CallerCountyMap.class, id);
		return callerCountyMap;
	}

	@Override
	public CallerCountyMap update(CallerCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CallerCountyMap> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerCountyMap> find(String ParamName, Date date) {
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
	public List<CallerCountyMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CallerCountyMap> getCallerCountyMapByCallerId(Integer callerId) {
		// TODO Auto-generated method stub
		List<CallerCountyMap> callerCountyMaps=this.sessionFactory.getCurrentSession().createCriteria(CallerCountyMap.class).add(Restrictions.eq("id.callerId", callerId)).list();
		return callerCountyMaps;
	}

	@Override
	public void deleteCallerCountyMapByCallerIdAndCountyId(Integer callerId,
			Integer countyId) {
		// TODO Auto-generated method stub
		CallerCountyMap callerCountyMap=(CallerCountyMap) this.sessionFactory.getCurrentSession().createCriteria(CallerCountyMap.class).add(Restrictions.and(Restrictions.eq("id.countyId", countyId),Restrictions.eq("id.callerId", callerId))).uniqueResult();
		if (callerCountyMap!=null) {
			this.sessionFactory.getCurrentSession().delete(callerCountyMap);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteCallerCountyMapByCallerId(Integer callerId) {
		// TODO Auto-generated method stub
		List<CallerCountyMap> callerCountyMaps=this.sessionFactory.getCurrentSession().createCriteria(CallerCountyMap.class).add(Restrictions.eq("id.callerId", callerId)).list();
		for (CallerCountyMap callerCountyMap : callerCountyMaps) {
			this.sessionFactory.getCurrentSession().delete(callerCountyMap);
		}
	}

	@Override
	public List<CountyList> getCountyListByCallerId(Integer callerId) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		
		Criteria criteria=session.createCriteria(CallerCountyMap.class);		
		criteria.createAlias("county", "c1");
		
		criteria.add(Restrictions.eq("id.callerId", callerId));
		
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("c1.countyId"),"countyId");
		projectionList.add(Projections.property("c1.name"),"countyName");
		
		criteria.setProjection(projectionList);
		
		@SuppressWarnings("unchecked")
		List<CountyList> countyLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(CountyList.class)).list();
		
		return countyLists;
	}
	
	
}
