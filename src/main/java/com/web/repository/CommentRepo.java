package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.Comment;
import com.web.data.Post;

public interface CommentRepo extends CrudRepository<Comment, Long> {
		
    Iterable<Comment> findByCommentedPost(Post commentedPost);
    
}
