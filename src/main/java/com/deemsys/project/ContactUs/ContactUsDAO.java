package com.deemsys.project.ContactUs;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ContactUs;
/**
 * 
 * @author Deemsys
 *
 */
public interface ContactUsDAO extends IGenericDAO<ContactUs>{
	public List<ContactUsForm> getContactUsListWitLatestStatus();
}
