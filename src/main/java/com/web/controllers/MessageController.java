package com.web.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.web.data.Message;

@Controller
public class MessageController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        Thread.sleep(3000); // simulated delay
        return new Message("Hello, " + message.getMessageText() + "!");
    }

}