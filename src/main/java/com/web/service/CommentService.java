package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;
import com.web.repository.CommentRepo;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private FileService fileService;

	public void addComment(Post post, String commentText, User currentUser, MultipartFile commentPic) throws IllegalStateException, IOException {
		Comment comment = new Comment(commentText, LocalDateTime.now(Clock.systemUTC()));
		comment.setCommentedPost(post);
		comment.setCommentAuthor(currentUser);
		comment.setCommentPicName(fileService.uploadFile(commentPic, UploadType.COMMENT));
		commentRepo.save(comment);		
	}

	public Iterable<CommentDto> findCommentsByCommentedPost(Post post) {
		return commentRepo.findByCommentedPost(post);
	}

	public void deleteComment(User currentUser, Post post, Comment comment) {
		if(((comment.getCommentAuthor().equals(currentUser)))
			|| (post.getPostAuthor() != null && post.getPostAuthor().equals(currentUser))
			|| (post.getPostGroup() != null && post.getPostGroup().getGroupAdmins().contains(currentUser))) {
			commentRepo.delete(comment);
		}
	}

	public void updateComment(User currentUser, Comment comment, String commentText, MultipartFile commentPic) throws IllegalStateException, IOException {
		if(comment.getCommentAuthor().equals(currentUser)) {
			comment.setCommentText(commentText);
			comment.setCommentCreationDate(LocalDateTime.now(Clock.systemUTC()));
			comment.setCommentPicName(fileService.uploadFile(commentPic, UploadType.COMMENT));
			commentRepo.save(comment);
		}
	}

}
