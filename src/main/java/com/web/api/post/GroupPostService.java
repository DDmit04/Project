package com.web.api.post;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Group;

public interface GroupPostService extends PostService {
	
	void addGroupPost(String postText, String tags, MultipartFile file, Group group) throws IllegalStateException, IOException;

}
