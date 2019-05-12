package com.web.data;

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
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime imageUploadDate;
	private String imgFileName;
	private Long imageRepostCount = (long) 0;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Post imgPost;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "comment_id")
	private Comment imgComment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User imgUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chat_id")
	private Chat imgChat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grup_id")
	private Group imgGroup;
	
	@OneToMany(mappedBy = "commentedImage")
	private Set<Comment> imgComments;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "img_likes", 
				joinColumns = { @JoinColumn(name = "img_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "user_id") }
	)
	private Set<User> imgLikes = new HashSet<User>();
	
	public Image(LocalDateTime imageUploadDate, String imgFilename) {
		this.imageUploadDate = imageUploadDate;
		this.imgFileName = imgFilename;
	}

}

