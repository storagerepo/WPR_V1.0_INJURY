package com.deemsys.project.Lawyers;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Lawyers;

@Repository
public class LawyersDAOImpl implements LawyersDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Lawyers entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Lawyers entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public Lawyers get(Integer id) {
		// TODO Auto-generated method stub
		Lawyers lawyers = (Lawyers) this.sessionFactory.getCurrentSession()
				.get(Lawyers.class, id);
		return lawyers;
	}

	@Override
	public Lawyers update(Lawyers entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Lawyers lawyers = this.get(id);
		if (lawyers != null) {
			this.sessionFactory.getCurrentSession().delete(lawyers);
		}
	}

	@Override
	public List<Lawyers> getAll() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Lawyers> lawyers = this.sessionFactory.getCurrentSession()
				.createCriteria(Lawyers.class).list();
		return lawyers;
	}

	@Override
	public List<Lawyers> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> find(String ParamName, Date date) {
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
	public List<Lawyers> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyers> getLawyersByLawyerAdmin(Integer lawyerAdminId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Lawyers> lawyers = this.sessionFactory.getCurrentSession()
				.createCriteria(Lawyers.class)
				.add(Restrictions.eq("lawyerAdmin.id", lawyerAdminId)).list();
		return lawyers;
	}

	@Override
	public Lawyers getLawyersByUserId(Integer userId) {
		// TODO Auto-generated method stub
		Lawyers lawyers = (Lawyers) this.sessionFactory.getCurrentSession()
				.createCriteria(Lawyers.class)
				.add(Restrictions.eq("users.userId", userId)).uniqueResult();
		return lawyers;
	}

}
