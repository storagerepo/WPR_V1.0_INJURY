package com.deemsys.project.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.Patient;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class PatientDAOImpl implements PatientDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Patient entity) {
		// TODO Auto-generated method stub
		System.out.println("checking4");
			this.sessionFactory.getCurrentSession().save(entity);
			System.out.println("checking5");
			
	}

	@Override
	public void merge(Patient entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public Patient get(Integer id) {
		// TODO Auto-generated method stub
		return (Patient) this.sessionFactory.getCurrentSession().get(Patient.class, id);
	}

	@Override
	public Patient update(Patient entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(id));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Patient.class).list();
	}

	@Override
	public List<Patient> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> find(String ParamName, Date date) {
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
	public List<Patient> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientListByCallerId(Integer callerId) {
		// TODO Auto-generated method stub
		List<Patient> patient = new ArrayList<Patient>();
		
		patient= this.sessionFactory.getCurrentSession().createQuery("FROM Patient where caller.id="+callerId).list();
		return patient;
	}	
	
	@Override
	public List<AppointmentsForm> getAppointmentListByCallerId(Integer callerId) {
	
		Query query= this.sessionFactory.getCurrentSession().createQuery("select s1.id,s1.patient.id,s1.patient.name,s1.scheduledDate,s1.notes,s1.status from Patient s2,Appointments s1 where s2.id=s1.patient.id and s2.caller.id="+callerId+"");
				
				@SuppressWarnings("unchecked")
				List<Object[]> list = query.list();
	
		List<AppointmentsForm> forms=new ArrayList<AppointmentsForm>();
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		for(Object[] arr : list){
			
			appointmentsForm=new AppointmentsForm(Integer.parseInt(arr[0].toString()), Integer.parseInt(arr[1].toString()),arr[2].toString(),arr[3].toString(), arr[4].toString(), Integer.parseInt(arr[5].toString()));
			forms.add(appointmentsForm);
				
		}
			return forms;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<AppointmentsForm> getTodayAppointmentListByCallerId(Integer callerId) {
	
		Query query= this.sessionFactory.getCurrentSession().createQuery("select s1.id,s1.patient.id,s1.patient.name,s1.scheduledDate,s1.notes,s1.status from Patient s2,Appointments s1 where s2.id=s1.patient.id and s2.caller.id="+callerId+" and s1.scheduledDate=CURDATE()" );
				
				@SuppressWarnings("unchecked")
				List<Object[]> list = query.list();
	
		List<AppointmentsForm> forms=new ArrayList<AppointmentsForm>();
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		for(Object[] arr : list){
			
			appointmentsForm=new AppointmentsForm(Integer.parseInt(arr[0].toString()), Integer.parseInt(arr[1].toString()),arr[2].toString(),arr[3].toString(), arr[4].toString(), Integer.parseInt(arr[5].toString()));
			forms.add(appointmentsForm);
				
		}
			return forms;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<AppointmentsForm> getParticularDayAppointmentListByCallerId(String date,Integer callerId) {
	
		Query query= this.sessionFactory.getCurrentSession().createQuery("select s1.id,s1.patient.id,s1.patient.name,s1.scheduledDate,s1.notes,s1.status from Patient s2,Appointments s1 where s2.id=s1.patient.id and s2.caller.id="+callerId+" and s1.scheduledDate='"+date+"'");
				
				@SuppressWarnings("unchecked")
				List<Object[]> list = query.list();
	
		List<AppointmentsForm> forms=new ArrayList<AppointmentsForm>();
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		for(Object[] arr : list){
			
			appointmentsForm=new AppointmentsForm(Integer.parseInt(arr[0].toString()), Integer.parseInt(arr[1].toString()),arr[2].toString(),arr[3].toString(), arr[4].toString(), Integer.parseInt(arr[5].toString()));
			forms.add(appointmentsForm);
				
		}
			return forms;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Patient> patientFileRead(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releasePatientFromCaller(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update Patient set caller.id = NULL where id="+id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointments> getAppointmentsListByPatientId(Integer patientId) {
		// TODO Auto-generated method stub
		return (List<Appointments>)this.sessionFactory.getCurrentSession().createCriteria(Appointments.class).add(Restrictions.eq("patient.id", patientId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CallLog> getCallLogsListByPatientId(Integer patientId) {
		// TODO Auto-generated method stub
		return (List<CallLog>)this.sessionFactory.getCurrentSession().createCriteria(CallLog.class).add(Restrictions.eq("patient.id", patientId)).list();
	}

	@Override
	public List<Patient> getpatientByDoctorId(Integer doctorId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Patient> patients=this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(Restrictions.eq("doctors.id", doctorId)).list();
		return patients;
	}

	@Override
	public List<Patient> getpatientByClinicId(Integer clinicId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Patient> patients=this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(Restrictions.eq("clinics.clinicId", clinicId)).list();
		return patients;
	}
	
	@Override
	public void removeAssignedDoctor(Integer patientId) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update Patient set doctors.id=NULL where id="+patientId);
		query.executeUpdate();
	}
	
@Override
	public void updatePatientStatus(Integer patientId) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update Patient set patientStatus='2' where id='"+patientId+"'");
		query.executeUpdate();
	
	}

@Override
public List<Patient> getPatientByStatus(Integer patientStatus) {
	// TODO Auto-generated method stub
	@SuppressWarnings("unchecked")
	List<Patient> patient=(List<Patient>) this.sessionFactory.getCurrentSession().createQuery("FROM  Patient WHERE patientStatus='"+patientStatus+"'").list();
	return patient;
}

@Override
public Integer activeStatusByPatientId(Integer id) {
	// TODO Auto-generated method stub
	Query query=this.sessionFactory.getCurrentSession().createQuery("update Patient set patientStatus = 1 where id="+id);
	query.executeUpdate();
	return 0;
}

@Override
public void removeAssignedClinic(Integer patientId) {
	// TODO Auto-generated method stub
	Query query=this.sessionFactory.getCurrentSession().createQuery("update Patient set clinics.clinicId=NULL where id="+patientId);
	query.executeUpdate();
}

@SuppressWarnings("unchecked")
@Override
public List<Patient> getPatientListByLimit(Integer pageNumber,
		Integer itemsPerPage,String name,String phoneNumber,String localReportNumber,String callerName) {
	// TODO Auto-generated method stub
	List<Patient> patient=new ArrayList<Patient>();
	if(name.equals("")&&phoneNumber.equals("")&&localReportNumber.equals("")&&callerName.equals("")){
	  patient=this.sessionFactory.getCurrentSession().createCriteria(Patient.class).setFirstResult((pageNumber-1)*itemsPerPage).setMaxResults(itemsPerPage).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	else if(callerName.equals("")){
		Criterion criterion=Restrictions.and(Restrictions.like("name", name, MatchMode.ANYWHERE), Restrictions.like("phoneNumber", phoneNumber, MatchMode.ANYWHERE));
		Criterion criterion2=Restrictions.and(criterion, Restrictions.like("localReportNumber", localReportNumber, MatchMode.ANYWHERE));
		patient=this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(criterion2).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	else if(!callerName.equals("")){
		patient=this.sessionFactory.getCurrentSession().createQuery("from Caller s1 join s1.patientes p1 where (s1.firstName like '%"+callerName+"%' or s1.lastName like '%"+callerName+"%') and p1.name like '%"+name+"%' and p1.phoneNumber like '%"+phoneNumber+"%' and p1.localReportNumber like '%"+localReportNumber+"%'").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	return patient;
}

@SuppressWarnings("unchecked")
@Override
public Integer getTotalPatientCount(String name,String phoneNumber,String localReportNumber,String callerName) {
	// TODO Auto-generated method stub
	List<Patient> patient=new ArrayList<Patient>();
	if(callerName.equals("")){
		Criterion criterion=Restrictions.and(Restrictions.like("name", name, MatchMode.ANYWHERE), Restrictions.like("phoneNumber", phoneNumber, MatchMode.ANYWHERE));
		Criterion criterion2=Restrictions.and(criterion, Restrictions.like("localReportNumber", localReportNumber, MatchMode.ANYWHERE));
		patient=this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(criterion2).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	else if(!callerName.equals("")){
		patient=this.sessionFactory.getCurrentSession().createQuery("from Caller s1 join s1.patientes p1 where (s1.firstName like '%"+callerName+"%' or s1.lastName like '%"+callerName+"%') and p1.name like '%"+name+"%' and p1.phoneNumber like '%"+phoneNumber+"%' and p1.localReportNumber like '%"+localReportNumber+"%'").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	return patient.size();
}

}
