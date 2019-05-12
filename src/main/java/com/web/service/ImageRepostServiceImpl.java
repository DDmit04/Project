package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.FileService;
import com.web.api.ImageRepostService;
import com.web.api.ImageService;
import com.web.api.post.PostService;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;
import com.web.repository.GroupRepo;
import com.web.repository.ImageRepo;
import com.web.repository.PostRepo;

@Service
public class ImageRepostServiceImpl implements ImageRepostService {
	
	private PostRepo postRepo;
	private GroupRepo groupRepo;
	private ImageRepo imageRepo;
	private PostService postService;
	
	@Autowired
	public ImageRepostServiceImpl(FileServiceImpl fileService, ImageServiceImpl imageService, PostServiceImpl postService,
				PostRepo postRepo, GroupRepo groupRepo, ImageRepo imageRepo) {
		this.postRepo = postRepo;
		this.groupRepo = groupRepo;
		this.imageRepo = imageRepo;
		this.postService = postService;
	}
	
	private boolean userIsAuthorOrAdmin(User currentUser, Post post) {
		return post.getPostAuthor() != null && post.getPostAuthor().equals(currentUser)
			   || (post.getPostGroup() != null && post.getPostGroup().getGroupAdmins().contains(currentUser));
	}
	
	private void incrementAndSaveImageRepostsCount(Image incrementedImage) {
		incrementedImage.setImageRepostCount(incrementedImage.getImageRepostCount() + 1);
		imageRepo.save(incrementedImage);
	}
	
	private void decrementAndSaveImageRepostsCount(Image decrementedImage) {
		decrementedImage.setImageRepostCount(decrementedImage.getImageRepostCount() - 1);
		imageRepo.save(decrementedImage);
	}
	
	@Override
	public void removeRepostImage(User currentUser, Post post) {
		if(userIsAuthorOrAdmin(currentUser, post)) {
			decrementAndSaveImageRepostsCount(post.getRepostImage());
			post.setRepostImage(null);
			postRepo.save(post);
		}
	}
	
	@Override
	public Post addRepostImage(User currentUser, Image repostedImage, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post post = new Post(repostText, repostTags, LocalDateTime.now(Clock.systemUTC()));
		Post newPost = postService.createPost(currentUser, post, null);	
		newPost.setRepostImage(repostedImage);
		if((imageRepo.findCountByRepostImageAndAuthor(currentUser, repostedImage)) == 0) {
			incrementAndSaveImageRepostsCount(repostedImage);
		}
		postRepo.save(newPost);
		return newPost;
	}
	
	@Override
	public Post addGroupRepostImage(Long groupId, Image repostedImage, String repostText, String repostTags) throws IllegalStateException, IOException {
		Post post = new Post(repostText, repostTags, LocalDateTime.now(Clock.systemUTC()));
		Group group = groupRepo.findByGroupId(groupId);
		Post newPost = postService.createPost(group, post, null);	
		newPost.setRepostImage(repostedImage);
		if((imageRepo.findCountByRepostImageAndGroup(group, repostedImage)) == 0) {
			incrementAndSaveImageRepostsCount(repostedImage);
		}
		postRepo.save(newPost);
		return newPost;
	}

}
