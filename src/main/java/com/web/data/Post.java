package com.web.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String postText;
//	@ElementCollection(targetClass=String.class)
	private String tags;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private String creationDate;
	private String filename;
	private Long repostsCount = (long) 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User postAuthor;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post repost;

	@OneToMany(mappedBy = "commentedPost")
	private Set<Comment> postComments;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "post_likes", 
				joinColumns = { @JoinColumn(name = "post_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "user_id") }
	)
	private Set<User> postLikes = new HashSet<User>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grup_id")
	private UserGroup postGroup;

	public Post() {
	}
	public Post(String postText, String tags, String creationDate) {
		this.postText = postText;
		this.tags = tags;
		this.creationDate = creationDate;
	}
	
	public UserGroup getPostGroup() {
		return postGroup;
	}
	public void setPostGroup(UserGroup postGroup) {
		this.postGroup = postGroup;
	}
	public Post getRepost() {
		return repost;
	}
	public void setRepost(Post repost) {
		this.repost = repost;
	}
	public int getCommentsCount() {
		return postComments.size();
	}
	public String getAuthorName() {
		return postAuthor.getUsername();
	}
	public User getPostAuthor() {
		return postAuthor;
	}
	public void setPostAuthor(User postAthor) {
		this.postAuthor = postAthor;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Long getId() {
		return id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Set<Comment> getPostComments() {
		return postComments;
	}
	public void setPostComments(Set<Comment> postComments) {
		this.postComments = postComments;
	}
	public Set<User> getPostLikes() {
		return postLikes;
	}
	public void setPostLikes(Set<User> postLikes) {
		this.postLikes = postLikes;
	}
	public Long getRepostsCount() {
		return repostsCount;
	}
	public void setRepostsCount(Long repostsCount) {
		this.repostsCount = repostsCount;
	}
}
