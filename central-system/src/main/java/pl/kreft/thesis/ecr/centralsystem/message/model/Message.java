package pl.kreft.thesis.ecr.centralsystem.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "message_id")
    UUID id;

    @NonNull
    @Column(name = "message_author_id")
    UUID authorId;

    @NonNull
    @Column(name = "message_recipient_id")
    UUID recipientId;

    @NonNull
    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    MessageType type;

    @NonNull
    @Column(name = "message_subject")
    String subject;

    @NonNull
    @Column(name = "message_contents")
    String contents;

    @NonNull
    @Column(name = "message_creation_date")
    Instant creationDate;

    @NonNull
    @Column(name = "message_removed")
    Boolean removed;
}