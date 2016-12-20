package com.deemsys.project.APIRequests;

public class APIBillSearchForm {
	private Long customerId;
	private String customerProductToken;
	private String subcriptionFromDate;
	private String subscriptionToDate;
	private String billFromDate;
	private String billToDate;
	private String billId;
	private Integer itemsPerPage;
	private Integer pageNumber;
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerProductToken() {
		return customerProductToken;
	}
	public void setCustomerProductToken(String customerProductToken) {
		this.customerProductToken = customerProductToken;
	}
	public String getSubcriptionFromDate() {
		return subcriptionFromDate;
	}
	public void setSubcriptionFromDate(String subcriptionFromDate) {
		this.subcriptionFromDate = subcriptionFromDate;
	}
	public String getSubscriptionToDate() {
		return subscriptionToDate;
	}
	public void setSubscriptionToDate(String subscriptionToDate) {
		this.subscriptionToDate = subscriptionToDate;
	}
	public String getBillFromDate() {
		return billFromDate;
	}
	public void setBillFromDate(String billFromDate) {
		this.billFromDate = billFromDate;
	}
	public String getBillToDate() {
		return billToDate;
	}
	public void setBillToDate(String billToDate) {
		this.billToDate = billToDate;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public APIBillSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public APIBillSearchForm(Long customerId, String customerProductToken,
			String subcriptionFromDate, String subscriptionToDate,
			String billFromDate, String billToDate, String billId,
			Integer itemsPerPage, Integer pageNumber) {
		super();
		this.customerId = customerId;
		this.customerProductToken = customerProductToken;
		this.subcriptionFromDate = subcriptionFromDate;
		this.subscriptionToDate = subscriptionToDate;
		this.billFromDate = billFromDate;
		this.billToDate = billToDate;
		this.billId = billId;
		this.itemsPerPage = itemsPerPage;
		this.pageNumber = pageNumber;
	}
	
}
