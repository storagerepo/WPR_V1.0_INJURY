package com.deemsys.project.LawyerAdminCountyMapping;

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
import com.deemsys.project.entity.LawyerAdminCountyMap;

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

	@SuppressWarnings("unchecked")
	@Override
	public void deleteLawyerAdminCountyMappingsByLawyerAdminId(
			Integer lawyerAdminId) {
		// TODO Auto-generated method stub
		
		List<LawyerAdminCountyMap> lawyerAdminCountyMaps= this.sessionFactory.getCurrentSession().createCriteria(LawyerAdminCountyMap.class).add(Restrictions.eq("lawyerAdmin.id", lawyerAdminId)).list();
		
		for (LawyerAdminCountyMap lawyerAdminCountyMap : lawyerAdminCountyMaps) {
			this.sessionFactory.getCurrentSession().delete(lawyerAdminCountyMap);
		}
	}

	@Override
	public List<CountyList> getCountyListByLawyerAdminId(
			Integer lawyerAdminId) {
		
		Session session=this.sessionFactory.getCurrentSession();
		
		Criteria criteria=session.createCriteria(LawyerAdminCountyMap.class);		
		criteria.createAlias("county", "c1");
		// Reporting Agency Count List
		criteria.createAlias("c1.reportingAgencies", "r1");
		
		criteria.add(Restrictions.eq("id.lawyerAdminId", lawyerAdminId));
		
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("c1.countyId"),"countyId");
		projectionList.add(Projections.property("c1.name"),"countyName");
		projectionList.add(Projections.count("r1.reportingAgencyId"),"reportingAgencyCount");
		projectionList.add(Projections.groupProperty("r1.county.countyId"));
		
		criteria.setProjection(projectionList);
		
		@SuppressWarnings("unchecked")
		List<CountyList> countyLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(CountyList.class)).list();
		
		return countyLists;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LawyerAdminCountyMap> getLawyerAdminCountyMappingsByCountyId(
			Integer countyId) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(LawyerAdminCountyMap.class).add(Restrictions.eq("id.countyId", countyId)).list();
	}
	
}
