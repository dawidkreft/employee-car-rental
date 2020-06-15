package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;

import java.time.Instant;

public class UserFactory {

    static public User getEmployee() {
        return User.builder()
                   .name("user")
                   .surname("surname")
                   .password("password")
                   .email("email@email.com")
                   .boss(null)
                   .role(UserRole.EMPLOYEE)
                   .isActive(true)
                   .creationDate(Instant.now())
                   .removed(false)
                   .build();
    }

    static public User getSecondEmployee() {
        return User.builder()
                   .name("user")
                   .surname("surname")
                   .password("password")
                   .email("email2@email.com")
                   .boss(null)
                   .role(UserRole.EMPLOYEE)
                   .isActive(true)
                   .creationDate(Instant.now())
                   .removed(false)
                   .build();
    }

    static public User getInspector() {
        return User.builder()
                   .name("inspektor")
                   .surname("gadzet")
                   .password("password")
                   .email("emailInspector@email.com")
                   .boss(null)
                   .role(UserRole.DISPATCHER)
                   .isActive(true)
                   .creationDate(Instant.now())
                   .removed(false)
                   .build();
    }

    static public User getSecondInspector() {
        return User.builder()
                   .name("inspektor")
                   .surname("gadzet")
                   .password("password")
                   .email("emailInspector2@email.com")
                   .boss(null)
                   .role(UserRole.DISPATCHER)
                   .isActive(true)
                   .creationDate(Instant.now())
                   .removed(false)
                   .build();
    }

    static public User getAdmin() {
        return User.builder()
                   .name("admin")
                   .surname("admin")
                   .password("password")
                   .email("emailAdmin@email.com")
                   .boss(null)
                   .role(UserRole.ADMIN)
                   .isActive(true)
                   .creationDate(Instant.now())
                   .removed(false)
                   .build();
    }
}
