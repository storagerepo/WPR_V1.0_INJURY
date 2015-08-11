package com.deemsys.project.Doctors;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Doctors;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class DoctorsDAOImpl implements DoctorsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Doctors entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public void merge(Doctors entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public Doctors get(Integer id) {
		// TODO Auto-generated method stub
		return (Doctors) this.sessionFactory.getCurrentSession().get(Doctors.class, id);
	}

	@Override
	public Doctors update(Doctors entity) {
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
	public List<Doctors> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Doctors.class).list();
	}

	@Override
	public List<Doctors> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> find(String ParamName, Date date) {
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
	public List<Doctors> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctors> getDoctorId() {
		// TODO Auto-generated method stubCriteria
		 Criteria cr = sessionFactory.getCurrentSession().createCriteria(Doctors.class)
				    .setProjection(Projections.projectionList()
				      .add(Projections.property("id"), "id"))

				    .setResultTransformer(Transformers.aliasToBean(Doctors.class));

				  List<Doctors> list = cr.list();
		
		return list;
	}

	

}
