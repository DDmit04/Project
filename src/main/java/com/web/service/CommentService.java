package com.web.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;
import com.web.repository.CommentRepo;
import com.web.utils.DateUtil;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private FileService fileService;

	public void addComment(Post post, String commentText, User currentUser, MultipartFile commentPic) throws IllegalStateException, IOException {
		Comment comment = new Comment(commentText, LocalDateTime.now());
		comment.setCommentedPost(post);
		comment.setCommentAuthor(currentUser);
		comment.setCommentPicName(fileService.uploadFile(commentPic, UploadType.COMMENT));
		commentRepo.save(comment);		
	}

	public Iterable<CommentDto> findCommentsByCommentedPost(Post post) {
		return commentRepo.findByCommentedPost(post);
	}

	public void deleteComment(Comment comment) {
		commentRepo.delete(comment);
	}

	public void updateComment(Comment comment, String commentText, MultipartFile commentPic) throws IllegalStateException, IOException {
		comment.setCommentText(commentText);
		comment.setCommentCreationDate(LocalDateTime.now());
		comment.setCommentPicName(fileService.uploadFile(commentPic, UploadType.COMMENT));
		commentRepo.save(comment);
	}

}
