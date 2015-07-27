package com.deemsys.project.patients;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Patients;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class PatientsDAOImpl implements PatientsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Patients entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Patients entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public Patients get(Integer id) {
		// TODO Auto-generated method stub
		return (Patients) this.sessionFactory.getCurrentSession().get(Patients.class, id);
	}

	@Override
	public Patients update(Patients entity) {
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
	public List<Patients> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Patients.class).list();
	}

	@Override
	public List<Patients> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> find(String ParamName, Date date) {
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
	public List<Patients> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patients> getPatientListByStaffId(Integer staffId) {
		// TODO Auto-generated method stub
		List<Patients> patients = new ArrayList<Patients>();
		
		patients= this.sessionFactory.getCurrentSession().createQuery("FROM Patients where staff.id="+staffId).list();
		return patients;
	}	
	
	@Override
	public List<AppointmentsForm> getAppointmentListByStaffId(Integer staffId) {
	
		Query query= this.sessionFactory.getCurrentSession().createQuery("select s1.id,s1.patients.id,s1.scheduledDate,s1.notes,s1.status from Patients s2,Appointments s1 where s2.id=s1.patients.id and s2.staff.id="+staffId);
				
				List<Object[]> list = query.list();
	
		List<AppointmentsForm> forms=new ArrayList<AppointmentsForm>();
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		for(Object[] arr : list){
			
			appointmentsForm=new AppointmentsForm(Integer.parseInt(arr[0].toString()), Integer.parseInt(arr[1].toString()), arr[2].toString(), arr[3].toString(), Integer.parseInt(arr[4].toString()));
			forms.add(appointmentsForm);
				
		}
			return forms;
		// TODO Auto-generated method stub
		
	}
}
