package com.web.data;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MessageJson {

	private Long senderId;
    private String content;
    private String sender;
    private String userPicName;
    private LocalDateTime messageDate;
    private MessageType type;
    
	public MessageJson(Long senderId, String content, String sender, String userPicName, LocalDateTime messageDate, MessageType type) {
		this.senderId = senderId;
		this.content = content;
		this.sender = sender;
		this.userPicName = userPicName;
		this.messageDate = messageDate;
		this.type = type;
	}
    
}
