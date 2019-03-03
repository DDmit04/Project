package com.web.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private User requestFrom;
	private Long requestFromId;
	private User requestTo;
	private Long requestToId;
	private String friendRequestText; 
	private String creationDate;
	
	public FriendRequest() {
	}
	public FriendRequest(String frendReqestText, String creationDate, User requestFrom , User requestTo) {
		this.requestFrom = requestFrom;
		this.requestTo = requestTo;
		this.friendRequestText = frendReqestText;
		this.creationDate = creationDate;
		this.requestToId = requestTo.getId();
		this.requestFromId = requestFrom.getId();
	}
	public String getFriendRequestText() {
		return friendRequestText;
	}
	public void setFriendReuqestText(String frendReqestText) {
		this.friendRequestText = frendReqestText;
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
	public User getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(User reqiestFrom) {
		this.requestFrom = reqiestFrom;
	}
	public User getRequestTo() {
		return requestTo;
	}
	public void setRequestTo(User reqiestTo) {
		this.requestTo = reqiestTo;
	}
	public Long getRequestFromId() {
		return requestFromId;
	}
	public void setRequestFromId(Long requestFromId) {
		this.requestFromId = requestFromId;
	}
	public Long getRequestToId() {
		return requestToId;
	}
	public void setRequestToId(Long requestToId) {
		this.requestToId = requestToId;
	}
}