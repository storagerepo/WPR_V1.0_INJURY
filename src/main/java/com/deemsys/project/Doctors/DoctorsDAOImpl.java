package com.deemsys.project.Doctors;

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
import com.deemsys.project.entity.Doctors;

/**
 * 
 * @author Deemsys
 * 
 */
@Repository
public class DoctorsDAOImpl implements DoctorsDAO {

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
		return (Doctors) this.sessionFactory.getCurrentSession().get(
				Doctors.class, id);
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
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Doctors.class).list();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctors> getDoctorId() {
		// TODO Auto-generated method stubCriteria
		Criteria cr = sessionFactory
				.getCurrentSession()
				.createCriteria(Doctors.class)
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("id"), "id")
								.add(Projections.property("doctorName"),
										"doctorName"))

				.setResultTransformer(Transformers.aliasToBean(Doctors.class));

		List<Doctors> list = cr.list();

		return list;
	}

	@Override
	public List<Doctors> getDoctorsByClinicId(Integer clinicId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Doctors> doctors = this.sessionFactory.getCurrentSession()
				.createCriteria(Doctors.class)
				.add(Restrictions.eq("clinics.clinicId", clinicId)).list();
		return doctors;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getDoctorsSizeByClinicId(Integer clinicId) {
		// TODO Auto-generated method stub
		List<Doctors> doctors = this.sessionFactory.getCurrentSession()
				.createCriteria(Doctors.class)
				.add(Restrictions.eq("clinics.clinicId", clinicId)).list();
		return doctors.size();
	}

	@Override
	public Integer removeClinicIdFromDoctor(Integer doctorId) {
		// TODO Auto-generated method stub
		Query query = this.sessionFactory.getCurrentSession()
				.createQuery(
						"update Doctors set clinics.clinicId=NULL where id="
								+ doctorId);
		query.executeUpdate();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctors> getDoctorsByClinic(Integer clinicId) {
		// TODO Auto-generated method stub
		return (List<Doctors>) this.sessionFactory.getCurrentSession()
				.createCriteria(Doctors.class)
				.add(Restrictions.eq("clinics.clinicId", clinicId)).list();

	}

}
