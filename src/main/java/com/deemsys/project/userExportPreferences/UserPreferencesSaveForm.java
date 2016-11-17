package com.deemsys.project.userExportPreferences;

import java.util.ArrayList;
import java.util.List;

import com.deemsys.project.exportFields.ExportFieldsForm;

public class UserPreferencesSaveForm {

	public List<ExportFieldsForm> exportFieldsForms=new ArrayList<ExportFieldsForm>();

	public List<ExportFieldsForm> getExportFieldsForms() {
		return exportFieldsForms;
	}

	public void setExportFieldsForms(List<ExportFieldsForm> exportFieldsForms) {
		this.exportFieldsForms = exportFieldsForms;
	}

	public UserPreferencesSaveForm(List<ExportFieldsForm> exportFieldsForms) {
		super();
		this.exportFieldsForms = exportFieldsForms;
	}

	public UserPreferencesSaveForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
