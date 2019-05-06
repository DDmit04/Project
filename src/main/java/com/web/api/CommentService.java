package com.web.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Comment;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;

public interface CommentService {
	
	Comment createComment(User user, Comment comment, Post post, MultipartFile commentPic) throws IllegalStateException, IOException;
	
	void deleteComment(User user, Post post, Comment comment);

	void editComment(User user, Comment comment, String commentText, MultipartFile commentPic) throws IllegalStateException, IOException; 
	
	Comment createComment(User currentUser, Comment comment, Image image, MultipartFile commentPic) throws IllegalStateException, IOException;

	void deleteComment(User currentUser, Image image, Comment comment);

	

	public Iterable<CommentDto> getCommentsByCommentedPost(Post post);


}
