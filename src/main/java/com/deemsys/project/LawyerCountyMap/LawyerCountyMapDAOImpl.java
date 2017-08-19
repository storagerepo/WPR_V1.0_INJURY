package com.deemsys.project.LawyerCountyMap;

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
import com.deemsys.project.entity.LawyerCountyMap;

@Repository
public class LawyerCountyMapDAOImpl implements LawyerCountyMapDAO{

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
		LawyerCountyMap lawyerCountyMap=(LawyerCountyMap) this.sessionFactory.getCurrentSession().get(LawyerCountyMap.class, id);
		return lawyerCountyMap;
	}

	@Override
	public LawyerCountyMap update(LawyerCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
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
	public List<LawyerCountyMap> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerCountyMap> find(String ParamName, Date date1, Date date2) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<LawyerCountyMap> getLawyerCountyMapByLawyerId(Integer lawyerId) {
		// TODO Auto-generated method stub
		List<LawyerCountyMap> lawyerCountyMaps=this.sessionFactory.getCurrentSession().createCriteria(LawyerCountyMap.class).add(Restrictions.eq("id.lawyerId", lawyerId)).list();
		return lawyerCountyMaps;
	}

	@Override
	public void deleteLawyerCountyMapByLawyerIdAndCountyId(Integer lawyerId,
			Integer countyId) {
		// TODO Auto-generated method stub
		LawyerCountyMap lawyerCountyMap=(LawyerCountyMap) this.sessionFactory.getCurrentSession().createCriteria(LawyerCountyMap.class).add(Restrictions.and(Restrictions.eq("id.countyId", countyId),Restrictions.eq("id.lawyerId", lawyerId))).uniqueResult();
		if (lawyerCountyMap!=null) {
			this.sessionFactory.getCurrentSession().delete(lawyerCountyMap);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteLawyerCountyMapByLawyerId(Integer lawyerId) {
		// TODO Auto-generated method stub
		List<LawyerCountyMap> lawyerCountyMaps=this.sessionFactory.getCurrentSession().createCriteria(LawyerCountyMap.class).add(Restrictions.eq("id.lawyerId", lawyerId)).list();
		for (LawyerCountyMap lawyerCountyMap : lawyerCountyMaps) {
			this.sessionFactory.getCurrentSession().delete(lawyerCountyMap);
		}
	}

	@Override
	public List<CountyList> getCountyListByLawyerId(Integer lawyerId) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		
		Criteria criteria=session.createCriteria(LawyerCountyMap.class);		
		criteria.createAlias("county", "c1");
		
		criteria.add(Restrictions.eq("id.lawyerId", lawyerId));
		
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("c1.countyId"),"countyId");
		projectionList.add(Projections.property("c1.name"),"countyName");
		
		criteria.setProjection(projectionList);
		
		@SuppressWarnings("unchecked")
		List<CountyList> countyLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(CountyList.class)).list();
		
		return countyLists;
	}
	
	
}
