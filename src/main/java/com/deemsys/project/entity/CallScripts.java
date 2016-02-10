package com.deemsys.project.entity;

// Generated Oct 1, 2015 1:05:03 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CallScripts generated by hbm2java
 */
@Entity
@Table(name = "CallScripts", catalog = "injurytest")
public class CallScripts implements java.io.Serializable {

	private Integer id;
	private String content;

	public CallScripts() {
	}

	public CallScripts(String content) {
		this.content = content;
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

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
