package com.chatserver.controller;

import com.chatserver.model.Message;
import com.chatserver.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/message") //app/message
    @SendTo("/chatroom/public")
    public Message sendMessage(@Payload Message message) {
        logger.info("Public message received: {}", message);
        messageService.saveMessage(message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message sendPrivateMessage(@Payload Message message) {
        logger.info("Private message received: {}", message);
        messagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        messageService.saveMessage(message);
        return message;
    }

    @GetMapping("/messages/public")
    @ResponseBody
    public List<Message> getPublicMessages() {
        return messageService.getPublicMessages();
    }

    @GetMapping("/messages/private/{username}")
    @ResponseBody
    public List<Message> getPrivateMessages(@PathVariable String username) {
        return messageService.getPrivateMessages(username);
    }
}
