package com.deemsys.project.CallerAdmin;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.CallerAdmin;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class CallerAdminDAOImpl implements CallerAdminDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(CallerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(CallerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public CallerAdmin get(Integer id) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.openSession();
		CallerAdmin callerAdmin=(CallerAdmin) session.get(CallerAdmin.class, id);
		session.flush();
		return callerAdmin;
	}

	@Override
	public CallerAdmin update(CallerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CallerAdmin> getAll() {
		// TODO Auto-generated method stub
		List<CallerAdmin> callerAdmins=this.sessionFactory.getCurrentSession().createCriteria(CallerAdmin.class).list();
		return callerAdmins;
	}

	@Override
	public List<CallerAdmin> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update CallerAdmin set status=0 where callerAdminId="+id+"");
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update CallerAdmin set status=1 where callerAdminId="+id+"");
		query.executeUpdate();
		return true;
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
	public List<CallerAdmin> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getUserIdByCallerAdminId(Integer callerAdminId) {
		// TODO Auto-generated method stub
		Integer userId=this.get(callerAdminId).getUsers().getUserId();
		
		return userId;
	}

}
