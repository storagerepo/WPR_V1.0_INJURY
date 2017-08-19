package com.deemsys.project.patientLawyerMap;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.PatientLawyerAdminMap;

@Repository
public class PatientLawyerDAOImpl implements PatientLawyerDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(PatientLawyerAdminMap entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(PatientLawyerAdminMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public PatientLawyerAdminMap get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientLawyerAdminMap update(PatientLawyerAdminMap entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PatientLawyerAdminMap> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(String queryString,
			String[] paramNames, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(String ParamName, Date date1,
			Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLawyerAdminMap> find(String ParamName, Date date) {
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
	public List<PatientLawyerAdminMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientLawyerAdminMap getPatientLawyerAdminMap(String patientId,Integer LawyerId) {
		// TODO Auto-generated method stub
		
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(PatientLawyerAdminMap.class);
		
		criteria.add(Restrictions.eq("id.patientId",patientId));
		criteria.add(Restrictions.eq("lawyer.lawyerId", LawyerId));
		
		return (PatientLawyerAdminMap) criteria.uniqueResult();
	}
	
	@Override
	public PatientLawyerAdminMap getPatientMapsByLawyerAdminId(String patientId,Integer LawyerAdminId) {
		// TODO Auto-generated method stub
		
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(PatientLawyerAdminMap.class);
		
		criteria.add(Restrictions.eq("id.patientId",patientId));
		criteria.add(Restrictions.eq("id.lawyerAdminId", LawyerAdminId));
		
		return (PatientLawyerAdminMap) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientLawyerAdminMap> getLawyerAdminMapsByLawyerId(
			Integer lawyerId) {
		// TODO Auto-generated method stub
		List<PatientLawyerAdminMap> patientLawyerAdminMaps=this.sessionFactory.getCurrentSession().createCriteria(PatientLawyerAdminMap.class).add(Restrictions.eq("lawyer.lawyerId", lawyerId)).list();
		return patientLawyerAdminMaps;
	}

}
