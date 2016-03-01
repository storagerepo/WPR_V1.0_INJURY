package com.deemsys.project.UserRoleMapping;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.UserRoleMapping;

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UserRoleMapping entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(UserRoleMapping entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserRoleMapping get(Integer id) {
		// TODO Auto-generated method stub
		UserRoleMapping userRoleMapping=(UserRoleMapping) this.sessionFactory.getCurrentSession().get(UserRoleMapping.class, id);
		return userRoleMapping;
	}

	@Override
	public UserRoleMapping update(UserRoleMapping entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		UserRoleMapping userRoleMapping = this.get(id);
		if(userRoleMapping!=null){
		this.sessionFactory.getCurrentSession().delete(userRoleMapping);
		}
	}

	@Override
	public List<UserRoleMapping> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleMapping> find(String ParamName, Date date) {
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
	public List<UserRoleMapping> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRoleMapping getbyUserId(Integer userId) {
		// TODO Auto-generated method stub
		UserRoleMapping userRole = (UserRoleMapping) sessionFactory.getCurrentSession()
			      .createCriteria(UserRoleMapping.class)
			      .add(Restrictions.eq("users.userId",userId)).uniqueResult();
		return userRole;
	}

	@Override
	public void deletebyUserId(Integer userId) {
		// TODO Auto-generated method stub
		UserRoleMapping userRoleMapping = this.getbyUserId(userId);
		if(userRoleMapping!=null){
		this.sessionFactory.getCurrentSession().delete(userRoleMapping);
		}
	}

}
