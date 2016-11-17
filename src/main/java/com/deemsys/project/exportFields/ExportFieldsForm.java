package com.deemsys.project.exportFields;


import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author Deemsys
 * 
 */
public class ExportFieldsForm {

	private Integer fieldId;
	private String fieldName;
	private Integer status;
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public ExportFieldsForm(Integer fieldId, String fieldName, Integer status) {
		super();
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.status = status;
	}
	public ExportFieldsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
