package com.deemsys.project.userExportPreferences;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.UserExportPreferences;
import com.deemsys.project.exportFields.ExportFieldsForm;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class UserExportPreferencesDAOImpl implements UserExportPreferencesDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(UserExportPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(UserExportPreferences entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public UserExportPreferences get(Integer id) {
		// TODO Auto-generated method stub
		return (UserExportPreferences) this.sessionFactory.getCurrentSession().get(UserExportPreferences.class, id);
	}

	@Override
	public UserExportPreferences update(UserExportPreferences entity) {
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
	public List<UserExportPreferences> getAll(Integer userId) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(UserExportPreferences.class).add(Restrictions.eq("id.userId",userId)).list();
	}

	@Override
	public List<UserExportPreferences> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> find(String ParamName, Date date) {
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
	public List<UserExportPreferences> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExportPreferences> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllByUserId(Integer userId) {
		// TODO Auto-generated method stub
		
		for (UserExportPreferences userExportPreferences : this.getAll(userId)) {
			this.sessionFactory.getCurrentSession().delete(userExportPreferences);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExportFieldsForm> getUserExportPreferenceList(Integer userId) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(UserExportPreferences.class,"ue1");
		criteria.createAlias("ue1.exportFields", "e1");
		
		criteria.add(Restrictions.eq("ue1.id.userId", userId));
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("ue1.id.fieldId"),"fieldId");
		projectionList.add(Projections.property("e1.fieldName"),"fieldName");
		projectionList.add(Projections.property("e1.isCustom"),"isCustom");
		projectionList.add(Projections.property("ue1.id.defaultValue"),"defaultValue");
		projectionList.add(Projections.property("ue1.id.format"),"format");
		projectionList.add(Projections.property("ue1.id.sequenceNo"),"sequenceNo");
		projectionList.add(Projections.property("ue1.id.status"),"status");
		
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.asc("ue1.id.sequenceNo"));
		
		return criteria.setResultTransformer(new AliasToBeanResultTransformer(ExportFieldsForm.class)).list();
	}

	
	

}
