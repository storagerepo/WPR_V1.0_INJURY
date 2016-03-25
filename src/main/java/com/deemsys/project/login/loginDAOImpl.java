package com.deemsys.project.login;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.Users;

@Repository
@Transactional
@Service
public class loginDAOImpl implements loginDAO,UserDetailsService{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Override
	public void save(Users entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(Users entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Users get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users update(Users entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Users> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> find(String ParamName, Date date) {
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
	public List<Users> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

	//OAUTH
		//Function that overrides the default spring security
		@Override
		public User loadUserByUsername(String userName){
			
			//Load User Who having the User name
			Users userLoginDetails=this.getByUserName(userName);
			/*if(userName.equals(userLoginDetails.getUsername())){
				userName=userLoginDetails.getUsername();
			}
			else{
				userName="";
			}*/
			List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
			
			if(userLoginDetails!=null)
				authorities= buildUserAuthority(usersDAO.get(userLoginDetails.getUserId()));
			
			return buildUserForAuthentication(userName,userLoginDetails, authorities);
		}
		
		
		private User buildUserForAuthentication(String username,Users userLoginDetails, List<GrantedAuthority> authorities) {
			
			String password;
			boolean isEnable=true,accountNonExpired=true,credentialsNonExpired=true,accountNonLocked=true;
			if(userLoginDetails==null)
			{
				password="";
			}
			else
			{
				password=userLoginDetails.getPassword();
				if(userLoginDetails.getIsEnable()==1)
					isEnable=true;
				else
					isEnable=false;
			}
			
			return new User(username,password,isEnable, accountNonExpired, credentialsNonExpired, accountNonLocked,authorities);
		}

		private List<GrantedAuthority> buildUserAuthority(Users users) {

			Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

			// Build user's authorities
			setAuths.add(new SimpleGrantedAuthority(users.getRoles().getRole()));

			List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

			return Result;
		}

	
		@Override
		public Users getByUserName(String username) {
			// TODO Auto-generated method stub
			return (Users) this.sessionFactory.getCurrentSession().createCriteria(Users.class).add(Restrictions.eq("username", username)).uniqueResult();
		}

		
}
