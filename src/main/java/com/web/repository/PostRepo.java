package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.Post;
import com.web.data.User;

public interface PostRepo extends CrudRepository<Post, Long> {
	
	Iterable<Post> findByTags(String tags);
	Iterable<Post> findByPostAuthor(User user);
	
}
