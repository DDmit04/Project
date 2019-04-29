package com.web.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;

public interface CommentService {
	
	void createComment(User user, Comment comment, Post post, MultipartFile commentPic) throws IllegalStateException, IOException;
	
	void deleteComment(User user, Post post, Comment comment);

	void editComment(User user, Comment comment, String commentText, MultipartFile commentPic) throws IllegalStateException, IOException; 
	
	
	

	public Iterable<CommentDto> getCommentsByCommentedPost(Post post);
}
