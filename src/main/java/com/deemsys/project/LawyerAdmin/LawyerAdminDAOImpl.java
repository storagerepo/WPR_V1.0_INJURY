package com.deemsys.project.LawyerAdmin;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.LawyerAdmin;

@Repository
public class LawyerAdminDAOImpl implements LawyerAdminDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(LawyerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(LawyerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);

	}

	@Override
	public LawyerAdmin get(Integer id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		LawyerAdmin lawyerAdmin = (LawyerAdmin) session.get(LawyerAdmin.class, id);
		session.flush();
		return lawyerAdmin;
	}

	@Override
	public LawyerAdmin update(LawyerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		LawyerAdmin lawyerAdmin = this.get(id);
		if (lawyerAdmin != null) {
			this.sessionFactory.getCurrentSession().delete(lawyerAdmin);
		}
	}

	@Override
	public List<LawyerAdmin> getAll() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<LawyerAdmin> lawyerAdmin = this.sessionFactory.getCurrentSession()
				.createCriteria(LawyerAdmin.class).list();
		return lawyerAdmin;
	}

	@Override
	public List<LawyerAdmin> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdmin> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdmin> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdmin> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdmin> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdmin> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawyerAdmin> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
	Query query=this.sessionFactory.getCurrentSession().createQuery("update LawyerAdmin set status=0 where lawyerAdminId="+id+"");
	query.executeUpdate();
	return true;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update LawyerAdmin set status=1 where lawyerAdminId="+id+"");
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
	public List<LawyerAdmin> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LawyerAdmin getByUserId(Integer userId) {
		// TODO Auto-generated method stub
		LawyerAdmin lawyerAdmin = (LawyerAdmin) this.sessionFactory
				.getCurrentSession().createCriteria(LawyerAdmin.class)
				.add(Restrictions.eq("users.userId", userId)).uniqueResult();
		return lawyerAdmin;
	}

	@Override
	public Integer getUserIdByLawyerAdminId(Integer lawyerAdminId) {
		// TODO Auto-generated method stub
		return this.get(lawyerAdminId).getUsers().getUserId();
		
	}

}
