package com.web.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.ImageService;
import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.User;
import com.web.data.dto.ImageDto;
import com.web.repository.ImageRepo;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageRepo imageRepo;
	
	@Override
	public Image createImage(User user, String imgFilename, MultipartFile file) {
		Image image = null;
		if(!file.isEmpty()) {
			image = new Image(LocalDateTime.now(Clock.systemUTC()), imgFilename);
			image.setImgUser(user);
			imageRepo.save(image);
		}
		return image;
	}
	
	@Override
	public Image createImage(Group group, String imgFilename, MultipartFile file) {
		Image image = null;
		if(!file.isEmpty()) {
			image = new Image(LocalDateTime.now(Clock.systemUTC()), imgFilename);
			image.setImgGroup(group);
			imageRepo.save(image);
		}
		return image;
	}
	
	@Override
	public Image createImage(Chat chat, String imgFilename, MultipartFile file) {
		Image image = null;
		if(!file.isEmpty()) {
			image = new Image(LocalDateTime.now(Clock.systemUTC()), imgFilename);
			image.setImgChat(chat);
			imageRepo.save(image);
		}
		return image;
	}

	@Override
	public void likeImage(User currentUser, User user, Image image) {
		if(user.equals(currentUser)) {
			Set<User> like = image.getImgLikes();
			if(like.contains(user)) {
				like.remove(user);
			} else {
				like.add(user);
			}
		}
	}

	@Override
	public Iterable<ImageDto> findByImgGroup(User currentUser, Group group) {
		return imageRepo.findByImgGroup(currentUser, group);
	}

	@Override
	public Iterable<ImageDto> findByImgOwner(User currentUser, User user) {
		return imageRepo.findByImgOwner(currentUser, user);
	}

	@Override
	public Iterable<ImageDto> findByImgChat(User currentUser, Chat chat) {
		return imageRepo.findByImgChat(currentUser, chat);
	}
	
	
}
