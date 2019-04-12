package com.web.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private User requestFrom;
	private Long requestFromId;
	private User requestTo;
	private Long requestToId;
	private String creationDate;
	
	public FriendRequest(String creationDate, User requestFrom , User requestTo) {
		this.creationDate = creationDate;
		this.requestFrom = requestFrom;
		this.requestTo = requestTo;
		this.requestToId = requestTo.getId();
		this.requestFromId = requestFrom.getId();
	}
}