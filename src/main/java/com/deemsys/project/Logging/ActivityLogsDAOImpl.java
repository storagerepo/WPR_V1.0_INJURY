package com.deemsys.project.Logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.ActivityLogs;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class ActivityLogsDAOImpl implements ActivityLogsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(ActivityLogs entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(ActivityLogs entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public ActivityLogs get(Integer id) {
		// TODO Auto-generated method stub
		return (ActivityLogs) this.sessionFactory.getCurrentSession().get(ActivityLogs.class, id);
	}

	@Override
	public ActivityLogs update(ActivityLogs entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActivityLogs> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityLogs> find(String ParamName, Date date) {
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
	public List<ActivityLogs> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityLogs getActivityLogsBySessionId(String sessionId) {
		// TODO Auto-generated method stub
		return (ActivityLogs) this.sessionFactory.getCurrentSession().createCriteria(ActivityLogs.class).add(Restrictions.eq("id.sessionId", sessionId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityLogsSearchResult searchActivityLogs(
			ActivityLogsSearchForm activityLogsSearchForm) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ActivityLogs.class,"a1");
		criteria.createAlias("a1.usersByLoginId", "u1");
		criteria.createAlias("u1.roles", "r1");
		criteria.createAlias("a1.usersByPrimaryId", "u2");
		criteria.createAlias("u2.roles", "r2");
		criteria.createAlias("a1.activity", "ac1");
		criteria.createAlias("a1.ipAccessList", "ia1");
		if(activityLogsSearchForm.getRoleId()!=null&&!activityLogsSearchForm.getRoleId().equals("")){
			criteria.add(Restrictions.eq("r1.roleId", activityLogsSearchForm.getRoleId()));
		}
		
		if(!activityLogsSearchForm.getLoginId().equals("")){
			criteria.add(Restrictions.like("u1.username", activityLogsSearchForm.getLoginId(),MatchMode.ANYWHERE));
		}
		
		if(!activityLogsSearchForm.getPrimaryLoginId().equals("")){
			criteria.add(Restrictions.like("u2.username", activityLogsSearchForm.getPrimaryLoginId(),MatchMode.ANYWHERE));
		}
		
		if(!activityLogsSearchForm.getIpAddress().equals("")){
			criteria.add(Restrictions.like("ia1.id.ipAddress", activityLogsSearchForm.getIpAddress(),MatchMode.ANYWHERE));
		}
		
		if(!activityLogsSearchForm.getFromDateTime().equals("")&&!activityLogsSearchForm.getToDateTime().equals("")){
			criteria.add(Restrictions.between("a1.id.accessInTime", activityLogsSearchForm.getFromDateTime(), activityLogsSearchForm.getToDateTime()));
		}
		
		if(activityLogsSearchForm.getActivityId()!=null&&!activityLogsSearchForm.getActivityId().equals("")){
			criteria.add(Restrictions.eq("ac1.id", activityLogsSearchForm.getActivityId()));
		}
		
		// For Export Excel
		if(activityLogsSearchForm.getIsSkipMyIp()&&!activityLogsSearchForm.getSkipMyIpAddress().equals("")){
			criteria.add(Restrictions.ne("ia1.id.ipAddress", activityLogsSearchForm.getSkipMyIpAddress()));
		}
		
		Long totalRecords = (Long) criteria.setProjection(Projections.count("a1.id.sessionId")).uniqueResult();
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("a1.id.sessionId"),"sessionId");
		projectionList.add(Projections.property("r1.roleId"),"roleId");
		projectionList.add(Projections.property("r1.role"),"role");
		projectionList.add(Projections.property("u1.username"),"loginUsername");
		projectionList.add(Projections.property("u1.userId"),"loginId");
		projectionList.add(Projections.property("ac1.name"),"activity");
		projectionList.add(Projections.property("ac1.id"),"activityId");
		projectionList.add(Projections.property("u2.username"),"primaryUsername");
		projectionList.add(Projections.property("u2.userId"),"primaryId");
		projectionList.add(Projections.property("ia1.id.ipAddress"),"ipAddress");
		projectionList.add(Projections.property("a1.id.accessDate"),"accessDateMonthFormat");
		projectionList.add(Projections.property("a1.id.accessInTime"),"accessInTime");
		projectionList.add(Projections.property("a1.id.accessOutTime"),"accessOutTime");
		projectionList.add(Projections.property("a1.id.description"),"description");
		projectionList.add(Projections.property("a1.id.status"),"status");
		criteria.addOrder(Order.desc("a1.id.accessInTime"));
		criteria.setProjection(projectionList);
		List<ActivityLogsForm> activityLogsForms = new ArrayList<ActivityLogsForm>();
		if(activityLogsSearchForm.getExportFormat()!=null&&!activityLogsSearchForm.getExportFormat().equals("")){
			activityLogsForms = criteria.setResultTransformer(new AliasToBeanResultTransformer(ActivityLogsForm.class)).list();
		}else{
			activityLogsForms = criteria.setResultTransformer(new AliasToBeanResultTransformer(ActivityLogsForm.class)).setFirstResult((activityLogsSearchForm.getPageNumber()-1)*activityLogsSearchForm.getItemsPerPage()).setMaxResults(activityLogsSearchForm.getItemsPerPage()).list();
		}
		ActivityLogsSearchResult activityLogsSearchResult = new ActivityLogsSearchResult(activityLogsForms, totalRecords);
		return activityLogsSearchResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityLogsSearchResult getIPAccessList(ActivityLogsSearchForm activityLogsSearchForm) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ActivityLogs.class);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("ipAddress"),"ipAddress");
		projectionList.add(Projections.count("ipAddress"),"accessCount");
		criteria.setProjection(projectionList);
		Long totalRecords=(Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(Projections.groupProperty("ipAddress"));
		List<ActivityLogsForm> activityLogsForms = criteria.setResultTransformer(new AliasToBeanResultTransformer(ActivityLogsForm.class))
												    .setFirstResult((activityLogsSearchForm.getPageNumber()-1)*activityLogsSearchForm.getItemsPerPage())
												    .setMaxResults(activityLogsSearchForm.getItemsPerPage()).list();
		ActivityLogsSearchResult activityLogsSearchResult = new ActivityLogsSearchResult(activityLogsForms, totalRecords);
		return activityLogsSearchResult;
	}

	@Override
	public ActivityLogs getActivityLogsBySessionIdAndActivityId(
			String sessionId, Integer activityId) {
		// TODO Auto-generated method stub
		ActivityLogs activityLogs = (ActivityLogs) this.sessionFactory.getCurrentSession().createCriteria(ActivityLogs.class).add(Restrictions.and(Restrictions.eq("id.sessionId", sessionId), Restrictions.eq("id.activityId", activityId))).uniqueResult();
		return activityLogs;
	}

	@Override
	public void updateActivityLogsBySessionIdAndActivityId(ActivityLogsForm activityLogsForm) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("Update ActivityLogs set id.accessOutTime='"+activityLogsForm.getAccessOutTime()+"' where id.sessionId='"+activityLogsForm.getSessionId()+"' and id.activityId='"+activityLogsForm.getActivityId()+"'").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityLogs> getActivityLogsByIpAddressAndDate(
			String ipAddress, Date accessDate) {
		// TODO Auto-generated method stub
		List<ActivityLogs> activityLogs = this.sessionFactory.getCurrentSession().createCriteria(ActivityLogs.class).add(Restrictions.and(Restrictions.eq("id.ipAddress", ipAddress),Restrictions.eq("id.accessDate", accessDate))).list();
		return activityLogs;
	}

	@Override
	public List<ActivityExportDataCount> exportActivityLogs(
			ActivityLogsSearchForm activityLogsSearchForm) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ActivityLogs.class,"a1");
		
		criteria.createAlias("a1.usersByLoginId", "u1");
		criteria.createAlias("u1.roles", "r1");
		criteria.createAlias("a1.usersByPrimaryId", "u2");
		criteria.createAlias("u2.roles", "r2");
		criteria.createAlias("a1.activity", "ac1");
		criteria.createAlias("a1.ipAccessList", "ia1");
		criteria.createAlias("ia1.ipAddress", "ip1");
		if(activityLogsSearchForm.getRoleId()!=null&&!activityLogsSearchForm.getRoleId().equals("")){
			criteria.add(Restrictions.eq("r1.roleId", activityLogsSearchForm.getRoleId()));
		}
		
		if(activityLogsSearchForm.getLoginId()!=null&&!activityLogsSearchForm.getLoginId().equals("")){
			criteria.add(Restrictions.like("u1.username", activityLogsSearchForm.getLoginId(),MatchMode.ANYWHERE));
		}
		
		if(activityLogsSearchForm.getPrimaryLoginId()!=null&&!activityLogsSearchForm.getPrimaryLoginId().equals("")){
			criteria.add(Restrictions.like("u2.username", activityLogsSearchForm.getPrimaryLoginId(),MatchMode.ANYWHERE));
		}
		
		if(activityLogsSearchForm.getIpAddress()!=null&&!activityLogsSearchForm.getIpAddress().equals("")){
			criteria.add(Restrictions.like("ia1.id.ipAddress", activityLogsSearchForm.getIpAddress(),MatchMode.ANYWHERE));
		}
		
		if(activityLogsSearchForm.getFromDateTime()!=null&&!activityLogsSearchForm.getFromDateTime().equals("")&&!activityLogsSearchForm.getToDateTime().equals("")){
			criteria.add(Restrictions.between("a1.id.accessInTime", activityLogsSearchForm.getFromDateTime(), activityLogsSearchForm.getToDateTime()));
		}
		
		if(activityLogsSearchForm.getActivityId()!=null&&!activityLogsSearchForm.getActivityId().equals("")){
			criteria.add(Restrictions.eq("ac1.id", activityLogsSearchForm.getActivityId()));
		}
		
		if(activityLogsSearchForm.getIsSkipMyIp()&&activityLogsSearchForm.getSkipMyIpAddress()!=null){
			criteria.add(Restrictions.ne("ia1.id.ipAddress", activityLogsSearchForm.getSkipMyIpAddress()));
		}
		
		ProjectionList projectionList = Projections.projectionList();
	
		projectionList.add(Projections.property("ac1.name"),"activity");
		projectionList.add(Projections.property("ac1.id"),"activityId");
		projectionList.add(Projections.property("a1.id.accessDate"),"accessDate");
		projectionList.add(Projections.property("u2.username"),"username");
		projectionList.add(Projections.property("a1.id.ipAddress"),"ipAddress");
		projectionList.add(Projections.property("ip1.city"),"ipCity");
		projectionList.add(Projections.property("ip1.region"),"ipRegion");
		projectionList.add(Projections.property("ip1.countryName"),"ipCountry");
		projectionList.add(Projections.count("a1.id.activityId"),"accessCount");
		projectionList.add(Projections.sum("a1.id.recordsCount"),"noOfRecordsExported");
		projectionList.add(Projections.groupProperty("a1.id.activityId"));
		projectionList.add(Projections.groupProperty("a1.id.ipAddress"));
		projectionList.add(Projections.groupProperty("a1.id.accessDate"));
		projectionList.add(Projections.groupProperty("u2.userId"));
		criteria.addOrder(Order.asc("u2.userId"));
		//criteria.addOrder(Order.asc("a1.id.activityId"));
		criteria.addOrder(Order.desc("a1.id.accessDate"));
		criteria.setProjection(projectionList);
		List<ActivityExportDataCount> activityExportDataCounts = criteria.setResultTransformer(new AliasToBeanResultTransformer(ActivityExportDataCount.class)).list();
		
		return activityExportDataCounts;
	}

	@Override
	public void deleteActivityLogsByDate(Date addedDate) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("CALL start_purge_activity_logs(:oldDate)");
		query.setParameter("oldDate", addedDate);
		query.executeUpdate();
	}


}
