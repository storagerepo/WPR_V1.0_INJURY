package com.deemsys.project.CallerAdmin;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.CallerAdmin;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class CallerAdminDAOImpl implements CallerAdminDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(CallerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(CallerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public CallerAdmin get(Integer id) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.openSession();
		CallerAdmin callerAdmin=(CallerAdmin) session.get(CallerAdmin.class, id);
		session.flush();
		return callerAdmin;
	}

	@Override
	public CallerAdmin update(CallerAdmin entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(entity);
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CallerAdmin> getAll() {
		// TODO Auto-generated method stub
		List<CallerAdmin> callerAdmins=this.sessionFactory.getCurrentSession().createCriteria(CallerAdmin.class).list();
		return callerAdmins;
	}

	@Override
	public List<CallerAdmin> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallerAdmin> find(String ParamName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update CallerAdmin set status=0 where callerAdminId="+id+"");
		query.executeUpdate();
			
		this.sessionFactory.getCurrentSession().createCriteria(Caller.class).add(Restrictions.eq("callerAdmin.callerAdminId", id)).list();
		
		
		return true;
	}

	@Override
	public boolean enable(Integer id) {
		// TODO Auto-generated method stub
		Query query=this.sessionFactory.getCurrentSession().createQuery("update CallerAdmin set status=1 where callerAdminId="+id+"");
		query.executeUpdate();
				
		return true;
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
	public List<CallerAdmin> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getUserIdByCallerAdminId(Integer callerAdminId) {
		// TODO Auto-generated method stub
		Integer userId=this.get(callerAdminId).getUsers().getUserId();
		
		return userId;
	}

	@Override
	public CallerAdmin getCallerAdminByUserId(Integer userId) {
		// TODO Auto-generated method stub
		CallerAdmin callerAdmin=(CallerAdmin) this.sessionFactory.getCurrentSession().createCriteria(CallerAdmin.class).add(Restrictions.eq("users.userId", userId)).uniqueResult();
		return callerAdmin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CallerAdminForm> getCallerAdminListByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(CallerAdmin.class,"c1");
		criteria.createAlias("c1.users", "u1");
		criteria.createAlias("u1.roles", "r1");
		
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("c1.callerAdminId"),"callerAdminId");
		projectionList.add(Projections.property("c1.firstName"),"firstName");
		projectionList.add(Projections.property("c1.lastName"),"lastName");
		projectionList.add(Projections.property("c1.street"),"street");
		projectionList.add(Projections.property("c1.city"),"city");
		projectionList.add(Projections.property("c1.state"),"state");
		projectionList.add(Projections.property("c1.zipcode"),"zipcode");
		projectionList.add(Projections.property("c1.emailAddress"),"emailAddress");
		projectionList.add(Projections.property("c1.phoneNumber"),"phoneNumber");
		projectionList.add(Projections.property("c1.notes"),"notes");
		projectionList.add(Projections.property("c1.status"),"status");
		projectionList.add(Projections.property("u1.userId"),"userId");
		projectionList.add(Projections.property("u1.username"),"username");
		projectionList.add(Projections.property("u1.isPrivilegedUser"),"isPrivilegedUser");
		projectionList.add(Projections.property("u1.productToken"),"productToken");
		
		criteria.setProjection(projectionList);
		
		return criteria.add(Restrictions.eq("r1.roleId", roleId)).setResultTransformer(new AliasToBeanResultTransformer(CallerAdminForm.class)).list();
	}

}
