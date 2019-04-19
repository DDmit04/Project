package com.web.data.dto;

import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.utils.DateUtil;

import lombok.Getter;

@Getter
public class PostDto extends SearchResultsGeneric{
	
	private Long id;
	private Long likes;
	private Long commentsCount;
	private Long repostsCount;
	private String postText;
	private String tags;
	private String postCreationDate;
	private String filename;
	private boolean liked;
	private Post repost;
	private User postAuthor;
	private Group postGroup;
	
	public PostDto(Post post, Long likes, boolean liked) {
		this.id = post.getId();
		this.postText = post.getPostText();
		this.tags = post.getTags();
		this.postAuthor = post.getPostAuthor();
		this.postCreationDate = DateUtil.formatDate(post.getPostCreationDate());
		this.filename = post.getFilename();
		this.liked = liked;
		this.commentsCount = (long) post.getPostComments().size();
		this.likes = likes;
		this.repost = post.getRepost();
		this.repostsCount = post.getRepostsCount();
		this.postGroup = post.getPostGroup();
	}
}
