package com.deemsys.project.PatientCallerMap;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientCallerAdminMapId;

@Repository
public class PatientCallerDAOImpl implements PatientCallerDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(PatientCallerAdminMap entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(PatientCallerAdminMap entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public PatientCallerAdminMap get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientCallerAdminMap update(PatientCallerAdminMap entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PatientCallerAdminMap> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(String queryString,
			String[] paramNames, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(String ParamName, Date date1,
			Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientCallerAdminMap> find(String ParamName, Date date) {
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
	public List<PatientCallerAdminMap> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientCallerAdminMap getPatientCallerAdminMap(String patientId,Integer callerId) {
		// TODO Auto-generated method stub
		
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(PatientCallerAdminMap.class);
		
		criteria.add(Restrictions.eq("id.patientId",patientId.getBytes()));
		criteria.add(Restrictions.eq("caller.callerId", callerId));
		
		return (PatientCallerAdminMap) criteria.uniqueResult();
	}
	
	@Override
	public PatientCallerAdminMap getPatientMapsByCallerAdminId(String patientId,Integer callerAdminId) {
		// TODO Auto-generated method stub
		
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(PatientCallerAdminMap.class);
		
		criteria.add(Restrictions.eq("id.patientId",patientId.getBytes()));
		criteria.add(Restrictions.eq("id.callerAdminId", callerAdminId));
		
		return (PatientCallerAdminMap) criteria.uniqueResult();
	}


	@Override
	public List<PatientCallerAdminMap> getAssignedPatientsByCallerId(Integer callerId) {
		// TODO Auto-generated method stub
		List<PatientCallerAdminMap> patientCallerAdminMaps=this.sessionFactory.getCurrentSession().createCriteria(PatientCallerAdminMap.class).add(Restrictions.eq("caller.callerId", callerId)).list();
		return patientCallerAdminMaps;
	}

}
