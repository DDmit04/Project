package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.Post;

public interface PostRepo extends CrudRepository<Post, Long> {
	
	Iterable<Post> findByTags(String tags);
	
}
