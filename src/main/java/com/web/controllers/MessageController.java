package com.web.controllers;

import java.util.LinkedList;

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

import com.web.api.user.UserCreationService;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.data.dto.UserDto;
import com.web.service.ChatSessionServiceImpl;
import com.web.service.MessageServiceImpl;
 
@Controller
public class MessageController {
 
    @Autowired
    private MessageServiceImpl messageService;
    
    @Autowired
    private UserCreationService userService;
    
    @Autowired
   	private ChatSessionServiceImpl chatSessionService;
	    
    @GetMapping("chats/{chat}")
	public String getMessages(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  Model model) {
    	ChatSession session = chatSessionService.getSession(currentUser, chat);
		if (session != null) {
		    chatSessionService.updateLastConnectionDate(currentUser, chat);
			User userFromDb = userService.getUserByUsername(currentUser);
			UserDto userDto = messageService.getOneUserToChat(currentUser, chat);
			ChatDto chatDto = messageService.getOneChat(chat);
			LinkedList<Message> chatMessages = messageService.getChatMessages(session, chat);
			model.addAttribute("user", userDto);
			model.addAttribute("chat", chatDto);
			model.addAttribute("session", session);
			model.addAttribute("chatMessages", chatMessages);
			model.addAttribute("friends", userFromDb.getUserFriends());
			model.addAttribute("subscriptions", userFromDb.getSubscriptions());
			model.addAttribute("subscribers", userFromDb.getSubscribers());
			model.addAttribute("chatMembers", chat.getChatMembers());
			model.addAttribute("chatAdmins", chat.getChatAdmins());
			model.addAttribute("chatBanList", chat.getChatBanList());
			return "chat";
		} else {
			return "noAccessChat";
		}
	}
    
    @GetMapping("chats/{chat}/disconnectChat")
   	public String disconnectChat(@AuthenticationPrincipal User currentUser, 
   							 @PathVariable Chat chat) {
       chatSessionService.updateLastConnectionDate(currentUser, chat);
       return "redirect:/messages";
   	}
	
    @MessageMapping("/chat.sendMessage/{chatId}")
	@SendTo("/topic/public/{chatId}")
	public MessageJson sendMessage(@DestinationVariable Long chatId, @Payload MessageJson jsonMessage) {
    	messageService.createMessage(chatId, jsonMessage);
		return jsonMessage;
	}
}