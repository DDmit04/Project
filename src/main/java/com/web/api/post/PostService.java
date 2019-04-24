package com.web.api.post;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;

public interface PostService {
	
	void deletePost(User user, Post post);
	
	void updatePost(User user, Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException;
	
	void like(User currentUser, User user, Post post);


}
