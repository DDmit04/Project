package com.web.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
import com.web.service.MessageService;
 
@Controller
public class MessageController {
 
    @Autowired
    private MessageService messageService;
	    
    @GetMapping("chats/{chat}")
	public String getMessages(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  Model model) {
		if (chat.getChatMembers().contains(currentUser) || chat.getChatsArcive().contains(currentUser)) {
			User userFromDb = messageService.findUserByUsername(currentUser);
			UserDto userDto = messageService.findOneUserToChat(currentUser, chat);
			ChatDto chatDto = messageService.findOneChat(chat);
			Set<Message> chatMessages = chat.getChatMessages();
			model.addAttribute("user", userDto);
			model.addAttribute("chat", chatDto);
			model.addAttribute("chatMessages", chatMessages);
			model.addAttribute("friends", userFromDb.getUserFriends());
			model.addAttribute("subscriptions", userFromDb.getSubscriptions());
			model.addAttribute("subscribers", userFromDb.getSubscribers());
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
    	messageService.createMessage(chatId, jsonMessage);
		return jsonMessage;
	}
}