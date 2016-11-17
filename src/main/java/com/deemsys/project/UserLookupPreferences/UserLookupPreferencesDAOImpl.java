package com.deemsys.project.UserLookupPreferences;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.UserLookupPreferences;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class UserLookupPreferencesDAOImpl implements UserLookupPreferencesDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(UserLookupPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(UserLookupPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public UserLookupPreferences get(Integer id) {
		// TODO Auto-generated method stub
		return (UserLookupPreferences) this.sessionFactory.getCurrentSession().get(UserLookupPreferences.class, id);
	}

	@Override
	public UserLookupPreferences update(UserLookupPreferences entity) {
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
	public List<UserLookupPreferences> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class).list();
	}

	@Override
	public List<UserLookupPreferences> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String ParamName, Date date) {
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
	public List<UserLookupPreferences> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLookupPreferences> getUserLookupPreferencesByUserId(
			Integer userId) {
		// TODO Auto-generated method stub
		List<UserLookupPreferences> userLookupPreferences = this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class).add(Restrictions.eq("id.userId", userId)).list();
		return userLookupPreferences;
	}

	@Override
	public void deleteUserLookupPreferencesByUserId(Integer userId) {
		// TODO Auto-generated method stub
		List<UserLookupPreferences> userLookupPreferences = this.getUserLookupPreferencesByUserId(userId);
		for (UserLookupPreferences userLookupPreferences2 : userLookupPreferences) {
			this.sessionFactory.getCurrentSession().delete(userLookupPreferences2);
		}
	}

	

}
