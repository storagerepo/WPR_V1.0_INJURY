package com.deemsys.project.Appointments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public List<AppointmentsForm> getAppointmentsBetweenDatesByStaffId(
			String startDate, String endDate,Integer staffId) {
		// TODO Auto-generated method stub
		List<Appointments> appointments=new ArrayList<Appointments>();
		
		Query query =this.sessionFactory.getCurrentSession().createQuery("select s1.id,s1.patients.id,s1.patients.name,s1.scheduledDate,s1.notes,s1.status from Patients s2,Appointments s1 where s2.id=s1.patients.id and s2.staff.id="+staffId+" and (s1.scheduledDate>='"+startDate+"' and s1.scheduledDate<='"+endDate+"')");
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		
		List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		for(Object[] arr : list){
			appointmentsForm=new AppointmentsForm(Integer.parseInt(arr[0].toString()), Integer.parseInt(arr[1].toString()),arr[2].toString(),arr[3].toString(), arr[4].toString(), Integer.parseInt(arr[5].toString()));
			appointmentsForms.add(appointmentsForm);
				
		}
		return appointmentsForms;
	}
	
	}
