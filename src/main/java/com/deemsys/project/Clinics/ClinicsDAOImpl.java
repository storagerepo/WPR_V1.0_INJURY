package com.deemsys.project.Clinics;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Clinic;

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
	public void save(Clinic entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Clinic entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public Clinic get(Integer id) {
		// TODO Auto-generated method stub
		Clinic clinics = (Clinic) this.sessionFactory.getCurrentSession()
				.get(Clinic.class, id);
		return clinics;
	}

	@Override
	public Clinic update(Clinic entity) {
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
	public List<Clinic> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Clinic.class).list();
	}

	@Override
	public List<Clinic> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("Update Clinic set status=0 where clinicId='"+id+"'");
		query.executeUpdate();
		return false;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("Update Clinic set status=1 where clinicId='"+id+"'");
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
	public List<Clinic> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> getEnabledClinicsListsByCallerAdmin(Integer callerAdminId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Clinic> clinics=this.sessionFactory.getCurrentSession().createCriteria(Clinic.class).add(Restrictions.and(Restrictions.eq("status", 1),Restrictions.eq("callerAdmin.callerAdminId", callerAdminId))).list();
		return clinics;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Clinic> getClinicId(Integer callerAdminId) {
		// TODO Auto-generated method stubCriteria
		Criteria cr = sessionFactory
				.getCurrentSession()
				.createCriteria(Clinic.class).add(Restrictions.eq("callerAdmin.callerAdminId", callerAdminId))
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("clinicId"),
										"clinicId")
								.add(Projections.property("clinicName"),
										"clinicName"))

				.setResultTransformer(Transformers.aliasToBean(Clinic.class));

		List<Clinic> list = cr.list();

		return list;
	}

	@Override
	public List<Clinic> getClinicsByCallerAdmin(Integer callerAdmin) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Clinic> clinics=this.sessionFactory.getCurrentSession().createCriteria(Clinic.class).add(Restrictions.eq("callerAdmin.callerAdminId", callerAdmin)).list();
		return clinics;
	}


}
