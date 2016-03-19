package com.deemsys.project.Lawyers;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Lawyer;

@Repository
public class LawyersDAOImpl implements LawyersDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Lawyer entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Lawyer entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public Lawyer get(Integer id) {
		// TODO Auto-generated method stub
		Lawyer lawyers = (Lawyer) this.sessionFactory.getCurrentSession()
				.get(Lawyer.class, id);
		return lawyers;
	}

	@Override
	public Lawyer update(Lawyer entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Lawyer lawyers = this.get(id);
		if (lawyers != null) {
			this.sessionFactory.getCurrentSession().delete(lawyers);
		}
	}

	@Override
	public List<Lawyer> getAll() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Lawyer> lawyers = this.sessionFactory.getCurrentSession()
				.createCriteria(Lawyer.class).list();
		return lawyers;
	}

	@Override
	public List<Lawyer> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("Update Lawyer set status=0 where lawyerId='"+id+"'");
		query.executeUpdate();
		return false;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("Update Lawyer set status=1 where lawyerId='"+id+"'");
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
	public List<Lawyer> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lawyer> getLawyersByLawyerAdmin(Integer lawyerAdminId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Lawyer> lawyers = this.sessionFactory.getCurrentSession()
				.createCriteria(Lawyer.class)
				.add(Restrictions.eq("lawyerAdmin.lawyerAdminId", lawyerAdminId)).list();
		return lawyers;
	}

	@Override
	public Lawyer getLawyersByUserId(Integer userId) {
		// TODO Auto-generated method stub
		Lawyer lawyers = (Lawyer) this.sessionFactory.getCurrentSession()
				.createCriteria(Lawyer.class)
				.add(Restrictions.eq("users.userId", userId)).uniqueResult();
		return lawyers;
	}

}
