package com.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.api.user.UserCreationService;
import com.web.api.user.UserSettingsService;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserCreationService userService;
	
	@Autowired
	private UserSettingsService userSettingsService;
	
	@GetMapping("/registration")
	public String userRegistration() {
		return "rgistrationForm";
	}
	
	@PostMapping("/registration")
	public String addNewUser(@RequestParam("userPic") MultipartFile userPic,
							 User user,
			   				 Model model) throws IllegalStateException, IOException {
		try {
			userService.createFullUser(user, userPic);
		} catch (UserException e) {
			UserExceptionType exType = e.getUserExceptionType();
			if(exType == UserExceptionType.EXISTING_USERNAME) {
				model.addAttribute("usernameError", e.getMessage());
			} 
			else if(exType == UserExceptionType.EXISTING_EMAIL) {
				model.addAttribute("userEmailError", e.getMessage());
			}
			model.addAttribute("registrationEmail", e.getUser().getUserEmail());
			model.addAttribute("registrationName", e.getUser().getUsername());
			return "rgistrationForm";
		} catch (MailSendException | SMTPSendFailedException e) {
			model.addAttribute("registrationAttention", "something go wrong, pleace try leter");
			return "rgistrationForm";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/activate/{code}")
	public String userActivate(@PathVariable String code,
							   HttpServletRequest request,
							   Model model) throws ServletException {
		request.logout();
		try {
			userSettingsService.confirmEmail(code); 
		} catch (UserException e) {
			model.addAttribute("loginAttention", e.getMessage());
			return "AttentionPage";
		}
		model.addAttribute("loginAttention", "mail successfully confirmed");
		return "AttentionPage";
	}

}
