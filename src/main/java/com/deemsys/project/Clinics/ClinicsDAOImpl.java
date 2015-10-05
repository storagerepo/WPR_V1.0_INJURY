package com.deemsys.project.Clinics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Clinics;
import com.deemsys.project.entity.Doctors;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class ClinicsDAOImpl implements ClinicsDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(Clinics entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Clinics entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public Clinics get(Integer id) {
		// TODO Auto-generated method stub
		Clinics clinics=(Clinics) this.sessionFactory.getCurrentSession().get(Clinics.class, id);
		return clinics;
	}

	@Override
	public Clinics update(Clinics entity) {
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
	public List<Clinics> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Clinics.class).list();
	}

	@Override
	public List<Clinics> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> find(String ParamName, Date date) {
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
	public List<Clinics> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinics> getClinicsLists() {
		// TODO Auto-generated method stub
		List<Clinics> clinics=this.sessionFactory.getCurrentSession().createCriteria(Clinics.class).list();
		return clinics;
	}
	
@Override
	public List<Clinics> getClinicId() {
		// TODO Auto-generated method stubCriteria
		 Criteria cr = sessionFactory.getCurrentSession().createCriteria(Clinics.class)
				    .setProjection(Projections.projectionList()
				      .add(Projections.property("clinicId"), "clinicId")
				      .add(Projections.property("clinicName"), "clinicName"))

				    .setResultTransformer(Transformers.aliasToBean(Clinics.class));

				  List<Clinics> list = cr.list();
		
		return list;
	}
	
	}
