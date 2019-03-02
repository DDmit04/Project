package com.web.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FrendReqest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private User reqiestFrom;
	private User reqiestTo;
	private String frendReqestText; 
	private String creationDate;
	
	public FrendReqest() {
	}
	public FrendReqest(String frendReqestText, String creationDate) {
		this.frendReqestText = frendReqestText;
		this.creationDate = creationDate;
	}
	public String getFrendReqestText() {
		return frendReqestText;
	}
	public void setFrendReqestText(String frendReqestText) {
		this.frendReqestText = frendReqestText;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getReqiestFrom() {
		return reqiestFrom;
	}
	public void setReqiestFrom(User reqiestFrom) {
		this.reqiestFrom = reqiestFrom;
	}
	public User getReqiestTo() {
		return reqiestTo;
	}
	public void setReqiestTo(User reqiestTo) {
		this.reqiestTo = reqiestTo;
	}
}