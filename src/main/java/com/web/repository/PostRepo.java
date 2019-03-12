package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;

public interface PostRepo extends CrudRepository<Post, Long> {
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "group by p")
    Iterable<PostDto> findAll(@Param("user") User user);
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   count(pl), " +
            "   sum(case when pl = :user then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.tags = :tags " +
            "group by p")
    Iterable<PostDto> findByTag(@Param("user") User user, @Param("tags") String tags);
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   count(pl), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.postAuthor = :user " +
            "group by p")
    Iterable<PostDto> findByPostAuthor(@Param("currentUser") User currentUser, @Param("user") User user);
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   count(pl), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.id = :postId " +
            "group by p")
    Iterable<PostDto> findOne(@Param("currentUser") User currentUser, @Param("postId") Long postId);
	
	  @Query("select new com.web.data.dto.PostDto(" +
	            "   p, " +
				"   count(pl), " +
	            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
	            ") " +
	            "from Post p left join p.postLikes pl " +
	            "			 left join p.postAuthor.userFriends pa " +
	            " 			 left join p.postAuthor.subscribers ps " +
	            "where pa = :currentUser or ps = :currentUser " +
	            "group by p")
	  Iterable<PostDto> findSubscriptionsPosts(@Param("currentUser") User currentUser);
	
}