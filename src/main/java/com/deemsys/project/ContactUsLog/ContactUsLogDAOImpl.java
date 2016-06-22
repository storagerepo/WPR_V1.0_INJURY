package com.deemsys.project.ContactUsLog;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.ContactUsLog;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class ContactUsLogDAOImpl implements ContactUsLogDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(ContactUsLog entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(ContactUsLog entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public ContactUsLog get(Integer id) {
		// TODO Auto-generated method stub
		return (ContactUsLog) this.sessionFactory.getCurrentSession().get(ContactUsLog.class, id);
	}

	@Override
	public ContactUsLog update(ContactUsLog entity) {
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
	public List<ContactUsLog> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(ContactUsLog.class).list();
	}

	@Override
	public List<ContactUsLog> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUsLog> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUsLog> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUsLog> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUsLog> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUsLog> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUsLog> find(String ParamName, Date date) {
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
	public List<ContactUsLog> getActiveList() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactUsLog> getContactUsLogByContactUsId(Integer contactUsId) {
		// TODO Auto-generated method stub
		List<ContactUsLog> contactUsLogs=this.sessionFactory.getCurrentSession().createCriteria(ContactUsLog.class).add(Restrictions.eq("contactUs.id",contactUsId)).list();
		return contactUsLogs;
	}

	

}
