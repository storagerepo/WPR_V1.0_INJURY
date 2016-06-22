package com.deemsys.project.ContactUsLog;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ContactUsLog;
/**
 * 
 * @author Deemsys
 *
 */
public interface ContactUsLogDAO extends IGenericDAO<ContactUsLog>{
	public List<ContactUsLog> getContactUsLogByContactUsId(Integer contactUsId);
}
