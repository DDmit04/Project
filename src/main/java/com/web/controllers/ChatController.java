package com.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Chat;
import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.repository.ChatRepo;
import com.web.service.FileService;
import com.web.service.UploadType;
import com.web.service.UserService;

@Controller
public class ChatController {
	
	@Autowired
	private ChatRepo chatRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("messages")
	public String getUserChats(@AuthenticationPrincipal User currentUser,
							   Model model) {
		Iterable<ChatDto> userChats = chatRepo.findUserChats(currentUser);
		model.addAttribute("userChats", userChats);
		return "chatList";
	}
	
	@GetMapping("/createChat")
	public String createChat() {
		return "createChat";
	}
	
	@PostMapping("/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
							 @RequestParam String chatName,
							 @RequestParam(required = false) String chatTitle,
							 @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		Chat chat = new Chat();
		chat.setChatName(chatName);
		chat.setChatTitle(chatTitle);
		chat.setChatPicName(fileService.uploadFile(file, UploadType.CHAT_PIC));
		chatRepo.save(chat);
		chat.setChatOwner(currentUser);
		chat.getChatMembers().add(currentUser);
		chat.getChatAdmins().add(currentUser);
		chat.getChatsArcive().add(currentUser);
		chatRepo.save(chat);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("{user}/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user) {
		Chat chat = new Chat();
		chat.setChatName(currentUser.getUsername() + " - " + user.getUsername());
		chatRepo.save(chat);
		chat.setChatOwner(currentUser);
		chat.getChatMembers().add(currentUser);
		chat.getChatMembers().add(user);
		chat.getChatsArcive().add(currentUser);
		chat.getChatsArcive().add(user);
		chatRepo.save(chat);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("/chats/{chat}/{user}/invate")
	public String invateUser(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user,
						     @PathVariable Chat chat) {
		chat.getChatMembers().add(user);
		chat.getChatsArcive().add(user);
		chatRepo.save(chat);
		return "redirect:/chats/" + chat.getId();
	}
	
	 @GetMapping("chats/{chat}/{user}/leave")
	 public String leaveChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		 chat.getChatMembers().remove(user);
		 chatRepo.save(chat);
		 return "redirect:/chats/" + chat.getId();
	 }
	 
	 @GetMapping("chats/{chat}/{user}/return")
	 public String returnChat(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  @PathVariable User user, 
							  Model model) {
		 if(user.getSavedChats().contains(chat)) {
			 chat.getChatMembers().add(user);
		 }
		 chatRepo.save(chat);
		 return "redirect:/chats/" + chat.getId();
	 }
	 
	 @GetMapping("chats/{chat}/{user}/delete")
	 public String deleteChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		 if(currentUser.equals(user)) {
			 if(chat.getChatAdmins().contains(currentUser)) {
				 chat.getChatAdmins().remove(user);
			 }
			 if(chat.getChatMembers().contains(currentUser)) {
				 chat.getChatMembers().remove(user);
			 }
			 chat.getChatsArcive().remove(user);
			 chatRepo.save(chat);
		 }
		 return "redirect:/chats/" + chat.getId();
	 }
	 
	 @GetMapping("chats/{chat}/{user}/chaseOut")
	 public String chaseOutUser(@AuthenticationPrincipal User currentUser, 
							    @PathVariable Chat chat, 
							    @PathVariable User user, 
							    Model model) {
		if (chat.getChatAdmins().contains(currentUser) || chat.getChatOwner().equals(currentUser)) {
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
		}
		return "redirect:/chats/" + chat.getId();
	 }
	 
	 @GetMapping("/chats/{chat}/{user}/giveAdmin")
		public String giveAdmin(@AuthenticationPrincipal User currentUser,
								@PathVariable Chat chat,
								@PathVariable User user) {
		if(chat.getChatOwner().equals(currentUser)) {
			chat.getChatAdmins().add(user);
			chatRepo.save(chat);
		}
		return "redirect:/chats/" + chat.getId();
	}
	 
	 @GetMapping("/chats/{chat}/{user}/removeAdmin")
		public String removeAdmin(@AuthenticationPrincipal User currentUser,
								  @PathVariable Chat chat,
								  @PathVariable User user) {
			if(user.equals(currentUser) || chat.getChatOwner().equals(currentUser)) {
				chat.getChatAdmins().remove(user);
				chatRepo.save(chat);
			}
			return "redirect:/chats/" + chat.getId();
	 }
	 
	 @GetMapping("/chats/{chat}/{user}/ban")
		public String banUserInChat(@AuthenticationPrincipal User currentUser,
							  		@PathVariable Chat chat,
							  		@PathVariable User user) {
			if(chat.getChatOwner().equals(currentUser) || chat.getChatAdmins().contains(currentUser)) {
				chat.getChatBanList().add(user);
				chat.getChatMembers().remove(user);
				chatRepo.save(chat);
			}
			return "redirect:/chats/" + chat.getId();
		}
		
		@GetMapping("/chats/{chat}/{user}/unban")
		public String unbanUserInChat(@AuthenticationPrincipal User currentUser,
						 	 		  @PathVariable Chat chat,
						 	 		  @PathVariable User user) {
			if(chat.getChatOwner().equals(currentUser) || chat.getChatAdmins().contains(currentUser)) {
				chat.getChatBanList().remove(user);
				chatRepo.save(chat);
			}
			return "redirect:/chats/" + chat.getId();
		}
		
		@GetMapping("/chats/{chat}/{user}/makeOwner")
		public String makeChatOwnerAuth(Model model) {
			model.addAttribute("loginAttention", "confirm the action on the group with login and password");
			return "login";
		}
		
		@PostMapping("/chats/{chat}/{user}/makeOwner")
		public String makeChatOwner(@AuthenticationPrincipal User currentUser,
								 	@RequestParam String username,
								 	@RequestParam String password,
								 	@PathVariable Chat chat,
								 	@PathVariable User user) {
			//Password encoder!!!
			User chatOwner = chat.getChatOwner();
			if(chatOwner.equals(currentUser) 
					&& username.equals(chatOwner.getUsername()) 
					&& password.equals(chatOwner.getPassword())) {
				chat.setChatOwner(user);
				chatRepo.save(chat);
			}
			return "redirect:/groups//socialList/groupAdmins";
		}
		
}