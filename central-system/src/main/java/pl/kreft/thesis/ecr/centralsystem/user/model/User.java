package pl.kreft.thesis.ecr.centralsystem.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "user_id")
    UUID id;

    @NonNull
    @Column(name = "user_name")
    String name;

    @NonNull
    @Column(name = "user_surname")
    String surname;

    @NonNull
    @Column(name = "user_password")
    String password;

    @NonNull
    @Column(name = "user_email" , unique = true)
    String email;

    @NonNull
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    UserRole role;

    @Nullable
    @Column(name = "user_boss_id_fk")
    UUID boss;

    @NonNull
    @Column(name = "user_active")
    Boolean isActive;

    @NonNull
    @Column(name = "user_creation_date")
    Instant creationDate;

    @NonNull
    @Column(name = "user_removed")
    Boolean removed;
}
