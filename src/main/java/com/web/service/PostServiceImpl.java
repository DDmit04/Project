package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.FileService;
import com.web.api.ImageService;
import com.web.api.post.PostService;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.repository.GroupRepo;
import com.web.repository.ImageRepo;
import com.web.repository.PostRepo;

@Service
public class PostServiceImpl implements PostService {
	
	private FileService fileService;
	private PostRepo postRepo;
	private GroupRepo groupRepo;
	private ImageService imageService;
	
	@Autowired
	public PostServiceImpl(FileServiceImpl fileService, ImageServiceImpl imageService, 
				PostRepo postRepo, GroupRepo groupRepo, ImageRepo imageRepo) {
		this.fileService = fileService;
		this.postRepo = postRepo;
		this.groupRepo = groupRepo;
		this.imageService = imageService;
	}

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
	
	@Override
	public Post createPost(User user, Post post, MultipartFile file) throws IllegalStateException, IOException {
		post.setPostAuthor(user);
		post.setPostCreationDate(LocalDateTime.now(Clock.systemUTC()));
		postRepo.save(post);
		if(file != null && !file.isEmpty()) {
			String filename = fileService.uploadFile(user, file, UploadType.USER_PIC);
			Image image = imageService.createImage(user, filename, file);
			post.setPostImage(image);
			postRepo.save(post);
		}
		return post;
	}
	
	@Override
	public Post createPost(Group group, Post post, MultipartFile file) throws IllegalStateException, IOException {
		post.setPostGroup(group);
		post.setPostCreationDate(LocalDateTime.now(Clock.systemUTC()));
		postRepo.save(post);
		if(file != null && !file.isEmpty()) {
			String filename = fileService.uploadFile(group, file, UploadType.GROUP_PIC);
			Image image = imageService.createImage(group, filename, file);
			post.setPostImage(image);
			postRepo.save(post);
		}
		return post;
	}
	
	@Override
	public Post updatePost(User currentUser, Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			post.setPostText(text);
			post.setTags(tags);
			post.setPostCreationDate(LocalDateTime.now(Clock.systemUTC()));
			postRepo.save(post);
			if(!file.isEmpty()) {
				String filename = fileService.uploadFile(currentUser, file, UploadType.USER_PIC);
				Image image = imageService.createImage(currentUser, filename, file);
				post.setPostImage(image);
				postRepo.save(post);
			}
		}
		return post;
	}

	@Override
	public void deletePost(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			if((postRepo.findCountByRepostAndAuthor(currentUser, post.getRepost())) == 1)  {
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
	public void removeImage(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			post.setPostImage(null);
			postRepo.save(post);
		}
	}
	
	@Override
	public Post addRepost(User currentUser, Post repostedPost, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post post = new Post(repostText, repostTags, LocalDateTime.now(Clock.systemUTC()));
		Post newPost = createPost(currentUser, post, null);	
		newPost.setRepost(repostedPost);
		if((postRepo.findCountByRepostAndAuthor(currentUser, post)) == 0) {
			incrementAndSaveRepostsCount(repostedPost);
		}
		postRepo.save(newPost);
		return newPost;
	}
	
	@Override
	public Post addGroupRepost(Long groupId, Post repostedPost, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post post = new Post(repostText, repostTags, LocalDateTime.now(Clock.systemUTC()));
		Group group = groupRepo.findByGroupId(groupId);
		Post newPost = createPost(group, post, null);	
		newPost.setRepost(repostedPost);
		if((postRepo.findCountByRepostAndGroup(group, post)) == 0) {
			incrementAndSaveRepostsCount(repostedPost);
		}
		postRepo.save(newPost);
		return newPost;
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