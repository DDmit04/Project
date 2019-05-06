package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.Comment;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;

import lombok.Getter;

@Getter
public class CommentDto {
	
	private Long id;
	private String commentText;
	private LocalDateTime commentCreationDate;
	private Post commentedPost;
	private User commentAuthor;
	private Image commentedImage;
	private Image commentImage;
	
	public CommentDto(Comment comment) {
		this.id = comment.getId();
		this.commentText = comment.getCommentText();
		this.commentCreationDate = comment.getCommentCreationDate();
		this.commentedPost = comment.getCommentedPost();
		this.commentAuthor = comment.getCommentAuthor();
		this.commentedImage = comment.getCommentedImage();
		this.commentImage = comment.getCommentImage();
	}
}
