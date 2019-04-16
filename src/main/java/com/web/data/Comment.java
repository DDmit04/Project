package com.web.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String commentText;
	private LocalDateTime commentCreationDate;
	private String commentPicName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Post commentedPost;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User commentAuthor;
	
	public Comment(String commentText, LocalDateTime commentCreationDate) {
		this.commentText = commentText;
		this.commentCreationDate = commentCreationDate;
	}
}
