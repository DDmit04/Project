package com.web.data;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
public class MessageJson {

	private Long senderId;
    private String content;
    private String sender;
    private String userPicName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getUserPicName() {
		return userPicName;
	}

	public void setUserPicName(String userPicName) {
		this.userPicName = userPicName;
	}
}
