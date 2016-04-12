package com.deemsys.project.Appointments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.cglib.core.Local;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.opsworks.model.App;
import com.deemsys.project.CallLogs.CallLogsForm;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class AppointmentsDAOImpl implements AppointmentsDAO{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Appointments entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public void merge(Appointments entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public Appointments get(Integer id) {
		// TODO Auto-generated method stub
		return (Appointments) this.sessionFactory.getCurrentSession().get(Appointments.class, id);
	}

	@Override
	public Appointments update(Appointments entity) {
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
	public List<Appointments> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Appointments.class).list();
	}

	@Override
	public List<Appointments> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointments> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointments> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointments> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointments> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointments> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointments> find(String ParamName, Date date) {
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
	public List<Appointments> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Appointments getAppointmentsByAppintementId(Long appointmentId) {
		// TODO Auto-generated method stub
		Appointments appointments=(Appointments) this.sessionFactory.getCurrentSession().createCriteria(Appointments.class).add(Restrictions.eq("appointmentId", appointmentId)).uniqueResult();
		return appointments;
	}

	@Override
	public void deleteAppointmentsByAppointmentId(Long appointmentId) {
		// TODO Auto-generated method stub
		Appointments appointments=this.getAppointmentsByAppintementId(appointmentId);
		if(appointments!=null){
			this.sessionFactory.getCurrentSession().delete(appointments);
		}
	}

	@Override
	public AppointmentsSearchResult searchAppointments(AppointmentSearchForm appointmentSearchForm) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(Appointments.class);
		criteria.createAlias("callLog", "c1");
		criteria.createAlias("clinic", "c2");
		criteria.createAlias("c1.caller", "c3",Criteria.LEFT_JOIN);
		criteria.createAlias("c1.patientCallerAdminMap", "pc1",Criteria.INNER_JOIN);
		criteria.createAlias("pc1.callerAdmin", "ca1",Criteria.INNER_JOIN);
		criteria.createAlias("pc1.patient", "p1",Criteria.INNER_JOIN);
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("appointmentId"),"id");
		projectionList.add(Projections.property("scheduledDate"),"scheduledDate");
		projectionList.add(Projections.property("scheduledDateTime"),"scheduledDateTime");
		projectionList.add(Projections.property("status"),"status");
		projectionList.add(Projections.property("clinic.clinicId"),"clinicId");
		projectionList.add(Projections.property("doctorId"),"doctorId");
		projectionList.add(Projections.property("c3.firstName"),"callerFirstName");
		projectionList.add(Projections.property("c3.lastName"),"callerLastName");
		projectionList.add(Projections.property("ca1.firstName"),"callerAdminFirstName");
		projectionList.add(Projections.property("ca1.lastName"),"callerAdminLastName");
		projectionList.add(Projections.property("c2.clinicName"),"clinicName");
		projectionList.add(Projections.property("p1.patientId"),"patientId");
		projectionList.add(Projections.property("p1.name"),"patientName");
		criteria.setProjection(projectionList);
		
		// Date Between Criterion
		Date monthBegin=new Date();
		Date monthEnd=new Date();
		if(appointmentSearchForm.getMonth()!=13){
			monthBegin = new LocalDate(appointmentSearchForm.getYear(), appointmentSearchForm.getMonth(), 1).toDate();
			monthEnd = new LocalDate(appointmentSearchForm.getYear(), appointmentSearchForm.getMonth(), 1).plusMonths(1)
					.withDayOfMonth(1).minusDays(1).toDate();
		
		System.out.println("Month Beginnn......"+monthBegin);
		System.out.println("Month End......"+monthEnd);
		
		}else{
		
			monthBegin = new LocalDate(appointmentSearchForm.getYear(), 1, 1).toDate();
			monthEnd = new LocalDate(appointmentSearchForm.getYear(), 12, 1).plusMonths(1)
					.withDayOfMonth(1).minusDays(1).toDate();
			System.out.println("Month Beginnn 1......"+monthBegin);
			System.out.println("Month End 1......"+monthEnd);
		}
		
		Criterion monthCriterion=Restrictions.between("scheduledDate", monthBegin, monthEnd);
		criteria.add(monthCriterion);
		
		// Date Search
		if(!appointmentSearchForm.getDate().equals("")){
			Criterion dateCriterion=Restrictions.eq("scheduledDate", InjuryConstants.convertYearFormat(appointmentSearchForm.getDate()));
			criteria.add(dateCriterion);
		}
		// PatientName Search
		if(!appointmentSearchForm.getPatientName().equals("")){
			Criterion patientNameCriterion=Restrictions.like("p1.name",appointmentSearchForm.getPatientName(),MatchMode.ANYWHERE);
			criteria.add(patientNameCriterion);
		}
		
		//Appointment Status
		if(appointmentSearchForm.getStatus()!=0){
			Criterion statusCriterion=Restrictions.eq("status",appointmentSearchForm.getStatus());
			criteria.add(statusCriterion);
		}
		
		// Clinic Id Search
		if(!appointmentSearchForm.getClinicName().equals("")){
			Criterion clinicCriterion=Restrictions.like("c2.clinicName",appointmentSearchForm.getClinicName(),MatchMode.ANYWHERE);
			criteria.add(clinicCriterion);
		}
		
		if(!appointmentSearchForm.getCallerFirstName().equals("")){
			Criterion callerFirstNameCriterion=Restrictions.like("c3.firstName",appointmentSearchForm.getCallerFirstName(),MatchMode.ANYWHERE);
			Criterion callerAdminFirstNameCriterion=Restrictions.like("ca1.firstName",appointmentSearchForm.getCallerFirstName(),MatchMode.ANYWHERE);
			Criterion cadminFirstnameCriterion=Restrictions.isNull("c3.firstName");
			
			criteria.add(Restrictions.or(Restrictions.and(callerAdminFirstNameCriterion, cadminFirstnameCriterion),callerFirstNameCriterion));
		}
		
		if(!appointmentSearchForm.getCallerLastName().equals("")){
			Criterion callerLastNameCriterion=Restrictions.like("c3.lastName",appointmentSearchForm.getCallerLastName(),MatchMode.ANYWHERE);
			Criterion callerAdminLastNameCriterion=Restrictions.like("ca1.lastName",appointmentSearchForm.getCallerLastName(),MatchMode.ANYWHERE);
			Criterion cadminLastnameCriterion=Restrictions.isNull("c3.lastName");
			criteria.add(Restrictions.or(Restrictions.and(callerAdminLastNameCriterion,cadminLastnameCriterion),callerLastNameCriterion));
		}
		
		criteria.add(Restrictions.eq("c1.patientCallerAdminMap.id.callerAdminId", appointmentSearchForm.getCallerAdminId()));
		Integer totalRecords=criteria.setResultTransformer(new AliasToBeanResultTransformer(AppointmentsForm.class)).list().size();
		List<AppointmentsForm> appointmentsForms=criteria.setFirstResult((appointmentSearchForm.getPageNumber()-1)*appointmentSearchForm.getItemsPerPage()).setMaxResults(appointmentSearchForm.getItemsPerPage()).setResultTransformer(new AliasToBeanResultTransformer(AppointmentsForm.class)).list();
		AppointmentsSearchResult appointmentsSearchResult=new AppointmentsSearchResult();
		appointmentsSearchResult.setTotalRecords(totalRecords);
		appointmentsSearchResult.setAppointmentsForms(appointmentsForms);
		return appointmentsSearchResult;
	}

	@Override
	public Integer getAppointmentsCount(Integer callerAdminId) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(Appointments.class);
		criteria.createAlias("callLog", "c1");
		criteria.add(Restrictions.eq("c1.patientCallerAdminMap.id.callerAdminId", callerAdminId));
		List<Appointments> appointments=criteria.list();
		return appointments.size();
	}

	@Override
	public Integer changeAppointmentStatus(Long appointmentId,Integer status) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("Update Appointments set status="+status+" where appointmentId="+appointmentId);
		query.executeUpdate();
		return 1;
	}

	@Override
	public AppointmentsForm getAppointmentsByAppointmentIdWithFullDetails(
			Long appointmentId) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(Appointments.class);
		criteria.createAlias("callLog", "c1");
		criteria.createAlias("clinic", "c2");
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("appointmentId"),"id");
		projectionList.add(Projections.property("scheduledDate"),"scheduledDate");
		projectionList.add(Projections.property("scheduledDateTime"),"scheduledDateTime");
		projectionList.add(Projections.property("status"),"status");
		projectionList.add(Projections.property("clinic.clinicId"),"clinicId");
		projectionList.add(Projections.property("doctorId"),"doctorId");
		projectionList.add(Projections.property("c2.clinicName"),"clinicName");
		criteria.setProjection(projectionList);
		
		criteria.add(Restrictions.eq("appointmentId", appointmentId));
		
		AppointmentsForm appointmentsForm=(AppointmentsForm) criteria.setResultTransformer(new AliasToBeanResultTransformer(AppointmentsForm.class)).uniqueResult();
		
		return appointmentsForm;
	}

	@Override
	public void deleteAppointmentsByCalllogId(Long calllogId) {
		// TODO Auto-generated method stub
		Appointments appointments=(Appointments) this.sessionFactory.getCurrentSession().createCriteria(Appointments.class).add(Restrictions.eq("callLog.callLogId", calllogId)).uniqueResult();
		if(appointments!=null){
			this.sessionFactory.getCurrentSession().delete(appointments);
		}
	}
	
}
