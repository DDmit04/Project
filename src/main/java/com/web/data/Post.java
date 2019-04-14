package com.web.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String postText;
	private String tags;
	private LocalDateTime PostCreationDate;
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
	private Group postGroup;

	public Post(String postText, String tags, LocalDateTime PostCreationDate) {
		this.postText = postText;
		this.tags = tags;
		this.PostCreationDate = PostCreationDate;
	}	
}
