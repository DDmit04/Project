package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.CommentService;
import com.web.api.FileService;
import com.web.api.ImageService;
import com.web.data.Comment;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;
import com.web.repository.CommentRepo;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentRepo commentRepo;
	private FileService fileService;
	private ImageService imageService;
	
	@Autowired
	public CommentServiceImpl(CommentRepo commentRepo, FileServiceImpl fileService, ImageServiceImpl imageService) {
		this.commentRepo = commentRepo;
		this.fileService = fileService;
		this.imageService = imageService;
	}

	@Override
	public Comment createComment(User currentUser, Comment comment, Post post, MultipartFile commentPic) throws IllegalStateException, IOException {
		comment.setCommentCreationDate(LocalDateTime.now(Clock.systemUTC()));
		comment.setCommentedPost(post);
		comment.setCommentAuthor(currentUser);
		commentRepo.save(comment);		
		if(!commentPic.isEmpty()) {
			String filename = fileService.uploadFile(currentUser, commentPic, UploadType.USER_PIC);
			Image commentImage = imageService.createImage(currentUser, filename, commentPic);
			comment.setCommentImage(commentImage);
			commentRepo.save(comment);	
		}
		return comment;
	}
	
	@Override
	public Comment createComment(User currentUser, Comment comment, Image image, MultipartFile commentPic) throws IllegalStateException, IOException {
		comment.setCommentCreationDate(LocalDateTime.now(Clock.systemUTC()));
		comment.setCommentedImage(image);
		comment.setCommentAuthor(currentUser);
		commentRepo.save(comment);	
		if(!commentPic.isEmpty()) {
			String filename = fileService.uploadFile(currentUser, commentPic, UploadType.USER_PIC);
			Image commentImage = imageService.createImage(currentUser, filename, commentPic);
			comment.setCommentImage(commentImage);
			commentRepo.save(comment);
		}
		return comment;
	}
	
	@Override
	public void deleteComment(User currentUser, Image image, Comment comment) {
		if(((comment.getCommentAuthor().equals(currentUser)))
			|| (image.getImgUser() != null && image.getImgUser().equals(currentUser))
			|| (image.getImgGroup() != null && image.getImgGroup().getGroupAdmins().contains(currentUser))) {
			commentRepo.delete(comment);
		}
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
			commentRepo.save(comment);
			if(!commentPic.isEmpty()) {
				String filename = fileService.uploadFile(currentUser, commentPic, UploadType.USER_PIC);
				Image commentImage = imageService.createImage(currentUser, filename, commentPic);
				comment.setCommentImage(commentImage);
				commentRepo.save(comment);
			}
		}
	}

	@Override
	public Iterable<CommentDto> getCommentsByCommentedPost(Post post) {
		return commentRepo.findByCommentedPost(post);
	}

}
