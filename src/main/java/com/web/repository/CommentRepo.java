package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.dto.CommentDto;

public interface CommentRepo extends CrudRepository<Comment, Long> {
		
	@Query("select new com.web.data.dto.CommentDto(" +
            "   c  " +
            ") " +
            "from Comment c " +
            "where c.commentedPost = :commentedPost " +
            "group by c")
    Iterable<CommentDto> findByCommentedPost(@Param("commentedPost") Post commentedPost);

}
