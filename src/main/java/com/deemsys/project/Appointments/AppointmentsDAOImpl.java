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
import com.deemsys.project.entity.Patients;

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
	
	@Override
	public List<Appointments> todaysAppointment()
	{
		List<Appointments> appointments = new ArrayList<Appointments>();
		  appointments= this.sessionFactory.getCurrentSession().createQuery("FROM Appointments where scheduledDate=CURDATE()").list();
		  return appointments;
	}

	@Override
	public Patients getPatientDetails(Integer patientId) {
		// TODO Auto-generated method stub
	return null;
	}

	@Override
	public List<Appointments> getByDates(String date) {
		// TODO Auto-generated method stub
		List<Appointments> appointments = new ArrayList<Appointments>();
		
		appointments= this.sessionFactory.getCurrentSession().createQuery("FROM Appointments where scheduledDate='"+date+"'").list();
		return appointments;
	}
	
	}
