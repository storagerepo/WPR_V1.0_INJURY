package com.deemsys.project.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.JoinType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.id.Assigned;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientCallerAdminMap;

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
		try{
			String UUIDString=UUID.randomUUID().toString();
			entity.setPatientId(UUIDString.replaceAll("-", ""));
			this.sessionFactory.getCurrentSession().save(entity);
		}catch(DataIntegrityViolationException exception){
			this.save(entity);
		}			
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
public Integer getTotalPatientCount(String localReportNumber, Integer county,
		String crashDate,String crashToDate, String recordedFromDate,
		String recordedToDate, String name) {
	// TODO Auto-generated method stub
	Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(Patient.class);
	
	Criterion countyCriterion=Restrictions.eq("county.countyId", county);
	cr.add(countyCriterion);
	
	Criterion nameCriterion=Restrictions.like("name", name,MatchMode.ANYWHERE);
	cr.add(nameCriterion);
	
	Criterion localReportNumberCriterion=Restrictions.like("localReportNumber",localReportNumber,MatchMode.START);
	cr.add(localReportNumberCriterion);
	
	if(recordedFromDate!=""&&recordedToDate!=""){
		Criterion addedDateCriterion=Restrictions.between("addedDate", recordedFromDate, recordedToDate);
		cr.add(addedDateCriterion);
	}
	
	if(crashDate!=""&&crashToDate!=""){
		Criterion crashDateCriterion=Restrictions.between("crashDate", crashDate, crashToDate);
		cr.add(crashDateCriterion);
	}
	List<Patient> patients=new ArrayList<Patient>();
	patients=cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

	return patients.size();
}

@SuppressWarnings("unchecked")
@Override
public List<Patient> searchPatients(Integer pageNumber, Integer itemsPerPage,String localReportNumber, Integer county,
		String crashDate, String crashToDate, String recordedFromDate,
		String recordedToDate, String name) {
		// TODO Auto-generated method stub
		//(county_id=60 and name like '%%' )and (local_report_number like '%%'  and (added_date between '03/02/2016' and '03/21/2016')) and (crash_date between '03/06/2016' and '03/22/2016');
		Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(Patient.class);
	
		Criterion countyCriterion=Restrictions.eq("county.countyId", county);
		cr.add(countyCriterion);
		
		Criterion nameCriterion=Restrictions.like("name", name,MatchMode.ANYWHERE);
		cr.add(nameCriterion);
		
		Criterion localReportNumberCriterion=Restrictions.like("localReportNumber",localReportNumber,MatchMode.START);
		cr.add(localReportNumberCriterion);
		
		if(recordedFromDate!=""&&recordedToDate!=""){
			Criterion addedDateCriterion=Restrictions.between("addedDate", recordedFromDate, recordedToDate);
			cr.add(addedDateCriterion);
		}
		
		if(crashDate!=""&&crashToDate!=""){
			Criterion crashDateCriterion=Restrictions.between("crashDate", crashDate, crashToDate);
			cr.add(crashDateCriterion);
		}
		
		
		List<Patient> patient=new ArrayList<Patient>();			
		patient=cr.setFirstResult((pageNumber-1)*itemsPerPage).setMaxResults(itemsPerPage).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return patient;
}

private Object value(String string, String localReportNumber, MatchMode anywhere) {
	// TODO Auto-generated method stub
	return null;
}

@SuppressWarnings("unchecked")
@Override
public List<Patient> searchPatientsByCAdmin(
		CallerPatientSearchForm callerPatientSearchForm) {
	// TODO Auto-generated method stub
	
	List<Patient> patients=new ArrayList<Patient>();	
	Session session=this.sessionFactory.getCurrentSession();
	
	Criteria criteria=session.createCriteria(Patient.class, "t1");
	
	criteria.createAlias("patientCallerAdminMaps", "t2", Criteria.LEFT_JOIN);
	
	//Check for caller admin id equal or null
	LogicalExpression logicalExpression=Restrictions.or(Restrictions.eq("t2.id.callerAdminId", callerPatientSearchForm.getCallerAdminId()),Restrictions.isNull("t2.id.callerAdminId"));
	criteria.add(logicalExpression);
	
	//Add Projections
	//criteria.setProjection(Projections.property("t1"));
	ProjectionList projectionList = Projections.projectionList();
	
	projectionList.add(Projections.property("t1.patientId"),"patientId");
	projectionList.add(Projections.property("t1.localReportNumber"),"localReportNumber");
	projectionList.add(Projections.property("t1.county.countyId"),"countyId");
	projectionList.add(Projections.property("t1.crashDate"),"crashDate");
	projectionList.add(Projections.property("t1.crashSeverity"),"crashSeverity");
	projectionList.add(Projections.property("t1.addedDate"),"addedDate");
	projectionList.add(Projections.property("t1.name"),"name");
	projectionList.add(Projections.property("t1.phoneNumber"),"phoneNumber");
	projectionList.add(Projections.property("t1.address"),"address");
	projectionList.add(Projections.property("t1.crashReportFileName"),"crashReportFileName");
	projectionList.add(Projections.property("t2.callerAdmin.callerAdminId"),"callerAdminId");
	projectionList.add(Projections.property("t2.caller.callerId"),"callerId");
	projectionList.add(Projections.property("t2.notes"),"notes");
	projectionList.add(Projections.property("t2.isArchived"),"isArchived");
	projectionList.add(Projections.property("t2.patientStatus"),"patientStatus");
	
	criteria.setProjection(projectionList);
	List<PatientSearchList> patientSearchLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(PatientSearchList.class)).list();
	return (List<Patient>) criteria.list();

}



}
