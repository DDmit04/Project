package com.web.api.post;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;

public interface BasePostService {
	
	Post createPost(User user, Post post, MultipartFile file) throws IllegalStateException, IOException;
	
	Post createPost(Group group, Post post, MultipartFile file) throws IllegalStateException, IOException;
	
	void deletePost(User user, Post post);
	
	void updatePost(User user, Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException;
	
	void like(User currentUser, User user, Post post);


}
