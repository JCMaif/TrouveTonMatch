package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.dtos.MessageDto;
import org.simplon.TrouveTonMatch.model.Message;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.MessageRepository;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(MessageDto messageDto) {

        Utilisateur sender = userRepository.findById(messageDto.senderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Utilisateur receiver = userRepository.findById(messageDto.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));


        Message message = new Message();
        message.setSenderId(messageDto.senderId());;
        message.setReceiverId(messageDto.receiverId());
        message.setContent(messageDto.content());
        message.setSentAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getMessagesBySender(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

}
