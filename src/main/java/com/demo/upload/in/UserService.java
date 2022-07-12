package com.demo.upload.in;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	public User getJson(List<MultipartFile> file) {

		User userJson = new User();
		
		int fileCount = file.size();
		userJson.setCount(fileCount);
		
		return userJson;

	}

}
