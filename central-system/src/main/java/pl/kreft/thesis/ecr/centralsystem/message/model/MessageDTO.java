package pl.kreft.thesis.ecr.centralsystem.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    @NonNull
    UUID recipientId;

    @NonNull
    MessageType type;

    @NonNull
    String subject;

    @NonNull
    String contents;
}
