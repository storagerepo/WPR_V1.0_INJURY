package com.deemsys.project.LawyerCountyMapping;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.LawyerCountyMapping;

public interface LawyerCountyMappingDAO extends IGenericDAO<LawyerCountyMapping> {

	public List<LawyerCountyMapping> getLaweCountyMappingsByLawyerId(Integer lawyerId);
}
