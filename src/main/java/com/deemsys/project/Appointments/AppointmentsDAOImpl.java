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
	public Integer updates(Integer id,Integer status) {
		Query query=sessionFactory.getCurrentSession().createQuery("update Appointments set status="+status+" where id="+id);
		query.executeUpdate();
			return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Appointments> todaysAppointment()
	{
		List<Appointments> appointments = new ArrayList<Appointments>();
		  appointments= this.sessionFactory.getCurrentSession().createQuery("FROM Appointments where scheduledDate=CURDATE()").list();
		  return appointments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointments> getByDates(String date) {
		// TODO Auto-generated method stub
		List<Appointments> appointments = new ArrayList<Appointments>();
		appointments= this.sessionFactory.getCurrentSession().createQuery("FROM Appointments where scheduledDate='"+date+"'").list();
		return appointments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointments> getAppointmentsBetweenDates(Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		List<Appointments> appointments=new ArrayList<Appointments>();
		appointments=this.sessionFactory.getCurrentSession().createCriteria(Appointments.class).add(Restrictions.and(Restrictions.ge("scheduledDate", startDate), Restrictions.le("scheduledDate", endDate))).list();
		return appointments;
	}

	@SuppressWarnings("unused")
	@Override
	public List<AppointmentsForm> getAppointmentsBetweenDatesByCallerId(
			String startDate, String endDate,Integer staffId) {
		// TODO Auto-generated method stub
		List<Appointments> appointments=new ArrayList<Appointments>();
		
		Query query =this.sessionFactory.getCurrentSession().createQuery("select s1.id,s1.patients.id,s1.patients.name,s1.scheduledDate,s1.notes,s1.status from Patients s2,Appointments s1 where s2.id=s1.patients.id and s2.staff.id="+staffId+" and (s1.scheduledDate>='"+startDate+"' and s1.scheduledDate<='"+endDate+"')");
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		
		List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		for(Object[] arr : list){
			//appointmentsForm=new AppointmentsForm(Integer.parseInt(arr[0].toString()), Integer.parseInt(arr[1].toString()),arr[2].toString(),arr[3].toString(), arr[4].toString(), Integer.parseInt(arr[5].toString()));
			appointmentsForms.add(appointmentsForm);
				
		}
		return appointmentsForms;
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
	public List<AppointmentsForm> searchAppointments(AppointmentSearchForm appointmentSearchForm) {
		// TODO Auto-generated method stub
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(Appointments.class);
		criteria.createAlias("callLog", "c1");
		criteria.createAlias("clinic", "c2");
		criteria.createAlias("c1.patientCallerAdminMap", "pc1",Criteria.INNER_JOIN);
		criteria.createAlias("pc1.patient", "p1",Criteria.INNER_JOIN);
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("appointmentId"),"id");
		projectionList.add(Projections.property("scheduledDate"),"scheduledDate");
		projectionList.add(Projections.property("status"),"status");
		projectionList.add(Projections.property("clinic.clinicId"),"clinicId");
		projectionList.add(Projections.property("doctorId"),"doctorId");
		projectionList.add(Projections.property("c2.clinicName"),"clinicName");
		projectionList.add(Projections.property("p1.name"),"patientName");
		criteria.setProjection(projectionList);
		
		// Date Between Criterion
		String monthBegin="";
		String monthEnd="";
		if (appointmentSearchForm.getMonth() == 0) {
			monthBegin = new LocalDate().withDayOfMonth(1).toString("MM/dd/YYYY");
			monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1)
					.minusDays(1).toString("MM/dd/YYYY");
		} else {
			monthBegin = new LocalDate(appointmentSearchForm.getYear(), appointmentSearchForm.getMonth(), 1).toString("MM/dd/YYYY");;
			monthEnd = new LocalDate(appointmentSearchForm.getYear(), appointmentSearchForm.getMonth(), 1).plusMonths(1)
					.withDayOfMonth(1).minusDays(1).toString("MM/dd/YYYY");;
		}
		System.out.println("Month Beginnn......"+monthBegin);
		System.out.println("Month End......"+monthEnd);
		Criterion monthCriterion=Restrictions.between("scheduledDate", monthBegin, monthEnd);
		criteria.add(monthCriterion);
		
		// Date Search
		if(!appointmentSearchForm.getDate().equals("")){
			Criterion dateCriterion=Restrictions.like("scheduledDate", appointmentSearchForm.getDate(),MatchMode.ANYWHERE);
			criteria.add(dateCriterion);
		}
		// PatientName Search
		if(!appointmentSearchForm.getPatientName().equals("")){
			Criterion patientNameCriterion=Restrictions.like("p1.patient.name",appointmentSearchForm.getPatientName(),MatchMode.ANYWHERE);
			criteria.add(patientNameCriterion);
		}
		
		//Appointment Status
		if(appointmentSearchForm.getStatus()!=0){
			Criterion statusCriterion=Restrictions.eq("status",appointmentSearchForm.getStatus());
			criteria.add(statusCriterion);
		}
		
		// Clinic Id Search
		if(appointmentSearchForm.getClinicId()!=0){
			Criterion clinicCriterion=Restrictions.eq("c2.clinicId",appointmentSearchForm.getClinicId());
			criteria.add(clinicCriterion);
		}
		criteria.add(Restrictions.eq("c1.patientCallerAdminMap.id.callerAdminId", appointmentSearchForm.getCallerAdminId()));
		Integer totalRecords=criteria.setResultTransformer(new AliasToBeanResultTransformer(AppointmentsForm.class)).list().size();
		List<AppointmentsForm> appointmentsForms=criteria.setFirstResult((appointmentSearchForm.getPageNumber()-1)*appointmentSearchForm.getItemsPerPage()).setMaxResults(appointmentSearchForm.getItemsPerPage()).setResultTransformer(new AliasToBeanResultTransformer(AppointmentsForm.class)).list();
		return appointmentsForms;
	}
	
}
