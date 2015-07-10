package com.deemsys.project.entity;
// Generated Jul 10, 2015 10:58:57 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Staff generated by hbm2java
 */
@Entity
@Table(name = "Staff", catalog = "injury")
public class Staff implements java.io.Serializable {

	private Integer id;
	private String role;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String notes;
	private Integer isEnable;
	private Set<Patients> patientses = new HashSet<Patients>(0);

	public Staff() {
	}

	public Staff(String role, String username, String password,
			String firstName, String lastName, String phoneNumber,
			String emailAddress, String notes, Integer isEnable,
			Set<Patients> patientses) {
		this.role = role;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.isEnable = isEnable;
		this.patientses = patientses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "role", length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "phone_number", length = 15)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email_address", length = 60)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "is_enable")
	public Integer getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff")
	public Set<Patients> getPatientses() {
		return this.patientses;
	}

	public void setPatientses(Set<Patients> patientses) {
		this.patientses = patientses;
	}

}
