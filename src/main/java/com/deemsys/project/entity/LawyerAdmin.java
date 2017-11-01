package com.deemsys.project.entity;

// Generated 19 Aug, 2017 10:03:05 AM by Hibernate Tools 3.4.0.CR1

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
 * LawyerAdmin generated by hbm2java
 */
@Entity
@Table(name = "lawyer_admin", catalog = "injuryreportsdbprotest")
public class LawyerAdmin implements java.io.Serializable {

	private Integer lawyerAdminId;
	private Users users;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String emailAddress;
	private String phoneNumber;
	private String notes;
	private Integer status;
	private Set<DirectReportLawyerAdminMap> directReportLawyerAdminMaps = new HashSet<DirectReportLawyerAdminMap>(
			0);
	private Set<PatientLawyerAdminMap> patientLawyerAdminMaps = new HashSet<PatientLawyerAdminMap>(
			0);
	private Set<LawyerAdminCountyMap> lawyerAdminCountyMaps = new HashSet<LawyerAdminCountyMap>(
			0);
	private Set<Lawyer> lawyers = new HashSet<Lawyer>(0);

	public LawyerAdmin() {
	}

	public LawyerAdmin(Users users, String firstName, String lastName,
			String street, String city, String state, String zipcode,
			String emailAddress, String phoneNumber, String notes,
			Integer status,
			Set<DirectReportLawyerAdminMap> directReportLawyerAdminMaps,
			Set<PatientLawyerAdminMap> patientLawyerAdminMaps,
			Set<LawyerAdminCountyMap> lawyerAdminCountyMaps, Set<Lawyer> lawyers) {
		this.users = users;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.status = status;
		this.directReportLawyerAdminMaps = directReportLawyerAdminMaps;
		this.patientLawyerAdminMaps = patientLawyerAdminMaps;
		this.lawyerAdminCountyMaps = lawyerAdminCountyMaps;
		this.lawyers = lawyers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lawyer_admin_id", unique = true, nullable = false)
	public Integer getLawyerAdminId() {
		return this.lawyerAdminId;
	}

	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "first_name", length = 45)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "street", length = 45)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "city", length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zipcode", length = 10)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "email_address", length = 60)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "phone_number", length = 20)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lawyerAdmin")
	public Set<DirectReportLawyerAdminMap> getDirectReportLawyerAdminMaps() {
		return this.directReportLawyerAdminMaps;
	}

	public void setDirectReportLawyerAdminMaps(
			Set<DirectReportLawyerAdminMap> directReportLawyerAdminMaps) {
		this.directReportLawyerAdminMaps = directReportLawyerAdminMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lawyerAdmin")
	public Set<PatientLawyerAdminMap> getPatientLawyerAdminMaps() {
		return this.patientLawyerAdminMaps;
	}

	public void setPatientLawyerAdminMaps(
			Set<PatientLawyerAdminMap> patientLawyerAdminMaps) {
		this.patientLawyerAdminMaps = patientLawyerAdminMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lawyerAdmin")
	public Set<LawyerAdminCountyMap> getLawyerAdminCountyMaps() {
		return this.lawyerAdminCountyMaps;
	}

	public void setLawyerAdminCountyMaps(
			Set<LawyerAdminCountyMap> lawyerAdminCountyMaps) {
		this.lawyerAdminCountyMaps = lawyerAdminCountyMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lawyerAdmin")
	public Set<Lawyer> getLawyers() {
		return this.lawyers;
	}

	public void setLawyers(Set<Lawyer> lawyers) {
		this.lawyers = lawyers;
	}

}
