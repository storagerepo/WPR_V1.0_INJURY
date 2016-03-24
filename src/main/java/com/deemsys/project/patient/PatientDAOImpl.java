package com.deemsys.project.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.xmlbeans.impl.regex.RegularExpression;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.login.LoginService;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class PatientDAOImpl implements PatientDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	LoginService loginService;

	@Override
	public void savePatient(Patient entity) throws Exception {		
		try{
			String UUIDString=UUID.randomUUID().toString();
			entity.setPatientId(UUIDString.replaceAll("-", ""));
			this.sessionFactory.getCurrentSession().save(entity);
		}catch(Exception exception){
			throw exception;
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
public PatientSearchResult searchPatientsByCAdmin(
		CallerPatientSearchForm callerPatientSearchForm) {
	// TODO Auto-generated method stub
		
	Session session=this.sessionFactory.getCurrentSession();
	
	
	//Patient Table Must be included
	Criteria criteria=session.createCriteria(Patient.class, "t1");
	           
	//Common Constrains - County
	Criterion countyCriterion=Restrictions.eq("t1.county.countyId", callerPatientSearchForm.getCountyId());
	criteria.add(countyCriterion);
	
	//Common Constrains - Tier
	if(callerPatientSearchForm.getTier()!=0){
		Criterion tierCriterion=Restrictions.eq("t1.patientStatus", callerPatientSearchForm.getTier());
		criteria.add(tierCriterion);
	}
	
	//Common Constrains - Crash Date
	if(!callerPatientSearchForm.getCrashFromDate().equals("")){		
		DateTime crashStartDate=DateTime.parse(callerPatientSearchForm.getCrashFromDate(),DateTimeFormat.forPattern("MM/dd/yyyy"));
		DateTime crashToDate=new DateTime();		
		if(callerPatientSearchForm.getNumberOfDays()==0){
			crashToDate=DateTime.parse(callerPatientSearchForm.getCrashToDate(),DateTimeFormat.forPattern("MM/dd/yyyy"));
			callerPatientSearchForm.setCrashToDate(crashToDate.toString("MM/dd/yyyy"));
		}else{
			crashToDate=crashStartDate.plusDays(callerPatientSearchForm.getNumberOfDays());		
			callerPatientSearchForm.setCrashToDate(crashToDate.toString("MM/dd/yyyy"));
		}
		
		//Set Criterion
		Criterion crashDateCriterion=Restrictions.between("t1.crashDate", callerPatientSearchForm.getCrashFromDate(), callerPatientSearchForm.getCrashToDate());
		criteria.add(crashDateCriterion);
	}
	
	//Common Constrain Patient Name
	Criterion patientNameCriterion=Restrictions.like("t1.name", callerPatientSearchForm.getPatientName(),MatchMode.ANYWHERE);
	criteria.add(patientNameCriterion);
	
	//Common Constrain Patient Name
	if(!callerPatientSearchForm.getPhoneNumber().equals("")){
		Criterion patientPhoneCriterion=Restrictions.like("t1.phoneNumber", callerPatientSearchForm.getPhoneNumber(),MatchMode.START);
		criteria.add(patientPhoneCriterion);
	}
	
	
	//Common Constrain Local Report Number
	if(!callerPatientSearchForm.getLocalReportNumber().equals("")){
	Criterion localReportNumberCriterion=Restrictions.like("t1.localReportNumber", callerPatientSearchForm.getLocalReportNumber(),MatchMode.START);
	criteria.add(localReportNumberCriterion);
	}
	
		
	String role=loginService.getCurrentRole();
	
	if(role.equals("ROLE_SUPER_ADMIN")){
		
	}else if(role.equals("ROLE_CALLER_ADMIN")||role.equals("ROLE_CALLER")){
		
		criteria.createAlias("patientCallerAdminMaps", "t2", Criteria.LEFT_JOIN,Restrictions.eq("t2.id.callerAdminId",callerPatientSearchForm.getCallerAdminId()));
		
		//Check for caller id
		if(callerPatientSearchForm.getCallerId()!=0){
			Criterion criterion=Restrictions.eq("t2.caller.callerId", callerPatientSearchForm.getCallerId());
			criteria.add(criterion);
			criteria.createAlias("t2.caller", "c2");
		}
				
		
	}else if(role.equals("ROLE_LAWYER_ADMIN")||role.equals("ROLE_LAWYER")){
		
		criteria.createAlias("patientLawyerAdminMaps", "t2", Criteria.LEFT_JOIN).add(Restrictions.or(Restrictions.eq("t2.id.lawyerAdminId", callerPatientSearchForm.getLawyerAdminId()),Restrictions.isNull("t2.id.lawyerAdminId")));		
		/*LogicalExpression logicalExpression=Restrictions.or();
		criteria.add(logicalExpression);*/
		//Check for lawyer id
		if(callerPatientSearchForm.getLawyerId()!=0){
			Criterion criterion=Restrictions.eq("t2.lawyer.lawyerId", callerPatientSearchForm.getLawyerId());
			criteria.add(criterion);
			criteria.createAlias("t2.lawyer", "l1");
		}
				
	}
	
	criteria.createAlias("county", "c1");
	
	
	//Common Constrain - Patient Status
	if(callerPatientSearchForm.getPatientStatus()!=0){
		Criterion patientStatus=Restrictions.eq("t2.patientStatus", callerPatientSearchForm.getPatientStatus());
		criteria.add(patientStatus);
	}
	
	//ARCHIVED
	
	
	//Add Projections
	ProjectionList projectionList = Projections.projectionList();
	
	projectionList.add(Projections.property("t1.patientId"),"patientId");
	projectionList.add(Projections.property("t1.localReportNumber"),"localReportNumber");
	projectionList.add(Projections.property("t1.county.countyId"),"countyId");
	projectionList.add(Projections.property("c1.name"),"county");
	projectionList.add(Projections.property("t1.crashDate"),"crashDate");
	projectionList.add(Projections.property("t1.crashSeverity"),"crashSeverity");
	projectionList.add(Projections.property("t1.addedDate"),"addedDate");
	projectionList.add(Projections.property("t1.name"),"name");
	projectionList.add(Projections.property("t1.phoneNumber"),"phoneNumber");
	projectionList.add(Projections.property("t1.address"),"address");	
	projectionList.add(Projections.property("t1.crashReportFileName"),"crashReportFileName");
	
	if(role.equals("ROLE_CALLER_ADMIN")||role.equals("ROLE_CALLER")){
	
		projectionList.add(Projections.property("t2.callerAdmin.callerAdminId"),"callerAdminId");
		projectionList.add(Projections.property("t2.caller.callerId"),"callerId");
		if(callerPatientSearchForm.getCallerId()!=0){
		projectionList.add(Projections.property("c2.firstName"),"callerFirstName");
		projectionList.add(Projections.property("c2.lastName"),"callerLastName");
		}
	}else if(role.equals("ROLE_LAWYER_ADMIN")||role.equals("ROLE_LAWYER")){
		
		projectionList.add(Projections.property("t2.lawyerAdmin.lawyerAdminId"),"lawyerAdminId");
		projectionList.add(Projections.property("t2.lawyer.lawyerId"),"lawyerId");
		projectionList.add(Projections.property("t1.atFaultInsuranceCompany"),"atFaultInsuranceCompany");
		projectionList.add(Projections.property("t1.victimInsuranceCompany"),"victimInsuranceCompany");
		if(callerPatientSearchForm.getLawyerId()!=0){
		projectionList.add(Projections.property("l1.firstName"),"lawyerFirstName");
		projectionList.add(Projections.property("l1.lastName"),"lawyerLastName");
		}	
	}
	
	if(!role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
		
		projectionList.add(Projections.property("t2.notes"),"notes");
		projectionList.add(Projections.property("t2.isArchived"),"isArchived");
		projectionList.add(Projections.property("t2.patientStatus"),"patientStatus");
		
	}
	
	
	criteria.setProjection(projectionList);
	Integer totalNumberOfRecords=criteria.setResultTransformer(new AliasToBeanResultTransformer(PatientSearchList.class)).list().size();
	List<PatientSearchList> patientSearchLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(PatientSearchList.class)).setFirstResult((callerPatientSearchForm.getPageNumber()-1)*callerPatientSearchForm.getItemsPerPage()).setMaxResults(callerPatientSearchForm.getItemsPerPage()).list();
	
	PatientSearchResult patientSearchResult=new PatientSearchResult(totalNumberOfRecords, patientSearchLists);
	
	return patientSearchResult;

}

@Override
public void save(Patient entity) {
	// TODO Auto-generated method stub
	
}

@Override
public Patient getPatientByPatientId(String patientId) {
	// TODO Auto-generated method stub
	Patient patient=(Patient) this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(Restrictions.eq("patientId", patientId)).uniqueResult();
	return patient;
}



}
