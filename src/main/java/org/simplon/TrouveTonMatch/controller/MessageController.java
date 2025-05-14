package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.MessageDto;
import org.simplon.TrouveTonMatch.mapper.MessageMapper;
import org.simplon.TrouveTonMatch.model.Message;
import org.simplon.TrouveTonMatch.service.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public MessageDto sendMessage(@RequestBody MessageDto messageDto) {
        Message message = messageService.sendMessage(messageDto);
        messagingTemplate.convertAndSend("/topic/messages", message);
        return messageDto;
    }
    @GetMapping("/{sender}")
    public List<Message> getMessagesBySender(@PathVariable Long senderId) {
        return messageService.getMessagesBySender(senderId);
    }
}
