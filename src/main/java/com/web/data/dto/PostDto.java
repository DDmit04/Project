package com.web.data.dto;

import com.web.data.Post;
import com.web.data.User;

public class PostDto {
	
	private Long id;
	private String postText;
	private String tags;
	private String creationDate;
	private String filename;
	private User postAuthor;
	private Long likes;
	private Long commentsCount;
	private Long repostsCount;
	private boolean liked;
	private Post repost;
	
	public PostDto(Post post, Long likes, boolean liked) {
		this.id = post.getId();
		this.postText = post.getPostText();
		this.tags = post.getTags();
		this.postAuthor = post.getPostAuthor();
		this.creationDate = post.getCreationDate();
		this.filename = post.getFilename();
		this.liked = liked;
		this.commentsCount = (long) post.getPostComments().size();
		this.likes = likes;
		this.repost = post.getRepost();
		this.repostsCount = post.getRepostsCount();
	}
	public Long getRepostsCount() {
		return repostsCount;
	}
	public Long getId() {
		return id;
	}
	public String getPostText() {
		return postText;
	}
	public String getTags() {
		return tags;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getFilename() {
		return filename;
	}
	public boolean isLiked() {
		return liked;
	}
	public Long getLikes() {
		return likes;
	}
	public User getPostAuthor() {
		return postAuthor;
	}
	public Long getCommentsCount() {
		return commentsCount;
	}
	public Post getRepost() {
		return repost;
	}
}
