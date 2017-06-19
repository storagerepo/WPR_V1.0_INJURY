package com.deemsys.project.CrashReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.taglib.html.RewriteTag;
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
import com.deemsys.project.login.LoginService;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
@Repository
public class CrashReportDAOImpl implements CrashReportDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	LoginService loginService;
	
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
	public CrashReportList searchCrashReports(CrashReportSearchForm crashReportSearchForm) {
		// TODO Auto-generated method stub
		List<CrashReportForm> crashReportForms=new ArrayList<CrashReportForm>();
		
		if(!crashReportSearchForm.getNumberOfDays().equals("0")){
			if(!crashReportSearchForm.getCrashFromDate().equals("")){
				crashReportSearchForm.setCrashToDate(InjuryConstants.getToDateByAddingNumberOfDays(crashReportSearchForm.getCrashFromDate(), Integer.parseInt(crashReportSearchForm.getNumberOfDays())));
			}
		}
		
		Session session=this.sessionFactory.getCurrentSession();
		
		Criteria criteria=session.createCriteria(CrashReport.class);
		
		criteria.createAlias("county", "c1");
		criteria.createAlias("crashReportError", "e1");
		// Join Police Agency
		criteria.createAlias("policeAgency", "pd");
		
		//Check Local Report Number
		if(!crashReportSearchForm.getLocalReportNumber().equals("")){
			criteria.add(Restrictions.like("localReportNumber", crashReportSearchForm.getLocalReportNumber(),MatchMode.ANYWHERE));
		}
		if(crashReportSearchForm.getCountyId().length>0){
			criteria.add(Restrictions.in("c1.countyId",crashReportSearchForm.getCountyId()));
		}
		if(!crashReportSearchForm.getCrashFromDate().equals("")){
			criteria.add(Restrictions.between("crashDate", InjuryConstants.convertYearFormat(crashReportSearchForm.getCrashFromDate()), InjuryConstants.convertYearFormat(crashReportSearchForm.getCrashToDate())));
		}
		if(!crashReportSearchForm.getAddedFromDate().equals("")){
			criteria.add(Restrictions.between("addedDate", InjuryConstants.convertYearFormat(crashReportSearchForm.getAddedFromDate()), InjuryConstants.convertYearFormat(crashReportSearchForm.getAddedToDate())));
		}
		if(!crashReportSearchForm.getCrashId().equals("")){
			criteria.add(Restrictions.eq("crashId", crashReportSearchForm.getCrashId()));
		}
		
		if(crashReportSearchForm.getIsRunnerReport()!=null&&crashReportSearchForm.getIsRunnerReport()!=-1){
			if(crashReportSearchForm.getIsRunnerReport()==0){
				Criterion isRunnerReportCriterion=Restrictions.or(Restrictions.eq("isRunnerReport", 2),Restrictions.eq("isRunnerReport", 0));
				criteria.add(Restrictions.or(isRunnerReportCriterion, Restrictions.eq("isRunnerReport", 4)));
			}else{
				criteria.add(Restrictions.eq("isRunnerReport", crashReportSearchForm.getIsRunnerReport()));
			}
		}
		
		// Report From Criterion
		if(crashReportSearchForm.getReportFrom()!=null&&crashReportSearchForm.getReportFrom()!=-1){
			Criterion reportFromCriterion=Restrictions.eq("pd.mapId", crashReportSearchForm.getReportFrom());
			criteria.add(reportFromCriterion);
		}
		
		String role=loginService.getCurrentRole();
		
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)){
			criteria.createAlias("directReportCallerAdminMaps", "dcl1", Criteria.LEFT_JOIN,Restrictions.eq("dcl1.id.callerAdminId", crashReportSearchForm.getCallerAdminId()));
			
			// Is Archived Status
			if(crashReportSearchForm.getIsArchived()==0){
				criteria.add(Restrictions.or(Restrictions.eq("dcl1.isArchived", crashReportSearchForm.getIsArchived()), Restrictions.isNull("dcl1.isArchived")));
			}else{
				criteria.add(Restrictions.eq("dcl1.isArchived", crashReportSearchForm.getIsArchived()));
			}
			
			// Archived From and To Date
			if(!crashReportSearchForm.getArchivedFromDate().equals("")){
				Criterion criterion = Restrictions.between("dcl1.archivedDate", InjuryConstants.convertYearFormat(crashReportSearchForm.getArchivedFromDate()), InjuryConstants.convertYearFormat(crashReportSearchForm.getArchivedToDate()));
				criteria.add(criterion);
			}
			
			// Direct Report Status
			if(crashReportSearchForm.getDirectReportStatus()!=null&&crashReportSearchForm.getDirectReportStatus()!=-1){
				if(crashReportSearchForm.getDirectReportStatus()==0){
					Criterion directReportSatusCriterion=Restrictions.isNull("dcl1.status");
					criteria.add(directReportSatusCriterion);
				}else{
					Criterion directReportSatusCriterion=Restrictions.eq("dcl1.status", crashReportSearchForm.getDirectReportStatus());
					criteria.add(directReportSatusCriterion);
				}
			}
			
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
			criteria.createAlias("directReportLawyerAdminMaps", "dcl1", Criteria.LEFT_JOIN,Restrictions.eq("dcl1.id.lawyerAdminId", crashReportSearchForm.getLawyerAdminId()));
			
			// Is Archived Status
			if(crashReportSearchForm.getIsArchived()==0){
				criteria.add(Restrictions.or(Restrictions.eq("dcl1.isArchived", crashReportSearchForm.getIsArchived()), Restrictions.isNull("dcl1.isArchived")));
			}else{
				criteria.add(Restrictions.eq("dcl1.isArchived", crashReportSearchForm.getIsArchived()));
			}
			
			// Archived From and To Date
			if(!crashReportSearchForm.getArchivedFromDate().equals("")){
				Criterion criterion = Restrictions.between("dcl1.archivedDate", InjuryConstants.convertYearFormat(crashReportSearchForm.getArchivedFromDate()), InjuryConstants.convertYearFormat(crashReportSearchForm.getArchivedToDate()));
				criteria.add(criterion);
			}
			
			// Direct Report Status
			if(crashReportSearchForm.getDirectReportStatus()!=null&&crashReportSearchForm.getDirectReportStatus()!=-1){
				if(crashReportSearchForm.getDirectReportStatus()==0){
					Criterion directReportSatusCriterion=Restrictions.isNull("dcl1.status");
					criteria.add(directReportSatusCriterion);
				}else{
					Criterion directReportSatusCriterion=Restrictions.eq("dcl1.status", crashReportSearchForm.getDirectReportStatus());
					criteria.add(directReportSatusCriterion);
				}
			}
		}
		
		//Projections
		ProjectionList projectionList=Projections.projectionList();
		
		//projectionList.add(Projections.alias(Projections.property("crashReportId"), "crashReportId"));

		projectionList.add(Projections.alias(Projections.property("isRunnerReport"), "isRunnerReport"));
		projectionList.add(Projections.alias(Projections.property("pd.mapId"), "reportFrom"));
		projectionList.add(Projections.alias(Projections.property("pd.name"), "reportFromDepartment"));
		projectionList.add(Projections.alias(Projections.property("e1.description"), "crashReportError"));
		projectionList.add(Projections.alias(Projections.property("localReportNumber"), "localReportNumber"));
		projectionList.add(Projections.alias(Projections.property("crashId"), "crashId"));
		projectionList.add(Projections.alias(Projections.property("crashDate"), "crashDate"));
		projectionList.add(Projections.alias(Projections.property("c1.name"), "county"));
		projectionList.add(Projections.alias(Projections.property("addedDate"), "addedDate"));
		projectionList.add(Projections.alias(Projections.property("filePath"), "filePath"));
		projectionList.add(Projections.alias(Projections.property("oldFilePath"), "oldFilePath"));
		projectionList.add(Projections.alias(Projections.property("numberOfPatients"), "numberOfPatients"));
		projectionList.add(Projections.alias(Projections.property("status"), "status"));
		
		if(!role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			projectionList.add(Projections.alias(Projections.property("dcl1.isArchived"), "isArchived"));
			projectionList.add(Projections.alias(Projections.property("dcl1.archivedDate"), "archivedDate"));
			projectionList.add(Projections.alias(Projections.property("dcl1.archivedDateTime"), "archivedDateTime"));
			projectionList.add(Projections.alias(Projections.property("dcl1.status"), "directReportStatus"));
		}
		
		Long totalNoOfRecords= (Long) criteria.setProjection(Projections.count("crashId")).uniqueResult();
		
		criteria.setProjection(projectionList);
		if(!role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			criteria.addOrder(Order.desc("addedDate"));
			criteria.addOrder(Order.desc("dcl1.archivedDateTime"));
		}else{
			criteria.addOrder(Order.desc("addedDate"));
		}
		criteria.addOrder(Order.desc("localReportNumber"));
		
				//criteria.setResultTransformer(new AliasToBeanResultTransformer(CrashReportForm.class)).list().size();
		//new AliasToBeanConstructorResultTransformer(CrashReportForm.class.getConstructors()[1])
		
		crashReportForms=criteria.setResultTransformer(new AliasToBeanResultTransformer(CrashReportForm.class)).setFirstResult((crashReportSearchForm.getPageNumber()-1)*crashReportSearchForm.getRecordsPerPage()).setMaxResults(crashReportSearchForm.getRecordsPerPage()).list();
				
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
	public CrashReport getCrashReportForChecking(String localReportNumber,
			String crashDate, Integer countyId, Integer isCheckAll) {
		// TODO Auto-generated method stub
		// 1 is Runner Report
		Criterion reportNumberAndCrashDate=Restrictions.and(Restrictions.eq("localReportNumber", localReportNumber), Restrictions.eq("crashDate", InjuryConstants.convertYearFormat(crashDate)));
		Criterion countyAndReportCrashDate=Restrictions.and(reportNumberAndCrashDate, Restrictions.eq("county.countyId", countyId));
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(CrashReport.class);
		if(isCheckAll!=1){
			Criterion isRunnerReportCriterion=Restrictions.or(Restrictions.eq("isRunnerReport", 1), Restrictions.eq("isRunnerReport", 3));
			criteria.add(Restrictions.and(countyAndReportCrashDate,isRunnerReportCriterion));
		}else{
			criteria.add(countyAndReportCrashDate);
		}
		return (CrashReport) criteria.uniqueResult();
		
	}

	@Override
	public void updateCrashReportByQuery(String oldCrashId,
			String newCrashId, Integer crashReportErrorId, String filePath,
			Integer isRunnerReport) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("update CrashReport set crashId='"+newCrashId+"', oldFilePath=filePath, filePath='"+filePath+"',crashReportError.crashReportErrorId='"+crashReportErrorId+"', isRunnerReport='"+isRunnerReport+"' where crash_id='"+oldCrashId+"' ").executeUpdate();
	}
	
	@Override
	public void updateCrashReportFileName(String CrashId, String filePath) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("update CrashReport set filePath='"+filePath+"' where crash_id='"+CrashId+"' ").executeUpdate();
	}

}
