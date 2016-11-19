package com.deemsys.project.exportFields;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ExportFields;
/**
 * 
 * @author Deemsys
 *
 */
public interface ExportFieldsDAO extends IGenericDAO<ExportFields>{
		public List<ExportFields> getStandardExportFields();
}
