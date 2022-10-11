package com.comaecod.jogtracker.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comaecod.jogtracker.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		String fileName = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String fileNameWithUUID = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
		String filePath = path + File.separator + fileNameWithUUID;

		// Create Folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileNameWithUUID;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(filePath);
		return inputStream;

	}

}
