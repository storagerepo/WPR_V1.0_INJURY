package com.deemsys.project.CallLogs;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.CallLog;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class CallLogsDAOImpl implements CallLogsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(CallLog entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(CallLog entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public CallLog get(Integer id) {
		// TODO Auto-generated method stub
		return (CallLog) this.sessionFactory.getCurrentSession().get(CallLog.class, id);
	}

	@Override
	public CallLog update(CallLog entity) {
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
	public List<CallLog> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(CallLog.class).list();
	}

	@Override
	public List<CallLog> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallLog> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallLog> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallLog> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallLog> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallLog> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallLog> find(String ParamName, Date date) {
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
	public List<CallLog> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallLog getCallLogsByAppointment(Integer appointmentId) {
		// TODO Auto-generated method stub
		CallLog callLogs=(CallLog) this.sessionFactory.getCurrentSession().createCriteria(CallLog.class).add(Restrictions.eq("appointments.id", appointmentId)).uniqueResult();
		return callLogs;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CallLog> getCallLogsByPatientsId(Integer id) {
		// TODO Auto-generated method stub
		return (List<CallLog>)this.sessionFactory.getCurrentSession().createCriteria(CallLog.class).add(Restrictions.eq("patients.id", id)).list();
	}

	@Override
	public List<CallLog> getCallLogsId() {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(CallLog.class)
			    .setProjection(Projections.projectionList()
			      .add(Projections.property("id"), "id"))
			      
			    .setResultTransformer(Transformers.aliasToBean(CallLog.class));

			  @SuppressWarnings("unchecked")
			List<CallLog> list = cr.list();
		return list;
	}

	@Override
	public List<CallLogsForm> getCallLogsByPatientIdAndCallerAdminIdAndCallerId(String patientId,
			Integer callerAdminId,Integer callerId) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(CallLog.class);
		criteria.createAlias("appointmentses", "a1", Criteria.LEFT_JOIN);
		criteria.createAlias("caller", "c1");
		// Add Projections
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("callLogId"), "callLogId");
		projectionList.add(Projections.property("timeStamp"), "timeStamp");
		projectionList.add(Projections.property("response"), "response");
		projectionList.add(Projections.property("notes"), "notes");
		projectionList.add(Projections.property("status"), "status");
		projectionList.add(Projections.property("c1.firstName"), "callerFirstName");
		projectionList.add(Projections.property("c1.lastName"), "callerLastName");
		// Appointment Details
		projectionList.add(Projections.property("a1.appointmentId"), "appointmentId");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.and(Restrictions.eq("patientCallerAdminMap.id.patientId", patientId.getBytes()),Restrictions.eq("patientCallerAdminMap.id.callerAdminId", callerAdminId)));
		
		
		List<CallLogsForm> callLogsForms=criteria.setResultTransformer(new AliasToBeanResultTransformer(CallLogsForm.class)).list();
		return callLogsForms;
	}

	@Override
	public CallLog getCallLogsByCallLogId(Long callLogId) {
		// TODO Auto-generated method stub
		CallLog callLog=(CallLog) this.sessionFactory.getCurrentSession().createCriteria(CallLog.class).add(Restrictions.eq("callLogId", callLogId)).uniqueResult();
		return callLog;
	}


}
