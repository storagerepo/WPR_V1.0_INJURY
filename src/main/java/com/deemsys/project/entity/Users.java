package com.deemsys.project.entity;

// Generated 17 Jan, 2017 1:10:25 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "injuryreportsdbtest")
public class Users implements java.io.Serializable {

	private Integer userId;
	private Roles roles;
	private String username;
	private String password;
	private Integer isEnable;
	private Integer isPasswordChanged;
	private Integer isDisclaimerAccepted;
	private String productToken;
	private Integer isPrivilegedUser;
	private Integer status;
	private Set<RatingReviews> ratingReviewses = new HashSet<RatingReviews>(0);

	public Users() {
	}

	public Users(Roles roles, String username, String password,
			Integer isEnable, Integer isPasswordChanged,
			Integer isDisclaimerAccepted, String productToken,
			Integer isPrivilegedUser, Integer status,
			Set<RatingReviews> ratingReviewses) {
		this.roles = roles;
		this.username = username;
		this.password = password;
		this.isEnable = isEnable;
		this.isPasswordChanged = isPasswordChanged;
		this.isDisclaimerAccepted = isDisclaimerAccepted;
		this.productToken = productToken;
		this.isPrivilegedUser = isPrivilegedUser;
		this.status = status;
		this.ratingReviewses = ratingReviewses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	@Column(name = "username", length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "is_enable")
	public Integer getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name = "is_password_changed")
	public Integer getIsPasswordChanged() {
		return this.isPasswordChanged;
	}

	public void setIsPasswordChanged(Integer isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}

	@Column(name = "is_disclaimer_accepted")
	public Integer getIsDisclaimerAccepted() {
		return this.isDisclaimerAccepted;
	}

	public void setIsDisclaimerAccepted(Integer isDisclaimerAccepted) {
		this.isDisclaimerAccepted = isDisclaimerAccepted;
	}

	@Column(name = "product_token", length = 45)
	public String getProductToken() {
		return this.productToken;
	}

	public void setProductToken(String productToken) {
		this.productToken = productToken;
	}

	@Column(name = "is_privileged_user")
	public Integer getIsPrivilegedUser() {
		return this.isPrivilegedUser;
	}

	public void setIsPrivilegedUser(Integer isPrivilegedUser) {
		this.isPrivilegedUser = isPrivilegedUser;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<RatingReviews> getRatingReviewses() {
		return this.ratingReviewses;
	}

	public void setRatingReviewses(Set<RatingReviews> ratingReviewses) {
		this.ratingReviewses = ratingReviewses;
	}

}
