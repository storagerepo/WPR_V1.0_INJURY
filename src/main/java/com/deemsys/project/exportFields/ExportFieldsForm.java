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
	private Integer isCustom;
	private String defaultValue;
	private Integer sequenceNo;
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
	public Integer getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(Integer isCustom) {
		this.isCustom = isCustom;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Integer getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public ExportFieldsForm(Integer fieldId, String fieldName, Integer isCustom, String defaultValue, Integer sequenceNo, Integer status) {
		super();
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.isCustom = isCustom;
		this.defaultValue = defaultValue;
		this.sequenceNo = sequenceNo;
		this.status = status;
	}
	public ExportFieldsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
