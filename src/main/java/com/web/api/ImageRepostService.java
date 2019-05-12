package com.web.api;

import java.io.IOException;

import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;

public interface ImageRepostService {

	void removeRepostImage(User currentUser, Post post);

	Post addRepostImage(User currentUser, Image repostedImage, String repostText, String repostTags)
			throws IllegalStateException, IOException;

	Post addGroupRepostImage(Long groupId, Image repostedImage, String repostText, String repostTags)
			throws IllegalStateException, IOException;

}