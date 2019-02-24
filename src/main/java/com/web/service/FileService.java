package com.web.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;

@Service
public class FileService {
	
	@Value("${upload.path.posts}")
	private String uploadPathPosts;
	
	@Value("${upload.path.userPics}")
	private String uploadPathUserPics;
	
	private String uploadPath;
	
	public String uploadFile(MultipartFile file, UploadType type) throws IllegalStateException, IOException {
		String resultFilename = null;
		if(type == UploadType.POST) {
			System.out.println("upload post");
			uploadPath = uploadPathPosts;
		}
		if(type == UploadType.USERPIC) {
			System.out.println("upload userpic");
			uploadPath = uploadPathUserPics;
		}
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String uuidFile = UUID.randomUUID().toString();
			resultFilename = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFilename));
		}
		return resultFilename;
	}

}
