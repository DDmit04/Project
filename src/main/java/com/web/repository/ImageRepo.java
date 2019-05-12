package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.ImageDto;

public interface ImageRepo extends CrudRepository<Image, Long> {

	@Query("select new com.web.data.dto.ImageDto(" +
			   "  i, " +
			   "  (select count(*) from i.imgLikes)," +
			   "  sum(case when il = :currentUser then 1 else 0 end) > 0 " +
			   ") " +
			   " from Image i left join i.imgLikes il " +
			   " where i.imgUser = :user" +
			   " group by i")
	Iterable<ImageDto> findByImgOwner(@Param("currentUser") User currentUser, @Param("user") User user);

	@Query("select new com.web.data.dto.ImageDto(" +
			   "  i, " +
			   "  (select count(*) from i.imgLikes)," +
			   "  sum(case when il = :currentUser then 1 else 0 end) > 0 " +
			   ") " +
			   " from Image i left join i.imgLikes il " +
			   " where i.imgGroup = :group " +
			   " group by i")	
	Iterable<ImageDto> findByImgGroup(@Param("currentUser") User currentUser, @Param("group") Group group);

	@Query("select new com.web.data.dto.ImageDto(" +
			   "  i, " +
			   "  (select count(*) from i.imgLikes)," +
			   "  sum(case when il = :currentUser then 1 else 0 end) > 0 " +
			   ") " +
			   " from Image i left join i.imgLikes il " +
			   " where i.imgChat = :chat " +
			   " group by i")	
	Iterable<ImageDto> findByImgChat(@Param("currentUser") User currentUser, @Param("chat") Chat chat);

	@Query("select new com.web.data.dto.ImageDto(" +
		   "  i, " +
		   "  (select count(*) from i.imgLikes)," +
		   "  sum(case when il = :currentUser then 1 else 0 end) > 0 " +
		   ") " +
		   " from Image i left join i.imgLikes il " +
		   " where i.id = :imageId " +
		   " group by i")
	ImageDto getOneImage(@Param("currentUser") User currentUser, @Param("imageId") Long imageId);
	
	@Query("select count(*) " +
		   "	from Post p " +
		   "	where p.repostImage = :image and p.postAuthor = :currentUser")
	Long findCountByRepostImageAndAuthor(@Param("currentUser") User currentUser, @Param("image") Image image);
		
	@Query("select count(*) " +
		   "	from Post p " +
		   "	where p.repostImage = :image and p.postGroup = :group")
	Long findCountByRepostImageAndGroup(@Param("group") Group group, @Param("image") Image image);

}
