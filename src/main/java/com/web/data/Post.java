package com.web.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User postAuthor;
	
	@OneToMany(mappedBy = "commentedPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<Comment> postComments;
	
	public Post() {
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
	
}
