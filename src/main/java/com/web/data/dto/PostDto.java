package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.Group;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;

import lombok.Getter;

@Getter
public class PostDto extends SearchResultsGeneric{
	
	private Long id;
	private Long likes;
	private Long commentsCount;
	private Long repostsCount;
	private String postText;
	private String tags;
	private LocalDateTime postCreationDate;
	private boolean liked;
	private Post repost;
	private User postAuthor;
	private Group postGroup;
	private Image postImage;
	private Image repostImage;
	
	public PostDto(Post post, Long likes, boolean liked) {
		this.id = post.getId();
		this.postText = post.getPostText();
		this.tags = post.getTags();
		this.postAuthor = post.getPostAuthor();
		this.postCreationDate = post.getPostCreationDate();
		this.commentsCount = (long) post.getPostComments().size();
		this.repost = post.getRepost();
		this.repostsCount = post.getRepostsCount();
		this.postGroup = post.getPostGroup();
		this.postImage = post.getPostImage();
		this.repostImage = post.getRepostImage();
		this.likes = likes;
		this.liked = liked;
	}
}
