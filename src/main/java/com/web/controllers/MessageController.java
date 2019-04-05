package com.web.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
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
import com.web.data.MessageHelp;
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.repository.MessageRepo;
import com.web.repository.UserRepo;
 
@Controller
public class MessageController {
 
    @Autowired
    private MessageRepo messageRepo;
    
    @Autowired
    private ChatRepo chatRepo;
    
    @Autowired
    private UserRepo userRepo;
 
    @GetMapping("chats/{chat}")
	public String getMessages(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  Model model) {
    	Set<Message> chatMessages = chat.getChatMessages();
		model.addAttribute("currentUsername", currentUser.getUsername());
		model.addAttribute("chatId", chat.getId());
		model.addAttribute("chatMessages", chatMessages);
		return "index";
	}
	
    @MessageMapping("/chat.sendMessage/{chatId}")
	@SendTo("/topic/public/{chatId}")
	public MessageHelp sendMessage(@DestinationVariable Long chatId, @Payload MessageHelp chatMessage) {
    	Message message = new Message(chatMessage.getContent());
    	message.setMessageAuthor(userRepo.findByUsername(chatMessage.getSender()));
    	Chat chat = chatRepo.findById1(chatId);
    	message.setMessageChat(chat);
    	message.setMessageAuthor(userRepo.findByUsername(chatMessage.getSender()));
		messageRepo.save(message);
		return chatMessage;
	}

    @MessageMapping("/chat.addUser")
	@SendTo("/topic/public/{chatId}")
	public MessageHelp addUser(@DestinationVariable Long chatId, @Payload MessageHelp chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Add username in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}