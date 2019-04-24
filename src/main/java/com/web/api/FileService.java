package com.web.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.service.UploadType;

public interface FileService {

	String uploadFile(MultipartFile file, UploadType type) throws IllegalStateException, IOException;
}
