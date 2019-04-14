package com.web.data;

import java.time.LocalDateTime;

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
	private Long requestFromId;
	private User requestFrom;
	private User requestTo;
	private LocalDateTime friendRequestCreationDate;
	
	public FriendRequest(LocalDateTime friendRequestcreationDate, User requestFrom , User requestTo) {
		this.friendRequestCreationDate = friendRequestcreationDate;
		this.requestFrom = requestFrom;
		this.requestTo = requestTo;
		this.requestFromId = requestFrom.getId();
	}
}