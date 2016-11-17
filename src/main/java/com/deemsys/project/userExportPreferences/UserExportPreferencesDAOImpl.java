package com.deemsys.project.userExportPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.UserExportPreferences;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class UserExportPreferencesDAOImpl implements UserExportPreferencesDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(UserExportPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(UserExportPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public UserExportPreferences get(Integer id) {
		// TODO Auto-generated method stub
		return (UserExportPreferences) this.sessionFactory.getCurrentSession().get(UserExportPreferences.class, id);
	}

	@Override
	public UserExportPreferences update(UserExportPreferences entity) {
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
	public List<UserExportPreferences> getAll(Integer userId) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(UserExportPreferences.class).add(Restrictions.eq("id.userId",userId)).list();
	}

	@Override
	public List<UserExportPreferences> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String ParamName, Date date) {
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
	public List<UserExportPreferences> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllByUserId(Integer userId) {
		// TODO Auto-generated method stub
		
		for (UserExportPreferences userExportPreferences : this.getAll(userId)) {
			this.sessionFactory.getCurrentSession().delete(userExportPreferences);
		}
		
		
	}

	
	

}
