package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.message.model.Message;
import pl.kreft.thesis.ecr.centralsystem.message.model.MessageDTO;
import pl.kreft.thesis.ecr.centralsystem.message.model.MessageType;

import java.time.Instant;
import java.util.UUID;

public class MessageFactory {
    public static Message getRandomMessage() {
        return Message.builder()
                      .authorId(UUID.randomUUID())
                      .recipientId(UUID.randomUUID())
                      .type(MessageType.WEBSITE_PROBLEM)
                      .contents("MESSAGE_CONTENTS")
                      .subject("MESSAGE_SUBJECT")
                      .creationDate(Instant.now())
                      .removed(false)
                      .build();
    }

    public static MessageDTO getRandomMessageDTO() {
        return MessageDTO.builder()
                         .recipientId(UUID.randomUUID())
                         .type(MessageType.WEBSITE_PROBLEM)
                         .contents("MESSAGE_CONTENTS")
                         .subject("MESSAGE_SUBJECT")
                         .build();
    }
}