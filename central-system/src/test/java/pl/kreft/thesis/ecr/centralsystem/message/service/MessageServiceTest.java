package pl.kreft.thesis.ecr.centralsystem.message.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.message.model.Message;
import pl.kreft.thesis.ecr.centralsystem.message.model.MessageDTO;

import java.util.UUID;

import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.MessageFactory.getRandomMessageDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    void shouldSaveMessageFromMessageDTO() {
        UUID authorId = UUID.randomUUID();
        MessageDTO messageDTO = getRandomMessageDTO();

        Message message = messageService.save(getRandomMessageDTO(), authorId);

        Assertions.assertEquals(message.getAuthorId(), authorId);
        Assertions.assertEquals(message.getRecipientId(), messageDTO.getRecipientId());
        Assertions.assertEquals(message.getContents(), messageDTO.getContents());
    }

}