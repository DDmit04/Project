package com.web.api;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.Image;
import com.web.data.User;
import com.web.data.dto.ImageDto;

public interface ImageService {

	Image createImage(User user, String imgFilename, MultipartFile file);

	Image createImage(Group group, String imgFilename, MultipartFile file);

	Image createImage(Chat chat, String imgFilename, MultipartFile file);

	void likeImage(User currentUser, User user, Image image);

	Iterable<ImageDto> findByImgGroup(User currentUser, Group group);

	Iterable<ImageDto> findByImgOwner(User currentUser, User user);

	Iterable<ImageDto> findByImgChat(User currentUser, Chat chat);

}