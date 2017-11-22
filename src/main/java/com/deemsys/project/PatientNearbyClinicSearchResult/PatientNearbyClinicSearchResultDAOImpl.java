package com.deemsys.project.PatientNearbyClinicSearchResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.PatientNearbyClinicSearchResult;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class PatientNearbyClinicSearchResultDAOImpl implements PatientNearbyClinicSearchResultDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(PatientNearbyClinicSearchResult entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(PatientNearbyClinicSearchResult entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public PatientNearbyClinicSearchResult get(Integer id) {
		// TODO Auto-generated method stub
		return (PatientNearbyClinicSearchResult) this.sessionFactory.getCurrentSession().get(PatientNearbyClinicSearchResult.class, id);
	}

	@Override
	public PatientNearbyClinicSearchResult update(PatientNearbyClinicSearchResult entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(id));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientNearbyClinicSearchResult> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(PatientNearbyClinicSearchResult.class).list();
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientNearbyClinicSearchResult> find(String ParamName, Date date) {
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
	public List<PatientNearbyClinicSearchResult> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientNearbyClinicSearchResult> getSearchResultByPatientId(
			BigDecimal originLatitude, BigDecimal originLongitude) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createQuery("from PatientNearbyClinicSearchResult where id.originLatitude like '"+InjuryConstants.subStringLatandLang(originLatitude)+"%' and id.originLongitude like'"+InjuryConstants.subStringLatandLang(originLongitude)+"%'").list();
	}

	@Override
	public void deleteOldSearchResults(Date date) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("CALL start_purge_map_search_results(:oldDate)");
		query.setParameter("oldDate", date);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientNearbyClinicSearchResult> getSearchresultByDate(
			Date date) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(PatientNearbyClinicSearchResult.class).add(Restrictions.le("updatedDateTime", date)).list();
	}

}
