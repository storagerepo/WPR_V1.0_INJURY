package com.deemsys.project.Caller;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.PatientCallerAdminMap;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class CallerDAOImpl implements CallerDAO{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Caller entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Caller entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public Caller get(Integer id) {
		// TODO Auto-generated method stub
		return (Caller) this.sessionFactory.getCurrentSession().get(Caller.class, id);
	}

	@Override
	public Caller update(Caller entity) {
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
	public List<Caller> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Caller.class).list();
	}

	@Override
	public List<Caller> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caller> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caller> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caller> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caller> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caller> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caller> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
		Query query=sessionFactory.getCurrentSession().createQuery("update Caller set status='0' where id='"+id+"'");
		query.executeUpdate();
		return false;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		Query query=sessionFactory.getCurrentSession().createQuery("update Caller set status='1' where id='"+id+"'");
		query.executeUpdate();
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
	public List<Caller> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caller getByUserName(String username) {
		// TODO Auto-generated method stub
		return (Caller) this.sessionFactory.getCurrentSession().createCriteria(Caller.class).add(Restrictions.eq("username", username)).uniqueResult();
	}

	@Override
	public List<Caller> getCallerId() {
		 Criteria cr = sessionFactory.getCurrentSession().createCriteria(Caller.class)
				    .setProjection(Projections.projectionList()
				      .add(Projections.property("id"), "id")
				      .add(Projections.property("firstName"), "firstName"))
				    .setResultTransformer(Transformers.aliasToBean(Caller.class));

				  @SuppressWarnings("unchecked")
				List<Caller> list = cr.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientByAccessToken(Integer callerId) {
		// TODO Auto-generated method stub
		return (List<Patient>)this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(Restrictions.eq("staff.id", callerId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientStatus(Integer patientStatus,Integer callerId) {
		// TODO Auto-generated method stub
		return (List<Patient>)this.sessionFactory.getCurrentSession().createQuery("FROM Patient WHERE patientStatus='"+patientStatus+"' and staff.id='"+callerId+"'").list();
	}
	@Override
	public void deleteCaller(Integer id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(id));
	 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientByCallerId(Integer id) {
		// TODO Auto-generated method stub
		return (List<Patient>)this.sessionFactory.getCurrentSession().createCriteria(Patient.class).add(Restrictions.eq("staff.id", id)).list();
	}

	
	@Override
	public Caller getDetails(String userName) {
		// TODO Auto-generated method stub
		return (Caller) this.sessionFactory.getCurrentSession().createQuery("FROM Caller where userName='"+userName+"'").uniqueResult();
		
	
	}


	@Override
	public Caller getByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return (Caller) this.sessionFactory.getCurrentSession().createCriteria(Caller.class).add(Restrictions.eq("users.userId", userId)).uniqueResult();
	}

	@Override
	public List<Caller> getCallerByCallerAdminId(Integer callerAdminId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Caller> callers=this.sessionFactory.getCurrentSession().createCriteria(Caller.class).add(Restrictions.eq("callerAdmin.callerAdminId", callerAdminId)).list();
		return callers;
	}

		
}
