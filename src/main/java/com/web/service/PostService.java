package com.web.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.repository.PostRepo;

@Service
public class PostService {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PostRepo postRepo;
	
	public void addPost(String postText, String tags, MultipartFile file, User user) throws IllegalStateException, IOException {
		postRepo.save(createPost(postText, user, tags, file));
	}
	
	public void addPost(String postText, String tags, MultipartFile file, Group group) throws IllegalStateException, IOException {
		postRepo.save(createGroupPost(postText, group, tags, file));		
	}
	
	public Post createPost(String postText, User user, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, LocalDateTime.now());
		post.setPostAuthor(user);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		return post;
	}
	
	public Post createGroupPost(String postText, Group group, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, LocalDateTime.now());
		post.setPostGroup(group);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		return post;
	}
	
	public void updatePost(User currentUser, Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			post.setPostText(text);
			post.setTags(tags);
			post.setPostCreationDate(LocalDateTime.now());
			post.setFilename(fileService.uploadFile(file, UploadType.POST));
			postRepo.save(post);
		}
	}

	public void deletePost(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			if(postRepo.findCountByRepostAndAuthor(currentUser, post.getRepost()) == 1)  {
				decrementAndSaveRepostsCount(post.getRepost());
			}
			postRepo.delete(post);
		}
	}
	
	public void removeRepost(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			decrementAndSaveRepostsCount(post.getRepost());
			post.setRepost(null);
			postRepo.save(post);
		}
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
	
	public void like(User currentUser, User user, Post post) {
		if(user.equals(currentUser)) {
			Set<User> like = post.getPostLikes();
			if(like.contains(user)) {
				like.remove(user);
			} else {
				like.add(user);
			}
		}
	}
	
	public PostDto findOnePost(User currentUser, Post post) {
		return postRepo.findOnePost(currentUser, post.getId());
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
	
	public boolean userIsAuthorOrAdmin(User currentUser, Post post) {
		return post.getPostAuthor() != null && post.getPostAuthor().equals(currentUser)
			   || (post.getPostGroup() != null && post.getPostGroup().getGroupAdmins().contains(currentUser));
	}
	
	public Iterable<PostDto> findPostsByUser(User currentUser, User user) {
		return postRepo.findByPostAuthor(currentUser, user);
	}

	public Iterable<PostDto> searchSubscriptionsPosts(User currentUser) {
		return postRepo.findSubscriptionsPosts(currentUser);
	}

	public Iterable<PostDto> findGroupPosts(User currentUser, Group group) {
		return postRepo.findGroupPosts(currentUser, group);
	}
}