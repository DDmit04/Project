package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Post;
import com.web.data.User;
import com.web.data.Group;
import com.web.data.dto.PostDto;

public interface PostRepo extends CrudRepository<Post, Long> {
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   (select count(*) from p.postLikes), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "group by p")
	//uses in search
    Iterable<PostDto> findAll(@Param("currentUser") User currentUser);
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   (select count(*) from p.postLikes), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.tags = :tags " +
            "group by p")
	//uses in search
    Iterable<PostDto> findByTag(@Param("currentUser") User currentUser, @Param("tags") String tags);
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   (select count(*) from p.postLikes), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.postAuthor = :user " +
            "group by p")
    Iterable<PostDto> findByPostAuthor(@Param("currentUser") User currentUser, @Param("user") User user);
	
	@Query("select count(*) " +
		   "from Post p " +
		   "where p.repost = :post and p.postAuthor = :currentUser")
	Long findCountByRepostAndAuthor(@Param("currentUser") User currentUser, @Param("post") Post post);
	
	@Query("select count(*) " +
			   "from Post p " +
			   "where p.repost = :post and p.postGroup = :group")
	Long findCountByRepostAndGroup(@Param("group") Group group, @Param("post") Post post);
	
	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   (select count(*) from p.postLikes), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.id = :postId " +
            "group by p")
    PostDto findOnePost(@Param("currentUser") User currentUser, @Param("postId") Long postId);
	
	@Query("select new com.web.data.dto.PostDto(" +
	            "   p, " +
				"   (select count(*) from p.postLikes), " +
	            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
	            ") " +
	            "from Post p left join p.postLikes pl " +
	            "			 left join p.postAuthor.userFriends pa " +
	            " 			 left join p.postAuthor.subscribers ps " +
	            " 			 left join p.postGroup.groupSubs sg " +
	            "where pa = :currentUser or ps = :currentUser or sg = :currentUser " +
	            "group by p")
	Iterable<PostDto> findSubscriptionsPosts(@Param("currentUser") User currentUser);

	@Query("select new com.web.data.dto.PostDto(" +
            "   p, " +
			"   (select count(*) from p.postLikes), " +
            "   sum(case when pl = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from Post p left join p.postLikes pl " +
            "where p.postGroup = :group " +
            "group by p")  
	Iterable<PostDto> findGroupPosts(@Param("currentUser") User currentUser, @Param("group") Group group);
	
}