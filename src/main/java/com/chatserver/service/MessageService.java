package com.chatserver.service;

import com.chatserver.model.Message;
import com.chatserver.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getPublicMessages() {
        return messageRepository.findByReceiverNameIsNull();
    }

    public List<Message> getPrivateMessages(String username) {
        return messageRepository.findByReceiverName(username);
    }
}
