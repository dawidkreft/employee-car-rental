package pl.kreft.thesis.ecr.centralsystem.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @NonNull
    UUID id;

    @NonNull
    String name;

    @NonNull
    String surname;

    @NonNull
    String email;

    @NonNull
    UserRole role;

    @NonNull
    LocalDateTime creationDate;

    @Nullable
    UUID bossId;

    @Nullable
    String boosName;

    @Nullable
    String boosSurname;
}