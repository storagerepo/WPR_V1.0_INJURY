package com.deemsys.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="log_sample",catalog="injuryreportsdbpro")
public class LogSample {

	public LogSample() {
		// TODO Auto-generated constructor stub
	}
	
	private Integer id;
	private String sessionId;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,unique=true)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="session_id")
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public LogSample(String sessionId) {
		super();
		this.sessionId = sessionId;
	}

}
