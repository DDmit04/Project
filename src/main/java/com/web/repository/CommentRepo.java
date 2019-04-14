package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Comment;
import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;

public interface CommentRepo extends CrudRepository<Comment, Long> {
		
	@Query("select new com.web.data.dto.CommentDto(" +
			"   c " +
			" )" +
			" from Comment c " +
			" where c.commentedPost = :commentedPost " +
			" group by c")
    Iterable<CommentDto> findByCommentedPost(@Param("commentedPost") Post commentedPost);
    
    @Query("from Comment c " +
    	    " where c.commentedPost.postGroup = :group " +
    	    "  	 	and c.commentAuthor = :bannedUser " +
    	    " group by c")
    Iterable<Comment> findBannedComments(@Param("group") Group group, @Param("bannedUser") User bannedUser);
    
    @Query("from Comment c " +
     	    " where c.commentedPost.postAuthor = :user " +
     	    "  	 	and c.commentAuthor = :bannedUser " +
     	    " group by c")
     Iterable<Comment> findBannedComments(@Param("user") User user, @Param("bannedUser") User bannedUser);
    
}
