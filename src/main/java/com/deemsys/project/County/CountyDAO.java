package com.deemsys.project.County;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.County;

public interface CountyDAO extends IGenericDAO<County> {
     public County getCountyByName(String countyName);
}
