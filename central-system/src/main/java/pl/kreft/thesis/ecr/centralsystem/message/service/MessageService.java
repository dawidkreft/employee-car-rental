package pl.kreft.thesis.ecr.centralsystem.message.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.message.model.Message;
import pl.kreft.thesis.ecr.centralsystem.message.model.MessageDTO;
import pl.kreft.thesis.ecr.centralsystem.message.repository.MessageRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(MessageDTO messageDTO, UUID authorId) {
        log.info("Method save called");
        Message message = prepareMessage(messageDTO, authorId);
        return messageRepository.save(message);
    }

    private Message prepareMessage(MessageDTO messageDTO, UUID authorId) {
        return Message.builder()
                      .id(UUID.randomUUID())
                      .type(messageDTO.getType())
                      .subject(messageDTO.getSubject())
                      .contents(messageDTO.getContents())
                      .authorId(authorId)
                      .recipientId(messageDTO.getRecipientId())
                      .removed(false)
                      .creationDate(Instant.now())
                      .build();
    }
}
