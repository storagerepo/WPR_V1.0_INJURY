package com.deemsys.project.entity;

// Generated 20 Jun, 2017 11:39:16 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserExportPreferences generated by hbm2java
 */
@Entity
@Table(name = "user_export_preferences", catalog = "injuryreportsdb")
public class UserExportPreferences implements java.io.Serializable {

	private UserExportPreferencesId id;
	private Users users;
	private ExportFields exportFields;

	public UserExportPreferences() {
	}

	public UserExportPreferences(UserExportPreferencesId id, Users users) {
		this.id = id;
		this.users = users;
	}

	public UserExportPreferences(UserExportPreferencesId id, Users users,
			ExportFields exportFields) {
		this.id = id;
		this.users = users;
		this.exportFields = exportFields;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "fieldId", column = @Column(name = "field_id")),
			@AttributeOverride(name = "sequenceNo", column = @Column(name = "sequence_no")),
			@AttributeOverride(name = "defaultValue", column = @Column(name = "default_value", length = 600)),
			@AttributeOverride(name = "format", column = @Column(name = "format")),
			@AttributeOverride(name = "status", column = @Column(name = "status")) })
	public UserExportPreferencesId getId() {
		return this.id;
	}

	public void setId(UserExportPreferencesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id", insertable = false, updatable = false)
	public ExportFields getExportFields() {
		return this.exportFields;
	}

	public void setExportFields(ExportFields exportFields) {
		this.exportFields = exportFields;
	}

}
