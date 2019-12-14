package pl.kreft.thesis.ecr.centralsystem.user.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getEmployee;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getInspector;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    public void shouldSaveFindAllAndFindByUserId() throws ObjectNotFoundException {
        User savedUser = userService.save(getEmployee());
        userService.save(getInspector());
        userService.save(getEmployee());

        List<User> allUsers = userService.getAll();
        User foundUser = userService.find(savedUser.getId());

        assertEquals(3, allUsers.size());
        assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    public void shouldSaveAndDisableUser() throws ObjectNotFoundException {
        User savedUser = userService.save(getEmployee());
        userService.save(getInspector());
        userService.save(getEmployee());

        userService.disableUser(savedUser.getId());
        List<User> allUser = userService.getAll();
        List<User> allActiveUser = userService.getAllActiveUser();

        assertEquals(3, allUser.size());
        assertEquals(2, allActiveUser.size());
    }

    @Test
    public void shouldSaveAndRemoveUser() throws ObjectNotFoundException {
        User savedUser = userService.save(getEmployee());
        userService.save(getInspector());
        userService.save(getEmployee());

        List<User> allUser = userService.getAll();
        userService.remove(savedUser.getId());
        List<User> allActiveUser = userService.getAllActiveUser();

        assertEquals(3, allUser.size());
        assertEquals(2, allActiveUser.size());
    }

    @Test
    public void shouldRemoveUserAndReturnExceptionWhenTryFindRemovedUser() throws ObjectNotFoundException {
        User savedUser = userService.save(getEmployee());

        List<User> allUsers = userService.getAll();
        userService.remove(savedUser.getId());

        assertEquals(1, allUsers.size());
        assertThrows(ObjectNotFoundException.class, () -> {
            userService.find(savedUser.getId());
        });
    }
}