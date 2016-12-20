package com.deemsys.project.APIRequests;

public class APICreditCardForm {
	private String customerProductToken;
	private Integer accountType;
	private String accountNumber;
	private String expiryMonth;
	private String expiryYear;	
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private String billingZipcode;
	
	public String getCustomerProductToken() {
		return customerProductToken;
	}
	public void setCustomerProductToken(String customerProductToken) {
		this.customerProductToken = customerProductToken;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public String getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingZipcode() {
		return billingZipcode;
	}
	public void setBillingZipcode(String billingZipcode) {
		this.billingZipcode = billingZipcode;
	}
	
	public APICreditCardForm(String customerProductToken, Integer accountType,
			String accountNumber, String expiryMonth, String expiryYear,
			String billingAddress, String billingCity, String billingState,
			String billingZipcode) {
		super();
		this.customerProductToken = customerProductToken;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.billingAddress = billingAddress;
		this.billingCity = billingCity;
		this.billingState = billingState;
		this.billingZipcode = billingZipcode;
	}
	public APICreditCardForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
