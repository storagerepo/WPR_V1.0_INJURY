package com.deemsys.project.entity;

// Generated 2 Jun, 2017 3:50:38 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LawyerAdminCountyMap generated by hbm2java
 */
@Entity
@Table(name = "lawyer_admin_county_map", catalog = "injuryreportsdbtest")
public class LawyerAdminCountyMap implements java.io.Serializable {

	private LawyerAdminCountyMapId id;
	private LawyerAdmin lawyerAdmin;
	private County county;
	private Date subscribedDate;
	private Integer status;

	public LawyerAdminCountyMap() {
	}

	public LawyerAdminCountyMap(LawyerAdminCountyMapId id,
			LawyerAdmin lawyerAdmin, County county) {
		this.id = id;
		this.lawyerAdmin = lawyerAdmin;
		this.county = county;
	}

	public LawyerAdminCountyMap(LawyerAdminCountyMapId id,
			LawyerAdmin lawyerAdmin, County county, Date subscribedDate,
			Integer status) {
		this.id = id;
		this.lawyerAdmin = lawyerAdmin;
		this.county = county;
		this.subscribedDate = subscribedDate;
		this.status = status;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "lawyerAdminId", column = @Column(name = "lawyer_admin_id", nullable = false)),
			@AttributeOverride(name = "countyId", column = @Column(name = "county_id", nullable = false)) })
	public LawyerAdminCountyMapId getId() {
		return this.id;
	}

	public void setId(LawyerAdminCountyMapId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lawyer_admin_id", nullable = false, insertable = false, updatable = false)
	public LawyerAdmin getLawyerAdmin() {
		return this.lawyerAdmin;
	}

	public void setLawyerAdmin(LawyerAdmin lawyerAdmin) {
		this.lawyerAdmin = lawyerAdmin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id", nullable = false, insertable = false, updatable = false)
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "subscribed_date", length = 10)
	public Date getSubscribedDate() {
		return this.subscribedDate;
	}

	public void setSubscribedDate(Date subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
