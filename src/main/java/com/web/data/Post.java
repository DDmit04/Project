package com.web.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User postAuthor;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "post_repost", 
	    joinColumns = @JoinColumn(name = "repostedPost_id"), 
	    inverseJoinColumns = @JoinColumn(name = "repost_id")
	)
	private Set<Post> reposts = new HashSet<Post>();

	@OneToMany(mappedBy = "commentedPost")
	private Set<Comment> postComments;

	@ManyToMany
	@JoinTable(name = "post_likes", 
				joinColumns = { @JoinColumn(name = "post_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "user_id") }
	)
	private Set<User> postLikes = new HashSet<User>();

	public Post() {
	}
	public Set<Post> getReposts() {
		return reposts;
	}
	public void setReposts(Set<Post> reposts) {
		this.reposts = reposts;
	}
	public Post(String postText, String tags, String creationDate) {
		this.postText = postText;
		this.tags = tags;
		this.creationDate = creationDate;
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
}
