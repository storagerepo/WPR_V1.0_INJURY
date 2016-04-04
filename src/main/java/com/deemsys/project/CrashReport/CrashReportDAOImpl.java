package com.deemsys.project.CrashReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.Patient;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
@Repository
public class CrashReportDAOImpl implements CrashReportDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(CrashReport entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(CrashReport entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public CrashReport get(Integer id) {
		// TODO Auto-generated method stub
		CrashReport crashReport=(CrashReport) this.sessionFactory.getCurrentSession().get(CrashReport.class, id);
		return crashReport;
	}

	@Override
	public CrashReport update(CrashReport entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		CrashReport crashReport=this.get(id);
		if(crashReport!=null){
			this.sessionFactory.getCurrentSession().delete(crashReport);
		}
		
	}

	@Override
	public List<CrashReport> getAll() {
		// TODO Auto-generated method stub
		List<CrashReport> crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).list();
		return crashReports;
	}

	@Override
	public List<CrashReport> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReport> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReport> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReport> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReport> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReport> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReport> find(String ParamName, Date date) {
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
	public List<CrashReport> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrashReportList searchCrashReports(String localReportNumber,
			String crashId, String crashFromDate, String crashToDate,
			String county, String addedFromDate, String addedToDate,
			Integer recordsPerPage, Integer pageNumber) {
		// TODO Auto-generated method stub
		List<CrashReportForm> crashReportForms=new ArrayList<CrashReportForm>();
		
		Session session=this.sessionFactory.getCurrentSession();
		
		Criteria criteria=session.createCriteria(CrashReport.class);
		
		criteria.createAlias("county", "c1");
		criteria.createAlias("crashReportError", "e1");
		
		//Check Local Report Number
		if(localReportNumber!=""){
			criteria.add(Restrictions.like("localReportNumber", localReportNumber,MatchMode.ANYWHERE));
		}
		if(county!=""){
			criteria.add(Restrictions.eq("c1.countyId", Integer.parseInt(county)));
		}
		if(crashFromDate!=""){
			criteria.add(Restrictions.between("crashDate", crashFromDate, crashToDate));
		}
		if(addedFromDate!=""){
			criteria.add(Restrictions.between("addedDate", addedFromDate, addedToDate));
		}
		if(crashId!=""){
			criteria.add(Restrictions.eq("crashId", crashId));
		}
		
		//Projections
		ProjectionList projectionList=Projections.projectionList();
		
		//projectionList.add(Projections.alias(Projections.property("crashReportId"), "crashReportId"));
		projectionList.add(Projections.alias(Projections.property("e1.description"), "crashReportError"));
		projectionList.add(Projections.alias(Projections.property("localReportNumber"), "localReportNumber"));
		projectionList.add(Projections.alias(Projections.property("crashId"), "crashId"));
		projectionList.add(Projections.alias(Projections.property("crashDate"), "crashDate"));
		projectionList.add(Projections.alias(Projections.property("c1.name"), "county"));
		projectionList.add(Projections.alias(Projections.property("addedDate"), "addedDate"));
		projectionList.add(Projections.alias(Projections.property("filePath"), "filePath"));
		projectionList.add(Projections.alias(Projections.property("numberOfPatients"), "numberOfPatients"));
		projectionList.add(Projections.alias(Projections.property("status"), "status"));
		
		criteria.setProjection(projectionList);
		
		Integer totalNoOfRecords=criteria.setResultTransformer(new AliasToBeanResultTransformer(CrashReportForm.class)).list().size();
		
		crashReportForms=criteria.setResultTransformer(new AliasToBeanResultTransformer(CrashReportForm.class)).setFirstResult((pageNumber-1)*recordsPerPage).setMaxResults(recordsPerPage).list();
				
		return new CrashReportList(totalNoOfRecords, crashReportForms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getTotalRecords(String localReportNumber, String crashId,
			String crashFromDate, String crashToDate, String county,
			String addedFromDate, String addedToDate) {
		// TODO Auto-generated method stub
		List<CrashReport> crashReports=new ArrayList<CrashReport>();
		if(localReportNumber.equals("")&&crashId.equals("")&&crashFromDate.equals("")&&crashToDate.equals("")&&county.equals("")&&addedFromDate.equals("")&&addedToDate.equals("")){
		   crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).list();
		}else if(county.equals("")){
			if(!crashFromDate.equals("")){
				Criterion criterion=Restrictions.and(Restrictions.like("localReportNumber", localReportNumber, MatchMode.ANYWHERE), Restrictions.like("crashId", crashId,MatchMode.ANYWHERE));
				Criterion criterion2=Restrictions.and(criterion, Restrictions.between("crashDate", crashFromDate, crashToDate));
				crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).add(criterion2).list();
			}else if(!addedFromDate.equals("")){
				Criterion criterion=Restrictions.and(Restrictions.like("localReportNumber", localReportNumber, MatchMode.ANYWHERE), Restrictions.like("crashId", crashId,MatchMode.ANYWHERE));
				Criterion criterion2=Restrictions.and(criterion, Restrictions.between("addedDate", addedFromDate, addedToDate));
				crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).add(criterion2).list();
			}else if(!crashFromDate.equals("")&&!addedFromDate.equals("")){
				Criterion criterion=Restrictions.and(Restrictions.like("localReportNumber", localReportNumber, MatchMode.ANYWHERE), Restrictions.like("crashId", crashId,MatchMode.ANYWHERE));
				Criterion criterion2=Restrictions.and(criterion, Restrictions.between("addedDate", addedFromDate, addedToDate));
				Criterion criterion3=Restrictions.and(criterion2, Restrictions.between("crashDate", crashFromDate, crashToDate));
				crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).add(criterion3).list();
			}
			else {
				Criterion criterion=Restrictions.and(Restrictions.like("localReportNumber", localReportNumber, MatchMode.ANYWHERE), Restrictions.like("crashId", crashId,MatchMode.ANYWHERE));
				crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).add(criterion).list();
			}
		}else if(!county.equals("")){
			if(!crashFromDate.equals("")){
				crashReports=this.sessionFactory.getCurrentSession().createQuery("from County c1 join c1.crashReports cr1 where (c1.name like '%"+county+"%') and cr1.localReportNumber like '%"+localReportNumber+"%' and cr1.crashId like '%"+crashId+"%' and (cr1.crashDate between '"+crashFromDate+"' and '"+crashToDate+"')").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			}
			else if(!addedFromDate.equals("")){
				crashReports=this.sessionFactory.getCurrentSession().createQuery("from County c1 join c1.crashReports cr1 where (c1.name like '%"+county+"%') and cr1.localReportNumber like '%"+localReportNumber+"%' and cr1.crashId like '%"+crashId+"%' and (cr1.addedDate between '"+addedFromDate+"' and '"+addedToDate+"')").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			}else if(!crashFromDate.equals("")&&!addedFromDate.equals("")){
				crashReports=this.sessionFactory.getCurrentSession().createQuery("from County c1 join c1.crashReports cr1 where (c1.name like '%"+county+"%') and cr1.localReportNumber like '%"+localReportNumber+"%' and cr1.crashId like '%"+crashId+"%' and (cr1.crashDate between '"+crashFromDate+"' and '"+crashToDate+"') and (cr1.addedDate between '"+addedFromDate+"' and '"+addedToDate+"')").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			}
			else{
				crashReports=this.sessionFactory.getCurrentSession().createQuery("from County c1 join c1.crashReports cr1 where (c1.name like '%"+county+"%') and cr1.localReportNumber like '%"+localReportNumber+"%' and cr1.crashId like '%"+crashId+"%'").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			}
		}
		return crashReports.size();
	}
	
	@Override
	public CrashReport getCrashReport(String crashId){
		return (CrashReport) this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).add(Restrictions.eq("crashId",crashId)).uniqueResult();
	}

}
