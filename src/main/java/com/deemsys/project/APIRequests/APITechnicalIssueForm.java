package com.deemsys.project.APIRequests;

public class APITechnicalIssueForm {
	private String productId;
	private String role;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private String emailId;
	private String platform;
	private String feature;
	private String issueTitle;
	private String description;
	private Integer issueStatus;
	private Integer status;
	private Integer issueAddedFrom;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getIssueTitle() {
		return issueTitle;
	}
	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getIssueAddedFrom() {
		return issueAddedFrom;
	}
	public void setIssueAddedFrom(Integer issueAddedFrom) {
		this.issueAddedFrom = issueAddedFrom;
	}
	public APITechnicalIssueForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public APITechnicalIssueForm(String productId, String role,
			String firstName, String lastName, String contactNumber,
			String emailId, String platform, String feature, String issueTitle,
			String description, Integer issueStatus, Integer status,
			Integer issueAddedFrom) {
		super();
		this.productId = productId;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
		this.platform = platform;
		this.feature = feature;
		this.issueTitle = issueTitle;
		this.description = description;
		this.issueStatus = issueStatus;
		this.status = status;
		this.issueAddedFrom = issueAddedFrom;
	}
	
}
