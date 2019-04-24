package com.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.service.PasswordRecoverService;

@Controller
public class PasswordRecoverController {
	
	@Autowired
	private PasswordRecoverService passwordRecoverService;
	
	@GetMapping("/passwordRecover")
	public String recoverPassword() {
		return "passwordRecover";
	}
	
	@PostMapping("passwordRecover/recoverData")
	public String getRecovderData(@RequestParam String recoverData,
								  HttpServletRequest request,
								  Model model) {
		User user;
		try {
			user = passwordRecoverService.realizeSendPasswordRecoverCode(recoverData);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("recoverData", recoverData);
			model.addAttribute("user", user);
		} catch (UserException e) {
			model.addAttribute("recoverDataError", e.getMessage());
		} catch (MailSendException | SMTPSendFailedException e) {
			model.addAttribute("passwordRecoverAttention", "something go wrong, pleace try leter");
		}
		model.addAttribute("recoverData", recoverData);
		return "passwordRecover";
	}
	
	@GetMapping("passwordRecover/sendCode")
	public String getPasswordRecoverCode(HttpServletRequest request,
										 Model model) {
		User user = (User) request.getSession().getAttribute("user");
		String recoverData = (String) request.getSession().getAttribute("recoverData");
		model.addAttribute("user", user);
		model.addAttribute("recoverData", recoverData);
		try {
			passwordRecoverService.sendPasswordRecoverCode(user, user.getUserEmail());
		} catch (MailSendException | SMTPSendFailedException e) {
			model.addAttribute("passwordRecoverAttention", "something go wrong, pleace try leter");
		}
		return "passwordRecover";
	}
	
	@PostMapping("passwordRecover/newPassword")
	public String setNewPassword(@RequestParam String password,
								 @RequestParam String emailCode,
								 HttpServletRequest request,
								 Model model) {
		User user = (User) request.getSession().getAttribute("user");
		String recoverData = (String) request.getSession().getAttribute("recoverData");
		model.addAttribute("user", user);
		model.addAttribute("recoverData", recoverData);
		try {
			passwordRecoverService.checkPasswordRecoverCode(user, emailCode);
			passwordRecoverService.changeRecoveredPassword(user, password);
		} catch (UserException e) {
			UserExceptionType exType = e.getUserExceptionType();
			if(exType == UserExceptionType.WRONG_PASSWORD_RECOVER_CODE) {
				model.addAttribute("emailCodeError", e.getMessage());
			} 
			if(exType == UserExceptionType.CHANGE_PASSWORD) {
				model.addAttribute("passwordError", e.getMessage());
			}
			return "passwordRecover";
		}
		request.getSession().setAttribute("user", null);
		request.getSession().setAttribute("recoverData", null);
		model.addAttribute("loginAttention", "password successfully changed!");
		return "AttentionPage";
	}


}
