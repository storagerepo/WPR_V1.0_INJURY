package com.deemsys.project.Users;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Users;

@Repository
public class UsersDAOImpl implements UsersDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Users entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Users entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public Users get(Integer id) {
		// TODO Auto-generated method stub
		 Users user = (Users) sessionFactory.getCurrentSession().get(
	                Users.class, id);
	        return user;
	}

	@Override
	public Users update(Users entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Users users=this.get(id);
		if(users!=null){
			this.sessionFactory.getCurrentSession().delete(users);
		}
		
	}

	@Override
	public List<Users> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String ParamName, Date date) {
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
	public List<Users> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> checkPassword(String password, String userName) {
		// TODO Auto-generated method stub
			List<Users> users=(List<Users>) this.sessionFactory.getCurrentSession().createQuery("FROM  Users WHERE password='"+password+"' and username='"+userName+"'").list();
			return users;
	}

	@Override
	public Integer changePassword(String newPassword, String userName) {
		// TODO Auto-generated method stub
			Query query=sessionFactory.getCurrentSession().createQuery("update Users set password='"+newPassword+"',isPasswordChanged=1  where username='"+userName+"'");
			query.executeUpdate();
				return 1;
	}

	@Override
	public Users getByUserName(String userName) {
		// TODO Auto-generated method stub
		return (Users) this.sessionFactory.getCurrentSession().createCriteria(Users.class).add(Restrictions.eq("username", userName)).uniqueResult();
	}

	@Override
	public Integer resetUserPassword(Integer id) {
		// TODO Auto-generated method stub
		Query query=sessionFactory.getCurrentSession().createQuery("update Users set password=username where userId='"+id+"'");
		query.executeUpdate();
		return 0;
	}

	@Override
	public Integer disclaimerAcceptedStatus(Integer userId) {
		// TODO Auto-generated method stub
		Users users = (Users) this.sessionFactory.getCurrentSession().createCriteria(Users.class).add(Restrictions.eq("userId", userId)).uniqueResult();
		return users.getIsDisclaimerAccepted();
	}

	@Override
	public Users getUserByProductToken(String productToken) {
		// TODO Auto-generated method stub
		Users users = (Users) this.sessionFactory.getCurrentSession().createCriteria(Users.class).add(Restrictions.eq("productToken",productToken)).uniqueResult();
		return users;
	}
	
}
