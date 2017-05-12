package com.deemsys.project.CrashReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
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
			Integer[] countyId, String addedFromDate, String addedToDate,
			Integer recordsPerPage, Integer pageNumber,Integer isRunnerReport) {
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
		if(countyId.length>0){
			criteria.add(Restrictions.in("c1.countyId",countyId));
		}
		if(crashFromDate!=""){
			criteria.add(Restrictions.between("crashDate", InjuryConstants.convertYearFormat(crashFromDate), InjuryConstants.convertYearFormat(crashToDate)));
		}
		if(addedFromDate!=""){
			criteria.add(Restrictions.between("addedDate", InjuryConstants.convertYearFormat(addedFromDate), InjuryConstants.convertYearFormat(addedToDate)));
		}
		if(crashId!=""){
			criteria.add(Restrictions.eq("crashId", crashId));
		}
		
		if(isRunnerReport!=null&&isRunnerReport!=-1){
			if(isRunnerReport==0){
				criteria.add(Restrictions.or(Restrictions.eq("isRunnerReport", 2),Restrictions.eq("isRunnerReport", isRunnerReport)));
			}else{
				criteria.add(Restrictions.eq("isRunnerReport", isRunnerReport));
			}
		}
		
		//Projections
		ProjectionList projectionList=Projections.projectionList();
		
		//projectionList.add(Projections.alias(Projections.property("crashReportId"), "crashReportId"));

		projectionList.add(Projections.alias(Projections.property("isRunnerReport"), "isRunnerReport"));
		projectionList.add(Projections.alias(Projections.property("reportFrom"), "reportFrom"));
		projectionList.add(Projections.alias(Projections.property("e1.description"), "crashReportError"));
		projectionList.add(Projections.alias(Projections.property("localReportNumber"), "localReportNumber"));
		projectionList.add(Projections.alias(Projections.property("crashId"), "crashId"));
		projectionList.add(Projections.alias(Projections.property("crashDate"), "crashDate"));
		projectionList.add(Projections.alias(Projections.property("c1.name"), "county"));
		projectionList.add(Projections.alias(Projections.property("addedDate"), "addedDate"));
		projectionList.add(Projections.alias(Projections.property("filePath"), "filePath"));
		projectionList.add(Projections.alias(Projections.property("numberOfPatients"), "numberOfPatients"));
		projectionList.add(Projections.alias(Projections.property("status"), "status"));
		
		Long totalNoOfRecords= (Long) criteria.setProjection(Projections.count("crashId")).uniqueResult();
		
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.desc("addedDate"));
				//criteria.setResultTransformer(new AliasToBeanResultTransformer(CrashReportForm.class)).list().size();
		//new AliasToBeanConstructorResultTransformer(CrashReportForm.class.getConstructors()[1])
		
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

	@Override
	public void deleteCrashReportByCrashId(String crashId) {
		// TODO Auto-generated method stub
		CrashReport crashReport=this.getCrashReport(crashId);
		if(crashReport!=null){
			this.sessionFactory.getCurrentSession().delete(crashReport);
		}
	}

	@Override
	public Long getLocalReportNumberCount(String localReportNumber) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(CrashReport.class);
		criteria.add(Restrictions.like("localReportNumber", localReportNumber,MatchMode.START));
		Long localReportNumberCount=(Long) criteria.setProjection(Projections.count("crashId")).uniqueResult();
		return localReportNumberCount;
	}
	
	@Override
	public Long getCrashReportCountByLocalReportNumber(String localReportNumber) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(CrashReport.class);
		criteria.add(Restrictions.eq("localReportNumber", localReportNumber));
		Long localReportNumberCount=(Long) criteria.setProjection(Projections.count("crashId")).uniqueResult();
		return localReportNumberCount;
	}

	@Override
	public List<CrashReport> getSixMonthOldCrashReports() {
		// TODO Auto-generated method stub
		LocalDate localDate1=new LocalDate().minusMonths(6);
		String date=localDate1.toString("yyyy-MM-dd");
		System.out.println("Previous 6 Month Date 1......"+date);
		List<CrashReport> crashReports=this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class).add(Restrictions.le("addedDate", date)).list();
		return crashReports;
	}

	@Override
	public String getCrashReportForChecking(String localReportNumber,
			String crashDate, Integer countyId) {
		// TODO Auto-generated method stub
		// 1 is Runner Report
		Criterion reportNumberAndCrashDate=Restrictions.and(Restrictions.eq("localReportNumber", localReportNumber), Restrictions.eq("crashDate", InjuryConstants.convertYearFormat(crashDate)));
		Criterion countyAndReportCrashDate=Restrictions.and(reportNumberAndCrashDate, Restrictions.eq("county.countyId", countyId));
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class);
		criteria.add(Restrictions.and(countyAndReportCrashDate,Restrictions.eq("isRunnerReport", 1)));
		criteria.setProjection(Projections.property("crashId"));
		return (String) criteria.uniqueResult();
		
	}

	@Override
	public void updateCrashReportByQuery(String oldCrashId,
			String newCrashId, Integer crashReportErrorId, String filePath,
			Integer isRunnerReport) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("update CrashReport set crashId='"+newCrashId+"', filePath='"+filePath+"',crashReportError.crashReportErrorId='"+crashReportErrorId+"', isRunnerReport='"+isRunnerReport+"' where crash_id='"+oldCrashId+"' ").executeUpdate();
	}
	
	@Override
	public void updateCrashReportFileName(String CrashId, String filePath) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("update CrashReport set filePath='"+filePath+"' where crash_id='"+CrashId+"' ").executeUpdate();
	}

}
