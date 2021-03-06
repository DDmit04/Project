package com.web.api.post;

import java.io.IOException;

import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;

public interface PostService extends BasePostService {
	
	void removeRepost(User user, Post post);
	
	Post addRepost(User user, Post post, String repostText, String repostTags) throws IllegalStateException, IOException;
	
	Post addGroupRepost(Long groupId, Post repostedPost, String repostText, String repostTags) throws IllegalStateException, IOException;
	
	void removeImage(User currentUser, Post post);
	
	
	PostDto getOnePost(User currentUser, Post post);
	
	Iterable<PostDto> getUserPosts(User currentUser, User user);
	
	Iterable<PostDto> getAllPosts(User currentUser);

	Iterable<PostDto> getGroupPosts(User currentUser, Group group);

}
