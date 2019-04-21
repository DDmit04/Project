package com.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.service.UserMailService;
import com.web.service.UserService;

@Controller
public class UserSettingsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMailService userMailService;
	
	@GetMapping("{user}/profile/settings")
	public String settings(@AuthenticationPrincipal User currentUser,
						   @PathVariable User user,
						   @ModelAttribute("redirectMessage") String redirectMessage,
						   @ModelAttribute("currentPasswordError") String currentPasswordError,
						   @ModelAttribute("changeEmailCodeError") String changeEmailCodeError,
						   @ModelAttribute("accountDeleteError") String accountDeleteError,
						   @ModelAttribute("changeEmailError") String changeEmailError,
						   Model model) {
		if(user.getEmailChangeCode() != null) {
			model.addAttribute("codeSended", "sended");
		}
		model.addAttribute("currentEmail", user.getUserEmail());
		model.addAttribute("emailConfirmed", (user.getEmailConfirmCode() == null));
		return "profileSettings";
	}
	
	@PostMapping("{user}/profile/settings/changePassword")
	public String changePassword(@AuthenticationPrincipal User currentUser,
								 @PathVariable User user,
								 @RequestParam String currentPassword,
								 @RequestParam("password") String newPassword,
							   	 RedirectAttributes redirectAttrs,
								 Model model) {
		//Password encoder!!!
		try {
			userService.changePassword(currentUser, currentPassword, newPassword);
		} catch (UserException userException) {
			redirectAttrs.addFlashAttribute("currentPasswordError", userException.getMessage());
			return "redirect:/" + user.getId() + "/profile/settings";
		}
		redirectAttrs.addFlashAttribute("redirectMessage", "password seccesfuli changed!");
		return "redirect:/" + user.getId() + "/profile/settings";
	}
	
	@GetMapping("{user}/profile/settings/sendCode")
	public String sendChangeEmailCode(@AuthenticationPrincipal User currentUser,
			 						  @PathVariable User user,
			 						  RedirectAttributes redirectAttrs,
								      Model model) {
		try {
			userMailService.sendChangeEmailCode(currentUser);
		} catch (MailSendException | SMTPSendFailedException a) {
			redirectAttrs.addFlashAttribute("redirectMessage", "something go wrong with email send, pleace try leter");
			return "redirect:/" + user.getId() + "/profile/settings";
		}
		return "redirect:/" + user.getId() + "/profile/settings";
	}
	
	@PostMapping("{user}/profile/settings/changeEmail")
	public String changeEmail(@AuthenticationPrincipal User currentUser,
							  @RequestParam String newEmail,
							  @RequestParam String changeEmailCode,
							  @PathVariable User user,
							  RedirectAttributes redirectAttrs,
							  Model model) {
		try {
			userMailService.changeEmail(currentUser, changeEmailCode, newEmail);
		} catch (UserException e) {
			redirectAttrs.addFlashAttribute("changeEmailError", e.getMessage());
			return "redirect:/" + user.getId() + "/profile/settings";
		} catch (SMTPSendFailedException | MailSendException e) {
			redirectAttrs.addFlashAttribute("redirectMessage", "something go wrong with email send, pleace try leter");
			return "redirect:/" + user.getId() + "/profile/settings";
		}
		redirectAttrs.addFlashAttribute("codeSended", "sended");
		redirectAttrs.addFlashAttribute("redirectMessage", "email seccesfuly changed!");
		return "redirect:/" + user.getId() + "/profile/settings";
	}
	
	@PostMapping("{user}/profile/settings/deleteAccount")
	public String deleteAccount(@AuthenticationPrincipal User currentUser,
							    @PathVariable User user,
								@RequestParam String accountDeletePassword,
								HttpServletRequest request,
								RedirectAttributes redirectAttrs,
								Model model) throws ServletException {
		try {
			userService.deleteUser(currentUser, accountDeletePassword);
			request.logout();
		} catch (UserException e) {
			redirectAttrs.addFlashAttribute("accountDeleteError", e.getMessage());
			return "redirect:/" + user.getId() + "/profile/settings";
		}
		return "redirect:/login";
	}

}
