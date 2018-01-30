package com.deemsys.project.entity;

// Generated 22 Jan, 2018 6:51:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IpAccessList generated by hbm2java
 */
@Entity
@Table(name = "ip_access_list", catalog = "injuryreportsdbpro")
public class IpAccessList implements java.io.Serializable {

	private IpAccessListId id;
	private IpAddress ipAddress;
	private Users users;
	private Date addedDate;
	private Integer status;
	private Set<ActivityLogs> activityLogses = new HashSet<ActivityLogs>(0);

	public IpAccessList() {
	}

	public IpAccessList(IpAccessListId id, IpAddress ipAddress, Users users) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.users = users;
	}

	public IpAccessList(IpAccessListId id, IpAddress ipAddress, Users users,
			Date addedDate, Integer status, Set<ActivityLogs> activityLogses) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.users = users;
		this.addedDate = addedDate;
		this.status = status;
		this.activityLogses = activityLogses;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "ipAddress", column = @Column(name = "ip_address", nullable = false, length = 45)),
			@AttributeOverride(name = "primaryLoginId", column = @Column(name = "primary_login_id", nullable = false)) })
	public IpAccessListId getId() {
		return this.id;
	}

	public void setId(IpAccessListId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ip_address", nullable = false, insertable = false, updatable = false)
	public IpAddress getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(IpAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_login_id", nullable = false, insertable = false, updatable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "added_date", length = 10)
	public Date getAddedDate() {
		return this.addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ipAccessList")
	public Set<ActivityLogs> getActivityLogses() {
		return this.activityLogses;
	}

	public void setActivityLogses(Set<ActivityLogs> activityLogses) {
		this.activityLogses = activityLogses;
	}

}
