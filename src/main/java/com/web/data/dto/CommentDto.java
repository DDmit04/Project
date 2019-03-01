package com.web.data.dto;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;

public class CommentDto {
	
	private Long id;
	private String commentText;
	private String creationDate;
	private String commentPicName;
	private Post commentedPost;
	private User commentAuthor;
	
	public CommentDto(Comment comment) {
		this.id = comment.getId();
		this.commentText = comment.getCommentText();
		this.creationDate = comment.getCreationDate();
		this.commentPicName = comment.getCommentPicName();
		this.commentedPost = comment.getCommentedPost();
		this.commentAuthor = comment.getCommentAuthor();
	}
	public Long getId() {
		return id;
	}
	public String getCommentText() {
		return commentText;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getCommentPicName() {
		return commentPicName;
	}
	public Post getCommentedPost() {
		return commentedPost;
	}
	public User getCommentAuthor() {
		return commentAuthor;
	}
}
