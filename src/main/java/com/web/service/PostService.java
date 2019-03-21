package com.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;
import com.web.data.UserGroup;
import com.web.data.dto.PostDto;
import com.web.repository.PostRepo;
import com.web.utils.DateUtil;

@Service
public class PostService {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PostRepo postRepo;
	
	public void addPost(String postText, String tags, MultipartFile file, User user) throws IllegalStateException, IOException {
		postRepo.save(createPost(postText, user, tags, file));
	}
	
	public void addPost(String postText, String tags, MultipartFile file, UserGroup group) throws IllegalStateException, IOException {
		postRepo.save(createGroupPost(postText, group, tags, file));		
	}
	
	public Post createPost(String postText, User user, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, DateUtil.getLocalDate("yyyy-MM-dd HH:mm:ss"));
		post.setPostAuthor(user);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		return post;
	}
	
	public Post createGroupPost(String postText, UserGroup group, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, DateUtil.getLocalDate("yyyy-MM-dd HH:mm:ss"));
		post.setPostGroup(group);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		return post;
	}
	
	public void updatePost(Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException {
		post.setPostText(text);
		post.setTags(tags);
		post.setCreationDate(DateUtil.getLocalDate() + " (edited)");
		post.setFilename(fileService.uploadFile(file, UploadType.POST));
		postRepo.save(post);
	}

	public void deletePost(Post post, User currentUser) {
		if(postRepo.findCountByRepostAndAuthor(currentUser, post.getRepost()) == 1)  {
			decrementAndSaveRepostsCount(post.getRepost());
		}
		postRepo.delete(post);
	}
	
	public void removeRepost(Post post) {
		decrementAndSaveRepostsCount(post.getRepost());
		post.setRepost(null);
		postRepo.save(post);
	}
	
	public void addRepost(User currentUser, Post post, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post newPost = createPost(repostText, currentUser, repostTags, null);	
		newPost.setRepost(post);
		if(postRepo.findCountByRepostAndAuthor(currentUser, post) == 0) {
			incrementAndSaveRepostsCount(post);
		}
		postRepo.save(newPost);
	}
	
	public void incrementAndSaveRepostsCount(Post incrementedPost) {
		incrementedPost.setRepostsCount(incrementedPost.getRepostsCount() + 1);
		postRepo.save(incrementedPost);
	}
	
	public void decrementAndSaveRepostsCount(Post decrimentedPost) {
		decrimentedPost.setRepostsCount(decrimentedPost.getRepostsCount() - 1);
		postRepo.save(decrimentedPost);
	}
	
	public PostDto findOnePost(User currentUser, Post post) {
		return postRepo.findOne(currentUser, post.getId());
	}
	
	public Iterable<PostDto> searchPostsByTag(String search, User currentUser) {
		Iterable<PostDto> searchResult;
		if(search != null && search != "") {
			searchResult = postRepo.findByTag(currentUser, search);
		} else {
			searchResult = postRepo.findAll(currentUser);
		}		
		return searchResult;
	}
	
	public Iterable<PostDto> findPostsByUser(User currentUser, User user) {
		return postRepo.findByPostAuthor(currentUser, user);
	}

	public Iterable<PostDto> searchSubscriptionsPosts(User currentUser) {
		return postRepo.findSubscriptionsPosts(currentUser);
	}
}