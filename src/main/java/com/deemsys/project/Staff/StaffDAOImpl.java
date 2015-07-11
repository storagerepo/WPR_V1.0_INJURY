package com.deemsys.project.Staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Staff;

/**
 * 
 * @author Deemsys
 *
 */
@Repository
public class StaffDAOImpl implements StaffDAO,UserDetailsService{
	
	
	@Autowired
	SessionFactory sessionFactory;

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

	//OAUTH
	//Function that overrides the default spring security
	@Override
	public User loadUserByUsername(String username){
		
		//Load User Who having the User name
		Staff staff=this.getByUserName(username);
		
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		if(staff!=null)
			authorities= buildUserAuthority(staff.getRole());
				
		return buildUserForAuthentication(username,staff, authorities);
	}
		
	
	private User buildUserForAuthentication(String username,Staff staff, List<GrantedAuthority> authorities) {
		
		String password;
		boolean isEnable=true,accountNonExpired=true,credentialsNonExpired=true,accountNonLocked=true;
		if(staff==null)
		{
			password="";
		}
		else
		{
			password=staff.getPassword();
			if(staff.getIsEnable()==1)
				isEnable=true;
			else
				isEnable=false;
		}
		
		return new User(username,password,isEnable, accountNonExpired, credentialsNonExpired, accountNonLocked,authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		setAuths.add(new SimpleGrantedAuthority(userRoles));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
	
	@Override
	public Staff getByUserName(String username) {
		// TODO Auto-generated method stub
		return (Staff) this.sessionFactory.getCurrentSession().createCriteria(Staff.class).add(Restrictions.eq("username", username)).uniqueResult();
	}
	
}