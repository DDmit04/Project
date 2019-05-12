package com.web.service;

import java.io.File;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.ImageService;
import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.User;
import com.web.data.dto.ImageDto;
import com.web.repository.ChatRepo;
import com.web.repository.GroupRepo;
import com.web.repository.ImageRepo;
import com.web.repository.UserRepo;

@Service
public class ImageServiceImpl implements ImageService {
	
	private ImageRepo imageRepo;
	private ChatRepo chatRepo;
	private UserRepo userRepo;
	private GroupRepo groupRepo;
	
	@Value("${upload.path.userImages}")
	private String userImagesUploadPath;

	@Value("${upload.path.groupImages}")
	private String groupImagesUploadPath;

	@Value("${upload.path.chatImages}")
	private String chatImagesUploadPath;

	@Autowired
	public ImageServiceImpl(ImageRepo imageRepo, ChatRepo chatRepo, UserRepo userRepo, GroupRepo groupRepo) {
		this.imageRepo = imageRepo;
		this.chatRepo = chatRepo;
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
	}



	@Override
	public Image createImage(User user, String imgFilename, MultipartFile file) {
		Image image = new Image(LocalDateTime.now(Clock.systemUTC()), imgFilename);
		image.setImgUser(user);
		imageRepo.save(image);
		return image;
	}
	
	@Override
	public Image createImage(Group group, String imgFilename, MultipartFile file) {
		Image image = new Image(LocalDateTime.now(Clock.systemUTC()), imgFilename);
		image.setImgGroup(group);
		imageRepo.save(image);
		return image;
	}
	
	@Override
	public Image createImage(Chat chat, String imgFilename, MultipartFile file) {
		Image image = new Image(LocalDateTime.now(Clock.systemUTC()), imgFilename);
		image.setImgChat(chat);
		imageRepo.save(image);
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
	
	//SQL can help there
	@Override
	public void deleteImage(User user, Image image) {
		if((image.getImgUser() != null && image.getImgUser().equals(user))
				|| (image.getImgGroup() != null && image.getImgGroup().getGroupAdmins().contains(user))
				|| (image.getImgChat() != null && image.getImgChat().getChatAdmins().contains(user))) {
			String imageFilename = image.getImgFileName();
			File file = null;
			if(image.getImgUser() != null) {
				file = new File(userImagesUploadPath + "/" + user.getUsername() + "/" + imageFilename);
				file.delete();
				image.getImgUser().setUserImage(null);
				userRepo.save(image.getImgUser());
			} else if(image.getImgGroup() != null) {
				file = new File(groupImagesUploadPath + "/" + image.getImgGroup().getGroupName() + "/" + imageFilename);
				file.delete();
				image.getImgGroup().setGroupImage(null);
				groupRepo.save(image.getImgGroup());
			} else if(image.getImgChat() != null) {
				file = new File(chatImagesUploadPath + "/" + image.getImgChat().getChatName() + "/" + imageFilename);
				file.delete();
				image.getImgChat().setChatImage(null);
				chatRepo.save(image.getImgChat());
			}
			imageRepo.delete(image);
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
