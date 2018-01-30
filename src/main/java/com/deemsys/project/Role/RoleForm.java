package com.deemsys.project.Role;

public class RoleForm {
	
	private Integer roleId;
	private String role;
	private Integer status;
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public RoleForm(Integer roleId, String role, Integer status) {
		super();
		this.roleId = roleId;
		this.role = role;
		this.status = status;
	}

	public RoleForm() {
		// TODO Auto-generated constructor stub
	}

}
