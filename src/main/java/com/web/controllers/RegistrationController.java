package com.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String userRegistration() {
		return "registrationForm";
	}
	
	@PostMapping("/registration")
	public String addNewUser(@RequestParam("userPic") MultipartFile userPic,
							 User user,
			   				 Model model) throws IllegalStateException, IOException {
		try {
			userService.addUser(user, userPic);
		} catch (UserException e) {
			model.addAttribute("usernameError", e.getMessage());
			model.addAttribute("registrationName", e.getUser().getUsername());
			return "registrationForm";
		}
		return "redirect:/posts";
	}

}
