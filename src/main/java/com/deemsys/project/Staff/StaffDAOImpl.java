package com.deemsys.project.Staff;

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
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class StaffDAOImpl implements StaffDAO{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Staff entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(Staff entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public Staff get(Integer id) {
		// TODO Auto-generated method stub
		return (Staff) this.sessionFactory.getCurrentSession().get(Staff.class, id);
	}

	@Override
	public Staff update(Staff entity) {
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
	public List<Staff> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(Staff.class).list();
	}

	@Override
	public List<Staff> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> find(String ParamName, Date date) {
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
	public List<Staff> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getByUserName(String username) {
		// TODO Auto-generated method stub
		return (Staff) this.sessionFactory.getCurrentSession().createCriteria(Staff.class).add(Restrictions.eq("username", username)).uniqueResult();
	}

	@Override
	public List<Staff> getStaffId() {
		 Criteria cr = sessionFactory.getCurrentSession().createCriteria(Staff.class)
				    .setProjection(Projections.projectionList()
				      .add(Projections.property("id"), "id")
				      .add(Projections.property("firstName"), "firstName"))
				    .setResultTransformer(Transformers.aliasToBean(Staff.class));

				  @SuppressWarnings("unchecked")
				List<Staff> list = cr.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Patients> getPatientsByAccessToken(Integer callerId) {
		// TODO Auto-generated method stub
		return (List<Patients>)this.sessionFactory.getCurrentSession().createCriteria(Patients.class).add(Restrictions.eq("staff.id", callerId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patients> getPatientStatus(Integer patientStatus,Integer callerId) {
		// TODO Auto-generated method stub
		return (List<Patients>)this.sessionFactory.getCurrentSession().createQuery("FROM Patients WHERE patientStatus='"+patientStatus+"' and staff.id='"+callerId+"'").list();
	}
	@Override
	public void deleteStaff(Integer id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(this.get(id));
	 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patients> getPatientsByStaffId(Integer id) {
		// TODO Auto-generated method stub
		return (List<Patients>)this.sessionFactory.getCurrentSession().createCriteria(Patients.class).add(Restrictions.eq("staff.id", id)).list();
	}

	@Override
	public Integer changePassword(String oldPassword,String userName) {
		// TODO Auto-generated method stub
		Query query=sessionFactory.getCurrentSession().createQuery("update Staff set password='"+oldPassword+"' where userName='"+userName+"'");
		query.executeUpdate();
			return 1;
	}


	@Override
	public List<Staff> checkPassword(String newPassword, String userName) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Staff> staff=(List<Staff>) this.sessionFactory.getCurrentSession().createQuery("FROM  Staff WHERE password='"+newPassword+"' and userName='"+userName+"'").list();
		return staff;
		
	}
	
	@Override
	public Staff getDetails(String userName) {
		// TODO Auto-generated method stub
		return (Staff) this.sessionFactory.getCurrentSession().createQuery("FROM Staff where userName='"+userName+"'").uniqueResult();
		
	
	}

	@Override
	public Integer isDisable(Integer getId) {
		// TODO Auto-generated method stub
		Query query=sessionFactory.getCurrentSession().createQuery("update Staff set status='0' where id='"+getId+"'");
		query.executeUpdate();
			return 0;
	
	}
	@Override
	public Integer isEnable(Integer getId) {
		// TODO Auto-generated method stub
		Query query=sessionFactory.getCurrentSession().createQuery("update Staff set status='1' where id='"+getId+"'");
		query.executeUpdate();
			return 1;
	
	}

	@Override
	public Integer resetPassword(Integer id) {
		// TODO Auto-generated method stub11
		Query query=sessionFactory.getCurrentSession().createQuery("update Staff set password=userName where id='"+id+"'");
		query.executeUpdate();
			return 0;
	}

	@Override
	public Staff getByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return (Staff) this.sessionFactory.getCurrentSession().createCriteria(Staff.class).add(Restrictions.eq("users.userId", userId)).uniqueResult();
	}

	
	
}
