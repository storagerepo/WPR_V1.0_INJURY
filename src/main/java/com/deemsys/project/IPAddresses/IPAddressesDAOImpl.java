package com.deemsys.project.IPAddresses;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.IpAccessList;
import com.deemsys.project.entity.IpAddress;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class IPAddressesDAOImpl implements IPAddressesDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(IpAddress entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void merge(IpAddress entity) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().merge(entity);
	}	
	
	@Override
	public IpAddress get(Integer id) {
		// TODO Auto-generated method stub
		return (IpAddress) this.sessionFactory.getCurrentSession().get(IpAddress.class, id);
	}

	@Override
	public IpAddress update(IpAddress entity) {
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
	public List<IpAddress> getAll() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(IpAddress.class).list();
	}

	@Override
	public List<IpAddress> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAddress> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAddress> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAddress> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAddress> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAddress> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpAddress> find(String ParamName, Date date) {
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
	public List<IpAddress> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkIPAlreadyExistOrNot(String ipAddress) {
		// TODO Auto-generated method stub
		IpAddress ip= this.getByIpAddress(ipAddress);
			
		if(ip!=null){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IpAddress getByIpAddress(String ipAddress) {
		// TODO Auto-generated method stub
		return (IpAddress) this.sessionFactory.getCurrentSession().createCriteria(IpAddress.class).add(Restrictions.eq("ipAddress", ipAddress)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IPAddressesSearchResult searchIPAddresses(
			IPAddressesSearchForm ipAddressSearchForm) {
		// TODO Auto-generated method stub
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(IpAddress.class,"ip1");
		criteria.createAlias("ip1.ipAccessLists", "ipa1");
		criteria.createAlias("ipa1.users", "u1");
		criteria.createAlias("ipa1.activityLogses", "ac1");
		if(ipAddressSearchForm.getDate()!=null&&!ipAddressSearchForm.getDate().equals("")){
			criteria.add(Restrictions.eq("ac1.id.accessDate", InjuryConstants.convertYearFormat(ipAddressSearchForm.getDate())));
		}
		if(ipAddressSearchForm.getPrimaryUsername()!=null&&!ipAddressSearchForm.getPrimaryUsername().equals("")){
			criteria.add(Restrictions.like("u1.username", ipAddressSearchForm.getPrimaryUsername(),MatchMode.ANYWHERE));
		}
		if(ipAddressSearchForm.getIpAddress()!=null&&!ipAddressSearchForm.getIpAddress().equals("")){
			criteria.add(Restrictions.like("ipa1.id.ipAddress", ipAddressSearchForm.getIpAddress(),MatchMode.ANYWHERE));
		}
		if(ipAddressSearchForm.getBlockStatus()!=null&&!ipAddressSearchForm.getBlockStatus().equals("")){
			criteria.add(Restrictions.eq("ip1.blockStatus", ipAddressSearchForm.getBlockStatus()));
		}
		// Common Constraints
		criteria.add(Restrictions.eq("ac1.id.activityId", 1));
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("ipa1.id.ipAddress"),"ipAddress");
		projectionList.add(Projections.property("u1.userId"),"primaryLoginId");
		projectionList.add(Projections.property("u1.username"),"primaryUsername");
		projectionList.add(Projections.property("ipa1.addedDate"),"addedDateYearFormat");
		projectionList.add(Projections.property("ip1.blockStatus"),"blockStatus");
		projectionList.add(Projections.count("ipa1.id.ipAddress"),"accessCount");
		projectionList.add(Projections.groupProperty("ipa1.id.ipAddress"));
		projectionList.add(Projections.groupProperty("ipa1.id.primaryLoginId"));
		criteria.setProjection(projectionList);
		int totalRecords = criteria.list().size();
		criteria.addOrder(Order.asc("ipa1.id.primaryLoginId"));
		criteria.addOrder(Order.desc("ipa1.addedDate"));
		List<IPAddressesForm> ipAddressForms = criteria.setResultTransformer(new AliasToBeanResultTransformer(IPAddressesForm.class)).
												 setFirstResult((ipAddressSearchForm.getPageNumber()-1)*ipAddressSearchForm.getItemsPerPage()).
												 setMaxResults(ipAddressSearchForm.getItemsPerPage()).list();
		
		return new IPAddressesSearchResult(new Long(totalRecords), ipAddressForms);
	}

	@Override
	public void deleteIPAddresses(String ipAddress) {
		// TODO Auto-generated method stub
		IpAddress ip = this.getByIpAddress(ipAddress);
		if(ip!=null){
			this.sessionFactory.getCurrentSession().delete(ip);
		}
	}


	@Override
	public void blockIpAddress(String ipAddress) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("update IpAddress set blockStatus=1 where id.ipAddress='"+ipAddress+"'").executeUpdate();
	}

	@Override
	public void unblockIpAddress(String ipAddress) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().createQuery("update IpAddress set blockStatus=0 where id.ipAddress='"+ipAddress+"'").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IpAddress> getBlockedIpAddress() {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createCriteria(IpAddress.class).add(Restrictions.eq("blockStatus", 1)).list();
	}

}
