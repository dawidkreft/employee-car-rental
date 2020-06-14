package pl.kreft.thesis.ecr.centralsystem.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.message.model.Message;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    Optional<Message> findById(UUID id);

    Optional<Message> findByIdAndRemovedFalse(UUID id);

    Message save(Message message);
}
