package com.web.data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String commentText;
	private String creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Post commentedPost;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User commentAuthor;
	
	public Comment(String commentText, String creationDate) {
		this.commentText = commentText;
		this.creationDate = creationDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public Post getCommentedPost() {
		return commentedPost;
	}
	public void setCommentedPost(Post commentePost) {
		this.commentedPost = commentePost;
	}
	public User getCommentAuthor() {
		return commentAuthor;
	}
	public void setCommentAuthor(User commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
}
