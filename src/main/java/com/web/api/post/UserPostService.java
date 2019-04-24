package com.web.api.post;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;

public interface UserPostService extends PostService {
	
	void addUserPost(String postText, String tags, MultipartFile file, User user) throws IllegalStateException, IOException;
	
}
