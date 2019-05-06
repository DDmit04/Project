package com.web.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.User;
import com.web.service.UploadType;

public interface FileService {

	String uploadFile(User user, MultipartFile file, UploadType type) throws IllegalStateException, IOException;
	
	String uploadFile(Group group, MultipartFile file, UploadType type) throws IllegalStateException, IOException;

	String uploadFile(Chat chat, MultipartFile file, UploadType type) throws IllegalStateException, IOException;

}
