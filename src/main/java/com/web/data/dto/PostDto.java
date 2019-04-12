package com.web.data.dto;

import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;

import lombok.Getter;

@Getter
public class PostDto {
	
	private Long id;
	private String postText;
	private String tags;
	private String creationDate;
	private String filename;
	private User postAuthor;
	private Group postGroup;
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
		this.postGroup = post.getPostGroup();
	}
}
