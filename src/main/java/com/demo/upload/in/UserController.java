package com.demo.upload.in;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })

	public User upload(@RequestPart("file") List<MultipartFile> file) {

		User userJson = userService.getJson(file);
		return userJson;
	}
}
