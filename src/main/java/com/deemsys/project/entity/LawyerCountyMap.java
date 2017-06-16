package com.deemsys.project.entity;

// Generated 16 Jun, 2017 12:16:19 PM by Hibernate Tools 3.4.0.CR1

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
 * LawyerCountyMap generated by hbm2java
 */
@Entity
@Table(name = "lawyer_county_map", catalog = "injuryreportsdb")
public class LawyerCountyMap implements java.io.Serializable {

	private LawyerCountyMapId id;
	private Lawyer lawyer;
	private County county;
	private Date subscribedDate;
	private Integer status;

	public LawyerCountyMap() {
	}

	public LawyerCountyMap(LawyerCountyMapId id, Lawyer lawyer, County county) {
		this.id = id;
		this.lawyer = lawyer;
		this.county = county;
	}

	public LawyerCountyMap(LawyerCountyMapId id, Lawyer lawyer, County county,
			Date subscribedDate, Integer status) {
		this.id = id;
		this.lawyer = lawyer;
		this.county = county;
		this.subscribedDate = subscribedDate;
		this.status = status;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "lawyerId", column = @Column(name = "lawyer_id", nullable = false)),
			@AttributeOverride(name = "countyId", column = @Column(name = "county_id", nullable = false)) })
	public LawyerCountyMapId getId() {
		return this.id;
	}

	public void setId(LawyerCountyMapId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lawyer_id", nullable = false, insertable = false, updatable = false)
	public Lawyer getLawyer() {
		return this.lawyer;
	}

	public void setLawyer(Lawyer lawyer) {
		this.lawyer = lawyer;
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
