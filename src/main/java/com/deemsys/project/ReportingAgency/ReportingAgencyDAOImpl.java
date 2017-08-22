package com.deemsys.project.ReportingAgency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.ReportingAgency;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class ReportingAgencyDAOImpl implements ReportingAgencyDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(ReportingAgency entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(ReportingAgency entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public ReportingAgency get(Integer id) {
		// TODO Auto-generated method stub
		return (ReportingAgency) this.sessionFactory.getCurrentSession().get(ReportingAgency.class, id);
	}

	@Override
	public ReportingAgency update(ReportingAgency entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer reportingAgencyId) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(reportingAgencyId));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportingAgency> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(ReportingAgency.class).list();
	}

	@Override
	public List<ReportingAgency> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportingAgency> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportingAgency> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportingAgency> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportingAgency> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportingAgency> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportingAgency> find(String ParamName, Date date) {
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
	public List<ReportingAgency> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportingAgency> getReportingAgencyListByCounties(
			Integer[] countyIds) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(ReportingAgency.class);
		
		//OR Condition
		Disjunction countyCondition=Restrictions.disjunction();
		
		countyCondition.add(Restrictions.isNull("county.countyId"));
		
		if(countyIds.length>0)
			countyCondition.add(Restrictions.in("county.countyId", countyIds));
		
		criteria.addOrder(Order.asc("reportingAgencyId")).add(countyCondition);
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportingAgency> getReportingAgencyByCountyAndAgencyName(
			Integer countyId, String agencyName) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(ReportingAgency.class);
		criteria.add(Restrictions.and(Restrictions.eq("county.countyId", countyId), Restrictions.like("reportingAgencyName", agencyName, MatchMode.START)));
		return criteria.list();
	}

	@Override
	public ReportingAgency getReportingAgencyByCodeAndCounty(Integer countyId,
			String agencyCode) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(ReportingAgency.class);
		criteria.add(Restrictions.and(Restrictions.eq("county.countyId", countyId), Restrictions.eq("code", agencyCode)));
		return (ReportingAgency) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReportingAgencyList getReportingAgencyList2(ReportingAgencySearchParamForm reportingAgencySearchParamForm) {
		
		List<ReportingAgencyForm> reportingAgencyForms=new ArrayList<ReportingAgencyForm>();
		Session session=this.sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(ReportingAgency.class);
		
		criteria.createAlias("county", "c1");
		if(reportingAgencySearchParamForm.getCountyId()!=0)
			criteria.add(Restrictions.eq("c1.countyId", reportingAgencySearchParamForm.getCountyId()));
		if(!reportingAgencySearchParamForm.getNcicCode().equals(""))
		criteria.add(Restrictions.like("code", reportingAgencySearchParamForm.getNcicCode(),MatchMode.ANYWHERE));
		if(!reportingAgencySearchParamForm.getReportingAgencyName().equals(""))
			criteria.add(Restrictions.like("reportingAgencyName", reportingAgencySearchParamForm.getReportingAgencyName(),MatchMode.ANYWHERE));
		
		ProjectionList projectionList=Projections.projectionList();
		
		projectionList.add(Projections.alias(Projections.property("reportingAgencyId"), "reportingAgencyId"));
		projectionList.add(Projections.alias(Projections.property("c1.countyId"), "countyId"));
		projectionList.add(Projections.alias(Projections.property("c1.name"), "countyName"));
		projectionList.add(Projections.alias(Projections.property("reportingAgencyName"), "reportingAgencyName"));
		projectionList.add(Projections.alias(Projections.property("code"), "code"));
		projectionList.add(Projections.alias(Projections.property("status"), "status"));
		
		Long totalRecords=(Long) criteria.setProjection(Projections.count("reportingAgencyId")).uniqueResult();
		criteria.setProjection(projectionList);
		
		reportingAgencyForms=criteria.setResultTransformer(new AliasToBeanResultTransformer(ReportingAgencyForm.class)).addOrder(Order.asc("countyName")).setFirstResult((reportingAgencySearchParamForm.getPageNumber()-1)*reportingAgencySearchParamForm.getRecordsPerPage()).setMaxResults(reportingAgencySearchParamForm.getRecordsPerPage()).list();

		ReportingAgencyList reportingAgencyList=new ReportingAgencyList(totalRecords, reportingAgencyForms);
		return reportingAgencyList;
	
	}

	@Override
	public Integer checkNcicCode(String ncicCode,Integer reportingAgencyId,Integer countyId) {
		// TODO Auto-generated method stub
		
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(ReportingAgency.class);
		
		//Check User name
		criteria.add(Restrictions.eq("code", ncicCode));
		criteria.add(Restrictions.eq("county.countyId", countyId));
		
		//Skip for Add Edit
		if(reportingAgencyId!=null)
			criteria.add(Restrictions.ne("reportingAgencyId",reportingAgencyId));
		
		ReportingAgency reportingAgency=(ReportingAgency) criteria.uniqueResult();
		
		if(reportingAgency!=null){
			return 1;
		}else{
			return 0;
		}
	}

	


	

	

}
