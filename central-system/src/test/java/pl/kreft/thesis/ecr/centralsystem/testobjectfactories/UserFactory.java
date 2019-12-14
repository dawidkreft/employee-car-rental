package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;

import java.time.Instant;

public class UserFactory {

    static public User getEmployee(){
        return new User(
                "Jan",
                "Kowalski",
                "test",
                "email@email.com",
                UserRole.EMPLOYEE,
                null,
                true,
                Instant.now(),
                false
        );
    }

    static public User getInspector(){
        return new User(
                "Inspektor",
                "Gadżet",
                "test",
                "email@email.com",
                UserRole.DISPATCHER,
                null,
                true,
                Instant.now(),
                false
        );
    }

}
