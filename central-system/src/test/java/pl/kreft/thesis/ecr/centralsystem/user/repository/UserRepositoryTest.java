package pl.kreft.thesis.ecr.centralsystem.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    void shouldSaveAndFindById() {
        User testUser = UserFactory.getEmployee();
        User testUser1 = UserFactory.getSecondEmployee();

        userRepository.save(testUser);
        userRepository.save(testUser1);
        Optional<User> user = userRepository.findById(testUser.getId());
        List<User> allUsers = userRepository.findAll();

        assertEquals(testUser.getId(), user.get().getId());
        assertEquals(2, allUsers.size());
    }
}