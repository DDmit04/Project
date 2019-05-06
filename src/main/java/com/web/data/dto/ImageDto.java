package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.User;

import lombok.Getter;

@Getter
public class ImageDto {
	
	private Long id;
	private Long likes;
	private Long commentsCount;
	private LocalDateTime imgUploadDate;
	private String imgFileName;
	private boolean liked;
	private User imgUser;
	private Group imgGroup;
	private Chat imgChat;
	
	public ImageDto(Image image, Long likes, boolean liked) {
		this.id = image.getId();
		this.imgUploadDate = image.getImageUploadDate();
		this.imgFileName = image.getImgFileName();
		this.imgUser = image.getImgUser();
		this.imgGroup = image.getImgGroup();
		this.imgChat = image.getImgChat();
		this.commentsCount = (long) image.getImgComments().size();
		this.liked = liked;
		this.likes = likes;
	}
	
}
