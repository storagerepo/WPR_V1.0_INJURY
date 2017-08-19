package com.deemsys.project.RatingReviews;


import java.math.BigDecimal;

/**
 * 
 * @author Deemsys
 * 
 */
public class RatingReviewsForm {

	private Integer ratingReviewId;
	private Integer userId;
	private String role;
	private Integer ratingQ1;
	private Integer ratingQ2;
	private Integer ratingQ3;
	private Integer ratingQ4;
	private Integer ratingQ5;
	private String reviewQ1;
	private String reviewQ2;
	private String reviewQ3;
	private String reviewQ4;
	private BigDecimal overallRating;
	private String reviewDateTime;
	private Integer status;
	
	
	public Integer getRatingReviewId() {
		return ratingReviewId;
	}

	public void setRatingReviewId(Integer ratingReviewId) {
		this.ratingReviewId = ratingReviewId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getRatingQ1() {
		return ratingQ1;
	}

	public void setRatingQ1(Integer ratingQ1) {
		this.ratingQ1 = ratingQ1;
	}

	public Integer getRatingQ2() {
		return ratingQ2;
	}

	public void setRatingQ2(Integer ratingQ2) {
		this.ratingQ2 = ratingQ2;
	}

	public Integer getRatingQ3() {
		return ratingQ3;
	}

	public void setRatingQ3(Integer ratingQ3) {
		this.ratingQ3 = ratingQ3;
	}

	public Integer getRatingQ4() {
		return ratingQ4;
	}

	public void setRatingQ4(Integer ratingQ4) {
		this.ratingQ4 = ratingQ4;
	}

	public Integer getRatingQ5() {
		return ratingQ5;
	}

	public void setRatingQ5(Integer ratingQ5) {
		this.ratingQ5 = ratingQ5;
	}

	public String getReviewQ1() {
		return reviewQ1;
	}

	public void setReviewQ1(String reviewQ1) {
		this.reviewQ1 = reviewQ1;
	}

	public String getReviewQ2() {
		return reviewQ2;
	}

	public void setReviewQ2(String reviewQ2) {
		this.reviewQ2 = reviewQ2;
	}

	public String getReviewQ3() {
		return reviewQ3;
	}

	public void setReviewQ3(String reviewQ3) {
		this.reviewQ3 = reviewQ3;
	}

	public String getReviewQ4() {
		return reviewQ4;
	}

	public void setReviewQ4(String reviewQ4) {
		this.reviewQ4 = reviewQ4;
	}

	

	public BigDecimal getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(BigDecimal overallRating) {
		this.overallRating = overallRating;
	}

	public String getReviewDateTime() {
		return reviewDateTime;
	}

	public void setReviewDateTime(String reviewDateTime) {
		this.reviewDateTime = reviewDateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public RatingReviewsForm(Integer ratingReviewId, Integer userId, String role,
			Integer ratingQ1, Integer ratingQ2, Integer ratingQ3,
			Integer ratingQ4, Integer ratingQ5, String reviewQ1,
			String reviewQ2, String reviewQ3, String reviewQ4,
			BigDecimal overallRating, String reviewDateTime, Integer status) {
		super();
		this.ratingReviewId = ratingReviewId;
		this.userId = userId;
		this.role = role;
		this.ratingQ1 = ratingQ1;
		this.ratingQ2 = ratingQ2;
		this.ratingQ3 = ratingQ3;
		this.ratingQ4 = ratingQ4;
		this.ratingQ5 = ratingQ5;
		this.reviewQ1 = reviewQ1;
		this.reviewQ2 = reviewQ2;
		this.reviewQ3 = reviewQ3;
		this.reviewQ4 = reviewQ4;
		this.overallRating = overallRating;
		this.reviewDateTime = reviewDateTime;
		this.status = status;
	}

	public RatingReviewsForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
