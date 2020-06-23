package pl.kreft.thesis.ecr.centralsystem.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserResponse;
import pl.kreft.thesis.ecr.centralsystem.user.service.exception.UserNotExistsException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getAdmin;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getBoss;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getEmployee;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getInspector;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getSecondEmployee;

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
    public void shouldSaveFindAllAndFindByUserId() {
        User savedUser = userService.save(getEmployee());
        userService.save(getInspector());
        userService.save(getSecondEmployee());

        List<User> allUsers = userService.getAll();
        User foundUser = userService.find(savedUser.getId());

        assertEquals(3, allUsers.size());
        assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    public void shouldSaveAndDisableUser() {
        User savedUser = userService.save(getSecondEmployee());
        userService.save(getInspector());
        userService.save(getEmployee());

        userService.disableUser(savedUser.getId());
        List<User> allUser = userService.getAll();
        List<User> allActiveUser = userService.getAllActiveUser();

        assertEquals(3, allUser.size());
        assertEquals(2, allActiveUser.size());
    }

    @Test
    public void shouldSaveAndRemoveUser() {
        User savedUser = userService.save(getSecondEmployee());
        userService.save(getInspector());
        userService.save(getEmployee());

        List<User> allUser = userService.getAll();
        userService.remove(savedUser.getId());
        List<User> allActiveUser = userService.getAllActiveUser();

        assertEquals(3, allUser.size());
        assertEquals(2, allActiveUser.size());
    }

    @Test
    public void shouldRemoveUserAndReturnExceptionWhenTryFindRemovedUser() {
        User savedUser = userService.save(getEmployee());

        List<User> allUsers = userService.getAll();
        userService.remove(savedUser.getId());

        assertEquals(1, allUsers.size());
        assertThrows(UserNotExistsException.class, () -> {
            userService.find(savedUser.getId());
        });
    }

    @Test
    public void shouldReturnUserResponse() {
        User savedUser = userService.save(getEmployee());

        UserResponse userResponse = userService.getUser(savedUser.getId());

        assertEquals(savedUser.getId(), userResponse.getId());
        assertEquals(savedUser.getEmail(), userResponse.getEmail());
        assertEquals(savedUser.getSurname(), userResponse.getSurname());
    }

    @Test
    public void shouldReturnUserResponseListForAdmins() {
        User savedUser = userService.save(getAdmin());

        List<UserResponse> userResponses = userService.getAdmins();

        assertEquals(savedUser.getId(), userResponses.get(0).getId());
        assertEquals(savedUser.getEmail(), userResponses.get(0).getEmail());
        assertEquals(savedUser.getSurname(), userResponses.get(0).getSurname());
        assertEquals(1, userResponses.size());
    }

    @Test
    public void shouldReturnsAllEmployeeByBossId() {
        User savedUser = userService.save(getEmployee());
        User savedUser1 = userService.save(getSecondEmployee());
        User boss = userService.save(getBoss());
        savedUser.setBossId(boss.getId());
        savedUser1.setBossId(boss.getId());
        userService.save(savedUser);
        userService.save(savedUser1);

        List<UserResponse> usersResponse= userService.getAllUsersByBossId(boss.getId());


        assertNotNull(usersResponse);
        assertEquals(2, usersResponse.size());
        assertEquals(boss.getId(), usersResponse.get(0).getBossId());
    }
}