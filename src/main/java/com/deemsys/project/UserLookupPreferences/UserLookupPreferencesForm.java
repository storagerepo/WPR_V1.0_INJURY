package com.deemsys.project.UserLookupPreferences;


import java.util.List;

/**
 * 
 * @author Deemsys
 * 
 */
public class UserLookupPreferencesForm {

	private Integer userId;
	private List<UserLookupPreferenceMappedForm> userLookupPreferenceMappedForms;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<UserLookupPreferenceMappedForm> getUserLookupPreferenceMappedForms() {
		return userLookupPreferenceMappedForms;
	}

	public void setUserLookupPreferenceMappedForms(
			List<UserLookupPreferenceMappedForm> userLookupPreferenceMappedForms) {
		this.userLookupPreferenceMappedForms = userLookupPreferenceMappedForms;
	}

	public UserLookupPreferencesForm(Integer userId,
			List<UserLookupPreferenceMappedForm> userLookupPreferenceMappedForms) {
		super();
		this.userId = userId;
		this.userLookupPreferenceMappedForms = userLookupPreferenceMappedForms;
	}

	public UserLookupPreferencesForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
