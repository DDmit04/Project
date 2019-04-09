package com.web.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.data.Chat;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.data.dto.UserDto;
import com.web.repository.ChatRepo;
import com.web.repository.MessageRepo;
import com.web.repository.UserRepo;
import com.web.service.UserService;
 
@Controller
public class MessageController {
 
    @Autowired
    private MessageRepo messageRepo;
    
    @Autowired
    private ChatRepo chatRepo;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepo userRepo;
    
    @GetMapping("chats/{chat}")
	public String getMessages(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  Model model) {
    	if(chat.getChatMembers().contains(currentUser) || chat.getChatsArcive().contains(currentUser)) {
    		UserDto usr = userService.findOneUserToList(currentUser);
    		User user = userRepo.findByUsername(currentUser.getUsername());
    		Set<Message> chatMessages = chat.getChatMessages();
			model.addAttribute("chat", chatRepo.findOneChat(chat.getId()));
			model.addAttribute("user", usr);
			model.addAttribute("chatMembers", chat.getChatMembers());
			model.addAttribute("chatArcive", chat.getChatsArcive());
			model.addAttribute("chatMessages", chatMessages);
			model.addAttribute("friends", user.getUserFriends());
			model.addAttribute("subscriptions", user.getSubscriptions());
			model.addAttribute("subscribers", user.getSubscribers());
			model.addAttribute("chatAdmins", chat.getChatAdmins());
			model.addAttribute("listType", "firends");
			return "index";
    	} else {
    		return null;
    	}
		
	}
	
    @MessageMapping("/chat.sendMessage/{chatId}")
	@SendTo("/topic/public/{chatId}")
	public MessageJson sendMessage(@DestinationVariable Long chatId, @Payload MessageJson jsonMessage) {
    	Message message = new Message(jsonMessage.getContent());
    	message.setMessageAuthor(userRepo.findByUsername(jsonMessage.getSender()));
    	Chat chat = chatRepo.findChatById(chatId);
    	message.setMessageChat(chat);
    	message.setMessageAuthor(userRepo.findByUsername(jsonMessage.getSender()));
		messageRepo.save(message);
		return jsonMessage;
	}

    @MessageMapping("/chat.addUser")
	@SendTo("/topic/public/{chatId}")
	public MessageJson addUser(@DestinationVariable Long chatId, @Payload MessageJson chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Add username in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}