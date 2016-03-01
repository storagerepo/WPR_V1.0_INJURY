package com.deemsys.project.County;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.County;

@Repository
public class CountyDAOImpl implements CountyDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(County entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
		
	}

	@Override
	public void merge(County entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public County get(Integer id) {
		// TODO Auto-generated method stub
		County county=(County) this.sessionFactory.getCurrentSession().get(County.class, id);
		return county;
	}

	@Override
	public County update(County entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		County county=this.get(id);
		if(county!=null){
			this.sessionFactory.getCurrentSession().delete(county);
		}
	}

	@Override
	public List<County> getAll() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<County> county=this.sessionFactory.getCurrentSession().createCriteria(County.class).list();
		return county;
	}

	@Override
	public List<County> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<County> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<County> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<County> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<County> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<County> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<County> find(String ParamName, Date date) {
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
	public List<County> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

}
