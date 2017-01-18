package com.deemsys.project.RatingReviews;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.RatingReviews;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class RatingReviewsDAOImpl implements RatingReviewsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(RatingReviews entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(RatingReviews entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public RatingReviews get(Integer id) {
		// TODO Auto-generated method stub
		return (RatingReviews) this.sessionFactory.getCurrentSession().get(RatingReviews.class, id);
	}

	@Override
	public RatingReviews update(RatingReviews entity) {
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
	public List<RatingReviews> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(RatingReviews.class).list();
	}

	@Override
	public List<RatingReviews> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingReviews> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingReviews> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingReviews> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingReviews> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingReviews> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingReviews> find(String ParamName, Date date) {
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
	public List<RatingReviews> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RatingReviews getRatingReviewsByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return (RatingReviews) this.sessionFactory.getCurrentSession().createCriteria(RatingReviews.class).add(Restrictions.eq("users.userId", userId)).uniqueResult();
	}

	

}
