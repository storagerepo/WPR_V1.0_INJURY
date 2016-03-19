package com.deemsys.project.CrashReportError;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deemsys.project.common.BasicQuery;
import com.deemsys.project.entity.CrashReportError;

@Repository
public class CrashReportErrorDAOImpl implements CrashReportErrorDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(CrashReportError entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(CrashReportError entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrashReportError get(Integer id) {
		// TODO Auto-generated method stub
		CrashReportError crashReportError=(CrashReportError) this.sessionFactory.getCurrentSession().get(CrashReportError.class, id);
		return crashReportError;
	}

	@Override
	public CrashReportError update(CrashReportError entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CrashReportError> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(String paramName, String paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(String paramName, Long paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(String paramName, Integer paramValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(BasicQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(String queryString, String[] paramNames,
			String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(String ParamName, Date date1, Date date2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrashReportError> find(String ParamName, Date date) {
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
	public List<CrashReportError> getActiveList() {
		// TODO Auto-generated method stub
		return null;
	}

}
