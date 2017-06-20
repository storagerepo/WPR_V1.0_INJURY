package com.deemsys.project.exportFields;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.ExportFields;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * ExportFields 	 - Entity
 * exportFields 	 - Entity Object
 * exportFieldss 	 - Entity List
 * exportFieldsDAO   - Entity DAO
 * exportFieldsForms - EntityForm List
 * ExportFieldsForm  - EntityForm
 *
 */
@Service
@Transactional
public class ExportFieldsService {

	@Autowired
	ExportFieldsDAO exportFieldsDAO;
	
	@Autowired
	LoginService loginService;
	
	//Get All Entries
	public List<ExportFieldsForm> getExportFieldsList()
	{
		List<ExportFieldsForm> exportFieldsForms=new ArrayList<ExportFieldsForm>();
		
		List<ExportFields> exportFieldss=new ArrayList<ExportFields>();
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			exportFieldss=exportFieldsDAO.getCallerLawyerExportFields();
		}else if(role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
			exportFieldss=exportFieldsDAO.getAutoDealerAllFields();
		}
		
		for (ExportFields exportFields : exportFieldss) {
			//TODO: Fill the List
			exportFieldsForms.add(new ExportFieldsForm(exportFields.getFieldId(), exportFields.getFieldName(), exportFields.getIsCustom(), exportFields.getSequenceNo(), exportFields.getDefaultValue(), exportFields.getFormat(), exportFields.getIsCallerLawyer(), exportFields.getAutoDealerSequence(), exportFields.getIsAutoDealer(), exportFields.getStatus()));
		}
		
		return exportFieldsForms;
	}
	
	//Get Particular Entry
	public ExportFieldsForm getExportFields(Integer getId)
	{
		ExportFields exportFields=new ExportFields();
		
		exportFields=exportFieldsDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		
		ExportFieldsForm exportFieldsForm=new ExportFieldsForm(exportFields.getFieldId(), exportFields.getFieldName(), exportFields.getIsCustom(), exportFields.getSequenceNo(), exportFields.getDefaultValue(), exportFields.getFormat(), exportFields.getIsCallerLawyer(), exportFields.getAutoDealerSequence(), exportFields.getIsAutoDealer(), exportFields.getStatus());
		
		//End
		
		return exportFieldsForm;
	}
	
	// Get Standard Fields Only
	public List<ExportFieldsForm> getStandardExportFieldsList()
	{
		List<ExportFieldsForm> exportFieldsForms=new ArrayList<ExportFieldsForm>();
		
		List<ExportFields> exportFieldss=new ArrayList<ExportFields>();
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			exportFieldss=exportFieldsDAO.getStandardExportFields();
		}else if(role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
			exportFieldss=exportFieldsDAO.getAutoDealerStandardExportFields();
		}
		
		
		for (ExportFields exportFields : exportFieldss) {
			//TODO: Fill the List
			exportFieldsForms.add(new ExportFieldsForm(exportFields.getFieldId(), exportFields.getFieldName(), exportFields.getIsCustom(), exportFields.getSequenceNo(), exportFields.getDefaultValue(), exportFields.getFormat(), exportFields.getIsCallerLawyer(), exportFields.getAutoDealerSequence(), exportFields.getIsAutoDealer(), exportFields.getStatus()));
		}
		
		return exportFieldsForms;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeExportFields(ExportFieldsForm exportFieldsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		ExportFields exportFields=new ExportFields(exportFieldsForm.getFieldName(), exportFieldsForm.getSequenceNo(), exportFieldsForm.getIsCustom(), exportFieldsForm.getDefaultValue(), exportFieldsForm.getFormat(), exportFieldsForm.getIsCallerLawyer(), exportFieldsForm.getAutoDealerSequence(), exportFieldsForm.getIsAutoDealer(), exportFieldsForm.getStatus(), null);
		exportFields.setFieldId(exportFieldsForm.getFieldId());
		//Logic Ends
		
		
		exportFieldsDAO.merge(exportFields);
		return 1;
	}
	
	//Save an Entry
	public int saveExportFields(ExportFieldsForm exportFieldsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ExportFields exportFields=new ExportFields(exportFieldsForm.getFieldName(), exportFieldsForm.getSequenceNo(), exportFieldsForm.getIsCustom(), exportFieldsForm.getDefaultValue(), exportFieldsForm.getFormat(), exportFieldsForm.getIsCallerLawyer(), exportFieldsForm.getAutoDealerSequence(), exportFieldsForm.getIsAutoDealer(), exportFieldsForm.getStatus(), null);
		
		//Logic Ends
		
		exportFieldsDAO.save(exportFields);
		return 1;
	}
	
	//Update an Entry
	public int updateExportFields(ExportFieldsForm exportFieldsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ExportFields exportFields=new ExportFields(exportFieldsForm.getFieldName(), exportFieldsForm.getSequenceNo(), exportFieldsForm.getIsCustom(), exportFieldsForm.getDefaultValue(), exportFieldsForm.getFormat(), exportFieldsForm.getIsCallerLawyer(), exportFieldsForm.getAutoDealerSequence(), exportFieldsForm.getIsAutoDealer(), exportFieldsForm.getStatus(), null);
		exportFields.setFieldId(exportFieldsForm.getFieldId());
		//Logic Ends
		
		exportFieldsDAO.update(exportFields);
		return 1;
	}
	
	//Delete an Entry
	public int deleteExportFields(Integer id)
	{
		exportFieldsDAO.delete(id);
		return 1;
	}
	
	
	
}
