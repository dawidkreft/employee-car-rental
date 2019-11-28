package pl.kreft.thesis.ecr.centralsystem.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "user_id")
    UUID id;

    @Column(name = "user_name")
    String name;

    @Column(name = "user_surname")
    String surname;

    @Column(name = "user_password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    UserRole role;

    @Column(name = "boss_id_fk")
    UUID boss;

    @Column(name = "user_active")
    Boolean isActive;

    @Column(name = "creation_date")
    Instant creationDate;

    @Column(name = "user_removed")
    Boolean removed;
}