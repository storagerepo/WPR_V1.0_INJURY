package com.deemsys.project.CallerAdminCountyMapping;

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
import com.deemsys.project.entity.CallerAdminCountyMap;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Repository
public class CallerAdminCountyMapDAOImpl implements CallerAdminCountyMapDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(CallerAdminCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(CallerAdminCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public CallerAdminCountyMap get(Integer id) {
		// TODO Auto-generated method stub
		CallerAdminCountyMap callerAdminCountyMap=(CallerAdminCountyMap) this.sessionFactory.getCurrentSession().get(CallerAdminCountyMap.class, id);
		return callerAdminCountyMap;
	}

	@Override
	public CallerAdminCountyMap update(CallerAdminCountyMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CallerAdminCountyMap> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(String queryString,
			String[] paramNames, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(String ParamName, Date date1,
			Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> find(String ParamName, Date date) {
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
	public List<CallerAdminCountyMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdminCountyMap> getCallerAdminCountyMapByCallerAdminId(
			Integer callerAdminId) {
		// TODO Auto-generated method stub
		List<CallerAdminCountyMap> callerAdminCountyMaps=this.sessionFactory.getCurrentSession().createCriteria(CallerAdminCountyMap.class).add(Restrictions.eq("id.callerAdminId", callerAdminId)).list();
		return callerAdminCountyMaps;
	}
	
	@Override
	public List<CountyList> getCountyListByCallerAdminId(
			Integer callerAdminId) {
		
		Session session=this.sessionFactory.getCurrentSession();
		
		Criteria criteria=session.createCriteria(CallerAdminCountyMap.class);		
		criteria.createAlias("county", "c1");
		
		criteria.add(Restrictions.eq("id.callerAdminId", callerAdminId));
		
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("c1.countyId"),"countyId");
		projectionList.add(Projections.property("c1.name"),"countyName");
		
		criteria.setProjection(projectionList);
		
		@SuppressWarnings("unchecked")
		List<CountyList> countyLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(CountyList.class)).list();
		
		return countyLists;
		
		
	}

	@Override
	public void deleteCallerAdminCountyMapByCallerAdminIdAndCountyId(
			Integer callerAdminId, Integer countyId) {
		// TODO Auto-generated method stub
		CallerAdminCountyMap callerAdminCountyMap=(CallerAdminCountyMap) this.sessionFactory.getCurrentSession().createCriteria(CallerAdminCountyMap.class).add(Restrictions.and(Restrictions.eq("id.callerAdminId", callerAdminId), Restrictions.eq("id.countyId", countyId))).uniqueResult();
		if(callerAdminCountyMap!=null){
			this.sessionFactory.getCurrentSession().delete(callerAdminCountyMap);
		}
	}

	@Override
	public void deleteCallerAdminCountyMapByCallerAdminId(Integer callerAdminId) {
		// TODO Auto-generated method stub
		List<CallerAdminCountyMap> callerAdminCountyMaps=this.sessionFactory.getCurrentSession().createCriteria(CallerAdminCountyMap.class).add(Restrictions.eq("id.callerAdminId", callerAdminId)).list();
		for (CallerAdminCountyMap callerAdminCountyMap : callerAdminCountyMaps) {
			this.sessionFactory.getCurrentSession().delete(callerAdminCountyMap);
		}
	}

}
