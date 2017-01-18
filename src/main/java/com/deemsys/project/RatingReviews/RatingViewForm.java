package com.deemsys.project.RatingReviews;

public class RatingViewForm {
	private String callCentersAverageRating;
	private String lawyersAverageRating;
	private Integer numberOfCallCentersRating;
	private Integer numberOfLawyersRating;
	private Integer totalNumberOfRating;
	public String getCallCentersAverageRating() {
		return callCentersAverageRating;
	}
	public void setCallCentersAverageRating(String callCentersAverageRating) {
		this.callCentersAverageRating = callCentersAverageRating;
	}
	public String getLawyersAverageRating() {
		return lawyersAverageRating;
	}
	public void setLawyersAverageRating(String lawyersAverageRating) {
		this.lawyersAverageRating = lawyersAverageRating;
	}
	public Integer getNumberOfCallCentersRating() {
		return numberOfCallCentersRating;
	}
	public void setNumberOfCallCentersRating(Integer numberOfCallCentersRating) {
		this.numberOfCallCentersRating = numberOfCallCentersRating;
	}
	public Integer getNumberOfLawyersRating() {
		return numberOfLawyersRating;
	}
	public void setNumberOfLawyersRating(Integer numberOfLawyersRating) {
		this.numberOfLawyersRating = numberOfLawyersRating;
	}
	public Integer getTotalNumberOfRating() {
		return totalNumberOfRating;
	}
	public void setTotalNumberOfRating(Integer totalNumberOfRating) {
		this.totalNumberOfRating = totalNumberOfRating;
	}
	public RatingViewForm(String callCentersAverageRating,
			String lawyersAverageRating, Integer numberOfCallCentersRating,
			Integer numberOfLawyersRating, Integer totalNumberOfRating) {
		super();
		this.callCentersAverageRating = callCentersAverageRating;
		this.lawyersAverageRating = lawyersAverageRating;
		this.numberOfCallCentersRating = numberOfCallCentersRating;
		this.numberOfLawyersRating = numberOfLawyersRating;
		this.totalNumberOfRating = totalNumberOfRating;
	}
	public RatingViewForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
