package com.deemsys.project.exportFields;



/**
 * 
 * @author Deemsys
 * 
 */
public class ExportFieldsForm {

	private Integer fieldId;
	private String fieldName;
	private Integer isCustom;
	private Integer sequenceNo;
	private String defaultValue;
	private Integer format;
	private Integer isCallerLawyer;
	private Integer autoDealerSequence;
	private Integer isAutoDealer;
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
	public Integer getFormat() {
		return format;
	}
	public void setFormat(Integer format) {
		this.format = format;
	}
	public Integer getIsCallerLawyer() {
		return isCallerLawyer;
	}
	public void setIsCallerLawyer(Integer isCallerLawyer) {
		this.isCallerLawyer = isCallerLawyer;
	}
	public Integer getAutoDealerSequence() {
		return autoDealerSequence;
	}
	public void setAutoDealerSequence(Integer autoDealerSequence) {
		this.autoDealerSequence = autoDealerSequence;
	}
	public Integer getIsAutoDealer() {
		return isAutoDealer;
	}
	public void setIsAutoDealer(Integer isAutoDealer) {
		this.isAutoDealer = isAutoDealer;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public ExportFieldsForm(Integer fieldId, String fieldName, Integer isCustom, Integer sequenceNo, String defaultValue, Integer format,
			Integer isCallerLawyer, Integer autoDealerSequence, Integer isAutoDealer, Integer status) {
		super();
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.isCustom = isCustom;
		this.sequenceNo = sequenceNo;
		this.defaultValue = defaultValue;
		this.format = format;
		this.isCallerLawyer = isCallerLawyer;
		this.autoDealerSequence = autoDealerSequence;
		this.isAutoDealer = isAutoDealer;
		this.status = status;
	}
	public ExportFieldsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
