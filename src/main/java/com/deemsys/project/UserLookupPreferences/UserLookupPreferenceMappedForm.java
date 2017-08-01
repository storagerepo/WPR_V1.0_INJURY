package com.deemsys.project.UserLookupPreferences;

import java.util.List;

public class UserLookupPreferenceMappedForm {
	private Integer type;
	private Integer countyId;
	private List<Integer> preferredId;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public List<Integer> getPreferredId() {
		return preferredId;
	}
	public void setPreferredId(List<Integer> preferredId) {
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
