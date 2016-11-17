package com.deemsys.project.UserLookupPreferences;

import java.util.List;

public class UserLookupPreferenceMappedForm {
	private Integer type;
	private List<Integer> preferredId;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<Integer> getpreferredId() {
		return preferredId;
	}
	public void setpreferredId(List<Integer> preferredId) {
		this.preferredId = preferredId;
	}
	public UserLookupPreferenceMappedForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserLookupPreferenceMappedForm(Integer type, List<Integer> preferredId) {
		super();
		this.type = type;
		this.preferredId = preferredId;
	}
	
}
