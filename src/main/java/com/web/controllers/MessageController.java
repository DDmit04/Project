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
import com.web.data.dto.ChatDto;
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
    		User userFromDb = userRepo.findByUsername(currentUser.getUsername());
    		UserDto userDto = userRepo.findOneToChat(currentUser.getId(), chat);
    		ChatDto chatDto = chatRepo.findOneChat(chat.getId());
    		Set<Message> chatMessages = chat.getChatMessages();
			model.addAttribute("user", userDto);
			model.addAttribute("chatMessages", chatMessages);
			model.addAttribute("friends", userFromDb.getUserFriends());
			model.addAttribute("subscriptions", userFromDb.getSubscriptions());
			model.addAttribute("subscribers", userFromDb.getSubscribers());
			model.addAttribute("chat", chatDto);
			model.addAttribute("chatMembers", chat.getChatMembers());
			model.addAttribute("chatArcive", chat.getChatsArcive());
			model.addAttribute("chatAdmins", chat.getChatAdmins());
			model.addAttribute("chatBanList", chat.getChatBanList());
//			Important
			model.addAttribute("listType", " "); 
			return "chat";
    	} else {
    		return "noAccessChat";
    	}
		
	}
	
    @MessageMapping("/chat.sendMessage/{chatId}")
	@SendTo("/topic/public/{chatId}")
	public MessageJson sendMessage(@DestinationVariable Long chatId, @Payload MessageJson jsonMessage) {
    	Chat chat = chatRepo.findChatById(chatId);
    	User messageAuthor = userRepo.findByUsername(jsonMessage.getSender());
    	Message message = new Message(jsonMessage.getContent());
    	message.setMessageAuthor(messageAuthor);
    	message.setMessageChat(chat);
		messageRepo.save(message);
		return jsonMessage;
	}
}