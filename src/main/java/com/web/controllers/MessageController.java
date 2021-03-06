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

import com.web.api.MessageService;
import com.web.api.chat.ChatService;
import com.web.api.chat.ChatSessionService;
import com.web.api.user.UserService;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.data.dto.UserDto;
import com.web.service.ChatServiceImpl;
import com.web.service.ChatSessionServiceImpl;
import com.web.service.MessageServiceImpl;
import com.web.service.UserServiceImpl;
 
@Controller
public class MessageController {
 
    private MessageService messageService;
    private UserService userService;
   	private ChatSessionService chatSessionService;
   	private ChatService chatService;
   	
   	@Autowired
    public MessageController(MessageServiceImpl messageService, UserServiceImpl userService, 
    						 ChatSessionServiceImpl chatSessionService, ChatServiceImpl chatService) {
		this.messageService = messageService;
		this.userService = userService;
		this.chatSessionService = chatSessionService;
		this.chatService = chatService;
	}

	@GetMapping("chats/{chat}")
	public String getMessages(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  Model model) {
    	ChatSession session = chatSessionService.getSession(currentUser, chat);
		if (session != null) {
		    chatSessionService.updateLastConnectionDate(currentUser, chat);
			User userFromDb = userService.getUserByUsername(currentUser);
			UserDto userDto = userService.getOneUserToChat(currentUser, chat);
			ChatDto chatDto = chatService.getOneChat(chat);
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
	
    @MessageMapping("/chat.sendMessage/{chatId}")
	@SendTo("/topic/public/{chatId}")
	public MessageJson sendMessage(@DestinationVariable Long chatId, 
								   @Payload MessageJson jsonMessage) {
    	messageService.createMessage(chatId, jsonMessage);
    	User user = userService.findUserById(jsonMessage.getSenderId());
    	chatSessionService.updateLastConnectionDate(user, chatId);
		return jsonMessage;
	}
}