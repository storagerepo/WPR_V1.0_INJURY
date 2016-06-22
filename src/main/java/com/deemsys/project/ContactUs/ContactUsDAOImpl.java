package com.deemsys.project.ContactUs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.ContactUs;
import com.deemsys.project.entity.ContactUsLog;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class ContactUsDAOImpl implements ContactUsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(ContactUs entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(ContactUs entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public ContactUs get(Integer id) {
		// TODO Auto-generated method stub
		return (ContactUs) this.sessionFactory.getCurrentSession().get(ContactUs.class, id);
	}

	@Override
	public ContactUs update(ContactUs entity) {
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
	public List<ContactUs> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(ContactUs.class).addOrder(Order.desc("id")).list();
	}

	@Override
	public List<ContactUs> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUs> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUs> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUs> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUs> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUs> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactUs> find(String ParamName, Date date) {
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
	public List<ContactUs> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactUsForm> getContactUsListWitLatestStatus() {
		// TODO Auto-generated method stub
		List<ContactUsForm> contactUsForms = new ArrayList<ContactUsForm>();
		Criteria criteria=this.sessionFactory.getCurrentSession().createCriteria(ContactUs.class,"c1");
		criteria.createAlias("c1.contactUsLogs","cl1");
		
		//Sub Query
		DetachedCriteria maxLogDateTime = DetachedCriteria.forClass(ContactUsLog.class,"icl1");
		maxLogDateTime.add(Restrictions.eqProperty("icl1.contactUs.id", "c1.id")).setProjection(Projections.projectionList().add(Projections.max("icl1.logDateTime")));
		
		criteria.add(Subqueries.propertyEq("cl1.logDateTime", maxLogDateTime));
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("c1.firstName"), "firstName");
		projectionList.add(Projections.property("c1.lastName"), "lastName");
		projectionList.add(Projections.property("c1.firmName"), "firmName");
		projectionList.add(Projections.property("c1.email"), "email");
		projectionList.add(Projections.property("c1.phoneNumber"), "phoneNumber");
		projectionList.add(Projections.property("c1.addedDateTime"), "addedDateTable");
		projectionList.add(Projections.property("c1.id"), "id");
		projectionList.add(Projections.property("cl1.logStatus"), "status");
		projectionList.add(Projections.property("cl1.logDateTime"), "logDateTimeTable");
		projectionList.add(Projections.property("cl1.byWhom"), "updatedBy");
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.desc("c1.addedDateTime"));
		contactUsForms=criteria.setResultTransformer(new AliasToBeanResultTransformer(ContactUsForm.class)).list();
		return contactUsForms;
	}


}
