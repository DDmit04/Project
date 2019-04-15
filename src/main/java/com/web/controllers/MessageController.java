package com.web.controllers;

import java.time.LocalDateTime;
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
import com.web.data.ChatSession;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.data.dto.UserDto;
import com.web.repository.ChatSessionRepo;
import com.web.service.MessageService;
import com.web.utils.DateUtil;
 
@Controller
public class MessageController {
 
    @Autowired
    private MessageService messageService;
    
    @Autowired
	private ChatSessionRepo chatSessionRepo;
	    
    @GetMapping("chats/{chat}")
	public String getMessages(@AuthenticationPrincipal User currentUser, 
							  @PathVariable Chat chat, 
							  Model model) {
		if (chat.getChatMembers().contains(currentUser) || chat.getChatsArcive().contains(currentUser)) {
			
			//on work
			ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
			session.setLastView(LocalDateTime.now());
			chatSessionRepo.save(session);
			
			model.addAttribute("session", session);
			model.addAttribute("DateUtills", new DateUtil());
			
			
			
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