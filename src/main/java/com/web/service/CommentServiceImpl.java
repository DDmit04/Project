package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.CommentService;
import com.web.api.FileService;
import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;
import com.web.repository.CommentRepo;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentRepo commentRepo;
	private FileService fileService;
	
	@Autowired
	public CommentServiceImpl(CommentRepo commentRepo, FileServiceImpl fileService) {
		this.commentRepo = commentRepo;
		this.fileService = fileService;
	}

	@Override
	public void createComment(User currentUser, Comment comment, Post post, MultipartFile commentPic) throws IllegalStateException, IOException {
		comment.setCommentCreationDate(LocalDateTime.now(Clock.systemUTC()));
		comment.setCommentedPost(post);
		comment.setCommentAuthor(currentUser);
		comment.setCommentPicName(fileService.uploadFile(commentPic, UploadType.COMMENT));
		commentRepo.save(comment);		
	}
	
	@Override
	public void deleteComment(User currentUser, Post post, Comment comment) {
		if(((comment.getCommentAuthor().equals(currentUser)))
			|| (post.getPostAuthor() != null && post.getPostAuthor().equals(currentUser))
			|| (post.getPostGroup() != null && post.getPostGroup().getGroupAdmins().contains(currentUser))) {
			commentRepo.delete(comment);
		}
	}

	@Override
	public void editComment(User currentUser, Comment comment, String commentText, MultipartFile commentPic) throws IllegalStateException, IOException {
		if(comment.getCommentAuthor().equals(currentUser)) {
			comment.setCommentText(commentText);
			comment.setCommentCreationDate(LocalDateTime.now(Clock.systemUTC()));
			comment.setCommentPicName(fileService.uploadFile(commentPic, UploadType.COMMENT));
			commentRepo.save(comment);
		}
	}

	@Override
	public Iterable<CommentDto> getCommentsByCommentedPost(Post post) {
		return commentRepo.findByCommentedPost(post);
	}

}
