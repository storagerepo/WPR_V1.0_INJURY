package com.deemsys.project.UserLookupPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.UserLookupPreferences;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class UserLookupPreferencesDAOImpl implements UserLookupPreferencesDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(UserLookupPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(UserLookupPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public UserLookupPreferences get(Integer id) {
		// TODO Auto-generated method stub
		return (UserLookupPreferences) this.sessionFactory.getCurrentSession().get(UserLookupPreferences.class, id);
	}

	@Override
	public UserLookupPreferences update(UserLookupPreferences entity) {
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
	public List<UserLookupPreferences> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class).list();
	}

	@Override
	public List<UserLookupPreferences> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserLookupPreferences> find(String ParamName, Date date) {
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
	public List<UserLookupPreferences> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLookupPreferences> getUserLookupPreferencesByUserId(
			Integer userId) {
		// TODO Auto-generated method stub
		List<UserLookupPreferences> userLookupPreferences = this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class).add(Restrictions.eq("id.userId", userId)).list();
		return userLookupPreferences;
	}

	@Override
	public void deleteUserLookupPreferencesByUserId(Integer userId) {
		// TODO Auto-generated method stub
		List<UserLookupPreferences> userLookupPreferences = this.getUserLookupPreferencesByUserId(userId);
		for (UserLookupPreferences userLookupPreferences2 : userLookupPreferences) {
			this.sessionFactory.getCurrentSession().delete(userLookupPreferences2);
		}
	}

	@Override
	public void deleteUserLookupPreferenceByUserAndPPreferedId(Integer userId, Integer preferedType,
			Integer preferredId) {
		// TODO Auto-generated method stub
		Criterion userIdCriterion=Restrictions.and(Restrictions.eq("id.userId", userId), Restrictions.eq("id.preferedId", preferredId));
		UserLookupPreferences userLookupPreferences=(UserLookupPreferences) this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class)
				.add(Restrictions.and(Restrictions.eq("id.type", preferedType),userIdCriterion)).uniqueResult();
		if(userLookupPreferences!=null){
			this.sessionFactory.getCurrentSession().delete(userLookupPreferences);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLookupPreferences> getReportingAgencyUserLookupPreferences(
			Integer userId, List<Integer> countyId) {
		// TODO Auto-generated method stub
		// Look Up Preference Type
		Criterion userIdCriterion = Restrictions.and(Restrictions.eq("id.userId", userId),Restrictions.in("id.countyId", countyId));
		List<UserLookupPreferences> userLookupPreferences = this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class)
						.add(Restrictions.and(Restrictions.eq("id.type", InjuryConstants.REPORTING_AGENCY_LOOKUP), userIdCriterion)).list();
		return userLookupPreferences;
	}

	@Override
	public void deleteReportingAgencyPreferences(Integer userId,
			Integer countyId) {
		// TODO Auto-generated method stub
		List<Integer> countyIds=new ArrayList<Integer>();
		countyIds.add(countyId);
		List<UserLookupPreferences> userLookupPreferences = this.getReportingAgencyUserLookupPreferences(userId,countyIds);
		for (UserLookupPreferences userLookupPreferences2 : userLookupPreferences) {
			this.sessionFactory.getCurrentSession().delete(userLookupPreferences2);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountyList> getReportingAgencyUserLookupPreferencesCount(
			Integer userId, List<Integer> countyId) {
		// TODO Auto-generated method stub
		Criterion userIdCriterion = Restrictions.and(Restrictions.eq("id.userId", userId),Restrictions.in("id.countyId", countyId));
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class)
						.add(Restrictions.and(Restrictions.eq("id.type", InjuryConstants.REPORTING_AGENCY_LOOKUP), userIdCriterion));
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("id.countyId"),"countyId");
		projectionList.add(Projections.count("id.countyId"),"newCount");
		projectionList.add(Projections.groupProperty("id.countyId"));
		criteria.setProjection(projectionList);
		List<CountyList> countyLists=criteria.setResultTransformer(new AliasToBeanResultTransformer(CountyList.class)).list();
		return countyLists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLookupPreferences> getUserLookupPreferencesByUserIdAndType(
			Integer userId, Integer type) {
		// TODO Auto-generated method stub
		Criterion userIdCriterion = Restrictions.and(Restrictions.eq("id.userId", userId),Restrictions.eq("id.type", type));
		List<UserLookupPreferences> userLookupPreferences = this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class)
				.add(userIdCriterion).list();
		return userLookupPreferences;
	}

	@Override
	public void deleteUserLookupPreferencesByUserIdAndType(Integer userId,Integer type) {
		// TODO Auto-generated method stub
		List<UserLookupPreferences> userLookupPreferences = this.getUserLookupPreferencesByUserIdAndType(userId, type);
		for (UserLookupPreferences userLookupPreferences2 : userLookupPreferences) {
			this.sessionFactory.getCurrentSession().delete(userLookupPreferences2);
		}
	}

	@Override
	public void deleteUserLookupPreferencesNotInCountyList(Integer userId,
			List<Integer> countyIds) {
		// TODO Auto-generated method stub
		List<UserLookupPreferences> userLookupPreferences = this.getReportingAgencyUserLookupPreferencesNotInCountyList(userId, countyIds);
		for (UserLookupPreferences userLookupPreferences2 : userLookupPreferences) {
			this.sessionFactory.getCurrentSession().delete(userLookupPreferences2);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLookupPreferences> getReportingAgencyUserLookupPreferencesNotInCountyList(
			Integer userId, List<Integer> countyId) {
		// TODO Auto-generated method stub
		Criterion userIdCriterion = Restrictions.and(Restrictions.eq("id.userId", userId),Restrictions.not(Restrictions.in("id.countyId", countyId)));
		List<UserLookupPreferences> userLookupPreferences = this.sessionFactory.getCurrentSession().createCriteria(UserLookupPreferences.class)
						.add(Restrictions.and(Restrictions.eq("id.type", InjuryConstants.REPORTING_AGENCY_LOOKUP), userIdCriterion)).list();
		return userLookupPreferences;
	}

}
