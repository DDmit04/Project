package com.web.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.FileService;
import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.User;

@Service
public class FileServiceImpl implements FileService {
	
	@Value("${upload.path.userImages}")
	private String userImagesUploadPath;

	@Value("${upload.path.groupImages}")
	private String groupImagesUploadPath;
	
	@Value("${upload.path.chatImages}")
	private String chatImagesUploadPath;
	
	private String uploadPath;

	@Override
	public String uploadFile(User user, MultipartFile file, UploadType type) throws IllegalStateException, IOException {
		String resultFilename = null;
		uploadPath = userImagesUploadPath  + "/" + user.getUsername();
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			String uuidFile = UUID.randomUUID().toString();
			resultFilename = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFilename));
		}
		return resultFilename;
	}
	
	@Override
	public String uploadFile(Group group, MultipartFile file, UploadType type) throws IllegalStateException, IOException {
		String resultFilename = null;
		uploadPath = groupImagesUploadPath + "/" + group.getGroupName();
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			String uuidFile = UUID.randomUUID().toString();
			resultFilename = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFilename));
		}
		return resultFilename;
	}
	
	@Override
	public String uploadFile(Chat chat, MultipartFile file, UploadType type) throws IllegalStateException, IOException {
		String resultFilename = null;
		uploadPath = chatImagesUploadPath  + "/" + chat.getChatName();
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			String uuidFile = UUID.randomUUID().toString();
			resultFilename = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFilename));
		}
		return resultFilename;
	}

}
