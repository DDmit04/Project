package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.post.FullPostService;
import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.repository.PostRepo;

@Service
public class PostServiceImpl implements FullPostService {
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Autowired
	private PostRepo postRepo;
	
	private boolean userIsAuthorOrAdmin(User currentUser, Post post) {
		return post.getPostAuthor() != null && post.getPostAuthor().equals(currentUser)
			   || (post.getPostGroup() != null && post.getPostGroup().getGroupAdmins().contains(currentUser));
	}
	
	private void incrementAndSaveRepostsCount(Post incrementedPost) {
		incrementedPost.setRepostsCount(incrementedPost.getRepostsCount() + 1);
		postRepo.save(incrementedPost);
	}
	
	private void decrementAndSaveRepostsCount(Post decrimentedPost) {
		decrimentedPost.setRepostsCount(decrimentedPost.getRepostsCount() - 1);
		postRepo.save(decrimentedPost);
	}
	
	private Post createPost(String postText, User user, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, LocalDateTime.now(Clock.systemUTC()));
		post.setPostAuthor(user);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		return post;
	}
	
	private Post createGroupPost(String postText, Group group, String tags, MultipartFile file) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, LocalDateTime.now(Clock.systemUTC()));
		post.setPostGroup(group);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		return post;
	}
	
	@Override
	public void addUserPost(String postText, String tags, MultipartFile file, User user) throws IllegalStateException, IOException {
		postRepo.save(createPost(postText, user, tags, file));
	}
	
	@Override
	public void addGroupPost(String postText, String tags, MultipartFile file, Group group) throws IllegalStateException, IOException {
		postRepo.save(createGroupPost(postText, group, tags, file));		
	}
	
	@Override
	public void updatePost(User currentUser, Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			post.setPostText(text);
			post.setTags(tags);
			post.setPostCreationDate(LocalDateTime.now(Clock.systemUTC()));
			post.setFilename(fileService.uploadFile(file, UploadType.POST));
			postRepo.save(post);
		}
	}

	@Override
	public void deletePost(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			if(postRepo.findCountByRepostAndAuthor(currentUser, post.getRepost()) == 1)  {
				decrementAndSaveRepostsCount(post.getRepost());
			}
			postRepo.delete(post);
		}
	}
	
	@Override
	public void removeRepost(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			decrementAndSaveRepostsCount(post.getRepost());
			post.setRepost(null);
			postRepo.save(post);
		}
	}
	
	@Override
	public void addRepost(User currentUser, Post post, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post newPost = createPost(repostText, currentUser, repostTags, null);	
		newPost.setRepost(post);
		if(postRepo.findCountByRepostAndAuthor(currentUser, post) == 0) {
			incrementAndSaveRepostsCount(post);
		}
		postRepo.save(newPost);
	}
	
	@Override
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
	
	@Override
	public PostDto getOnePost(User currentUser, Post post) {
		return postRepo.findOnePost(currentUser, post.getId());
	}
	
	@Override
	public Iterable<PostDto> getUserPosts(User currentUser, User user) {
		return postRepo.findByPostAuthor(currentUser, user);
	}
	
	@Override
	public Iterable<PostDto> getAllPosts(User currentUser) {
		return postRepo.findAll(currentUser);
	}

	@Override
	public Iterable<PostDto> getGroupPosts(User currentUser, Group group) {
		return postRepo.findGroupPosts(currentUser, group);
	}
}