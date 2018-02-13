package com.deemsys.project.RatingReviews;

public class RatingViewForm {
	private String callCentersAverageRating;
	private String lawyersAverageRating;
	private String dealersAverageRating;
	private String shopOwnersAverageRating;
	private Integer numberOfCallCentersRating;
	private Integer numberOfLawyersRating;
	private Integer numberOfDealersRating;
	private Integer numberOfShopsRating;
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
	public String getDealersAverageRating() {
		return dealersAverageRating;
	}
	public void setDealersAverageRating(String dealersAverageRating) {
		this.dealersAverageRating = dealersAverageRating;
	}
	public String getShopOwnersAverageRating() {
		return shopOwnersAverageRating;
	}
	public void setShopOwnersAverageRating(String shopOwnersAverageRating) {
		this.shopOwnersAverageRating = shopOwnersAverageRating;
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
	public Integer getNumberOfDealersRating() {
		return numberOfDealersRating;
	}
	public void setNumberOfDealersRating(Integer numberOfDealersRating) {
		this.numberOfDealersRating = numberOfDealersRating;
	}
	public Integer getNumberOfShopsRating() {
		return numberOfShopsRating;
	}
	public void setNumberOfShopsRating(Integer numberOfShopsRating) {
		this.numberOfShopsRating = numberOfShopsRating;
	}
	public Integer getTotalNumberOfRating() {
		return totalNumberOfRating;
	}
	public void setTotalNumberOfRating(Integer totalNumberOfRating) {
		this.totalNumberOfRating = totalNumberOfRating;
	}
	public RatingViewForm(String callCentersAverageRating,
			String lawyersAverageRating, String dealersAverageRating, String shopOwnersAverageRating, Integer numberOfCallCentersRating,
			Integer numberOfLawyersRating, Integer numberOfDealersRating, Integer numberOfShopsRating, Integer totalNumberOfRating) {
		super();
		this.callCentersAverageRating = callCentersAverageRating;
		this.lawyersAverageRating = lawyersAverageRating;
		this.dealersAverageRating = dealersAverageRating;
		this.shopOwnersAverageRating = shopOwnersAverageRating;
		this.numberOfCallCentersRating = numberOfCallCentersRating;
		this.numberOfLawyersRating = numberOfLawyersRating;
		this.numberOfDealersRating = numberOfDealersRating;
		this.numberOfShopsRating = numberOfShopsRating;
		this.totalNumberOfRating = totalNumberOfRating;
	}
	public RatingViewForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
