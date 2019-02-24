package com.web.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;
import com.web.repository.PostRepo;

@Service
public class PostService {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PostRepo postRepo;
	
	public void addPost(Post post, String text, String tags, MultipartFile file, User user) throws IllegalStateException, IOException {
		post.setPostAthor(user);
		post.setPostText(text);
		post.setTags(tags);
		post.setCreationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		post.setFilename(fileService.uploadFile(file,UploadType.POST));
		postRepo.save(post);
	}
	
	public void updatePost(Post post, String text, String tags, MultipartFile file) throws IllegalStateException, IOException {
		post.setPostText(text);
		post.setTags(tags);
		post.setCreationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		post.setFilename(fileService.uploadFile(file, UploadType.POST));
		postRepo.save(post);
	}

	public void deletePost(Post post) {
		postRepo.delete(post);
	}

	public Iterable<Post> searchPostsByTag(String search) {
		Iterable<Post> searchResult;
		if(search != null && search != "") {
			searchResult = postRepo.findByTags(search);
		} else {
			searchResult = postRepo.findAll();
		}		
		return searchResult;
	}

}
