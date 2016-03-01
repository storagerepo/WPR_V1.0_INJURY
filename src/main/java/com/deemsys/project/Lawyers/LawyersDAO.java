package com.deemsys.project.Lawyers;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Lawyers;

public interface LawyersDAO extends IGenericDAO<Lawyers>{
	
	public List<Lawyers> getLawyersByLawyerAdmin(Integer lawyerAdminId);
	public Lawyers getLawyersByUserId(Integer userId);
	
}
