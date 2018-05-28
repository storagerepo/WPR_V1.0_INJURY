package com.deemsys.project.Lawyers;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Lawyer;

public interface LawyersDAO extends IGenericDAO<Lawyer> {

	public List<Lawyer> getLawyersByLawyerAdmin(Integer lawyerAdminId);

	public Lawyer getLawyersByUserId(Integer userId);

	public Long getLawyersByLawyeradminUserId(Integer userId);

}
