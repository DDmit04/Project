package com.web.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.repository.PostRepo;
import com.web.repository.UserRepo;

@Service
public class PostService {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PostRepo postRepo;
	
	public void addPost(String postText, String tags, MultipartFile file, User user) throws IllegalStateException, IOException {
		Post post = new Post(postText, tags, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		post.setPostAuthor(user);
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		postRepo.save(post);
	}
	
	public void updatePost(Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException {
		post.setPostText(text);
		post.setTags(tags);
		post.setCreationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " (edited)");
		post.setFilename(fileService.uploadFile(file, UploadType.POST));
		postRepo.save(post);
	}

	public void deletePost(Post post) {
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
}