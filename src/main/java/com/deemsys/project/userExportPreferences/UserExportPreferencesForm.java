package com.deemsys.project.userExportPreferences;



/**
 * 
 * @author Deemsys
 * 
 */
public class UserExportPreferencesForm {

	private Integer userId;
	private Integer fieldId;
	private Integer sequenceNo;
	private Integer status;
	public UserExportPreferencesForm(Integer userId, Integer fieldId,
			Integer sequenceNo, Integer status) {
		super();
		this.userId = userId;
		this.fieldId = fieldId;
		this.sequenceNo = sequenceNo;
		this.status = status;
	}
	public UserExportPreferencesForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
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
	
	
	
}
