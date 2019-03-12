package com.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;
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
		postRepo.save(createNewPost(postText, user, tags, file));
	}
	
	public void addRepost(User currentUser, Post post, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post newPost = createNewPost(repostText, currentUser, repostTags, null);	
		newPost.setRepost(post);
		incrementAndSaveRepostsCount(post);
		postRepo.save(newPost);
	}
	
	public Post createNewPost(String postText, User user, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, DateUtil.getLocalDate("yyyy-MM-dd HH:mm:ss"));
		post.setPostAuthor(user);
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

	public void deletePost(Post post) {
		if(post.getRepost() != null) {
			decrementAndSaveRepostsCount(post.getRepost());
		}
		postRepo.delete(post);
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
	
	public PostDto findOnePost(User currentUser, Post post) {
		Iterable<PostDto> findPost = postRepo.findOne(currentUser, post.getId());
		PostDto commentedPost = findPost.iterator().next();
		return 	commentedPost;
	}
	
	public void incrementAndSaveRepostsCount(Post incrementedPost) {
		incrementedPost.setRepostsCount(incrementedPost.getRepostsCount() + 1);
		postRepo.save(incrementedPost);
	}
	
	public void decrementAndSaveRepostsCount(Post decrimentedPost) {
		decrimentedPost.setRepostsCount(decrimentedPost.getRepostsCount() - 1);
		postRepo.save(decrimentedPost);
	}

	public Iterable<PostDto> searchFriendPosts(User currentUser) {
		return postRepo.findSubscriptionsPosts(currentUser);
	}
}