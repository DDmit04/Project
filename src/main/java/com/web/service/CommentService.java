package com.web.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.repository.CommentRepo;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;

	public void addComment(Post post, String commentText, User currentUser) {
		Comment comment = new Comment(commentText, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		comment.setCommentedPost(post);
		comment.setCommentAuthor(currentUser);
		commentRepo.save(comment);		
	}

	public Iterable<Comment> findCommentsByCommentedPost(Post post) {
		return commentRepo.findByCommentedPost(post);
	}

}
