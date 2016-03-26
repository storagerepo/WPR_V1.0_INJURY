package com.deemsys.project.entity;

// Generated Mar 16, 2016 12:32:39 PM by Hibernate Tools 3.4.0.CR1

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
 * Lawyer generated by hbm2java
 */
@Entity
@Table(name = "lawyer", catalog = "injurytest")
public class Lawyer implements java.io.Serializable {

	private Integer lawyerId;
	private LawyerAdmin lawyerAdmin;
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
	private Set<PatientLawyerAdminMap> patientLawyerAdminMaps = new HashSet<PatientLawyerAdminMap>(
			0);
	private Set<LawyerCountyMap> lawyerCountyMaps = new HashSet<LawyerCountyMap>(
			0);

	public Lawyer() {
	}

	public Lawyer(LawyerAdmin lawyerAdmin, Users users, String firstName,
			String lastName, String street, String city, String state,
			String zipcode, String emailAddress, String phoneNumber,
			String notes, Integer status,
			Set<PatientLawyerAdminMap> patientLawyerAdminMaps,
			Set<LawyerCountyMap> lawyerCountyMaps) {
		this.lawyerAdmin = lawyerAdmin;
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
		this.patientLawyerAdminMaps = patientLawyerAdminMaps;
		this.lawyerCountyMaps = lawyerCountyMaps;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lawyer_id", unique = true, nullable = false)
	public Integer getLawyerId() {
		return this.lawyerId;
	}

	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lawyer_admin_id")
	public LawyerAdmin getLawyerAdmin() {
		return this.lawyerAdmin;
	}

	public void setLawyerAdmin(LawyerAdmin lawyerAdmin) {
		this.lawyerAdmin = lawyerAdmin;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lawyer")
	public Set<PatientLawyerAdminMap> getPatientLawyerAdminMaps() {
		return this.patientLawyerAdminMaps;
	}

	public void setPatientLawyerAdminMaps(
			Set<PatientLawyerAdminMap> patientLawyerAdminMaps) {
		this.patientLawyerAdminMaps = patientLawyerAdminMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lawyer")
	public Set<LawyerCountyMap> getLawyerCountyMaps() {
		return this.lawyerCountyMaps;
	}

	public void setLawyerCountyMaps(Set<LawyerCountyMap> lawyerCountyMaps) {
		this.lawyerCountyMaps = lawyerCountyMaps;
	}

}
